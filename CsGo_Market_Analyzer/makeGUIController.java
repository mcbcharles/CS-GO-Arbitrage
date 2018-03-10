package CsGo_Market_Analyzer;

import com.twilio.sdk.TwilioRestException;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by mb3752 on 2016-05-11.
 */

public class makeGUIController {
    public Button minPriceQues, maxPriceQues, minProfitEmailLimitQues, minSoldPerWeekLimitQues, usernameQues,
            passwordQues, receivingEmailQues, phoneNumberQues, startButton, testEmail, testPhoneNumber, optionsButton,
            mainMenu, applySettings, logFilePathQues, profitableItemsPathQues, siteURLQues, startPageNumQues;
    public TextField minimumPrice, maximumPrice, minProfitEmailLimit, minSoldPerWeekLimit, username, password,
            receivingEmail, phoneNumber, logFilePath, siteURL, profitableItemsPath, startPageNum;
    public Label help, community, webAPI, options;
    public CheckBox noEmail, noTexting;
    private Media sound = new Media(new File("src\\Resources\\Sound Effects\\button_hover_sound_effect.mp3").toURI().toString());
    private String[] makeGuiStringArray = new String[9];
    private TextField[] mainMenuTextFieldArray = new TextField[8];
    private Node[] mainMenuArray = new Node[19];
    private Node[] optionsMenuArray = new Node[10];
    private startGetSourceCode SGSC = new startGetSourceCode();
    private getSourceCode GSC = new getSourceCode();
    private sendEmails SE = new sendEmails();
    private sendTexts ST = new sendTexts();
    private logFileMakerWriter log = new logFileMakerWriter();
    private iniDataGetterAndSetter ini = new iniDataGetterAndSetter();


    private void populateArrays(){
        /**This method populates button array, mainMenuTextFieldArray, and label_Array so that methods fontSetup and checkFilledOut
         * can be much shorter.*/
        mainMenuTextFieldArray[0] = minimumPrice;
        mainMenuTextFieldArray[1] = maximumPrice;
        mainMenuTextFieldArray[2] = minProfitEmailLimit;
        mainMenuTextFieldArray[3] = minSoldPerWeekLimit;
        mainMenuTextFieldArray[4] = username;
        mainMenuTextFieldArray[5] = password;
        mainMenuTextFieldArray[6] = receivingEmail;
        mainMenuTextFieldArray[7] = phoneNumber;
        mainMenuArray[0] = minPriceQues;
        mainMenuArray[1] = maxPriceQues;
        mainMenuArray[2] = minProfitEmailLimitQues;
        mainMenuArray[3] = minSoldPerWeekLimitQues;
        mainMenuArray[4] = usernameQues;
        mainMenuArray[5] = passwordQues;
        mainMenuArray[6] = receivingEmailQues;
        mainMenuArray[7] = phoneNumberQues;
        mainMenuArray[8] = testEmail;
        mainMenuArray[9] = testPhoneNumber;
        mainMenuArray[10] = optionsButton;
        mainMenuArray[11] = minimumPrice;
        mainMenuArray[12] = maximumPrice;
        mainMenuArray[13] = minProfitEmailLimit;
        mainMenuArray[14] = minSoldPerWeekLimit;
        mainMenuArray[15] = username;
        mainMenuArray[16] = password;
        mainMenuArray[17] = receivingEmail;
        mainMenuArray[18] = phoneNumber;
        optionsMenuArray[0] = applySettings;
        optionsMenuArray[1] = mainMenu;
        optionsMenuArray[2] = logFilePathQues;
        optionsMenuArray[3] = profitableItemsPathQues;
        optionsMenuArray[4] = siteURLQues;
        optionsMenuArray[5] = logFilePath;
        optionsMenuArray[6] = profitableItemsPath;
        optionsMenuArray[7] = siteURL;
        optionsMenuArray[8] = startPageNumQues;
        optionsMenuArray[9] = startPageNum;
    }

    void steamStatus() throws IOException, InterruptedException {
        /**This method calls a method from getSourceCode which opens a web page,*/
        String className = "steamStatus";
        int status = GSC.steamStatus();
        if (status == 1){
            log.setText(Level.INFO, getClass().toString(), className, "Everything is good with Steam");
        }else if (status == 2) {
            webAPI.setId("error");
            log.setText(Level.INFO, getClass().toString(), className, "There is an issue with the Steam web API");
            webAPI.setText("Web API error");
        }else if (status == 3) {
            community.setId("error");
            log.setText(Level.INFO, getClass().toString(), className, "There is an issue with the Steam community");
            community.setText("Steam community error");
        }else {
            community.setId("error");
            webAPI.setId("error");
            log.setText(Level.INFO, getClass().toString(), className, "There is an issue with the Steam community and the web API");
            webAPI.setText("Web API error");
            community.setText("Steam community error");
        }
    }

