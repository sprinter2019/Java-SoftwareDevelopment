package edu.gatech.seclass;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCoverageClassTestSC2 {

    // Achieves 100% statement coverage but less than 100% branch coverage and does not reveal the fault
    @Test
    public void testCoverageMethod2() {
        assertEquals(0, TestCoverageClass.testCoverageMethod2(1));
    }
}
