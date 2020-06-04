package com.webapp.assignment.Repository;

import com.webapp.assignment.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface CartRepository extends JpaRepository<Cart,Integer> {

}
