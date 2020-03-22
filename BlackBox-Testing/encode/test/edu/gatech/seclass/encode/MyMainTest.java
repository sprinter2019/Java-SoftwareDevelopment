package edu.gatech.seclass.encode;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class MyMainTest {
	
/*
Place all  of your tests in this class, optionally using MainTest.java as an example.
*/
    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
     *  TEST UTILITIES
     */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file =  createTmpFile();

        OutputStreamWriter fileWriter =
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);

        fileWriter.close();
        return file;
    }


    //Read File Utility
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /*
     * TEST FILE CONTENT
     */

    private static final String FILE4 = "";
    private static final String FILE6 = "@xTY 5%9*&. mE3Pl o2d$ SsZ!\n" +
            "asFg &j^ lkf";
    private static final String FILE7 = "#@@@~!!!*&^%?'''_-++@3\n" +
            "^#$@!**~?";



    // test cases

    /*
     *   TEST CASES
     *
     */

    // Purpose: Test Empty File
    // Frame #: Test Case 1
    @Test
    public void encodeTest1() throws Exception {
        File inputFile = createInputFile(FILE4);
        String args[] = {"-a", "abc", inputFile.getPath()}; //
        Main.main(args);
        String actual = getFileContent(inputFile.getPath());
        assertEquals(0, actual.length());
    }

    // Purpose: Test Incorrect Argument/s
    // Frame #:Test Case 3
    @Test
    public void encodeTest2() throws Exception {
        boolean validFlag = true;
        File inputFile = createInputFile(FILE6);
        String validFlags[] = {"-a", "-k", "-r", "-c"}; //List of valid flags
        String args[] = {"-x", "abc", inputFile.getPath()}; // Invalid argument
        Main.main(args);

        if(args.length>1){
            for (int i=0;i<validFlags.length;i++) {
                validFlag = Arrays.stream(args).anyMatch(validFlags[i]::equals);
                if(validFlag==true){
                    break;
                }
            }
        }

        assertEquals(false, validFlag);

    }

    // Purpose: Test OPT -a
    // Frame #: Test Case 37
    @Test
    public void encodeTest3() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-a", inputFile.getPath()};
        Main.main(args);

        String expected = "@cGB 5%9*&. nV3Ko l2w$ HhA!\n" +
                "zhUt &q^ opu";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -c
    // Frame #: Test Case 62
    @Test
    public void encodeTest4() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-c", "aXzTs", inputFile.getPath()};
        Main.main(args);

        String expected = "@XtY 5%9*&. mE3Pl o2d$ sSz!\n" +
                "ASFg &j^ lkf";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -r
    // Frame #: Test Case 47
    @Test
    public void encodeTest5() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-r", "aXzTs", inputFile.getPath()};
        Main.main(args);

        String expected = "@Y 5%9*&. mE3Pl o2d$ !\n" +
                "Fg &j^ lkf";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -k
    // Frame #: Test Case 57
    @Test
    public void encodeTest6() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-k", "aXzTs", inputFile.getPath()};
        Main.main(args);

        String expected = "@xT 5%9*&. 3 2$ SsZ!\n" +
                "as &^ ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -a and -c together
    // Frame #: Test Case 32
    @Test
    public void encodeTest7() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-a", "-c", "aXzTs", inputFile.getPath()};
        Main.main(args);

        String expected = "@CgB 5%9*&. nV3Ko l2w$ hHa!\n" +
                "ZHUt &q^ opu";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -a and -r together
    // Frame #: Test Case 17
    @Test
    public void encodeTest8() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-a", "-r", "aXzTs", inputFile.getPath()};
        Main.main(args);

        String expected = "@B 5%9*&. nV3Ko l2w$ !\n" +
                "Ut &q^ opu";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -a and -k together
    // Frame #: Test Case 27
    @Test
    public void encodeTest9() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-a", "-k", "aXzTs", inputFile.getPath()};
        Main.main(args);

        String expected = "@cG 5%9*&. 3 2$ HhA!\n" +
                "zh &^";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -r and -c together
    // Frame #: Test Case 42
    @Test
    public void encodeTest10() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-r", "aXzTs", "-c", "yLf", inputFile.getPath()};
        Main.main(args);

        String expected = "@y 5%9*&. mE3PL o2d$ !\n" +
                "fg &j^ LkF";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -k and -c together
    // Frame #: Test Case 52
    @Test
    public void encodeTest11() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-k", "aXzTs", "-c", "xSz", inputFile.getPath()};
        Main.main(args);

        String expected = "@XT 5%9*&. 3 2$ sSz!\n" +
                "aS &^ ";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -r, -c, and -a together
    // Frame #: Test Case 12
    @Test
    public void encodeTest12() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-a", "-r", "aXzTs", "-c", "yLf", inputFile.getPath()};
        Main.main(args);

        String expected = "@b 5%9*&. nV3KO l2w$ !\n" +
                "ut &q^ OpU";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -k, -c, and -a together
    // Frame #: Test Case 22
    @Test
    public void encodeTest13() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {"-a", "-k", "aXzTs", "-c", "xSz", inputFile.getPath()};
        Main.main(args);

        String expected = "@CG 5%9*&. 3 2$ hHa!\n" +
                "zH &^";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT default (-c applied to all letters)
    // Frame #: Test Case 2
    @Test
    public void encodeTest14() throws Exception {
        File inputFile = createInputFile(FILE6);

        String args[] = {inputFile.getPath()};
        Main.main(args);

        String expected = "@Xty 5%9*&. Me3pL O2D$ sSz! \n" +
        "ASfG &J^ LKF";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
    }

    // Purpose: Test OPT -k, -c, and -a together without the presence of any alphabet in the file
    // Frame #: Test Case 23
    @Test
    public void encodeTest15() throws Exception {
        File inputFile = createInputFile(FILE7);

        String args[] = {"-a", "-k", "VnBt", "-c", "aUx", inputFile.getPath()};
        Main.main(args);

        String expected = "#@@@~!!!*&^%?'''_-++@3\n" +
                "^#$@!**~?";

        String actual = getFileContent(inputFile.getPath());

        assertEquals(expected, actual);
    }

}
