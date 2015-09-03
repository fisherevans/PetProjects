package com.fisherevans.simple_macro.actions;

import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import com.fisherevans.simple_macro.SimpleMacro;
import com.fisherevans.simple_macro.UIHelper;

public class ClipboardAction implements SimpleAction {
	private File _file;
	
	public ClipboardAction(File file) {
		_file = file;
	}
	
	@Override
	public Object run(Map<Object, Object> data) {
		String text = getFileText(_file);
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		SimpleMacro.getInstance().displayMessage("Text Coppied", "Coppied the folling text to your clip board:\n" + text, TrayIcon.MessageType.INFO);
		return null;
	}
	
	private static String getFileText(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			return new String(data, "UTF-8");
		} catch(Exception e) {
			UIHelper.error(e.getMessage(), "Error Copying File to Clipboard");
			return null;
		}
	}

}
