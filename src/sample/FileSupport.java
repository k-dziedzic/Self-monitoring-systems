package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileSupport {
    public static List<String> readFile(String path) {
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

    public static void createRaport(Integer[] hammingWord, String path, boolean result, String position) {
        Date date = new Date();
        try {
            PrintWriter printWriter = new PrintWriter(path);

            printWriter.println("Plik zostal wygenerowany :" + date.toString()+".");
            printWriter.print("Utworzone słowo Hamminga to: ");
            for (int i = hammingWord.length-1; i >=0; i--) {
                printWriter.print(hammingWord[i]);
            }
            printWriter.print(".");
            printWriter.println();
            if (result) {
                printWriter.println("Po przeprowadzeniu testu transmisji, nie wykryto przeklaman.");
            } else {
                printWriter.println("Po przeprowadzeniu testu transmisji, zostaly wykryte przeklamania.");
                printWriter.println("Błędy wystąpiły na pozycjach: "+position+".");
                printWriter.println("Błędy zostały naprawione.");
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
