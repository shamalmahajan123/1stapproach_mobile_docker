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
import javax.mail.*
import java.util.Properties
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.annotation.Keyword as Keyword

// UNCOMMENT BELOW COMMENRS TO RUN INDIVIDUAL TOKEN IMPORT

// WebUI.callTestCase(findTestCase('CommonTestCases/TC_KillPreviousSession'), [:], FailureHandling.OPTIONAL) 

//'APPLICATION LAUNCH :  '
// WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickAppLaunch'), [:], FailureHandling.STOP_ON_FAILURE)

//'USERLOGGED IN VERIFICATION : '
// CustomKeywords.'helper.customfunction.LoginToken'(Role, Cedula, Pasaporte, Password, Email)

'RELAUNCH APPLICATION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickAppLaunchToken'), [:], FailureHandling.STOP_ON_FAILURE)

'REQUEST FOR TOKEN DIGITAL MIGRATION : '
WebUI.callTestCase(findTestCase('TC-TokenDigitalMigration/TC_TokenRequest'), [('Password') : Password], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.TokenStatus == 'True') {
	KeywordUtil.markPassed('Token Is Already Imported...!!!')

} else {
	
	'DELETE PREVIOUS TOKEN EMAILS : '
	WebUI.callTestCase(findTestCase('TC-TokenDigitalMigration/TC_DeletePreviousMails'), [:], FailureHandling.STOP_ON_FAILURE)
	
	'CONNECT DB IBKPerData'
	CustomKeywords.'helper.functionsupdate.connectDB4'(User,Pass)
	
	'UPDATE TOKENREQUESTSTATUS ID : '
	CustomKeywords.'helper.functionsupdate.MigrateToken'()
	
	'RELAUNCH APPLICATION : '
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickAppLaunchToken'), [:], FailureHandling.STOP_ON_FAILURE)
	
	'COMPLETE TOKEN DIGITAL MIGRATION : '
	WebUI.callTestCase(findTestCase('TC-TokenDigitalMigration/TC_TokenRequest'), [('Password') : Password], FailureHandling.STOP_ON_FAILURE)
}

