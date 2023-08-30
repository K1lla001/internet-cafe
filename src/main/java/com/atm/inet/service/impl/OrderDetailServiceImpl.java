package com.atm.inet.service.impl;

import com.atm.inet.entity.Customer;
import com.atm.inet.entity.OrderDetail;
import com.atm.inet.entity.computer.*;
import com.atm.inet.entity.constant.ECategory;
import com.atm.inet.entity.constant.EStatus;
import com.atm.inet.entity.constant.EStatusOrder;
import com.atm.inet.model.request.ComputerRequest;
import com.atm.inet.model.request.OrderDetailRequest;
import com.atm.inet.model.response.*;
import com.atm.inet.repository.OrderDetailRepository;
import com.atm.inet.service.*;
import com.atm.inet.service.payment.MidtransService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final CustomerService customerService;
    private final TypeService typeService;
    private final TypePriceService typePriceService;
    private final ComputerService computerService;
    private final MidtransService midtransService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PaymentResponse create(OrderDetailRequest request) {

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime parsedDate = LocalDateTime.parse(request.getBookingDate(), dateTimeFormatter);


        CustomerResponse customerResponse = customerService.findById(request.getCustomerId());

        ComputerResponse computerResponse = computerService.getById(request.getComputerId());
        TypePrice price = typePriceService.findByTypeId(computerResponse.getType().getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerResponse authenticateCustomer = customerService.authenticationCustomer(authentication);

        if (!customerResponse.getId().equals(authenticateCustomer.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed here!");

        log.info("START TRANSACTION");


        if (request.getBookingDate().isBefore(LocalDateTime.now()) || request.getDuration() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Booking Date or Duration!");
        if (computerResponse.getStatus().equalsIgnoreCase(EStatus.USED.name())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer Is Already Use");

        Customer customer = Customer.builder()
                .id(customerResponse.getId())
                .firstName(customerResponse.getFirstName())
                .lastName(customerResponse.getLastName())
                .email(customerResponse.getEmail())
                .phoneNumber(customerResponse.getPhoneNumber())
                .isMember(customerResponse.getIsMember())
                .build();

        List<TypePrice> typePrices = new ArrayList<>();
        computerResponse.getType().getPrices().forEach(typePriceResponse ->
                typePrices.add(TypePrice.builder()
                        .id(typePriceResponse.getId())
                        .price(typePriceResponse.getPrice())
                        .isActive(typePriceResponse.getIsActive())
                        .build()));

        FileResponse image = computerResponse.getType().getImage();

        ComputerImage computerImage = ComputerImage.builder()
                .id(image.getId())
                .name(image.getFilename())
                .path(price.getType().getComputerImage().getPath())
                .build();

        Type.builder()
                .id(computerResponse.getType().getId())
                .category(ECategory.valueOf(computerResponse.getType().getCategory()))
                .typePrices(typePrices)
                .computerImage(computerImage)
                .build();

        ComputerSpec spec = ComputerSpec.builder()
                .id(computerResponse.getSpecification().getId())
                .processor(computerResponse.getSpecification().getProcessor())
                .ram(computerResponse.getSpecification().getRam())
                .monitor(computerResponse.getSpecification().getMonitor())
                .ssd(computerResponse.getSpecification().getSsd())
                .vga(computerResponse.getSpecification().getVga())
                .build();


        Computer computer = Computer.builder()
                .id(computerResponse.getId())
                .name(computerResponse.getName())
                .code(computerResponse.getCode())
                .status(EStatus.valueOf(computerResponse.getStatus()))
                .specification(spec)
                .build();


        OrderDetail orderDetail = OrderDetail.builder()
                .customer(customer)
                .status(EStatusOrder.PENDING)
                .computer(computer)
                .duration(request.getDuration())
                .bookingDate(request.getBookingDate())
                .endBookingDate(request.getBookingDate().plusHours(request.getDuration()))
                .transactionDate(LocalDateTime.now())
                .build();

        orderDetailRepository.save(orderDetail);

        OrderDetailRespose response = OrderDetailRespose.builder()
                .orderId(orderDetail.getId())
                .computerCode(computerResponse.getCode())
                .computerName(computerResponse.getName())
                .type(computerResponse.getType().getCategory())
                .price(price.getPrice() * orderDetail.getDuration())
                .status(orderDetail.getStatus().name())
                .customerFirstName(customer.getFirstName())
                .customerLastName(customer.getLastName())
                .customerPhoneNumber(customer.getPhoneNumber())
                .customerEmail(customer.getEmail())
                .endBookingDate(orderDetail.getEndBookingDate())
                .build();


        return midtransService.requestTransaction(response);
    }

    @Override
    public String updateStatus(String id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Id Not Found!"));

        String transactionById = midtransService.getTransactionById(orderDetail.getId());

        JSONObject jsonObject = new JSONObject(transactionById);

        String transactionStatus = jsonObject.getString("transaction_status");

        if (transactionStatus.equalsIgnoreCase("settlement")) {
            orderDetail.setStatus(EStatusOrder.SUCCESS);
            Computer computer = computerService.getByComputerId(orderDetail.getComputer().getId());
            computer.setStatus(EStatus.ORDERED);
            computerService.saveByComputer(computer);
        } else if (transactionStatus.equalsIgnoreCase("expire") || transactionStatus.equalsIgnoreCase("cancel")) {
            orderDetail.setStatus(EStatusOrder.FAILED);
        }
        orderDetailRepository.save(orderDetail);
        return transactionById;
    }


    @Override
    public OrderDetail findById(String id) {
        return orderDetailRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction Data Not Found!"));
    }

    @Override
    public List<OrderDetail> getAll() {
        return orderDetailRepository.findAll();
    }
}
