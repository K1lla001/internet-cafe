package com.atm.inet.service.impl;

import com.atm.inet.entity.Customer;
import com.atm.inet.entity.OrderDetail;
import com.atm.inet.entity.computer.Computer;
import com.atm.inet.entity.computer.Type;
import com.atm.inet.entity.computer.TypePrice;
import com.atm.inet.entity.constant.EStatus;
import com.atm.inet.model.request.OrderDetailRequest;
import com.atm.inet.model.response.CustomerResponse;
import com.atm.inet.model.response.OrderDetailRespose;
import com.atm.inet.model.response.PaymentResponse;
import com.atm.inet.repository.OrderDetailRepository;
import com.atm.inet.service.*;
import com.atm.inet.service.payment.MidtransService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.midtrans.Config;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.midtrans.service.impl.MidtransSnapApiImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Type type = typeService.findById(request.getTypeId());
        TypePrice price = typePriceService.findByTypeId(type.getId());
        Computer computer = computerService.findByTypeId(type.getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerResponse authenticateCustomer = customerService.authenticationCustomer(authentication);

        if(!customerResponse.getId().equals(authenticateCustomer.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed here!");


        System.out.println(customerResponse.getId().equals(authenticateCustomer.getId()));


        log.info("START TRANSACTION");


        if (request.getBookingDate().isBefore(LocalDateTime.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Booking Date!");


        if (!computer.getStatus()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer Is Already Use");

        Customer customer = Customer.builder()
                .id(customerResponse.getId())
                .firstName(customerResponse.getFirstName())
                .lastName(customerResponse.getLastName())
                .email(customerResponse.getEmail())
                .phoneNumber(customerResponse.getPhoneNumber())
                .isMember(customerResponse.getIsMember())
                .build();


        OrderDetail orderDetail = OrderDetail.builder()
                .customer(customer)
                .status(EStatus.PENDING)
                .type(type)
                .duration(request.getDuration())
                .bookingDate(request.getBookingDate())
                .endBookingDate(request.getBookingDate().plusHours(request.getDuration()))
                .transactionDate(LocalDateTime.now())
                .build();

        orderDetailRepository.save(orderDetail);

        OrderDetailRespose response = OrderDetailRespose.builder()
                .orderId(orderDetail.getId())
                .computerCode(computer.getCode())
                .computerName(computer.getName())
                .type(type.getCategory().name())
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
            orderDetail.setStatus(EStatus.SUCCESS);
        } else if (transactionStatus.equalsIgnoreCase("expire") || transactionStatus.equalsIgnoreCase("cancel")) {
            orderDetail.setStatus(EStatus.FAILED);
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
