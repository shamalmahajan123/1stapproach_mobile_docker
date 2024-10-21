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
if (Mobile.waitForElementPresent(findTestObject('Debugger/CANCEL'), 10, FailureHandling.OPTIONAL)) {
    KeywordUtil.markPassed('Debugger Is Displaying - Attempting To Cancel It')

    Mobile.delay(3)

    Mobile.tap(findTestObject('Debugger/CANCEL'), 15, FailureHandling.OPTIONAL)

    Mobile.delay(3)

    for (int i=0; i<=4; i++) {
		if(i==4) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			Mobile.delay(3)
			KeywordUtil.markFailedAndStop('Vista Rapid Screen Not Found - Please Check Once...!!!')
			break
		} else {
			if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Vista Rpida'), 10, FailureHandling.OPTIONAL) ||
				Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 5, FailureHandling.OPTIONAL)||
				Mobile.waitForElementPresent(findTestObject('DigitalToken/Token Digital'), 3, FailureHandling.OPTIONAL)||
				Mobile.waitForElementPresent(findTestObject('Token/Token Digital'), 3, FailureHandling.OPTIONAL)) {
				KeywordUtil.markPassed('User Is Logged In Application Personal...!!!')
				break
			} else if (Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 5, FailureHandling.OPTIONAL)) {
				Mobile.delay(3)
				KeywordUtil.markPassed('User Is Not Logged In Application Personal...!!!')
			}
		}
	}
	
} else if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 5, FailureHandling.OPTIONAL)) {
    KeywordUtil.markPassed('Application Launched Successfully...!!!')

    if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Vista Rpida'), 10, FailureHandling.CONTINUE_ON_FAILURE) || 
    Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 10, FailureHandling.CONTINUE_ON_FAILURE)) {
        KeywordUtil.markPassed('User Is Logged In Application Personal...!!!')
    } else if (Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 5, FailureHandling.CONTINUE_ON_FAILURE)) {
        Mobile.delay(3)

        KeywordUtil.markPassed('User Is Not Logged In Application Personal...!!!')
    }
} else {
    CustomKeywords.'helper.customfunction.Screenshot'()

    Mobile.delay(3)

    KeywordUtil.markFailedAndStop('Vista Rapid Screen Not Found - Please Check Once...!!!')
}