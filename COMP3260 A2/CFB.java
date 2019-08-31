/*
CFB.java

Description:
    The CFB (Cipher Feedback) mode for block ciphers implementation.
 */

import java.util.List;

public class CFB extends AESMode {

    // -- Constructor --
    //   Role: Call super constructor
    //   Args: params - The parameters as defined when the program is initiated.
    // Return: this
    //
    public CFB(InputParameters params) {
        super(params, "CFB");
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

        int size = getParams().getSize();
        int iterations = input.size() / size;
        List<Integer> encryptPart = getParams().getIV();

        // Loop through the input text, breaking it up into 16-byte portions.
        for (int i = 0; i < iterations; i++) {
            List<Integer> inputPart = input.subList(i * size, (i + 1) * size);

            // Run encryption and keep first s bytes.
            List<Integer> resultPart = AES.encrypt(encryptPart, key);
            resultPart = resultPart.subList(0, size);

            // XOR result of encryption and input, and add result to the output.
            addLists(resultPart, inputPart);
            addToOutput(resultPart);

            // Shift encryptPart to the left and append cipher-text to end.
            encryptPart = encryptPart.subList(size, key.size());
            if(getParams().isEncrypting())
                encryptPart.addAll(resultPart);
            else
                encryptPart.addAll(inputPart);

        }
    }
}
