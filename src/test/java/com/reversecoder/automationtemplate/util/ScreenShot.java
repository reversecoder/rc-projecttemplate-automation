package com.reversecoder.automationtemplate.util;

import java.io.File;

public class ScreenShot {

	/**
	 * The test name.
	 */
	private String testName;

	/**
	 * The test parameters.
	 */
	private Object[] parameters;

	/**
	 * The screen shot.
	 */
	private File file;

	public ScreenShot(String fileName) {
		this.testName = fileName;
	}
	/**
	 * Gets the screen shot file name.
	 * 
	 * @return the file name
	 */
	public String getScreenShotFile() {
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
