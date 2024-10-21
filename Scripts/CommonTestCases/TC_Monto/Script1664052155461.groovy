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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

if (Monto == 'NA') {
    println('Monto Option Is Skipped...!!!')
   // CustomKeywords.'helper.customfunction.continuar'()
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
	GlobalVariable.origen_before_app = "SelectionFailed"
    println('Origen Account Not Available - Skipping Monto Flow...!!!')
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
	GlobalVariable.destino_before_app = "SelectionFailed"
    //CustomKeywords.'helper.customfunction.Screenshot'()
    println('Destino Account Not Available...!!!')
} else {
    Mobile.tap(findTestObject('TransferenciaPropia/Digita un monto'), 5, FailureHandling.OPTIONAL)
	Mobile.tap(findTestObject('ImpuestosYServicios/Monto - Title'), 1, FailureHandling.OPTIONAL)
	Mobile.tap(findTestObject('EnvioTuEfectivo/Selecciona la cantidad Monto'), 1, FailureHandling.OPTIONAL)

	//Multiple Transaction
	if(Description.contains("Multiple Transaction") && Monto.equals("NaN")) {
		MontoSelection()
		'REPLACE MONTO VALUE : '
		String checkMonto = Monto
		try {
			if(checkMonto.matches(".*[a-zA-Z]+.*")) {
				String preDefinedMonto = Mobile.getText(findTestObject('Object Repository/TarjetaPropia/Monto on Datos screen'), 1, FailureHandling.OPTIONAL)
				String Balance1 = preDefinedMonto.replaceAll('[,]', '')
				String Balance2 = Balance1.substring(4)
				GlobalVariable.montoReplace = Balance2.split("\\.")[0]
			}
		} catch(Exception e) {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailed("Monto value is not visible please check!!!")
		}
	}
	
    String data

    int j = 0

    //int i = 0
	
	//int a

    int num

    num = Monto.length()
	/*
    while (i < num) {
		Mobile.delay(1)
        Mobile.tap(findTestObject('Keypad/Button'+ Monto.substring(j, i+1)), 3, FailureHandling.OPTIONAL)
		j++
		i++
    }*/
	
    try {
		for(int i=0;i<num;i++) {
			Mobile.tap(findTestObject('Keypad/android.widget.Button - '+ Monto.substring(j, i+1)), 3, FailureHandling.OPTIONAL)
			j++
		}
    } catch(Exception e) {
			KeywordUtil.logInfo("KEYPAD NOT VISIBLE!!!")
	}
	
    if (Decimal == '0' || Decimal == 'NA') {
        println('Number Is Not Decimal Number')
    } else {
		Value = ''
		
        Value = Mobile.getAttribute(findTestObject('Buttons/Numeros/Button-Dot'), 'enabled', 3, FailureHandling.OPTIONAL)

        GlobalVariable.DotButton = Value
		println "GlobalVariable.DotButton" + GlobalVariable.DotButton

        if ((Value == 'True') || (Value == 'true')) {
            Mobile.tap(findTestObject('Buttons/Numeros/Button-Dot'), 3, FailureHandling.OPTIONAL)

            j = 0

            i = 0

            int decimal_num

            decimal_num = Decimal.length()

            println('DECIMAL NUM ' + decimal_num)

			for(int i=0;i<decimal_num;i++) {
				Mobile.tap(findTestObject('Keypad/android.widget.Button - '+ Decimal.substring(j, i+1)), 3, FailureHandling.OPTIONAL)
				j++
			}
			
            /*while (i < decimal_num) {
                Mobile.tap(findTestObject('Buttons/Numeros/Button' + Decimal.substring(j, i + 1)), 3, FailureHandling.OPTIONAL)

                j++

                i++
            }*/
        } else {
			
			if ((Value == '') || (Value == null)) {
				println('Dot Button Not Found For Decimal Entry')
				
			} else if ((Value == 'False') || (Value == 'false')) {
				println('Dot Is Disabled For Decimal Entry')
				GlobalVariable.Keyboard = 'false'
//				CustomKeywords.'helper.customfunction.Screenshot'()
//				Mobile.delay(1)
//				Mobile.tap(findTestObject('Object Repository/NavigateBack/Back'), 5,FailureHandling.OPTIONAL)
//				Mobile.tap(findTestObject('Object Repository/NavigateBack/Back'), 5,FailureHandling.OPTIONAL)
			}
        }
    }
    
    Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
    Mobile.delay(3)

}

def MontoSelection() {
	if (Mobile.waitForElementPresent(findTestObject('Servicios/Monto a pagar'), 15, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Servicios/DOP 500.00'),1,FailureHandling.OPTIONAL)
		
		if(Mobile.waitForElementPresent(findTestObject('Servicios/Monto mximo'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Servicios/Monto mximo'), 1)
		}
		if(Mobile.waitForElementPresent(findTestObject('Servicios/Monto mnimo'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Servicios/Monto mnimo'), 1)
		}
		if(Mobile.waitForElementPresent(findTestObject('Servicios/Balance total'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Servicios/Balance total'), 1)
		}
		
		if(Mobile.waitForElementPresent(findTestObject('Servicios/No existe saldo pendiente para este servicio'), 1, FailureHandling.OPTIONAL)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailed("Terminating Test Case Due To Error Message...!!!")
		}
		
	} else if (Mobile.waitForElementPresent(findTestObject('Servicios/Elegir factura - Title'), 60, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Servicios/Elegir Selection'),1,FailureHandling.OPTIONAL)
			if (Mobile.waitForElementPresent(findTestObject('Object Repository/Servicios/Monto a pagar'), 60, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Servicios/Monto total'),1,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Servicios/Monto pendiente'),1,FailureHandling.OPTIONAL)
			}
	} else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/Ocurrio un error. Favor contactar el administrador del sistema'), 5, FailureHandling.OPTIONAL)) {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed("Terminating Test Case Due To Error Message...!!!")
	} else if (Mobile.waitForElementPresent(findTestObject('ErrorMessages/Referencia no encontrada'), 5, FailureHandling.OPTIONAL)) {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed("Terminating Test Case Due To Error Message...!!!")
	}
	
}