package com.reversecoder.automationtemplate.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.reversecoder.automationtemplate.dto.Dto;
import com.reversecoder.automationtemplate.dto.data.UserDto;
import com.reversecoder.automationtemplate.util.CSVHandler.ResourceType;

/**
 * @author Md. Rashsadul Alam
 *
 */
public class DataParameters {

    private static Iterator<UserDto[]> getUserByTC(String id) {
        Collection<UserDto[]> provider = new ArrayList<>();
        List<Dto> loginUser = new CSVHandler().getTestData(id, new UserDto(), ResourceType.TEST_DATA);
        for (Dto user : loginUser) {
            provider.add(new UserDto[] { (UserDto) user });
        }
        return provider.iterator();
    }

    @DataProvider(name = "DP_tc_01_019")
    public static Iterator<UserDto[]> getDataForTc_01_019() {
        return getUserByTC("tc_01_019");
    }
}