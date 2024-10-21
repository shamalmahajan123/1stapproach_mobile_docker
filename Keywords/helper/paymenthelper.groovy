package helper
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import internal.GlobalVariable
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileAbstractKeyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import io.appium.java_client.AppiumDriver as AppiumDriver
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.StringUtils as StringUtils
import java.lang.Integer as Integer
import io.appium.java_client.MobileElement as MobileElement
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

public class paymenthelper {

	@Keyword
	public void PaymentResponses() {
		Mobile.delay(3)
		try {
			for(int rowNum=1; rowNum<=findTestData("Excel-Data/PaymentResponses").getRowNumbers();rowNum++) {
				//Mobile.delay(0.1)
				if (Mobile.waitForElementPresent(findTestObject(findTestData("Excel-Data/PaymentResponses").getValue(3,rowNum)),2, FailureHandling.OPTIONAL)) {
					KeywordUtil.markPassed("Scenario Passed " + findTestData("Excel-Data/PaymentResponses").getValue(2,rowNum))
					GlobalVariable.PaymentMessage = Mobile.getText(findTestObject(findTestData("Excel-Data/PaymentResponses").getValue(3,rowNum)),3, FailureHandling.OPTIONAL)
					break
				} else {
					println "Try Other"
				}
			}
			/*
			 if (Mobile.waitForElementPresent(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)) {
			 Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			 }else {
			 KeywordUtil.markPassed("ACEPTAR Button Not Available...!!!")
			 }*/
			//Mobile.tap(findTestObject('PaymentSuccess/BackButton'), 5, FailureHandling.OPTIONAL)
			//Mobile.delay(2)
			//Mobile.tap(findTestObject('PaymentSuccess/BackButton'), 5, FailureHandling.OPTIONAL)
		}catch(Exception e) {
			//println e.printStackTrace()
		}
	}

	@Keyword
	def VerifyTransactionMessage() {
		if(GlobalVariable.ExpectedPaymentMessage == GlobalVariable.PaymentMessage ) {
			GlobalVariable.CaseStatusMessage += "\n Expected payment message in datapool matches with actual payment message in app..!! \n"
		} else if(GlobalVariable.ExpectedPaymentMessage == 'NA' || GlobalVariable.ExpectedPaymentMessage == '' || GlobalVariable.ExpectedPaymentMessage == null || GlobalVariable.ExpectedPaymentMessage == 'null') {
			//Mobile.comment("Expected Transaction Message is null - No need to check with actual message..!!")
			GlobalVariable.CaseStatusMessage += "\n Expected Transaction Message is null - No need to check with actual message..!! \n"
		} else {
			GlobalVariable.CaseStatusMessage += "\n Expected Payment Message In Datapool Doesn't Match With Actual Payment Message In App..!! \n"
			KeywordUtil.markFailed("Expected Payment Message In Datapool Doesn't Match With Actual Payment Message In App..!!")
		}
	}

	@Keyword
	def VerifyBalance() {
		//import com.kms.katalon.core.configuration.RunConfiguration as RC
		def executionProfile = RC.getExecutionProfile()

		if (executionProfile.equalsIgnoreCase('local')) {
			println('Profile Set Is Local - Bypass DB Connection TCs - Can not verify BD and App Balance..!!')
			KeywordUtil.logInfo('Profile Set Is Local - Bypass DB Connection TCs - Can not verify BD and App Balance..!!')

		} else {
			GlobalVariable.origen_before_db = GlobalVariable.origen_before_db.toString()
			GlobalVariable.origen_after_db = GlobalVariable.origen_after_db.toString()
			GlobalVariable.origen_before_app = FormatBalance(GlobalVariable.origen_before_app)
			GlobalVariable.origen_after_app = FormatBalance(GlobalVariable.origen_after_app)

			// Check For Balance Before Transaction For Origin Accounts
			if(GlobalVariable.origen_before_db != GlobalVariable.origen_before_app)
			{
				'Origin Balance Before Transaction - Database Doesn\'t Match With App..!!'
				//KeywordUtil.logInfo('Origin Balance Befor Transaction In Database Doesn\'t Match With Origin Balance Before Transaction In App..!!')
				KeywordUtil.markFailed('Origin Balance Before Transaction In Database Doesn\'t Match With Origin Balance Before Transaction In App..!!')
				GlobalVariable.CaseStatusMessage += "\n Origin Balance Before Transaction In Database Doesn\'t Match With Origin Balance Before Transaction In App..!! \n"
			}
			// Check For Balance After Transaction For Origin Accounts
			if(GlobalVariable.origen_after_db != GlobalVariable.origen_after_app)
			{
				'Origin Balance After Transaction - Database Doesn\'t Match With App..!!'
				KeywordUtil.markFailed('Origin Balance After Transaction In Database Doesn\'t Match With Origin Balance After Transaction In App..!!')
				GlobalVariable.CaseStatusMessage += "\n Origin Balance After Transaction In Database Doesn\'t Match With Origin Balance After Transaction In App..!! \n"
			}

			if(GlobalVariable.BenefUnicoDetails == 'True' || GlobalVariable.CaseDescription.contains('ACH - ') || GlobalVariable.CaseDescription.contains('LBTR - ')) {
				KeywordUtil.markPassed('No Info Available For Destino Account..!!')

			} else {

				GlobalVariable.destino_before_db = GlobalVariable.destino_before_db.toString()
				GlobalVariable.destino_after_db = GlobalVariable.destino_after_db.toString()
				GlobalVariable.destino_before_app = FormatBalance(GlobalVariable.destino_before_app)
				GlobalVariable.destino_after_app = FormatBalance(GlobalVariable.destino_after_app)

				// Check For Balance Before Transaction For Destino Accounts
				if(GlobalVariable.destino_before_db != GlobalVariable.destino_before_app)
				{
					'Destino Balance Befor Transaction - Database Doesn\'t Match With App..!!'
					KeywordUtil.markFailed('Destino Balance Before Transaction In Database Doesn\'t Match With Destino Balance Before Transaction In App..!!')
					GlobalVariable.CaseStatusMessage += "\n Destino Balance Before Transaction In Database Doesn\'t Match With Destino Balance Before Transaction In App..!! \n"
				}

				// Check For Balance After Transaction For Destino Accounts
				if(GlobalVariable.destino_after_db != GlobalVariable.destino_after_app)
				{
					'Destino Balance After Transaction - Database Doesn\'t Match With App..!!'
					KeywordUtil.markFailed('Destino Balance After Transaction In Database Doesn\'t Match With Destino Balance After Transaction In App..!!')
					GlobalVariable.CaseStatusMessage += "\n Destino Balance After Transaction In Database Doesn\'t Match With Destino Balance After Transaction In App..!! \n"
				}
			}
		}
	}

	@Keyword
	def FormatBalance(String Amount) {
		try {
			int StartIndex = Amount.indexOf('.')
			int length = Amount.length()
			String B = Amount.substring(StartIndex+1, length)
			String Result = null;
			if(B.endsWith('0')) {
				Result = Amount.substring(0, length - 1)
			} else {
				Result = Amount
			}
			return Result
		} catch (Exception e) {

		}
	}
}