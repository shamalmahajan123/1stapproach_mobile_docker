import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import java.time.Duration
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import java.text.DecimalFormat
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import io.appium.java_client.MobileElement

//Variables
GlobalVariable.CaseDescription = Description
AppiumDriver<?> driver = MobileDriverFactory.getDriver()
int TotalDestino = Integer.parseInt(DestinoCount)
ArrayList<String> DestinoAcc = new ArrayList<String>(Arrays.asList(Destino1, Destino2, Destino3, Destino4, Destino5, Destino6))
ArrayList<String> monto = new ArrayList<String>(Arrays.asList(Monto, Monto1, Monto2, Monto3, Monto4, Monto5))
ArrayList<String> MontoCurr = new ArrayList<String>(Arrays.asList(MontoCurrency1, MontoCurrency2))
int i = 0;
double totalTax

//Get Tax Value
for(int a = 0; a < TotalDestino; a++) {
	Mobile.tap(findTestObject('MultipleTransactions/Add destino button'), 30, FailureHandling.OPTIONAL)
	if(DestinoAcc[a].charAt(0) == "*" && DestinoAcc[a] != "NA") {
		println("Destino is Account")
		'SELECT DESTINO ACCOUNT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccount'), [('CtaDestino') : DestinoAcc[a]], FailureHandling.STOP_ON_FAILURE)
		
		'ENTER MONTO FOR PAYMENT : '
		Mobile.tap(findTestObject('TransferenciaPropia/Digita un monto'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		if(Mobile.verifyElementVisible(findTestObject('Object Repository/TarjetaPropia/Monto a pagar Title'), 5, FailureHandling.OPTIONAL)){
			'TARJETA MONTO : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto - Tarjeta'), [('Monto') : monto[a], ('Decimal') : Decimal, ('MontoCurrency') : MontoCurr[i], ('MontoType') : MontoType, ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
			i++
		} else {
			'MONTO : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[a] ,('Decimal') : Decimal], FailureHandling.STOP_ON_FAILURE)
		}
		
		Mobile.tap(findTestObject('MultipleTransactions/Agregar Button'), 20, FailureHandling.OPTIONAL)
				
	} else if(DestinoAcc[a] != "NA") {
		println("Destino is Beneficiary")
		'SELECT DESTINO ACCOUNT BENEFICIARY : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoBeneficiary'), [('CtaDestino') : DestinoAcc[a], ('Description') : Description], FailureHandling.STOP_ON_FAILURE)

		'ENTER MONTO FOR PAYMENT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[a] ,('Decimal') : Decimal, ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
		
		Mobile.tap(findTestObject('MultipleTransactions/Agregar Button'), 20, FailureHandling.OPTIONAL)
	} else {
		println("Destino is Blank")
	}
	
	Mobile.delay(1)
	Mobile.tap(findTestObject('Buttons/Confirmar'), 20, FailureHandling.OPTIONAL)
	
	//tax update
	String TAX = ""
	GlobalVariable.GetImpuesta = ""
	double ImpuestoUpdated
	
	if(Mobile.verifyElementVisible(findTestObject('MultipleTransactions/Single Tax Element'), 3, FailureHandling.OPTIONAL)) {
		TAX = Mobile.getText(findTestObject('MultipleTransactions/Single Tax Element'), 1, FailureHandling.OPTIONAL)
		ImpuestoUpdated = Double.parseDouble(TAX.split("Impuesto: DOP ")[1])		
	} else {
		TAX = '0.0'
		ImpuestoUpdated = Double.parseDouble(TAX)
	}
	
	totalTax += ImpuestoUpdated

	//tax mapping
	CustomKeywords.'helper.customfunction.ImpuestosMap'(a, TotalDestino, ImpuestoUpdated)
	
	Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 10, FailureHandling.OPTIONAL)
	Mobile.delay(2)

	ArrayList<String> Element = new ArrayList<String>();
	Element = driver.findElementsByXPath("//*[@class = 'android.widget.TextView' and (@text = 'Label' or . = 'Label')]")
	MobileElement secondElement = Element.get(1);						
	int midOfY = secondElement.getLocation().y +(secondElement.getSize().height/2);
	int fromXLocation = secondElement.getLocation().x;
	int toXLocation = Mobile.getDeviceWidth()-1;
	
	Mobile.delay(2)
	TouchAction action = new TouchAction(driver);
	action.longPress(PointOption.point(fromXLocation, midOfY)).moveTo(PointOption.point(toXLocation, midOfY)).release().perform();
	Mobile.delay(2)
	Mobile.tap(findTestObject('Benef_ProductYServicios/Button - Eliminar'), 3, FailureHandling.OPTIONAL)
}

//Selecting multiple destino account
 i = 0;
