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
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.sql.Connection as Connection
import java.sql.DriverManager as DriverManager
import java.sql.ResultSet as ResultSet
import java.sql.Statement as Statement
import java.text.DecimalFormat as DecimalFormat
import com.ibm.db2.jcc.DB2Driver as DB2Driver
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import com.opencsv.*
import java.io.FileWriter.*
import java.io.IOException as IOException
import java.io.Reader as Reader
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import org.eclipse.persistence.platform.database.DB2Platform as DB2Platform
import org.junit.After as After
import org.eclipse.persistence.platform.database.DB2MainframePlatform as DB2MainframePlatform
import java.sql.SQLException as SQLException
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class BanReservas_Personal {

	@BeforeTestCase
	def BeforeTestCase(TestCaseContext testCaseContext) {
		//println testCaseContext.getTestCaseId()
		//println testCaseContext.getTestCaseVariables()	
		KeywordUtil.logInfo('PREREQUISITES VERIFICATION FOR TESTCASE : ')
		String TestCaseName=testCaseContext.getTestCaseId()
		GlobalVariable.APK_bit = 'x'+(GlobalVariable.AppPath).replaceAll("[^0-9]", "")
		
		if(TestCaseName.contains("APPLAUNCH") || TestCaseName.contains("LOGIN") || TestCaseName.contains("SMS") || TestCaseName.contains("EMAIL") || TestCaseName.contains("TC_QuickAppLaunch")) {
			
			if("PASSED".equalsIgnoreCase(GlobalVariable.Status)) {
				println "CURRENT TEST CASE EXECUTION : " + testCaseContext.getTestCaseId()
								
			} else if ("FAILED".equalsIgnoreCase(GlobalVariable.Status) || "SKIPPED".equalsIgnoreCase(GlobalVariable.Status)) {
				
				println "SKIPPED TEST CASE NAME : " + testCaseContext.getTestCaseId()
				testCaseContext.skipThisTestCase()
			}
					
		} else {
			println "CURRENT TEST CASE EXECUTION IS " + testCaseContext.getTestCaseId()
			
			'----------------------------RESETTING THE VALUES--------------------------------------'
			
				GlobalVariable.origen_before_db = ''
				GlobalVariable.destino_before_db = ''
				GlobalVariable.origen_after_db = ''
				GlobalVariable.destino_after_db = ''
				GlobalVariable.origen_before_app = ''
				GlobalVariable.destino_before_app = ''
				GlobalVariable.origen_after_app = ''
				GlobalVariable.destino_after_app = ''
				
				GlobalVariable.PaymentStatus = ''
				GlobalVariable.OrigenAccountStatus = ''
				GlobalVariable.DestinoAccountStatus = ''
				GlobalVariable.CaseDescription = ''
				GlobalVariable.BenefUnicoDetails = ''
				
				GlobalVariable.Tasa = ''
				
				GlobalVariable.PaymentMessage = ''
				GlobalVariable.PaymentTime = ''
				GlobalVariable.PaymentReciept = ''
				GlobalVariable.PaymentAmount = ''
				GlobalVariable.PaymentDestino = ''
				
				GlobalVariable.CuentaOrigen = ''
				GlobalVariable.CuentaDestino = ''
				GlobalVariable.Impuesto = ''
				GlobalVariable.Verification = ''
				GlobalVariable.UpdatedMonto = ''
				GlobalVariable.GetImpuesta = ''
				GlobalVariable.Otp = ''
				GlobalVariable.MontoDecimal=''
				GlobalVariable.CommissionAmount=''
				GlobalVariable.Keyboard = ''
				
				GlobalVariable.TokenStatus = ''
				GlobalVariable.CodeValue = ''
				GlobalVariable.ExpectedPaymentMessage = ''
				GlobalVariable.CaseStatusMessage = ''
			'--------------------------------------------------------------------------------------'
		}
		

	}

	@BeforeTestSuite
	def BeforeTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
		
		//String cmd = 'taskkill /im node.exe /f'
		//Runtime.getRuntime().exec(cmd)
	}
	
	@AfterTestSuite
	def AfterTestSuite(TestSuiteContext testSuiteContext) {
		//println testSuiteContext.getTestSuiteId()
		def executionProfile = RC.getExecutionProfile()
		if (executionProfile.equalsIgnoreCase('default')) {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_UserUnlink'), [:], FailureHandling.OPTIONAL)
		}
		//RunConfiguration.getReportFolder()

	}
	
	@AfterTestCase
	def AfterTestCase(TestCaseContext testCaseContext) {
		println testCaseContext.getTestCaseId()
		println testCaseContext.getTestCaseStatus()
		
		String TestCaseName=testCaseContext.getTestCaseId()
		String Status=testCaseContext.getTestCaseStatus()
		GlobalVariable.Status = Status
		
	if(GlobalVariable.Verification == 'NA' || GlobalVariable.Verification == 'ExceedOrigenAmount') {		
		KeywordUtil.logInfo(('------------------ : DETAILED REPORT : ------------------' +
			'\n'+
			'\n'+
			'APK Bit : ' + GlobalVariable.APK_bit +
			'\n'+
			'BALANCE VERIFICATION NOT NECESSARY : ' + 
			'CASE DESCRIPTION : ' + GlobalVariable.CaseDescription +
			'\n'+
			'\n'+
			' * EXPECTED TRANSACTION MESSAGE : ' + GlobalVariable.ExpectedPaymentMessage +
			'\n'+
			' * TRANSACTION MESSAGE IN APP : ' + GlobalVariable.PaymentMessage +
			'\n'+
			'\n'+
			GlobalVariable.CaseStatusMessage+
			'\n'+
			'-----------------------------------------------------------'))
			
	} else if(GlobalVariable.Verification == 'Import') {
			
		if (GlobalVariable.Verification == 'Import' && GlobalVariable.TokenStatus == 'True') {
			KeywordUtil.logInfo(('------------------ : DETAILED REPORT : ------------------' +
				'\n'+
				'APK Bit : ' + GlobalVariable.APK_bit +
				'\n'+
				'TOKEN IMPORTED SUCCESSFULLY...!!!' +
				'\n'+
				'\n'+				
				'-----------------------------------------------------------'))		
		} else if (GlobalVariable.Verification == 'Import' && GlobalVariable.TokenStatus == 'false' || GlobalVariable.TokenStatus == '') {
			KeywordUtil.logInfo(('------------------ : DETAILED REPORT : ------------------' +
				'\n'+
				'APK Bit : ' + GlobalVariable.APK_bit +
				'\n'+
				'TOKEN IMPORT FAILED...!!!' +
				'\n'+
				'\n'+
				'-----------------------------------------------------------'))
		}			
	} else {
		
	if(testCaseContext.testCaseId.contains('PAGO_TARJETA_CREDITO_PROPIA_DOP_USD')){
KeywordUtil.logInfo(('\n'+
	'\n'+
	'------------------------ : DETAILED REPORT : -----------------------' +
	'\n'+
	'\n'+
	'APK Bit : ' + GlobalVariable.APK_bit +
	'\n'+
	'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
	'\n'+
	' * EXPECTED TRANSACTION MESSAGE : ' + GlobalVariable.ExpectedPaymentMessage +
	'\n'+
	' * TRANSACTION MESSAGE IN APP : ' + GlobalVariable.PaymentMessage +
	'\n'+
	' * PAYMENT TIME : ' + GlobalVariable.PaymentTime +
	'\n'+
	' * PAYMENT RECIEPT : ' + GlobalVariable.PaymentReciept +
	'\n'+
	' * TRANSFER MONTO AMOUNT IS : ' + GlobalVariable.montoReplace + ' - ' + GlobalVariable.MontoDecimal +
	'\n'+
	' * RATE APPLIED : ' + GlobalVariable.Tasa +
	'\n'+
	' * TAX APPLIED : ' + GlobalVariable.Impuesto +
	'\n'+
	' * TAX AMOUNT : ' + GlobalVariable.GetImpuesta +
	'\n'+
	' * COMMISSION APPLIED : ' + GlobalVariable.CommissionAmount +
	'\n'+
	' * TRANSFERED (MONTO/TASA/COMMISIONES ) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.montoReplace + ' : ' + GlobalVariable.UpdatedMonto +
	'\n'+
	' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
	'\n'+
	'\n'+
	//'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
	//'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
	//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.montoReplace + ' - ' + GlobalVariable.destino_before_db + '\n' +
	'\n'+
	//'\n'+
	'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
	'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
	'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.montoReplace + ' - ' + GlobalVariable.destino_before_app + '\n' +
	'\n'+
	'\n'+
	//'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
	//'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
	//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.montoReplace + ' - ' + GlobalVariable.destino_after_db  + '\n' +
	//'\n'+
	//'\n'+
	//'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : \n' +
	//'\n'+
	//'\n'+
	//'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
	//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Calculation Via Function :- ' + GlobalVariable.montoReplace + ' - ' + GlobalVariable.Tax_Destinobalance_after  + '\n' +
	//'\n'+
	//'\n'+
	'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
	'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
	'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.montoReplace + ' - ' + GlobalVariable.destino_after_app + '\n' +
	'\n'+
	GlobalVariable.CaseStatusMessage+
	'\n'+
	'-------------------------------------------------------------------------'))
	} else if(testCaseContext.testCaseId.contains('TRANSF') || testCaseContext.testCaseId.contains('PAGO') || testCaseContext.testCaseId.contains('TRANSF_') || testCaseContext.testCaseId.contains('PAGO_') || testCaseContext.testCaseId.contains('INTER') || testCaseContext.testCaseId.contains('AVANCE') || testCaseContext.testCaseId.contains('Compra') || testCaseContext.testCaseId.contains('Envio') || testCaseContext.testCaseId.contains('IMPUESTOS') || testCaseContext.testCaseId.contains('TUEFECTIVO') || testCaseContext.testCaseId.contains('SERVICIO') || testCaseContext.testCaseId.contains('RECARGA')){
KeywordUtil.logInfo(('\n'+
	'\n'+
	'------------------------ : DETAILED REPORT : -----------------------' +
	'\n'+
	'\n'+
	'APK Bit : ' + GlobalVariable.APK_bit +
	'\n'+
	'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
	'\n'+
	' * EXPECTED TRANSACTION MESSAGE : ' + GlobalVariable.ExpectedPaymentMessage +
	'\n'+
	' * TRANSACTION MESSAGE IN APP : ' + GlobalVariable.PaymentMessage +
	'\n'+
	' * PAYMENT TIME : ' + GlobalVariable.PaymentTime +
	'\n'+
	' * PAYMENT RECIEPT : ' + GlobalVariable.PaymentReciept +
	'\n'+
	' * TRANSFER MONTO AMOUNT IS : ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.MontoDecimal +
	'\n'+
	' * RATE APPLIED : ' + GlobalVariable.Tasa +
	'\n'+
	' * TAX APPLIED : ' + GlobalVariable.Impuesto +
	'\n'+
	' * TAX AMOUNT : ' + GlobalVariable.GetImpuesta +
	'\n'+
	' * COMMISSION APPLIED : ' + GlobalVariable.CommissionAmount +
	'\n'+
	' * TRANSFERED (MONTO/TASA/COMMISIONES ) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.CurrencyTypeDestino + ' : ' + GlobalVariable.UpdatedMonto +
	'\n'+
	' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
	'\n'+
	'\n'+
	//'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
	//'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
	//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_db + '\n' +
	'\n'+
	//'\n'+
	'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
	'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
	'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_app + '\n' +
	'\n'+
	'\n'+
	//'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
	//'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
	//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_db  + '\n' +
	//'\n'+
	//'\n'+
	//'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : \n' +
	//'\n'+
	//'\n'+
	//'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
	//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.Tax_Destinobalance_after  + '\n' +
	//'\n'+
	//'\n'+
	'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
	'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
	'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_app + '\n' +
	'\n'+
	GlobalVariable.CaseStatusMessage+
	'\n'+
	'-------------------------------------------------------------------------'))
	} else if (testCaseContext.testCaseId.contains('TRANSACCIONES_MULTIPLES')) {
		KeywordUtil.logInfo(GlobalVariable.MultiDestinoValue)
		}
	}
		
	}	
}