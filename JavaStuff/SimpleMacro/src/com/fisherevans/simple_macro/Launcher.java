package com.fisherevans.simple_macro;

import java.awt.SystemTray;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Launcher {
	private static final String ERROR_TITLE = "Cannot Run SimpleMacro";
	
	private static final String ERROR_MESSAGE = "This system does not support applications that use the System Tray.";
	
	public static void main(String[] args) {
		if(!SystemTray.isSupported())
			UIHelper.errorThenExit(ERROR_MESSAGE, ERROR_TITLE);
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			UIHelper.errorThenExit(e);
		}
		SimpleMacro.getInstance();
	}
}
