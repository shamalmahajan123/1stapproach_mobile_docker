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

'TRANSACCIONES MULTIPLES : '
WebUI.comment((CaseNo + ' - ') + Description)

'PROFILE VERIFICATION FOR DATABASE CONNECTION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProfileVerification'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino,('CuentaDestino1') : CuentaDestino1,('CuentaDestino2') : CuentaDestino2,('CuentaDestino3') : CuentaDestino3,('CuentaDestino4') : CuentaDestino4,('CuentaDestino5') : CuentaDestino5,('CuentaDestino6') : CuentaDestino6, ('DestinoCount') : DestinoCount, ('MontoCurrency1') : MontoCurrency1, ('MontoCurrency2') : MontoCurrency2], FailureHandling.STOP_ON_FAILURE)

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
	
		Mobile.tap(findTestObject('MultipleTransactions/Transacciones mltiples'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
		Mobile.delay(1)

	} else {
		Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		
		if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('MultipleTransactions/Transacciones mltiples'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
		} else {
			Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('MultipleTransactions/Transacciones mltiples'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
		}
	}
	
	'ATTEMPT TRANSACTION PAYMENTS : '
	if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Origen'), 30, FailureHandling.OPTIONAL)) {
	
		'SELECT ORIGEN ACCOUNT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_OrigenAccount'), [('CtaOrigen') : CtaOrigen, ('Monto') : Monto], FailureHandling.CONTINUE_ON_FAILURE)
	
		'SELECT DESTINO ACCOUNT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Multiple_Destino'), [('Destino') : CtaDestino, ('Destino1') : CtaDestino1, ('Destino2') : CtaDestino2, ('Destino3') : CtaDestino3, ('Destino4') : CtaDestino4, ('Destino5') : CtaDestino5, ('Destino6') : CtaDestino6, ('DestinoCount') : DestinoCount, ('Monto') : Monto, ('Monto1') : Monto1, ('Monto2') : Monto2, ('Monto3') : Monto3, ('Monto4') : Monto4, ('Monto5') : Monto5, ('Decimal') : Decimal, ('MontoCurrency') : MontoCurrency, ('MontoCurrency1') : MontoCurrency1, ('MontoCurrency2') : MontoCurrency2, ('MontoType') : MontoType, ('Concept') : Concept, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)

		'TOTAL MONTO : '
		String Balance = Mobile.getText(findTestObject('MultipleTransactions/Total Monto'), 2, FailureHandling.OPTIONAL)
		
		String Balance2
		try {
			String Balance1 = Balance.replaceAll('[,]', '')
			Balance2 = Balance1.substring(4)
		} catch(Exception e) {
			GlobalVariable.CaseStatusMessage += "\n *** Something wrong selecting destino accounts *** \n"
		} 
		GlobalVariable.MontoDecimal = Balance2	
		
		'VERIFICATION CODIGO : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification - Multiple Transaction'), [('Monto') : Monto, ('DestinoCount') : DestinoCount, ('Password') : Password, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
		
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
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResultsModified - MultiTransaction'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino, ('CuentaDestino1') : CuentaDestino1, ('CuentaDestino2') : CuentaDestino2, ('CuentaDestino3') : CuentaDestino3, ('CuentaDestino4') : CuentaDestino4, ('CuentaDestino5') : CuentaDestino5, ('CuentaDestino6') : CuentaDestino6, ('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino, ('CtaDestino1') : CtaDestino1, ('CtaDestino2') : CtaDestino2, ('CtaDestino3') : CtaDestino3, ('CtaDestino4') : CtaDestino4, ('CtaDestino5') : CtaDestino5, ('CtaDestino6') : CtaDestino6, ('Monto') : Monto, ('Monto1') : Monto1, ('Monto2') : Monto2, ('Monto3') : Monto3, ('Monto4') : Monto4, ('Monto5') : Monto5, ('Impuesto') : Impuesto, ('DestinoCount') : DestinoCount, ('MontoCurrency1') : MontoCurrency1, ('MontoCurrency2') : MontoCurrency2], FailureHandling.OPTIONAL)
	} else {	
		//CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.Verification = Verification
		GlobalVariable.CaseDescription = Description
		println('Balance Verification Not Necessary  ' + Description)
	}
}

//CustomKeywords.'helper.paymenthelper.VerifyTransactionMessage'()

'CLOSING THE APPLICATION : '
Mobile.closeApplication()