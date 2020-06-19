package com.webapp.assignment.Service;

import com.webapp.assignment.Entity.Cart;
import com.webapp.assignment.Entity.Product;
import com.webapp.assignment.Entity.User;
import com.webapp.assignment.Repository.CartRepository;
import com.webapp.assignment.Repository.ProductRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.*;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

//    @Autowired
//    private CartRepository cartRepository;

    public void AddDetail(Product product){

        productRepository.save(product);
    }

    public List<Product> getAllProducts() {


        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));

    }

    public Product getProduct(int id) throws ResourceNotFoundException {
        return productRepository.findById(id);
    }

    public void DeleteProduct(int productId){

        productRepository.deleteById(productId);
        //cartRepository.deleteById(productId);

    }

    public void UpdateProduct(String ISBN, String Title, String Authore,Double price,String publication_date,int quantity,int id){

        productRepository.setProductInfoById(ISBN,Title,Authore,price,publication_date,quantity,id);

    }
    public List<Product> sortBySeller(User seller){
        List<Product> fulllist = productRepository.findAll();
        List<Product> filtered_list = new ArrayList<>();

        for(Product product : fulllist){
            if(product.getSeller().getEmailid().equals(seller.getEmailid())){
                filtered_list.add(product);
            }
        }
        return filtered_list;
    }

    private static Comparator<Product> COMPARATOR = new Comparator<Product>()
    {

        public int compare(Product o1, Product o2)
        {
            return (int) (o1.getPrice() - o2.getPrice());
        }
    };

}
