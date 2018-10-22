package com.example.sargiskh.rateam.helper;

import com.example.sargiskh.rateam.detail_view.model.Branch;
import com.example.sargiskh.rateam.detail_view.model.ResponseBranches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HelperBranch {

    public static List<Branch> getBranchList(ResponseBranches responseBranches) {
        if (responseBranches == null) {
            return new ArrayList<Branch>();
        }
        Map<String, Branch> idBranchSet =  responseBranches.branches;
        List<Branch> branchList = new ArrayList(idBranchSet.values());
        return branchList;
    }

    public static List<String> getBranchIdList(ResponseBranches responseBranches) {
        if (responseBranches == null) {
            return new ArrayList<String>();
        }
        Map<String, Branch> idBranchSet =  responseBranches.branches;
        List<String> branchIdList = new ArrayList(idBranchSet.keySet());
        return branchIdList;
    }

    public static String getHeadBranchId(ResponseBranches responseBranches, int headKey) {
        if (responseBranches == null) {
            return "";
        }
        Map<String, Branch> idBranchSet =  responseBranches.branches;

        for (Map.Entry<String, Branch> branchEntry : idBranchSet.entrySet()) {
            String branchId = branchEntry.getKey();
            Branch branch = branchEntry.getValue();
            if (branch.head == headKey) {
                return branchId;
            }
        }
        return "";
    }

    public static Branch getHeadBranch(ResponseBranches responseBranches, int headKey) {
        if (responseBranches == null) {
            return null;
        }
        Map<String, Branch> idBranchSet =  responseBranches.branches;

        for (Map.Entry<String, Branch> branchEntry : idBranchSet.entrySet()) {
            String branchId = branchEntry.getKey();
            Branch branch = branchEntry.getValue();
            if (branch.head.intValue() == headKey) {
                return branch;
            }
        }
        return null;
    }
}