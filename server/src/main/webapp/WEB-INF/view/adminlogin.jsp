<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    
    <%@ include file="layout/header.jsp" %>    

    <div class="py-5" style="background-color: #F9F9F9; height: 700px;" id="main-container">
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            Login
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email address</label>
                                    <input type="email" name="email" class="form-control" id="email" aria-describedby="emailHelp" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" name="password" class="form-control" id="password" required>
                                </div>
                                <div class="d-flex justify-content-between">
                                    <div class="mb-3 form-check">
                                        <input type="checkbox" class="form-check-input" id="remember">
                                        <label class="form-check-label" for="remember">Remember</label>
                                    </div>
                                    <button type="button" class="btn btn-primary" onclick="login()">Login</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

<script src="/js/adminlogin.js"></script>

<%@ include file="layout/footer.jsp" %>