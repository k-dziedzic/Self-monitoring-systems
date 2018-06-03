package sample;


import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.image.*;

import java.io.File;
import java.util.List;

import static sample.HammingAlgorithm.*;

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
    private Integer[] hammingCode;
    private static final String PATH = "raport.txt";
    private boolean result = true;
    private File selectedFile;
    private String mistakePosition;

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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT files", "*.txt"));
        selectedFile = fileChooser.showOpenDialog(null);
        listView.getItems().clear();

        if (selectedFile != null) {
            wordsFromFile = FileSupport.readFile(selectedFile.getAbsolutePath());
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
            for (int i = 0; i < wordsFromFile.size(); i++) {
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
        button3.setDisable(true);
        if ((radioButton1.isSelected() && selectedFile != null && listView.getSelectionModel().getSelectedItem() != null || !radioButton1.isSelected()) && ((textField1.getText().matches("[01]+")) || textField1.getText().isEmpty()) && (radioButton1.isSelected() || !textField1.getText().isEmpty()) && (textField2.getText().isEmpty() || textField2.getText().matches("^([0-9]*\\s+)*[0-9]*$") || radioButton2.isSelected())) {

            if (radioButton1.isSelected() && textField2.getText().isEmpty() && !radioButton2.isSelected()) {
                hammingCode = hammingBinaryCode(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                result = true;
                textField3.setText(tableIntegerToString(hammingCode));
                noErrors();
                textField7.setText(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                selectedFile = null;
            } else if (radioButton1.isSelected() && radioButton2.isSelected()) {
                hammingCode = hammingBinaryCode(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                textField3.setText(tableIntegerToString(hammingCode));
                injectTheMistakesRandom(hammingCode);
                result = isHammingCorrect(hammingCode);
                textField4.setText(tableIntegerToString(hammingCode));
                mistakePosition = mistakePostions(hammingCode, hammingBinaryCode(String.valueOf(listView.getSelectionModel().getSelectedItem())));
                textField5.setText(mistakePosition);
                if (mistakePosition.length() > 1) {
                    toMuchErrors();
                } else {
                    textField6.setText(tableIntegerToString(recoverCorrectCode(hammingCode, mistakePosition)));
                    textField7.setText(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                }
                selectedFile = null;
            } else if (radioButton1.isSelected() && textField2.getText().matches("^([0-9]*\\s+)*[0-9]*$")) {
                hammingCode = hammingBinaryCode(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                String mistakes = textField2.getText();
                textField3.setText(tableIntegerToString(hammingCode));
                injectTheMistakes(hammingCode, mistakes);
                result = isHammingCorrect(hammingCode);
                if (result == true) {
                    noErrors();
                } else {
                    textField4.setText(tableIntegerToString(hammingCode));
                    mistakePosition = mistakePostions(hammingCode, hammingBinaryCode(String.valueOf(listView.getSelectionModel().getSelectedItem())));
                    textField5.setText(mistakePosition);
                    if (mistakePosition.length() > 1) {
                        toMuchErrors();
                    } else {
                        textField6.setText(tableIntegerToString(recoverCorrectCode(hammingCode, mistakePosition)));
                        textField7.setText(String.valueOf(listView.getSelectionModel().getSelectedItem()));
                    }
                }
                selectedFile = null;
            } else if (!textField1.getText().isEmpty() && textField2.getText().isEmpty() && !radioButton2.isSelected()) {
                hammingCode = hammingBinaryCode(textField1.getText());
                result = true;
                textField3.setText(tableIntegerToString(hammingCode));
                noErrors();
                textField7.setText(textField1.getText());
            } else if (!textField1.getText().isEmpty() && radioButton2.isSelected()) {
                hammingCode = hammingBinaryCode(textField1.getText());
                textField3.setText(tableIntegerToString(hammingCode));
                injectTheMistakesRandom(hammingCode);
                result = isHammingCorrect(hammingCode);
                textField4.setText(tableIntegerToString(hammingCode));
                mistakePosition = mistakePostions(hammingCode, hammingBinaryCode(textField1.getText()));
                textField5.setText(mistakePosition);
                if (mistakePosition.length() > 1) {
                    toMuchErrors();
                } else {
                    textField6.setText(tableIntegerToString(recoverCorrectCode(hammingCode, mistakePosition)));
                    textField7.setText(textField1.getText());
                }
            } else if (!textField1.getText().isEmpty() && textField2.getText().matches("^([0-9]*\\s+)*[0-9]*$")) {
                hammingCode = hammingBinaryCode(textField1.getText());
                String mistakes = textField2.getText();
                textField3.setText(tableIntegerToString(hammingCode));
                injectTheMistakes(hammingCode, mistakes);
                result = isHammingCorrect(hammingCode);
                if (result == true) {
                    noErrors();
                } else {
                    textField4.setText(tableIntegerToString(hammingCode));
                    mistakePosition = mistakePostions(hammingCode, hammingBinaryCode(textField1.getText()));
                    textField5.setText(mistakePosition);
                    if (mistakePosition.length() > 1) {
                        toMuchErrors();
                    } else {

                        textField6.setText(tableIntegerToString(recoverCorrectCode(hammingCode, mistakePosition)));
                        textField7.setText(textField1.getText());
                    }
                }
            }

            listView.setStyle("-fx-border-color: null;");
            textField1.setStyle("-fx-border-color: null;");
            radioButton1.setStyle("-fx-border-color: null");
            button1.setStyle("-fx-border-color: null;");
            textField2.setStyle("-fx-border-color: null;");
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
                button3.setDisable(false);
            });
            delay.play();

        } else if (!radioButton1.isSelected() && textField1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wpisz kod binarny lub wybierz go z pliku,\naby wysłać dane.", ButtonType.OK);
            textField1.setStyle("-fx-border-color: red;");
            radioButton1.setStyle("-fx-border-color: red");
            button1.setStyle("-fx-border-color: null;");
            listView.setStyle("-fx-border-color: null;");
            textField2.setStyle("-fx-border-color: null;");
            alert.showAndWait();
        } else if (!radioButton1.isSelected() && !textField1.getText().matches("[01]+")) {
            radioButton1.setStyle("-fx-border-color: null");
            button1.setStyle("-fx-border-color: null;");
            listView.setStyle("-fx-border-color: null;");
            textField2.setStyle("-fx-border-color: null;");
            textField1.setStyle("-fx-border-color: red;");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wpisz poprawny kod binarny (wartości 0 lub 1).", ButtonType.OK);
            alert.showAndWait();
        } else if (radioButton1.isSelected() && selectedFile != null && listView.getSelectionModel().getSelectedItem() == null) {
            radioButton1.setStyle("-fx-border-color: null");
            textField1.setStyle("-fx-border-color: null;");
            button1.setStyle("-fx-border-color: null;");
            textField2.setStyle("-fx-border-color: null;");
            listView.setStyle("-fx-border-color: red;");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wybierz wartość z listy.", ButtonType.OK);
            alert.showAndWait();
        } else if (radioButton1.isSelected() && selectedFile == null) {
            textField1.setStyle("-fx-border-color: null;");
            radioButton1.setStyle("-fx-border-color: null");
            listView.setStyle("-fx-border-color: null;");
            textField2.setStyle("-fx-border-color: null;");
            button1.setStyle("-fx-border-color: red;");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie wybrałeś żadnego pliku.", ButtonType.OK);
            alert.showAndWait();
        } else if (radioButton1.isSelected() || !textField1.getText().isEmpty() && !textField2.getText().matches("[0-99 ]+")) {
            textField1.setStyle("-fx-border-color: null;");
            radioButton1.setStyle("-fx-border-color: null");
            listView.setStyle("-fx-border-color: null;");
            button1.setStyle("-fx-border-color: null;");
            textField2.setStyle("-fx-border-color: red;");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wpisz miejsca błędów oddzielając je spacją\nnp. \"3 4 5\".", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void save() {
        FileSupport.createRaport(hammingCode, PATH, result, mistakePosition);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Raport został zapisany do pliku.", ButtonType.OK);
        alert.showAndWait();
    }

    private void noErrors() {
        textField4.setText("Brak błędu.");
        textField5.setText("Brak błędu.");
        textField6.setText("Brak błędu.");
    }

    private void toMuchErrors() {
        textField6.setText("Zbyt dużo błędów.");
        textField7.setText("Nie można odwtorzyć wiadomości.");
    }
}
