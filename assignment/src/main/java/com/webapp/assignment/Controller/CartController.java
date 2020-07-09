package com.webapp.assignment.Controller;

import com.timgroup.statsd.StatsDClient;
import com.webapp.assignment.Entity.Cart;
import com.webapp.assignment.Entity.User;
import com.webapp.assignment.Repository.CartRepository;
import com.webapp.assignment.Service.CartService;
import com.webapp.assignment.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
//@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private StatsDClient statsDClient;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = "/cart")
    public String getCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            model.addAttribute("cart", cartService.findUserCart((User) session.getAttribute("logged_user")));
            return "cart";
        }catch (Exception e){
            return "product_all";
        }
    }


    @RequestMapping(value = "/cart{productId}")
    public String AddToCart(@PathVariable("productId") int productId, Model model, Cart cart, HttpServletRequest request) {
        //ModelAndView modelAndView = new ModelAndView("cart");
        try {
            logger.info("Product added to cart");
            HttpSession session = request.getSession();
            model.addAttribute("product", productService.getProduct(productId));
            model.addAttribute("cart", cartService.getAllProductFromCart());
            List<Cart> totalproduct = cartService.getAllProductFromCart();
            cart.setProduct(productService.getProduct(productId));
            cart.setQuantity(1);
            cart.setCartUser((User) session.getAttribute("logged_user"));
            long start = System.currentTimeMillis();
            cartService.AddToCart(cart);
            long time = System.currentTimeMillis() - start;
            statsDClient.recordExecutionTime("AddTocart",time);
            cartService.findUserCart((User) session.getAttribute("logged_user"));

            List<Cart> carts = cartService.findUserCart((User) session.getAttribute("logged_user"));
            if(carts == null){
                return "cart";
            }else {
                model.addAttribute("cart", carts);
                return "cart";
            }
        }catch (Exception e){
            return "product";
        }
    }
}
