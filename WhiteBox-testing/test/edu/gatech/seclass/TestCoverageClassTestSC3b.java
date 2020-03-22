package edu.gatech.seclass;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCoverageClassTestSC3b {

    // Less than 100% statement coverage and reveals the fault    
    @Test
    public void test2CoverageMethod3() {
        assertEquals(0, TestCoverageClass.testCoverageMethod3(0));
    }
}
