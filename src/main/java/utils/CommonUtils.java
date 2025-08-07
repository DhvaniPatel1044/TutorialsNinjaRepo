package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Theme;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class CommonUtils {
	public ExtentReports extent;

	public static String generateNewEmail() {

		return new Date().toString().replaceAll(" ", "").replaceAll("\\:", "") + "@gmail.com";

	}

	public static boolean compareTwoScreenshots(String actualImagePath, String expectedImagePath) {

		BufferedImage acutualBImg = null;
		BufferedImage expectedBImag = null;
		try {
			acutualBImg = ImageIO.read(new File(actualImagePath));
			expectedBImag = ImageIO.read(new File(expectedImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageDiffer imgDiffer = new ImageDiffer();
		ImageDiff imagdifferance = imgDiffer.makeDiff(expectedBImag, acutualBImg);

		return imagdifferance.hasDiff();

	}

	public static Properties loadProperties() {

		Properties prop = new Properties();
		FileReader fr;

		try {
			fr = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resourses\\projectdata.properties");
			prop.load(fr);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return prop;
	}

	public static void setUpProperties(String propertyKey, String propertyValue) {
		Properties prop = CommonUtils.loadProperties();
		prop.setProperty(propertyKey, propertyValue);
		FileWriter fr = null;
		try {
			fr = new FileWriter(System.getProperty("user.dir") + "\\src\\test\\resources\\projectdata.properties");
			prop.store(fr, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver takeScreenshot(WebDriver driver, String pathToBCopied) {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);

		try {
			// Create destination file with full path
			File destination = new File(System.getProperty("user.dir") + pathToBCopied);

			// Create parent folders if they do not exist
			destination.getParentFile().mkdirs();

			// Copy screenshot to the destination path
			FileHandler.copy(srcScreenshot, destination);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return driver;

		/*
		 * TakesScreenshot ts = (TakesScreenshot) driver; File srcScrrenshot =
		 * ts.getScreenshotAs(OutputType.FILE); try { FileHandler.copy(srcScrrenshot,
		 * new File(System.getProperty("user.dir") + pathToBCopied)); } catch
		 * (IOException e) { e.printStackTrace(); }
		 * 
		 * return driver;
		 */
	}
	public static String takeScreenshotAndReturnPath(WebDriver driver, String pathToBCopied) {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);
        String destScreenshotPath = System.getProperty("user.dir") + pathToBCopied;
		try {
			// Create destination file with full path
			File destination = new File(destScreenshotPath);

			// Create parent folders if they do not exist
			destination.getParentFile().mkdirs();

			// Copy screenshot to the destination path
			FileHandler.copy(srcScreenshot, destination);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return destScreenshotPath;
		}

	public static String validEmailRandomizeGenerator() {

		String[] validEmails = { "amotooricap1@gmail.com", "amotooricap2@gmail.com", "amotooricap3@gmail.com",
				"amotooricap4@gmail.com", "amotooricap5@gmail.com", "amotooricap6@gmail.com", "amotooricap7@gmail.com",
				"amotooricap8@gmail.com" };

		Random random = new Random();
		int index = random.nextInt(validEmails.length);

		return validEmails[index];

	}

	public static Object[][] getTestData(MyXLSReader xls_received, String testName, String sheetName) {

		MyXLSReader xls = xls_received;

		String testCaseName = testName;

		String testDataSheet = sheetName;

		int testStartRowNumber = 1;

		while (!(xls.getCellData(testDataSheet, 1, testStartRowNumber).equals(testCaseName))) {

			testStartRowNumber++;

		}

		int columnStartRowNumber = testStartRowNumber + 1;
		int dataStartRowNumber = testStartRowNumber + 2;

		int rows = 0;
		while (!(xls.getCellData(testDataSheet, 1, dataStartRowNumber + rows).equals(""))) {

			rows++;

		}

		// Total number of columns in the required test
		int columns = 1;

		while (!(xls.getCellData(testDataSheet, columns, columnStartRowNumber).equals(""))) {

			columns++;

		}

		Object[][] obj = new Object[rows][1];

		HashMap<String, String> map = null;

		// Reading the data in the test
		for (int i = 0, row = dataStartRowNumber; row < dataStartRowNumber + rows; row++, i++) {

			map = new HashMap<String, String>();

			for (int j = 0, column = 1; column < columns; column++, j++) {

				String key = xls.getCellData(testDataSheet, column, columnStartRowNumber);

				String value = xls.getCellData(testDataSheet, column, row);

				map.put(key, value);

			}

			obj[i][0] = map;

		}

		return obj;

	}
	
  public static ExtentReports getExtentReport() {
	
	   ExtentReports extentReport = new ExtentReports();
	   String reportPath = System.getProperty("user.dir")+"\\reports\\TNExtentReport.html";
	   System.out.println("Report path: "+ reportPath);
		
		File extentReportFile = new File(reportPath);
		
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);
		ExtentSparkReporterConfig sparkConfig = sparkReporter.config();
		sparkConfig.setReportName("Tutorials Ninja Test Automation Results");
		sparkConfig.setDocumentTitle("TNER Results");
		
		extentReport.attachReporter(sparkReporter);
		extentReport.setSystemInfo("OS",System.getProperty("os.name"));
		extentReport.setSystemInfo("Java Version",System.getProperty("java.version"));
		extentReport.setSystemInfo("Username",System.getProperty("user.name"));
		extentReport.setSystemInfo("Selenium WebDriver Version","4.24.0");
		
		return extentReport;
		
	}
}



