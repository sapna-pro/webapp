<%@ page import="com.webapp.assignment.Entity.Product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
      xmlns:th="http://www.thymeleaf.org">
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>

<html lang="en">
<head>
    <title>Home</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container text-center">
    <h1>Welcome to Book Store</h1>
    <p>Browse all our awesome Books</p>
</div>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Logo</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">

                <li class="active"><a href="product_all">Products</a></li>
                <li><a href="AddProduct">Add Product</a></li>
                <li><a href="MyProduct">MyProduct</a></li>
                <li><a href="#">Contact</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="update"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
                <li><a href="logout"><span class="glyphicon glyphicon-user"></span>Logout</a></li>
                <li><a href="cart"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="page-header">
    <h1 id="tables">Cart</h1>
</div>
<div class="bs-component">
    <table class="table table-hover">
        <thead>
        <tr>
            <th></th>
            <th>Title</th>
            <th>Price</th>
            <th>Quantity</th>
            <th></th>
            <th>Sub Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cart}" var="cart">

        <tr>
            <td align="center"></td>
            <td>${cart.getProduct().getTitle()}</td>
            <td>${cart.getProduct().getPrice()}</td><span>${quantity}</span>
            <form method="post" action="cart_qun" >
                <td><input type="text" name="quantity" value=${cart.quantity}></td>
                <td><button type="submit">ADD</button></td>
            <c:set var="xyz" value="${cart.getQuantity() * request.getParameter(quantity)}"></c:set>
            <td>${cart.getProduct().getPrice() * cart.getQuantity()}</td>
            <c:set var="total"
                   value="${total + cart.getProduct().getPrice() * cart.getQuantity()}"></c:set>
        </tr>
        </c:forEach>

        <tr>

            <td colspan="6" align="right">Sum</td>
            <td>${total}</td>
        </form>
        </tr>
    </table>
    </div>
</body>
</html>
