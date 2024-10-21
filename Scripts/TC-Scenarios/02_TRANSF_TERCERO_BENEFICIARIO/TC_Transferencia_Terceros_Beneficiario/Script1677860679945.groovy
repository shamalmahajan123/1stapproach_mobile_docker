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

GlobalVariable.ExpectedPaymentMessage = TransactionMessage

'TRANSFERENCIA TERCERO BENEFICIARIO : '
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
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_TransactionType'), [('Type') : Type], FailureHandling.CONTINUE_ON_FAILURE)
	
	if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Origen'), 30, FailureHandling.OPTIONAL)) {
		'ATTEMPT TRANSACTION PAYMENTS : '
	
		'SELECT ORIGEN ACCOUNT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_OrigenAccount'), [('CtaOrigen') : CtaOrigen, ('Monto') : Monto], FailureHandling.CONTINUE_ON_FAILURE)
		if (Verification == "ExceedOrigenAmount") {
			try {
				println "Origen Monto Balance Updated Is : " + GlobalVariable.origen_before_db
				double Balance1 = Double.parseDouble(Monto)
				double Balance2 = GlobalVariable.origen_before_db + Balance1
				println "Balance2 Is : " + Balance2
				String UpdatedMonto = String.format("%.0f", Balance2)
				Monto = UpdatedMonto
				println "Updated Monto Is : " + Monto
			}catch(Exception e) {
				//e.printStackTrace()
			}
			Verification = "SelfAuthorize"
		}
		
		'SELECT DESTINO ACCOUNT :'
	    WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoBeneficiary'), [('CtaDestino') : CtaDestino, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
	
		'ENTER MONTO FOR PAYMENT :'
		println "Monto For Transaction Is : " + Monto + '.'+ Decimal
		GlobalVariable.MontoDecimal = Monto + '.'+ Decimal
	
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : Monto ,('Decimal') : Decimal], FailureHandling.CONTINUE_ON_FAILURE)
		if(GlobalVariable.DotButton == 'false') {
			CustomKeywords.'helper.customfunction.GetCommisones'()
			CustomKeywords.'helper.customfunction.GetImpuesto'()
			GlobalVariable.Tasa = "No Conversion Rate"
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markPassed('Restrict Decimal Scenario Passed - ' + Description)
		} else {
			'TASA VERIFICATION : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
			
			'ENTER CONCEPT FOR PAYMENT : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept, ('Description') : Description],
			FailureHandling.CONTINUE_ON_FAILURE)
			
			'CHECK IMPUESTOS/COMMISSION IS PRESENT OR NOT : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyCommission'), [:], FailureHandling.OPTIONAL)
		}
		
		'VERIFICATION CODIGO : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification'), [('Monto') : Monto, ('Password') : Password, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
	
		'VERIFICATION SCENARIO TEST CASE :'
		CustomKeywords.'helper.paymenthelper.PaymentResponses'()
	
	}
	
	if ((Verification == 'SelfAuthorize')) {
		
		if(Mobile.verifyElementVisible(findTestObject('UserDetails/HamburgerMenu'), 2, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 10,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(5)
		} else if(Mobile.verifyElementVisible(findTestObject('Object Repository/Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL) 
			|| Mobile.verifyElementVisible(findTestObject('Object Repository/Buttons/Regresar'), 1, FailureHandling.OPTIONAL) 
			|| Mobile.verifyElementVisible(findTestObject('EnvioTuEfectivo/OK Button'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Object Repository/Buttons/ACEPTAR'), 1,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Object Repository/Buttons/Regresar'), 1,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('EnvioTuEfectivo/OK Button'), 1,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 10,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(5)
		}
	
		'PAYMENT VERIFICATION - CHECK THE BALANCE UPDATES : '
		PaymentVerification()
		
	} else if ((Verification == 'NA')) {
		Mobile.delay(1)
		//CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.Verification = Verification
		GlobalVariable.CaseDescription = Description
		println('Balance Verification Not Necessary  ' + Description)
		
	} else if ((Verification == 'ExceedOrigenAmount')) {
		
		GlobalVariable.Verification = Verification
		GlobalVariable.CaseDescription = Description
		println('Insufficient Balance Condition - Verification Not Necessary  ' + Description)
		
	} else {
		GlobalVariable.Verification = Verification
		GlobalVariable.CaseDescription = Description
		//CustomKeywords.'helper.customfunction.Screenshot'()
		println('Non Payment Flow  ' + Description)
	}
}

def PaymentVerification() {
	if (Verification == 'SelfAuthorize') {
			
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResultsModified'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : Monto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)
	
	} else {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.Verification = Verification
		GlobalVariable.CaseDescription = Description
		println('Balance Verification Not Necessary  ' + Description)
	}
}

CustomKeywords.'helper.paymenthelper.VerifyTransactionMessage'()

'CLOSING THE APPLICATION : '
Mobile.closeApplication()