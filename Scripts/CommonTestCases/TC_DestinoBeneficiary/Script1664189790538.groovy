import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement

GlobalVariable.BenefUnicoDetails = "True"

if (CtaDestino == 'NA') {
	KeywordUtil.markPassed('Destino Beneficiary Not Available...!!!')
} else if(GlobalVariable.OrigenAccountStatus.equals("NotAvailable")) {
	KeywordUtil.markFailed('Given Origen Account is not Present in List Origen Account not Selected Skipping further flow!!!')
} else  {
	Mobile.tap(findTestObject('TransferenciaPropia/Destino'), 5, FailureHandling.OPTIONAL)

    Mobile.delay(1)

    String Attribute = Mobile.getAttribute(findTestObject('Object Repository/TransferenciaTerceroBeneficiary/Destino Beneficiario Button'), 'enabled', 2, FailureHandling.CONTINUE_ON_FAILURE)

    if ((Attribute == 'True') || (Attribute == 'true')) {
        Mobile.tap(findTestObject('Object Repository/TransferenciaTerceroBeneficiary/Destino Beneficiario Button'), 3, FailureHandling.OPTIONAL)

		if(Mobile.verifyElementVisible(findTestObject('Object Repository/TransferenciaTerceroBeneficiary/No se encontraron beneficiarios (msg)'), 2, FailureHandling.OPTIONAL)) {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailed('***No beneficiary account found for case*** - ' + Description)
		} else {
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			String Description = Description
			
			if(Description.contains("MultiCurrency")) {
				
				if(GlobalVariable.CurrencyTypeOrigen.equals("USD")) {
					try {
						Mobile.scrollToText("DOP", FailureHandling.CONTINUE_ON_FAILURE)
						GlobalVariable.CaseStatusMessage += "\n *** Beneficiary contains other currency account compared to origen account *** \n"
						KeywordUtil.markFailed("*** Beneficiary contains other currency account compared to origen account ***")
					} catch(Exception e) {
						GlobalVariable.CaseStatusMessage += "\n *** Beneficiary contains same currency account compared to origen account *** \n"
						KeywordUtil.markPassed("*** Beneficiary contains same currency account compared to origen account ***")
					}	
				} else if(GlobalVariable.CurrencyTypeOrigen.equals("DOP")) {
					try {
						Mobile.scrollToText("USD", FailureHandling.CONTINUE_ON_FAILURE)
						GlobalVariable.CaseStatusMessage += "\n *** Beneficiary contains other currency account compared to origen account *** \n"
						KeywordUtil.markFailed("*** Beneficiary contains other currency account compared to origen account ***")
					} catch(Exception e) {
						GlobalVariable.CaseStatusMessage += "\n *** Beneficiary contains same currency account compared to origen account *** \n"
						KeywordUtil.markPassed("*** Beneficiary contains same currency account compared to origen account ***")
					}
				}
				
			} else {
				//Multiple Transaction
				if(Description.contains("Multiple Transaction")) {
					if(CtaDestino.contains("Servicio-")) {
						CtaDestino = CtaDestino.substring(9)
						Mobile.tap(findTestObject('MultipleTransactions/Beneficiario de servicios button'), 2, FailureHandling.OPTIONAL)
					} else {
						Mobile.tap(findTestObject('MultipleTransactions/Beneficiario de producto Button'), 2, FailureHandling.OPTIONAL)
					}
				}
				CustomKeywords.'helper.customfunction.DestinoAccount'(CtaDestino)
			}
		}
        GlobalVariable.destino_before_app = "Destino is Beneficiary Account - No Balance is visible"
    } else {
        //CustomKeywords.'helper.customfunction.Screenshot'()

        println('Beneficiary Button Is Disabled...!!!')

        KeywordUtil.markPassed('Beneficiary Button Is Disabled For Case ' + Description)
    }
}