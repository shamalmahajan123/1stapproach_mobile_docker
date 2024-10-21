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
	CustomKeywords.'helper.customfunction.Screenshot'()
	CustomKeywords.'helper.customfunction.continuar'()
	PrintResults()
	
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
	GlobalVariable.origen_before_app = "SelectionFailed"
    println('Origen Account Not Available - Skipping Codigo Verification Flow...!!!')
	//CustomKeywords.'helper.customfunction.Screenshot'()
	PrintResults()
	ErrorCheck()
	
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
	GlobalVariable.destino_before_app = "SelectionFailed"
    println('Destino Account Not Available - Skipping Codigo Verification Flow...!!!')
	//CustomKeywords.'helper.customfunction.Screenshot'()
	PrintResults()
	ErrorCheck()
	
} else if (Mobile.waitForElementPresent(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)) {
	
	  //CustomKeywords.'helper.customfunction.Screenshot'()
      //Mobile.scrollToText("Código de verificación", FailureHandling.OPTIONAL)	  
	  //Mobile.scrollToText("Código de verificación", FailureHandling.CONTINUE_ON_FAILURE)
	  Swipe_Screen()
	  String CodigoNumber = ''
	  String GetCodigo = ''
	  
   if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/GetPositionType'), 2, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/GetPositionType1'), 3, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), 2, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), 1, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), 1, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), 1, FailureHandling.OPTIONAL)||Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/Credimas-GetCodigo'), 2, FailureHandling.OPTIONAL)) {
	   
	   if(Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/GetPositionType'), 3, FailureHandling.OPTIONAL)) {
		   CodigoNumber = Mobile.getText(findTestObject('TransferenciaPropia/GetPositionType'), 3, FailureHandling.OPTIONAL)
	   } else if(Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), 2, FailureHandling.OPTIONAL)) {
		   CodigoNumber = Mobile.getText(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), 2, FailureHandling.OPTIONAL)
	   } else if(Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/Credimas-GetCodigo'), 2, FailureHandling.OPTIONAL)) {
		   CodigoNumber = Mobile.getText(findTestObject('EnvioTuEfectivo/Credimas-GetCodigo'), 2, FailureHandling.OPTIONAL)
	   } else if(Mobile.waitForElementPresent(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), 1, FailureHandling.OPTIONAL)) {
		   CodigoNumber = Mobile.getText(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), 2, FailureHandling.OPTIONAL)
	   } else if(Mobile.waitForElementPresent(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), 1, FailureHandling.OPTIONAL)) {
		   CodigoNumber = Mobile.getText(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), 2, FailureHandling.OPTIONAL)
	   } else if(Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), 1, FailureHandling.OPTIONAL)) {
		   CodigoNumber = Mobile.getText(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), 2, FailureHandling.OPTIONAL)
	   } else {
		   CodigoNumber = Mobile.getText(findTestObject('TransferenciaPropia/GetPositionType1'), 3, FailureHandling.OPTIONAL)
	   }
	   println "CodigoNumber Is " + CodigoNumber	   
	   GetCodigo = CodigoNumber.replaceAll("[^0-9]+", "");
	   println "GetCodigo Is " + GetCodigo
	   
	   CustomKeywords.'helper.codigo.TarjetaCodigo_Cedula'(GetCodigo)
	   Mobile.delay(2)
	   println "CodeValue Is " + GlobalVariable.CodeValue
	   
	   if(Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/GetPositionType'), 3, FailureHandling.OPTIONAL)) {
		   	Mobile.setText(findTestObject('TransferenciaPropia/Set-CodeValue'), GlobalVariable.CodeValue, 3, FailureHandling.OPTIONAL)
	   		CustomKeywords.'helper.customfunction.HideKeyboard'()
	   } else if(Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), 1, FailureHandling.OPTIONAL)) {
		    Mobile.setText(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), GlobalVariable.CodeValue, 2, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
	   } else if(Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/Credimas-GetCodigo'), 1, FailureHandling.OPTIONAL)) {
		    Mobile.setText(findTestObject('EnvioTuEfectivo/Credimas-GetCodigo'), GlobalVariable.CodeValue, 3, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
	   } else if(Mobile.waitForElementPresent(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), 1, FailureHandling.OPTIONAL)) {
		    Mobile.setText(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), GlobalVariable.CodeValue, 2, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
	   } else if(Mobile.waitForElementPresent(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), 1, FailureHandling.OPTIONAL)) {
		    Mobile.setText(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), GlobalVariable.CodeValue, 2, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
	   } else if(Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), 1, FailureHandling.OPTIONAL)) {
		    Mobile.setText(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), GlobalVariable.CodeValue, 1, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
	   } else {
		   	Mobile.setText(findTestObject('TransferenciaPropia/Set-CodeValue1'), GlobalVariable.CodeValue, 3, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
	   }
	   CustomKeywords.'helper.customfunction.Screenshot'()
	   Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
	   
   } else {
//	  if(Description.contains('Prestamo')) {
//		   CustomKeywords.'helper.customfunction.Screenshot'()
//	  }
	  CustomKeywords.'helper.customfunction.Screenshot'()
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
  
def PrintResults() {
    'TRANSACTION DETAILS :'
	if(Description.contains('Envio TuEfectivo') || Description.contains('Retiro TuEfectivo')) {
		GlobalVariable.PaymentMessage = Mobile.getText(findTestObject('EnvioTuEfectivo/Completado Transaction Message'), 1, FailureHandling.OPTIONAL)
		WebUI.comment('### - TRANSACTION PAYMENT MESSAGE ### : ' + GlobalVariable.PaymentMessage )
	
		if(Mobile.verifyElementVisible(findTestObject('EnvioTuEfectivo/Completado Payment Date'), 1, FailureHandling.OPTIONAL)) {
			GlobalVariable.PaymentTime = Mobile.getText(findTestObject('EnvioTuEfectivo/Completado Payment Date'), 1, FailureHandling.OPTIONAL)
			WebUI.comment('### - PAYMENT TIME ### : ' + GlobalVariable.PaymentTime )
		} else {
			GlobalVariable.PaymentTime = Mobile.getText(findTestObject('PaymentSuccess/TransactionTime'), 1, FailureHandling.OPTIONAL)
			WebUI.comment('### - PAYMENT TIME ### : ' + GlobalVariable.PaymentTime )
		}
		
		GlobalVariable.PaymentReciept = "NO RECIEPT NUMBER"
		WebUI.comment('### - RECIEPT NUMBER ### : ' + GlobalVariable.PaymentReciept)
	
		GlobalVariable.PaymentAmount = Mobile.getText(findTestObject('EnvioTuEfectivo/Completado Amount'), 1, FailureHandling.OPTIONAL)
		WebUI.comment('### - MONTO AMOUNT ### : ' + GlobalVariable.PaymentAmount)
		
		String destinoName = Mobile.getText(findTestObject('EnvioTuEfectivo/Completado Destino Name'), 1, FailureHandling.OPTIONAL)
		String destinoNum = Mobile.getText(findTestObject('EnvioTuEfectivo/Completado Destino Number'), 1, FailureHandling.OPTIONAL)
		GlobalVariable.PaymentDestino = destinoName+' '+destinoNum
		WebUI.comment('### - DESTINO ACCOUNT ### : ' + GlobalVariable.PaymentDestino)
		
	} else {
		GlobalVariable.PaymentMessage = Mobile.getText(findTestObject('PaymentSuccess/TransactionMessage'), 1, FailureHandling.OPTIONAL)
	    WebUI.comment('### - TRANSACTION PAYMENT MESSAGE ### : ' + GlobalVariable.PaymentMessage )
	
		GlobalVariable.PaymentTime = Mobile.getText(findTestObject('PaymentSuccess/TransactionTime'), 1, FailureHandling.OPTIONAL)
		WebUI.comment('### - PAYMENT TIME ### : ' + GlobalVariable.PaymentTime )
		
		GlobalVariable.PaymentReciept = Mobile.getText(findTestObject('PaymentSuccess/Get-RecieptNumber'), 1, FailureHandling.OPTIONAL)
	    WebUI.comment('### - RECIEPT NUMBER ### : ' + GlobalVariable.PaymentReciept)
	
		GlobalVariable.PaymentAmount = Mobile.getText(findTestObject('PaymentSuccess/Amount'), 1, FailureHandling.OPTIONAL)
	    WebUI.comment('### - MONTO AMOUNT ### : ' + GlobalVariable.PaymentAmount)
		
		GlobalVariable.PaymentDestino = Mobile.getText(findTestObject('PaymentSuccess/DestinoAccount'), 1, FailureHandling.OPTIONAL)
	    WebUI.comment('### - DESTINO ACCOUNT ### : ' + GlobalVariable.PaymentDestino)
	}
	
}

def PaymentVerification() {
	Mobile.delay(5)
	//sms service error check
	for(int timer = 0; timer<50; timer++) {
		
		if(timer>15) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.CaseStatusMessage += '\n ***** STUCK ON PROCESS SCREEN OR USER GOT LOGGED OUT *****'
			KeywordUtil.markFailedAndStop('***** STUCK ON PROCESS SCREEN OR USER GOT LOGGED OUT *****')
		}
		
		if(Mobile.verifyElementVisible(findTestObject('PaymentMessages/Email service not available'), 1, FailureHandling.OPTIONAL)) {
			GlobalVariable.CaseStatusMessage += '\n SMS service error!!!'
			CustomKeywords.'helper.customfunction.Screenshot'()
			Mobile.tap(findTestObject('Buttons/ACEPTAR'), 3, FailureHandling.OPTIONAL)
			break;
		} else if(Mobile.verifyElementVisible(findTestObject('PaymentSuccess/Nueva'), 1, FailureHandling.OPTIONAL)) {
			if(Mobile.verifyElementVisible(findTestObject('Buttons/ACEPTAR'), 1, FailureHandling.OPTIONAL)) {
				GlobalVariable.CaseStatusMessage += '\n SMS service error!!!'
				CustomKeywords.'helper.customfunction.Screenshot'()
				Mobile.tap(findTestObject('Buttons/ACEPTAR'), 3, FailureHandling.OPTIONAL)
			}
			KeywordUtil.logInfo("Last screen loaded!!!")
			break;
		}
	}
	
	'PAYMENT TRANSACTION VERIFICATON : '
	if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/Creada Pendiente Autorizacion'), 5, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('PaymentMessages/Sometida. Pendiente de autorizacin'), 5, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('PaymentMessages/TRANSACCIN PROCESADA'), 5, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('PaymentMessages/La transaccin fue realizada'), 1, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('PaymentMessages/SOLICITUD PROCESADA'), 1, FailureHandling.OPTIONAL)) {
		GlobalVariable.PaymentStatus = "Passed"
		KeywordUtil.markPassed('Payment Transaction Success...!!!')
		CustomKeywords.'helper.customfunction.Screenshot'()
		PrintResults()
		
	} else if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/TRANSACCIN NO PROCESADA'), 1, FailureHandling.OPTIONAL)) {
		GlobalVariable.PaymentStatus = "Failed"
		KeywordUtil.markPassed('Payment Failed..' + Description)
		CustomKeywords.'helper.customfunction.Screenshot'()
		PrintResults()
		
	} else {
		PrintResults()
		ErrorCheck()
//		GlobalVariable.PaymentStatus = "Failed"
//		println('Non Payment Transaction Scenario - So Verification Skipped...!!!')
	}
	//CustomKeywords.'helper.customfunction.Screenshot'()
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


