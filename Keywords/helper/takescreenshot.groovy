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
import sun.misc.BASE64Decoder;
import java.io.ByteArrayInputStream;

public class takescreenshot {

	@Keyword
	def Screenshot5() {
		String image = Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
		def img = ImageIO.read(new File(image))
		int width = img.getWidth()
		int height = img.getHeight()
		int newWidth = width / 2
		int newHeight = height / 2
		def newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)
		ImageIO.write(newImg, "png", new File(image))
	}

	@Keyword
	def ReduceImageResolution() {

		BufferedImage img = null;
		String f = Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)

		try{
			f = new File(f);
			img = ImageIO.read(f);
		} catch(IOException e){
			System.out.println(e);
		}

		int width = img.getWidth();
		int height = img.getHeight();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(img, 0, 0, width, height, null);

		BufferedImage bi = ImageIO.read(new File("image.jpg"));
		width = bi.getWidth();
		height = bi.getHeight();
		if(width>height){
			width = width/2;
		}
		else{
			height = height/2;
		}
		image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.setColor(Color.RED);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		ImageIO.write(tag, "JPEG", new File(f));
	}

	@Keyword
	def Screenshot3() {
		String img = Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
		File original = new File(img)
		File updated = new File(img)
		Resize(original,updated,240,427,"png")
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
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	@Keyword
	def Screenshot1() {

		String img = Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
		ImageIcon getimage = new ImageIcon(img);
		Image newimage = getimage.getImage();

		Image modifiedimage = newimage.getScaledInstance(150,300,java.awt.Image.SCALE_SMOOTH)
		getimage = new ImageIcon(modifiedimage)
	}

	@Keyword
	def Screenshot2() {
		String Image = Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
		println "Image Is " + Image
		String inImg = Image
		String outImg = Image
		try {
			int width = 350;
			int height = 500;
			ResizeImg(inImg, outImg, width, height);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	def ResizeImg(String inImg, String outImg, int w, int h) {
		try {
			File f = new File(inImg);
			BufferedImage inputImage = ImageIO.read(f);
			BufferedImage img = new BufferedImage(w, h, inputImage.getType());
			Graphics2D g = img.createGraphics();
			g.drawImage(inputImage, 0, 0, w, h, null);
			g.dispose();
			String name = outImg.substring(outImg.lastIndexOf(".") + 1);
			ImageIO.write(img, name, new File(outImg));
		} catch(Exception e) {
			e.printStackTrace()
		}
	}
}
