package org.soulaimane.orderservices.repository;

import org.soulaimane.orderservices.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
