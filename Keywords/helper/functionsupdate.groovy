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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.text.DecimalFormat
import com.ibm.db2.jcc.DB2Driver
import com.opencsv.*;
import java.io.FileWriter.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.persistence.platform.database.DB2Platform
import org.junit.After
import org.eclipse.persistence.platform.database.DB2MainframePlatform
import java.sql.SQLException

public class functionsupdate {

	public String UserID ;
	private static Connection connection ;
	private static Connection connection2 ;
	private static Connection connection3;
	private static Connection connection4;
	private static double balance_origen ;
	private static double balance_despues ;
	private static  double balance_destino ;
	public static final String DATABASE_USER = "user";
	public static final String DATABASE_PASSWORD = "password";
	public static final String DEFERPREPARES = "false"
	private static DecimalFormat df = new DecimalFormat(".00")

	//RICHARD
	//String Device = "6d37165816909caf"

	//RAGHAVENDRA
	//String Device = "ed7c88d20446d901"

	//ALFREDO
	//String Device = ""

	@Keyword
	def MigrateToken() {
		GetUserID()
		try {
			Statement stm = connection4.createStatement()
			ResultSet rs = stm.executeQuery("update TokenAssistedRequest set TokenRequestStatusId = 3 where UserId = "+ UserID +" and TokenRequestStatusId = 1")
			//rs.next()
			//return rs
		} catch (Exception e) {
			//e.printStackTrace()
		}
	}

	def migrateToken() {
		try {
			Statement stm = connection3.createStatement()
			ResultSet rs = stm.executeQuery("update TokenAssistedRequest set TokenRequestStatusId = 3 where UserId  =  (select UserId from Users where UserName = 'AO019625') and TokenRequestStatusId = 1")
		} catch (Exception e) {
			//e.printStackTrace()
		}
	}

	def GetUserID() {
		try {
			Statement stm1 = connection4.createStatement()
			//select * from Users where UserName = 'username'
			ResultSet rs1 = stm1.executeQuery("select * from Users where UserName = '"+ GlobalVariable.Username +"'")
			rs1.next()
			UserID = rs1.getString("UserId")
			println " UserID for user "+ GlobalVariable.Username + " Is : " + UserID

		} catch (Exception e) {
			switch(GlobalVariable.Username) {
				// Set UserID according to the UserName..
				case 'AO019625' : UserID = '1696072' ;
					break;
				case 'XILE15' : UserID = '905' ;
					break;
				case 'HDLEON' : UserID = '1280' ;
					break;
				case 'rayxavier' : UserID = '792868' ;
					break;
				default: UserID = '1696072' ;
			}
		}
	}


	@Keyword
	def connectDB1(String host, String db, String port, String user, String pass){
		String jdbcClassName ="com.ibm.db2.jcc.DB2Driver"
		String conn = "jdbc:db2://"+host+":"+port+"/"+db
		Class.forName(jdbcClassName)

		java.util.Properties connProperties = new java.util.Properties();
		connProperties.put(DATABASE_USER, user);
		connProperties.put(DATABASE_PASSWORD, pass);
		connProperties.put(DEFERPREPARES, 'false');

		if(connection != null && !connection.isClosed()){
			connection.close()
		}
		connection = DriverManager.getConnection(conn, connProperties)
		return connection
	}

	@Keyword
	def connectDB2(String host, String db, String port, String user, String pass){
		String jdbcClassName ="com.microsoft.sqlserver.jdbc.SQLServerDriver"
		String conn2 = "jdbc:oracle:thin:aloyal@"+host+":"+port+"/"+db

		Class.forName(jdbcClassName)
		if(connection2 != null && !connection2.isClosed()){
			connection2.close()
		}
		connection2 = DriverManager.getConnection(conn2, user, pass)
		return connection2
	}

	@Keyword
	def connectDB3(String user,String pass){
		String jdbcClassName ="com.microsoft.sqlserver.jdbc.SQLServerDriver"
		String conn3 = "jdbc:sqlserver://172.22.50.212:1433;databaseName=IBKEmpData;"

		Class.forName(jdbcClassName)
		if(connection3 != null && !connection3.isClosed()){
			connection3.close()
		}
		connection3 = DriverManager.getConnection(conn3, user, pass)
		return connection3
	}

