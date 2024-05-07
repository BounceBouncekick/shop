package com.example.shop.entity;

import com.example.shop.em.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Enumerated(EnumType.STRING) //ORDINAL은 숫자로 들어간다. 근데 여기서 상태를 추가해버리면
    private DeliveryStatus status;
}
