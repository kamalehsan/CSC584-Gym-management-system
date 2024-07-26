<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.gsm.dao.MemberDAO" %>
<%@ page import="com.gsm.dao.MembershipDAO" %>
<%@ page import="com.gsm.model.Member" %>
<%@ page import="com.gsm.model.MembershipType" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Add Member</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <!-- overlayScrollbars -->
  <link rel="stylesheet" href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
  <!-- DataTables -->
  <link rel="stylesheet" href="plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
  <link rel="stylesheet" href="plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/adminlte.min.css">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
</head>

<body class="hold-transition sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed">
<div class="wrapper">
    <!-- Navbar -->
    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
            </li>
        </ul>
    </nav>
    <!-- /.navbar -->

    <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="" class="brand-link">
        <img src="dist/img/2382414.png" class="brand-image img-circle elevation-3" alt="User Image">
        <span class="brand-text font-weight-light">Membership System</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <li class="nav-item">
                    <a href="dashboard.jsp" class="nav-link">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <p>
                            Dashboard
                        </p>
                    </a>
                </li>

                <li class="nav-item has-treeview">
                    <a href="#" class="nav-link">
                        <i class="nav-icon fas fa-th-list"></i>
                        <p>
                            Membership Types
                            <i class="fas fa-angle-left right"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="membership.jsp" class="nav-link">
                                <i class="fas fa-circle-notch nav-icon"></i>
                                <p>Add New</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="viewTypeServlet" class="nav-link">
                                <i class="fas fa-circle-notch nav-icon"></i>
                                <p>View and Manage</p>
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="nav-item">
                    <a href="add_members.jsp" class="nav-link">
                        <i class="nav-icon fas fa-users"></i>
                        <p>Add Members</p>
                    </a>
                </li>

                <li class="nav-item">
                    <a href="manage_members.jsp" class="nav-link">
                        <i class="nav-icon fas fa-users-cog"></i>
                        <p>Manage Members</p>
                    </a>
                </li>

                <li class="nav-item">
                    <a href="manage_renew.jsp" class="nav-link">
                        <i class="nav-icon fas fa-undo"></i>
                        <p>Renewal</p>
                    </a>
                </li>


            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0 text-dark">Add Members</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item active"></li>
                        </ol>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <!-- Info boxes -->
                <div class="row">
                    <!-- left column -->
                    <div class="col-md-12">
                        <% 
                            Boolean success = (Boolean) request.getAttribute("success");
                            String message = (String) request.getAttribute("message");
                            if (message != null) { 
                        %>
                            <div class="alert <%= success != null && success ? "alert-success" : "alert-danger" %> alert-dismissible">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <h5><i class="icon fas fa-<%= success != null && success ? "check" : "ban" %>"></i> 
                                    <%= success != null && success ? "Success" : "Error" %></h5>
                                <%= message %>
                            </div>
                        <% } %>
                        <!-- general form elements -->
                        <div class="card card-primary">
                            <div class="card-header">
                                <h3 class="card-title"><i class="fas fa-keyboard"></i> Add Members Form</h3>
                            </div>
                            <!-- /.card-header -->
                            <!-- form start -->
                            <form action="AddMemberServlet" method="post">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <label for="fullname">Full Name</label>
                                            <input type="text" class="form-control" id="fullname" name="fullname"
                                                   placeholder="Enter full name" required>
                                        </div>
                                        <div class="col-sm-3">
                                            <label for="dob">Date of Birth</label>
                                            <input type="date" class="form-control" id="dob" name="dob" required>
                                        </div>
                                        <div class="col-sm-3">
                                            <label for="gender">Gender</label>
                                            <select class="form-control" id="gender" name="gender" required>
                                                <option value="Male">Male</option>
                                                <option value="Female">Female</option>
                                                <option value="Other">Other</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="row mt-3">
                                        <div class="col-sm-6">
                                            <label for="contactNumber">Contact Number</label>
                                            <input palceholder="Enter contac number" type="text" name="contact_number" id="contactNumber" class="form-control">
                                        </div>
                                        <div class="col-sm-6">
                                            <label for="email">Email</label>
                                            <input type="email" class="form-control" id="email" name="email"
                                                   placeholder="Enter email" required>
                                        </div>
                                    </div>

                                    <div class="row mt-3">
                                        <div class="col-sm-6">
                                            <label for="address">Address</label>
                                            <input type="text" class="form-control" id="address" name="address"
                                                   placeholder="Enter address" required>
                                        </div>
                                        <div class="col-sm-6">
                                            <label for="country">Country</label>
                                            <input type="text" class="form-control" id="country" name="country"
                                                   placeholder="Enter country" required>
                                        </div>
                                    </div>

                                    <div class="row mt-3">
                                        <div class="col-sm-6">
                                            <label for="postcode">Postcode</label>
                                            <input type="text" class="form-control" id="postcode" name="postcode"
                                                   placeholder="Enter postcode" required>
                                        </div>
                                        <div class="col-sm-6">
                                            <label for="occupation">Occupation</label>
                                            <input type="text" class="form-control" id="occupation" name="occupation"
                                                   placeholder="Enter occupation" required>
                                        </div>
                                    </div>

                                    <div class="row mt-3">
                                        <div class="col-sm-6">
                                            <label for="membershipType">Membership Type</label>
                                            <select class="form-control" id="membershipType" name="membership_type_id" required>
                                                <% 
                                                    MembershipDAO membershipDAO = new MembershipDAO();
                                                    List<MembershipType> membershipTypes = membershipDAO.getAllMembershipTypes();
                                                    for (MembershipType type : membershipTypes) {
                                                %>
                                                    <option value="<%= type.getId() %>"><%= type.getType() %></option>
                                                <% } %>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.card-body -->

                                <div class="card-footer">
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                </div>
                            </form>
                        </div>
                        <!-- /.card -->
                    </div>
                </div>
                <!-- /.row -->
            </div>
            <!--/. container-fluid -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
        <strong>Gym Membership Management System &copy; 2023.</strong>
        All rights reserved.
        <div class="float-right d-none d-sm-inline-block">
            <b>Version</b> 1.0
        </div>
    </footer>
</div>
<!-- ./wrapper -->

<!-- REQUIRED SCRIPTS -->
<!-- jQuery -->
<script src="plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- overlayScrollbars -->
<script src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.js"></script>

<!-- PAGE PLUGINS -->
<!-- DataTables -->
<script src="plugins/datatables/jquery.dataTables.min.js"></script>
<script src="plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
</body>
</html>
