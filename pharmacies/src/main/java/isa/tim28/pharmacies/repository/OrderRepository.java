package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
