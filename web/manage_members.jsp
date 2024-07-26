<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.gsm.model.Member" %>
<%@ page import="com.gsm.dao.MemberDAO" %>
<%@ page import="com.gsm.dao.MembershipDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gsm.model.MembershipType" %>
<%@ page import="java.sql.SQLException" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Membership Management</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <!-- icheck bootstrap -->
  <link rel="stylesheet" href="plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/adminlte.min.css">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
</head>
<body class="hold-transition sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed">
<div class="wrapper">
  <!-- Include nav -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      <li class="nav-item d-none d-sm-inline-block">
        <a href="dashboard.jsp" class="nav-link">Home</a>
      </li>
    </ul>
  </nav>
  <!-- Include sidebar -->
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

    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">Manage Members</h1>
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
          <div class="col-12">
              <%

            Boolean successDelete = (Boolean) session.getAttribute("successDelete");
            String messageDelete = (String) session.getAttribute("messageDelete");
            if (messageDelete != null) { 
              session.removeAttribute("successDelete");
              session.removeAttribute("messageDelete");
          %>
              <div class="alert <%= successDelete != null && successDelete ? "alert-success" : "alert-danger" %> alert-dismissible">
                  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                  <h5><i class="icon fas fa-<%= successDelete != null && successDelete ? "check" : "ban" %>"></i> 
                      <%= successDelete != null && successDelete ? "Success" : "Error" %></h5>
                  <%= messageDelete %>
              </div>
          <% } %>
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
            <div class="card">
<div class="card-header">
        <h3 class="card-title">Members DataTable</h3>
    </div>
              <div class="card-body">
                <!-- Include table content here -->
                <table id="example1" class="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Fullname</th>
                      <th>Contact</th>
                      <th>Email</th>
                      <th>Type</th>
                      <th>Status</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                   <%
    MemberDAO memberDAO = new MemberDAO();
    MembershipDAO membershipDAO = new MembershipDAO();
    List<Member> members = null;
    List<MembershipType> membershipTypes = null;

    try {
        members = memberDAO.getAllMembers();
        membershipTypes = membershipDAO.getAllMembershipTypes();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    if (members == null) {
        out.println("Members list is null");
    } else if (membershipTypes == null) {
        out.println("Membership types list is null");
    } else {
        Map<Integer, String> membershipTypeMap = new HashMap<Integer, String>();

        for (MembershipType type : membershipTypes) {
            membershipTypeMap.put(type.getId(), type.getType());
        }

        for (Member member : members) {
%>
<tr role="row" class="odd">
    <td tabindex="0" class="sorting_1"><%= member.getId() %></td>
    <td><%= member.getFullname() %></td>
    <td><%= member.getContactNumber() %></td>
    <td><%= member.getEmail() %></td>
    <td><%= membershipTypeMap.get(member.getMembershipTypeId()) %></td>
    <td>Active</td>
    <td>
        <a class="btn btn-primary" href="AddMemberServlet?action=edit&id=<%= member.getId() %>">Edit</a>
        <button class="btn btn-danger" onclick="confirmDelete(<%= member.getId() %>)">Delete</button>
    <%--   <a class="btn btn-danger" href="AddMemberServlet?action=delete&id=<%= member.getId() %>">Delete</a> --%>
    </td>
</tr>
<%
        }
    }
%>

                
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      </div><!--/. container-fluid -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <!-- Include footer -->
  <footer class="main-footer">

  </footer>
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.min.js"></script>
<!-- DataTables -->
<script src="plugins/datatables/jquery.dataTables.min.js"></script>
<script src="plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
<!-- page script -->
<script>
  $(function () {
    $("#example1").DataTable({
      "responsive": true,
      "autoWidth": false,
    });
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false,
      "responsive": true,
    });
  });
</script>
<script>
  function confirmDelete(id) {
    if (confirm("Are you sure you want to delete this member?")) {
      window.location.href = 'AddMemberServlet?action=delete&id=' + id;
    }
  }
</script>
<script>
  function deleteMember(id) {
    if (confirm("Are you sure you want to delete this member?")) {
      window.location.href = 'AddMemberServlet?action=list' + id;
    }
  }
</script>

</body>
</html>
