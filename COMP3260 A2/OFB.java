/*
OFB.java

Description:
    The OFB (Output Feedback) mode for block ciphers implementation.
 */

import java.util.List;

public class OFB extends AESMode {

    // -- Constructor --
    //   Role: Call super constructor
    //   Args: params - The parameters as defined when the program is initiated.
    // Return: this
    //
    public OFB(InputParameters params) {
        super(params, "OFB");
    }

    // -- Public  --
    //   Role: Implement run method of AESMode.
    //   Args: None
    // Return: Void
    //
    @Override
    public void run() {
        // Ensure list is empty and get params to local variables.
        getOutput().clear();
        List<Integer> input = getParams().getInputText();
        List<Integer> key = getParams().getKey();

        int iterations = input.size() / key.size();
        List<Integer> encryptPart = getParams().getIV();

        // Loop through the input text, breaking it up into 16-byte portions.
        for (int i = 0; i < iterations; i++) {
            List<Integer> inputPart = input.subList(i * key.size(), (i + 1) * key.size());

            // Encryption is same as decryption in OFB.
            encryptPart = AES.encrypt(encryptPart, key);

            // XOR the two lists and add result to output.
            addLists(inputPart, encryptPart);
            addToOutput(inputPart);
        }
    }
}
