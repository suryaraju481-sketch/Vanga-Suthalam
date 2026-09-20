package com.vangasuthalam.model;

import java.sql.Date;

public class IslandApproval {

    private int approvalId;

    private int bookingId;
    private int destinationId;

    private String certificateNumber;
    private String approvedBy;

    private Date approvalStartDate;
    private Date approvalEndDate;

    private String approvalStatus;
    private String remarks;

    public IslandApproval() {
    }

    public IslandApproval(int bookingId,
                          int destinationId,
                          String certificateNumber,
                          String approvedBy,
                          Date approvalStartDate,
                          Date approvalEndDate,
                          String remarks) {

        this.bookingId = bookingId;
        this.destinationId = destinationId;
        this.certificateNumber = certificateNumber;
        this.approvedBy = approvedBy;
        this.approvalStartDate = approvalStartDate;
        this.approvalEndDate = approvalEndDate;
        this.remarks = remarks;
    }

    public int getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(int approvalId) {
        this.approvalId = approvalId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovalStartDate() {
        return approvalStartDate;
    }

    public void setApprovalStartDate(Date approvalStartDate) {
        this.approvalStartDate = approvalStartDate;
    }

    public Date getApprovalEndDate() {
        return approvalEndDate;
    }

    public void setApprovalEndDate(Date approvalEndDate) {
        this.approvalEndDate = approvalEndDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
