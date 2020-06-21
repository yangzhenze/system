package com.swsk.web.controller;
import com.paypal.http.HttpClient;
import com.paypal.http.HttpResponse;
import com.swsk.paypal.core.PayPalEnvironment;
import com.swsk.paypal.core.PayPalHttpClient;
import com.swsk.paypal.orders.*;
import com.swsk.paypal.payments.Capture;
import com.swsk.paypal.payments.CapturesGetRequest;
import com.swsk.paypal.payments.CapturesRefundRequest;
import com.swsk.paypal.payments.Refund;
import com.swsk.paypal.payments.RefundsGetRequest;
import com.swsk.web.ThreadTest;
import com.swsk.web.util.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author zzy
 * @Date 2020-03-10 17:27
 */
@Slf4j
@RestController
public class RenderController {


    PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
            "ATNuULnRo3dQONIyLtt9hirbIIgGYQnzcXOMrGlYQUKL3E_2uQ0JbvaetBOvKWpDqPZqB-ISi2ZIWWKQ",
            "EC8VdkJczzD2GdcFMGOyodfvL4TlgTpuDWOgtc5bO3tmJIQ4VF-YGwMmIhfNZ0itKaZjasKO7c0fO9YA");
    HttpClient client = new PayPalHttpClient(environment);

    @RequestMapping("/")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView("index");

        /*for (int i = 1; i <100 ; i++) {
            java.lang.Thread thread1 = new java.lang.Thread(new ThreadTest(i));
            thread1.start();
        }*/

        return modelAndView;
    }

    // 提交订单
    @RequestMapping("/pay")
    public String createOrder() throws IOException {
        Order createdOrder = createOrder(client);
        return Ret.getJson(createdOrder.id());
    }

    public OrderRequest buildRequestBody() throws IOException {
        return new OrderRequest()
                .checkoutPaymentIntent("CAPTURE")
                .purchaseUnits(new ArrayList<PurchaseUnitRequest>(){{
                    add(new PurchaseUnitRequest()
                            .referenceId("test_ref_id321123")
                            .amountWithBreakdown(new AmountWithBreakdown()
                                    .currencyCode("USD")
                                    .value("100.00")));
                }});
    }

    public Order createOrder(HttpClient client) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.requestBody(buildRequestBody());
        request.prefer("return=representation");
        HttpResponse<Order> response = client.execute(request);

        Order createdOrder = response.result();
        return createdOrder;
    }

    @RequestMapping("/order")
    public void testOrdersCaptureRequest(String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);

        HttpResponse<Order> response = client.execute(request);
        response.statusCode();
        response.result();
        Order order = response.result();
    }

    // 退款
    @RequestMapping("/order/refund")
    public void testRefundsGetRequest(String orderId) throws IOException {
        RefundsGetRequest request = new RefundsGetRequest(orderId);

        HttpResponse<Refund> response = client.execute(request);
        response.statusCode();
        response.result();
    }


    @RequestMapping("/refund")
    public void testCapturesRefundRequest(String orderId) throws IOException {
        CapturesRefundRequest request = new CapturesRefundRequest(orderId);

        HttpResponse<Refund> response = client.execute(request);
        response.statusCode();
        response.result();
    }

    public void testCapturesGetRequest() throws IOException {
        CapturesGetRequest request = new CapturesGetRequest("CAPTURE-ID");

        HttpResponse<Capture> response = client.execute(request);
        response.statusCode();
        response.result();
    }
}
