package isa.tim28.pharmacies.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import isa.tim28.pharmacies.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select o from Order o where o.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value="0")})
	public Order findOrderById(long id);
}
