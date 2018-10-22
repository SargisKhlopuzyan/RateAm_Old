package com.example.sargiskh.rateam.helper;


import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelperOrganization {

    public static List<Organization> getOrganizationList(Map<String, Organization> organizationMap) {
        List<Organization> organizationList = new ArrayList(organizationMap.values());
        return organizationList;
    }

    public static List<String> getOrganizationIdList(Map<String, Organization> organizationMap) {
        List<String> organizationIdList = new ArrayList(organizationMap.keySet());
        return organizationIdList;
    }
}