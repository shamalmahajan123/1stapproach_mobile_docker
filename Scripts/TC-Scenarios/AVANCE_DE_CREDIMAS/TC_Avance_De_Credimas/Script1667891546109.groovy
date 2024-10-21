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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC
import org.openqa.selenium.remote.RemoteWebElement
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import io.appium.java_client.AppiumDriver
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

'AVANCE DE CREDIMAS : '
WebUI.comment((CaseNo + ' - ') + Description)

'PROFILE VERIFICATION FOR DATABASE CONNECTION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProfileVerification'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino], FailureHandling.STOP_ON_FAILURE)

'APPLICATION LAUNCH / USER LOGGED IN VERIFICATION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickAppLaunch'), [:], FailureHandling.STOP_ON_FAILURE)

'USERLOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLogin'(Role, Cedula, Pasaporte, Password, Email)

if ((Verification == 'Import') && Role == 'Pasaporte') {
	
   WebUI.callTestCase(findTestCase('TC-TokenDigitalMigration/TC_DigitalTokenMigration'), [('Verification') : Verification, ('Cedula') : Cedula,('Password') : Password,('Role') : Role,('Email') : Email], FailureHandling.CONTINUE_ON_FAILURE)

} else {
	
	'TAP ON HAMBURGER MENU : '
	Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
	
	'SELECT TRANSACCIONES : '
	if (Type == 'NA') {
		println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
		
	} else if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
		Mobile.tap(findTestObject('Object Repository/AvanceDeEfectivo/Avance de efectivo Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		
	} else {
		Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		
		if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('Object Repository/AvanceDeEfectivo/Avance de efectivo Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)

		} else {
			Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('Object Repository/AvanceDeEfectivo/Avance de efectivo Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		}
	}
	
	if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Origen'), 30, FailureHandling.OPTIONAL)) {
		'ATTEMPT TRANSACTION PAYMENTS : '
	
		'SELECT ORIGEN ACCOUNT :'
		if (CtaOrigen == 'NA') {
			println('Origen Account Not Available...!!!')
		} else {
			Mobile.tap(findTestObject('Object Repository/AvanceDeEfectivo/Origen Title'), 5, FailureHandling.OPTIONAL)
			Mobile.delay(5)
			CredimasOrigin(CtaOrigen)
		}
		//WebUI.callTestCase(findTestCase('CommonTestCases/TC_OrigenAccount'), [('CtaOrigen') : CtaOrigen, ('Monto') : Monto], FailureHandling.STOP_ON_FAILURE)
		
		if (Verification == "ExceedOrigenAmount") {
			try {
				println "Origen Monto Balance Updated Is : " + GlobalVariable.origen_before_db
				double Balance1 = Double.parseDouble(Monto)
				//double Balance2 = GlobalVariable.origen_before_db + Balance1
				double origenBeforeApp = Double.parseDouble(GlobalVariable.origen_before_app)
				println "origenBeforeApp is : " + origenBeforeApp
				double Balance2 = origenBeforeApp + Balance1
				println "Balance2 Is : " + Balance2
				String UpdatedMonto = String.format("%.0f", Balance2)
				Monto = UpdatedMonto
				println "Updated Monto Is : " + Monto
			}catch(Exception e) {
				e.printStackTrace()
			}
		}
		
		'SELECT DESTINO ACCOUNT :'
	    WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccount'), [('CtaDestino') : CtaDestino], FailureHandling.STOP_ON_FAILURE)
		
		'ENTER MONTO FOR PAYMENT :'
		println "Monto For Transaction Is : " + Monto + '.'+ Decimal
		GlobalVariable.MontoDecimal = Monto + '.'+ Decimal
	
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : Monto ,('Decimal') : Decimal], FailureHandling.STOP_ON_FAILURE)

//		'TASA VERIFICATION : '
//		WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
//			
		if (Mobile.waitForElementPresent(findTestObject('AvanceDeEfectivo/Introducir el valor de plazo'), 5, FailureHandling.OPTIONAL)) {
			'ENTER PLAZO FOR PAYMENT : '
			Mobile.setText(findTestObject('AvanceDeEfectivo/Introducir el valor de plazo'), Plazo , 3, FailureHandling.CONTINUE_ON_FAILURE)
			
		} else {
			println('No Plazo Required For Payment..!!')
		}
		
		'ENTER CONCEPT FOR PAYMENT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept, ('Description') : Description],
			FailureHandling.STOP_ON_FAILURE)
		
		if (Mobile.waitForElementPresent(findTestObject('AvanceDeEfectivo/El monto digitado es mayor a tu balance disponible'), 3, FailureHandling.OPTIONAL)) {
			println('Origen Account Does Not Have Sufficient Fund...!!!')
			
		} else {
			'VERIFICATION CODIGO : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification'), [('Monto') : Monto, ('Password') : Password, ('Description') : Description], FailureHandling.OPTIONAL)
		}
		
		'VERIFICATION SCENARIO TEST CASE :'
		 CustomKeywords.'helper.paymenthelper.PaymentResponses'()

	}
	
	if ((Verification == 'SelfAuthorize') && GlobalVariable.PaymentStatus.equals("Passed") || GlobalVariable.PaymentStatus.equals("Failed")) {
		
		if(Mobile.verifyElementVisible(findTestObject('UserDetails/HamburgerMenu'), 2, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 10,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(5)
			
		} else if(Mobile.verifyElementVisible(findTestObject('Object Repository/Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL) || Mobile.verifyElementVisible(findTestObject('Object Repository/Buttons/Regresar'), 2, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Object Repository/Buttons/ACEPTAR'), 1,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Object Repository/Buttons/Regresar'), 1,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 10,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(5)
		}
	
		'PAYMENT VERIFICATION - CHECK THE BALANCE UPDATES : '	
		PaymentVerification()
		
	} else if ((Verification == 'NA')) {
		
		CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.Verification = Verification
		GlobalVariable.CaseDescription = Description
		println('Balance Verification Not Necessary  ' + Description)
		
	} else if ((Verification == 'ExceedOrigenAmount')) {
		
		GlobalVariable.Verification = Verification
		GlobalVariable.CaseDescription = Description
		println('Insufficient Balance Condition - Verification Not Necessary  ' + Description)
		
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		println('Non Payment Flow  ' + Description)
	}
}

def PaymentVerification() {
	if (Verification == 'SelfAuthorize') {
			
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResultsModified'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : Monto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)
	
	} else {	
		CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.Verification = Verification
		GlobalVariable.CaseDescription = Description
		println('Balance Verification Not Necessary  ' + Description)
	}
}

'CLOSING THE APPLICATION : '
Mobile.closeApplication()


def CredimasOrigin (String CtaOrigen) {
	if( Mobile.verifyElementExist(findTestObject('Object Repository/AvanceDeEfectivo/Origen Title'), 05, FailureHandling.OPTIONAL)
		||Mobile.verifyElementExist(findTestObject('TransferenciaPropia/Cuentas-List'), 05, FailureHandling.OPTIONAL)) {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		try {
			//Mobile.scrollToText('Credimas', FailureHandling.OPTIONAL)
			for(int i=4; i<=10; i++) {
				TestObject testObjectOne = new TestObject()
				testObjectOne.addProperty("xpath", ConditionType.EQUALS, "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/androidx.recyclerview.widget.RecyclerView[1]/android.view.ViewGroup["+ i +"]/android.view.ViewGroup[1]/*[@class = 'android.widget.TextView' and (@text = '"+CtaOrigen+"' or . = '"+CtaOrigen+"')]")
				
				TestObject testObjectTwo = new TestObject()
				testObjectTwo.addProperty("xpath", ConditionType.EQUALS, "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/androidx.recyclerview.widget.RecyclerView[1]/android.view.ViewGroup["+ i +"]/android.view.ViewGroup[1]/*[@class = 'android.widget.TextView' and (@text = 'Tarjeta de crédito' or . = 'Tarjeta de crédito')]")
				
				if(Mobile.verifyElementExist(testObjectOne, 5, FailureHandling.OPTIONAL) && Mobile.verifyElementExist(testObjectTwo, 5, FailureHandling.OPTIONAL)) {
					KeywordUtil.markPassed("Origen Account Available...!!!")
					MobileElement Element = driver.findElement(By.xpath("//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/androidx.recyclerview.widget.RecyclerView[1]/android.view.ViewGroup["+ i +"]/android.view.ViewGroup[1]"))
					Element.click()
					break
					
				} else {
					KeywordUtil.markPassed("Origen Account Not Found - Try Again...!!!")
				}
				
				if(i>10) {
					GlobalVariable.origen_before_app = "CtaOrigen Account Not Found " + CtaOrigen
					CustomKeywords.'helper.customfunction.Screenshot'()
					GlobalVariable.OrigenAccountStatus = "NotAvailable"
					KeywordUtil.markPassed("Origen Account Not Available - Redirected To Test Case...!!!")
					Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
				}
			}
			
		} catch(Exception e) {
			GlobalVariable.origen_before_app = "CtaOrigen Account Not Found " + CtaOrigen
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
			GlobalVariable.OrigenAccountStatus = "NotAvailable"
			e.printStackTrace()
		}
	} else {
		GlobalVariable.origen_before_app = "CtaOrigen Account Not Found " + CtaOrigen
		CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.OrigenAccountStatus = "NotAvailable"
		KeywordUtil.markPassed("Origen Account Not Available - Redirected To Test Case...!!!")
		Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
	}
}
