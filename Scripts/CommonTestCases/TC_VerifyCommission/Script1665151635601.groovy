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

if (Mobile.waitForElementPresent(findTestObject('PagoUnico_LBTR_ACH/Cargos por comisiones'), 2, FailureHandling.OPTIONAL)) {
	KeywordUtil.markPassed('Commission Is Applicable...!!!')
} else {
	KeywordUtil.markWarning('Commission Is Not Applicable...!!!')
}

//Impuestos
if(Mobile.verifyElementVisible(findTestObject('Object Repository/TransferenciaTerceroBeneficiary/Impuesto title confirmation screen'), 2, FailureHandling.OPTIONAL)) {
	KeywordUtil.markPassed("***** IMPUESTOS IS PRESENT ON CONFIRMATION SCREEN *****")
} else {
	KeywordUtil.markWarning("***** IMPUESTOS IS NOT PRESENT ON CONFIRMATION SCREEN *****")
}