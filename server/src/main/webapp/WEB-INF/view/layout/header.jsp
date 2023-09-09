<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.14.0/Sortable.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/js/select2.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portfolio</title>
    <style>
        body {
            padding-top: 88px;
        }
    </style>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>    
    
    <header class="navbar navbar-custom navbar-expand-lg navbar-light fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/mainpage">포트폴리오</a>
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto"> 
                    <li class="nav-item me-4">
                        <a class="nav-link" href="/resume">Resume</a>
                    </li>
                    <li class="nav-item me-4">
                        <a class="nav-link" href="/blog">TechBlog</a>
                    </li>
                    <li class="nav-item me-4">
                        <a class="nav-link" href="/project">Project</a>
                    </li>
                    <li class="nav-item me-5">
                        <a class="nav-link" href="/skills">Skills</a>
                    </li>
                </ul>

                    <button type="button" class="btn btn-primary mb-2 ms-2 edit-button" style="display:none" onclick="toggleEditMode()">편집모드</button>
                    <button type="button" class="btn btn-secondary btn-sm mb-2 ms-2 logout-button" style="display:none" onclick="logout()">로그아웃</button>

            </div>
        </div>
    </header>        

<script src="/js/header.js"></script>