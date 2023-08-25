package com.atm.inet.service;

import com.atm.inet.entity.Customer;
import com.atm.inet.model.common.CustomerSearch;
import com.atm.inet.model.request.CustomerRequest;
import com.atm.inet.model.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    CustomerResponse updateCustomer(CustomerRequest customer);

    CustomerResponse findById(String id);
    public String findEmailById(String id);

    Page<CustomerResponse> getCustomerPerPage(Pageable pageable, CustomerSearch customer);

    String deleteCustomer(String id);

}
