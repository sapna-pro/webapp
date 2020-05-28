<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
      xmlns:th="http://www.thymeleaf.org">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="java.security.Principal" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.webapp.assignment.Entity.User" %>
<%@ page import="com.webapp.assignment.Service.Userservice" %>
<%@ page import="com.webapp.assignment.Repository.UserRepository" %>
<%@ page import="org.springframework.web.servlet.ModelAndView" %>
<%@ page import="java.io.IOException" %>

<head>
    <title>Assignment</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

</head>
<body>
<%!
    @Autowired
    private Userservice userservice;
    private UserRepository userRepository;
%>
<%

if(session.getAttribute("email")==null){
    response.sendRedirect("login");
}

%>
<%!
    public void getUser(HttpServletRequest request) {
        HttpSession session=request.getSession();

        String email = "";
        if(session!=null) {
            email=(String)session.getAttribute("email");
            System.out.println(email +" "+"session");
        }
       // User update_user = userservice.findUserByEmail(email);
        User user = userRepository.findByEmailid(email);
    }
%>

<% User user = (User)session.getAttribute("user");%>


  <h1>Welcome &nbsp;<%=request.getAttribute("email") %></h1>
    <h3>Want to Update Information:</h3>

    <form action="re_update" method="post">
    <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#modalPoll-1">Update</button></form>


 <h3>Want to view information</h3>
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalPoll-2">View</button>
<!-- Modal: modalPoll -->
<div class="modal fade right" id="modalPoll-2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog modal-full-height modal-right modal-notify modal-info" role="document">
        <div class="modal-content">
            <!--Header-->
            <div class="modal-header">
                <p class="heading lead">Feedback request
                </p>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">×</span>
                </button>
            </div>

            <form action="updatev" style="border:1px solid #ccc" method="post">
                <div class="container">
                    <h1 align="justify">Update</h1>

                    <hr>
                    <table cellspacing="10">

                        <tr><td> <label><b>First Name</b></label></td>
                            <td><input type="text"  name="fname" required value=<%=user.getFname()%>></td></tr>

                        <tr><td><label><b>Last Name</b></label></td>
                            <td> <input type="text"  name="lname" required value=<%=user.getLname()%>></td></tr>

                        <tr><td><label><b>Password</b></label></td>
                            <td><input type="password" name="psw" required value=<%=user.getPassword()%>></td></tr>
                    </table>
                </div>

                <!--Footer-->
                <div class="modal-footer justify-content-center">

                    <i class="fa fa-paper-plane ml-1"></i>
                    <a type="button" class="btn btn-outline-primary waves-effect" data-dismiss="modal">Cancel</a>
                </div>
            </form>


        </div>
    </div>
</div>
<!-- Modal: modalPoll -->
<br>
<form action="logout" method="post">
    <button type="submit" name="action" value="logout">Logout</button>
</form>
</body>
</html>