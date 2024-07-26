<%@ page import="java.sql.*" %>
<%String currentPage = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);
%>
<%--
<%
    String[] pages = {
        "dashboard.jsp",
        "members_list.jsp",
        "add_type.jsp",
        "view_type.jsp",
        // Add other page names here
    };

    String currentPage = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);

    String jdbcURL = "jdbc:derby://localhost:1527/GymManagementSystem";
    String jdbcUsername = "app";
    String jdbcPassword = "app";

    int totalCount = 0;

    try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
         Statement stmt = conn.createStatement()) {

        String countQuery = "SELECT COUNT(*) as total_types FROM membership_types";
        ResultSet countResult = stmt.executeQuery(countQuery);

        if (countResult.next()) {
            totalCount = countResult.getInt("total_types");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    String systemName = "CodeAstro";
    String logoUrl = "dist/img/AdminLTELogo.png";

    try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
         Statement stmt = conn.createStatement()) {

        ResultSet settingsResult = stmt.executeQuery("SELECT system_name, logo FROM settings");
        if (settingsResult.next()) {
            systemName = settingsResult.getString("system_name");
            logoUrl = settingsResult.getString("logo");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>
--%>
<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="#" class="brand-link">
        
        <span class="brand-text font-weight-light">GSM</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="" class="img-circle elevation-2" alt="User Image">
            </div>
            
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <li class="nav-item">
                    <a href="dashboard.jsp" class="nav-link <%= currentPage.equals("dashboard.jsp") ? "active" : "" %>">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <p>Dashboard</p>
                    </a>
                </li>

                <li class="nav-item has-treeview">
                    <a href="#" class="nav-link <%= currentPage.equals("add_type.jsp") || currentPage.equals("view_type.jsp") ? "active" : "" %>">
                        <i class="nav-icon fas fa-th-list"></i>
                        <p>
                            Membership Types
                            <i class="fas fa-angle-left right"></i>
                            <span class="badge badge-info right"><%--<%= totalCount %>--%></span>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="add_type.jsp" class="nav-link">
                                <i class="fas fa-circle-notch nav-icon"></i>
                                <p>Add New</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="view_type.jsp" class="nav-link">
                                <i class="fas fa-circle-notch nav-icon"></i>
                                <p>View and Manage</p>
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="nav-item">
                    <a href="add_members.jsp" class="nav-link <%= currentPage.equals("add_members.jsp") ? "active" : "" %>">
                        <i class="nav-icon fas fa-users"></i>
                        <p>Add Members</p>
                    </a>
                </li>

                <li class="nav-item">
                    <a href="manage_members.jsp" class="nav-link <%= currentPage.equals("manage_members.jsp") || currentPage.equals("edit_member.jsp") || currentPage.equals("memberProfile.jsp") ? "active" : "" %>">
                        <i class="nav-icon fas fa-users-cog"></i>
                        <p>Manage Members</p>
                    </a>
                </li>

                <li class="nav-item">
                    <a href="list_renewal.jsp" class="nav-link <%= currentPage.equals("list_renewal.jsp") || currentPage.equals("renew.jsp") ? "active" : "" %>">
                        <i class="nav-icon fas fa-undo"></i>
                        <p>Renewal</p>
                    </a>
                </li>

               

                <li class="nav-item">
                    <a href="logout.jsp" class="nav-link <%= currentPage.equals("logout.jsp") ? "active" : "" %>">
                        <i class="nav-icon fas fa-power-off"></i>
                        <p>Logout</p>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>