	@Keyword
	def connectDB4(String user,String pass){
		String jdbcClassName ="com.microsoft.sqlserver.jdbc.SQLServerDriver"
		String conn4 = "jdbc:sqlserver://172.22.50.212:1433;databaseName=IBKPerData;"

		Class.forName(jdbcClassName)
		if(connection4 != null && !connection4.isClosed()){
			connection4.close()
		}
		connection4 = DriverManager.getConnection(conn4, user, pass)
		return connection4
	}

	@Keyword
	def ClearAuthorization() {
		try {
			Statement stm = connection3.createStatement()
			ResultSet rs = stm.executeQuery("UPDATE Transactions SET TransactionStatusId = 3 WHERE GroupId = 129 and ProcessedDate > '2022-08-31'")
		}catch (Exception e) {
			//e.printStackTrace()
		}
	}

	@Keyword
	def ConsultaBalance_Origen(String CuentaOrigen) {
		try {
			if(CuentaOrigen == "") {
				println "Origen Account Is Blank In Datapool...!!!"
				GlobalVariable.origen_before_db = "No Account Number"
			}else {
				try {
					Statement stm = connection.createStatement()
					ResultSet rs = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ CuentaOrigen +"')")
					rs.next()
					String balance = rs.getString("DMCBAL")
					double monto = Double.parseDouble(balance)
					GlobalVariable.origen_before_db = monto
					println "Before Payment : Origen Account " +CuentaOrigen+ " DB Balance Is : " + GlobalVariable.origen_before_db
					return rs
				}catch (Exception e) {
					GlobalVariable.origen_before_db = "No Info For Acct " + CuentaOrigen
					println "Before Payment : Origen Account " +CuentaOrigen+ " DB Balance Is : " + GlobalVariable.origen_before_db
				}
			}
		}catch (Exception e) {
			GlobalVariable.origen_before_db = "No Info For Acct " + CuentaOrigen
			//e.printStackTrace()
		}
	}

	@Keyword
	def ConsultaBalance_Destino(String CuentaDestino) {

		try {
			if(CuentaDestino == "") {
				println "Destino Account Is Blank In Datapool...!!!"
				GlobalVariable.destino_before_db = "No Account Number"
			}else {
				try {
					Statement stm = connection.createStatement()
					ResultSet rs = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ CuentaDestino +"')")
					rs.next()
					String balance = rs.getString("DMCBAL")
					double monto = Double.parseDouble(balance)
					GlobalVariable.destino_before_db = monto
					println "Before Payment : Destino Account " +CuentaDestino+ " DB Balance Is : " + GlobalVariable.destino_before_db
					return rs
				}catch (Exception e) {
					GlobalVariable.destino_before_db = "No Info For Acct " + CuentaDestino
					println "Before Payment : Destino Account " +CuentaDestino+ " DB Balance Is : " + GlobalVariable.destino_before_db
				}
			}
		}catch (Exception e) {
			GlobalVariable.destino_before_db = "No Info For Acct " + CuentaDestino
			//e.printStackTrace()
		}
	}

	@Keyword
	def ConsultaBalance_OrigenAfter(String CuentaOrigen) {
		try {
			if(CuentaOrigen == "") {
				println "Origen Account Is Blank In Datapool...!!!"
				GlobalVariable.origen_after_db = "No Account Number"
			}else {
				try {
					Statement stm = connection.createStatement()
					ResultSet rs = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ CuentaOrigen +"')")
					rs.next()
					String balance = rs.getString("DMCBAL")
					double monto = Double.parseDouble(balance)
					GlobalVariable.origen_after_db = monto
					println "After Payment : Origen Account " +CuentaOrigen+ "  DB Balance Is : " + GlobalVariable.origen_after_db
					return rs
				}catch (Exception e) {
					GlobalVariable.origen_after_db = "No Info For Acct " + CuentaOrigen
					println "After Payment : Origen Account " +CuentaOrigen+ "  DB Balance Is : " + GlobalVariable.origen_after_db
				}
			}
		}catch (Exception e) {
			GlobalVariable.origen_after_db = "No Info For Acct " + CuentaOrigen
			//e.printStackTrace()
		}
	}

	@Keyword
	def ConsultaBalance_DestinoAfter(String CuentaDestino) {

		try {
			if(CuentaDestino == "") {
				println "Destino Account Is Blank In Datapool...!!!"
				GlobalVariable.destino_after_db = "No Account Number"
			}else {
				try {
					Statement stm = connection.createStatement()
					ResultSet rs = stm.executeQuery("SELECT dmcbal FROM BDRQCD.TAP00201 WHERE ACCOUNT_NBR = ('"+ CuentaDestino +"')")
					rs.next()
					String balance = rs.getString("DMCBAL")
					double monto = Double.parseDouble(balance)
					GlobalVariable.destino_after_db = monto
					println "After Payment : Destino Account  "+ CuentaDestino+ "  DB Balance Is : " + GlobalVariable.destino_after_db
					return rs
				}catch (Exception e) {
					GlobalVariable.destino_after_db = "No Info For Acct " + CuentaDestino
					println "After Payment : Destino Account  "+ CuentaDestino+ "  DB Balance Is : " + GlobalVariable.destino_after_db
				}
			}
		}catch (Exception e) {
			GlobalVariable.destino_after_db = "No Info For Acct " + CuentaDestino
			//e.printStackTrace()
		}
	}

	@Keyword
	def CalculateTaxUpdate(String CuentaOrigen, CuentaDestino){

		double balance_origen
		double balance_destino
		double balance_after
		double balanceO
		double balanceD

		DecimalFormat df = new DecimalFormat(".00")
		double impuesto = GlobalVariable.GetImpuesta
		double commission = GlobalVariable.CommissionAmount

		if(GlobalVariable.MontoDecimal != '') {
			double monto = Double.parseDouble(GlobalVariable.MontoDecimal)

			if(GlobalVariable.PaymentStatus == "Passed") {
				try {
					String balanceOrigen = GlobalVariable.origen_before_db
					balanceO = Double.parseDouble(balanceOrigen)
					println 'balanceO ' + balanceO

					balance_after =  balanceO - impuesto - monto - commission
					println 'balance_despues1 ' + balance_after
					balance_after = Double.parseDouble(df.format(balance_after).toString())
					println 'balance_despues2 ' + balance_after
					GlobalVariable.Tax_Origenbalance_after = balance_after
				} catch(Exception e) {
					GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_after_db
				}
			} else {
				GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_before_db
			}

			String balanceDestino = GlobalVariable.destino_before_db
			try {
				if(balanceDestino.contains("[a-zA-Z]+") == true && balanceDestino.length() > 2) {
					println "No Info " + CuentaOrigen
					GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
				} else {
					balanceDestino = GlobalVariable.destino_before_db
					balanceD = Double.parseDouble(balanceDestino)
					println 'balanceD ' + balanceD

					//balance_destino = balanceD + monto + impuesto + commission
					balance_destino = balanceD + monto + commission
					println 'balance_destino ' + balance_destino
					balance_destino = Double.parseDouble(df.format(balance_destino).toString())
					GlobalVariable.Tax_Destinobalance_after = balance_destino
				}
			}catch(Exception e) {
				GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
			}
		} else {
			GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_before_db
			GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
		}

	}

	@Keyword
	def CalculateTaxMultipleTransaction(double tax, String amount, def DestinoAfterDB, def DestinoBeforeDB, int count, int accountCount, double totalTax, String totalAmount){

		double balance_destino
		double balance_after
		double balanceO
		double balanceD
		def customfunction = new customfunction()
		DecimalFormat df = new DecimalFormat(".00")
		double impuesto = tax
		double commission = GlobalVariable.CommissionAmount
		double monto = Double.parseDouble(amount)
		double totalmonto = Double.parseDouble(totalAmount)

		if(GlobalVariable.PaymentStatus == "Passed") {
			String balanceOrigen = GlobalVariable.origen_before_db
			balanceO = Double.parseDouble(balanceOrigen)
			println 'balanceO ' + balanceO

			balance_after =  balanceO - totalTax - totalmonto - commission
			println 'balance_despues1 ' + balance_after
			balance_after = Double.parseDouble(df.format(balance_after).toString())
			println 'balance_despues2 ' + balance_after
			GlobalVariable.Tax_Origenbalance_after = balance_after
		} else {
			GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_before_db
		}

		String balanceDestino = DestinoBeforeDB
		try {
			if(balanceDestino.contains("[a-zA-Z]+") == true && balanceDestino.length() > 2) {
				GlobalVariable.Tax_Destinobalance_after = DestinoAfterDB
				customfunction.TaxAfterTransactionMap(count, accountCount)
			}else {
				balanceDestino = DestinoBeforeDB
				balanceD = Double.parseDouble(balanceDestino)
				println 'balanceD ' + balanceD

				//balance_destino = balanceD + monto + impuesto + commission
				balance_destino = balanceD + monto + commission
				println 'balance_destino ' + balance_destino
				GlobalVariable.Tax_Destinobalance_after = balance_destino
				customfunction.TaxAfterTransactionMap(count, accountCount)
			}
		}catch(Exception e) {
			GlobalVariable.Tax_Destinobalance_after = DestinoAfterDB
			customfunction.TaxAfterTransactionMap(count, accountCount)
		}
	}

	@Keyword
	def CalculateTaxUpdate1(String CuentaOrigen, CuentaDestino){

		double balance_origen
		double balance_destino
		double balance_after
		double balanceO
		double balanceD
		//String taxvalue = tax
		//double monto = GlobalVariable.MontoDecimal

		//float impuesto
		DecimalFormat df = new DecimalFormat(".00")
		double impuesto = GlobalVariable.GetImpuesta
		double commission = GlobalVariable.CommissionAmount
		double monto = Double.parseDouble(GlobalVariable.MontoDecimal)


		//String balanceOrigen = GlobalVariable.origen_after_db
		String balanceOrigen = GlobalVariable.origen_before_db
		balanceO = Double.parseDouble(balanceOrigen)
		println 'balanceO ' + balanceO

		//monto = Long.parseLong(amount)
		//println "monto" + monto

		//impuesto = monto * GlobalVariable.GetImpuesta
		balance_after =  balanceO - impuesto - monto - commission
		println 'balance_despues1 ' + balance_after
		balance_after = Double.parseDouble(df.format(balance_after).toString())
		println 'balance_despues2 ' + balance_after
		GlobalVariable.Tax_Origenbalance_after = balance_after

		String balanceDestino = GlobalVariable.destino_before_db
		try {
			if(balanceDestino.contains("[a-zA-Z]+") == true && balanceDestino.length() > 2) {
				println "No Info " + CuentaOrigen
				GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
			}else {
				balanceDestino = GlobalVariable.destino_after_db
				balanceD = Double.parseDouble(balanceDestino)
				println 'balanceD ' + balanceD

				balance_destino = balanceD + monto + impuesto + commission
				println 'balance_destino ' + balance_destino
				GlobalVariable.Tax_Destinobalance_after = balance_destino
			}
		}catch(Exception e) {
			GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
		}

	}

	@Keyword
	def CalculateTax1(String amount,fromaccount,toAccount,tax) {

		double balance_origen
		double balance_destino
		double balance_after
		double balanceO
		double balanceD
		double monto
		float impuesto
		DecimalFormat df = new DecimalFormat(".00")
		impuesto = 0

		String balanceOrigen = GlobalVariable.origen_after_db
		balanceO = Double.parseDouble(balanceOrigen)
		println 'balanceO ' + balanceO

		try {
			String balanceDestino = GlobalVariable.destino_after_db
			balanceD = Double.parseDouble(balanceDestino)
			println 'balanceD ' + balanceD
		}catch(Exception e) {
			//e.printStackTrace()
			GlobalVariable.destino_after_db = "No Information Available"
		}

		try {
			monto = Long.parseLong(amount)
			println "monto" + monto
		}catch(Exception e) {
			monto = Double.parseDouble(amount)
			println "monto" + monto
		}

		if(tax == "TRUE" || tax == "True"|| tax == "true"){
			//impuesto = monto * 0.0015
			impuesto = monto * GlobalVariable.GetImpuesta
			println "impuesto" + impuesto
		}

		balance_after =  balanceO - impuesto - monto
		println 'balance_despues1 ' + balance_after
		balance_after = Double.parseDouble(df.format(balance_after).toString())
		println 'balance_despues2 ' + balance_after
		GlobalVariable.Tax_Origenbalance_after = balance_after

		try {
			balance_destino = balanceD + monto
			println 'balance_destino ' + balance_destino
			GlobalVariable.Tax_Destinobalance_after = balance_destino
		}catch (Exception e){
			GlobalVariable.Tax_Destinobalance_after = "No Balance Fetched"
			//e.printStackTrace()
		}


		//println("Current Balance In The DB: " + balanceO + " Balance Calculated By The function " + balance_despues + " La cuenta esta exenta :"+tax)
		//println("Current Balance Of The Beneficiary In The DB " + balanceD + " Current Balance Of The Beneficiary Calculated By The Function" + balance_destino)

		//if( balanceO == balance_despues && balanceD == balance_destino ){
		//println("PASS")
		//} else
		//println("FAIL")

		//return or
	}

	@Keyword
	def ConsultaBalancePrestamos(String CuentaDestino) {
		try {
			if(CuentaDestino == "") {
				println "Prestamo Account Is Blank In Datapool...!!!"
				GlobalVariable.destino_before_db = "No Account Number"
			}else {
				try {
					Statement stm = connection.createStatement()
					ResultSet rs = stm.executeQuery("select CURRENT_PRINCIPAL_BALANCE from bdrqcd.lnp00301 where note_number = ('"+ CuentaDestino +"')")
					rs.next()
					String balance1 = rs.getString("CURRENT_PRINCIPAL_BALANCE")
					double monto1 = Double.parseDouble(balance1)
					println(monto1)

					rs = stm.executeQuery("select INTEREST_DUE from bdrqcd.lnp00301 where note_number = ('"+ CuentaDestino +"')")
					rs.next()
					String balance2 = rs.getString("INTEREST_DUE")
					double monto2 = Double.parseDouble(balance2)
					println(monto2)

					double monto = monto1 + monto2

					GlobalVariable.destino_before_db = monto
					println "Before Payment : Prestamo Account "+ CuentaDestino+ " DB Balance Is : " + GlobalVariable.destino_before_db
					return rs
				}catch (Exception e) {
					GlobalVariable.destino_before_db = "No Info For Acct " + CuentaDestino
					println "Before Payment : Prestamo Account "+ CuentaDestino+ " DB Balance Is : " + GlobalVariable.destino_before_db
				}
			}
		}catch (Exception e) {
			GlobalVariable.destino_before_db = "No Info For Acct " + CuentaDestino
			//e.printStackTrace()
		}
	}

	@Keyword
	def ConsultaBalancePrestamosAfter(String CuentaDestino) {

		try {
			if(CuentaDestino == "") {
				println "Prestamo Account Is Blank In Datapool...!!!"
				GlobalVariable.destino_after_db = "No Account Number"
			}else {
				try {
					Statement stm = connection.createStatement()
					ResultSet rs = stm.executeQuery("select CURRENT_PRINCIPAL_BALANCE from bdrqcd.lnp00301 where note_number = ('"+ CuentaDestino +"')")
					rs.next()
					String balance1 = rs.getString("CURRENT_PRINCIPAL_BALANCE")
					double monto1 = Double.parseDouble(balance1)
					println(monto1)

					rs = stm.executeQuery("select INTEREST_DUE from bdrqcd.lnp00301 where note_number = ('"+ CuentaDestino +"')")
					rs.next()
					String balance2 = rs.getString("INTEREST_DUE")
					double monto2 = Double.parseDouble(balance2)
					println(monto2)

					double monto = monto1 + monto2

					GlobalVariable.destino_after_db = monto
					println "After Payment : Prestamo Account "+ CuentaDestino+ " DB Balance Is : " + GlobalVariable.destino_after_db
					return rs
				}catch (Exception e) {
					GlobalVariable.destino_after_db = "No Info For Acct " + CuentaDestino
					println "After Payment : Prestamo Account "+ CuentaDestino+ " DB Balance Is : " + GlobalVariable.destino_after_db
				}
			}
		}catch (Exception e) {
			GlobalVariable.destino_after_db = "No Info For Acct " + CuentaDestino
			//e.printStackTrace()
		}
	}

	@Keyword
	def ConsultaBalance_TarjetaBefore(String CuentaDestino, String CurrType) {

		try {
			if(CuentaDestino == "") {
				println "Destino Account Is Blank In Datapool...!!!"
				GlobalVariable.destino_before_db = "No Account Number"
			}else {
				try {
					Statement stm = connection2.createStatement()
					ResultSet rs
					double monto
					if(CurrType.equals("DOP")) {
						//RD balance
						rs = stm.executeQuery("select x.card_nbr, x.cred_limit - (X.stm_balnce + X.debit_adj + X.duty_debit + X.other_fees + X.card_fees + X.Purchases + X.cash_advce + x.cred_adj + x.duty_credt + x.pen_chrg + x.paymt_uncl + x.paymt_clrd - x.contra_bal + x.fees_taxes + x.cash_adfee + x.cred_vouch) DISPONIBLE from acct x where card_nbr = ('"+ CuentaDestino +"') and curr_num = 214")
						rs.next()
						String balance1 = rs.getString("DISPONIBLE")
						monto = Double.parseDouble(balance1)
						println(monto)
					} else if(CurrType.equals("USD")) {
						//US balance
						rs = stm.executeQuery("select x.card_nbr, x.cred_limit - (X.stm_balnce + X.debit_adj + X.duty_debit + X.other_fees + X.card_fees + X.Purchases + X.cash_advce + x.cred_adj + x.duty_credt + x.pen_chrg + x.paymt_uncl + x.paymt_clrd - x.contra_bal + x.fees_taxes + x.cash_adfee + x.cred_vouch) DISPONIBLE from acct x where card_nbr = ('"+ CuentaDestino +"') and curr_num = 840")
						rs.next()
						String balance2 = rs.getString("DISPONIBLE")
						monto = Double.parseDouble(balance2)
						println(monto)
					}

					GlobalVariable.destino_before_db = monto
					return rs
				} catch (Exception e) {
					GlobalVariable.destino_before_db = "No Info For Tarjeta Acct " + CuentaDestino
				}
			}
		} catch (Exception e) {
			GlobalVariable.destino_before_db = "No Info For Tarjeta Acct " + CuentaDestino
			//e.printStackTrace()
		}
	}

	@Keyword
	def ConsultaBalance_TarjetaAfter(String CuentaDestino, String CurrType) {

		try {
			if(CuentaDestino == "") {
				println "Destino Account Is Blank In Datapool...!!!"
				GlobalVariable.destino_after_db = "No Account Number"
			}else {
				try {
					Statement stm = connection2.createStatement()
					ResultSet rs
					double monto
					if(CurrType.equals("DOP")) {
						//RD balance
						rs = stm.executeQuery("select x.card_nbr, x.cred_limit - (X.stm_balnce + X.debit_adj + X.duty_debit + X.other_fees + X.card_fees + X.Purchases + X.cash_advce + x.cred_adj + x.duty_credt + x.pen_chrg + x.paymt_uncl + x.paymt_clrd - x.contra_bal + x.fees_taxes + x.cash_adfee + x.cred_vouch) DISPONIBLE from acct x where card_nbr = ('"+ CuentaDestino +"') and curr_num = 214")
						rs.next()
						String balance1 = rs.getString("DISPONIBLE")
						monto = Double.parseDouble(balance1)
						println(monto)
					} else if(CurrType.equals("USD")) {
						//US balance
						rs = stm.executeQuery("select x.card_nbr, x.cred_limit - (X.stm_balnce + X.debit_adj + X.duty_debit + X.other_fees + X.card_fees + X.Purchases + X.cash_advce + x.cred_adj + x.duty_credt + x.pen_chrg + x.paymt_uncl + x.paymt_clrd - x.contra_bal + x.fees_taxes + x.cash_adfee + x.cred_vouch) DISPONIBLE from acct x where card_nbr = ('"+ CuentaDestino +"') and curr_num = 840")
						rs.next()
						String balance2 = rs.getString("DISPONIBLE")
						monto = Double.parseDouble(balance2)
						println(monto)
					}

					GlobalVariable.destino_after_db = monto
					return rs
				} catch (Exception e) {
					GlobalVariable.destino_after_db = "No Info For Tarjeta Acct " + CuentaDestino
				}
			}
		} catch (Exception e) {
			GlobalVariable.destino_after_db = "No Info For Tarjeta Acct " + CuentaDestino
			//e.printStackTrace()
		}
	}

	@Keyword
	def credimas_DBAfter(String CuentaOrigen, String CurrType) {

		try {
			if(CuentaOrigen == "") {
				println "Origin Account Is Blank In Datapool...!!!"
				GlobalVariable.origen_after_db = "No Account Number"
			}else {
				try {
					Statement stm = connection2.createStatement()
					ResultSet rs
					double monto
					if(CurrType.equals("DOP")) {
						//RD balance
						rs = stm.executeQuery("select card_nbr tarjeta, (mp_limit - (mp_bal + mp_odue + mp_auths + capital + stm_otherf)) DISPONIBLE_CREDIMAS from acct where card_nbr = ('"+ CuentaOrigen +"') and curr_num = 214")
						rs.next()
						String balance1 = rs.getString("DISPONIBLE_CREDIMAS")
						monto = Double.parseDouble(balance1)
						println(monto)
					} else if(CurrType.equals("USD")) {
						//US balance
						rs = stm.executeQuery("select card_nbr tarjeta, (mp_limit - (mp_bal + mp_odue + mp_auths + capital + stm_otherf)) DISPONIBLE_CREDIMAS from acct where card_nbr = ('"+ CuentaOrigen +"') and curr_num = 840")
						rs.next()
						String balance2 = rs.getString("DISPONIBLE_CREDIMAS")
						monto = Double.parseDouble(balance2)
						println(monto)
					}

					GlobalVariable.origen_after_db = monto
					return rs
				} catch (Exception e) {
					GlobalVariable.origen_after_db = "No Info For Tarjeta Acct " + CuentaOrigen
				}
			}
		} catch (Exception e) {
			GlobalVariable.origen_after_db = "No Info For Tarjeta Acct " + CuentaOrigen
			//e.printStackTrace()
		}
	}

	@Keyword
	def credimas_DBBefore(String CuentaOrigen, String CurrType) {

		try {
			if(CuentaOrigen == "") {
				println "Origin Account Is Blank In Datapool...!!!"
				GlobalVariable.origen_before_db = "No Account Number"
			}else {
				try {
					Statement stm = connection2.createStatement()
					ResultSet rs
					double monto
					if(CurrType.equals("DOP")) {
						//RD balance
						rs = stm.executeQuery("select card_nbr tarjeta, (mp_limit - (mp_bal + mp_odue + mp_auths + capital + stm_otherf)) DISPONIBLE_CREDIMAS from acct where card_nbr = ('"+ CuentaOrigen +"') and curr_num = 214")
						rs.next()
						String balance1 = rs.getString("DISPONIBLE_CREDIMAS")
						monto = Double.parseDouble(balance1)
						println(monto)
					} else if(CurrType.equals("USD")) {
						//US balance
						rs = stm.executeQuery("select card_nbr tarjeta, (mp_limit - (mp_bal + mp_odue + mp_auths + capital + stm_otherf)) DISPONIBLE_CREDIMAS from acct where card_nbr = ('"+ CuentaOrigen +"') and curr_num = 840")
						rs.next()
						String balance2 = rs.getString("DISPONIBLE_CREDIMAS")
						monto = Double.parseDouble(balance2)
						println(monto)
					}

					GlobalVariable.origen_before_db = monto
					return rs
				} catch (Exception e) {
					GlobalVariable.origen_before_db = "No Info For Tarjeta Acct " + CuentaOrigen
				}
			}
		} catch (Exception e) {
			GlobalVariable.origen_before_db = "No Info For Tarjeta Acct " + CuentaOrigen
			//e.printStackTrace()
		}
	}
}
