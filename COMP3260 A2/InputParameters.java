/*
InputParameters.java

Description:
    Class which stores the user-input parameters at the start of AESInterface.java
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParameters {

    // Private member data for 6 lines of input.
    private boolean encrypting;
    private int mode;
    private int size;
    private List<Integer> inputText;
    private List<Integer> key;
    private List<Integer> iv;

    // -- Constructor --
    //   Role: Creates InputParameters from scanner.
    //   Args: scanner - Scanner to read System.in
    // Return: this
    //
    public InputParameters(Scanner scanner) {
        System.out.println("Please input your data as per the assignment specification below:");

        // 0 for encrypt - 1 for decrypt
        int encOrDec = scanner.nextInt();
        setEncrypting(encOrDec == 0);

        // 0 for ECB - 1 for CFB - 2 for CBC - 3 for OFB
        int mode = scanner.nextInt();
        setMode(mode);

        // Transmission size between 1 and 16 - or 0 if not applicable
        int size = scanner.nextInt();
        setSize(size);
        scanner.nextLine();

        // The plaintext
        String inputText = scanner.nextLine();
        setInputText(inputText.split(" "));

        // Ciphertext
        String key = scanner.nextLine();
        setKey(key.split(" "));

        // Initialization vector if applicable
        String initializationVector = scanner.nextLine();
        setIV(initializationVector.split(" "));
    }

    // -- Getters and Setters -- //

    public boolean isEncrypting() {
        return encrypting;
    }

    private void setEncrypting(boolean encrypting) {
        this.encrypting = encrypting;
    }

    public int getMode() {
        return mode;
    }

    private void setMode(int mode) {
        this.mode = mode;
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public List<Integer> getInputText() {
        return inputText;
    }

    private void setInputText(String[] input) {
        this.inputText = new ArrayList<>(input.length);
        for (int i = 0; i < input.length; i++) {
            this.inputText.add(Integer.parseInt(input[i], 16)); // Parse each string as a hex value
        }
    }

    public List<Integer> getKey() {
        return key;
    }

    private void setKey(String[] key) {
        this.key = new ArrayList<>(key.length);
        for (int i = 0; i < key.length; i++) {
            this.key.add(Integer.parseInt(key[i], 16)); // Parse each string as a hex value
        }
    }

    public List<Integer> getIV() {
        return iv;
    }

    private void setIV(String[] iv) {
        this.iv = new ArrayList<>(iv.length);
        for (int i = 0; i < iv.length; i++) {
            this.iv.add(Integer.parseInt(iv[i], 16)); // Parse each string as a hex value
        }
    }
}
