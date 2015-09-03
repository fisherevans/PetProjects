package com.fisherevans.simple_macro;

import java.awt.Component;
import java.awt.Menu;
import java.awt.MenuComponent;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.fisherevans.simple_macro.actions.ActionDirector;
import com.fisherevans.simple_macro.actions.ClipboardAction;
import com.fisherevans.simple_macro.actions.ExitAction;
import com.fisherevans.simple_macro.actions.ReloadAction;
import com.fisherevans.simple_macro.actions.SimpleAction;

public class SimpleMacro implements ActionListener {
	private static SimpleMacro instance = null;
	
	private SystemTray _systemTray;
	private TrayIcon _trayIcon;
	private PopupMenu _popupMenu;
	
	private ActionDirector _actionDirector;
	
	private MenuItem _exitItem, _reloadItem;
	
	private SimpleMacro() {
		try {
			_actionDirector = new ActionDirector();
			_popupMenu = new PopupMenu();
			_systemTray = SystemTray.getSystemTray();
			_trayIcon = new TrayIcon(UIHelper.createImage("res/images/clipboard-arrow-icon-icon.png", "tray icon"), "SimpleMacro", null);
			_trayIcon.setPopupMenu(_popupMenu);
			reloadMenu();
		} catch (Exception e) {
			UIHelper.errorThenExit(e);
		}
	}
	
	private void parseFolder(Menu menu, File folder) {
		List<File> folders = new ArrayList<File>();
		List<File> files = new ArrayList<File>();
		for(File file:folder.listFiles()) {
			if(file.isFile()) files.add(file);
			else folders.add(file);
		}
		Collections.sort(folders);
		Collections.sort(files);
		for(File file:files)
			addAction(menu, file.getName().replaceAll(".txt$", ""), new ClipboardAction(file));
		if(files.size() > 0 && folders.size() > 0)
	        menu.addSeparator();
		for(File file:folders) {
			Menu subMenu = new Menu(file.getName());
			menu.add(subMenu);
			parseFolder(subMenu, file);
		}
	}
	
	public void reloadMenu() {
		try {
			_systemTray.remove(_trayIcon);
			_popupMenu.removeAll();
			parseFolder(_popupMenu, new File("macros"));
		    _popupMenu.addSeparator();
		    _reloadItem = addRootAction("Reload", new ReloadAction());
		    _exitItem = addRootAction("Exit", new ExitAction());
			_systemTray.add(_trayIcon);
		} catch (Exception e) {
			UIHelper.errorThenExit(e);
		}
	}
	
	public MenuItem addRootAction(String text, SimpleAction action) {
		return addAction(_popupMenu, text, action);
	}
	
	public MenuItem addAction(Menu menu, String text, SimpleAction action) {
		MenuItem item = getMenuItem(text); 
		_actionDirector.registerAction(item, action);
		menu.add(item);
		return item;
	}
	
	public MenuItem getMenuItem(String text) {
		MenuItem item = new MenuItem(text);
		item.addActionListener(this);
		return item;
	}
	
	public void displayMessage(String title, String message, TrayIcon.MessageType type) {
		_trayIcon.displayMessage(title, message, type);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_actionDirector.runAction(e.getSource(), null);
	}
	
	public static SimpleMacro getInstance() {
		if(instance == null)
			instance = new SimpleMacro();
		return instance;
	}
}
