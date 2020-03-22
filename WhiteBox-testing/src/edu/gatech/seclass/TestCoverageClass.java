package edu.gatech.seclass;

public class TestCoverageClass {

    public static int testCoverageMethod1(int x){
        int i=1;
        if(x>0){
            i-=x;
        }
        int result=1/(i*x);

        System.out.println(result);

        return result;
    }

    public static int testCoverageMethod2(int x){
        int i=1;
        if(x>0){
            i+=x;
        }
        int result=1/(i*x);

        System.out.println(result);

        return result;
    }

    public static int testCoverageMethod3(int x){
        int i=1;
        if(x>0){
            i+=x;
        }
        int result=1/(i*x);

        System.out.println(result);

        return result;
    }

    public static void testCoverageMethod4(){
        /*
          comments:
              100% branch coverage will always make sure of 100% statement coverage. As a result,
              an implementation of criteria 1 (a test suit that achieves 100% branch coverage without
              revealing the fault) will inherently create a test suit that achieves 100% statement
              coverage that does not reveal the fault and will contradict criteria 2 (every test suit
              that achieves 100% statement coverage reveals the fault). Therefore, it is not possible
              to create a method that fulfills both criteria 1 and 2 at the same time.

        */
    }

    public boolean testCoverageMethod5(boolean a, boolean b) {
        int x = 2;
        int y = 4;
        if(a)
            x += 2;
        else
            y = y/x;
        if(b)
            y -= 4;
        else
            y -= 2;
        return ((x/y)>= 0);
    }

    // ================
    //
    // Fill in column “output” with T, F, or E:
    //
    // | a | b |output|
    // ================
    // | T | T |   E   |
    // | T | F |   T   |
    // | F | T |   F   |
    // | F | F |   E   |
    // ================
    //
    // Fill in the blanks in the following sentences with
    // “NEVER”, “SOMETIMES” or “ALWAYS”:
    //
    // Test suites with 100% statement coverage __Sometimes___ reveal the fault in this method.
    // Test suites with 100% branch coverage __Sometimes____ reveal the fault in this method.
    // Test suites with 100% path coverage ___Always___ reveal the fault in this method.
    // ================
}
