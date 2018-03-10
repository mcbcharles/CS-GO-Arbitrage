package CsGo_Market_Analyzer;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by Matthew on 2016-04-09.
 * This class makes the GUI for the program
 */

public class makeGUI extends Application{
    @FXML private final Button minPriceQues = new Button();
    @FXML private final Button maxPriceQues = new Button();
    @FXML private final Button minProfitEmailLimitQues = new Button();
    @FXML private final Button minSoldPerWeekLimitQues  = new Button();
    @FXML private final Button usernameQues = new Button();
    @FXML private final Button passwordQues = new Button();
    @FXML private final Button receivingEmailQues = new Button();
    @FXML private final Button phoneNumberQues = new Button();
    @FXML private final Button startButton = new Button();
    @FXML private final Button testEmail = new Button();
    @FXML private final TextField minimumPrice = new TextField();
    @FXML private final TextField maximumPrice = new TextField();
    @FXML private final TextField minProfitEmailLimit = new TextField();
    @FXML private final TextField minSoldPerWeekLimit = new TextField();
    @FXML private final TextField username = new TextField();
    @FXML private final TextField password = new TextField();
    @FXML private final TextField receivingEmail = new TextField();
    @FXML private final TextField phoneNumber = new TextField();
    @FXML private final Label help = new Label();
    @FXML private final Label community = new Label();
    @FXML private final Label webAPI = new Label();
    @FXML private final Label options = new Label();
    private final Button[] buttonsArray = new Button[10];
    private final TextField[] textFieldArray = new TextField[8];
    private final Label[] labelArray = new Label[4];

    public void start(final Stage primaryStage) throws InterruptedException, IOException {
        /**This method starts the program; loading an FXML and CSS formatter, and calls three other methods (populateArrays,
         * * fontSetup, and steamStatus).*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
        Parent root = loader.load();
        makeGUIController C = loader.getController();
        populateArrays();
        fontSetup();
        Scene thisScene;
        primaryStage.setTitle("CsGo: Market Analyzer");
        thisScene = new Scene(root, 1014, 636);
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(thisScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        C.steamStatus();
    }//end of start


    private void populateArrays() {
        /**This method populates button array, textFieldArray, and label_Array so that methods fontSetup and checkFilledOut
         * can be much shorter.*/
        buttonsArray[0] = minPriceQues;
        buttonsArray[1] = maxPriceQues;
        buttonsArray[2] = minProfitEmailLimitQues;
        buttonsArray[3] = minSoldPerWeekLimitQues;
        buttonsArray[4] = usernameQues;
        buttonsArray[5] = passwordQues;
        buttonsArray[6] = receivingEmailQues;
        buttonsArray[7] = phoneNumberQues;
        buttonsArray[8] = startButton;
        buttonsArray[9] = testEmail;
        textFieldArray[0] = minimumPrice;
        textFieldArray[1] = maximumPrice;
        textFieldArray[2] = minProfitEmailLimit;
        textFieldArray[3] = minSoldPerWeekLimit;
        textFieldArray[4] = username;
        textFieldArray[5] = password;
        textFieldArray[6] = receivingEmail;
        textFieldArray[7] = phoneNumber;
        labelArray[0] = help;
        labelArray[1] = community;
        labelArray[2] = webAPI;
        labelArray[3] = options;
    }

    private void fontSetup() {
        /**This method sets the font of all the elements using for loops and the arrays from populateArrays.*/
        try
        {
            Font f = Font.loadFont(new FileInputStream(new File("src\\Resources\\Fonts\\Stratum2-Black.otf")), 18);
            for (Button aButtons_array : buttonsArray) {
                aButtons_array.setFont(f);
            }
            for (TextField aTextField_array : textFieldArray) {
                aTextField_array.setFont(f);
            }
            for (Label aLabel_array : labelArray) {
                aLabel_array.setFont(f);
            }
        }
        catch (Exception e) {
            System.out.print(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}//end of class