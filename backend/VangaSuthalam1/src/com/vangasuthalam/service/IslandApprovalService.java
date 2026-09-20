package com.vangasuthalam.service;

import com.vangasuthalam.dao.IslandApprovalDAO;
import com.vangasuthalam.model.IslandApproval;

public class IslandApprovalService {

    private IslandApprovalDAO islandApprovalDAO;

    public IslandApprovalService() {

        islandApprovalDAO = new IslandApprovalDAO();

    }

    // ==========================================
    // ADD ISLAND APPROVAL
    // ==========================================

    public boolean addIslandApproval(IslandApproval approval) {

        if (approval == null) {
            return false;
        }

        if (approval.getBookingId() <= 0) {
            return false;
        }

        if (approval.getDestinationId() < 5 ||
            approval.getDestinationId() > 9) {

            System.out.println();
            System.out.println("Invalid Island Destination ID.");
            return false;
        }

        if (approval.getCertificateNumber() == null ||
            approval.getCertificateNumber().trim().isEmpty()) {

            return false;
        }

        if (approval.getApprovedBy() == null ||
            approval.getApprovedBy().trim().isEmpty()) {

            return false;
        }

        if (approval.getApprovalStartDate() == null ||
            approval.getApprovalEndDate() == null) {

            return false;
        }

        // ==========================================
        // MAXIMUM 2 DAYS
        // ==========================================

        long difference =
                approval.getApprovalEndDate().getTime()
                - approval.getApprovalStartDate().getTime();

        long days =
                difference / (1000 * 60 * 60 * 24);

        if (days < 0 || days > 1) {

            System.out.println();
            System.out.println(
                    "Island approval period must be maximum 2 days."
            );

            return false;
        }

        // ==========================================
        // SAVE APPROVAL
        // ==========================================

        return islandApprovalDAO.addIslandApproval(approval);
    }
}