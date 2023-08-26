package com.atm.inet.service;

import com.atm.inet.entity.OrderDetail;
import com.atm.inet.model.request.OrderDetailRequest;
import com.atm.inet.model.response.OrderDetailRespose;

import java.util.List;

public interface OrderDetailService {

      OrderDetailRespose create(OrderDetailRequest request);

      OrderDetail getById(String id);

      List<OrderDetail> getAll();

}
