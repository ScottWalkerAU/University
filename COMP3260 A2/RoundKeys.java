/*
RoundKeys.java

Description:
    This class takes a single 16 byte key as an input, and extrapolates it into 11 keys
    as per the key expansion rules of Rijndael (AES). A single public method exists
    for getting the key of a particular round.
 */

import java.util.ArrayList;
import java.util.List;

public class RoundKeys {

    // Key schedule from https://en.wikipedia.org/wiki/Rijndael_key_schedule
    public static final Integer[] ROUND_CONSTANTS = new Integer[]
            {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36};

    private List<Integer[][]> keys;

    // -- Constructor --
    //   Role: Round keys from initial key.
    //   Args: key - Key to create the others from.
    // Return: this
    //
    public RoundKeys(List<Integer> key) {
        keys = new ArrayList<>(11);
        generateKeys(key);
    }

    // -- Private --
    //   Role: Generate 11 round keys.
    //   Args: key - Key for round 1.
    // Return: Void
    //
    private void generateKeys(List<Integer> key) {
        // Turn the first key from list into a 4x4 matrix.
        Integer[][] firstKey = new Integer[4][4];
        for (int i = 0; i < 16; i++) {
            int x = i / 4;
            int y = i % 4;
            firstKey[y][x] = key.get(i);
        }
        keys.add(firstKey);

        // Generate the remaining 10 rounds.
        for (int i = 1; i < 11; i++) {
            keys.add(getNextRoundKey(i, keys.get(i-1)));
        }
    }

    // -- Private --
    //   Role: Generate a specific round key given the last round.
    //   Args: round - Round number to generate.
    //         lastKey - Key from the last round.
    // Return: Integer[][] - Round key.
    //
    private Integer[][] getNextRoundKey(Integer round, Integer[][] lastKey) {
        Integer[][] key = new Integer[4][4];
        // Loop through each position of the key (4x4)
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {

                if (col == 0) {
                    // If the byte is in the first column,
                    // Shift rows of one place back, substitute with S-Box, and XOR with value of last key.
                    key[row][col] = lastKey[(row+1)%4][3];
                    key[row][col] = AES.substitute(key[row][col]);
                    key[row][col] ^= lastKey[row][col];

                    if (row == 0)
                        // If the byte is in the top-left corner also XOR with the round constant.
                        key[row][col] ^= ROUND_CONSTANTS[round-1];
                }
                else {
                    // XOR value of last key and one place back.
                    key[row][col] = key[row][col-1] ^ lastKey[row][col];
                }

            }
        }
        return key;
    }

    // -- Public --
    //   Role: Get round key for a particular round.
    //   Args: i - Round number to get.
    // Return: Integer[][] - Round key.
    //
    public Integer[][] get(int i) {
        return keys.get(i);
    }
}
