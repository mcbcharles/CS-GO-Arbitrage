package CsGo_Market_Analyzer;

/**
 * This class gets the source code for each gun and determines whether the gun is profitable if resold.
 */

import com.twilio.sdk.TwilioRestException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by halas on 4/15/2016.
 */

class getSourceCode {

    private final int[] stopper = new int[10];//Int for stopping the getPrices method from running if the price is too low
    private double buyOrdersDouble = 0;
    private final double[] sellPrice = new double[10];
    private final String[] links = new String[10];//String array for storing the individual links of each gun (per page).
    private final String[] emailData = new String[2];//String array for storing the link to a profitable gun.
    private Formatter formatter;
    private final sendEmails SE = new sendEmails();//Creates an object out of the send_Emails class.
    private final sendTexts ST = new sendTexts();//Creates an object out of the send_Texts class.
    private final graphChecker GC = new graphChecker();
    private final logFileMakerWriter log = new logFileMakerWriter();
    private final checkLink checkLink = new checkLink();
    private final iniDataGetterAndSetter ini = new iniDataGetterAndSetter();

    void makeFile() throws IOException {
        /**Creates a new HTML formatter used in the addRecords & check class*/
        String methodName = "makeFile";
        try {
            formatter = new Formatter("Sourcecode.html");
        } catch (Exception e) {
            log.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
        }
    }

    void addRecords(int pageNumber) throws IOException, InterruptedException {
        String methodName = "addRecords";
        /**Opens a link and scrapes the html from the page into Sourcecode.html*/
        String html = null;
        String mainPageLink = ini.dataGetter("link", "link")+ pageNumber;
        System.out.println("1");
        WebDriver driver = new FirefoxDriver();
        System.out.println("2");
        try {
            driver.navigate().to(mainPageLink + "_price_asc");
            driver.manage().window().setPosition(new Point(-2000, 0));
            driver.manage().window().setSize(new Dimension(1, 1));
            TimeUnit.SECONDS.sleep(3);
            driver.findElement(By.xpath("//div[@id = 'result_0']")).getText();
            html = driver.getPageSource();
            driver.quit();
            log.setText(Level.INFO, getClass().toString(), methodName, "Link to main page: " + mainPageLink);
        } catch (NoSuchElementException e) {
            log.setText(Level.WARNING, getClass().toString(), methodName, "Too many requests recently. " +
                    "Program will resume in four minutes (" + timeInFourMinutes() + ")");
            System.out.println(mainPageLink);
            TimeUnit.MINUTES.sleep(10);
            driver.quit();
            TimeUnit.SECONDS.sleep(240);
            addRecords(pageNumber);
        }
        formatter.format("%s \n ", (html));
    }

    void closeFile(){
        formatter.close();
    }

