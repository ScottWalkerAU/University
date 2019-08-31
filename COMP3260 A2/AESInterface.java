/*
AESInterface.java

Description:
    Class which starts the program. It gathers the input data, executes the
    appropriate mode of encryption / decryption, and then prints the results.
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class AESInterface {

    public static void main(String[] args) {
        // Set up scanner and parameters class
        Scanner scanner = new Scanner(System.in);
        InputParameters params;

        // Get input from scanner into InputParameters class
        try {
            params = new InputParameters(scanner);
        } catch (InputMismatchException e) {
            // If input is not exactly as described by assignment specifications, error.
            System.out.println("Input invalid, please try again");
            e.printStackTrace();
            return;
        }

        // Determine which mode of operation to use
        AESMode mode;
        switch(params.getMode()) {
            default:
            case 0:
                mode = new ECB(params);
                break;
            case 1:
                mode = new CFB(params);
                break;
            case 2:
                mode = new CBC(params);
                break;
            case 3:
                mode = new OFB(params);
                break;
        }

        // Run the encryption or decryption, then print the final result.
        mode.run();
        mode.printOutput();
    }

}
