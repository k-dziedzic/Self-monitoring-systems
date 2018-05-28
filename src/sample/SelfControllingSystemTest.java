package sample;

import java.util.List;
import java.util.Scanner;


public class SelfControllingSystemTest  {

    public static void injectMistakesAndSave(Integer[] hammingWord) {
        System.out.println();
        String path = "raport.txt";
        int save;
        int choice;
        boolean result = false;
        Scanner scannerChoice = new Scanner(System.in);
        Scanner scannerSave = new Scanner(System.in);
        System.out.println("Jesli chcesz wstrzyknac bledy wcisnij 1, jesli nie, wcisnij 2");
        choice = scannerChoice.nextInt();
        if (choice == 1) {
            HammingErrorDetection.injectTheMistakes(hammingWord);
            result = HammingErrorDetection.checkHammingCorectness(hammingWord);
            System.out.print("Slowo Hamminga z bledami: ");
            for (Integer x : hammingWord) {
                System.out.print(x);
            }
            System.out.println();
            System.out.println("Sprawdzenie poprawnosci transmisji: " + result);
        } else if (choice == 2) {
            result = HammingErrorDetection.checkHammingCorectness(hammingWord);
            System.out.println("Sprawdzenie poprawnosci transmisji: " + result);
        } else {
            System.out.println("Wcisnales zly przycisk, powrot do glownego menu");
        }
        System.out.println("Jesli chcesz sporzadzic raport w pliku txt wcisnij 1, jesli nie, wcisnij 2");
        save = scannerSave.nextInt();
        if (save == 1) {
            FileReader.saveResult(hammingWord, path, result);
            System.out.println("Plik zostal zapisany");
        } else if (save == 2) {
            System.out.println("Powrot do glownego menu");
        } else {
            System.out.println("Wcisnales zly przycisk, powrot do glownego menu");
        }
    }

    public static void systemTest() {
        int choice;

        while (true) {
            System.out.println("Jesli chcesz wczytac slowo z pliku, wcisnij 1, jesli wpisac recznie, wcisnij 2: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    String path;
                    int counter = 0;
                    int wordFromFileC = 0;
                    System.out.println("Podaj sciezke pliku: ");
                    Scanner scanner1 = new Scanner(System.in);
                    Scanner scannerC = new Scanner(System.in);
                    path = scanner1.nextLine();

                    List<String> wordsFromFile = FileReader.loadFile(path);
                    System.out.println("Wczytane słowa");
                    for (String x : wordsFromFile) {
                        System.out.println(counter + 1 + ": " + x);
                        counter++;
                    }
                    System.out.println("Wybierz slowo do przetworzenia na slowo Hamminga: ");
                    wordFromFileC = scannerC.nextInt();
                    Integer tab[] = HammingErrorDetection.createHammingWord(wordsFromFile.get(wordFromFileC - 1));
                    if (tab == null) {
                        System.out.println("Podane slowo zawiera niepoprawne znaki");
                    } else {
                        System.out.print("Utworzone słowo Hamminga:");
                        for (Integer x : tab) {
                            System.out.print(x);
                        }
                    }
                    injectMistakesAndSave(tab);

                    break;
                case 2:
                    String word;
                    System.out.println("Podaj slowo: ");
                    Scanner scanner2 = new Scanner(System.in);
                    word = scanner2.nextLine();
                    Integer tab1[] = HammingErrorDetection.createHammingWord(word);
                    if (tab1 == null) {
                        System.out.println("Podane slowo zawiera niepoprawne znaki");
                    } else {
                        System.out.print("Utworzone słowo Hamminga:");
                        for (Integer x : tab1) {
                            System.out.print(x);
                        }
                    }
                    injectMistakesAndSave(tab1);
                    break;
                default:
                    System.out.println("Wcisnales zly przycisk, zostaniesz przekierowany do menu glownego");
            }

        }
    }
}
