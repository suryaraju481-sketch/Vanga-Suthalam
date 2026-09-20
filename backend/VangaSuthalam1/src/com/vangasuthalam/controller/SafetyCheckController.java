package com.vangasuthalam.controller;

import com.vangasuthalam.model.SafetyCheck;
import com.vangasuthalam.service.SafetyCheckService;

public class SafetyCheckController {

    private SafetyCheckService safetyCheckService;

    public SafetyCheckController() {
        safetyCheckService = new SafetyCheckService();
    }

    public boolean performSafetyCheck(SafetyCheck safetyCheck) {

        return safetyCheckService.performSafetyCheck(safetyCheck);
    }
}