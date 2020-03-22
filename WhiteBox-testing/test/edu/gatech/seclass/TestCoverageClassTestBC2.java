package edu.gatech.seclass;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCoverageClassTestBC2 {

    // 100% branch coverage and reveals the fault
    @Test
    public void test1CoverageMethod2() {
        assertEquals(0, TestCoverageClass.testCoverageMethod2(1));
    }
    
    @Test
    public void test2CoverageMethod2() {
        assertEquals(0, TestCoverageClass.testCoverageMethod2(0));
    }
}
