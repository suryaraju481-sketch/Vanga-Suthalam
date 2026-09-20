package com.vangasuthalam.service;

import com.vangasuthalam.dao.SafetyCheckDAO;
import com.vangasuthalam.model.SafetyCheck;

public class SafetyCheckService {

    private SafetyCheckDAO safetyCheckDAO;

    public SafetyCheckService() {
        safetyCheckDAO = new SafetyCheckDAO();
    }

    public boolean performSafetyCheck(SafetyCheck safetyCheck) {

        if (safetyCheck == null) {
            return false;
        }

        if (safetyCheck.getBookingId() <= 0) {
            return false;
        }

        if (!safetyCheck.isCaptainApproved()) {
            return false;
        }

        if (!safetyCheck.isBoatAvailable()) {
            return false;
        }

        if (!safetyCheck.isPassengerCapacityOk()) {
            return false;
        }

        if (!safetyCheck.isLifeJacketsAvailable()) {
            return false;
        }

        if (!safetyCheck.isEmergencyEquipmentAvailable()) {
            return false;
        }

        if (!safetyCheck.isCommunicationEquipmentAvailable()) {
            return false;
        }

        if (!safetyCheck.isWeatherClearance()) {
            return false;
        }

        if (safetyCheck.getCheckedBy() == null ||
            safetyCheck.getCheckedBy().trim().isEmpty()) {
            return false;
        }

        return safetyCheckDAO.addSafetyCheck(safetyCheck);
    }
}
