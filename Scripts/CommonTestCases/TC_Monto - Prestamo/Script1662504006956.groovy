import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.annotation.Keyword as Keyword

if (Monto == '') {
    println('Monto Option Is Skipped...!!!')
    CustomKeywords.'helper.customfunction.continuar'()
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
    println('Origen Account Not Available - Skipping Monto Flow...!!!')
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
    CustomKeywords.'helper.customfunction.Screenshot'()
    println('Destino Account Not Available...!!!')
} else {	
    Mobile.tap(findTestObject('TransferenciaPropia/Digita un monto'), 5, FailureHandling.CONTINUE_ON_FAILURE)
	if (Mobile.waitForElementPresent(findTestObject('Object Repository/Prestamo Propia/Otro monto Title'), 60, FailureHandling.OPTIONAL)) {
		if(MontoType.equals("Otro monto")) {
			CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoType)
			String data
			int j = 0
			//int i = 0
			int num
			num = Monto.length()
			/*
			while (i < num) {
				Mobile.tap(findTestObject('Buttons/Numeros/Button' + Monto.substring(j, i + 1)), 0)
				j++
				i++
			}*/
			
			for(int i=0;i<num;i++) {
				Mobile.tap(findTestObject('Keypad/android.widget.Button - '+ Monto.substring(j, i+1)), 0)
				j++
			}
			
			//Decimal code
			if (Decimal == '0' || Decimal == 'NA') {
				println('Number Is Not Decimal Number')
			} else {
				Value = Mobile.getAttribute(findTestObject('Buttons/Numeros/Button-Dot'), 'enabled', 0, FailureHandling.OPTIONAL)
				GlobalVariable.DotButton = Value
				println "GlobalVariable.DotButton" + GlobalVariable.DotButton
				if ((Value == 'True') || (Value == 'true')) {
					Mobile.tap(findTestObject('Buttons/Numeros/Button-Dot'), 0, FailureHandling.OPTIONAL)
					j = 0
					i = 0
					int decimal_num
					decimal_num = Decimal.length()
					println('DECIMAL NUM ' + decimal_num)
					
					for(int i=0;i<decimal_num;i++) {
						Mobile.tap(findTestObject('Keypad/android.widget.Button - '+ Decimal.substring(j, i+1)), 0)
						j++
					}
					/*
					while (i < decimal_num) {
						Mobile.tap(findTestObject('Buttons/Numeros/Button' + Decimal.substring(j, i + 1)), 0)
						j++
						i++
					}
					*/
				} else {
					println('Dot Is Disabled For Decimal Entry')
					GlobalVariable.Keyboard = 'false'
					CustomKeywords.'helper.customfunction.Screenshot'()
					Mobile.delay(1)
					Mobile.tap(findTestObject('Object Repository/NavigateBack/Back'), 5,FailureHandling.OPTIONAL)
					Mobile.tap(findTestObject('Object Repository/NavigateBack/Back'), 5,FailureHandling.OPTIONAL)
				}
			}
			//End of decimal code'
		} else {
			CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoType)
		}
		
	} else if (Mobile.waitForElementPresent(findTestObject('Object Repository/ErrorMessages/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 30, FailureHandling.OPTIONAL)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('Object Repository/Buttons/ACEPTAR'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(2)
		Mobile.tap(findTestObject('TransferenciaPropia/Digita un monto'),5, FailureHandling.OPTIONAL)
		Mobile.delay(15)
		Mobile.waitForElementPresent(findTestObject('Object Repository/ErrorMessages/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 60, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.Screenshot'()
		
		if(MontoType.equals("Otro monto")) {
			CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoType)
			String data
			int j = 0
			//int i = 0
			int num
			num = Monto.length()
			/*
			while (i < num) {
				Mobile.tap(findTestObject('Buttons/Numeros/Button' + Monto.substring(j, i + 1)), 0)
				j++
				i++
			}
			*/
			
			for(int i=0;i<num;i++) {
				Mobile.tap(findTestObject('Keypad/android.widget.Button - '+ Monto.substring(j, i+1)), 0)
				j++
			}
			
			//Decimal code
			if (Decimal == '0' || Decimal == 'NA') {
				println('Number Is Not Decimal Number')
			} else {
				Value = Mobile.getAttribute(findTestObject('Buttons/Numeros/Button-Dot'), 'enabled', 0, FailureHandling.OPTIONAL)
				GlobalVariable.DotButton = Value
				println "GlobalVariable.DotButton" + GlobalVariable.DotButton
				if ((Value == 'True') || (Value == 'true')) {
					Mobile.tap(findTestObject('Buttons/Numeros/Button-Dot'), 0, FailureHandling.OPTIONAL)
					j = 0
					i = 0
					int decimal_num
					decimal_num = Decimal.length()
					println('DECIMAL NUM ' + decimal_num)
					for(int i=0;i<decimal_num;i++) {
						Mobile.tap(findTestObject('Keypad/android.widget.Button - '+ Decimal.substring(j, i+1)), 0)
						j++
					}
					/*
					while (i < decimal_num) {
						Mobile.tap(findTestObject('Buttons/Numeros/Button' + Decimal.substring(j, i + 1)), 0)
						j++
						i++
					}
					*/
				} else {
					println('Dot Is Disabled For Decimal Entry')
					GlobalVariable.Keyboard = 'false'
					CustomKeywords.'helper.customfunction.Screenshot'()
					Mobile.delay(1)
					Mobile.tap(findTestObject('Object Repository/NavigateBack/Back'), 5,FailureHandling.OPTIONAL)
					Mobile.tap(findTestObject('Object Repository/NavigateBack/Back'), 5,FailureHandling.OPTIONAL)
				}
			}
			//End of decimal code'
			Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
			Mobile.delay(2)
		} else {
			CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoType)
		}
	}
	 
	if (Mobile.waitForElementPresent(findTestObject('Object Repository/ErrorMessages/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Object Repository/Buttons/ACEPTAR'), 5, FailureHandling.OPTIONAL)
		KeywordUtil.markFailed("Terminating Test Case Due To Error Message...!!!")
	}
}