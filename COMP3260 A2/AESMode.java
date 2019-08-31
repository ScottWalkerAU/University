/*
AESMode.java

Description:
    An abstract class that is extended by ECB, CBC, CFB, and OFB classes. These classes
    define the mode of operation for encryption and decryption for a particular block cipher.
 */

import java.util.ArrayList;
import java.util.List;

public abstract class AESMode {

    private String mode;
    private InputParameters params; // Parameters supplied by user
    private List<Integer> output; // The result from running the mode

    // -- Constructor --
    //   Role: Create generic AESMode
    //   Args: params - The parameters as defined when the program is initiated.
    // Return: this
    //
    public AESMode(InputParameters params, String mode) {
        setMode(mode);
        setParams(params);
        setOutput(new ArrayList<>(params.getInputText().size()));
    }

    // -- Public Abstract --
    //   Role: Run the mode.
    //   Args: None
    // Return: Void
    //
    public abstract void run();

    // -- Protected --
    //   Role: Append a list to the current output.
    //   Args: list - Items to add to the output
    // Return: Void
    //
    protected void addToOutput(List<Integer> list) {
        getOutput().addAll(list);
    }

    // -- Public --
    //   Role: Print the final output to console.
    //   Args: None
    // Return: Void
    //
    public void printOutput() {
        // Print title with operation and mode.
        System.out.printf("Output for %s (%s):\n",
                getParams().isEncrypting() ? "encrypting" : "decrypting",
                mode);

        // Loop through printing each byte in hex.
        for (Integer c : getOutput()) {
            String text = Integer.toHexString(c).toUpperCase();
            if (text.length() == 1) // All values should have 2 characters.
                text = "0" + text;
            System.out.print(text + " ");
        }
    }

    // -- Public --
    //   Role: XOR two lists together, storing the result in the first list.
    //   Args: input - Resultant list
    //         key - List to XOR with
    // Return: Void
    //
    public static void addLists(List<Integer> input, List<Integer> key) {
        int size = Math.min(input.size(), key.size());
        for (int i = 0; i < size; i++) {
            input.set(i, input.get(i) ^ key.get(i));
        }
    }

    // -- Getters and Setters -- //

    public String getMode() {
        return mode;
    }

    protected void setMode(String mode) {
        this.mode = mode;
    }

    public InputParameters getParams() {
        return params;
    }

    protected void setParams(InputParameters params) {
        this.params = params;
    }

    public List<Integer> getOutput() {
        return output;
    }

    protected void setOutput(List<Integer> output) {
        this.output = output;
    }
}
