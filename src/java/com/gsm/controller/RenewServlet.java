package com.gsm.controller;

import com.gsm.dao.MemberDAO;
import com.gsm.dao.MembershipDAO;
import com.gsm.dao.RenewDAO;
import com.gsm.model.Member;
import com.gsm.model.MembershipType;
import com.gsm.model.Renew;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/RenewServlet")
public class RenewServlet extends HttpServlet {
    private MemberDAO memberDAO;
    private RenewDAO renewDAO;
    private MembershipDAO membershipDAO;

    @Override
    public void init() {
        memberDAO = new MemberDAO(); // Use no-argument constructor
        renewDAO = new RenewDAO();
        membershipDAO = new MembershipDAO();
    }

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String memberIdParam = request.getParameter("id");

    // Debug logging
    System.out.println("memberIdParam: " + memberIdParam);

    if (memberIdParam == null) {
        request.setAttribute("success", false);
        request.setAttribute("message", "Error: Missing member ID.");
        request.getRequestDispatcher("/renew_membership.jsp").forward(request, response);
        return;
    }

    int memberId;
    try {
        memberId = Integer.parseInt(memberIdParam);
    } catch (NumberFormatException e) {
        request.setAttribute("success", false);
        request.setAttribute("message", "Error: Invalid member ID format.");
        request.getRequestDispatcher("/renew_membership.jsp").forward(request, response);
        return;
    }

    try {
        Member member = memberDAO.getMemberById(memberId);
        List<MembershipType> membershipTypes = membershipDAO.getAllMembershipTypes();
        request.setAttribute("member", member);
        request.setAttribute("membershipTypes", membershipTypes);
        request.getRequestDispatcher("/renew_membership.jsp").forward(request, response);
    } catch (SQLException e) {
        throw new ServletException("Cannot obtain member or membership types from DB", e);
    }
}


   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String memberIdParam = request.getParameter("memberId");
    String membershipTypeParam = request.getParameter("membershipType");
    String renewDurationParam = request.getParameter("extend");
    String totalAmountParam = request.getParameter("totalAmount");

    // Debug logging
    System.out.println("memberId: " + memberIdParam);
    System.out.println("membershipType: " + membershipTypeParam);
    System.out.println("renewDuration: " + renewDurationParam);
    System.out.println("totalAmount: " + totalAmountParam);

    if (memberIdParam == null || membershipTypeParam == null || renewDurationParam == null || totalAmountParam == null) {
        request.setAttribute("success", false);
        request.setAttribute("message", "Error renewing membership: Missing required parameters.");
        request.getRequestDispatcher("/renew_membership.jsp").forward(request, response);
        return;
    }

    int memberId;
    int membershipTypeId;
    int renewDuration;
    double totalAmount;

    try {
        memberId = Integer.parseInt(memberIdParam);
        membershipTypeId = Integer.parseInt(membershipTypeParam);
        renewDuration = Integer.parseInt(renewDurationParam);
        totalAmount = Double.parseDouble(totalAmountParam);
    } catch (NumberFormatException e) {
        request.setAttribute("success", false);
        request.setAttribute("message", "Error renewing membership: Invalid number format.");
        request.getRequestDispatcher("/renew_membership.jsp").forward(request, response);
        return;
    }

    Renew renew = new Renew();
    renew.setMemberId(memberId);
    renew.setTotalAmount(totalAmount);
    renew.setRenewDate(new Timestamp(System.currentTimeMillis()));

    try {
        // Calculate new expiry date
        Member member = memberDAO.getMemberById(memberId);
        Date currentExpiryDate = member.getExpiryDate();
        Calendar calendar = Calendar.getInstance();

        if (currentExpiryDate != null) {
            calendar.setTime(currentExpiryDate);
        } else {
            calendar.setTime(new Date()); // If expiry date is null, start from now
        }

        calendar.add(Calendar.MONTH, renewDuration);
        java.sql.Date newExpiryDate = new java.sql.Date(calendar.getTimeInMillis());

        memberDAO.updateMembershipTypeAndExpiry(memberId, membershipTypeId, newExpiryDate);
        renewDAO.insertRenew(renew);

        request.setAttribute("success", true);
        request.setAttribute("message", "Membership renewed successfully.");
        request.setAttribute("member", memberDAO.getMemberById(memberId)); // Retrieve the updated member
        request.setAttribute("membershipTypes", membershipDAO.getAllMembershipTypes());
        request.getRequestDispatcher("/renew_membership.jsp").forward(request, response);
    } catch (SQLException e) {
        request.setAttribute("success", false);
        request.setAttribute("message", "Error renewing membership: " + e.getMessage());
        request.getRequestDispatcher("/renew_membership.jsp").forward(request, response);
    }
}



}
