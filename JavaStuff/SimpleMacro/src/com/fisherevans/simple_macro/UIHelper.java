package com.fisherevans.simple_macro;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class UIHelper {
	public static void errorThenExit(Exception e) {
		errorThenExit(e.getMessage(), "Error" , 1);
	}
	
	public static void errorThenExit(String message, String title) {
		errorThenExit(message, title, 1);
	}
	
	public static void errorThenExit(String message, String title, int errorCode) {
		error(message, title);
		System.exit(errorCode);
	}
	
	public static void error(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static Image createImage(String path, String description) {
		try {
			URL imageURL = new File(path).toURI().toURL();
			return (new ImageIcon(imageURL, description)).getImage();
		} catch(Exception e) {
			UIHelper.errorThenExit(e);
			return null;
		}
	}
}
