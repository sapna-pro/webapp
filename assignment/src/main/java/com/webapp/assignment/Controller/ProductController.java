package com.webapp.assignment.Controller;

import com.webapp.assignment.Entity.Cart;
import com.webapp.assignment.Entity.Product;
import com.webapp.assignment.Entity.User;
import com.webapp.assignment.Repository.CartRepository;
import com.webapp.assignment.Service.CartService;
import com.webapp.assignment.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
//@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/AddProduct")
    public String AddPage(Model model){
        model.addAttribute("product",new Product());
        return "AddProduct";
    }

    @PostMapping("/AddProduct")
    public String AddDetail(HttpServletRequest request, @Valid @ModelAttribute(value = "product") Product product, Model model,RedirectAttributes attributes){

        HttpSession session = request.getSession();
        int quantity = (Integer.parseInt(request.getParameter("quantity")));
        double price = (Double.parseDouble(request.getParameter("price")));

        if(request.getParameter("isbn") == null || request.getParameter("isbn").equals("")){
            attributes.addFlashAttribute("not_empty1","Fileld should not be emplty");
            return "redirect:/AddProduct";
        }
        if(request.getParameter("title") == null || request.getParameter("title").equals("")){
            attributes.addFlashAttribute("not_empty2","Fileld should not be emplty");
            return "redirect:/AddProduct";
        }
        if(request.getParameter("author") == null || request.getParameter("author").equals("")){
            attributes.addFlashAttribute("not_empty3","Fileld should not be emplty");
            return "redirect:/AddProduct";
        }
//        if(request.getParameter("P_date") == null || request.getParameter("p_date").equals("")){
//            attributes.addFlashAttribute("not_empty4","Fileld should not be emplty");
//            return "redirect:/AddProduct";
//        }
        if(request.getParameter("quantity") == null || request.getParameter("quantity").equals("")){
            attributes.addFlashAttribute("not_empty5","Fileld should not be emplty");
            return "redirect:/AddProduct";
        }
        if(request.getParameter("price") == null || request.getParameter("price").equals("")){
            attributes.addFlashAttribute("not_empty6","Fileld should not be emplty");
            return "redirect:/AddProduct";
        }
        if(price <= 0.0 || price >= 9999.9){
            attributes.addFlashAttribute("price","minimum of 0.01 and maximum of 9999.99");
            return "redirect:/AddProduct";
        }
        if(quantity <= 0 || quantity >= 999){
            attributes.addFlashAttribute("quantity","minimum of 0 and maximum of 999");
            return "redirect:/AddProduct";
        }

        else {
            try {
                String author = request.getParameter("author");
                product.setISBN(request.getParameter("isbn"));
                product.setTitle(request.getParameter("title"));
                product.setAuthore(request.getParameter("author"));
                product.setPublication_date(request.getParameter("p_date"));
                product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
                product.setPrice(Double.parseDouble(request.getParameter("price")));
                product.setSeller((User) session.getAttribute("logged_user"));
                productService.AddDetail(product);
                return "product_all";
            } catch (Exception e) {
                return "AddProduct";
            }
        }
    }

    @GetMapping("/product_all")
    public String getAllProducts(Model model,HttpServletRequest request) {
        model.addAttribute("products",productService.getAllProducts());
        HttpSession session=request.getSession();
        return "product_all";
    }

    @GetMapping("/MyProduct")
    public String MyProduct(Model model,HttpServletRequest request) {
        HttpSession session=request.getSession();
        List<Product> abc = productService.sortBySeller((User) session.getAttribute("logged_user"));
        model.addAttribute("filterproduct",productService.sortBySeller((User) session.getAttribute("logged_user")));
        System.out.println(abc + "filterlist");
        return "MyProduct";
    }

    @GetMapping("/delete_product{id}")
    public String DeleteProduct(@PathVariable("id") int id,Model model){
        productService.DeleteProduct(id);
        return "redirect:/MyProduct";
    }

    @GetMapping("/Update_product{id}")
    public String getUpdateProduct(@PathVariable("id") int id,Model model){
        model.addAttribute("p",productService.getProduct(id));
        return "Update_product";
    }
    @PostMapping("/Update_product")
    public String UpdateProduct(Model model,HttpServletRequest request,RedirectAttributes attributes){

            try {
                String isbn = request.getParameter("isbn");
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String pdate = request.getParameter("p_date");
                int quantity = (Integer.parseInt(request.getParameter("quantity")));
                Double price = (Double.parseDouble(request.getParameter("price")));
                int id = (Integer.parseInt(request.getParameter("id")));
                productService.UpdateProduct(isbn, title, author, price, pdate, quantity, id);
                return "product_all";
            }catch (Exception e){
                return "redirect:/Update_product";
            }

    }


    @GetMapping("/{productId}")
    public String getProductById(@PathVariable("productId") int productId,Model model,HttpServletRequest request) {

        model.addAttribute("product",productService.getProduct(productId));
        model.addAttribute("cart",new Cart());
        Product p = productService.getProduct(productId);
        User seller = p.getSeller();
        model.addAttribute("seller",seller);
        HttpSession session = request.getSession();
        model.addAttribute("current_user",session.getAttribute("logged_user"));
        return "product";
    }


    private int exists(int id, List<Cart> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
