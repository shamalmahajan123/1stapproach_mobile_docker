package helper
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import internal.GlobalVariable
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileAbstractKeyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import io.appium.java_client.AppiumDriver as AppiumDriver
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.StringUtils as StringUtils
import java.lang.Integer as Integer
import io.appium.java_client.MobileElement as MobileElement
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Transparency;

class customfunction extends takescreenshot {

	@Keyword
	def LoginToken(Role,Cedula,Pasaporte,Password,Email) {
		String ActualUsername = Mobile.getText(findTestObject('Login/GetLoggedUsername'), 5, FailureHandling.OPTIONAL)
		println "Logged UserName Is " + ActualUsername

		if (Role == "Cedula") {
			println "Role Is " + Role
			GlobalVariable.Username = Cedula
		} else if (Role == "Pasaporte") {
			println "Role Is " + Role
			GlobalVariable.Username = Pasaporte
		}
		println "GlobalVariable.Username Is " + GlobalVariable.Username

		if (ActualUsername.equals(GlobalVariable.Username)) {
			'ENTER CORRECT PASSWORD:'
			Mobile.delay(2)
			Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 5, FailureHandling.STOP_ON_FAILURE)
			HideKeyboard()
			'CLICK ON CONTINUAR BUTTON:'
			Mobile.tap(findTestObject('Buttons/ACCEDER'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(5)
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboardToken'), [:], FailureHandling.STOP_ON_FAILURE)
		} else {
			'LOG OUT AND USER REGISTRATION FLOW :'
			if (ActualUsername.equals(GlobalVariable.Username)) {
				println "Logged User Is " + GlobalVariable.Username
			} else if (ActualUsername.equals('IDENTIFICACIÓN')) {
				println "UserName Field Is Blank - No User Is Logged In..!!"
			} else {
				if (Mobile.waitForElementPresent(findTestObject('Login/ClearFields'), 30, FailureHandling.OPTIONAL)) {
					Mobile.tap(findTestObject('Login/ClearFields'), 2, FailureHandling.OPTIONAL)
				} else if (Mobile.waitForElementPresent(findTestObject('Login/ClearFields1'), 30, FailureHandling.OPTIONAL)) {
					Mobile.tap(findTestObject('Login/ClearFields1'), 2, FailureHandling.OPTIONAL)
				}
				Mobile.delay(3)
				Mobile.tap(findTestObject('Buttons/DESVINCULAR'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(3)
				Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			}
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLogin'), [('Username') : GlobalVariable.Username, ('Password') : Password],
			FailureHandling.STOP_ON_FAILURE)
			Mobile.delay(3)
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickEmail'), [('Email') : Email], FailureHandling.OPTIONAL)
			Mobile.delay(5)
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboardToken'), [:], FailureHandling.OPTIONAL)
			Mobile.delay(1)
		}
	}

	@Keyword
	def QuickLogin(Role,Cedula,Pasaporte,Password,Email) {
		String ActualUsername = Mobile.getText(findTestObject('Login/GetLoggedUsername'), 5, FailureHandling.OPTIONAL)
		println "Logged UserName Is " + ActualUsername

		if (Role == "Cedula") {
			println "Role Is " + Role
			GlobalVariable.Username = Cedula
		} else if (Role == "Pasaporte") {
			println "Role Is " + Role
			GlobalVariable.Username = Pasaporte
		}
		println "GlobalVariable.Username Is " + GlobalVariable.Username

		if (ActualUsername.equals(GlobalVariable.Username)) {
			'ENTER CORRECT PASSWORD:'
			Mobile.delay(1)
			Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 5, FailureHandling.STOP_ON_FAILURE)
			HideKeyboard()
			'CLICK ON ACCEDER BUTTON:'
			Mobile.tap(findTestObject('Buttons/ACCEDER'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(1)
			if(Mobile.waitForElementPresent(findTestObject('ErrorMessages/ERROR EN PROCESAMIENTO'), 30, FailureHandling.CONTINUE_ON_FAILURE) || Mobile.waitForElementPresent(findTestObject('ErrorMessages/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
				Screenshot()
				GlobalVariable.CaseStatusMessage+='\nLogin Error'
				KeywordUtil.markFailedAndStop('ERROR EN PROCESAMIENTO or Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente')
			}
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboard'), [:], FailureHandling.STOP_ON_FAILURE)
		} else {

			'LOG OUT AND USER REGISTRATION FLOW :'
			if (ActualUsername.equals(GlobalVariable.Username)) {
				println "Logged User Is " + GlobalVariable.Username
			} else if (ActualUsername.equals('IDENTIFICACIÓN')) {
				println "UserName Field Is Blank - No User Is Logged In..!!"
			} else {
				if (Mobile.waitForElementPresent(findTestObject('Login/ClearFields'), 30, FailureHandling.OPTIONAL)) {
					Mobile.tap(findTestObject('Login/ClearFields'), 2, FailureHandling.OPTIONAL)
				} else if (Mobile.waitForElementPresent(findTestObject('Login/ClearFields1'), 30, FailureHandling.OPTIONAL)) {
					Mobile.tap(findTestObject('Login/ClearFields1'), 2, FailureHandling.OPTIONAL)
				}
				Mobile.delay(2)
				Mobile.tap(findTestObject('Buttons/DESVINCULAR'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(2)
				Mobile.tap(findTestObject('Buttons/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			}
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLogin'), [('Username') : GlobalVariable.Username, ('Password') : Password],
			FailureHandling.STOP_ON_FAILURE)
			Mobile.delay(3)
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickEmail'), [('Email') : Email], FailureHandling.OPTIONAL)
			Mobile.delay(5)
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboard'), [:], FailureHandling.OPTIONAL)
			Mobile.delay(1)
		}
	}
	/*
	 @Keyword
	 def LoginResponses() {
	 try {
	 for(int rowNum=1; rowNum<=findTestData("Excel-Data/Responses").getRowNumbers();rowNum++) {
	 if (Mobile.waitForElementPresent(findTestObject(findTestData("Excel-Data/Responses").getValue(3,rowNum)),3, FailureHandling.CONTINUE_ON_FAILURE)) {
	 KeywordUtil.markPassed("Mentioned Responses Found " + findTestData("Excel-Data/Responses").getValue(2,rowNum))
	 break
	 } else {
	 println "Try Other Responses"
	 }
	 }
	 } catch(Exception e) {
	 //println e.printStackTrace()
	 }
	 }
	 */
	@Keyword
	def VerificationDashboard(String Description) {
		if (Mobile.waitForElementPresent(findTestObject('VerificationDashboard/Configuraciones'), 10, FailureHandling.STOP_ON_FAILURE)) {

			//Mobile.tap(findTestObject('VerificationDashboard/InActive1'), 2,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('VerificationDashboard/InActive2'), 2,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/ContinuarNotification'), 10,FailureHandling.OPTIONAL)

			Mobile.tap(findTestObject('Buttons/Confirmar'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(2)
			Mobile.tap(findTestObject('Buttons/Confirmar'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(2)

			Mobile.tap(findTestObject('Buttons/SALTAR'), 5,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/Terms-Acepto'), 10,FailureHandling.OPTIONAL)
			if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 80, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 3, FailureHandling.OPTIONAL)
			}
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'),  3,FailureHandling.OPTIONAL)
			KeywordUtil.markPassed('Scenario Passed...!!!' + Description)

			GetUserDetails()
			Mobile.tap(findTestObject('Buttons/Productos'),  3,FailureHandling.OPTIONAL)
		}
	}

	@Keyword
	def GetUserDetails() {
		String Username = Mobile.getText(findTestObject('UserDetails/LoggedUserName'), 2, FailureHandling.OPTIONAL)
		String UserLoggedTime = Mobile.getText(findTestObject('UserDetails/LoggedUserTime'), 2, FailureHandling.OPTIONAL)

		Mobile.delay(2)
		println('LOGGED USERNAME : ' +  Username + " LOGGED TIME : " + UserLoggedTime)
	}

	@Keyword
	def OrigenAccount(String CtaOrigen) {
		if(Mobile.verifyElementExist(findTestObject('TransferenciaPropia/Cuentas-List'), 05, FailureHandling.OPTIONAL) || Mobile.verifyElementExist(findTestObject('Object Repository/AvanceDeEfectivo/Origen Title'), 05, FailureHandling.OPTIONAL)) {
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			try {
				Mobile.scrollToText(CtaOrigen, FailureHandling.OPTIONAL)
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaOrigen+"' or . = '"+CtaOrigen+"')]"))

				if(Element.displayed.TRUE) {
					Element.click()
					if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Origen'), 5, FailureHandling.OPTIONAL)){
						KeywordUtil.markPassed("Origen Account Selected Sucessfuly...!!!")
						GlobalVariable.OrigenAccountStatus = "Available"
						if(Mobile.verifyElementVisible(findTestObject('GetBalances/GetOrigenBalance'), 1, FailureHandling.OPTIONAL)) {
							GlobalVariable.origen_before_app = Mobile.getText(findTestObject('GetBalances/GetOrigenBalance'), 3, FailureHandling.OPTIONAL)
						} else if(Mobile.verifyElementVisible(findTestObject('GetBalances/GetOrigenBalance-Envio TuEfectivo'), 1, FailureHandling.OPTIONAL)) {
							GlobalVariable.origen_before_app = Mobile.getText(findTestObject('GetBalances/GetOrigenBalance-Envio TuEfectivo'), 3, FailureHandling.OPTIONAL)
						} else {
							GlobalVariable.origen_before_app = Mobile.getText(findTestObject('GetBalances/GetOrigenBalance-Avance de efectivo'), 3, FailureHandling.OPTIONAL)
						}
						String CurrencyOrigen = GetCurrency(GlobalVariable.origen_before_app)
						GlobalVariable.CurrencyTypeOrigen = CurrencyOrigen
						String OrigenBalanceBefore = FormatBalance(GlobalVariable.origen_before_app)
						GlobalVariable.origen_before_app = OrigenBalanceBefore
					} else {
						GlobalVariable.origen_before_app = "SelectionFailed"
						GlobalVariable.OrigenAccountStatus = "NotAvailable"
						if(Mobile.waitForElementPresent(findTestObject('PaymentMessages/Por favor complete el campo transaccin no vlida'), 2, FailureHandling.OPTIONAL)){
							println "Error Occured - Por favor complete el campo transaccin no vlida..!!"
						} else if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/No se permiten transacciones en monedas distintas'), 1, FailureHandling.OPTIONAL)) {
							println "Error Occured - No se permiten transacciones en monedas distintas..!!"
						} else {
							//Screenshot()
							//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
							//KeywordUtil.markFailed('Origin Account Not Selected...!!!')
							KeywordUtil.markPassed('Origin Account Not Selected...!!!')
						}
					}
				} else {
					GlobalVariable.origen_before_app = "CtaOrigen Account Not Found " + CtaOrigen
					println "CtaOrigen Account Not Found " + CtaOrigen
					GlobalVariable.OrigenAccountStatus = "NotAvailable"
					//Screenshot()
					//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
					KeywordUtil.markFailed('CtaOrigen Account Not Found ' + CtaOrigen)
				}
			} catch(Exception e) {
				GlobalVariable.origen_before_app = "CtaOrigen Account Not Found " + CtaOrigen
				GlobalVariable.CaseStatusMessage += "\n *** CtaOrigen Account Not Found " + CtaOrigen +" *** \n"
				//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
				GlobalVariable.OrigenAccountStatus = "NotAvailable"
				//Screenshot()
				//e.printStackTrace()
				KeywordUtil.markFailed('CtaOrigen Account Not Found ' + CtaOrigen)
			}
		} else {
			GlobalVariable.origen_before_app = "CtaOrigen Account Not Found " + CtaOrigen
			//Screenshot()
			GlobalVariable.OrigenAccountStatus = "NotAvailable"
			//KeywordUtil.markPassed("Origen Account Not Available - Redirected To Test Case...!!!")
			//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
			KeywordUtil.markFailed('CtaOrigen Account Not Found ' + CtaOrigen)
		}
	}

	@Keyword
	def DestinoAccount(String CtaDestino) {

		if(Mobile.verifyElementExist(findTestObject('TransferenciaPropia/Destino'), 20, FailureHandling.OPTIONAL) || Mobile.verifyElementExist(findTestObject('TransferenciaPropia/Cuentas-List'), 05, FailureHandling.OPTIONAL) || Mobile.verifyElementExist(findTestObject('TransferenciaTerceroBeneficiary/Beneficiario de producto Title'), 10, FailureHandling.OPTIONAL) || Mobile.verifyElementExist(findTestObject('EnvioTuEfectivo/Beneficiarios de TuEfectivo Heading'), 01, FailureHandling.OPTIONAL) || Mobile.verifyElementExist(findTestObject('ImpuestosYServicios/Telecomunicaciones'),02,FailureHandling.OPTIONAL) || Mobile.verifyElementExist(findTestObject('MultipleTransactions/Beneficiario de servicios Title'),1,FailureHandling.OPTIONAL)) {
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			try {
				Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaDestino+"' or . = '"+CtaDestino+"')]"))
				if(Element.displayed.TRUE) {
					Element.click()

					if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Destino'), 5, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('ImpuestosYservicios/Proveedor - Title'), 5, FailureHandling.OPTIONAL ) || Mobile.waitForElementPresent(findTestObject('Servicios/Servicio'), 5, FailureHandling.OPTIONAL )) {
						KeywordUtil.markPassed("Destino Account Selected Sucessfuly...!!!")
						GlobalVariable.DestinoAccountStatus = "Available"
						if(Mobile.verifyElementVisible(findTestObject('GetBalances/GetDestinoBalance'), 1, FailureHandling.OPTIONAL)) {
							GlobalVariable.destino_before_app = Mobile.getText(findTestObject('GetBalances/GetDestinoBalance'), 3, FailureHandling.OPTIONAL)
						} else {
							GlobalVariable.destino_before_app = Mobile.getText(findTestObject('Object Repository/GetBalances/GetDestinoBal - Avance de efectivo'), 3, FailureHandling.OPTIONAL)
						}
						String CurrencyDestino = GetCurrency(GlobalVariable.destino_before_app)
						if(CurrencyDestino == null) {
							GlobalVariable.CurrencyTypeDestino = "No Currency Info"
						}else {
							GlobalVariable.CurrencyTypeDestino = CurrencyDestino
						}
						String DestinoBalanceBefore = FormatBalance(GlobalVariable.destino_before_app)
						if(DestinoBalanceBefore == null) {
							GlobalVariable.destino_before_app = "No Info For Acct " + CtaDestino
						}else {
							GlobalVariable.destino_before_app = DestinoBalanceBefore
						}
					} else {
						GlobalVariable.destino_before_app = "SelectionFailed"
						GlobalVariable.OrigenAccountStatus = "NotAvailable"
						if(Mobile.waitForElementPresent(findTestObject('PaymentMessages/Por favor complete el campo transaccin no vlida'), 2, FailureHandling.OPTIONAL)){
							println "Error Occured - Por favor complete el campo transaccin no vlida..!!"
						} else if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/No se permiten transacciones en monedas distintas'), 1, FailureHandling.OPTIONAL)) {
							println "Error Occured - No se permiten transacciones en monedas distintas..!!"
						} else {
							//Screenshot()
							//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
							println "CtaDestino Account Not Found " + CtaDestino
							KeywordUtil.markPassed('Destino Account Not Selected...!!!')
						}
					}
				} else {
					GlobalVariable.destino_before_app = "CtaDestino Account Not Found " + CtaDestino
					GlobalVariable.DestinoAccountStatus = "NotAvailable"
					//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
					println "CtaDestino Account Not Found " + CtaDestino
					KeywordUtil.markFailed('CtaDestino Account Not Found ' + CtaDestino)
				}
			} catch(Exception e) {
				GlobalVariable.destino_before_app = "CtaDestino Account Not Found " + CtaDestino
				GlobalVariable.DestinoAccountStatus = "NotAvailable"
				GlobalVariable.CaseStatusMessage += "\n *** CtaDestino Account Not Found " + CtaDestino +" *** \n"
				println "CtaDestino Account Not Found " + CtaDestino
				//e.printStackTrace()
				KeywordUtil.markFailed('CtaDestino Account Not Found ' + CtaDestino)
			}
		} else {
			GlobalVariable.destino_before_app = "CtaDestino Account Not Found " + CtaDestino
			GlobalVariable.DestinoAccountStatus = "NotAvailable"
			//Screenshot()
			//KeywordUtil.markPassed("Destino Account Not Available - Redirected To Test Case...!!!")
			//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
			//Mobile.tap(findTestObject('Buttons/ACEPTAR'), 1,FailureHandling.OPTIONAL)
			KeywordUtil.markFailed('CtaDestino Account Not Found ' + CtaDestino)
		}
	}

	@Keyword
	def OrigenAccount_AfterBalance(String CtaOrigen) {
		Refresh()
		if(CtaOrigen=='') {
			GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
		} else {
			try {
				Mobile.scrollToText(CtaOrigen, FailureHandling.OPTIONAL)
				AppiumDriver<?> driver = MobileDriverFactory.getDriver()
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaOrigen+"' or . = '"+CtaOrigen+"')]"))
				Element.click()
				Mobile.delay(10)
				try {
					if (Mobile.waitForElementPresent(findTestObject('GetBalances/TUBNCO'), 25, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('GetBalances/ArrowButton'), 05, FailureHandling.OPTIONAL)){
						GlobalVariable.origen_after_app = Mobile.getText(findTestObject('GetBalances/Get-Balance'), 3, FailureHandling.OPTIONAL)
						String OrigenBalanceAfter = FormatBalance(GlobalVariable.origen_after_app)
						GlobalVariable.origen_after_app = OrigenBalanceAfter
					} else {
						println "Account Is Not Available : " + CtaOrigen
						GlobalVariable.origen_after_app = "Account Is Not Available : " + CtaOrigen
					}
				} catch(Exception e) {
					GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
					//e.printStackTrace()
				}
			}catch(Exception e) {
				GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
				//e.printStackTrace()
			}
		}
	}

	@Keyword
	def OrigenCredimas_AfterBalance(String CtaOrigen) {
		Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
		Refresh()
		if(CtaOrigen=='') {
			GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
		} else {
			try {
				Mobile.scrollToText(CtaOrigen, FailureHandling.OPTIONAL)
				AppiumDriver<?> driver = MobileDriverFactory.getDriver()
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaOrigen+"' or . = '"+CtaOrigen+"')]"))
				Element.click()
				Mobile.delay(10)
				try {
					if (Mobile.waitForElementPresent(findTestObject('AvanceDeEfectivo/AfterBal-CredimsTitle'), 25, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('GetBalances/ArrowButton'),5, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('GetBalances/TUBNCO'), 3, FailureHandling.OPTIONAL)){
						if (Mobile.waitForElementPresent(findTestObject('AvanceDeEfectivo/AfterBal-CredimsTitle'), 25, FailureHandling.OPTIONAL)) {
							Mobile.tap(findTestObject('AvanceDeEfectivo/AfterBal-CredimsTitle'), 25, FailureHandling.OPTIONAL)
							GlobalVariable.origen_after_app = Mobile.getText(findTestObject('AvanceDeEfectivo/AfterBal-CredimasAmount'), 3, FailureHandling.OPTIONAL)
							String OrigenBalanceAfter = FormatBalance(GlobalVariable.origen_after_app)
							GlobalVariable.origen_after_app = OrigenBalanceAfter
						} else {
							println "Account Is Not Available : " + CtaOrigen
							GlobalVariable.origen_after_app = "Account Is Not Available : " + CtaOrigen
						}
					} else {
						println "Account Is Not Available : " + CtaOrigen
						GlobalVariable.origen_after_app = "Account Is Not Available : " + CtaOrigen
					}
				} catch(Exception e) {
					GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
					//e.printStackTrace()
				}
			}catch(Exception e) {
				GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
				//e.printStackTrace()
			}
		}
	}

