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

if (Monto == 'NA' || GlobalVariable.Keyboard == 'false') {
    KeywordUtil.markPassed('Skipped NonPayment Flows ' + Description)
	CustomKeywords.'helper.customfunction.continuar'()
	
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
    println('Origen Account Not Available - Skipping Codigo Verification Flow...!!!')
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
    println('Destino Account Not Available - Skipping Codigo Verification Flow...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('Buttons/Confirmar'), 5, FailureHandling.OPTIONAL)) {
	  //CustomKeywords.'helper.customfunction.Screenshot'()
      //Mobile.scrollToText("Código de verificación", FailureHandling.OPTIONAL)	  
	  //Mobile.scrollToText("Código de verificación", FailureHandling.CONTINUE_ON_FAILURE)
	  Swipe_Screen()
	  String CodigoNumber = ''
	  String GetCodigo = ''
	  
   if (Mobile.waitForElementPresent(findTestObject('MultipleTransactions/Codigo Field Multiple Transaction'), 2, FailureHandling.OPTIONAL)) {
	   CodigoNumber = Mobile.getText(findTestObject('MultipleTransactions/Codigo Field Multiple Transaction'), 3, FailureHandling.OPTIONAL)
	   
	   println "CodigoNumber Is " + CodigoNumber	   
	   GetCodigo = CodigoNumber.replaceAll("[^0-9]+", "");
	   println "GetCodigo Is " + GetCodigo
	   
	   CustomKeywords.'helper.codigo.TarjetaCodigo_Cedula'(GetCodigo)
	   Mobile.delay(2)
	   println "CodeValue Is " + GlobalVariable.CodeValue
	
	   Mobile.setText(findTestObject('MultipleTransactions/Codigo Field Multiple Transaction'), GlobalVariable.CodeValue, 3, FailureHandling.OPTIONAL)
	   CustomKeywords.'helper.customfunction.HideKeyboard'()
	   
	   CustomKeywords.'helper.customfunction.Screenshot'()
	   Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
	   
   } else {
	  KeywordUtil.markPassed('Codigo Is Not Necessary For Test Case ' + Description)	  
	  Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
	  Mobile.delay(2)
	  Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
   }
	
	PaymentVerification()
	
  } else {
	  KeywordUtil.markPassed('Codigo Is Not Necessary For Test Case...!!!')	  
	  Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
	  Mobile.delay(2)
	  Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
	  
	  PaymentVerification()
  }


def PaymentVerification() {
	if(Mobile.verifyElementVisible(findTestObject('MultipleTransactions/Completado screen button'), 120, FailureHandling.OPTIONAL)) {
		'PAYMENT TRANSACTION VERIFICATON : '
		CustomKeywords.'helper.customfunction.Screenshot'()
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		int TotalDestino = Integer.parseInt(DestinoCount)
		List<MobileElement> Element = new ArrayList<MobileElement>()
		
		for(int i = 0; i<TotalDestino; i++) {
			Element = driver.findElements(By.xpath("//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.HorizontalScrollView[${i+1}]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.widget.TextView[1]"))
			if(Element[0] == null) {
				while (Element[0] == null){
					Swipe_Screen()
					Thread.sleep(150);
					Element = driver.findElements(By.xpath("//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.HorizontalScrollView[${i+1}]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.widget.TextView[1]"))
					if (Element[0].displayed)
						break;
				}
				CustomKeywords.'helper.customfunction.Screenshot'()
			}
		}
		
		try {
			Mobile.scrollToText("TRANSACCION PROCESADA", FailureHandling.OPTIONAL)
			MobileElement ElementOne = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'TRANSACCION PROCESADA' or . = 'TRANSACCION PROCESADA')]"))
			if(ElementOne.displayed.TRUE) {
				GlobalVariable.PaymentStatus = "Passed"
				GlobalVariable.PaymentMessageMultiple = "TRANSACCION PROCESADA"
			}
		} catch(Exception e) {
			try {
				Mobile.scrollToText("TRANSACCIÓN PROCESADA", FailureHandling.OPTIONAL)
				MobileElement ElementTwo = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'TRANSACCIÓN PROCESADA' or . = 'TRANSACCIÓN PROCESADA')]"))
				if(ElementTwo.displayed.TRUE) {
					GlobalVariable.PaymentStatus = "Passed"
					GlobalVariable.PaymentMessageMultiple = "TRANSACCIÓN PROCESADA"
				}
			} catch(Exception er) {
				GlobalVariable.PaymentStatus = "Failed"
				KeywordUtil.logInfo("TRANSACCIÓN PROCESADA or TRANSACCION PROCESADA not found !!!")
			}
		}
		
		try {
			Mobile.scrollToText("TRANSACCION NO PROCESADA", FailureHandling.OPTIONAL)
			MobileElement ElementThree = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'TRANSACCION NO PROCESADA' or . = 'TRANSACCION NO PROCESADA')]"))
			if(ElementThree.displayed.TRUE) {
				GlobalVariable.PaymentStatus = "Passed"
				GlobalVariable.PaymentMessageMultiple += "/TRANSACCION NO PROCESADA"
			}
		} catch(Exception e) {
				try {
				Mobile.scrollToText("TRANSACCIÓN NO PROCESADA", FailureHandling.OPTIONAL)
				MobileElement ElementFour = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'TRANSACCIÓN NO PROCESADA' or . = 'TRANSACCIÓN NO PROCESADA')]"))
				if(ElementFour.displayed.TRUE) {
					GlobalVariable.PaymentStatus = "Passed"
					GlobalVariable.PaymentMessageMultiple += "/TRANSACCIÓN NO PROCESADA"
				}
			} catch(Exception er) {
				GlobalVariable.PaymentStatus = "Passed"
				KeywordUtil.logInfo("TRANSACCIÓN NO PROCESADA or TRANSACCION NO PROCESADA not found !!!")
			}
		}
	} else {
		Mobile.delay(1)
		CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.PaymentStatus = "Failed"
		println('Non Payment Transaction Scenario - So Verification Skipped...!!!')
	}
}


def Swipe_Screen() {
	int device_Height = Mobile.getDeviceHeight()

	int device_Width = Mobile.getDeviceWidth()

	int startX = device_Width / 2

	int endX = startX

	int startY = device_Height * 0.30

	int endY = device_Height * 0.70

	Mobile.swipe(startX, endY, endX, startY)
}