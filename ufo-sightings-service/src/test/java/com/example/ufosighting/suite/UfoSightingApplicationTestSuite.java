package com.example.ufosighting.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.example.ufosighting.UfoSightingApplicationTests;
import com.example.ufosighting.integration.UfoSightingIntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({ UfoSightingApplicationTests.class, UfoSightingIntegrationTests.class })
public class UfoSightingApplicationTestSuite {

}