for(int count = 0; count < TotalDestino; count++) {
	String tempDestinoCurreny = ''
	Mobile.tap(findTestObject('MultipleTransactions/Add destino button'), 30, FailureHandling.OPTIONAL)
	if(DestinoAcc[count].charAt(0) == "*" && DestinoAcc[count] != "NA") {
		println("Destino is Account")
		'SELECT DESTINO ACCOUNT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccount'), [('CtaDestino') : DestinoAcc[count]], FailureHandling.STOP_ON_FAILURE)
		
		'TASA VERIFICATION : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
		
		'ENTER MONTO FOR PAYMENT : '		
		Mobile.tap(findTestObject('TransferenciaPropia/Digita un monto'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		if(Mobile.verifyElementVisible(findTestObject('Object Repository/TarjetaPropia/Monto a pagar Title'), 10, FailureHandling.OPTIONAL)){
			'TARJETA MONTO : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto - Tarjeta'), [('Monto') : monto[count], ('Decimal') : Decimal, ('MontoCurrency') : MontoCurr[i], ('MontoType') : MontoType, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
			i++
		} else {
			'MONTO : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[count] ,('Decimal') : Decimal], FailureHandling.STOP_ON_FAILURE)
		}
		
		'ENTER CONCEPT FOR PAYMENT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept, ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
		
		Mobile.tap(findTestObject('MultipleTransactions/Agregar Button'), 20, FailureHandling.OPTIONAL)
		
		//get currenyType
		CustomKeywords.'helper.customfunction.DestinoCurrMap'(count, TotalDestino)
				
	} else if(DestinoAcc[count] != "NA") {
		println("Destino is Beneficiary")
		'SELECT DESTINO ACCOUNT BENEFICIARY : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoBeneficiary'), [('CtaDestino') : DestinoAcc[count], ('Description') : Description], FailureHandling.STOP_ON_FAILURE)

		'TASA VERIFICATION : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
		
		'ENTER MONTO FOR PAYMENT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[count] ,('Decimal') : Decimal, ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
		
		'ENTER CONCEPT FOR PAYMENT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept, ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
		
		Mobile.tap(findTestObject('MultipleTransactions/Agregar Button'), 20, FailureHandling.OPTIONAL)
		
		//get currenyType
		CustomKeywords.'helper.customfunction.DestinoCurrMap'(count, TotalDestino)
		
	} else {
		println("Destino is Blank")
	}
	
	CustomKeywords.'helper.customfunction.DestinoBeforeAppMap'(count, TotalDestino)
}

Mobile.delay(1)
Mobile.tap(findTestObject('Buttons/Confirmar'), 20, FailureHandling.OPTIONAL)

//Total Tax
GlobalVariable.GetImpuesta = totalTax
KeywordUtil.logInfo('-----'+GlobalVariable.GetImpuesta)

//Check Token Digital Message
if(Mobile.verifyElementVisible(findTestObject('MultipleTransactions/Activate digital token (msg)'), 5, FailureHandling.OPTIONAL)) {
	KeywordUtil.markFailedAndStop("*** DIGITAL TOKEN REQUIRED AFTER IMPORTING TOKEN ***")
}

/*
//Get Tax Value
'GET TAX VALUE : '
GlobalVariable.GetImpuesta = ""
double totalTax 
int montoVal
for(int index = 0; index<TotalDestino; index++) {
	if(monto[index]!="NA") {
		if(monto[index] !="NaN") {
			montoVal = Integer.parseInt(monto[index])
		} else {
			monto[index] = GlobalVariable.montoReplace
			montoVal = Integer.parseInt(monto[index])
		}
		
		String tax_In_String = ""
		if(montoVal>10) {
			//DOP
			double tax = montoVal*0.0015
			DecimalFormat df_obj = new DecimalFormat("#.##");
			tax_In_String = df_obj.format(tax)
		} else {
			//USD
			double tax = montoVal*0.0015*(GlobalVariable.USD_to_DOP_rate)
			DecimalFormat df_obj = new DecimalFormat("#.##");
			tax_In_String = df_obj.format(tax)
		}
		try {
			//reset list to inital pos
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 2, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/Confirmar'), 2, FailureHandling.OPTIONAL)
			
			Mobile.scrollToText(tax_In_String, FailureHandling.OPTIONAL)
			//MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'Impuesto: DOP "+tax_In_String+"' or . = 'Impuesto: DOP "+tax_In_String+"')]"))
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (contains(@text, '"+tax_In_String+"') or contains(., '"+tax_In_String+"'))]"))
			totalTax += Double.parseDouble(Element.getText().substring(14))
		} catch(Exception e) {
			KeywordUtil.logInfo("TAX IS NOT APPLIED FOR GIVEN ACCOUNT AND AMOUNT")
		}
	}
}
GlobalVariable.GetImpuesta = String.format("%.2f", totalTax)
double ImpuestoUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
GlobalVariable.GetImpuesta = ImpuestoUpdated
*/