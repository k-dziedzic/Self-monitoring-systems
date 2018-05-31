package sample;


import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

import static sample.HammingErrorDetection.*;

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
    public Text text1;
    public Text text2;
    public Text text3;
    public Text text4;
    public Text text5;
    public Label label1;
    public Label label2;
    public ProgressBar progressBar;

    private List<String> wordsFromFile;
    private Integer[] tab;
    private static final String PATH = "raport.txt";
    private boolean result = false;

    public void fromFile() {
        if (radioButton1.isSelected()) {
            button1.setDisable(false);
            textField1.setDisable(true);
        } else {
            button1.setDisable(true);
            textField1.setDisable(false);
        }
    }

    public void findFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("../Self-monitoring systems"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            wordsFromFile = FileReader.loadFile(selectedFile.getAbsolutePath());
        }
    }


    public void randomErrorPosition() {
        if (radioButton2.isSelected()) {
            textField2.setDisable(true);
        } else {
            textField2.setDisable(false);
        }
    }

    public void check() {
        if(radioButton1.isSelected() || !textField2.getText().isEmpty()) {
            textField3.setVisible(false);
            textField4.setVisible(false);
            textField5.setVisible(false);
            textField6.setVisible(false);
            textField7.setVisible(false);
            text1.setVisible(false);
            text2.setVisible(false);
            text3.setVisible(false);
            text4.setVisible(false);
            text5.setVisible(false);
            label1.setVisible(false);
            label2.setVisible(true);
            progressBar.setVisible(true);


            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                    new KeyFrame(Duration.seconds(5), e -> {
                    }, new KeyValue(progressBar.progressProperty(), 1))
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.setCycleCount(1);
            timeline.play();

            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> {
                textField3.setVisible(true);
                textField4.setVisible(true);
                textField5.setVisible(true);
                textField6.setVisible(true);
                textField7.setVisible(true);
                text1.setVisible(true);
                text2.setVisible(true);
                text3.setVisible(true);
                text4.setVisible(true);
                text5.setVisible(true);
                label1.setVisible(true);
                label2.setVisible(false);
                progressBar.setVisible(false);
            });
            delay.play();

            if (radioButton1.isSelected() && textField2.getText().isEmpty() && !radioButton2.isSelected()) {
                tab = createHammingWord(wordsFromFile.get(0));
                result = checkHammingCorectness(tab);
                textField3.setText(tableOfIntegerToString(tab));
                textField4.setText("brak błędu");
                textField5.setText("brak błędu");
                textField6.setText("brak błędu");
                textField7.setText(wordsFromFile.get(0));
            } else if (!textField1.getText().isEmpty() && textField2.getText().isEmpty() && !radioButton2.isSelected()) {
                tab = createHammingWord(textField1.getText());
                result = checkHammingCorectness(tab);
                textField3.setText(tableOfIntegerToString(tab));
                textField4.setText("brak błędu");
                textField5.setText("brak błędu");
                textField6.setText("brak błędu");
                textField7.setText(textField1.getText());
            } else if (radioButton1.isSelected() && radioButton2.isSelected()) {
                tab = createHammingWord(wordsFromFile.get(0));
                textField3.setText(tableOfIntegerToString(tab));
                injectTheMistakesRandom(tab);
                result = checkHammingCorectness(tab);
                textField4.setText(tableOfIntegerToString(tab));
                String mistakePosition = mistakePostion(tab, createHammingWord(wordsFromFile.get(0)));
                textField5.setText(mistakePosition);
                textField6.setText(tableOfIntegerToString(recoverCorrectCode(tab, mistakePosition)));
                textField7.setText(wordsFromFile.get(0));
            } else if (radioButton1.isSelected() && !textField2.getText().isEmpty()) {
                tab = createHammingWord(wordsFromFile.get(0));
                String mistakes = textField2.getText();
                textField3.setText(tableOfIntegerToString(tab));
                injectTheMistakes(tab, mistakes);
                result = checkHammingCorectness(tab);
                textField4.setText(tableOfIntegerToString(tab));
                String mistakePosition = mistakePostion(tab, createHammingWord(wordsFromFile.get(0)));
                textField5.setText(mistakePosition);
                textField6.setText(tableOfIntegerToString(recoverCorrectCode(tab, mistakePosition)));
                textField7.setText(wordsFromFile.get(0));
            } else if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
                tab = createHammingWord(textField1.getText());
                String mistakes = textField2.getText();
                textField3.setText(tableOfIntegerToString(tab));
                injectTheMistakes(tab, mistakes);
                result = checkHammingCorectness(tab);
                textField4.setText(tableOfIntegerToString(tab));
                String mistakePosition = mistakePostion(tab, createHammingWord(textField1.getText()));
                textField5.setText(mistakePosition);
                textField6.setText(tableOfIntegerToString(recoverCorrectCode(tab, mistakePosition)));
                textField7.setText(textField1.getText());
            } else if (!textField1.getText().isEmpty() && radioButton2.isSelected()) {
                tab = createHammingWord(textField1.getText());
                textField3.setText(tableOfIntegerToString(tab));
                injectTheMistakesRandom(tab);
                result = checkHammingCorectness(tab);
                textField4.setText(tableOfIntegerToString(tab));
                String mistakePosition = mistakePostion(tab, createHammingWord(textField1.getText()));
                textField5.setText(mistakePosition);
                textField6.setText(tableOfIntegerToString(recoverCorrectCode(tab, mistakePosition)));
                textField7.setText(textField1.getText());
            }
        }

        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wpisz kod binarny lub wybierz go z pliku,\naby wysłać dane.", ButtonType.OK);
            alert.showAndWait();
        }



    }

    public void save() {
        FileReader.saveResult(tab, PATH, result);
    }
}
