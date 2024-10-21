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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.annotation.Keyword as Keyword
double Balance2;

if (CtaOrigen == 'NA') {
    println('Origen Account Not Available...!!!')
} else {
    Mobile.tap(findTestObject('TransferenciaPropia/Origen'), 5, FailureHandling.OPTIONAL)

    Mobile.delay(1)
    CustomKeywords.'helper.customfunction.OrigenAccount'(CtaOrigen)
	Mobile.delay(2)

	if (Monto == 'NA') {
		println('Monto Is Not Available...!!!')		
	} else {
		double Balance1;
		try {
		  Balance1 = Double.parseDouble(GlobalVariable.origen_before_app)
		  Balance2 = Double.parseDouble(Monto)
		  
		} catch (Exception e) {
			KeywordUtil.markWarning("Origen Account not available!")
		}
		if (Balance1 < Balance2 || Balance1 < 100) {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			//KeywordUtil.markFailedAndStop("Origen Balance Is Less Then Monto Amount - Terminating The Test Case...!!!")
		} else {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markPassed("Origen Balance Has Sufficient Balance - " + Balance1)
		}
	}
}