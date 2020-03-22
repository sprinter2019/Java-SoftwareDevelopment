package edu.gatech.seclass;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCoverageClassTestSC3a {

    // Achieves 100% statement coverage and does not reveal the fault
    @Test
    public void testCoverageMethod3() {
        assertEquals(0, TestCoverageClass.testCoverageMethod3(3));
    }
}
