import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import com.kms.katalon.core.mobile.keyword.internal.MobileAbstractKeyword as MobileAbstractKeyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory as MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper as MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException as WebElementNotFoundException
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.ui.SystemOutputInterceptor as SystemOutputInterceptor
import io.appium.java_client.AppiumDriver as AppiumDriver
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.StringUtils as StringUtils
import java.lang.Integer as Integer
import io.appium.java_client.MultiTouchAction as MultiTouchAction
import io.appium.java_client.TouchAction as TouchAction
import io.appium.java_client.touch.WaitOptions as WaitOptions
import io.appium.java_client.touch.offset.PointOption as PointOption
import io.appium.java_client.MobileElement as MobileElement
import io.appium.java_client.remote.AndroidMobileCapabilityType as AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType as MobileCapabilityType
import io.appium.java_client.remote.MobilePlatform as MobilePlatform
import org.openqa.selenium.remote.CapabilityType as CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC

if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicios/ImpuestoSelect'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('ImpuestosYServicios/ImpuestoSelect'), 5, FailureHandling.OPTIONAL)
	Mobile.delay(1)
	Mobile.tap(findTestObject('ImpuestosYServicios/Selecciona el tipo de impuesto'), 5, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.PagoUnicoHelper'(Impuestos)
	
	if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicios/Digita el cdigo de referencia1'), 5, FailureHandling.OPTIONAL)) {
		Mobile.setText(findTestObject('ImpuestosYServicios/Digita el cdigo de referencia1'),ReferenceNo,  5, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
	} else if(Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Digita el cdigo de referencia'), 2, FailureHandling.OPTIONAL)) {
		Mobile.setText(findTestObject('Benef_ProductYServicios/Digita el cdigo de referencia'),ReferenceNo,  2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
	} else {
		println('Digita El Cdigo De Referencia Not Found..')
		//CustomKeywords.'helper.customfunction.Screenshot'()
	}
	
	Mobile.tap(findTestObject('Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
	
} else if(Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Impuestos y servicios - Title'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Benef_ProductYServicios/Impuestos y servicios - Title'), 5, FailureHandling.OPTIONAL)
	Mobile.delay(1)
	Mobile.setText(findTestObject('Benef_ProductYServicios/SelectServicios - Selecciona un proveedor'),Impuestos,  5, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.HideKeyboard'()
	Mobile.tap(findTestObject('Benef_ProductYServicios/SelectServicios - SearchButton'), 3, FailureHandling.OPTIONAL)
	Mobile.delay(3)
	
	AppiumDriver<?> driver = MobileDriverFactory.getDriver()
	String elementSelector = Impuestos
	try {
		MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+elementSelector+"' or . = '"+elementSelector+"')]"))
		 if(Element.displayed.TRUE) {
			 Element.click()
	 //		if (Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Button - Guardar'), 3, FailureHandling.OPTIONAL)){
	 //			KeywordUtil.markPassed(Impuestos+" Selected Sucessfully...!!!")
	 //		} else if(Mobile.waitForElementPresent(findTestObject('Object Repository/Buttons/Continuar-Payment'), 5, FailureHandling.OPTIONAL)){
	 //			KeywordUtil.markPassed(Impuestos+" Selected Sucessfully...!!!")
	 //		} else {
	 //			CustomKeywords.'helper.customfunction.Screenshot'()
	 //			KeywordUtil.markFailed(Impuestos+' Not Selected...!!!')
	 //		}
		 } else {
			 //Mobile.backbutton
			 //Mobile.tap(findTestObject('Benef_ProductYServicios/Impuestos y servicios - Title'), 5, FailureHandling.OPTIONAL)
//			 CustomKeywords.'helper.customfunction.PagoUnicoHelper'(Impuestos)
		 }
	} catch(Exception e) {
		if(Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/No se han encontrado resultados'), 3, FailureHandling.OPTIONAL)){
			GlobalVariable.PaymentMessage = Mobile.getText(findTestObject('Benef_ProductYServicios/No se han encontrado resultados'), 3, FailureHandling.OPTIONAL)
			println("No se han encontrado resultados. Inpuestos "+ Impuestos +" Is Not Available...!!")
			//CustomKeywords.'helper.customfunction.Screenshot'()
			//KeywordUtil.markFailed("No se han encontrado resultados. Inpuestos "+ Impuestos +" Is Not Available...!!")
			
		} else {
			//Mobile.backbutton
			//Mobile.tap(findTestObject('Benef_ProductYServicios/Impuestos y servicios - Title'), 5, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.PagoUnicoHelper'(Impuestos)
		}
	}
	
	//CustomKeywords.'helper.customfunction.PagoUnicoHelper'(Impuestos)
	
	if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicios/Digita el cdigo de referencia1'), 5, FailureHandling.OPTIONAL)) {
		Mobile.setText(findTestObject('ImpuestosYServicios/Digita el cdigo de referencia1'),ReferenceNo,  5, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
	} else if(Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Digita el cdigo de referencia'), 2, FailureHandling.OPTIONAL)) {
		Mobile.setText(findTestObject('Benef_ProductYServicios/Digita el cdigo de referencia'),ReferenceNo,  2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
	} else {
		println('Digita El Cdigo De Referencia Not Found..')
		//CustomKeywords.'helper.customfunction.Screenshot'()
	}
	Mobile.setText(findTestObject('Benef_ProductYServicios/Digita un alias para el servicio'), Impuestos, 5, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.HideKeyboard'()
	
} else {
	println('Monto Not Found..')
	//CustomKeywords.'helper.customfunction.Screenshot'()
	KeywordUtil.markFailed('Unable To Select Service Monto..')
}