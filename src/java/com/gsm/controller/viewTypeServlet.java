package com.gsm.controller;

import com.gsm.dao.MembershipDAO;
import com.gsm.model.MembershipType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/viewTypeServlet")
public class viewTypeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MembershipDAO membershipDAO;

    public void init() {
        membershipDAO = new MembershipDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<MembershipType> membershipTypes = membershipDAO.getMembershipTypes();
            request.setAttribute("membershipTypes", membershipTypes);
            request.setAttribute("currencySymbol", "RM"); // Replace with your currency symbol
            request.getRequestDispatcher("view_type.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
