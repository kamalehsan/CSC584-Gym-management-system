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

@WebServlet("/editMembershipType")
public class EditMembershipTypeServlet extends HttpServlet {
    private MembershipDAO membershipDAO;

    public void init() {
        membershipDAO = new MembershipDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int typeId = Integer.parseInt(request.getParameter("id"));

        try {
            MembershipType membershipType = membershipDAO.getMembershipTypeById(typeId);
            request.setAttribute("membershipType", membershipType);
            request.getRequestDispatcher("/edit_membership_type.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error retrieving membership type for editing", e);
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
