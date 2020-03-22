package edu.gatech.seclass;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCoverageClassTestSC1a {

    //Achieves less than 100 statement coverage and reveals the fault
    @Test
    public void testCoverageMethod1() {
        assertEquals(0, TestCoverageClass.testCoverageMethod1(0));
    }
}