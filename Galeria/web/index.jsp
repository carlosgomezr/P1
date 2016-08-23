<%-- 
    Document   : index
    Created on : 21/08/2016, 10:42:00 PM
    Author     : Carlos Gomez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Galeria</title>
        <link rel="stylesheet" href="piccolo/piccolo/css/bootstrap.css">
        <link rel="stylesheet" href="piccolo/piccolo/css/bootstrap-responsive.css">
        <link rel="stylesheet" href="piccolo/piccolo/css/prettyPhoto.css" />
        <link rel="stylesheet" href="piccolo/piccolo/css/flexslider.css" />
        <link rel="stylesheet" href="piccolo/piccolo/css/custom-styles.css">
        
        <link rel="stylesheet" href="paralelismo/assets/css/main.css">
        <noscript><link rel="stylesheet" href="paralelismo/assets/css/noscript.css" /></noscript>
    </head>
    <body class="home">
         <!-- Color Bars (above header)-->
	<div class="color-bar-1"></div>
        <div class="color-bar-2 color-bg"></div>
        
        <h1>Galeria</h1>
        
        
        <div class="row header"><!-- Begin Header -->
            <!-- Logo
            ================================================== -->
            <div class="span5 logo">
            	<a href="index.htm"><img src="img/piccolo-logo.png" alt="" /></a>
                <h5>Mis Imagenes</h5>
            </div>
        </div>

        
         <!-- Main Navigation
        ================================================== -->
        <div class="span7 navigation">
            <div class="navbar hidden-phone">
            
            <ul class="nav">
            <li class="dropdown active">
                <a class="dropdown-toggle" data-toggle="dropdown" href="index.htm">Home <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href='#'>Full Page</a></li>
                    <li><a href='#'>Gallery Only</a></li>
                    <li><a href='#'>Slider Only</a></li>
                </ul>
            </li>
            
                
            <li><a href="features.htm">Features</a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="page-full-width.htm">Pages <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="page-full-width.htm">Full Width2</a></li>
                    <li><a href="page-right-sidebar.htm">Right Sidebar2</a></li>
                    <li><a href="page-left-sidebar.htm">Left Sidebar2</a></li>
                    <li><a href="page-double-sidebar.htm">Double Sidebar2</a></li>
                </ul>
            </li>
            </ul>   
            </div>
        </div>
    </body>
</html>
