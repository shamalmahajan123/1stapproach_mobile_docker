import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.annotation.Keyword as Keyword

'SERVICIOS :'
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
	if (Type == 'NA') {
		println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
	} else if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
		Mobile.delay(2)
	
		Mobile.tap(findTestObject('ImpuestosYServicios/Impuestos y servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(2)
		Mobile.tap(findTestObject('Servicios/Servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		
	} else {
		Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		
		if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('ImpuestosYServicios/Impuestos y servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(2)
			Mobile.tap(findTestObject('Servicios/Servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		
		} else {
			Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('TransferenciaPropia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(2)
			Mobile.tap(findTestObject('ImpuestosYServicios/Impuestos y servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(2)
		Mobile.tap(findTestObject('Servicios/Servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		
			//CustomKeywords.'helper.customfunction.Screenshot'()
		}
	}
	if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Origen'), 30, FailureHandling.OPTIONAL)) {
		'ATTEMPT TRANSACTION PAYMENTS : '
	
		'SELECT ORIGEN ACCOUNT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_OrigenAccount'), [('CtaOrigen') : CtaOrigen, ('Monto') : Monto], FailureHandling.STOP_ON_FAILURE)
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
				e.printStackTrace()
			}
		}
		
		'SELECT SERVICIO'
		Mobile.tap(findTestObject('Servicios/Servicio'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(1)
		
		CustomKeywords.'helper.customfunction.DestinoAccount'(CtaDestino)
			
		'ENTER MONTO FOR PAYMENT : '
		println "Monto For Transaction Is : " + Monto + '.'+ Decimal
		GlobalVariable.MontoDecimal = Monto + '.'+ Decimal
	
		MontoSelection()

		'REPLACE MONTO VALUE : '
		String checkMonto = GlobalVariable.MontoDecimal
		if(checkMonto.matches(".*[a-zA-Z]+.*")) {
			String preDefinedMonto = Mobile.getText(findTestObject('Servicios/Monto on Datos Screen'), 1, FailureHandling.OPTIONAL)
			String Balance1 = preDefinedMonto.replaceAll('[,]', '')
			String Balance2 = Balance1.substring(4)
			GlobalVariable.MontoDecimal = Balance2
		}
		
			'TASA VERIFICATION : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
			
			'ENTER CONCEPT FOR PAYMENT : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept, ('Description') : Description],
				FailureHandling.STOP_ON_FAILURE)
			
			'CHECK IMPUESTOS/COMMISSION IS PRESENT OR NOT : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyCommission'), [:], FailureHandling.OPTIONAL)
		
			'DATOS SCREENSHOT'
			CustomKeywords.'helper.customfunction.Screenshot'()

			'VERIFICATION CODIGO : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification'), [('Monto') : Monto, ('Password') : Password, ('Description') : Description], FailureHandling.OPTIONAL)
		
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

def MontoSelection() {
	Mobile.tap(findTestObject('Servicios/Monto'), 1, FailureHandling.CONTINUE_ON_FAILURE)
	if (Mobile.waitForElementPresent(findTestObject('Servicios/Monto a pagar'), 15, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Servicios/DOP 500.00'),1,FailureHandling.OPTIONAL)
		
		if(Mobile.waitForElementPresent(findTestObject('Servicios/Monto mximo'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Servicios/Monto mximo'), 1)
		}
		if(Mobile.waitForElementPresent(findTestObject('Servicios/Monto mnimo'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Servicios/Monto mnimo'), 1)
		}
		if(Mobile.waitForElementPresent(findTestObject('Servicios/Balance total'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Servicios/Balance total'), 1)
		}
	}
	else if (Mobile.waitForElementPresent(findTestObject('Servicios/Elegir factura - Title'), 60, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Servicios/Elegir Selection'),1,FailureHandling.OPTIONAL)
		
		if (Mobile.waitForElementPresent(findTestObject('Object Repository/Servicios/Monto a pagar'), 60, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Servicios/Monto total'),1,FailureHandling.OPTIONAL)
		Mobile.tap(findTestObject('Servicios/Monto pendiente'),1,FailureHandling.OPTIONAL)
		}
	}

else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/Ocurrio un error. Favor contactar el administrador del sistema'), 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Object Repository/Buttons/ACEPTAR'), 5, FailureHandling.OPTIONAL)
	KeywordUtil.markFailedAndStop("Terminating Test Case Due To Error Message...!!!")
	}
	else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/Referencia no encontrada'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Object Repository/Buttons/ACEPTAR'), 5, FailureHandling.OPTIONAL)
		KeywordUtil.markFailedAndStop("Terminating Test Case Due To Error Message...!!!")
		}
	
}

'CLOSING THE APPLICATION : '
Mobile.closeApplication()