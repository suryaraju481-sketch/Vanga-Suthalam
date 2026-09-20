package com.vangasuthalam.service;

import com.vangasuthalam.dao.CaptainDAO;
import com.vangasuthalam.model.Captain;

public class CaptainService {

    private CaptainDAO captainDAO;

    public CaptainService() {
        captainDAO = new CaptainDAO();
    }

    public boolean registerCaptain(Captain captain) {

        if (captain == null) {
            return false;
        }

        if (captain.getName() == null ||
            captain.getName().trim().isEmpty()) {
            return false;
        }

        if (captain.getMobile() == null ||
            captain.getMobile().trim().isEmpty()) {
            return false;
        }

        if (captain.getPassword() == null ||
            captain.getPassword().trim().isEmpty()) {
            return false;
        }

        if (captain.getExperienceYears() < 0) {
            return false;
        }

        if (captain.getQualification() == null ||
            captain.getQualification().trim().isEmpty()) {
            return false;
        }

        return captainDAO.addCaptain(captain);
    }
}