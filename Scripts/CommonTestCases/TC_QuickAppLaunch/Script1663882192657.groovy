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

'APPLICATION LAUNCH : '
Mobile.startApplication(GlobalVariable.AppPath, false)

'VERIFY USER IS LOGGED IN / LOGGED OUT : '
if (Mobile.waitForElementPresent(findTestObject('Debugger/CANCEL'), 60, FailureHandling.OPTIONAL)) {
    KeywordUtil.markPassed('Debugger Is Displaying - Attempting To Cancel It')

    Mobile.tap(findTestObject('Debugger/CANCEL'), 15, FailureHandling.OPTIONAL)

    Mobile.delay(2)

    for (int i = 1; i <= 2; i++) {
		
        if(Mobile.waitForElementPresent(findTestObject('ErrorMessages/Invalid Json response was returned (msg)'), 5, FailureHandling.CONTINUE_ON_FAILURE) || Mobile.waitForElementPresent(findTestObject('PaymentMessages/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
			
			Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			
		}
		
		if (((Mobile.waitForElementPresent(findTestObject('QuickAccess/Vista Rpida'), 5, FailureHandling.CONTINUE_ON_FAILURE) || 
        Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 5, FailureHandling.CONTINUE_ON_FAILURE)) || Mobile.waitForElementPresent(
            findTestObject('TokenFlow/App Banreservas'), 5, FailureHandling.STOP_ON_FAILURE)) || Mobile.waitForElementPresent(
            findTestObject('Login/IDENTIFICACIN-Updated'), 120, FailureHandling.STOP_ON_FAILURE)) {
            KeywordUtil.markPassed('User Is Logged In Application Personal...!!!')

            Mobile.tap(findTestObject('Buttons/ACCEDER'), 3, FailureHandling.OPTIONAL)

            if(Mobile.waitForElementPresent(findTestObject('ErrorMessages/ERROR EN PROCESAMIENTO'), 30, FailureHandling.CONTINUE_ON_FAILURE)) {
					CustomKeywords.'helper.customfunction.Screenshot'()
					GlobalVariable.CaseStatusMessage+='\nERROR EN PROCESAMIENTO'
					KeywordUtil.markFailedAndStop('ERROR EN PROCESAMIENTO')
					
			}
            
            break
        } else if (Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 5, FailureHandling.CONTINUE_ON_FAILURE)) {
            KeywordUtil.markPassed('User Is Not Logged In Application Personal...!!!')

            Mobile.delay(1)

            break
        } else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/An error occurred while making the request. Please check device connectivity, server url and request parameters'), 
            5, FailureHandling.CONTINUE_ON_FAILURE)) {
            CustomKeywords.'helper.customfunction.Screenshot'()

            Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)

            KeywordUtil.markFailedAndStop('Error Occured...!!!')

            Mobile.delay(1)

            break
        } else {
            Mobile.delay(2)

            if (i == 2) {
                CustomKeywords.'helper.customfunction.Screenshot'()

                KeywordUtil.markFailedAndStop('Vista Rapid Screen Not Found - App Still Loading...!!!')
            }
            
            KeywordUtil.logInfo('Vista Rapid Screen Not Found - Attempt No - ' + i)
        }
    }
} else if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 10, FailureHandling.OPTIONAL)|| Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN-Updated'), 120, FailureHandling.STOP_ON_FAILURE)) {
    KeywordUtil.markPassed('Application Launched Successfully...!!!')

    'Personal Application Launched Successfull'
    if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Vista Rpida'), 20, FailureHandling.CONTINUE_ON_FAILURE) || 
    Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 20, FailureHandling.CONTINUE_ON_FAILURE)) {
        Mobile.tap(findTestObject('Buttons/ACCEDER'), 3, FailureHandling.OPTIONAL)

        KeywordUtil.markPassed('User Is Logged In Application Personal...!!!')
    } else if (Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 5, FailureHandling.CONTINUE_ON_FAILURE)) {
        Mobile.delay(2)

        KeywordUtil.markPassed('User Is Not Logged In Application Personal...!!!')
    }
} else {
    Mobile.delay(2)

    KeywordUtil.markFailedAndStop('Vista Rapid Screen Not Found - Please Check Once...!!!')
}