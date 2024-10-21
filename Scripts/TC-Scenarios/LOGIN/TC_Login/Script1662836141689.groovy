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
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger

Login() 

def Login() {
    if (GlobalVariable.Authentication == 'Pasaporte') {
        GlobalVariable.Username = Pasaporte
    } else if (GlobalVariable.Authentication == 'Cedula') {
        GlobalVariable.Username = Cedula
    }
    
    'LOGIN SCENARIOS '
    KeywordUtil.logInfo((ID + ' - ') + Description)

    if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 10, FailureHandling.STOP_ON_FAILURE) || Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN-Updated'), 10, FailureHandling.STOP_ON_FAILURE)) {
        Mobile.tap(findTestObject('Login/IDENTIFICACIN-Updated'), 2, FailureHandling.OPTIONAL)

        KeywordUtil.logInfo('Set Username : ' + GlobalVariable.Username)

        Mobile.setText(findTestObject('Login/IDENTIFICACIN-Updated'), GlobalVariable.Username, 2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
		
        Mobile.delay(1)

		CustomKeywords.'helper.customfunction.Screenshot'()
		
        Mobile.tap(findTestObject('Buttons/CONTINUAR'), 2, FailureHandling.OPTIONAL)

        if (Mobile.waitForElementPresent(findTestObject('Login/CONTRASEA'), 2, FailureHandling.OPTIONAL)) {
            if (Password == '') {
                KeywordUtil.logInfo('Set Password : ' + Password)

                CustomKeywords.'helper.customfunction.Screenshot'()

                Mobile.delay(1)

                Mobile.tap(findTestObject('Buttons/ACCEDER'), 2, FailureHandling.OPTIONAL)
            } else {
                Mobile.tap(findTestObject('Login/CONTRASEA'), 2, FailureHandling.OPTIONAL)

                KeywordUtil.logInfo('Set Password : ' + Password)

                Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 2, FailureHandling.OPTIONAL)
				CustomKeywords.'helper.customfunction.HideKeyboard'()
				
                Mobile.delay(1)

                CustomKeywords.'helper.customfunction.Screenshot'()

                Mobile.tap(findTestObject('Buttons/ACCEDER'), 2, FailureHandling.OPTIONAL)
            }
        }
        
        //CustomKeywords.'helper.customfunction.LoginResponses'()

        CustomKeywords.'helper.customfunction.Screenshot'()

        Mobile.delay(2)

        if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/NO EXISTE DATOS PARA ESTA CONSULTA'), 2, FailureHandling.OPTIONAL)) {
            CustomKeywords.'helper.customfunction.Screenshot'()

            Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.STOP_ON_FAILURE)

            KeywordUtil.markFailedAndStop('There Is No Data For This Query...!!!')
        }
        
        Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)

        Mobile.delay(2)

        if (Mobile.waitForElementPresent(findTestObject('Login/Registro de dispositivo'), 2, FailureHandling.OPTIONAL)) {
            KeywordUtil.markPassed('Valid Username Password Scenario Is Passed...!!!')

            Phone()
        } else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/USUARIO BLOQUEADO POR ADMIN'), 2, FailureHandling.STOP_ON_FAILURE) || Mobile.waitForElementPresent(findTestObject('ErrorMessages/USUARIO RECHAZADO'), 2, FailureHandling.STOP_ON_FAILURE)) {
            KeywordUtil.markFailedAndStop('User Is Blocked...!!!')

            CustomKeywords.'helper.customfunction.Screenshot'()
        } else if (Mobile.waitForElementPresent(findTestObject('Login/ClearFields'), 2, FailureHandling.OPTIONAL)) {
            Mobile.tap(findTestObject('Login/ClearFields'), 2, FailureHandling.OPTIONAL)

            Mobile.delay(2)

            Mobile.tap(findTestObject('Buttons/DESVINCULAR'), 2, FailureHandling.OPTIONAL)
        } else if (Mobile.waitForElementPresent(findTestObject('Login/ClearFields1'), 2, FailureHandling.OPTIONAL)) {
            Mobile.tap(findTestObject('Login/ClearFields1'), 2, FailureHandling.OPTIONAL)

            Mobile.delay(2)

            Mobile.tap(findTestObject('Buttons/DESVINCULAR'), 2, FailureHandling.OPTIONAL)
        } else {
            CustomKeywords.'helper.customfunction.Screenshot'()

            Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
        }
    } else if (Mobile.waitForElementPresent(findTestObject('Login/Registro de dispositivo'), 25, FailureHandling.STOP_ON_FAILURE)) {
        KeywordUtil.markPassed('Valid Username Password Scenario Is Passed...!!!')

        Mobile.delay(2)

        Phone()
    } else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/USUARIO BLOQUEADO POR ADMIN'), 2, FailureHandling.STOP_ON_FAILURE) || Mobile.waitForElementPresent(findTestObject('ErrorMessages/USUARIO RECHAZADO'), 2, FailureHandling.STOP_ON_FAILURE)) {
            KeywordUtil.markFailedAndStop('User Is Blocked...!!!')

            CustomKeywords.'helper.customfunction.Screenshot'()
    } else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/NO EXISTE DATOS PARA ESTA CONSULTA'), 2, FailureHandling.STOP_ON_FAILURE)) {
        CustomKeywords.'helper.customfunction.Screenshot'()

        Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.STOP_ON_FAILURE)

        KeywordUtil.markFailedAndStop('There Is No Data For This Query...!!!')
    } else {
        CustomKeywords.'helper.customfunction.Screenshot'()

        KeywordUtil.markFailedAndStop('Login Failed - Terminating The Test Case..!!!')
    }
}

def Phone() {
    String Value = Mobile.getAttribute(findTestObject('Login/GetPhone'), 'enabled', 3, FailureHandling.OPTIONAL)

    GlobalVariable.Phone = Value

    println('GlobalVariable.Phone ' + GlobalVariable.Phone)
}