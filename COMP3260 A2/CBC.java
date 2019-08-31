/*
CBC.java

Description:
    The CBC (Cipher Block Chaining) mode for block ciphers implementation.
 */

import java.util.List;

public class CBC extends AESMode {

    // -- Constructor --
    //   Role: Call super constructor
    //   Args: params - The parameters as defined when the program is initiated.
    // Return: this
    //
    public CBC(InputParameters params) {
        super(params, "CBC");
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
        List<Integer> xorList = getParams().getIV();

        // Loop through the input text, breaking it up into 16-byte portions.
        for (int i = 0; i < iterations; i++) {
            List<Integer> inputPart = input.subList(i * key.size(), (i + 1) * key.size());

            // Encrypt or decrypt depending on parameters.
            List<Integer> resultPart;
            if (getParams().isEncrypting()) {
                addLists(inputPart, xorList); // XOR
                resultPart = AES.encrypt(inputPart, key);
                xorList = resultPart; // Update list for next iteration
            } else {
                resultPart = AES.decrypt(inputPart, key);
                addLists(resultPart, xorList); // XOR
                xorList = inputPart; // Update list for next iteration
            }

            // Add result to output
            addToOutput(resultPart);
        }
    }
}
