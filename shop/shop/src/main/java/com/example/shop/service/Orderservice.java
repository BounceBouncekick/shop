package com.example.shop.service;


import com.example.shop.dto.OrderItemDTO;
import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import com.example.shop.entity.Product;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Orderservice {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void order(OrderItemDTO orderItemDTO,String name,String uuid){
        Optional<Product>optionalItem = Optional.ofNullable(productRepository.findByUuid(uuid));

        log.info(" optionalItem = {}" ,optionalItem);
        if (optionalItem.isPresent()) {
            Product product = optionalItem.get();
            log.info("Product found: {}", product);

            // 상품을 사용하여 주문 상품 생성
            OrderItem orderItem = OrderItem.createOrderItem(product, orderItemDTO);

            // 주문 생성 (주문 상품을 배열로 넘겨줌)
            Order order = Order.createOrder(product,orderItem);

            orderRepository.save(order);

            product.removeStock(orderItemDTO.getCount());
        } else {
            log.warn("Product not found for UUID: {}", uuid);
            // 상품을 찾을 수 없는 경우에 대한 처리
            // 예를 들어, 예외를 던지거나 로그를 남기는 등의 작업 수행
        }
    }

    @Transactional
    public void cancelOrder(String uuid) {
        //주문 엔티티 조회
        Order order = orderRepository.findByUuid(uuid);
        log.info("cancelOrder : {}",order);
        //주문 취소
        order.cancel();
    }



}
