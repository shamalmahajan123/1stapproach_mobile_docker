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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC

Login()

def Login() {
    'LOGIN SCENARIOS '
    if (Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN-Updated'), 10, FailureHandling.STOP_ON_FAILURE)) {
        Mobile.tap(findTestObject('Login/IDENTIFICACIN-Updated'), 2, FailureHandling.OPTIONAL)

        Mobile.setText(findTestObject('Login/IDENTIFICACIN-Updated'), GlobalVariable.Username, 2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
		
        Mobile.delay(2)

        Mobile.tap(findTestObject('Buttons/CONTINUAR'), 2, FailureHandling.OPTIONAL)

        if (Mobile.waitForElementPresent(findTestObject('Login/CONTRASEA'), 2, FailureHandling.STOP_ON_FAILURE)) {
            if (Password == '') {
                Mobile.tap(findTestObject('Buttons/ACCEDER'), 2, FailureHandling.OPTIONAL)
            } else {
                Mobile.tap(findTestObject('Login/CONTRASEA'), 2, FailureHandling.OPTIONAL)

                Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 2, FailureHandling.OPTIONAL)
				CustomKeywords.'helper.customfunction.HideKeyboard'()
				
                Mobile.delay(2)

                Mobile.tap(findTestObject('Buttons/ACCEDER'), 2, FailureHandling.OPTIONAL)
            }
        }
        
        Mobile.delay(2)

        //CustomKeywords.'helper.customfunction.LoginResponses'()
        if(Mobile.waitForElementPresent(findTestObject('ErrorMessages/ERROR EN PROCESAMIENTO'), 30, FailureHandling.CONTINUE_ON_FAILURE)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.CaseStatusMessage+='\nERROR EN PROCESAMIENTO'
			KeywordUtil.markFailedAndStop('ERROR EN PROCESAMIENTO')		
		}

        String Aceptar = Mobile.getAttribute(findTestObject('Buttons/ACEPTAR'), 'enabled', 5, FailureHandling.OPTIONAL)

        if ((Aceptar == 'True') || (Aceptar == 'true')) {
            Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
        }
        
        Mobile.delay(2)

        if (Mobile.waitForElementPresent(findTestObject('TokenFlow/NO, GRACIAS'), 2, FailureHandling.OPTIONAL)) {
            Mobile.tap(findTestObject('TokenFlow/NO, GRACIAS'), 2, FailureHandling.OPTIONAL)
        } else if (Mobile.waitForElementPresent(findTestObject('Login/Registro de dispositivo'), 15, FailureHandling.OPTIONAL)) {
            KeywordUtil.markPassed('Valid Username Password Scenario Is Passed...!!!')
        } else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/USUARIO BLOQUEADO POR ADMIN'), 2, FailureHandling.STOP_ON_FAILURE) || 
        Mobile.waitForElementPresent(findTestObject('ErrorMessages/USUARIO RECHAZADO'), 2, FailureHandling.STOP_ON_FAILURE)) {
            KeywordUtil.markFailedAndStop('User Is Blocked...!!!')

            CustomKeywords.'helper.customfunction.Screenshot'()
        } else if (Mobile.waitForElementPresent(findTestObject('TokenFlow/MS TARDE'), 2, FailureHandling.OPTIONAL)) {
            Mobile.tap(findTestObject('TokenFlow/MS TARDE'), 2, FailureHandling.OPTIONAL)
        }
    }
}