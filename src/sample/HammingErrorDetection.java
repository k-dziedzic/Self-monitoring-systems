package sample;

import java.util.ArrayList;
import java.util.Arrays;
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
            if (word.charAt(i) != '0' && word.charAt(i) != '1') {
                return null;
            }

        }
        int hammingWordCounter = 0;
        int counter = 0;
        int length = 3;
        int indexValue = 0;

        ArrayList<Integer> powerOfTwoIndexes = new ArrayList<>();
        ArrayList<String> binaryIndexes = new ArrayList<>();

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

                indexValue = getIndexValue(indexValue, binaryIndexes, hammingWord, i);
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

    private static int getIndexValue(int indexValue, ArrayList<String> binaryIndexes, Integer[] hammingWord, int i) {
        if (hammingWord[i] == 1) {
            String tempBinaryIndex = Integer.toBinaryString(i + 1);
            binaryIndexes.add(tempBinaryIndex);


            for (int j = tempBinaryIndex.length(); j < binaryIndexes.get(0).length(); j++) {
                tempBinaryIndex = "0" + tempBinaryIndex;
                binaryIndexes.set(indexValue, tempBinaryIndex);
            }
            indexValue++;
        }
        return indexValue;
    }

    public static boolean checkHammingCorectness(Integer[] hammingWord) {
        int indexValue = 0;
        boolean corectness = true;
        ArrayList<String> binaryIndexes = new ArrayList<>();

        for (int i = hammingWord.length - 1; i >= 0; i--) {
            indexValue = getIndexValue(indexValue, binaryIndexes, hammingWord, i);
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

    public static void injectTheMistakesRandom(Integer[] hammingWord) {
        Random randomIndex = new Random();

        for (int i = 0; i < 3; i++) {
            int random = randomIndex.nextInt(hammingWord.length);
            boolean b = (hammingWord[random] != 0);
            Integer negate = (!b) ? 1 : 0;
            hammingWord[random] = negate;
        }
    }

    public static void injectTheMistakes(Integer[] hammingWord, String position) {
        String[] num = position.split(" ");
        ArrayList<Integer> numList=new ArrayList<>();
        for(String str:num){
            numList.add(Integer.parseInt(str));
        }

        for (int i = 0; i < numList.size(); i++) {
            if (numList.get(i)>hammingWord.length) {
                numList.remove(i);
                --i;

            }
        }

        for (int i = 0; i < numList.size(); i++) {
            if (hammingWord[hammingWord.length - numList.get(i)] == 0) {
                hammingWord[hammingWord.length - numList.get(i)] = 1;
            } else {
                hammingWord[hammingWord.length - numList.get(i)] = 0;
            }
        }
    }

    public static String mistakePostion(Integer[] tab, Integer[] tab2) {
        String position = "";
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] != tab2[i]) {
                position += tab.length - i + " ";
            }
        }
        position = position.substring(0, position.length() - 1);
        return position;
    }

    public static Integer[] recoverCorrectCode(Integer[] tab, String position) {
        String[] num = position.split(" ");
        ArrayList<Integer> numList=new ArrayList<>();
        for(String str:num){
            numList.add(Integer.parseInt(str));
        }

        for (int i = 0; i < numList.size(); i++) {
            if (tab[tab.length - numList.get(i)] == 0) {
                tab[tab.length - numList.get(i)] = 1;
            } else {
                tab[tab.length - numList.get(i)] = 0;
            }
        }

        return tab;
    }

    public static String tableOfIntegerToString(Integer[] tab) {
        String hammingWord = "";
        for (Integer x : tab) {
            hammingWord += x;
        }
        return hammingWord;
    }
}
