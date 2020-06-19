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
                <li><a href="MyProduct">My Product</a></li>
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

<div class="container">
    <div class="row">
        <c:forEach items="${filterproduct}" var="filterproduct">
            <div class="col-sm-4">
                <div class="panel panel-primary">
                    <div class="panel-heading"> ${filterproduct.getTitle()} </div>
                    <div class="panel-body">
                        <p>Price : ${filterproduct.getPrice()}  </p>
                        <p>Author :  ${filterproduct.getAuthore()} </p>
                        <img src=${filterproduct.getImagepath()} style="width:50px;height:50px;"/>
                    </div>
                    <div class="panel-footer"> <button type="button" class="btn btn-primary btn-md"
                                                       onClick="location.href='Update_product${filterproduct.getId()}'">Update</button>
                     <button type="button" class="btn btn-primary btn-md"
                                                       onClick="location.href='delete_product${filterproduct.getId()}'">Delete</button></div>
                  <div><button type="button" class="btn btn-primary btn-md"
                            onClick="location.href='delete_img${filterproduct.getId()}'">Delete image</button>
                        <button type="button" class="btn btn-primary btn-md"
                                onClick="location.href='update_img${filterproduct.getId()}'">update image</button></div>


            </div>
            </div>
        </c:forEach>
    </div>
</div><br>

</body>
</html>