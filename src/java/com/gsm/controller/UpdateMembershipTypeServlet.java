package com.gsm.controller;

import com.gsm.dao.MembershipDAO;
import com.gsm.model.MembershipType;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/updateMembershipType")
public class UpdateMembershipTypeServlet extends HttpServlet {
    private MembershipDAO membershipDAO;

    public void init() {
        membershipDAO = new MembershipDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        double amount = Double.parseDouble(request.getParameter("amount"));

        MembershipType membershipType = new MembershipType(id, type, description, amount);

        try {
            membershipDAO.updateMembershipType(membershipType);
            response.sendRedirect(request.getContextPath() + "/viewTypeServlet");
        } catch (SQLException e) {
            throw new ServletException("Error updating membership type", e);
        }
    }

    public void destroy() {
        try {
            membershipDAO.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
