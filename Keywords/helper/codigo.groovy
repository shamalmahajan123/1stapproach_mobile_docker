package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable

public class codigo {
	/*
	 @Keyword
	 def TarjetaCodigo_Cedula(String Position) {
	 int Value = Integer.parseInt(Position)
	 ArrayList<String> ArrayCode = new ArrayList<String>(Arrays.asList('0', '4411', '7313', '5134','2438','4635','7149','3400','0268','9501','6168','5301','5251','4122','0317','3860','5844','0569','2724','8846','3702','5514','2255','6082','9374','3823','7045','1934','4214','4027','2022','5315','3860','1174','2242','8954','1945','5645','1339','2255','7247'))
	 for (int i = 1; i <= 40; i++) {
	 try {
	 if (Value == i) {
	 String CodeValue = ArrayCode.get(i)
	 System.out.println('Code Value Is ' + CodeValue)
	 GlobalVariable.CodeValue = CodeValue
	 return CodeValue
	 break
	 }
	 }
	 catch (Exception e) {
	 e.printStackTrace()
	 }
	 }
	 }
	 */

	@Keyword
	def TarjetaCodigo_Cedula(String Position) {
		int Value = Integer.parseInt(Position)

		switch (Value) {
			case 1:
				GlobalVariable.CodeValue = '4411'
				break;
			case 2:
				GlobalVariable.CodeValue = '7313'
				break;
			case 3:
				GlobalVariable.CodeValue = '5134'
				break;
			case 4:
				GlobalVariable.CodeValue = '2438'
				break;
			case 5:
				GlobalVariable.CodeValue = '4635'
				break;
			case 6:
				GlobalVariable.CodeValue = '7149'
				break;
			case 7:
				GlobalVariable.CodeValue = '3400'
				break;
			case 8:
				GlobalVariable.CodeValue = '0268'
				break;
			case 9:
				GlobalVariable.CodeValue = '9501'
				break;
			case 10:
				GlobalVariable.CodeValue = '6168'
				break;
			case 11:
				GlobalVariable.CodeValue = '5301'
				break;
			case 12:
				GlobalVariable.CodeValue = '5251'
				break;
			case 13:
				GlobalVariable.CodeValue = '4122'
				break;
			case 14:
				GlobalVariable.CodeValue = '0317'
				break;
			case 15:
				GlobalVariable.CodeValue = '3860'
				break;
			case 16:
				GlobalVariable.CodeValue = '5844'
				break;
			case 17:
				GlobalVariable.CodeValue = '0569'
				break;
			case 18:
				GlobalVariable.CodeValue = '2724'
				break;
			case 19:
				GlobalVariable.CodeValue = '8846'
				break;
			case 20:
				GlobalVariable.CodeValue = '3702'
				break;
			case 21:
				GlobalVariable.CodeValue = '5514'
				break;
			case 22:
				GlobalVariable.CodeValue = '2255'
				break;
			case 23:
				GlobalVariable.CodeValue = '6082'
				break;
			case 24:
				GlobalVariable.CodeValue = '9374'
				break;
			case 25:
				GlobalVariable.CodeValue = '3823'
				break;
			case 26:
				GlobalVariable.CodeValue = '7045'
				break;
			case 27:
				GlobalVariable.CodeValue = '1934'
				break;
			case 28:
				GlobalVariable.CodeValue = '4214'
				break;
			case 29:
				GlobalVariable.CodeValue = '4027'
				break;
			case 30:
				GlobalVariable.CodeValue = '2022'
				break;
			case 31:
				GlobalVariable.CodeValue = '5315'
				break;
			case 32:
				GlobalVariable.CodeValue = '3860'
				break;
			case 33:
				GlobalVariable.CodeValue = '1174'
				break;
			case 34:
				GlobalVariable.CodeValue = '2242'
				break;
			case 35:
				GlobalVariable.CodeValue = '8954'
				break;
			case 36:
				GlobalVariable.CodeValue = '1945'
				break;
			case 37:
				GlobalVariable.CodeValue = '5645'
				break;
			case 38:
				GlobalVariable.CodeValue = '1339'
				break;
			case 39:
				GlobalVariable.CodeValue = '2255'
				break;
			case 40:
				GlobalVariable.CodeValue = '7247'
				break;
		}
	}
}
