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

GlobalVariable.BenefUnicoDetails = 'True'

if (CtaDestino == 'NA') {
   KeywordUtil.markPassed('Destino Account Not Available...!!!')
} else if(GlobalVariable.OrigenAccountStatus.equals("NotAvailable")) {
	KeywordUtil.markFailedAndStop('Given Origen Account is not Present in List - Origen Account not Selected - Skipping further flow!!!')
} else {
	GlobalVariable.destino_before_app = "Destino is PagoUnico - No Balance is visible"
	GlobalVariable.CurrencyTypeDestino = "No Currency Info"
	
	if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicios/Servicio'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('ImpuestosYServicios/Servicio'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(1)
		
		if(Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/Pago unico Button'), 5, FailureHandling.OPTIONAL)) {
			if (Mobile.verifyElementAttributeValue(findTestObject('PagoUnico_LBTR_ACH/Pago unico Button'), 'clickable',  'true', 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('PagoUnico_LBTR_ACH/Pago unico Button'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(1)
				CustomKeywords.'helper.customfunction.PagoUnicoHelper'(CtaDestino)
				
			} else {
				CustomKeywords.'helper.customfunction.Screenshot'()
				KeywordUtil.logInfo('Pago Unico Button Is Disabled...!!!')
				KeywordUtil.markFailed('Pago Unico Button Is Disabled For Case ' + Description)
			}
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.logInfo('Pago Unico Button Not Found...!!!')
			KeywordUtil.markFailed('Pago Unico Button Not Found For Case ' + Description)
		}
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.logInfo('Destino Tab Not Available...!!!')
		KeywordUtil.markFailed('Destino Tab Not Available For Case ' + Description)
	}
    
}