    public void hoverSound(){
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public void methodForTestEmail() throws IOException {
        makeGuiStringArray[0] = username.getText();
        makeGuiStringArray[1] = password.getText();
        if (SE.testEmail(makeGuiStringArray)){
            testEmail.setVisible(false);
            if (testPhoneNumber.isVisible()){
                help.setText("Please insert your phone number now");
                testPhoneNumber.setLayoutX(873);
                testPhoneNumber.setLayoutY(188);
            }else {
                startButton.setVisible(true);
            }
        }else {
            help.setText("Your email doesn't work");
        }
    }

    public void methodForTestPhoneNumber() throws TwilioRestException, IOException {
        makeGuiStringArray[6] = phoneNumber.getText();
        if (ST.testTexting(makeGuiStringArray)){
            testPhoneNumber.setVisible(false);
            if (testEmail.isVisible()){
                help.setText("Please sellDates your Email now");
                testEmail.setLayoutX(873);
                testEmail.setLayoutY(188);
            }else {
                startButton.setVisible(true);
            }
        }else{
            help.setText("Your phone number doesn't work");
        }
    }

    public void methodForQuestionButtons(){
        if (usernameQues.isHover()){
            help.setText("The email feature works by logging into your Gmail and sending an email from there. If you aren't" +
                    " comfortable with this feature there is a checkbox to disable it in the bottom right of the screen.");
        }if (passwordQues.isHover()){
            help.setText("The email feature works by logging into your Gmail and sending an email from there. If you aren't" +
                    " comfortable with this feature there is a checkbox to disable it in the bottom right of the screen.");
        }if (receivingEmailQues.isHover()){
            help.setText("This text field allows you to tell the program who will be receiving the profit notifications.");
        }if (phoneNumberQues.isHover()){
            help.setText("Inputting your phone number allows you to receive text notifications about profitable items.");
        }if (minPriceQues.isHover()){
            help.setText("This box allows you to choose a minimum price that you are willing to pay \n WARNING: Lowering this" +
                    " value isn't recommended unless you are willing to sift through hundreds of weapons worth only a few cents");
        }if (maxPriceQues.isHover()){
            help.setText("This puts a limit on the maximum profit that you are willing to pay.");
        }if (minSoldPerWeekLimitQues.isHover()){
            help.setText("This sets a limit for the minimum amount of times an item has sold in the past week. If you set a" +
                    " high value you will get less guns, but the ones you do get will sell much faster.");
        }if (minProfitEmailLimitQues.isHover()){
            help.setText("This puts a limit on the minimum profit that you want to receive email and text notifications for.If you aren't " +
                    "comfortable with this feature there is a checkbox to disable it in the bottom right of the screen.");
        }if (logFilePathQues.isHover()){
            help.setText("This sets where the log files will go. (Requires restart if changed)");
        }if (profitableItemsPathQues.isHover()){
            help.setText("This sets where the profitable items list will go.");
        }if (siteURLQues.isHover()){
            help.setText("This sets the URL that the program will get the steam market items from.");
        }if (startPageNumQues.isHover()){
            help.setText("This sets the page that the program will start on when running the program. This can be useful" +
                    " for skipping a lot of pages that are filled with items that are worth only a few cents or dollars.");
        }
    }

    public void methodFornoEmailAndTexting(){
        if (noTexting.isSelected() && noEmail.isSelected() || !testEmail.isVisible() && !testPhoneNumber.isVisible()){ //TODO: FIX THIS. Check one, uncheck it, and check the other one.
            testEmail.setVisible(false);
            testPhoneNumber.setVisible(false);
            startButton.setVisible(true);
        }else if (noTexting.isSelected()){
            testPhoneNumber.setVisible(false);
            testEmail.setLayoutX(873);
            testEmail.setLayoutY(188);
        }else if (noEmail.isSelected()){
            testEmail.setVisible(false);
            testPhoneNumber.setLayoutX(873);
            testPhoneNumber.setLayoutY(188);
        }
    }

    private boolean checkFilledOut(int choice){
        /**This method is called by methodForStartButton and it sets the ID of certain elements to either null or error.
         * If the ID is set to null then the color of the element is normal, but if the ID is set to error, then the element
         * turns red. An error message is then displayed telling the user that they need to fill out the red colored element*/
        populateArrays();
        boolean filledOut = true;
        if (choice == 2){
            if (phoneNumber.getText().isEmpty()){
                phoneNumber.setId("error");
                filledOut = false;
            }else {
                phoneNumber.setId(null);
            }
        }else if (choice == 3){
            if (receivingEmail.getText().isEmpty()){
                receivingEmail.setId("error");
                filledOut = false;
            }else {
                receivingEmail.setId(null);
            }
        }else if (choice == 4){
            for (int i = 4; i <= 7; i++){
                if (mainMenuTextFieldArray[i].getText().isEmpty()){
                    mainMenuTextFieldArray[i].setId("error");
                    filledOut = false;
                }else {
                    mainMenuTextFieldArray[i].setId(null);
                }
            }
        }
        for (int i = 0; i <= 3; i++){
            if (mainMenuTextFieldArray[i].getText().isEmpty()){
                mainMenuTextFieldArray[i].setId("error");
                filledOut = false;
            }else {
                mainMenuTextFieldArray[i].setId(null);
            }
        }
        return filledOut;
    }

    public void optionsMenu() throws IOException {
        populateArrays();
        for (Node aNodeArray : mainMenuArray){
            aNodeArray.setVisible(false);
        }
        for (Node aNodeArray : optionsMenuArray){
            aNodeArray.setVisible(true);
        }
        logFilePath.setText(ini.dataGetter("paths", "logFilePath"));
        profitableItemsPath.setText(ini.dataGetter("paths", "profitableItemsPath"));
        siteURL.setText(ini.dataGetter("link", "link"));
        startPageNum.setText(ini.dataGetter("price", "startNum"));
    }

    public void mainMenu(){
        populateArrays();
        for (Node aNodeArray : mainMenuArray){
            aNodeArray.setVisible(true);
        }
        for (Node aNodeArray : optionsMenuArray){
            aNodeArray.setVisible(false);
        }
    }

    public void applySettings() throws IOException {
        boolean visible = true;
        if (!logFilePath.getText().isEmpty()){
            if (testPaths(logFilePath.getText())){
                ini.dataSetter("paths", "logFilePath", logFilePath.getText());
                logFilePath.setId(null);
            }else {
                help.setText("Please insert a correct path for the log file path");
                logFilePath.setId("error");
                visible = false;
            }
        }else {
            help.setText("Please insert a path for the log file path");
            logFilePath.setId("error");
            visible = false;
        }
        if (!profitableItemsPath.getText().isEmpty()){
            if (testPaths(profitableItemsPath.getText())){
                ini.dataSetter("paths", "profitableItemsPath", profitableItemsPath.getText());
                profitableItemsPath.setId(null);
            }else {
                help.setText("Please insert a correct path for the profitable items file path");
                profitableItemsPath.setId("error");
                visible = false;
            }
        }else {
            help.setText("Please insert a path for the profitable items path");
            profitableItemsPath.setId("error");
            visible = false;
        }
        if (!siteURL.getText().isEmpty()){
            WebDriver driver = new FirefoxDriver();
            try {
                driver.navigate().to(siteURL.getText());
                driver.findElement(By.xpath("//div[@id = 'result_0']")).getText();
                driver.close();
                siteURL.setId(null);
            }catch (Exception e){
                help.setText("The link you provided for the site URL is not working. Please make sure that it goes" +
                        " to the right page and that you have an internet connection");
                siteURL.setId("error");
                visible = false;
                driver.close();
            }
        }else {
            siteURL.setId("error");
            help.setText("Please insert a URL for the site URL");
            visible = false;
        }
        mainMenu.setVisible(visible);

    }

    private boolean testPaths(String path){
        File file = new File(path);
        return file.isDirectory();
    }

    public void methodForStartButton() {
        boolean everythingWorks = false;
        makeGuiStringArray[0] = username.getText();
        makeGuiStringArray[1] = password.getText();
        makeGuiStringArray[2] = receivingEmail.getText();
        makeGuiStringArray[3] = minimumPrice.getText();
        makeGuiStringArray[4] = maximumPrice.getText();
        makeGuiStringArray[5] = minProfitEmailLimit.getText();
        makeGuiStringArray[6] = phoneNumber.getText();
        makeGuiStringArray[7] = minSoldPerWeekLimit.getText();
        if (noEmail.isSelected() && noTexting.isSelected()){
            if (checkFilledOut(1)){
                makeGuiStringArray[0] = "skip";
                makeGuiStringArray[6] = "skip";
                everythingWorks = true;
            }
        }else if (noEmail.isSelected()){
            if (checkFilledOut(2)){
                makeGuiStringArray[0] = "skip";
                everythingWorks = true;
            }
        }else if (noTexting.isSelected()){
            if (checkFilledOut(3)){
                makeGuiStringArray[6] = "skip";
                everythingWorks = true;
            }
        }else {
            if (checkFilledOut(4)) {
                everythingWorks = true;
            }
        }

        if (everythingWorks){
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }else {
            help.setText("Please fill in the text field(s) shaded red");
        }
    }//end of methodForStartButton

    private final Task task = new Task<Void>() {
        @Override public Void call() throws Exception {
            log.makeLogFile();
            SGSC.starter(makeGuiStringArray);
            return null;
        }
    };
}