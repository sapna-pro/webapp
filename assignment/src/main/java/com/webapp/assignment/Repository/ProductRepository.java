package com.webapp.assignment.Repository;
import com.webapp.assignment.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;


@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(@Param("id") int id) throws ResourceNotFoundException;

    @Modifying
    @Query("update Product p set p.ISBN = ?1, p.Title = ?2,p.Authore = ?3,p.price = ?4,p.publication_date = ?5,p.quantity = ?6 where p.id = ?7")
    void setProductInfoById(String ISBN, String Title, String Authore,Double price,String publication_date,int quantity,int id);

}
