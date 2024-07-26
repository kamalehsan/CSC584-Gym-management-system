package com.gsm.controller;

import com.gsm.dao.MemberDAO;
import com.gsm.model.Member;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewMemberServlet")
public class ViewMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberDAO memberDAO;

    public void init() {
        memberDAO = new MemberDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Member> members = memberDAO.getAllMembers(); // Fetch all members
            request.setAttribute("members", members); // Set members list as request attribute
            request.getRequestDispatcher("view_members.jsp").forward(request, response); // Forward to JSP
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

