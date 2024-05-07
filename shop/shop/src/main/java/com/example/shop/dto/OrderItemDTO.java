package com.example.shop.dto;

import com.example.shop.entity.OrderItem;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class OrderItemDTO {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    private int orderPrice; //주문가격

    private int count; //주문 수량

    @Builder
    public  OrderItemDTO(int orderPrice, int count) {
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderItemDTO toEntity(OrderItem orderItem){
        return OrderItemDTO.builder()
                .orderPrice(orderItem.getOrderPrice())
                .count(orderItem.getCount())
                .build();
    }


}