def ErrorCheck() {
	
	CustomKeywords.'helper.customfunction.Screenshot'()
	
//	if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/Cuenta de origen no tiene fondos suficientes'), 1, FailureHandling.OPTIONAL)) {
//		GlobalVariable.PaymentStatus = "Passed"
//		//GlobalVariable.PaymentMessage = Mobile.getText(findTestObject('PaymentMessages/Cuenta de origen no tiene fondos suficientes'), 5, FailureHandling.OPTIONAL)
//		//Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
//		KeywordUtil.markPassed('Payment Transaction Success..' + Description)
//		
//	} else if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/Por favor complete el campo transaccin no vlida'), 1, FailureHandling.OPTIONAL)) {
//		GlobalVariable.PaymentStatus = "Passed"
//		KeywordUtil.markPassed('Payment Transaction Success..' + Description)
//		PrintResults()
//		
//	} else if (Mobile.waitForElementPresent(findTestObject('AvanceDeEfectivo/El monto digitado es mayor a tu balance disponible'), 1, FailureHandling.OPTIONAL)) {
//		GlobalVariable.PaymentStatus = "Passed"
//		KeywordUtil.markPassed('Payment Transaction Success..' + Description)
//		PrintResults()
//		
//	} else 
	if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/WS199 - Ocurrio un error. Favor contactar el administrador del sistema'), 1, FailureHandling.OPTIONAL)) {
		KeywordUtil.markFailed('Payment Failed..' + Description)
		
	} else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/Actualmente no posees productos activos en Banreservas'), 1, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('ErrorMessages/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 1, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('Historico_TuEfectivo/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 1, FailureHandling.OPTIONAL)) {
		GlobalVariable.PaymentStatus = "Failed"
		//Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
		KeywordUtil.markFailed('Payment Failed..' + Description)
		PrintResults()
		
	} else if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/Por favor digita el cdigo de validacin'), 1, FailureHandling.OPTIONAL)) {
		GlobalVariable.PaymentStatus = "Failed"
		//Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
		KeywordUtil.markFailed('Payment Failed..' + Description)
		PrintResults()
		
	} else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/Error al validar la cuenta del beneficiario. Por favor verifica la cuenta e intenta nuevamente'), 1, FailureHandling.OPTIONAL)) {
		GlobalVariable.PaymentStatus = "Failed"
		//Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
		KeywordUtil.markFailed('Payment Failed..' + Description)
//		PrintResults()

	} else {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.PaymentStatus = "Failed"
		println('Non Payment Transaction Scenario - So Verification Skipped...!!!')
	}
}
