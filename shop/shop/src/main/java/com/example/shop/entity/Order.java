package com.example.shop.entity;

import com.example.shop.em.DeliveryStatus;
import com.example.shop.em.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @CreationTimestamp
   private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
   private OrderStatus status; //주문상태

   @JsonIgnore
   @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
   private List<OrderItem> orderItems = new ArrayList<>();

   @OneToOne
   @JoinColumn(name = "delivery_id")
   private Delivery delivery;

    private String uuid = UUID.randomUUID().toString();


    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public static Order createOrder(Product productName,OrderItem... orderItems){
            Order order = new Order();
//            order.setDelivery(delivery);
            for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
            }
            order.setStatus(OrderStatus.OREDER);
            order.setOrderDate(LocalDateTime.now());


            return order;
    }

    public void cancel() {
//        if (delivery.getStatus() == DeliveryStatus.COMP) {
//            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
//        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}

