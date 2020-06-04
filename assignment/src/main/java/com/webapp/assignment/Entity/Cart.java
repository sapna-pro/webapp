package com.webapp.assignment.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(cascade=CascadeType.REMOVE)
    private Product product;
    private int quantity;

    @OneToOne
    private User cartUser;

//    @ManyToOne(optional = false)
//    private Product products;
//
//    public Product getProducts() {
//        return products;
//    }
//
//    public void setProducts(Product products) {
//        this.products = products;
//    }

}
