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
import org.openqa.selenium.Rectangle as Rectangle
import com.kms.katalon.core.annotation.TearDown as TearDown
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

'KILL ANY PREVIOUS NODEJS RUNNING SESSION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_KillPreviousSession'), [:], FailureHandling.OPTIONAL)

'APPLICATION LAUNCH : '
Mobile.startApplication(GlobalVariable.AppPath, true)

ApplicationLaunch()

def ApplicationLaunch() {
    if (Mobile.waitForElementPresent(findTestObject('Debugger/CANCEL'), 10, FailureHandling.OPTIONAL)) {
        KeywordUtil.markPassed('Debugger Is Displaying - Attempting To Cancel It')

        Mobile.tap(findTestObject('Debugger/CANCEL'), 5, FailureHandling.OPTIONAL)

        Mobile.delay(3)

        if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 10, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN-Updated'), 5, FailureHandling.OPTIONAL)) {
            KeywordUtil.markPassed('Application Launched Successfully Cancelling The Koni Debugger...!!!')

            Mobile.delay(3)

            CustomKeywords.'helper.customfunction.Screenshot'()
        } 
    } else if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 5, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN-Updated'), 5, FailureHandling.OPTIONAL)) {
        CustomKeywords.'helper.customfunction.Screenshot'()

        KeywordUtil.markPassed('Application Launched Successfully...!!!')
    } else if (Mobile.waitForElementPresent(findTestObject('Token/Token Digital'), 5, FailureHandling.OPTIONAL)) {
        CustomKeywords.'helper.customfunction.Screenshot'()

        Mobile.tap(findTestObject('Token/Token Digital'), 15, FailureHandling.OPTIONAL)

        Mobile.delay(3)

        if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TUBNCO'), 5, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN-Updated'), 5, FailureHandling.OPTIONAL)) {
            CustomKeywords.'helper.customfunction.Screenshot'()

            KeywordUtil.markPassed('Application Launched Successfully Tapping Token Digital Screen...!!!')
        }
    } else {
        CustomKeywords.'helper.customfunction.Screenshot'()

        KeywordUtil.markFailedAndStop('Personal Application Launch Failed...Please Check Screenshot...!!!')
    }
}