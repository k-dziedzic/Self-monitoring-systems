package sample;


import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.image.*;

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
    public ImageView imageView1;
    public ImageView imageView2;
    public Label label3;
    public Label label4;
    public ListView listView;


    private List<String> wordsFromFile;
    private Integer[] tab;
    private static final String PATH = "raport.txt";
    private boolean result = false;
    private File selectedFile;

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
        selectedFile = fileChooser.showOpenDialog(null);
        listView.getItems().clear();

        if (selectedFile != null) {
            wordsFromFile = FileReader.loadFile(selectedFile.getAbsolutePath());
            viewList();
            for(int i=0; i<wordsFromFile.size();i++){
                listView.getItems().add(wordsFromFile.get(i));
            }
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
        if(((radioButton1.isSelected() && selectedFile != null && listView.getSelectionModel().getSelectedItem()!=null) || textField1.getText().matches("[01]+")) && (textField2.getText().isEmpty() || textField2.getText().matches("[0-99 ]+"))) {
            if (radioButton1.isSelected() && textField2.getText().isEmpty() && !radioButton2.isSelected())
            {
                tab = createHammingWord(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                result = checkHammingCorectness(tab);
                textField3.setText(tableOfIntegerToString(tab));
                textField4.setText("brak błędu");
                textField5.setText("brak błędu");
                textField6.setText("brak błędu");
                textField7.setText(String.valueOf(listView.getSelectionModel().getSelectedItem()));
            }
            else if (radioButton1.isSelected() && radioButton2.isSelected())
            {
                System.out.println("2");
                tab = createHammingWord(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                textField3.setText(tableOfIntegerToString(tab));
                injectTheMistakesRandom(tab);
                result = checkHammingCorectness(tab);
                textField4.setText(tableOfIntegerToString(tab));
                String mistakePosition = mistakePostion(tab, createHammingWord(String.valueOf(listView.getSelectionModel().getSelectedItem())));
                textField5.setText(mistakePosition);
                textField6.setText(tableOfIntegerToString(recoverCorrectCode(tab, mistakePosition)));
                textField7.setText(String.valueOf(listView.getSelectionModel().getSelectedItem()));
            }
            else if (radioButton1.isSelected() && textField2.getText().matches("[0-99 ]+"))
            {
                System.out.println("3");
                tab = createHammingWord(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                String mistakes = textField2.getText();
                textField3.setText(tableOfIntegerToString(tab));
                injectTheMistakes(tab, mistakes);
                result = checkHammingCorectness(tab);
                textField4.setText(tableOfIntegerToString(tab));
                String mistakePosition = mistakePostion(tab, createHammingWord(String.valueOf(listView.getSelectionModel().getSelectedItem())));
                textField5.setText(mistakePosition);
                textField6.setText(tableOfIntegerToString(recoverCorrectCode(tab, mistakePosition)));
                textField7.setText(String.valueOf(listView.getSelectionModel().getSelectedItem()));
            }

            else if (!textField1.getText().isEmpty() && textField2.getText().isEmpty() && !radioButton2.isSelected()) {
                System.out.println("4");
                tab = createHammingWord(textField1.getText());
                result = checkHammingCorectness(tab);
                textField3.setText(tableOfIntegerToString(tab));
                textField4.setText("brak błędu");
                textField5.setText("brak błędu");
                textField6.setText("brak błędu");
                textField7.setText(textField1.getText());
            }   else if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty()) {
                System.out.println("5");
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
                System.out.println("6");
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

            transfer();
        }
        else if(!radioButton1.isSelected() && textField1.getText().isEmpty()){
            System.out.println(!radioButton1.isSelected());
            System.out.println(textField1.getText().isEmpty());

            Alert alert = new Alert(Alert.AlertType.ERROR, "Wpisz kod binarny lub wybierz go z pliku,\naby wysłać dane.", ButtonType.OK);
            textField1.setStyle("-fx-border-color: red;");
            radioButton1.setStyle("-fx-border-color: red");
            button1.setStyle("-fx-border-color: null;");
            listView.setStyle("-fx-border-color: null;");
            alert.showAndWait();
        }
        else if(!radioButton1.isSelected() && !textField1.getText().matches("[01]+")){
            radioButton1.setStyle("-fx-border-color: null");
            button1.setStyle("-fx-border-color: null;");
            listView.setStyle("-fx-border-color: null;");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wpisz poprawny kod binarny (wartości 0 lub 1).", ButtonType.OK);
            textField1.setStyle("-fx-border-color: red;");
            alert.showAndWait();
        }
        else if(radioButton1.isSelected() && listView.getSelectionModel().getSelectedItem()==null){
            radioButton1.setStyle("-fx-border-color: null");
            textField1.setStyle("-fx-border-color: null;");
            button1.setStyle("-fx-border-color: null;");
            listView.setStyle("-fx-border-color: red;");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wybierz wartość z listy.", ButtonType.OK);
            alert.showAndWait();
        }
        else if(!textField2.getText().matches("[0-99 ]+")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wpisz miejsca błędów oddzielając je spacją\nnp. \"3 4 5\".", ButtonType.OK);
            alert.showAndWait();
        }
        else{
            textField1.setStyle("-fx-border-color: null;");
            radioButton1.setStyle("-fx-border-color: null");
            listView.setStyle("-fx-border-color: null;");
            button1.setStyle("-fx-border-color: red;");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie wybrałeś żadnego pliku.", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void save() {
        FileReader.saveResult(tab, PATH, result);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Raport został zapisany do pliku", ButtonType.OK);
        alert.showAndWait();
    }

    public void transfer(){
        listView.setStyle("-fx-border-color: null;");
        textField1.setStyle("-fx-border-color: null;");
        radioButton1.setStyle("-fx-border-color: null");
        button1.setStyle("-fx-border-color: null;");
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
        label3.setVisible(false);
        label4.setVisible(false);
        listView.setVisible(false);

        label2.setVisible(true);
        imageView1.setVisible(true);
        imageView2.setVisible(true);
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
            imageView1.setVisible(false);
            imageView2.setVisible(false);
            progressBar.setVisible(false);
        });
        delay.play();
    }

    public void viewList(){
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

        label3.setVisible(true);
        label4.setVisible(true);
        listView.setVisible(true);
    }
}
