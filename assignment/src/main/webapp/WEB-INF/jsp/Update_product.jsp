<%@ page import="com.webapp.assignment.Entity.Product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/sql" prefix="sql" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
      xmlns:th="http://www.thymeleaf.org">

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src=https://code.jquery.com/jquery-1.12.4.js></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $( function() {
            $( "#datepicker" ).datepicker();
        } );
    </script>
    <style type="text/css">
        .error {
            color: red;
        }
    </style>
</head>
<body>
<div class="container text-center">
    <h1></h1>
    <p></p>
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
                <li><a href="MyProduct">Stores</a></li>
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



<form action="/Update_product" method="post" name="frm" item="${p}">

    <table cellpadding="10" cellspacing="10" border="0">
        <tr>

            <td><input type="hidden" name="id"  value=${p.getId()}></td>
        </tr>

        <tr>
            <td>ISBN</td>
            <td><input type="text" name="isbn" path="ISBN" value=${p.getISBN()}></td>
            <td>${not_empty1}</td>
        </tr>

        <tr>
            <td>Title</td>
            <td><input type="text" name="title" path="Title" value=${p.getTitle()}></td>
            <td>${not_empty2}</td>
        </tr>

        <tr>
            <td>Author</td>
            <td><input type="text" name="author" path="Authore" value=${p.getAuthore()}></td>
            <td>${not_empty3}</td>
        </tr>

        <tr>
            <td>Publication Date</td>
            <td><input type="text" id="datepicker" name="p_date" path="publication_date" value=${p.getPublication_date()}></td>

        </tr>

        <tr>
            <td>Quantity</td>
            <td><input type="number" name="quantity" path="quantity" value=${p.getQuantity()}></td>
            <td>${not_empty5}</td>
            <td>${quantity}</td>

        </tr>

        <tr>
            <td>Price</td>
            <td><input type="number" path="price" name="price" value=${p.getPrice()}></td>
            <td>${not_empty6}</td>
            <td>${price}</td>

        </tr>

        <tr>
            <td><input type="submit" name="action" value="Update"></td>

        </tr>

    </table>

</form>
</body>
</html>