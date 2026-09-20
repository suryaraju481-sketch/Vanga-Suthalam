package com.vangasuthalam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.vangasuthalam.model.IslandApproval;
import com.vangasuthalam.util.DBConnection;

public class IslandApprovalDAO {

    // ==========================================
    // ADD ISLAND APPROVAL
    // ==========================================

    public boolean addIslandApproval(IslandApproval approval) {

        String sql = "INSERT INTO island_approvals "
                   + "(booking_id, destination_id, certificate_number, "
                   + "approved_by, approval_start_date, approval_end_date, "
                   + "approval_status, remarks) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, approval.getBookingId());
            ps.setInt(2, approval.getDestinationId());
            ps.setString(3, approval.getCertificateNumber());
            ps.setString(4, approval.getApprovedBy());
            ps.setDate(5, approval.getApprovalStartDate());
            ps.setDate(6, approval.getApprovalEndDate());

            ps.setString(7, "APPROVED");

            ps.setString(8, approval.getRemarks());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // ==========================================
    // CHECK ISLAND APPROVAL
    // ==========================================

    public boolean isIslandApproved(int bookingId, int destinationId) {

        String sql = "SELECT approval_id "
                   + "FROM island_approvals "
                   + "WHERE booking_id = ? "
                   + "AND destination_id = ? "
                   + "AND approval_status = 'APPROVED'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, destinationId);

            var rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}