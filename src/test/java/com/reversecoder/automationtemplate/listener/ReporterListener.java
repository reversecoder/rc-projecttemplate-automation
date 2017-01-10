package com.reversecoder.automationtemplate.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;

public class ReporterListener implements IReporter {
 @Override
 public void generateReport(List xmlSuites, List suites, String outputDirectory) {

  int passCount = 0;
  int failCount = 0;
  int skipCount = 0;
  int totalCount = 0;

  for (Object s : suites) {
   ISuite suite = (ISuite) s;
   Map<String, ISuiteResult> tests = suite.getResults();
   for (String testClass : tests.keySet()) {
    int pCnt = 0;
    int fCnt = 0;
    int sCnt = 0;

    ISuiteResult suiteResult = tests.get(testClass);
    ITestContext testContext = suiteResult.getTestContext();

    pCnt = testContext.getPassedTests().size();
    fCnt = testContext.getFailedTests().size();
    sCnt = testContext.getSkippedTests().size();

    passCount = passCount + pCnt;
    failCount = failCount + fCnt;
    skipCount = skipCount + sCnt;
   }
  }

  totalCount = passCount + failCount + skipCount;

  String SummeryResult = "Total = " + totalCount + "\r\n" + "Passed = " + passCount + "\r\n" + "Failed = "
    + failCount + "\r\n" + "Skipped = " + skipCount + "\r\n";

  try {
   File file = new File("target/surefire-reports/summeryResult.txt");

   if (!file.exists()) {
    file.createNewFile();
   }

   FileWriter fw = new FileWriter(file.getAbsoluteFile());
   BufferedWriter bw = new BufferedWriter(fw);
   bw.write(SummeryResult);
   bw.close();
  } catch (Exception e) {
  }
 }
}