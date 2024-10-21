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

'BENEF PRODUCTOS Y SERVICIOS : '
WebUI.comment((CaseNo + ' - ') + Description)

'PROFILE VERIFICATION FOR DATABASE CONNECTION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProfileVerification'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino], FailureHandling.STOP_ON_FAILURE)

'APPLICATION LAUNCH / USER LOGGED IN VERIFICATION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickAppLaunch'), [:], FailureHandling.STOP_ON_FAILURE)

Mobile.delay(5)

'USERLOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLogin'(Role, Cedula, Pasaporte, Password, Email)
Mobile.delay(10)
if ((Verification == 'Import') && Role == 'Pasaporte') {
	
   WebUI.callTestCase(findTestCase('TC-TokenDigitalMigration/TC_DigitalTokenMigration'), [('Verification') : Verification, ('Cedula') : Cedula,('Password') : Password,('Role') : Role,('Email') : Email], FailureHandling.CONTINUE_ON_FAILURE)

} else {
	
	'TAP ON HAMBURGER MENU : '
	Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
	
	'SELECT TRANSACCIONES : '
	if (Type == 'NA') {
		println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
		
	} else if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Configuraciones - Title'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Benef_ProductYServicios/Configuraciones - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
	} else {
		Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		
		if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Configuraciones - Title'), 5, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Benef_ProductYServicios/Configuraciones - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
		} else {
			Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Benef_ProductYServicios/Configuraciones - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
		}
	}
	
	if(Description.contains('Servicios')){
		Mobile.tap(findTestObject('Benef_ProductYServicios/Servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else if(Description.contains('Productos')){
		Mobile.tap(findTestObject('Benef_ProductYServicios/Productos - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
		
	if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Aadir beneficiario de producto'), 30, FailureHandling.OPTIONAL)) {
		
		if(Concept.contains('Agregar')) {
			
			'CHECK FOR EXISTING BENEFICIARY : '
			CheckExistingBenf()
			
			KeywordUtil.markPassed('Agregar beneficiario Button Is Displayed..!!')
			'ATTEMPT AGREGAR BENEFICIARIO : '
			Mobile.tap(findTestObject('Benef_ProductYServicios/Aadir beneficiario de producto'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			
			if (CtaDestino == '') {
				println('Destino Account Not Available...!!!')
			} else {
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProductosYServicios_AgregarBenef'), [('CtaDestino') : CtaDestino, ('Banco') : Banco, ('Moneda') : Moneda,
				('TipoDeProducto') : TipoDeProducto, ('CuentaDestino') : CuentaDestino, ('Description') : Description, ('TipoDeDocumento') : TipoDeDocumento,
				('Alias') : Alias, ('NumeroDeDocumento') : NumeroDeDocumento], FailureHandling.OPTIONAL)
			}
			
			'VERIFICATION CODIGO : '
			VerifyCodigo()
			
		} else if(Concept.contains('Eliminar')) {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProductosYServicios_EliminarBenef'), [('Alias') : Alias, ('Concept') : Concept, ('CtaDestino') : CtaDestino], FailureHandling.OPTIONAL)
		}
		
		if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/El beneficiario ha sido agregado'), 10, FailureHandling.OPTIONAL)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markPassed('Beneficiary Added Successfully...!!')
			Mobile.tap(findTestObject('Buttons/ACEPTAR'), 5, FailureHandling.OPTIONAL)
		} else {
			'VERIFICATION SCENARIO TEST CASE :'
			 CustomKeywords.'helper.paymenthelper.PaymentResponses'()
		}
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



def CheckExistingBenf() {
	try {
		//Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
		// check for obj also..
		//KeywordUtil.logInfo('Beneficiary For Account: ' + CtaDestino + ' Already Exist - Delete Existing Beneficiary..!!')
		//KeywordUtil.markPassed('Beneficiary For Account: ' + CtaDestino + ' Already Exist - Delete Existing Beneficiary..!!')
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProductosYServicios_EliminarBenef'), [('Alias') : Alias, ('Concept') : Concept, ('CtaDestino') : CtaDestino], FailureHandling.OPTIONAL)
		
		RefreshBenef()
	} catch(Exception e) {
		//println e.printStackTrace()
		e.printStackTrace()
		KeywordUtil.markPassed('Beneficiary For Account: ' + CtaDestino + ' Does Not Exist - User Can Add Beneficiary..!!')
	}
}

def RefreshBenef() {
	//Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
	if (Mobile.waitForElementPresent(findTestObject('Buttons/Back-ArrowButton'), 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else if (Mobile.waitForElementPresent(findTestObject('NavigateBack/Back'), 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('NavigateBack/Back'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else if (Mobile.waitForElementPresent(findTestObject('PaymentSuccess/BackButton'), 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('PaymentSuccess/BackButton'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else {
		Mobile.pressBack(FailureHandling.OPTIONAL)
	}
	
	Mobile.delay(1)
	
	if(Description.contains('Servicios')){
		Mobile.tap(findTestObject('Benef_ProductYServicios/Servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else if(Description.contains('Productos')){
		Mobile.tap(findTestObject('Benef_ProductYServicios/Productos - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
}

def VerifyCodigo() {
	if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Button - Guardar'), 5, FailureHandling.OPTIONAL)) {
		
	  CustomKeywords.'helper.customfunction.swipeUP'()
	  String CodigoNumber = ''
	  String GetCodigo = ''
		  
	  if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/ProductosYServicios-GetCodigoType'), 3, FailureHandling.OPTIONAL) 
		  || Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/GetPositionType'), 2, FailureHandling.OPTIONAL) 
		  || Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/GetPositionType1'), 3, FailureHandling.OPTIONAL) 
		  || Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), 2, FailureHandling.OPTIONAL) 
		  || Mobile.waitForElementPresent(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), 1, FailureHandling.OPTIONAL) 
		  || Mobile.waitForElementPresent(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), 1, FailureHandling.OPTIONAL) 
		  || Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), 1, FailureHandling.OPTIONAL)) {
		   
		    if(Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/ProductosYServicios-GetCodigoType'), 3, FailureHandling.OPTIONAL)) {
			   CodigoNumber = Mobile.getText(findTestObject('Benef_ProductYServicios/ProductosYServicios-GetCodigoType'), 3, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/GetPositionType'), 3, FailureHandling.OPTIONAL)) {
			   CodigoNumber = Mobile.getText(findTestObject('TransferenciaPropia/GetPositionType'), 3, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), 2, FailureHandling.OPTIONAL)) {
			   CodigoNumber = Mobile.getText(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), 2, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), 2, FailureHandling.OPTIONAL)) {
			   CodigoNumber = Mobile.getText(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), 2, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), 2, FailureHandling.OPTIONAL)) {
			   CodigoNumber = Mobile.getText(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), 2, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), 1, FailureHandling.OPTIONAL)) {
			   CodigoNumber = Mobile.getText(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), 1, FailureHandling.OPTIONAL)
		   } else {
			   CodigoNumber = Mobile.getText(findTestObject('TransferenciaPropia/GetPositionType1'), 3, FailureHandling.OPTIONAL)
		   }
		   println "CodigoNumber Is " + CodigoNumber
		   GetCodigo = CodigoNumber.replaceAll("[^0-9]+", "");
		   println "GetCodigo Is " + GetCodigo
		   
		   CustomKeywords.'helper.codigo.TarjetaCodigo_Cedula'(GetCodigo)
		   Mobile.delay(2)
		   println "CodeValue Is " + GlobalVariable.CodeValue
		   
		   if(Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/ProductosYServicios-GetCodigoType'), 3, FailureHandling.OPTIONAL)) {
				   Mobile.setText(findTestObject('Benef_ProductYServicios/ProductosYServicios-GetCodigoType'), GlobalVariable.CodeValue, 3, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/GetPositionType'), 3, FailureHandling.OPTIONAL)) {
				   Mobile.setText(findTestObject('TransferenciaPropia/Set-CodeValue'), GlobalVariable.CodeValue, 3, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), 2, FailureHandling.OPTIONAL)) {
				Mobile.setText(findTestObject('PagoUnico_LBTR_ACH/GetCodigoTypeNumberTwo'), GlobalVariable.CodeValue, 2, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), 2, FailureHandling.OPTIONAL)) {
				Mobile.setText(findTestObject('Object Repository/AvanceDeEfectivo/CodigoField_Avance_de_efectivo'), GlobalVariable.CodeValue, 2, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), 2, FailureHandling.OPTIONAL)) {
				Mobile.setText(findTestObject('Object Repository/EnvioTuEfectivo/CodigoField_Envio_TuEfectivo'), GlobalVariable.CodeValue, 2, FailureHandling.OPTIONAL)
		   } else if(Mobile.waitForElementPresent(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), 1, FailureHandling.OPTIONAL)) {
				Mobile.setText(findTestObject('EnvioTuEfectivo/CodigoField_Retiro_TuEfectivo'), GlobalVariable.CodeValue, 1, FailureHandling.OPTIONAL)
		   } else {
				   Mobile.setText(findTestObject('TransferenciaPropia/Set-CodeValue1'), GlobalVariable.CodeValue, 3, FailureHandling.OPTIONAL)
		   }
		   //CustomKeywords.'helper.customfunction.Screenshot'()
		   Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Guardar'), 3, FailureHandling.OPTIONAL)
		   Mobile.delay(1)
		   if(Mobile.waitForElementPresent(findTestObject('PagoUnico/Nombre - Title'), 5, FailureHandling.OPTIONAL)) {
			   //CustomKeywords.'helper.customfunction.swipeUP'()
			   CustomKeywords.'helper.customfunction.Screenshot'()
			   Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Guardar'), 3, FailureHandling.OPTIONAL)
		   } else {
			   CustomKeywords.'helper.customfunction.Screenshot'()
		   }
		   
	   } else {
		  KeywordUtil.markPassed('Codigo Is Not Necessary For Test Case ' + Description)
		  Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Guardar'), 3, FailureHandling.OPTIONAL)
		  Mobile.delay(2)
		  Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Guardar'), 3, FailureHandling.OPTIONAL)
	   }
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed('Failed To Enter Codigo..!!')
		Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Guardar'), 3, FailureHandling.OPTIONAL)
		Mobile.delay(2)
		Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Guardar'), 3, FailureHandling.OPTIONAL)
	}
}
