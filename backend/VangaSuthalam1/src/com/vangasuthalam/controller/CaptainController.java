package com.vangasuthalam.controller;

import com.vangasuthalam.model.Captain;
import com.vangasuthalam.service.CaptainService;

public class CaptainController {

    private CaptainService captainService;

    public CaptainController() {
        captainService = new CaptainService();
    }

    public boolean registerCaptain(Captain captain) {

        return captainService.registerCaptain(captain);
    }
}
