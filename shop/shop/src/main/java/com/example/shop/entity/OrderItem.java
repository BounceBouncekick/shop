package com.example.shop.entity;

import com.example.shop.dto.OrderItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import static jakarta.persistence.FetchType.LAZY;


@Slf4j
@Entity
@Getter
@NoArgsConstructor
@ToString
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id") // Product와의 관계를 설정하는데 사용할 외부 키
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격
    private int count; //주문 수량

    @Builder
    public OrderItem(int orderPrice, int count,Product product) {
        this.orderPrice = orderPrice;
        this.product = product;
        this.count = count;
    }

    public OrderItem(Product product) {
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public static OrderItem toDTO(OrderItemDTO orderItemDTO){
        return OrderItem.builder()
                .orderPrice(orderItemDTO.getOrderPrice())
                .count(orderItemDTO.getCount())
                .build();
    }


    public static OrderItem createOrderItem(Product product, OrderItemDTO orderItemDTO) {
        log.info("createOrderItem : {}", product);
        log.info("createOrderItem2 : {}", orderItemDTO);
        log.info("ItemCount : {}",orderItemDTO.getCount());
        return OrderItem.builder()
                .product(product)
                .orderPrice(orderItemDTO.getOrderPrice())
                .count(orderItemDTO.getCount())
                .build();
    }

    public void cancel(){
        getProduct().addStock(count);
    }

    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }
}
