import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.events.AbstractWebDriverEventListener
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.driver.DriverType
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * copied from https://www.seleniumeasy.com/selenium-tutorials/webdriver-event-listener-example
 */
class WebEventListener extends AbstractWebDriverEventListener {
	public void beforeClickOn(WebElement element, WebDriver driver) {
		System.out.println("Trying to click on: " + element.toString());
	}
	public void afterClickOn(WebElement element, WebDriver driver) {
		System.out.println("Clicked on: " + element.toString());
	}
}

WebDriver createWebDriver() {
	DriverType dt = DriverFactory.getExecutedBrowser()
	WebUI.comment("executed browser is ${dt.getName()}")
	WebDriver driver = null
	switch (dt.getName()) {
		case 'FIREFOX_DRIVER':
			System.setProperty('webdriver.gecko.driver', DriverFactory.getGeckoDriverPath())
			driver = new FirefoxDriver()
			break;
		case 'CHROME_DRIVER':
			System.setProperty('webdriver.chrome.driver', DriverFactory.getChromeDriverPath())
			driver = new ChromeDriver()
			break;
	}
	return driver
}

// create WebDriver which can fire events
WebDriver driver = createWebDriver()

EventFiringWebDriver e_driver = new EventFiringWebDriver(driver)
e_driver.register(new WebEventListener())

// let Katalon to use the event-firing WebDriver
DriverFactory.changeWebDriver(e_driver)

WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment'), 10)

WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment'))

WebUI.closeBrowser()

