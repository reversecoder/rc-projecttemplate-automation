package com.reversecoder.automationtemplate.util;

import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.logging.LogEntry;

public class LogcatLogHandler {
	public LogcatLog takeLog(LogcatLog log, AppiumDriver driver) {
		try {
			if(driver == null) return log;
			List<LogEntry> logEntries = driver.manage().logs().get("logcat")
					.filter(Level.ALL);
			File logFile = new File(getLogFileDirectory() + "/"
					+ log.getLogFile() + ".txt");
			if(logFile.exists()) {
				logFile.delete();
				logFile = new File(getLogFileDirectory() + "/"
						+ log.getLogFile() + ".txt");
			}
			PrintWriter log_file_writer = new PrintWriter(logFile);
			for (LogEntry entry : logEntries) {
				log_file_writer.println("["
						+ new DateUtil(entry.getTimestamp()).toDateTimeStr()
						+ "]: " + entry.getMessage());
			}
			log_file_writer.flush();
			log_file_writer.close();
			log.setFile(logFile);
		} catch (Exception e) {
		}
		return log;
	}

	private String getLogFileDirectory() {
		File file = new File(Constants.LOGCAT_LOG_LOCATION);
		if (!file.exists()) {
			file.mkdir();
		}
		return file.getAbsolutePath();
	}
}
