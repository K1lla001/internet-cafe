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
import com.atm.inet.repository.OrderDetailRepository;
import com.atm.inet.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final CustomerService customerService;
    private final TypeService typeService;
    private final TypePriceService typePriceService;
    private final ComputerService computerService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderDetailRespose create(OrderDetailRequest request) {
        CustomerResponse customerResponse = customerService.findById(request.getCostumerId());
        Type type = typeService.findById(request.getTypeId());
        TypePrice price = typePriceService.findByTypeId(type.getId());


        Computer computer = computerService.findByTypeId(type.getId());
        if(!computer.getStatus()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Computer Is Already Use");

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

        return OrderDetailRespose.builder()
                .orderId(orderDetail.getId())
                .computerCode(computer.getCode())
                .computerName(computer.getName())
                .type(type.getCategory().name())
                .price(price.getPrice())
                .endBookingDate(orderDetail.getEndBookingDate())
                .build();
    }

    @Override
    public OrderDetail getById(String id) {
        return null;
    }

    @Override
    public List<OrderDetail> getAll() {
        return null;
    }
}
