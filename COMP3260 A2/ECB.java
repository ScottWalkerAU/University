/*
EBC.java

Description:
    The EBC (Electronic Code-book) mode for block ciphers implementation.
 */

import java.util.List;

public class ECB extends AESMode {

    // -- Constructor --
    //   Role: Call super constructor
    //   Args: params - The parameters as defined when the program is initiated.
    // Return: this
    //
    public ECB(InputParameters params) {
        super(params, "ECB");
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

        // Loop through the input text, breaking it up into 16-byte portions.
        for (int i = 0; i < iterations; i++) {
            List<Integer> inputPart = input.subList(i * key.size(), (i+1) * key.size());

            // Encrypt or decrypt depending on parameters.
            if (getParams().isEncrypting())
                addToOutput(AES.encrypt(inputPart, key));
            else
                addToOutput(AES.decrypt(inputPart, key));
        }
    }
}
