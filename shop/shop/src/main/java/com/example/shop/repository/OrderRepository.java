package com.example.shop.repository;

import com.example.shop.entity.Order;
import com.example.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findByUuid(String Uuid);
}
