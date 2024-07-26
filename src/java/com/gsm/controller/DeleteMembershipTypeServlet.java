package com.gsm.controller;

import com.gsm.dao.MembershipDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteMembershipType")
public class DeleteMembershipTypeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        MembershipDAO membershipDAO = new MembershipDAO();
        try {
            membershipDAO.connect();
            membershipDAO.deleteMembershipType(id);
            response.sendRedirect("viewTypeServlet");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                membershipDAO.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
