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

Token()

Verification()

def Token() {
    if (Mobile.waitForElementPresent(findTestObject('DigitalToken/android.widget.TextView - Token Digital'), 30, FailureHandling.OPTIONAL)) {
        Mobile.tap(findTestObject('DigitalToken/android.widget.TextView - Token Digital'), 5, FailureHandling.OPTIONAL)

        if (Mobile.waitForElementPresent(findTestObject('DigitalToken/Mis productos'), 10, FailureHandling.OPTIONAL)) {
            Mobile.tap(findTestObject('DigitalToken/Mis productos'), 5, FailureHandling.OPTIONAL)
            if (Mobile.waitForElementPresent(findTestObject('Login/CONTRASEA'), 30, FailureHandling.OPTIONAL)) {
                Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 2, FailureHandling.OPTIONAL)
				CustomKeywords.'helper.customfunction.HideKeyboard'()
				Mobile.delay(2)
				Mobile.tap(findTestObject('Buttons/ACCEDER'), 5, FailureHandling.OPTIONAL)
            }
            Mobile.delay(5)
			GlobalVariable.TokenStatus = 'True'
			GlobalVariable.Verification = 'Import'
        } else {
            if (Mobile.waitForElementPresent(findTestObject('Login/CONTRASEA'), 30, FailureHandling.OPTIONAL)) {
                Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 2, FailureHandling.OPTIONAL)
				CustomKeywords.'helper.customfunction.HideKeyboard'()
				
                if (Mobile.waitForElementPresent(findTestObject('Buttons/ACCEDER'), 30, FailureHandling.OPTIONAL)) {
                    Mobile.tap(findTestObject('Buttons/ACCEDER'), 5, FailureHandling.OPTIONAL)

                    if (Mobile.waitForElementPresent(findTestObject('DigitalToken/CONTINUAR'), 15, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('DigitalToken/SOLICITAR'), 15, FailureHandling.OPTIONAL)) {
                        Mobile.tap(findTestObject('DigitalToken/CONTINUAR'), 5, FailureHandling.OPTIONAL)
						Mobile.tap(findTestObject('DigitalToken/SOLICITAR'), 5, FailureHandling.OPTIONAL)
						
                        if (Mobile.waitForElementPresent(findTestObject('TokenFlow/DE ACUERDO'), 15, FailureHandling.OPTIONAL)) {
                            Mobile.tap(findTestObject('TokenFlow/DE ACUERDO'), 5, FailureHandling.OPTIONAL)
                        } else if (Mobile.waitForElementPresent(findTestObject('DigitalToken/SOLICITAR'), 15, FailureHandling.OPTIONAL)){
							Mobile.tap(findTestObject('DigitalToken/SOLICITAR'), 5, FailureHandling.OPTIONAL)
							if (Mobile.waitForElementPresent(findTestObject('TokenFlow/DE ACUERDO'), 15, FailureHandling.OPTIONAL)) {
								Mobile.tap(findTestObject('TokenFlow/DE ACUERDO'), 5, FailureHandling.OPTIONAL)
							}else {
								KeywordUtil.markFailedAndStop('DE ACUERDO Not Found...!!!')
							}							
                        } else if (Mobile.waitForElementPresent(findTestObject('Token/android.widget.TextView - Para continuar el proceso, favor colocar  el cdigo enviado a tu correo electrnico'), 
                            15, FailureHandling.OPTIONAL)) {
                            Mobile.delay(10)

                            'GET OTP FROM GMAIL : '
                            WebUI.callTestCase(findTestCase('TC-TokenDigitalMigration/TC_GetOtpGmail'), [:], FailureHandling.STOP_ON_FAILURE)

                            Mobile.setText(findTestObject('DigitalToken/SetToken'), GlobalVariable.Otp, 5, FailureHandling.OPTIONAL)
							CustomKeywords.'helper.customfunction.HideKeyboard'()
							
                            Mobile.delay(5)

                            Mobile.tap(findTestObject('Buttons/Continuar-Payment'), 5, FailureHandling.OPTIONAL)

                            GlobalVariable.TokenStatus = 'True'
							GlobalVariable.Verification = 'Import'
                        } else {
                            KeywordUtil.markFailedAndStop('CONTINUAR Button Not Found')
                        }
                    } else if (Mobile.waitForElementPresent(findTestObject('Para continuar el proceso, favor colocar  el cdigo enviado a tu correo electrnico'), 
                        15, FailureHandling.OPTIONAL)) {
                        Mobile.delay(10)

                        'GET OTP FROM GMAIL : '
                        WebUI.callTestCase(findTestCase('TC-TokenDigitalMigration/TC_GetOtpGmail'), [:], FailureHandling.STOP_ON_FAILURE)

                        Mobile.setText(findTestObject('DigitalToken/SetToken'), GlobalVariable.Otp, 5, FailureHandling.OPTIONAL)
						CustomKeywords.'helper.customfunction.HideKeyboard'()
						
                        Mobile.delay(5)

                        Mobile.tap(findTestObject('Buttons/Continuar-Payment'), 5, FailureHandling.OPTIONAL)

                        GlobalVariable.TokenStatus = 'True'
						GlobalVariable.Verification = 'Import'
                    } else {
                        Mobile.takeScreenshot(FailureHandling.STOP_ON_FAILURE)

                        KeywordUtil.markFailedAndStop('Something Went Wrong...!!!')
                    }
                } else {
                    KeywordUtil.markFailedAndStop('CONTINUAR Button Not Found')
                }
            } else {
                KeywordUtil.markFailedAndStop('CONTRASEA Screen Not Found')
            }
        }
    } else {
        KeywordUtil.markFailedAndStop('Token Digital Screen Not Found')
    }
}

def Verification() {
    if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 80, FailureHandling.STOP_ON_FAILURE)) {
       //CustomKeywords.'helper.customfunction.Screenshot'()

        KeywordUtil.markPassed('User Logged In - Redirected To Dashboard...!!!')
    } else if (Mobile.waitForElementPresent(findTestObject('DigitalToken/Activacin satisfactoria'), 60, FailureHandling.STOP_ON_FAILURE)) {
        CustomKeywords.'helper.customfunction.Screenshot'()

        KeywordUtil.markPassed('Token Successfully Migrated...!!!')

        Mobile.delay(3)

        Mobile.tap(findTestObject('Object Repository/DigitalToken/VER MIS PRODUCTOS'), 5)

        if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 10, FailureHandling.STOP_ON_FAILURE)) {
            //CustomKeywords.'helper.customfunction.Screenshot'()
            KeywordUtil.markPassed('User Logged In - Redirected To Dashboard...!!!')
        }
    } else if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 10, FailureHandling.STOP_ON_FAILURE)) {
        //CustomKeywords.'helper.customfunction.Screenshot'()
        KeywordUtil.markPassed('User Logged In - Redirected To Dashboard...!!!')
		
    } else if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 15, FailureHandling.STOP_ON_FAILURE)) {
        //CustomKeywords.'helper.customfunction.Screenshot'()
        KeywordUtil.markPassed('User Logged In - Redirected To Dashboard...!!!')
    } else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('Buttons/ACEPTAR'), 1,FailureHandling.OPTIONAL)
		KeywordUtil.markFailedAndStop('Token Digital Migration Failed - Check the ScreenShot Error...!!!')
	}
}