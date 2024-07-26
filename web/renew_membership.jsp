<%@ page import="java.util.List" %>
<%@ page import="com.gsm.model.MembershipType" %>
<%@ page import="com.gsm.model.Member" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    Member member = (Member) request.getAttribute("member");
    List<MembershipType> membershipTypes = (List<MembershipType>) request.getAttribute("membershipTypes");
%>

<!DOCTYPE html>
<html>
<%@ include file="includes/header.jsp"%>
<head>
    <!-- Stylesheets -->
  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <link rel="stylesheet" href="dist/css/adminlte.min.css">
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">

    <meta charset="UTF-8">
    <title>Renew Membership</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
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

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <!-- Logout Link -->
      <li class="nav-item">
        <a class="nav-link" href="LogoutServlet">Logout</a>
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

    <!-- Main content -->
    <div class="content-wrapper">
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1>Renew Membership</h1>
                    </div>
                </div>
            </div>
        </section>

        <section class="content">
            <div class="container-fluid">
                <!-- Info boxes -->
                <div class="row">
                    <!-- left column -->
                    <div class="col-md-12">
                        <div class="card card-primary">
                            <div class="card-header">
                                <h3 class="card-title">Renew Membership Form</h3>
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty message}">
                                    <div class="alert ${success ? 'alert-success' : 'alert-danger'} alert-dismissible">
                                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                                        ${message}
                                    </div>
                                </c:if>

                                <form method="post" action="RenewServlet">
                                    <input type="hidden" name="memberId" value="${member.id}">
                                    <div class="form-group">
                                        <label for="fullname">Full Name</label>
                                        <input type="text" class="form-control" id="fullname" value="${member.fullname}" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label for="membershipType">Membership Type</label>
                                        <select class="form-control" id="membershipType" name="membershipType">
                                            <c:forEach var="type" items="${membershipTypes}">
                                                <option value="${type.id}">${type.type} - ${type.amount}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="extend">Renew Upto</label>
                                        <select class="form-control" id="extend" name="extend">
                                            <option value="1">One Month</option>
                                            <option value="3">Three Months</option>
                                            <option value="6">Six Months</option>
                                            <option value="12">One Year</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="totalAmount">Total Amount</label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" id="currencySymbol">RM</span>
                                            </div>
                                            <input type="text" class="form-control" id="totalAmount" name="totalAmount" readonly>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <!--/.col (left) -->
                </div>
                <!-- /.row -->
            </div><!--/. container-fluid -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- /.wrapper -->

<%@ include file="includes/footer.jsp"%>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script>
    $(document).ready(function () {
        function updateTotalAmount() {
            var membershipTypeAmount = parseFloat($('#membershipType option:selected').text().split('-').pop());
            var renewDuration = parseFloat($('#extend').val());
            var totalAmount = membershipTypeAmount * renewDuration;
            $('#totalAmount').val(totalAmount.toFixed(2));
        }
        $('#membershipType, #extend').change(updateTotalAmount);
        updateTotalAmount();
    });
</script>
</body>
</html>
<!-- Include necessary JS files -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="path/to/your/custom/scripts.js"></script> <!-- Adjust the path as needed -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
