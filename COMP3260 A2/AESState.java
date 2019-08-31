/*
AESState.java

Description:
    Class which represents the current state of an AES operation. Four major methods
    (and three of their inverses) and provided along with a few helper methods.
 */

import java.util.ArrayList;
import java.util.List;

public class AESState {

    // The current state of AES.
    private Integer[][] state;

    // -- Constructor --
    //   Role: Create AESState matrix.
    //   Args: input - List of length 16.
    // Return: this
    //
    public AESState (List<Integer> input) {
        state = new Integer[4][4];
        for (int i = 0; i < 16; i++) {
            int x = i / 4;
            int y = i % 4;
            state[y][x] = input.get(i);
        }
    }

    // -- Public --
    //   Role: XOR the state with a given key.
    //   Args: key - value to XOR with.
    // Return: Void
    //
    public void addRoundKey(Integer[][] key) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                state[row][col] ^= key[row][col];
            }
        }
    }

    // -- Public --
    //   Role: Mix columns of state.
    //   Args: None
    // Return: Void
    //
    public void mixColumns() {
        mix(MixBoxes.MIX_BOX);
    }

    // -- Public --
    //   Role: Inverse mix columns.
    //   Args: None.
    // Return: Void
    //
    public void inverseMixColumns() {
        mix(MixBoxes.INVERSE_MIX_BOX);
    }

    // -- Private --
    //   Role: Helper method for mixing
    //   Args: matrix - The matrix to multiple state against.
    // Return: Void
    //
    private void mix(Integer[][] matrix) {
        Integer[][] clone = cloneState();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int value = 0;
                value ^= MixBoxes.multiply(clone[0][col], matrix[row][0]);
                value ^= MixBoxes.multiply(clone[1][col], matrix[row][1]);
                value ^= MixBoxes.multiply(clone[2][col], matrix[row][2]);
                value ^= MixBoxes.multiply(clone[3][col], matrix[row][3]);
                state[row][col] = value;
            }
        }
    }

    // -- Public --
    //   Role: Shifts rows to the left.
    //   Args: None
    // Return: Void
    //
    public void shiftRows() {
        Integer[][] clone = cloneState();

        for (int col = 0; col < 4; col++) {
            state[1][col] = clone[1][ (col+1) % 4 ]; // One to the left
            state[2][col] = clone[2][ (col+2) % 4 ]; // Two to the left
            state[3][col] = clone[3][ (col+3) % 4 ]; // Three to the left
        }
    }

    // -- Public --
    //   Role: Shifts rows to the right.
    //   Args: None
    // Return: Void
    //
    public void inverseShiftRows() {
        Integer[][] clone = cloneState();

        for (int col = 0; col < 4; col++) {
            state[1][col] = clone[1][ (col+3) % 4 ]; // One to the right
            state[2][col] = clone[2][ (col+2) % 4 ]; // Two to the right
            state[3][col] = clone[3][ (col+1) % 4 ]; // Three to the right
        }
    }

    // -- Public --
    //   Role: Runs every byte of the state through S-Box.
    //   Args: None
    // Return: Void
    //
    public void substituteBytes() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                state[row][col] = AES.substitute(state[row][col]);
            }
        }
    }

    // -- Public --
    //   Role: Runs every byte of state through inverse S-Box.
    //   Args: None
    // Return: Void
    //
    public void inverseSubstituteBytes() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                state[row][col] = AES.inverseSubstitute(state[row][col]);
            }
        }
    }

    // -- Private --
    //   Role: Performs a deep clone on state.
    //   Args: None
    // Return: Integer[][] - Cloned state.
    //
    private Integer[][] cloneState() {
        Integer[][] clone = new Integer[4][4];
        for (int row = 0; row < 4; row++) {
            clone[row] = state[row].clone();
        }
        return clone;
    }

    // -- Public --
    //   Role: Transforms state into a list.
    //   Args: None
    // Return: List<Integer> - state in list form.
    //
    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>(16);
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                list.add(state[row][col]);
            }
        }
        return list;
    }
}
