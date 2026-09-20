package com.vangasuthalam.controller;

import com.vangasuthalam.model.IslandApproval;
import com.vangasuthalam.service.IslandApprovalService;

public class IslandApprovalController {

    private IslandApprovalService islandApprovalService;

    public IslandApprovalController() {
        islandApprovalService = new IslandApprovalService();
    }

    public boolean addIslandApproval(IslandApproval approval) {

        return islandApprovalService.addIslandApproval(approval);
    }
}
