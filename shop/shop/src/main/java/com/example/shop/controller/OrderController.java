package com.example.shop.controller;

import com.example.shop.dto.OrderItemDTO;
import com.example.shop.dto.ProductDTO;
import com.example.shop.entity.Order;
import com.example.shop.entity.Product;
import com.example.shop.repository.OrderSearch;
import com.example.shop.service.Orderservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("shop-service")
public class OrderController {

    private final Orderservice orderservice;

    @PostMapping("/orders/{uuid}/order")
    public ResponseEntity<String> order(@ModelAttribute OrderItemDTO orderItemDTO,
                                        @RequestParam("name") String name, @PathVariable("uuid") String uuid)throws IOException {


        log.info("Controller_orderItemDTO : {}",orderItemDTO);
        log.info("Controller_name : {}",name);
        log.info("Controller_uuid : {}",uuid);

        orderservice.order(orderItemDTO,name,uuid);
        return ResponseEntity.ok("주문되었습니다.");

    }

    @PostMapping("/orders/{uuid}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("uuid") String uuid) {
        orderservice.cancelOrder(uuid);
        return ResponseEntity.ok("취소되었습니다.");
    }


}