    void check(String[] minAndMaxPrice) throws IOException {
        String methodName = "check";
        /**Checks Source.html for the links to the weapons and the sell price of the weapons*/
        File file = new File("Sourcecode.html");
        int i = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                while (line.isEmpty()){
                    line = scanner.nextLine();
                }
                if (line.substring(1, line.length()).contains("market_listing_row_link")) {
                    links[i] = line.substring(27, line.length() - 34);
                    if (checkLink.check(links[i])){
                        stopper[i] = 1;
                    }
                }
                if (line.substring(1, line.length()).contains("class=\"normal_price")) {
                    String[] part = line.split("<");
                    line = part[1].substring(27, part[1].length());
                    sellPrice[i] = Double.parseDouble(line);
                    log.setText(Level.INFO, getClass().toString(), methodName, "Current sell price for this weapon: " + sellPrice[i]);
                    if (sellPrice[i] < (Integer.parseInt(minAndMaxPrice[3]) * 1.12) || sellPrice[i] > Integer.parseInt(minAndMaxPrice[4])) {
                        stopper[i] = 1;
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            log.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
        }
    }

    void getPrices(String[] makeGuiStringArray) throws IOException, InterruptedException, TwilioRestException {
        String methodName = "getPrices";
        for (int counter = 0; counter < 10; counter++) {
            if (stopper[counter] == 0) {
                double sell = sellPrice[counter];
                String buyOrders = "DIE";
                while (buyOrders.equals("DIE")){
                    buyOrders = getWeaponInfo(links[counter]);
                }
                if (buyOrders.length() == 1) {
                    log.setText(Level.INFO, getClass().toString(), methodName, "Error getting the buy order price");
                } else {
                    String[] part = buyOrders.split(" ");
                    String part1 = part[5];
                    buyOrders = part1.substring(1, part1.length());
                    buyOrdersDouble = Double.parseDouble(buyOrders);
                    log.setText(Level.INFO, getClass().toString(), methodName, "Current cheapest buy order: "
                            + buyOrdersDouble + "\nCurrent cheapest sell price: " + sell + "\nLink to weapon: "
                            + links[counter]);
                }
                sell = sell * .87;
                double buywith = buyOrdersDouble + 7;
                int totalSold = GC.makeFile(links[counter], false);
                log.setText(Level.INFO, getClass().toString(), methodName, "Stupid joe: " + buyOrdersDouble +
                        "\nSell price after Valves cut: " + sell + "\nTotal sold in the past week: " + totalSold);
                if (sell > Integer.parseInt(makeGuiStringArray[3]) && buywith < Integer.parseInt(makeGuiStringArray[4])) {
                    if (buywith < sell) {
                        if (Integer.parseInt(makeGuiStringArray[7]) <= totalSold) {
                            double profit = sell - buyOrdersDouble;
                            log.setText(Level.INFO, getClass().toString(), methodName, "PROFIT: " + profit);
                            if (profit > Integer.parseInt(makeGuiStringArray[5])) {
                                emailData[1] = (links[counter]);
                                SE.emailUser(makeGuiStringArray, emailData);
                                ST.sendText(makeGuiStringArray, links[counter]);
                            }
                            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ini.dataGetter(
                                    "paths", "profitableItemsPath") + "\\ProfitableItems.txt", true)))) {
                                out.println(links[counter]);
                                out.println("Profit: " + profit + " Total Sold: " + totalSold + " Current Price :" + sell);
                            } catch (Exception e) {
                                log.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
                            }
                        } else {
                            log.setText(Level.INFO, getClass().toString(), methodName, "Not enough of the item sold in the past week:\n" +
                                    "Your Limit: " + makeGuiStringArray[7] + " Total sold: " + totalSold + "\n" +
                                    "Weapon finished\n\n");
                        }
                    } else {
                        log.setText(Level.INFO, getClass().toString(), methodName, "Not profitable\n" +
                                "Weapon finished\n\n");
                    }
                } else {
                    log.setText(Level.INFO, getClass().toString(), methodName, "Not in price range\n" +
                            "Weapon finished\n\n");
                    stopper[counter] = 0;
                }
            } else {
                log.setText(Level.INFO, getClass().toString(), methodName, "Not in buy range\n" + sellPrice[counter]
                        + "\nWeapon finished\n\n");
                stopper[counter] = 0;
            }
        }
    }

    int steamStatus() throws InterruptedException {
        /**Opens a tab in firefox to check if what the current status of the steam community and web API is. returns the
         * values 1-4 to the make_GUI class*/
        int status;
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().setPosition(new Point(-2000, 0));
        driver.manage().window().setSize(new Dimension(1, 1));
        driver.navigate().to("https://steamstat.us/");
        TimeUnit.SECONDS.sleep(1);
        String community = driver.findElement(By.xpath("id('community')")).getText();
        String webapi = driver.findElement(By.xpath("id('webapi')")).getText();
        if (community.contains("Normal") && webapi.contains("Normal")) {
            status = 1;
        } else if (community.contains("Normal") && !webapi.contains("Normal")) {
            status = 2;
        } else if (webapi.contains("Normal") && !community.contains("Normal")) {
            status = 3;
        } else {
            status = 4;
        }
        driver.quit();
        return status;
    }

    private String timeInFourMinutes(){
        DateFormat dateFormat = new SimpleDateFormat("hh꞉mm꞉ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, + 4);
        return dateFormat.format(calendar.getTime());
    }

    private String getWeaponInfo(String link) throws IOException, InterruptedException {
        String methodName = "getWeaponInfo";
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().setPosition(new Point(-2000, 0));
        driver.manage().window().setSize(new Dimension(1, 1));
        driver.navigate().to(link);
        TimeUnit.SECONDS.sleep(1);
        String value = "DIE";
        try {
            value = driver.findElement(By.xpath("//div[@id='market_commodity_buyrequests']")).getText();
        } catch (NoSuchElementException e) {
            log.setText(Level.WARNING, getClass().toString(), methodName, "Too many requests recently. Program " +
                    "will resume in four minutes (" + timeInFourMinutes() + ")");
            driver.quit();
        }
        if (value == null || value.length() == 1 || value.equals("DIE")){
            log.setText(Level.WARNING, getClass().toString(), methodName, "value equals is either null or has a length " +
                    "of one. Program will resume in four minutes (" + timeInFourMinutes() + ")");
            driver.quit();
            TimeUnit.SECONDS.sleep(240);
            getWeaponInfo(link);
        }
        if (!value.equals("DIE")){
            driver.quit();
            return value;
        }
        return "DIE";
    }
}