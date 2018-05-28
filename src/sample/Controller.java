package sample;


import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class Controller {

    public RadioButton radioButton1;
    public RadioButton radioButton2;
    public Button button1;
    public Button button2;
    public Button button3;
    public TextField textField1;
    public TextField textField2;
    public TextField textField3;
    public TextField textField4;
    public TextField textField5;
    public TextField textField6;
    public TextField textField7;

    private List<String> wordsFromFile;
    private Integer []tab;
    private static final String PATH="raport.txt";
    private boolean result = false;

    public void fromFile(){
        if(radioButton1.isSelected()){
            button1.setDisable(false);
            textField1.setDisable(true);
        }
        else{
            button1.setDisable(true);
            textField1.setDisable(false);
        }
    }

    public void findFile(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File("E:/IdeaWorkspace/Self-monitoring systems"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT files","*.txt"));
        File selectedFile=fileChooser.showOpenDialog(null);

        if(selectedFile!=null){
            int counter = 0;
            wordsFromFile = FileReader.loadFile(selectedFile.getAbsolutePath());
            System.out.println("Wczytane słowa");
            for (String x : wordsFromFile) {
                System.out.println(counter + 1 + ": " + x);
                counter++;
            }
        }
        else{
            System.out.println("File is not valid");
        }
    }



    public void randomErrorPosition(){
        if(radioButton2.isSelected()){
            textField2.setDisable(true);
        }
        else{
            textField2.setDisable(false);
        }
    }

    public void check(){
        if(radioButton1.isSelected() && textField2.getText().isEmpty() && !radioButton2.isSelected()){
            tab = HammingErrorDetection.createHammingWord(wordsFromFile.get(0));
            result = HammingErrorDetection.checkHammingCorectness(tab);
            textField3.setText(tableOfIntegerToString(tab));
            textField4.setText("brak błędu");
            textField5.setText("brak błędu");
            textField6.setText("brak błędu");
            textField7.setText(wordsFromFile.get(0));
        }
        else if(!textField1.getText().isEmpty() && textField2.getText().isEmpty() && !radioButton2.isSelected()){
            tab = HammingErrorDetection.createHammingWord(textField1.getText());
            result = HammingErrorDetection.checkHammingCorectness(tab);
            textField3.setText(tableOfIntegerToString(tab));
            textField4.setText("brak błędu");
            textField5.setText("brak błędu");
            textField6.setText("brak błędu");
            textField7.setText(textField1.getText());
        }

        else if(radioButton1.isSelected() && radioButton2.isSelected()){
            tab = HammingErrorDetection.createHammingWord(wordsFromFile.get(0));
            textField3.setText(tableOfIntegerToString(tab));
            HammingErrorDetection.injectTheMistakesRandom(tab);
            result = HammingErrorDetection.checkHammingCorectness(tab);
            textField4.setText(tableOfIntegerToString(tab));
            String mistakePosition=HammingErrorDetection.mistakePostion(tab,HammingErrorDetection.createHammingWord(wordsFromFile.get(0)));
            textField5.setText(mistakePosition);
            textField6.setText(tableOfIntegerToString(HammingErrorDetection.recoverCorrectCode(tab,mistakePosition)));
            textField7.setText(wordsFromFile.get(0));
        }

        else if(radioButton1.isSelected() && !textField2.getText().isEmpty()){
            tab = HammingErrorDetection.createHammingWord(wordsFromFile.get(0));
            String mistakes=textField2.getText();
            textField3.setText(tableOfIntegerToString(tab));
            HammingErrorDetection.injectTheMistakes(tab,mistakes);
            result = HammingErrorDetection.checkHammingCorectness(tab);
            textField4.setText(tableOfIntegerToString(tab));
            String mistakePosition=HammingErrorDetection.mistakePostion(tab,HammingErrorDetection.createHammingWord(wordsFromFile.get(0)));
            textField5.setText(mistakePosition);
            textField6.setText(tableOfIntegerToString(HammingErrorDetection.recoverCorrectCode(tab,mistakePosition)));
            textField7.setText(wordsFromFile.get(0));
        }

        else if(!textField1.getText().isEmpty() && !textField2.getText().isEmpty()){
            tab = HammingErrorDetection.createHammingWord(textField1.getText());
            String mistakes=textField2.getText();
            textField3.setText(tableOfIntegerToString(tab));
            HammingErrorDetection.injectTheMistakes(tab,mistakes);
            result = HammingErrorDetection.checkHammingCorectness(tab);
            textField4.setText(tableOfIntegerToString(tab));
            String mistakePosition=HammingErrorDetection.mistakePostion(tab,HammingErrorDetection.createHammingWord(textField1.getText()));
            textField5.setText(mistakePosition);
            textField6.setText(tableOfIntegerToString(HammingErrorDetection.recoverCorrectCode(tab,mistakePosition)));
            textField7.setText(textField1.getText());
        }

        else if(!textField1.getText().isEmpty() && radioButton2.isSelected()){
            tab = HammingErrorDetection.createHammingWord(textField1.getText());
            textField3.setText(tableOfIntegerToString(tab));
            HammingErrorDetection.injectTheMistakesRandom(tab);
            result = HammingErrorDetection.checkHammingCorectness(tab);
            textField4.setText(tableOfIntegerToString(tab));
            String mistakePosition=HammingErrorDetection.mistakePostion(tab,HammingErrorDetection.createHammingWord(textField1.getText()));
            textField5.setText(mistakePosition);
            textField6.setText(tableOfIntegerToString(HammingErrorDetection.recoverCorrectCode(tab,mistakePosition)));
            textField7.setText(textField1.getText());
        }


    }

    public void save(){
        FileReader.saveResult(tab, PATH, result);
    }

    public String tableOfIntegerToString(Integer [] tab){
        String hammingWord="";
        for (Integer x : tab) {
            hammingWord+=x;
        }
        return hammingWord;
    }


}
