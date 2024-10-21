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

GlobalVariable.ExpectedPaymentMessage = TransactionMessage

'HISTORICO_PENDIENTE_TUEFECTIVO : '
WebUI.comment((ID + ' - ') + Description)

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
	SelectTransaction()
	
	if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Pendientes'), 40, FailureHandling.OPTIONAL)) {
		
		if(Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/No existen datos para esta consulta'), 2, FailureHandling.OPTIONAL)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			println(' No Transaction History Present - Message Displayed - No existen datos para esta consulta..!!')
			Mobile.comment(' No Transaction History Present - Message Displayed - No existen datos para esta consulta..!!')
		} else if(Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 1, FailureHandling.OPTIONAL)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			//println('No Transaction History Present - Message Displayed - Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente..!!')
			Mobile.comment(' No Transaction History Present - Message Displayed - Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente..!!')
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
		}
		
		'ATTEMPT SWIPE PENDING TRANSACTION : '
		SwipeTransaction()
		
		if(HistoricoStatus == 'Passed') {
			
			if(Operation == 'Cancelar') {
				'ATTEMPT TO CANCLE PENDING TRANSACTION : '
				CustomKeywords.'helper.customfunction.Screenshot'()
				if(Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/historicoCancelar'), 5, FailureHandling.OPTIONAL)){
					//CustomKeywords.'helper.customfunction.Screenshot'()
					Mobile.tap(findTestObject('Historico_TuEfectivo/historicoCancelar'), 3, FailureHandling.OPTIONAL)
					Mobile.delay(2, FailureHandling.OPTIONAL)
					HistoricoStatus = 'Passed'
					
				} else {
					HistoricoStatus = 'Failed'
					println('Cancelar Button Not Available..!!')
					//CustomKeywords.'helper.customfunction.Screenshot'()
					KeywordUtil.markFailed('Cancelar Button Not Available..!!')
				}
				
			} else if(Operation == 'Reenviar') {
				'ATTEMPT TO RESEND PENDING TRANSACTION : '
				CustomKeywords.'helper.customfunction.Screenshot'()
				if(Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/HistoricoReenviar'), 5, FailureHandling.OPTIONAL)){
					//CustomKeywords.'helper.customfunction.Screenshot'()
					Mobile.tap(findTestObject('Historico_TuEfectivo/HistoricoReenviar'), 3, FailureHandling.OPTIONAL)
					HistoricoStatus = 'Passed'
					
					if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Pendientes'), 10, FailureHandling.OPTIONAL)) {
						KeywordUtil.markPassed('Resend Transaction Successfully..!!')
						
					} else if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Numero de token expirado'), 10, FailureHandling.OPTIONAL)) {
						//KeywordUtil.logInfo('Expired token number..!!')
						KeywordUtil.markWarning('Expired token number..!!')
					}
					
				} else {
					HistoricoStatus = 'Failed'
					println('Reenviar Button Not Available..!!')
					//CustomKeywords.'helper.customfunction.Screenshot'()
					KeywordUtil.markFailed('Reenviar Button Not Available..!!')
				}
			} else {
				HistoricoStatus = 'Failed'
				KeywordUtil.logInfo('Unknown Operation - Please Check In Datafile..!!')
				KeywordUtil.markFailed('Unknown Operation - Please Check In Datafile..!!')
			}
			
//			'VERIFICATION CODIGO : '
//			WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification'), [('Monto') : Monto, ('Password') : Password, ('Description') : Description], FailureHandling.OPTIONAL)
			
		}
		
