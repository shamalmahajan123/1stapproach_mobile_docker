import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import java.time.Duration
import org.openqa.selenium.By as By
import com.kms.katalon.core.mobile.keyword.internal.MobileAbstractKeyword as MobileAbstractKeyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory as MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper as MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException as WebElementNotFoundException
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.ui.SystemOutputInterceptor as SystemOutputInterceptor
import io.appium.java_client.AppiumDriver as AppiumDriver
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.StringUtils as StringUtils
import java.lang.Integer as Integer
import io.appium.java_client.MultiTouchAction as MultiTouchAction
import io.appium.java_client.TouchAction as TouchAction
import io.appium.java_client.touch.WaitOptions as WaitOptions
import io.appium.java_client.touch.offset.PointOption as PointOption
import io.appium.java_client.MobileElement as MobileElement
import io.appium.java_client.remote.AndroidMobileCapabilityType as AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType as MobileCapabilityType
import io.appium.java_client.remote.MobilePlatform as MobilePlatform
import org.openqa.selenium.remote.CapabilityType as CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC
import org.openqa.selenium.remote.RemoteWebElement
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import io.appium.java_client.AppiumDriver
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

AppiumDriver<?> driver = MobileDriverFactory.getDriver()
//String EliminarBenf = Alias + ' ' + CtaDestino

try {
	Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
	//MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '" + EliminarBenf + "' or . = '"+ EliminarBenf + "')]"))
	MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (contains(@text, '" + CtaDestino + "') or contains(., '"+ CtaDestino + "'))]"))
	KeywordUtil.markPassed('Deleting Beneficiary..!!')
	
	if(Element.displayed)
	{
		ArrayList<String> Elements = new ArrayList<String>();
		Elements = driver.findElementsByXPath("//*[@class = 'android.widget.TextView' and (contains(@text, '" + CtaDestino + "') or contains(., '"+ CtaDestino + "'))]")
		MobileElement firstElement = Elements.get(0);						
		int midOfY = firstElement.getLocation().y +(firstElement.getSize().height/2);
		int fromXLocation = firstElement.getLocation().x;
		KeywordUtil.logInfo(""+fromXLocation)
		int toXLocation = Mobile.getDeviceWidth()-1;
		Mobile.delay(2)
		TouchAction action = new TouchAction(driver);
		action.longPress(PointOption.point(fromXLocation, midOfY)).moveTo(PointOption.point(toXLocation, midOfY)).release().perform();
		Mobile.delay(2)
		
	} else {
		if(Concept.contains('Eliminar')) {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.CaseStatusMessage += "\n" + CtaDestino +" Beneficiary Is Not Dislayed - Unable To Delete Beneficiary..!! \n"
			KeywordUtil.markFailed(CtaDestino + ' Beneficiary Is Not Dislayed - Unable To Delete Beneficiary For Case ' + Description)
		} else {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markPassed(CtaDestino + ' Beneficiary Does Not Exist..!!')
		}	
	}
	
} catch(Exception e) {
	if(Concept.contains('Eliminar')) {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.CaseStatusMessage += "\n" + CtaDestino +" Beneficiary Is Not Dislayed - Unable To Delete Beneficiary..!! \n"
		KeywordUtil.markFailed(CtaDestino + ' Beneficiary Is Not Dislayed - Unable To Delete Beneficiary For Case ' + Description)
	} else {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markPassed(CtaDestino + ' Beneficiary Does Not Exist..!!')
	}
}


if(Concept.contains('Eliminar')) {
	if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Button - Eliminar'), 5, FailureHandling.OPTIONAL)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Eliminar'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(0.2)
		if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/El beneficiario ha sido eliminado'), 10, FailureHandling.OPTIONAL)){
			KeywordUtil.markPassed('Beneficiary Deleted Successfully...!!')
		} else {
			println('Failed To Eliminar Beneficiary..!!')
			//CustomKeywords.'helper.customfunction.Screenshot'()
			//KeywordUtil.markFailed('El beneficiario ha sido eliminado Message Not Displayed - Failed To Eliminar Beneficiary..!!')
		}
	} else {
		KeywordUtil.markFailed('Eliminar Button Not Found..!!')
	}
} else {
	if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Button - Eliminar'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Eliminar'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(0.2)
		if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/El beneficiario ha sido eliminado'), 10, FailureHandling.OPTIONAL)){
			Mobile.tap(findTestObject('Buttons/ACEPTAR'), 5, FailureHandling.OPTIONAL)
			KeywordUtil.markPassed('Beneficiary Deleted Successfully...!!')
			Mobile.delay(0.5)
		} else {
			println('Failed To Eliminar Beneficiary..!!')
			//CustomKeywords.'helper.customfunction.Screenshot'()
			//KeywordUtil.markFailed('El beneficiario ha sido eliminado Message Not Displayed - Failed To Eliminar Beneficiary..!!')
		}
	} else {
		KeywordUtil.markWarning('Eliminar Button Not Found..!!')
	}
}