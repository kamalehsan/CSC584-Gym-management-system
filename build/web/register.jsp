<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Membership Management - Register</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="dist/css/adminlte.min.css">
    <!-- MDB CSS (Bootstrap) -->
    <link rel="stylesheet" href="path/to/mdb.min.css">
    <link rel="stylesheet" href="style.css"> <!-- Optional: link to your CSS -->
    
    <style>
        body, html {
            height: 100%;
            margin: 0;
            overflow: hidden; /* Prevent scroll */
        }
        .container {
            height: 100%;
            padding-top: 2rem; /* Remove top padding */
            padding-bottom: 2rem; /* Adjust bottom padding */
        }
        .card {
            width: 90%; /* Adjust width */
            max-width: 400px; /* Set a max-width */
            margin: auto; /* Center the card */
            height: auto; /* Allow card to expand based on content */
        }
    </style>
</head>
<body style="background-color: #8fc4b7;">
<section class="h-100 h-custom">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-8 col-xl-6">
                <div class="card rounded-3">
                    <img src="https://goodhealthandfitness.co.uk/wp-content/uploads/2022/01/gym-header-1500x430.png"
                         class="w-100" style="border-top-left-radius: .3rem; border-top-right-radius: .3rem;"
                         alt="Sample photo">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5 px-md-2">Registration Info</h3>

                        <form action="RegisterServlet" method="post" class="px-md-2">
                            <div data-mdb-input-init class="form-outline mb-4">
                                <input type="text" id="username" name="username" class="form-control" required />
                                <label class="form-label" for="username">Username</label>
                            </div>

                            <div data-mdb-input-init class="form-outline mb-4">
                                <input type="password" id="password" name="password" class="form-control" required />
                                <label class="form-label" for="password">Password</label>
                            </div>

                            <div data-mdb-input-init class="form-outline mb-4">
                                <input type="email" id="email" name="email" class="form-control" required />
                                <label class="form-label" for="email">Email</label>
                            </div>

                            <button type="submit" class="btn btn-success btn-lg mb-1">Register</button>
                        </form>

                        <div class="mt-3">
                            <a href="login.jsp">Already have an account? Login here.</a>
                        </div>
                        <div>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
                out.println("<p style='color: red;'>" + errorMessage + "</p>");
            }
        %>
    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- jQuery -->
<script src="plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- MDB -->
<script src="path/to/mdb.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.min.js"></script>

</body>
</html>
