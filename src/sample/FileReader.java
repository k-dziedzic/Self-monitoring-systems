package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public static List<String> loadFile(String path) {
        Scanner s = null;
        try {
            s = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> list = new ArrayList<>();
        while (s.hasNextLine()) {
            list.add(s.nextLine());
        }
        s.close();
        return list;
    }

    public static void saveResult(Integer[] hammingWord, String path, boolean result) {
        Date date = new Date();
        try {
            PrintWriter printWriter = new PrintWriter(path);

            printWriter.println("Plik zostal wygenerowany :" + date.toString());
            printWriter.print("Utworzone słowo Hamminga to: ");
            for (int i = 0; i < hammingWord.length; i++) {
                printWriter.print(hammingWord[i]);
            }
            printWriter.println();
            if (result) {
                printWriter.println("Po przeprowadzeniu testu transmisji, nie wykryto przeklaman");
            } else {
                printWriter.println("Po przeprowadzeniu testu transmisji, zostaly wykryte przeklamania");
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
