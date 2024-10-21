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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

Concept()

def Concept() {
    if (Concept == 'NA') {
        println('Concept Not Necessary For TestCase ' + Description)
		Mobile.tap(findTestObject('Buttons/Confirmar'), 2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.GetCommisones'()
		CustomKeywords.'helper.customfunction.GetImpuesto'()
		
    } else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
		GlobalVariable.origen_before_app = "SelectionFailed"
		GlobalVariable.OrigenAccountStatus = "NotAvailable"
		Mobile.tap(findTestObject('Buttons/Confirmar'), 2, FailureHandling.OPTIONAL)
        println('Origen Account Not Available - Skipping Concept Flow...!!!')
		CustomKeywords.'helper.customfunction.GetCommisones'()
		CustomKeywords.'helper.customfunction.GetImpuesto'()
		
    } else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
		GlobalVariable.destino_before_app = "SelectionFailed"
		GlobalVariable.DestinoAccountStatus = "NotAvailable"
		Mobile.tap(findTestObject('Buttons/Confirmar'), 2, FailureHandling.OPTIONAL)
        println('Destino Account Not Available - Skipping Concept Flow...!!!')
		CustomKeywords.'helper.customfunction.GetCommisones'()
		CustomKeywords.'helper.customfunction.GetImpuesto'()
		
    } else {
        Mobile.tap(findTestObject('TransferenciaPropia/Digita un concepto'), 2, FailureHandling.OPTIONAL)
        Mobile.setText(findTestObject('TransferenciaPropia/android.widget.EditText - Digita un concepto'), Concept, 2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
		Mobile.tap(findTestObject('Servicios/Digita el concepto'), 2, FailureHandling.OPTIONAL)
		Mobile.setText(findTestObject('Servicios/Digita el concepto'), Concept, 2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
		
		if(Mobile.waitForElementPresent(findTestObject('PaymentMessages/No se permiten transacciones en monedas distintas'), 2, FailureHandling.OPTIONAL)){
			println('Error Occured - No se permiten transacciones en monedas distintas..!!')
			
		} else if(Description.contains('ACH') || Description.contains('LBTR') || Concept.contains('ACH') || Concept.contains('LBTR')){
			
			if(Mobile.waitForElementPresent(findTestObject('Interbancario_Beneficiary/android.widget.EditText - ACH'), 1, FailureHandling.OPTIONAL)
				|| Mobile.waitForElementPresent(findTestObject('Interbancario_Beneficiary/android.widget.EditText - Pago al Instante BCRD'), 1, FailureHandling.OPTIONAL)
				|| Mobile.waitForElementPresent(findTestObject('Interbancario_Beneficiary/android.widget.TextView - Mtodo de pago'), 1, FailureHandling.OPTIONAL)){
				CustomKeywords.'helper.customfunction.Screenshot'()
			} else {
				println('Datos Screen Not Found..!!')
				KeywordUtil.markPassed('Datos Screen Not Found..!!')
			}
		}
		
        Mobile.tap(findTestObject('Buttons/Confirmar'), 2, FailureHandling.OPTIONAL)
        Mobile.delay(3)
		
		CustomKeywords.'helper.customfunction.GetCommisones'()
		CustomKeywords.'helper.customfunction.GetImpuesto'()
		
		//Mobile.delay(2)
    }
}