	@Keyword
	def DestinoAccount_AfterBalance(String CtaDestino) {
		Refresh()
		if(CtaDestino=='') {
			GlobalVariable.destino_after_app =  "Account Not Available " + CtaDestino
		} else if(GlobalVariable.BenefUnicoDetails == "True") {
			GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
		} else {
			try {
				Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
				AppiumDriver<?> driver = MobileDriverFactory.getDriver()
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaDestino+"' or . = '"+CtaDestino+"')]"))
				Element.click()
				Mobile.delay(10)
				try {
					if (Mobile.waitForElementPresent(findTestObject('GetBalances/TUBNCO'), 25, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('GetBalances/ArrowButton'), 05, FailureHandling.OPTIONAL)){
						GlobalVariable.destino_after_app = Mobile.getText(findTestObject('GetBalances/Get-Balance'), 3, FailureHandling.OPTIONAL)
						String DestinoBalanceAfter = FormatBalance(GlobalVariable.destino_after_app)
						GlobalVariable.destino_after_app = DestinoBalanceAfter
					} else {
						println "Account Is Not Available : " + CtaDestino
						GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
					}
				} catch(Exception e) {
					GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
					//e.printStackTrace()
				}
			} catch(Exception e) {
				GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
				//e.printStackTrace()
			}
		}
	}

	@Keyword
	def DestinoAccount_AfterBalance_MultipleTransaction(String CtaDestino, String CurrType) {
		Refresh()
		try {
			Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaDestino+"' or . = '"+CtaDestino+"')]"))
			Element.click()
			Mobile.delay(10)

			try {
				if (Mobile.waitForElementPresent(findTestObject('GetBalances/TUBNCO'), 25, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('GetBalances/ArrowButton'), 05, FailureHandling.OPTIONAL)){
					if(CurrType.equals("DOP")) {
						Mobile.tap(findTestObject('MultipleTransactions/Pesos Button'), 2, FailureHandling.OPTIONAL)
					} else {
						Mobile.tap(findTestObject('MultipleTransactions/Dolares Button'), 2, FailureHandling.OPTIONAL)
					}
					Mobile.delay(10)
					String bal
					bal = Mobile.getText(findTestObject('GetBalances/Get-Balance'), 3, FailureHandling.OPTIONAL)
					GlobalVariable.destino_after_app = bal.replaceAll('[,]', '')
				} else {
					println "Account Is Not Available : " + CtaDestino
					GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
				}
			} catch(Exception e) {
				GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
				//e.printStackTrace()
			}
		}catch(Exception e) {
			GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
			//e.printStackTrace()
		}
	}

	@Keyword
	def FormatBalance(String Balance) {
		try {
			String Balance1 = Balance.replaceAll('[,]', '')
			String Balance2
			if(Balance1.contains("DOP") && Balance1.contains("USD")) {
				Balance2 = Balance1
			} else {
				Balance2 = Balance1.substring(4)
			}
			return Balance2
		} catch(Exception e) {
			KeywordUtil.logInfo("No Balance is visible maybe destino is beneficiary")
			//e.printStackTrace()
		}
	}

	@Keyword
	def GetCurrency(String Currency) {
		try {
			String Curr
			if(Currency.contains("DOP") && Currency.contains("USD")) {
				Curr  = "DOP/USD"
			} else if(Currency.contains("DOP") || Currency.contains("USD")) {
				Curr = Currency.substring(0,3)
			} else {
				Curr = "NA"
			}
			return Curr
		} catch(Exception e) {
			KeywordUtil.logInfo("Currency Is Not Available")
			//e.printStackTrace()
		}
	}

	@Keyword
	def UpdateMonto(String Amount) {
		try {
			double Balance1 = Double.parseDouble(GlobalVariable.origen_before_db)
			double Balance2 = Double.parseDouble(Amount)
			double UpdatedValue = Balance1+Balance2
			Amount = UpdatedValue
			println "Origen Monto Balance Updated Is : " + Amount
			return Amount
		} catch(Exception e) {
			KeywordUtil.logInfo("Monto Amount Value Is Null")
			//e.printStackTrace()
		}
	}

	@Keyword
	def Refresh() {
		if(Mobile.waitForElementPresent(findTestObject('UserDetails/HamburgerMenu'), 3, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 3,FailureHandling.OPTIONAL)
			if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 80, FailureHandling.OPTIONAL)) {
				println "User Is On DashBoard Screen"
			} else {
				println "TRY AGAIN"
				Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 3,FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('UserDetails/Productos'), 3,FailureHandling.OPTIONAL)
			}
		} else {
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 3,FailureHandling.OPTIONAL)
			Mobile.delay(1)
			Mobile.tap(findTestObject('UserDetails/Productos'), 3,FailureHandling.OPTIONAL)
			Mobile.delay(2)
			if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 80, FailureHandling.OPTIONAL)) {
				println "User Is On DashBoard Screen"
			} else {
				println "TRY AGAIN"
				Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 3,FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('UserDetails/Productos'), 3,FailureHandling.OPTIONAL)
				Mobile.delay(2)
			}
		}
	}

	@Keyword
	def continuar() {

		String Value = Mobile.getAttribute(findTestObject('TransferenciaPropia/Continuar'),"enabled", 3, FailureHandling.OPTIONAL)
		println "Value Is " + Value
		if(Value == "true") {
			Mobile.tap(findTestObject('TransferenciaPropia/Continuar'), 3, FailureHandling.OPTIONAL)
		} else if(Value == "null")  {
			println "Continuar Button Not Available For Test Case...!!!"
		}
	}

	//Tarjeta monto selector
	@Keyword
	def TarjetaMonto(String Selector) {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		String elementSelector = Selector

		try {
			Mobile.scrollToText(elementSelector, FailureHandling.CONTINUE_ON_FAILURE)
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+elementSelector+"' or . = '"+elementSelector+"')]"))
			if(Element.displayed.TRUE) {
				Element.click()
				if(elementSelector == "DOP" || elementSelector == "USD") {
					KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
				} else {
					if (Mobile.waitForElementPresent(findTestObject('TransferenciaPropia/Origen'), 5, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('Object Repository/Buttons/Confirmar'), 5, FailureHandling.OPTIONAL)){
						//Screenshot()
						KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
					} else {
						//Screenshot()
						KeywordUtil.markFailed(Selector+' Not Selected...!!!')
					}
				}
			} else {
				//Screenshot()
				KeywordUtil.markWarning("Given monto details is not found please check!!!")
			}
		} catch(Exception e) {
			//Screenshot()
			if(elementSelector.equals("Cuota mensual credimás")) {
				//Screenshot()
				GlobalVariable.CaseStatusMessage += "Cuota mensual credimás not found!!!"
				GlobalVariable.MultiDestinoValue = ('\n'+
						'\n'+
						'------------------------ : DETAILED REPORT : -----------------------' +
						'\n'+
						'\n'+
						'APK Bit : ' + GlobalVariable.APK_bit +
						'\n'+
						'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
						'\n'+
						GlobalVariable.CaseStatusMessage+
						'\n'+
						'-------------------------------------------------------------------------')
				KeywordUtil.markFailedAndStop("Cuota mensual credimás not found!!!")
			} else {
				KeywordUtil.markWarning("Given monto details is not found please check!!!")
			}
		}
	}

	//pago unico helper
	@Keyword
	def PagoUnicoHelper(String Selector) {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		String elementSelector = Selector

		try {
			Mobile.scrollToText(elementSelector, FailureHandling.CONTINUE_ON_FAILURE)
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+elementSelector+"' or . = '"+elementSelector+"')]"))
			if(Element.displayed.TRUE) {
				Element.click()
				if (Mobile.waitForElementPresent(findTestObject('Object Repository/Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
				|| Mobile.waitForElementPresent(findTestObject('Benef_ProductYServicios/Button - Guardar'), 3, FailureHandling.OPTIONAL)){
					KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
				} else if(Mobile.waitForElementPresent(findTestObject('Object Repository/Buttons/Continuar-Payment'), 5, FailureHandling.OPTIONAL)){
					KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
				}else {
					GlobalVariable.DestinoAccountStatus = "NotAvailable"
					//Screenshot()
					//KeywordUtil.markFailed(Selector+' Not Selected...!!!')
				}
			} else {
				GlobalVariable.DestinoAccountStatus = "NotAvailable"
				//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
				KeywordUtil.markFailed("Given "+elementSelector+" Not Found!!!")
			}
		} catch(Exception e) {
			GlobalVariable.DestinoAccountStatus = "NotAvailable"
			//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
			KeywordUtil.markFailed("Given "+elementSelector+" Not Found!!!")
		}
	}

	//swipe up
	@Keyword
	public void swipeUP() {
		try {
			int device_Height = Mobile.getDeviceHeight()
			int device_Width = Mobile.getDeviceWidth()
			int startX = device_Width / 2
			int endX = startX
			int startY = device_Height * 0.30
			int endY = device_Height * 0.70
			Mobile.swipe(startX, endY, endX, startY)
		}catch (Exception e) {
			KeywordUtil.logInfo("Can't perform swipe Popup windown maybe opened...!!!")
		}
	}

	//swipe down
	@Keyword
	public void swipeDown() {
		try {
			int device_Height = Mobile.getDeviceHeight()
			int device_Width = Mobile.getDeviceWidth()
			int startX = device_Width / 2
			int endX = startX
			int startY = device_Height * 0.30
			int endY = device_Height * 0.70
			Mobile.swipe(startX, startY, endX, endY)
		}catch (Exception e) {
			KeywordUtil.logInfo("Can't perform swipe Popup windown maybe opened...!!!")
		}
	}

	@Keyword
	def Screenshot() {
		Mobile.delay(1)
		String img = Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
		File original = new File(img)
		File updated = new File(img)
		Mobile.delay(1)
		Resize(original,updated,350,520,"png")
		// 260,430
		// 350,520
	}

	def Resize(File originalImage, File resizedImage, int width, int height, String format) {
		try {
			BufferedImage original = ImageIO.read(originalImage);
			BufferedImage resized = new BufferedImage(width, height, original.getType());
			Graphics2D g2 = resized.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			//g2.setTransform(AffineTransform.getScaleInstance(4, 4));
			g2.drawImage(original, 0, 0, width, height,null)
			g2.dispose()
			ImageIO.write(resized, format, resizedImage);
			Mobile.delay(1)
		}catch(IOException ex) {
			//ex.printStackTrace();
		}
	}


	@Keyword
	def DestinoBeforeAppMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.destino_before_app0 = GlobalVariable.destino_before_app
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.destino_before_app1 = GlobalVariable.destino_before_app
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.destino_before_app2 = GlobalVariable.destino_before_app
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.destino_before_app3 = GlobalVariable.destino_before_app
		} else if(count == 4 && DestinoCount!=count) {
			GlobalVariable.destino_before_app4 = GlobalVariable.destino_before_app
		} else if(count == 5 && DestinoCount!=count) {
			GlobalVariable.destino_before_app5 = GlobalVariable.destino_before_app
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoBeforeDBMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.destino_before_db0 = GlobalVariable.destino_before_db
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.destino_before_db1 = GlobalVariable.destino_before_db
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.destino_before_db2 = GlobalVariable.destino_before_db
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.destino_before_db3 = GlobalVariable.destino_before_db
		} else if(count == 4 && DestinoCount!=count) {
			GlobalVariable.destino_before_db4 = GlobalVariable.destino_before_db
		} else if(count == 5 && DestinoCount!=count) {
			GlobalVariable.destino_before_db5 = GlobalVariable.destino_before_db
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoAfterAppMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.destino_after_app0 = GlobalVariable.destino_after_app
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.destino_after_app1 = GlobalVariable.destino_after_app
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.destino_after_app2 = GlobalVariable.destino_after_app
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.destino_after_app3 = GlobalVariable.destino_after_app
		} else if(count == 4 && DestinoCount!=count) {
			GlobalVariable.destino_after_app4 = GlobalVariable.destino_after_app
		} else if(count == 5 && DestinoCount!=count) {
			GlobalVariable.destino_after_app5 = GlobalVariable.destino_after_app
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoAfterDBMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.destino_after_db0 = GlobalVariable.destino_after_db
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.destino_after_db1 = GlobalVariable.destino_after_db
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.destino_after_db2 = GlobalVariable.destino_after_db
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.destino_after_db3 = GlobalVariable.destino_after_db
		} else if(count == 4 && DestinoCount!=count) {
			GlobalVariable.destino_after_db4 = GlobalVariable.destino_after_db
		} else if(count == 5 && DestinoCount!=count) {
			GlobalVariable.destino_after_db5 = GlobalVariable.destino_after_db
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}


	@Keyword
	def ImpuestosMap (int count, int DestinoCount, double tax) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta1 = tax
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta2 = tax
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta3 = tax
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta4 = tax
		} else if(count == 4 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta5 = tax
		} else if(count == 5 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta6 = tax
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoCurrMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino1 = GlobalVariable.CurrencyTypeDestino
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino2 = GlobalVariable.CurrencyTypeDestino
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino3 = GlobalVariable.CurrencyTypeDestino
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino4 = GlobalVariable.CurrencyTypeDestino
		} else if(count == 4 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino5 = GlobalVariable.CurrencyTypeDestino
		} else if(count == 5 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino6 = GlobalVariable.CurrencyTypeDestino
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def TaxAfterTransactionMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after1 = GlobalVariable.Tax_Destinobalance_after
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after2 = GlobalVariable.Tax_Destinobalance_after
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after3 = GlobalVariable.Tax_Destinobalance_after
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after4 = GlobalVariable.Tax_Destinobalance_after
		} else if(count == 4 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after5 = GlobalVariable.Tax_Destinobalance_after
		} else if(count == 5 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after6 = GlobalVariable.Tax_Destinobalance_after
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoPagoUnicoRecarga(String ImpuestosyServicio, String destino){
		GlobalVariable.destino_before_app = "Destino is PagoUnico - No Balance is visible"
		GlobalVariable.CurrencyTypeDestino = "No Currency Info"
		if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicios/Recarga - PagoUnico'), 10, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('ImpuestosYServicios/Recarga - PagoUnico'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicios/Impuestos y servicios'), 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('ImpuestosYServicios/Impuestos y servicios'), 3, FailureHandling.CONTINUE_ON_FAILURE)
				Mobile.delay(1)
				PagoUnicoHelper(ImpuestosyServicio)
				if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicios/Digita el cdigo de referencia (1)'), 2, FailureHandling.OPTIONAL)) {
					Mobile.setText(findTestObject('ImpuestosYServicios/Digita el cdigo de referencia (1)'),destino, 3, FailureHandling.CONTINUE_ON_FAILURE)
					HideKeyboard()
					Mobile.setText(findTestObject('ImpuestosYServicios/Digita un alias para el servicio (1)'),ImpuestosyServicio, 3, FailureHandling.CONTINUE_ON_FAILURE)
					HideKeyboard()
					Mobile.tap(findTestObject('Object Repository/Buttons/Confirmar'), 3, FailureHandling.OPTIONAL)
				}
			}
		}
		else{
			Screenshot()
			KeywordUtil.markFailed("Terminating Test Case Account not loaded!!!")
		}
	}

	@Keyword
	def DestinoAlticeRecarga(String destino){
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccount'), [('CtaDestino') : destino], FailureHandling.CONTINUE_ON_FAILURE)
	}

	@Keyword
	def GetCommisones() {
		if (Mobile.waitForElementPresent(findTestObject('ImpuestosValue/Cargos por comisiones'), 5, FailureHandling.OPTIONAL)) {

			if(Mobile.waitForElementPresent(findTestObject('ImpuestosValue/GetCommissionValue'), 5, FailureHandling.OPTIONAL)) {
				GlobalVariable.CommissionAmount = Mobile.getText(findTestObject('ImpuestosValue/GetCommissionValue'), 1, FailureHandling.OPTIONAL)

			} else if(Mobile.waitForElementPresent(findTestObject('ImpuestosValue/GetCommissionValue1'), 5, FailureHandling.OPTIONAL)) {
				GlobalVariable.CommissionAmount = Mobile.getText(findTestObject('ImpuestosValue/GetCommissionValue1'), 1, FailureHandling.OPTIONAL)
			}

			WebUI.comment('### - COMMISSION AMOUNT ### : ' + GlobalVariable.CommissionAmount)

			double CommissionAmount = Double.parseDouble(GlobalVariable.CommissionAmount)

			GlobalVariable.CommissionAmount = CommissionAmount
			println('### - COMMISSION AMOUNT ### : ' + GlobalVariable.CommissionAmount)
		} else {
			GlobalVariable.CommissionAmount = '0.0'

			double CommissionAmount = Double.parseDouble(GlobalVariable.CommissionAmount)

			Mobile.delay(0.5)

			GlobalVariable.CommissionAmount = CommissionAmount
			println('### - COMMISSION AMOUNT ### : ' + GlobalVariable.CommissionAmount)
			WebUI.comment('### - COMMISSION AMOUNT ### : ' + GlobalVariable.CommissionAmount)
		}
	}

	@Keyword
	def GetImpuesto() {
		if (Mobile.waitForElementPresent(findTestObject('Impuesta/Impuesto'), 3, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('Impuesta/Impuesto-Title'), 2, FailureHandling.OPTIONAL)) {

			double monto = Double.parseDouble(GlobalVariable.MontoDecimal)
			double largeMonto = Double.parseDouble(GlobalVariable.LargeMonto)

			if(monto > largeMonto) {
				double taxRate = 0.0015
				double correctTax = Math.round(largeMonto * taxRate)
				String Tax = String.valueOf(correctTax)
				GlobalVariable.GetImpuesta = Tax
				double correctTaxUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
				GlobalVariable.GetImpuesta = correctTaxUpdated
				WebUI.comment('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
				println('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)

			} else {
				String Impuesta1 = ''
				String Impuesta2 = ''
				String Impuesta3 = ''
				String Impuesta4 = ''
				String Impuesta5 = ''

				if(Mobile.verifyElementVisible(findTestObject('ImpuestosValue/GetTaxAmount'), 1, FailureHandling.OPTIONAL)) {
					Impuesta1 = Mobile.getText(findTestObject('ImpuestosValue/GetTaxAmount'), 1, FailureHandling.OPTIONAL)
				}

				if(Mobile.verifyElementVisible(findTestObject('ImpuestosValue/GetTaxAmount1'), 1, FailureHandling.OPTIONAL)) {
					Impuesta2 = Mobile.getText(findTestObject('ImpuestosValue/GetTaxAmount1'), 1, FailureHandling.OPTIONAL)
				}

				if(Mobile.verifyElementVisible(findTestObject('ImpuestosValue/GetTaxAmount2'), 1, FailureHandling.OPTIONAL)) {
					Impuesta3 = Mobile.getText(findTestObject('ImpuestosValue/GetTaxAmount2'), 1, FailureHandling.OPTIONAL)
				}

				if(Mobile.verifyElementVisible(findTestObject('Servicios/Impuestos 4'), 1, FailureHandling.OPTIONAL)) {
					Impuesta4 = Mobile.getText(findTestObject('Servicios/Impuestos 4'), 1, FailureHandling.OPTIONAL)
				}

				if(Mobile.verifyElementVisible(findTestObject('Servicios/GetTaxAmount5'), 1, FailureHandling.OPTIONAL)) {
					Impuesta5 = Mobile.getText(findTestObject('Servicios/GetTaxAmount5'), 1, FailureHandling.OPTIONAL)
				}

				if (Impuesta1.contains('DOP 0') || Impuesta1.contains('USD 0')) {
					GlobalVariable.GetImpuesta = Impuesta1
					WebUI.comment('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					String Impuesto = GlobalVariable.GetImpuesta
					GlobalVariable.GetImpuesta = Impuesto.substring(4)
					double ImpuestoUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
					GlobalVariable.GetImpuesta = ImpuestoUpdated
					WebUI.comment('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)

				} else if (Impuesta2.contains('DOP 0') || Impuesta2.contains('USD 0') || Impuesta2.contains('DOP')){
					GlobalVariable.GetImpuesta = Impuesta2
					WebUI.comment('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					String Impuesto = GlobalVariable.GetImpuesta
					GlobalVariable.GetImpuesta = Impuesto.substring(4)
					double ImpuestoUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
					GlobalVariable.GetImpuesta = ImpuestoUpdated
					WebUI.comment('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)

				} else if (Impuesta3.contains('DOP 0') || Impuesta3.contains('USD 0')){
					GlobalVariable.GetImpuesta = Impuesta3
					WebUI.comment('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					String Impuesto = GlobalVariable.GetImpuesta
					GlobalVariable.GetImpuesta = Impuesto.substring(4)
					double ImpuestoUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
					GlobalVariable.GetImpuesta = ImpuestoUpdated
					WebUI.comment('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)

				} else if (Impuesta4.contains('DOP ') || Impuesta4.contains('USD ')){
					GlobalVariable.GetImpuesta = Impuesta4
					WebUI.comment('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					String Impuesto = GlobalVariable.GetImpuesta
					GlobalVariable.GetImpuesta = Impuesto.substring(4)
					double ImpuestoUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
					GlobalVariable.GetImpuesta = ImpuestoUpdated
					WebUI.comment('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)

				} else if (Impuesta5.contains('DOP 0') || Impuesta5.contains('USD 0') || Impuesta5.contains('DOP')){
					GlobalVariable.GetImpuesta = Impuesta5
					WebUI.comment('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					String Impuesto = GlobalVariable.GetImpuesta
					GlobalVariable.GetImpuesta = Impuesto.substring(4)
					double ImpuestoUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
					GlobalVariable.GetImpuesta = ImpuestoUpdated
					WebUI.comment('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
					println('### - UPDATED IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)

				} else {
					GlobalVariable.GetImpuesta = '0.0'
					double ImpuestoUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
					GlobalVariable.GetImpuesta = ImpuestoUpdated
					KeywordUtil.logInfo("*** VALUE FETCHED FAILED FOR IMPUESTOS ***")
				}
			}

		} else {
			GlobalVariable.GetImpuesta = '0.0'
			double ImpuestoUpdated = Double.parseDouble(GlobalVariable.GetImpuesta)
			GlobalVariable.GetImpuesta = ImpuestoUpdated
			WebUI.comment('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
			println('### - IMPUESTA AMOUNT ### : ' + GlobalVariable.GetImpuesta)
		}
	}

	@Keyword
	def HideKeyboard() {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		boolean check = driver.getKeyboard()
		if(check && GlobalVariable.device == "Android Studio") {
			Mobile.hideKeyboard(FailureHandling.OPTIONAL)
		}
	}
}