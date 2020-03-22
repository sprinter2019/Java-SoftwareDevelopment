package edu.gatech.seclass;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCoverageClassTestSC1b {

    //Achieves 100 statement coverage and does not reveal the fault
    @Test
    public void testCoverageMethod1() {
        assertEquals(0, TestCoverageClass.testCoverageMethod1(2));
    }
}