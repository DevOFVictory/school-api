package de.devofvictory.schoolapi.objects.timetable;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class TimetableManager {
	
	private String host;
	private String username;
	private String password;
	private WebDriver driver;
	private boolean debug;
	
	public TimetableManager() {
		initDriver("chrome", "chromedriver");
	}
	
	public TimetableManager(String host, String username, String password) {
		this.debug = false;
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	public TimetableManager(String browserType, String path, boolean debug, String host, String username, String password) {
		this.debug = debug;
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	
	public void closeDriver() {
		driver.close();
	}
	
	public void initDriver(String browserType, String path) {

		if (browserType.equalsIgnoreCase("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();

			if (!debug) {
			    chromeOptions.setHeadless(true);
			    chromeOptions.addArguments("--no-sandbox");
			    chromeOptions.addArguments("--disable-gpu");
			}

		    chromeOptions.addArguments("user-data-dir="+System.getProperty("user.dir")+"\\chrome_data_dir", "window-size=900,900");
			System.setProperty("webdriver.chrome.driver", "chromedriver");


			driver = new ChromeDriver(chromeOptions);

		}else if (browserType.equalsIgnoreCase("firefox")) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setHeadless(true);
			firefoxOptions.addArguments("--no-sandbox");
			firefoxOptions.addArguments("--disable-gpu");
		    firefoxOptions.addArguments("user-data-dir="+System.getProperty("user.dir")+"\\firefox_data_dir", "window-size=900,900");
		    
		    
			System.setProperty("webdriver.gecko.driver", "geckodriver");
	
			driver = new FirefoxDriver(firefoxOptions);
		}else if (browserType.equalsIgnoreCase("opera")) {
			OperaOptions operaOptions = new OperaOptions();
			operaOptions.addArguments("--no-sandbox");
			operaOptions.addArguments("--disable-gpu");
			operaOptions.addArguments("--headless");
			operaOptions.setBinary("operadriver");
			operaOptions.addArguments("user-data-dir="+System.getProperty("user.dir")+"\\opera_data_dir", "window-size=900,900");
		    
		    
//			System.setProperty("webdriver.opera.driver", "operadriver");
	
			driver = new OperaDriver(operaOptions);
		}else {
			throw new IllegalArgumentException("Browsertype must be chrome, firefox or opera.");
		}
	}

	public File requestScreenshot(int week, int classId, File targetDirectory) {
		return requestScreenshot(week, classId, targetDirectory, host, username, password);
	}
	
	public File requestScreenshot(int week, int classId, File targetDirectory, String apiHost, String apiUser, String apiPassword) {
		try {
			driver.get("http://" + apiUser + ":" + apiPassword
					+ "@"+apiHost+"/c/" + String.format("%02d" , week) + "/c000" + classId + ".htm");

			WebElement ele = driver.findElement(By.xpath("/html/body/center/table[1]/tbody"));
			

			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage fullImg = ImageIO.read(screenshot);

			Point point = ele.getLocation();

			int eleWidth = ele.getSize().getWidth();
			int eleHeight = ele.getSize().getHeight();

			BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
			ImageIO.write(eleScreenshot, "png", screenshot);

			File screenshotLocation = new File(targetDirectory.getPath() + System.currentTimeMillis() + ".png");

			FileUtils.copyFile(screenshot, screenshotLocation);
			return screenshotLocation;

		} catch (Exception ex) {
			if (ex instanceof NoSuchElementException) {
				System.out.println("[ERROR] Couldn't find any timetable on given host. Are you using 'Untis Vertretungsplan 2021'?");
				ex.printStackTrace();
			}else {
				ex.printStackTrace();				
			}
			return null;
		}
	}
	
}
