//package com.atm.inet.controller;
//
//import com.atm.inet.model.request.OrderPaypalRequest;
//import com.atm.inet.service.PaypalService;
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping(path = "/v1/paypal")
//public class PaypalController {
////    private final PaypalService paypalService;
////    private static final String SUCCESS_URL = "pay/success";
////    private static final String CANCEL_URL = "pay/cancel";
////    @Value("${server.port}")
////    private Integer port;
////
////    @PostMapping("/pay")
////    public String payment(@ModelAttribute("order") OrderPaypalRequest order) {
////        try {
////            Payment payment = paypalService.createPayment(order.getPrice(), order.getCurrency(),
////                    order.getMethode(), order.getIntent(),
////                    order.getDescription(), CANCEL_URL, SUCCESS_URL);
////            for(Links link:payment.getLinks()) {
////                if(link.getRel().equals("approval_url")) {
////                    return "redirect:"+link.getHref();
////                }
////            }
////
////        } catch (PayPalRESTException e) {
////            e.printStackTrace();
////        }
////        return "redirect:/v1/paypal";
////    }
////
////
////    @GetMapping(value = CANCEL_URL)
////    public String cancelPay() {
////        return "cancel";
////    }
////
////    @GetMapping(value = SUCCESS_URL)
////    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
////        try {
////            Payment payment = paypalService.executePayment(paymentId, payerId);
////            System.out.println(payment.toJSON());
////            if (payment.getState().equals("approved")) {
////                return "success";
////            }
////        } catch (PayPalRESTException e) {
////            System.out.println(e.getMessage());
////        }
////        return "redirect:/v1/paypal";
////    }
//}
