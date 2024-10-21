import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable

if (Monto == '') {
    println('Monto Option Is Skipped...!!!')
   // CustomKeywords.'helper.customfunction.continuar'()
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
    println('Origen Account Not Available - Skipping Monto Flow...!!!')
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
   // CustomKeywords.'helper.customfunction.Screenshot'()
    println('Destino Account Not Available...!!!')
} else {
    Mobile.tap(findTestObject('TarjetaPropia/Monto Title'), 5, FailureHandling.OPTIONAL)

	if(Mobile.verifyElementVisible(findTestObject('Object Repository/TarjetaPropia/Monto a pagar Title'), 5, FailureHandling.OPTIONAL)){
		CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoCurrency)
		
		//Multiple Transaction
		if(Description.contains("Multiple Transaction")) {
			if(Monto.equals("NaN")) {
				MontoType = "Cuota mensual credimás"
			}
		}
		
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
//					CustomKeywords.'helper.customfunction.Screenshot'()
//					Mobile.delay(1)
//					Mobile.tap(findTestObject('Object Repository/NavigateBack/Back'), 5,FailureHandling.OPTIONAL)
//					Mobile.tap(findTestObject('Object Repository/NavigateBack/Back'), 5,FailureHandling.OPTIONAL)
				}
			}
			//End of decimal code'
				
			Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
			
			Mobile.delay(2)
			
		} else {
			CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoType)
			
			//Multiple Transaction
			if(Description.contains("Multiple Transaction") && Monto.equals("NaN")) {
				'REPLACE MONTO VALUE : '
				String checkMonto = Monto
				if(checkMonto.matches(".*[a-zA-Z]+.*")) {
					String preDefinedMonto = Mobile.getText(findTestObject('Object Repository/TarjetaPropia/Monto on Datos screen'), 1, FailureHandling.OPTIONAL)
					String Balance1 = preDefinedMonto.replaceAll('[,]', '')
					String Balance2 = Balance1.substring(4)
					GlobalVariable.montoReplace = Balance2.split("\\.")[0]
				}
			}	
		}
			
	} else {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.logInfo('Monto Screen not visible please check!!!')
			KeywordUtil.markFailed('Monto Screen not visible please check!!!' + Description)
	}
}