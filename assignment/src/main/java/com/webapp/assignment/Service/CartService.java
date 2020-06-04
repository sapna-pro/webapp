package com.webapp.assignment.Service;

import com.webapp.assignment.Entity.Cart;
import com.webapp.assignment.Entity.User;
import com.webapp.assignment.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public void AddToCart(Cart cart){

        cartRepository.save(cart);

    }
    public List<Cart> getAllProductFromCart(){

        return cartRepository.findAll();
    }
    public List<Cart> findUserCart(User user){

        List<Cart> allProduct = cartRepository.findAll();
        List<Cart> filteredCart = new ArrayList<>();

        try {
            for (Cart cart : allProduct) {
                if (cart.getCartUser().getEmailid().equals(user.getEmailid())) {
                    filteredCart.add(cart);
                }
            }
        }catch (Exception e){
            return null;
        }
       return filteredCart;
    }
}
