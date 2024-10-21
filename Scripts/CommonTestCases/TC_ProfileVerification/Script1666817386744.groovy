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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC

Profile()
def Profile() {
	def executionProfile = RC.getExecutionProfile()
	
	if (executionProfile.equalsIgnoreCase('local')) {
		println('Profile Set Is Local - Bypass DB Connection TCs')
		WebUI.comment('Profile Set Is Local - Bypass DB Connection TCs')
	
	} else if (executionProfile.equalsIgnoreCase('default')) {
		println('Profile Set Is Server - Executing DB Connection TCs ')
		WebUI.comment('Profile Set Is Server - Executing DB Connection TCs ')
		
		'CONNECT DATABASE : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_ConnectionToDatabase'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino], FailureHandling.CONTINUE_ON_FAILURE)
			
		'GET DATABASE ORIGIN ACCOUNT BALANCE  : '
		if (Description.contains('AvanceDeCredimas')) {
			CustomKeywords.'helper.functionsupdate.credimas_DBBefore'(CuentaOrigen, MontoCurrency)
		} else {
			CustomKeywords.'helper.functionsupdate.ConsultaBalance_Origen'(CuentaOrigen)
		}
		
		'GET DATABASE DESTINO ACCOUNT BALANCE  : '
		if (Description.contains('Prestamo')) {
			CustomKeywords.'helper.functionsupdate.ConsultaBalancePrestamos'(CuentaDestino)
		} else if(Description.contains('Multiple Transaction')) {
			int TotalDestinoDB = Integer.parseInt(DestinoCount)
			ArrayList<String> DestinoAccDB = new ArrayList<String>(Arrays.asList(CuentaDestino1, CuentaDestino2, CuentaDestino3, CuentaDestino4, CuentaDestino5, CuentaDestino6))
			ArrayList<String> MontoCurr = new ArrayList<String>(Arrays.asList(MontoCurrency1, MontoCurrency2))
			int i = 0;
			for(int count = 0; count < TotalDestinoDB; count++) {
				if(DestinoAccDB[count].length() > 10 && DestinoAccDB[count] != "NA"  && DestinoAccDB[count].matches("[0-9]+")) {
					CustomKeywords.'helper.functionsupdate.ConsultaBalance_TarjetaBefore'(DestinoAccDB[count], MontoCurr[i])
					CustomKeywords.'helper.customfunction.DestinoBeforeDBMap'(count, TotalDestinoDB)
					i++
				} else if(DestinoAccDB[count].length() == 10 && DestinoAccDB[count] != "NA" && DestinoAccDB[count].matches("[0-9]+")) {
					CustomKeywords.'helper.functionsupdate.ConsultaBalance_Destino'(DestinoAccDB[count])
					CustomKeywords.'helper.customfunction.DestinoBeforeDBMap'(count, TotalDestinoDB)
				} else {
					GlobalVariable.destino_before_db = "Destino is Beneficiary Account - No Balance is visible"
					CustomKeywords.'helper.customfunction.DestinoBeforeDBMap'(count, TotalDestinoDB)
				}
			}
		} else if (Description.contains('TarjetaPropia')) {
			CustomKeywords.'helper.functionsupdate.ConsultaBalance_TarjetaBefore'(CuentaDestino, MontoCurrency)
		} else {				
			CustomKeywords.'helper.functionsupdate.ConsultaBalance_Destino'(CuentaDestino)
		}

	}
}