//		if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/No existen datos para esta consulta'), 2, FailureHandling.OPTIONAL)) {
//			KeywordUtil.markPassed('No Pending Transaction - No existen datos para esta consulta..!!')
//			CustomKeywords.'helper.customfunction.Screenshot'()
//			
//		} else if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Pendientes'), 10 , FailureHandling.OPTIONAL)
//			&& Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/No existen datos para esta consulta'), 2, FailureHandling.OPTIONAL)) {
//			CustomKeywords.'helper.customfunction.Screenshot'()
//			
//		}else if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Pendientes'), 1, FailureHandling.OPTIONAL)) {
//			
//		} else {
//			'VERIFICATION SCENARIO TEST CASE :'
//			CustomKeywords.'helper.customfunction.Screenshot'()
//			CustomKeywords.'helper.paymenthelper.PaymentResponses'()
//		}
		
	} else {
		println('No Pending Transaction Found..')
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed('No Pending Transaction Found..!!')
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


def SelectTransaction() {
	if (Type == 'NA') {
		println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
	} else if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
		Mobile.delay(1)
	
		Mobile.tap(findTestObject('EnvioTuEfectivo/TuEfectivo Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
		Mobile.delay(1)
		
		Mobile.tap(findTestObject('Historico_TuEfectivo/Histrico Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		
	} else {
		Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		
		if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('EnvioTuEfectivo/TuEfectivo Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('Historico_TuEfectivo/Histrico Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		} else {
			Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('EnvioTuEfectivo/TuEfectivo Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('Historico_TuEfectivo/Histrico Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		}
	}
}


def SwipeTransaction() {
	
	AppiumDriver<?> driver = MobileDriverFactory.getDriver()
	
	if(Concept.contains('Retiro')) {
		
		try {
			Mobile.scrollToText('RETIRO', FailureHandling.OPTIONAL)
			//MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'RETIRO' or . = 'RETIRO')]"))
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (contains(@text, 'RETIRO') or contains(., 'RETIRO'))]"))
			int startX = Element.getLocation().getX()
			int startY = Element.getLocation().getY()
			int endX = 500
			int endY = Element.getLocation().getY() + 30
			if(Element.displayed) {
				HistoricoStatus = 'Passed'
			}
			Mobile.swipe(startX, startY, endX, endY, FailureHandling.OPTIONAL)
			Mobile.delay(1)
			
		} catch(Exception e) {
			//println e.printStackTrace()
			e.printStackTrace()
			HistoricoStatus = 'Failed'
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.logInfo('RETIRO Transaction Does Not Exist In Historico Pendiente List..!!')
			KeywordUtil.markFailed('RETIRO Transaction Does Not Exist In Historico Pendiente List..!!')
		}
		
	} else if(Concept.contains('Envio')) {
		try {
			Mobile.scrollToText('ENVIO', FailureHandling.OPTIONAL)
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'ENVIO' or . = 'ENVIO')]"))
			int startX = Element.getLocation().getX()
			int startY = Element.getLocation().getY()
			int endX = 500
			int endY = Element.getLocation().getY() + 30
			if(Element.displayed) {
				HistoricoStatus = 'Passed'
			}
			Mobile.swipe(startX, startY, endX, endY, FailureHandling.OPTIONAL)
			Mobile.delay(1)
		
		} catch(Exception e) {
			//println e.printStackTrace()
			e.printStackTrace()
			HistoricoStatus = 'Failed'
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.logInfo('ENVIO Transaction Does Not Exist In Historico Pendiente List..!!')
			KeywordUtil.markFailed('ENVIO Transaction Does Not Exist In Historico Pendiente List..!!')
		}
	
	} else {
		println('No Pending Transaction Found..!!')
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed('No Pending Transaction Found..!!')
	}
}

def VerifycancleStatus() {
	
	if(Concept.contains('Retiro')) {
		if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Procesadas - Title'), 20, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Historico_TuEfectivo/Procesadas - Title'), 3, FailureHandling.OPTIONAL)
			if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Procesadas - Title'), 20, FailureHandling.OPTIONAL)) {
				CustomKeywords.'helper.customfunction.Screenshot'()
				
				TestObject testObjectOne = new TestObject()
				testObjectOne.addProperty("xpath", ConditionType.EQUALS, "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/*[@class = 'android.widget.TextView' and (contains(@text, 'RETIRO') or contains(., 'RETIRO'))]")
				
				TestObject testObjectTwo = new TestObject()
				testObjectTwo.addProperty("xpath", ConditionType.EQUALS, "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/*[@class = 'android.widget.TextView' and (@text = 'Cancelada' or . = 'Cancelada')]")
				
				if(Mobile.verifyElementExist(testObjectOne, 5, FailureHandling.OPTIONAL) && Mobile.verifyElementExist(testObjectTwo, 5, FailureHandling.OPTIONAL)) {
					KeywordUtil.markPassed("Transaction Remains In Cancled Status...!!!")
					
				} else {
					//CustomKeywords.'helper.customfunction.Screenshot'()
					KeywordUtil.logInfo('RETIRO Transaction Does Not Exist In Cancled Transaction List..!!')
					KeywordUtil.markWarning("RETIRO Transaction Does Not Exist In Cancled Transaction List..!!")
				}
			} else {
				CustomKeywords.'helper.customfunction.Screenshot'()
				KeywordUtil.logInfo('Procesadas List Not Loaded - failed To Verify Cancelation Status..!!')
				KeywordUtil.markFailedAndStop("Procesadas List Not Loaded - failed To Verify Cancelation Status..!!")
			}
		}
	} else if(Concept.contains('Envio')) {
		if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Procesadas - Title'), 20, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Historico_TuEfectivo/Procesadas - Title'), 3, FailureHandling.OPTIONAL)
			if (Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Procesadas - Title'), 20, FailureHandling.OPTIONAL)) {
				CustomKeywords.'helper.customfunction.Screenshot'()
				
				TestObject testObjectOne = new TestObject()
				testObjectOne.addProperty("xpath", ConditionType.EQUALS, "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/*[@class = 'android.widget.TextView' and (contains(@text, 'ENVIO') or contains(., 'ENVIO'))]")
				
				TestObject testObjectTwo = new TestObject()
				testObjectTwo.addProperty("xpath", ConditionType.EQUALS, "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[2]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/*[@class = 'android.widget.TextView' and (@text = 'Cancelada' or . = 'Cancelada')]")
				
				if(Mobile.verifyElementExist(testObjectOne, 5, FailureHandling.OPTIONAL) && Mobile.verifyElementExist(testObjectTwo, 5, FailureHandling.OPTIONAL)) {
					KeywordUtil.markPassed("ENVIO Transaction Remains In Cancled Status...!!!")
					
				} else {
					//CustomKeywords.'helper.customfunction.Screenshot'()
					KeywordUtil.logInfo('ENVIO Transaction Does Not Exist In Cancled Transaction List..!!')
					KeywordUtil.markWarning("ENVIO Transaction Does Not Exist In Cancled Transaction List..!!")
				}
			} else {
				CustomKeywords.'helper.customfunction.Screenshot'()
				KeywordUtil.logInfo('Procesadas List Not Loaded - failed To Verify Cancelation Status..!!')
				KeywordUtil.markFailedAndStop("Procesadas List Not Loaded - failed To Verify Cancelation Status..!!")
			}	
		}
	}
}

