package com.webapp.assignment.Controller;


import com.timgroup.statsd.StatsDClient;
import com.webapp.assignment.Entity.Product;
import com.webapp.assignment.Entity.User;
import com.webapp.assignment.Repository.UserRepository;
import com.webapp.assignment.Service.Userservice;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class MainController {

    private Userservice userservice;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String email_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Autowired
    private StatsDClient statsDClient;

    public boolean validate(final String password){
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
    public boolean validate_email(final String email){
        pattern = Pattern.compile(email_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }


    public MainController(){

    }

    @Autowired
    public MainController(Userservice userservice, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.userservice=userservice;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value={"/", "/Welcome"}, method = RequestMethod.GET)
    public String Welcome(Model model) {
        //statsDClient.incrementCounter("enpoint.homepage.http.get");
        return "Welcome";

    }

    @RequestMapping(value={"/index"}, method = RequestMethod.GET)
    public String Signup() {

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
//        return modelAndView;
        return "index";

    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public String Login() {

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//        return modelAndView;
        return "login";

    }

    @PostMapping(value = "/login")
    public String afterlogging(HttpServletRequest request, Model model,RedirectAttributes attributes){

        String email = request.getParameter("email");
        String pass = request.getParameter("psw");

        HttpSession session=request.getSession();
        session.setAttribute("email",email);
        User u = userservice.AuthenticateUser(email,pass);
        if(u != null){
            model.addAttribute("email", u.getEmailid());
            model.addAttribute("user",u);
            //model.addAttribute("user",u.getEmailid());
            session.setAttribute("logged_user",u);
            session.setAttribute("user",u);
            session.setAttribute("email", u.getEmailid());
            return "product_all";

        }else {
            attributes.addFlashAttribute("abc","User not exist");
            return "redirect:/login";
        }

    }

    @PostMapping(value = "/index")
    public String CreateNewUser(HttpServletRequest request, @Valid @ModelAttribute(value = "user") User user, Model model, RedirectAttributes attributes){

        User userExists = userservice.findUserByEmail(request.getParameter("email"));

        if(userExists == null) {

            HttpSession session = request.getSession();
            String email = request.getParameter("email");
            String pass = request.getParameter("psw");
            boolean psw_pattern = validate(pass);
            boolean email_pattern = validate_email(email);
            if(psw_pattern == false) {
                attributes.addFlashAttribute("pass","Add valid password");
                return "redirect:/index";
            }
            if(email_pattern == false){
                attributes.addFlashAttribute("email_validate","Add valid email_address");
                return "redirect:/index";
            }else {
                session.setAttribute("email", email);
                user.setEmailid(request.getParameter("email"));
                user.setPassword(bCryptPasswordEncoder.encode(request.getParameter("psw")));
                user.setFname(request.getParameter("fname"));
                user.setLname(request.getParameter("lname"));
                long start = System.currentTimeMillis();
                userservice.saveUser(user);
                long time = System.currentTimeMillis() - start;
                statsDClient.recordExecutionTime("Sign_up",time);
                session.setAttribute("user", user);
                session.setAttribute("logged_user",user);
                model.addAttribute("email", user.getEmailid());
                model.addAttribute("user", user);
                return "product_all";
            }
        }else{
            attributes.addFlashAttribute("exist","User Already Exists");
            return "redirect:/index";
        }

//        ModelAndView modelAndView = new ModelAndView();
//        try {
//            User userExists = userservice.findUserByEmail(request.getParameter("email"));
//            System.out.println(userExists + "userexist");
//            HttpSession session = request.getSession();
//
//            boolean result = userservice.userExist(request.getParameter("email"));
//
//            if (result == false) {
//                String email = request.getParameter("email");
//                String pass = request.getParameter("psw");
//                String fname = request.getParameter("fname");
//                String lname = request.getParameter("lname");
//                if(email.equals(" ") && pass.equals(" ") && fname.equals(" ") && lname.equals("")){
//                    request.setAttribute("null","all Fileld required");
//                }else {
//                    session.setAttribute("email", email);
//                    user.setEmailid(request.getParameter("email"));
//                    user.setPassword(bCryptPasswordEncoder.encode(request.getParameter("psw")));
//                    user.setFname(request.getParameter("fname"));
//                    user.setLname(request.getParameter("lname"));
//                    userservice.saveUser(user);
//                    session.setAttribute("user", user);
//                    modelAndView.addObject("successMessage", "User has been registered successfully");
//                    modelAndView.addObject("user1", new User());
//                    modelAndView.addObject("email", user.getEmailid());
//                    modelAndView.addObject("user", user);
//                    modelAndView.setViewName("product_all");
//                }
//            } else {
//                bindingResult.rejectValue("email", "error.user", "This email id is already registered.");
//                modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
//                modelAndView.setViewName("index");
//                return modelAndView;
//            }
//        }catch (Exception e){
//            request.setAttribute("not-found","User already exist");
//            modelAndView.setViewName("index");
//            return modelAndView;
//        }



//        if(userExists != null){
//            bindingResult.rejectValue("email", "error.user", "This email id is already registered.");
//            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
//            modelAndView.setViewName("index");
//            return modelAndView;
//        }
//
//        if(userExists == null){
//
//            String email = request.getParameter("email");
//            String pass = request.getParameter("psw");
//            session.setAttribute("email",email);
//
//            user.setEmailid(request.getParameter("email"));
//            user.setPassword(bCryptPasswordEncoder.encode(request.getParameter("psw")));
//            user.setFname(request.getParameter("fname"));
//            user.setLname(request.getParameter("lname"));
//            userservice.saveUser(user);
//            session.setAttribute("user",user);
//            modelAndView.addObject("successMessage", "User has been registered successfully");
//            modelAndView.addObject("user1", new User());
//            modelAndView.addObject("email", user.getEmailid());
//            modelAndView.addObject("user",user);
//            modelAndView.setViewName("update");
//        }

    }

    @RequestMapping(value = {"/update"},method = RequestMethod.GET)
    public ModelAndView update(){
        ModelAndView modelAndView = new ModelAndView();
        //System.out.println("in update");
        return modelAndView;
    }

    @PostMapping(value = {"/update"})
   public ModelAndView updateUser(HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView("update");
        String email = "";
        HttpSession session=request.getSession();
        if(session!=null) {
             email=(String)session.getAttribute("email");
            System.out.println(email +" "+"session");
        }else{
            modelAndView.setViewName("login");
        }
            User update_user = userservice.findUserByEmail(email);

        System.out.println(update_user+"update user");

        if(update_user == null) {
            modelAndView.setViewName("update");
        }
        else{
            update_user.setFname(request.getParameter("fname"));
            update_user.setLname(request.getParameter("lname"));
            update_user.setPassword(request.getParameter("psw"));
            long start = System.currentTimeMillis();
            userRepository.save(update_user);
            long time = System.currentTimeMillis() - start;
            statsDClient.recordExecutionTime("update_user_info",time);
        }
        return modelAndView;
   }

   @PostMapping(value = {"/btn_login"})
   public String btn_login(){
//        ModelAndView modelAndView1 = new ModelAndView();
//        modelAndView1.setViewName("/login");
        return "login";
   }
    @PostMapping(value = {"/btn_signup"})
    public String btn_signup(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
        return "index";
    }

    @PostMapping(value = {"/logout"})
    public ModelAndView logout(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session=request.getSession();
        session.removeAttribute("email");
        session.invalidate();

        return modelAndView;
    }

    @PostMapping(value = {"/re_update"})
    public ModelAndView re_update(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();

        String email="";
        HttpSession session=request.getSession();
        if(session!=null) {
            email=(String)session.getAttribute("email");
            System.out.println(email +" "+"session");
        }else{
            modelAndView.setViewName("login");
        }
        User update_user = userservice.findUserByEmail(email);

        String username="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
            System.out.println(username+"principle");
        } else {
              username = principal.toString();
        }
        User user = userRepository.findByEmailid(email);
        modelAndView.addObject("email",email);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("update_info");

        return modelAndView;
    }

    @PostMapping(value = "/update_info")
    public ModelAndView update_info(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();

        String email="";
        HttpSession session=request.getSession();
        if(session!=null) {
            email=(String)session.getAttribute("email");
            System.out.println(email +" "+"session");
        }else{
            modelAndView.setViewName("login");
        }
        User update_user = userservice.findUserByEmail(email);
        if(update_user == null) {
            modelAndView.setViewName("update");
        }
        else{
            update_user.setFname(request.getParameter("fname"));
            update_user.setLname(request.getParameter("lname"));
            update_user.setPassword(bCryptPasswordEncoder.encode(request.getParameter("psw")));
            long start = System.currentTimeMillis();
            userRepository.save(update_user);
            long time = System.currentTimeMillis() - start;
            statsDClient.recordExecutionTime("update_user_info",time);
            modelAndView.setViewName("login");
        }


        return modelAndView;
    }
}
