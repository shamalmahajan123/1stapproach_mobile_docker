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
import java.io.*
import java.util.*
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.configuration.RunConfiguration as RC

GlobalVariable.CaseDescription = Description
GlobalVariable.CuentaOrigen = CuentaOrigen
GlobalVariable.CuentaDestino = CuentaDestino
GlobalVariable.Impuesto = Impuesto
GlobalVariable.Verification = Verification
//GlobalVariable.OriginalMonto = Monto
Monto = GlobalVariable.MontoDecimal

println('DescriptionUpdated' + GlobalVariable.CaseDescription)

String Description = GlobalVariable.CaseDescription

if (Description.contains('AvanceDeCredimas')) {
	CustomKeywords.'helper.functionsupdate.credimas_DBAfter'(CuentaOrigen, MontoCurrency)
} else {
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_OrigenAfter'(CuentaOrigen)
}

if (Description.contains('Prestamo')) {
	CustomKeywords.'helper.functionsupdate.ConsultaBalancePrestamosAfter'(CuentaDestino)
	CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)
	if (Description.contains('Prestamo Acc')) {
		CustomKeywords.'helper.customfunction.DestinoAccount_AfterBalance'(CtaDestino)
	} else {
		GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
	}	
} else if (Description.contains('TarjetaPropia')) {
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_TarjetaAfter'(CuentaDestino, MontoCurrency)
	CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)
	CustomKeywords.'helper.customfunction.DestinoAccount_AfterBalance_MultipleTransaction'(CtaDestino, MontoCurrency)
	
} else if (Description.contains('ACH - ') || Description.contains('LBTR - ') ) {
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_DestinoAfter'(CuentaDestino)
	CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)
	GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
	GlobalVariable.destino_after_db = "No Info For Acct " + CtaDestino
	GlobalVariable.destino_before_db = "No Info For Acct " + CtaDestino
	
} else if (Description.contains('AvanceDeCredimas')) {
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_DestinoAfter'(CuentaDestino)
	CustomKeywords.'helper.customfunction.OrigenCredimas_AfterBalance'(CtaOrigen)
	CustomKeywords.'helper.customfunction.DestinoAccount_AfterBalance'(CtaDestino)
	
} else {
	
	if(GlobalVariable.destino_after_db == '' && GlobalVariable.destino_before_db == '') {
		CustomKeywords.'helper.functionsupdate.ConsultaBalance_DestinoAfter'(CuentaDestino)
	}
	CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)
	CustomKeywords.'helper.customfunction.DestinoAccount_AfterBalance'(CtaDestino)
}

double Balance;
if ((Impuesto.equals('True') || Impuesto.equals('true')) || Impuesto.equals('TRUE')) {
	try {

		CustomKeywords.'helper.functionsupdate.CalculateTaxUpdate'(CuentaOrigen, CuentaDestino)
		//Balance = (0.0015 * Monto)
		
		//double monto = Double. parseDouble(Monto)
		//println "monto value is " + monto
		
		//Tax = GlobalVariable.GetImpuesta + monto
		//println "updated balance is " + Tax
		
		//GlobalVariable.GetImpuesta
		
		//GlobalVariable.Tax_Origenbalance_after
		//GlobalVariable.Tax_Destinobalance_after
	}
	catch (Exception e) {
		//e.printStackTrace()
	}
} else {
	GlobalVariable.GetImpuesta = '0.0'
	double ImpuestaValue = Double.parseDouble(GlobalVariable.GetImpuesta)
	GlobalVariable.GetImpuesta = ImpuestaValue
	println('TAX IS NOT CHARGED...!!!')	
		
    GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_after_db
	GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
}

if(GlobalVariable.Tasa == "No Conversion Rate") {
	try {
		println('Tasa Value Is ' + GlobalVariable.Tasa)
		UpdatedMonto = Double.parseDouble(Monto)
		println('UpdatedMonto1 Is : ' + UpdatedMonto)
		
		GlobalVariable.UpdatedMonto = UpdatedMonto + GlobalVariable.GetImpuesta + GlobalVariable.CommissionAmount
		println('UpdatedMonto2 Is : ' + 	GlobalVariable.UpdatedMonto)
			
		DecimalFormat df2 = new DecimalFormat("###.##");
		double input
		try {
			input = Double.parseDouble(GlobalVariable.UpdatedMonto)
		} catch (Exception e){
			input = GlobalVariable.UpdatedMonto
		}
		
		GlobalVariable.UpdatedMonto= df2.format(input)
		println('UpdatedMonto3 Is : ' + GlobalVariable.UpdatedMonto)
		
		//GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_after_db
		//GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
	} catch (Exception e) {
		//e.printStackTrace()
	}
} else {
	try {
		Balance = Double.parseDouble(Monto)
		println('Balance2' + Balance)
	
		UpdatedMonto = GlobalVariable.Tasa * Balance
		println('UpdatedMonto4 Is : ' + UpdatedMonto)
		GlobalVariable.UpdatedMonto = (UpdatedMonto) + (GlobalVariable.GetImpuesta) + (GlobalVariable.CommissionAmount);
		println('UpdatedMonto5 Is : ' + 	GlobalVariable.UpdatedMonto)
		GlobalVariable.UpdatedMonto = Double.parseDouble(GlobalVariable.UpdatedMonto)
		
		DecimalFormat df2 = new DecimalFormat("###.##");
		double input = GlobalVariable.UpdatedMonto
		GlobalVariable.UpdatedMonto= df2.format(input)
		println('UpdatedMonto6 Is : ' + GlobalVariable.UpdatedMonto)
		
		//GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_after_db
		//GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
	} catch (Exception e) {
		//e.printStackTrace()
	}
}

//if (Verification=='NA'||Verification=='') {
//	println('Verification Is NA - No Need To Compare Balance..!!')
//} else {
//	//CustomKeywords.'helper.paymenthelper.VerifyBalance'()
//}


KeywordUtil.logInfo(('\n'+
	'\n'+
	'------------------------ : DETAILED REPORT : -----------------------' +
	'\n'+
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
	' * TRANSFERED (MONTO / TASA / COMMISIONES ) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.CurrencyTypeDestino + ' : ' + GlobalVariable.UpdatedMonto +
	'\n'+
	' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
	'\n'+
	'\n'+
	//'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
	//'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
	//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_db + '\n' +
	'\n'+
	'\n'+
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
	//'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : ' +
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
	'\n'+
	'-------------------------------------------------------------------------'))
