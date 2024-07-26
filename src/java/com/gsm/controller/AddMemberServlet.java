package com.gsm.controller;

import com.gsm.dao.MemberDAO;
import com.gsm.model.Member;
import com.gsm.dao.MembershipDAO;
import com.gsm.model.MembershipType;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AddMemberServlet")
public class AddMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberDAO memberDAO;
    private static final Logger logger = Logger.getLogger(AddMemberServlet.class.getName());

    public void init() {
        memberDAO = new MemberDAO();
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
            addMember(request, response);
        } else if (action.equals("update")) {
            updateMember(request, response);
        } else if (action.equals("delete")) {
            deleteMember(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                listMembers(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            } else if (action.equals("delete")) {
                deleteMember(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    private void addMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String dobString = request.getParameter("dob");
        Date dob = null;
        String gender = request.getParameter("gender");
        String contactNumber = request.getParameter("contact_number");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String country = request.getParameter("country");
        String postcode = request.getParameter("postcode");
        String occupation = request.getParameter("occupation");
        String membershipTypeIdString = request.getParameter("membership_type_id");
        int membershipTypeId = 0;
        Date expiryDate = null;
        logger.info("Received parameters: fullname=" + fullname + ", dob=" + dobString + ", gender=" + gender + 
            ", contactNumber=" + contactNumber + ", email=" + email + ", address=" + address + 
            ", country=" + country + ", postcode=" + postcode + ", occupation=" + occupation + 
            ", membershipTypeId=" + membershipTypeIdString);

        String message;
        boolean success;

        try {
            if (dobString != null && !dobString.isEmpty()) {
                dob = Date.valueOf(dobString);
            }
            if (membershipTypeIdString != null && !membershipTypeIdString.isEmpty()) {
                membershipTypeId = Integer.parseInt(membershipTypeIdString);
            }

            Member newMember = new Member(
                0, // id will be auto-generated
                fullname, dob, gender, contactNumber, email, address, country, postcode, occupation, membershipTypeId, expiryDate
            );

            memberDAO.insertMember(newMember);
            success = true;
            message = "Member added successfully!";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while adding member", e);
            success = false;
            message = "Error adding member: " + e.getMessage();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error while adding member", e);
            success = false;
            message = "Unexpected error: " + e.getMessage();
        }

        if (success) {
            try {
                List<Member> members = memberDAO.getAllMembers();
                request.setAttribute("members", members);
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error retrieving members list", e);
                request.setAttribute("success", false);
                request.setAttribute("message", "Error retrieving members list: " + e.getMessage());
                request.getRequestDispatcher("add_members.jsp").forward(request, response);
                return;
            }
        }

        request.setAttribute("success", success);
        request.setAttribute("message", message);
        request.getRequestDispatcher(success ? "add_members.jsp" : "add_members.jsp").forward(request, response);
    }

    private void updateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fullname = request.getParameter("fullname");
        String dobString = request.getParameter("dob");
        Date dob = null;
        String gender = request.getParameter("gender");
        String contactNumber = request.getParameter("contact_number");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String country = request.getParameter("country");
        String postcode = request.getParameter("postcode");
        String occupation = request.getParameter("occupation");
        String membershipTypeIdString = request.getParameter("membership_type_id");
        int membershipTypeId = 0;
        Date expiryDate = null;
        
        try {
            if (dobString != null && !dobString.isEmpty()) {
                dob = Date.valueOf(dobString);
            }
            if (membershipTypeIdString != null && !membershipTypeIdString.isEmpty()) {
                membershipTypeId = Integer.parseInt(membershipTypeIdString);
            }

            Member updatedMember = new Member(
                id, // id to identify which member to update
                fullname, dob, gender, contactNumber, email, address, country, postcode, occupation, membershipTypeId, expiryDate
            );

            memberDAO.updateMember(updatedMember);

            request.setAttribute("success", true);
            request.setAttribute("message", "Member updated successfully!");
            request.getRequestDispatcher("manage_members.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("success", false);
            request.setAttribute("message", "Error updating member: " + e.getMessage());
            request.getRequestDispatcher("edit_member.jsp").forward(request, response);
        }
    }

   private void deleteMember(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int memberId = Integer.parseInt(request.getParameter("id"));
    HttpSession session = request.getSession();
    try {
        // Handle related records first
        memberDAO.deleteRelatedRecords(memberId);
        
        boolean success = memberDAO.deleteMember(memberId);
        if (success) {
            session.setAttribute("successDelete", true);
            session.setAttribute("messageDelete", "Member deleted successfully");
        } else {
            session.setAttribute("successDelete", false);
            session.setAttribute("messageDelete", "Failed to delete member");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        session.setAttribute("successDelete", false);
        session.setAttribute("messageDelete", "Error deleting member: " + e.getMessage());
    }
    response.sendRedirect("manage_members.jsp");
}




    private void listMembers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Member> members = memberDAO.getAllMembers();
    MembershipDAO membershipDAO = new MembershipDAO();
    List<MembershipType> membershipTypes = membershipDAO.getAllMembershipTypes();

    request.setAttribute("members", members);
    request.setAttribute("membershipTypes", membershipTypes);
    request.getRequestDispatcher("add_members.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Member existingMember = memberDAO.getMemberById(id);
        request.setAttribute("member", existingMember);
        request.getRequestDispatcher("edit_member.jsp").forward(request, response);
    }
}
