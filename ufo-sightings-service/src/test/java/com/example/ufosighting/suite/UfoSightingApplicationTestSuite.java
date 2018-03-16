package com.example.ufosighting.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.example.ufosighting.controller.UfoSightingControllerTests;
import com.example.ufosighting.integration.UfoSightingIntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({ UfoSightingControllerTests.class, UfoSightingIntegrationTests.class })
public class UfoSightingApplicationTestSuite {

}
