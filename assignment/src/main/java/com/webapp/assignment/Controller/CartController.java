package com.webapp.assignment.Controller;

import com.timgroup.statsd.StatsDClient;
import com.webapp.assignment.Entity.Cart;
import com.webapp.assignment.Entity.Product;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            Product p = productService.getProduct(productId);
            int qun = p.getQuantity()-1;
            p.setQuantity(qun);
            productService.AddDetail(p);
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

    @RequestMapping(value = "cart_qun" ,method = RequestMethod.POST)
    public String cart_quantity(HttpServletRequest request, Model model, RedirectAttributes attributes){

        HttpSession session = request.getSession();
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product p = null;

        List<Cart> carts = cartService.findUserCart((User) session.getAttribute("logged_user"));
        for(Cart cart : carts){
            p = cart.getProduct();
//            if(quantity>p.getQuantity()){
//                System.out.println("in first if");
//                break;
//            }
            if(cart.getQuantity()>quantity){
                int xyz = cart.getQuantity()-quantity;
                int product_quantity = p.getQuantity()+xyz;
                cart.setQuantity(quantity);
                cartService.AddToCart(cart);
                p.setQuantity(product_quantity);
                productService.AddDetail(p);
            }

            else {

                if(quantity>p.getQuantity()){
                    model.addAttribute("quantity","quantity more than product quantity");
                break;
            }else {

                    int xyz = quantity - cart.getQuantity();
                    int abc = p.getQuantity() - xyz;
                    cart.setQuantity(quantity);
                    p.setQuantity(abc);
                    productService.AddDetail(p);
                    System.out.println(p.getQuantity() + "product quantityyyyyyyyyy");
                    System.out.println(cart.getQuantity() + "quantityyyyyyyyyy");
                    cartService.AddToCart(cart);
                }
            }
        }

        model.addAttribute("cart", carts);
        return "cart";
    }

}
