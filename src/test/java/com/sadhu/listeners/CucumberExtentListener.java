package com.sadhu.listeners;

import com.aventstack.extentreports.ExtentReports;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import utils.ExtentReportManager;
import utils.ExtentTestManager;

import io.cucumber.plugin.event.EventPublisher;
import com.aventstack.extentreports.ExtentTest;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CucumberExtentListener implements ConcurrentEventListener {

    private static ExtentReports extent = ExtentReportManager.getInstance();
    private static Map<String, ExtentTest> featureTestMap = new HashMap<>();


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestCaseStarted.class,this::onTestCaseStarted);
        eventPublisher.registerHandlerFor(TestStepFinished.class,this::onTestStepFinished);
        eventPublisher.registerHandlerFor(TestCaseFinished.class,this::onTestCaseFinished);
        eventPublisher.registerHandlerFor(TestRunFinished.class, event ->extent.flush());
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        String uriString = event.getTestCase().getUri().toString();

        // Remove "classpath:" prefix if present
        if(uriString.contains("classpath")){
            uriString = uriString.substring("classpath:".length());
        }

        // Extract just the file name
        String featureName = Paths.get(uriString).getFileName().toString();

        // Optionally remove ".feature"
        featureName = featureName.replace(".feature", "");

        String scenarioName = event.getTestCase().getName();

        // Create or get feature node (shared across threads, synchronize access)
        synchronized (featureTestMap) {
            if (!featureTestMap.containsKey(featureName)) {
                ExtentTest featureTest = extent.createTest("Feature: " + featureName);
                featureTestMap.put(featureName, featureTest);
            }
        }
        ExtentTest featureTest = featureTestMap.get(featureName);

        // Create scenario node for current thread and store in ThreadLocal
        ExtentTest scenarioTest = featureTest.createNode("Scenario: " + scenarioName);
        ExtentTestManager.setTest(scenarioTest);
    }

    private void onTestStepFinished(TestStepFinished event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            // Ignore hooks or background steps if desired
            return;
        }

        PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
        String stepText = step.getStep().getKeyword() + step.getStep().getText();

        ExtentTest test = ExtentTestManager.getTest();
        if (test == null) return;

        switch (event.getResult().getStatus()) {
            case PASSED:
                test.log(com.aventstack.extentreports.Status.PASS,stepText);
                break;
            case FAILED:
                test.log(com.aventstack.extentreports.Status.FAIL, stepText);
                test.fail(event.getResult().getError());
                break;
            case SKIPPED:
                test.log(com.aventstack.extentreports.Status.SKIP, stepText);
                break;
            default:
                test.log(com.aventstack.extentreports.Status.INFO, stepText);
                break;
        }
    }

    private void onTestCaseFinished(TestCaseFinished event) {
        // Clean up ThreadLocal to avoid memory leaks
        ExtentTestManager.unload();
    }
}

