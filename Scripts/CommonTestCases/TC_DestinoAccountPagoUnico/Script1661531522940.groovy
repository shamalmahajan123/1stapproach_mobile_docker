import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

GlobalVariable.BenefUnicoDetails = "True"
GlobalVariable.destino_after_db = "No Info For Pago Unico Acct " + CtaDestino
GlobalVariable.destino_before_db = "No Info Pago Unico For Acct " + CtaDestino
GlobalVariable.destino_before_app = "Destino is PagoUnico - No Balance is visible"
GlobalVariable.CurrencyTypeDestino = "No Currency Info"

if (CtaDestino == 'NA') {
   KeywordUtil.markPassed('Destino Account Not Available...!!!')
} else if(GlobalVariable.OrigenAccountStatus.equals("NotAvailable")) {
	KeywordUtil.markFailed('Given Origen Account is not Present in List Origen Account not Selected Skipping further flow!!!')
} else {
    Mobile.tap(findTestObject('TransferenciaPropia/Destino'), 5, FailureHandling.OPTIONAL)
    Mobile.delay(1)
	
	if (Mobile.verifyElementAttributeValue(findTestObject('PagoUnico_LBTR_ACH/Pago unico Button'), 'clickable',  'true', 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('PagoUnico_LBTR_ACH/Pago unico Button'), 2, FailureHandling.OPTIONAL)
	
		if(Banco == 'BanReservas' || Banco == 'Banreservas' || Banco == '') {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoBanco'), [('Banco') : Banco], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoMoneda'), [('Moneda') : Moneda], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoTipoDeProducto'), [('TipoDeProducto') : TipoDeProducto], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoReferenceNumber'), [('CuentaDestino') : CuentaDestino], FailureHandling.STOP_ON_FAILURE)
				
			CustomKeywords.'helper.customfunction.Screenshot'()
			
			Mobile.tap(findTestObject('Object Repository/Buttons/Confirmar'), 5, FailureHandling.OPTIONAL)
			
			Mobile.delay(1)
			
			if(Mobile.waitForElementPresent(findTestObject('Object Repository/PagoUnico/Nombre - Title'), 15, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Object Repository/Buttons/Confirmar'), 15, FailureHandling.OPTIONAL)
			}
			
		} else {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoBanco'), [('Banco') : Banco], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoMoneda'), [('Moneda') : Moneda], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoTipoDeProducto'), [('TipoDeProducto') : TipoDeProducto], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoReferenceNumber'), [('CuentaDestino') : CuentaDestino], FailureHandling.STOP_ON_FAILURE)
				
			CustomKeywords.'helper.customfunction.Screenshot'()
			
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoTipoDeDocumento'), [('TipoDeDocumento') : TipoDeDocumento], FailureHandling.STOP_ON_FAILURE)
			
			if(TipoDeDocumento == 'RNC' || TipoDeDocumento == 'Cédula') {
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoNumeroDeDocumento'), [('NumeroDeDocumento') : NumeroDeDocumento], FailureHandling.STOP_ON_FAILURE)
			} else if(TipoDeDocumento == 'Pasaporte'){
				CustomKeywords.'helper.customfunction.swipeUP'()
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoNumeroDeDocumento'), [('NumeroDeDocumento') : NumeroDeDocumento], FailureHandling.STOP_ON_FAILURE)
				Mobile.tap(findTestObject('Object Repository/PagoUnico_LBTR_ACH/Ingresa nombre del beneficiario'), 1, FailureHandling.CONTINUE_ON_FAILURE)
				Mobile.setText(findTestObject('Object Repository/PagoUnico_LBTR_ACH/Ingresa nombre del beneficiario'), Banco, 2, FailureHandling.STOP_ON_FAILURE)
				CustomKeywords.'helper.customfunction.HideKeyboard'()
			}

			CustomKeywords.'helper.customfunction.Screenshot'()
			
			Mobile.tap(findTestObject('Object Repository/Buttons/Confirmar'), 5, FailureHandling.OPTIONAL)
			
			Mobile.delay(1)
		}
	} else {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.logInfo('Pago Unico Button Is Disabled...!!!')
			KeywordUtil.markFailed('Pago Unico Button Is Disabled For Case ' + Description)
	}
}
