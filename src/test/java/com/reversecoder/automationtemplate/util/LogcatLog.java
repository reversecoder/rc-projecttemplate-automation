package com.reversecoder.automationtemplate.util;

import java.io.File;

public class LogcatLog {
	private String testName;

	private Object[] parameters;

	private File file;

	public LogcatLog(String fileName) {
		this.testName = fileName;
	}

	public String getLogFile() {
		StringBuilder sb = new StringBuilder();
		sb.append(testName);
//		if (parameters != null) {
//			sb.append("(");
//			for (int i = 0; i < parameters.length; i++) {
//				sb.append(parameters[i]);
//				sb.append(",");
//			}
//			sb.append(")");
//		}
		return sb.toString();
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
