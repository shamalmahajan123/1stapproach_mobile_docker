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

if (CtaDestino == 'NA'|| CtaDestino == '') {
   KeywordUtil.markPassed('Destino Account Not Available...!!!')
   GlobalVariable.destino_before_app = "SelectionFailed"
	GlobalVariable.DestinoAccountStatus = "NotAvailable"
} else {
	GlobalVariable.destino_before_app = "Destino Is Beneficiario - No Balance Is Visible"
	GlobalVariable.CurrencyTypeDestino = "No Currency Info"
	
	if(Description.contains('Servicios')){
		
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Servicios_SelectImpuestos'), [('Impuestos') : Alias, ('ReferenceNo') : CtaDestino], FailureHandling.CONTINUE_ON_FAILURE)
		
	} else if(Description.contains('Productos')){
		
		if (Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/Banco Title'), 5, FailureHandling.OPTIONAL)) {
				
			if(Banco == 'BanReservas' || Banco == 'Banreservas' || Banco == '') {
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoBanco'), [('Banco') : Banco], FailureHandling.STOP_ON_FAILURE)
			
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoMoneda'), [('Moneda') : Moneda], FailureHandling.STOP_ON_FAILURE)
			
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoTipoDeProducto'), [('TipoDeProducto') : TipoDeProducto], FailureHandling.STOP_ON_FAILURE)
			
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoReferenceNumber'), [('CuentaDestino') : CuentaDestino], FailureHandling.STOP_ON_FAILURE)
				
				Mobile.setText(findTestObject('Benef_ProductYServicios/Introduce un alias para los productos'), Alias, 5, FailureHandling.OPTIONAL)
				CustomKeywords.'helper.customfunction.HideKeyboard'()
				
			} else {
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoBanco'), [('Banco') : Banco], FailureHandling.STOP_ON_FAILURE)
			
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoMoneda'), [('Moneda') : Moneda], FailureHandling.STOP_ON_FAILURE)
			
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoTipoDeProducto'), [('TipoDeProducto') : TipoDeProducto], FailureHandling.STOP_ON_FAILURE)
			
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoReferenceNumber'), [('CuentaDestino') : CuentaDestino], FailureHandling.STOP_ON_FAILURE)
					
				Mobile.setText(findTestObject('Benef_ProductYServicios/Introduce un alias para los productos'), Alias, 5, FailureHandling.OPTIONAL)
				CustomKeywords.'helper.customfunction.HideKeyboard'()
				
				CustomKeywords.'helper.customfunction.Screenshot'()
				
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoTipoDeDocumento'), [('TipoDeDocumento') : TipoDeDocumento], FailureHandling.STOP_ON_FAILURE)
				
//				if(TipoDeDocumento == 'RNC' || TipoDeDocumento == 'Cédula') {
//					WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoNumeroDeDocumento'), [('NumeroDeDocumento') : NumeroDeDocumento], FailureHandling.STOP_ON_FAILURE)
//				} else if(TipoDeDocumento == 'Pasaporte'){
				CustomKeywords.'helper.customfunction.swipeUP'()
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoUnicoNumeroDeDocumento'), [('NumeroDeDocumento') : NumeroDeDocumento], FailureHandling.STOP_ON_FAILURE)
				
				if(Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/Ingresa nombre del beneficiario'), 3, FailureHandling.OPTIONAL)) {
					if(Mobile.getAttribute(findTestObject('PagoUnico_LBTR_ACH/Ingresa nombre del beneficiario'), 'enabled', 3, FailureHandling.OPTIONAL)=='true') {
						Mobile.tap(findTestObject('PagoUnico_LBTR_ACH/Ingresa nombre del beneficiario'), 1, FailureHandling.OPTIONAL)
						Mobile.setText(findTestObject('PagoUnico_LBTR_ACH/Ingresa nombre del beneficiario'), Banco, 2, FailureHandling.OPTIONAL)
						CustomKeywords.'helper.customfunction.HideKeyboard'()
					} else {
						Mobile.setText(findTestObject('PagoUnico_LBTR_ACH/Ingresa nombre del beneficiario'), Banco, 2, FailureHandling.OPTIONAL)
						CustomKeywords.'helper.customfunction.HideKeyboard'()
						KeywordUtil.logInfo('Ingresa nombre del beneficiario Is Disabled - Unable To Enter Nombre...!!!')
						println('Ingresa nombre del beneficiario Is Disabled - Unable To Enter Nombre...!!!')
						//KeywordUtil.markFailed('Ingresa nombre del beneficiario Is Disabled - Unable To Create Beneficiary For Case ' + Description)
					}
				}
				//}
			}
		} else {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.logInfo('Banco Title Is Disabled - Unable To Create Beneficiary...!!!')
			KeywordUtil.markFailed('Banco Title Is Disabled - Unable To Create Beneficiary For Case ' + Description)
		}
	}
}
