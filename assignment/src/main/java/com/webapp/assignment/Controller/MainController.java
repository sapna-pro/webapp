package com.webapp.assignment.Controller;


import com.webapp.assignment.Entity.User;
import com.webapp.assignment.Repository.UserRepository;
import com.webapp.assignment.Service.Userservice;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
public class MainController {

    private Userservice userservice;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MainController(){

    }

    @Autowired
    public MainController(Userservice userservice, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.userservice=userservice;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value={"/", "/Welcome"}, method = RequestMethod.GET)
    public ModelAndView Welcome() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Welcome");
        return modelAndView;

    }

    @RequestMapping(value={"/index"}, method = RequestMethod.GET)
    public ModelAndView Signup() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;

    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView Login() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;

    }

    @PostMapping(value = "/login")
    public ModelAndView afterlogging(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        System.out.println(request.getParameter("email"));

        String email = request.getParameter("email");
        String pass = request.getParameter("psw");
        System.out.println(pass);

        HttpSession session=request.getSession();
        session.setAttribute("email",email);
        User u = userservice.AuthenticateUser(email,pass);
        if(u != null){
            modelAndView.addObject("email", u.getEmailid());
            modelAndView.addObject("user",u);
            modelAndView.setViewName("update");
            session.setAttribute("user",u);
        }
        if(u == null)
        {
            modelAndView.addObject("not-found","User not exist");
            System.out.println("usernotfound");
        }

        return modelAndView;
    }

    @PostMapping(value = "/index")
    public ModelAndView CreateNewUser(HttpServletRequest request, @Valid User user, BindingResult bindingResult){

        ModelAndView modelAndView = new ModelAndView();
        try {
            User userExists = userservice.findUserByEmail(request.getParameter("email"));
            System.out.println(userExists + "userexist");
            HttpSession session = request.getSession();

            boolean result = userservice.userExist(request.getParameter("email"));

            if (result == false) {
                String email = request.getParameter("email");
                String pass = request.getParameter("psw");
                String fname = request.getParameter("fname");
                String lname = request.getParameter("lname");
                if(email.equals(" ") && pass.equals(" ") && fname.equals(" ") && lname.equals("")){
                    request.setAttribute("null","all Fileld required");
                }else {
                    session.setAttribute("email", email);
                    user.setEmailid(request.getParameter("email"));
                    user.setPassword(bCryptPasswordEncoder.encode(request.getParameter("psw")));
                    user.setFname(request.getParameter("fname"));
                    user.setLname(request.getParameter("lname"));
                    userservice.saveUser(user);
                    session.setAttribute("user", user);
                    modelAndView.addObject("successMessage", "User has been registered successfully");
                    modelAndView.addObject("user1", new User());
                    modelAndView.addObject("email", user.getEmailid());
                    modelAndView.addObject("user", user);
                    modelAndView.setViewName("update");
                }
            } else {
                bindingResult.rejectValue("email", "error.user", "This email id is already registered.");
                modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
                modelAndView.setViewName("index");
                return modelAndView;
            }
        }catch (Exception e){
            request.setAttribute("not-found","User already exist");
            modelAndView.setViewName("index");
            return modelAndView;
        }
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
        return modelAndView;

    }

    @RequestMapping(value = {"/update"},method = RequestMethod.GET)
    public ModelAndView update(){
        ModelAndView modelAndView = new ModelAndView();
        //System.out.println("in update");
        return modelAndView;
    }

    @PostMapping(value = {"/update"})
   public ModelAndView updateUser(HttpServletRequest request,HttpServletResponse response){

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
            userRepository.save(update_user);
        }
        return modelAndView;
   }

   @PostMapping(value = {"/btn_login"})
   public ModelAndView btn_login(){
        ModelAndView modelAndView1 = new ModelAndView();
        modelAndView1.setViewName("/login");
        return modelAndView1;
   }
    @PostMapping(value = {"/btn_signup"})
    public ModelAndView btn_signup(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
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
            userRepository.save(update_user);
            modelAndView.setViewName("login");
        }


        return modelAndView;
    }
}
