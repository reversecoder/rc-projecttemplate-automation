package com.reversecoder.automationtemplate.dto.data;

import com.reversecoder.automationtemplate.dto.Dto;

public class UserDto implements Dto {
	public String testCaseId;

	public String email;

	public String password;

	public String emailNoData;

	@Override
	public String toString() {
		return "UserDto [testCaseId=" + testCaseId + ", email=" + email + ", password=" + password + ", emailNoData="
				+ emailNoData + "]";
	}
}
