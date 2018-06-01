package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HammingAlgorithm {
    private static boolean isPowerOfTwo(int number) {
        boolean isPowerOfTwo = false;
        if (number == 1) {
            isPowerOfTwo = true;
        } else {
            while (number % 2 == 0) {
                number =number / 2;
                if (number == 1) {
                    isPowerOfTwo = true;
                    break;
                }
            }
        }
        return isPowerOfTwo;
    }

    public static Integer[] hammingBinaryCode(String binaryCode) {
        int hammingWordCounter = 0;
        int counter = 0;
        int length = 3;
        int indexValue = 0;

        List<Integer> controlPositionsIndexes = new ArrayList<>();
        List<String> informationBitsIndexes = new ArrayList<>();

        for (int i = 2; i <= binaryCode.length(); i++) {
            boolean isPowerOfTwo = isPowerOfTwo(length + 1);

            if (isPowerOfTwo) {
                length += 2;
            } else {
                length += 1;
            }
        }

        Integer[] hammingCode = new Integer[length];

        for (int i = length - 1; i >= 0; i--) {
            boolean isPowerOfTwo = isPowerOfTwo(i + 1);

            if (isPowerOfTwo) {
                controlPositionsIndexes.add(i);
                hammingCode[i] = null;
            } else {
                String temp = "" + binaryCode.charAt(counter);
                hammingCode[i] = Integer.parseInt(temp);
                counter++;
                indexValue = getIndexValue(indexValue, informationBitsIndexes, hammingCode, i);
            }
        }

        for (int j = 0; j < informationBitsIndexes.get(0).length(); j++) {
            int numberRowWithOneInColumn = 0;
            for (int i = 0; i < informationBitsIndexes.size(); i++) {
                if (informationBitsIndexes.get(i).charAt(j) == '1') {
                    numberRowWithOneInColumn++;
                }
            }

            if (numberRowWithOneInColumn % 2 == 0) {
                hammingCode[controlPositionsIndexes.get(hammingWordCounter++)] = 0;
            } else {
                hammingCode[controlPositionsIndexes.get(hammingWordCounter++)] = 1;
            }
        }

        Integer [] hc=new Integer[hammingCode.length];
        int j=0;
        for(int i=hammingCode.length-1;i>=0;i--){
            hc[j]=hammingCode[i];
            ++j;
        }
        return hc;
    }

    private static int getIndexValue(int indexValue, List<String> informationBitsIndexes, Integer[] hammingWord, int i) {
        if (hammingWord[i] == 1) {
            String tempBinaryIndex = Integer.toBinaryString(i + 1);
            informationBitsIndexes.add(tempBinaryIndex);


            for (int j = tempBinaryIndex.length(); j < informationBitsIndexes.get(0).length(); j++) {
                tempBinaryIndex = "0" + tempBinaryIndex;
                informationBitsIndexes.set(indexValue, tempBinaryIndex);
            }
            indexValue++;
        }
        return indexValue;
    }

    public static boolean isHammingCorrect(Integer[] hammingCode) {
        int indexValue = 0;
        boolean correctness = true;
        List<String> informationBitsIndexes = new ArrayList<>();
        
        for (int i = hammingCode.length - 1; i >= 0; i--) {
            indexValue = getIndexValue(indexValue, informationBitsIndexes, hammingCode, i);
        }

        for (int j = 0; j < informationBitsIndexes.get(0).length(); j++) {
            int numberRowWithOneInColumn = 0;
            for (int i = 0; i < informationBitsIndexes.size(); i++) {
                if (informationBitsIndexes.get(i).charAt(j) == '1') {
                    numberRowWithOneInColumn++;
                }
            }
            if (numberRowWithOneInColumn % 2 != 0) {
                correctness = false;
            }
        }
        return correctness;
    }

    public static void injectTheMistakesRandom(Integer[] hammingCode) {
        Random randomIndex = new Random();

        for (int i = 0; i < 3; i++) {
            int random = randomIndex.nextInt(hammingCode.length);
            boolean b = (hammingCode[random] != 0);
            Integer negate;
            if(!b){
                negate=1;
            }
            else{
                negate=0;
            }
            hammingCode[random] = negate;
        }
    }

    public static void injectTheMistakes(Integer[] hammingCode, String position) {
        String[] num = position.split(" +");
        List<Integer> numList=new ArrayList<>();
        boolean flag=false;
        for(String str:num){
            if(numList.size()==0){
                numList.add(Integer.parseInt(str));
            }
            else {
                for (int i = 0; i < numList.size(); i++) {
                    if (numList.get(i) == Integer.parseInt(str)) {
                        flag=true;
                    }
                }
                if(!flag) {
                    numList.add(Integer.parseInt(str));
                }
            }
        }


        for (int i = 0; i < numList.size(); i++) {
            if (numList.get(i)>hammingCode.length) {
                numList.remove(i);
                --i;
            }
        }


        for (int i = 0; i < numList.size(); i++) {
            if (hammingCode[hammingCode.length - numList.get(i)] == 0) {
                hammingCode[hammingCode.length - numList.get(i)] = 1;
            } else {
                hammingCode[hammingCode.length - numList.get(i)] = 0;
            }
        }
    }

    public static String mistakePostion(Integer[] tabWithMistakes, Integer[] tabWithoutMistakes) {
        String position = "";
        for (int i = 0; i < tabWithMistakes.length; i++) {
            if (tabWithMistakes[i] != tabWithoutMistakes[i]) {
                position += tabWithMistakes.length - i + " ";
            }
        }
        position = position.substring(0, position.length() - 1);
        return position;
    }

    public static Integer[] recoverCorrectCode(Integer[] tabWithMistakes, String position) {
        String[] num = position.split(" ");
        List<Integer> numList=new ArrayList<>();
        for(String str:num){
            numList.add(Integer.parseInt(str));
        }

        for (int i = 0; i < numList.size(); i++) {
            if (tabWithMistakes[tabWithMistakes.length - numList.get(i)] == 0) {
                tabWithMistakes[tabWithMistakes.length - numList.get(i)] = 1;
            } else {
                tabWithMistakes[tabWithMistakes.length - numList.get(i)] = 0;
            }
        }
        return tabWithMistakes;
    }

    public static String tableIntegerToString(Integer[] tab) {
        String hammingWord = "";
        for (Integer x : tab) {
            hammingWord += x;
        }
        return hammingWord;
    }
}
