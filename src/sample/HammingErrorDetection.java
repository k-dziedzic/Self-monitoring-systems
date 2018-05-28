package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HammingErrorDetection {

    private static boolean checkIfTheNumberIsPowerOfTwo(int number) {
        boolean isPowerOfTwo = false;
        if (number == 1) {
            isPowerOfTwo = true;
        } else {
            while (number % 2 == 0) {
                number /= 2;
                if (number == 1) {
                    isPowerOfTwo = true;
                    break;
                }
            }
        }
        return isPowerOfTwo;
    }

    public static Integer[] createHammingWord(String word) {

        for (int i = 0; i < word.length(); i++) {
            //check the word only consists of 0 or 1
            if (word.charAt(i) != '0' && word.charAt(i) != '1') {
                return null;
            }
        }

        int hammingWordCounter = 0;
        int counter = 0;
        int length = 3;
        int indexValue = 0;

        List<Integer> powerOfTwoIndexes = new ArrayList<>();
        List<String> binaryIndexes = new ArrayList<>();

        for (int i = 2; i <= word.length(); i++) {
            boolean isPowerOfTwo = checkIfTheNumberIsPowerOfTwo(length + 1);

            if (isPowerOfTwo) {
                length += 2;
            } else {
                length += 1;
            }
        }

        Integer[] hammingWord = new Integer[length];

        for (int i = length - 1; i >= 0; i--) {

            boolean isPowerOfTwo = checkIfTheNumberIsPowerOfTwo(i + 1);

            if (isPowerOfTwo) {
                powerOfTwoIndexes.add(i);
                hammingWord[i] = null;
            } else {

                String temp = "" + word.charAt(counter);
                hammingWord[i] = Integer.parseInt(temp);
                counter++;

                aaaa(hammingWord,i,binaryIndexes,indexValue);
            }
        }

        for (int j = 0; j < binaryIndexes.get(0).length(); j++) {
            int numberOfOnesInColumn = 0;
            for (int i = 0; i < binaryIndexes.size(); i++) {
                if (binaryIndexes.get(i).charAt(j) == '1') {
                    numberOfOnesInColumn++;
                }
            }

            if (numberOfOnesInColumn % 2 == 0) {
                hammingWord[powerOfTwoIndexes.get(hammingWordCounter++)] = 0;
            } else {
                hammingWord[powerOfTwoIndexes.get(hammingWordCounter++)] = 1;
            }
        }
        return hammingWord;
    }

    public static void aaaa(Integer[] hammingWord, int i,  List<String> binaryIndexes, int indexValue ){
        if (hammingWord[i] == 1) {
            String tempBinaryIndex = Integer.toBinaryString(i + 1);
            binaryIndexes.add(tempBinaryIndex);

            for (int j = tempBinaryIndex.length(); j < binaryIndexes.get(0).length(); j++) {
                tempBinaryIndex = "0" + tempBinaryIndex;
                binaryIndexes.set(indexValue, tempBinaryIndex);
            }
            indexValue++;
        }
    }

    public static boolean checkHammingCorectness(Integer[] hammingWord) {
        int indexValue = 0;
        boolean corectness = true;
        List<String> binaryIndexes = new ArrayList<>();

        for (int i = hammingWord.length - 1; i >= 0; i--) {
            aaaa(hammingWord,i,binaryIndexes,indexValue);
        }

        for (int j = 0; j < binaryIndexes.get(0).length(); j++) {
            int numberOfOnesInColumn = 0;
            for (int i = 0; i < binaryIndexes.size(); i++) {
                if (binaryIndexes.get(i).charAt(j) == '1') {
                    numberOfOnesInColumn++;
                }
            }

            if (numberOfOnesInColumn % 2 != 0) {
                corectness = false;
            }

        }
        return corectness;
    }

    public static void injectTheMistakes(Integer[] hammingWord) {
        Random randomIndex = new Random();

        for (int i = 0; i < 3; i++) {
            int random = randomIndex.nextInt(hammingWord.length);
            boolean b = (hammingWord[random] != 0);
            Integer negate = (!b) ? 1 : 0;
            hammingWord[random] = negate;
        }
    }
}
