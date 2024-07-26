package com.gsm.controller;

import com.gsm.dao.MembershipDAO;
import com.gsm.model.MembershipType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/addMembership")
public class MembershipServlet extends HttpServlet {
    private MembershipDAO membershipDAO;

    public void init() {
        membershipDAO = new MembershipDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteMembershipType(request, response);
                    break;
                    case "addMember":
                    showAddMemberForm(request, response);
                    break;
                default:
                    listMembershipTypes(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            addMembershipType(request, response);
        } else if (action.equals("update")) {
            updateMembershipType(request, response);
        }
    }
    private void showAddMemberForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<MembershipType> membershipTypes = membershipDAO.getMembershipTypes();
        request.setAttribute("membershipTypes", membershipTypes);
        request.getRequestDispatcher("add_members.jsp").forward(request, response);
    }

    private void addMembershipType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeName = request.getParameter("membershipType");
        String description = request.getParameter("description");
        String amountStr = request.getParameter("membershipAmount");

        String message;
        boolean success;
        
        try {
            double amount = Double.parseDouble(amountStr);
            membershipDAO.insertMembershipType(typeName, description, amount);
            success = true;
            message = "Membership type added successfully!";
        } catch (SQLException e) {
            success = false;
            message = "Error: " + e.getMessage();
        } catch (NumberFormatException e) {
            success = false;
            message = "Invalid amount format.";
        }

        request.setAttribute("success", success);
        request.setAttribute("message", message);
        request.getRequestDispatcher("membership.jsp").forward(request, response);
    }

    private void listMembershipTypes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<MembershipType> membershipTypes = membershipDAO.getMembershipTypes();
        request.setAttribute("membershipTypes", membershipTypes);
        request.setAttribute("currencySymbol", "RM"); // Set your currency symbol here
        request.getRequestDispatcher("view_type.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        MembershipType existingMembershipType = membershipDAO.getMembershipTypeById(id);
        request.setAttribute("membershipType", existingMembershipType);
        request.getRequestDispatcher("edit_type.jsp").forward(request, response);
    }

    private void updateMembershipType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String typeName = request.getParameter("membershipType");
        String description = request.getParameter("description");
        String amountStr = request.getParameter("membershipAmount");

        String message;
        boolean success;

        try {
            double amount = Double.parseDouble(amountStr);
            MembershipType membershipType = new MembershipType(id, typeName, description, amount);
            membershipDAO.updateMembershipType(membershipType);
            success = true;
            message = "Membership type updated successfully!";
        } catch (SQLException e) {
            success = false;
            message = "Error: " + e.getMessage();
        } catch (NumberFormatException e) {
            success = false;
            message = "Invalid amount format.";
        }

        request.setAttribute("success", success);
        request.setAttribute("message", message);
        request.getRequestDispatcher("edit_type.jsp").forward(request, response);
    }

    private void deleteMembershipType(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        membershipDAO.deleteMembershipType(id);
        response.sendRedirect("membership?action=list");
    }
}
