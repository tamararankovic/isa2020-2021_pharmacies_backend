package isa.tim28.pharmacies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isa.tim28.pharmacies.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findOneByEmail(String email);
}
