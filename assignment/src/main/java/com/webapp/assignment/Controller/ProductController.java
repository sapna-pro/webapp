package com.webapp.assignment.Controller;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.webapp.assignment.Entity.Cart;
import com.webapp.assignment.Entity.Product;
import com.webapp.assignment.Entity.Product_image;
import com.webapp.assignment.Entity.User;
import com.webapp.assignment.Repository.CartRepository;
import com.webapp.assignment.Service.AmazonClient;
import com.webapp.assignment.Service.CartService;
import com.webapp.assignment.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Controller
//@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private AmazonClient client;
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
    public String AddDetail(HttpServletRequest request, @Valid @ModelAttribute(value = "product") Product product, Model model,RedirectAttributes attributes,
                            @RequestParam("image") MultipartFile[] image) throws IOException {

        HttpSession session = request.getSession();
        int quantity = (Integer.parseInt(request.getParameter("quantity")));
        double price = (Double.parseDouble(request.getParameter("price")));
       // Product_image product_image = new Product_image();
        List<MultipartFile> file1 = Arrays.asList(image);
        List<String> urllist = new ArrayList<>();

       // System.out.println(file1+"###################list multiple file");

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
                for(MultipartFile file : image){
                    String url =  client.uploadFile(file);
                    urllist.add(url);
                }
                product.setImages(urllist);
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
        //System.out.println(abc + "filterlist");
        return "MyProduct";
    }

    @GetMapping("/delete_product{id}")
    public String DeleteProduct(@PathVariable("id") int id,Model model){

        try{
            Product product = productService.getProduct(id);
            List<String> imageurl = product.getImages();
            for (String abc: imageurl) {
                client.deleteFileFromS3Bucket(abc);
            }
            productService.DeleteProduct(id);

            return "redirect:/MyProduct";
        }catch (Exception e){
            return "redirect:/product_all";
        }


    }

    @GetMapping("/delete_img{id}")
    public String DeleteImage(@PathVariable("id") int id,Model model,HttpServletRequest request){
        Product product = productService.getProduct(id);
        model.addAttribute("imageproduct",product);

//        String a = " ";
//        List<String> blanklist = new ArrayList<>();
//        List<String> imageurl = product.getImages();
//        for (String abc: imageurl) {
//            client.deleteFileFromS3Bucket(abc);
//            blanklist.add(" ");
//        }
//        product.setImages(blanklist);
////        String abc = client.deleteFileFromS3Bucket(fileurl);
////        product.setImagepath(a);
//        productService.AddDetail(product);
        return "delete_image";
    }

    @PostMapping("/selectedimage")
    public String selectedimage(@RequestParam("s1") String imageurl,HttpServletRequest request){

            try {
                Product product = productService.getProduct(Integer.parseInt(request.getParameter("id")));
                List<String> url = product.getImages();
                for (String abc : url) {
                    if (abc.equals(imageurl)) {
                        url.remove(abc);
                    }
                }
                product.setImages(url);
                productService.AddDetail(product);
                client.deleteFileFromS3Bucket(imageurl);
                return "product_all";
            }catch (Exception e){
                return "product_all";
            }

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

    @GetMapping("/update_image{id}")
    public String Update_Image(@PathVariable("id") int id,Model model){

        model.addAttribute("p",productService.getProduct(id));
//        Product product = productService.getProduct(id);
//        List<MultipartFile> file1 = Arrays.asList(image);
//        List<String> urllist = new ArrayList<>();
//        for(MultipartFile file : image){
//            String url =  client.uploadFile(file);
//            urllist.add(url);
//        }
//        product.setImages(urllist);
//        productService.AddDetail(product);

        return "update_image";
    }

    @PostMapping("/update_image")
    public String Update_img(Model model,HttpServletRequest request,
                             @RequestParam("image") MultipartFile[] image)throws IOException{

        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getProduct(id);
       // List<MultipartFile> file1 = Arrays.asList(image);
        List<String> urllist = product.getImages();
        for(MultipartFile file : image){
            String url =  client.uploadFile(file);
            urllist.add(url);
        }
        product.setImages(urllist);
        productService.AddDetail(product);
        return "redirect:/MyProduct";
    }

}
