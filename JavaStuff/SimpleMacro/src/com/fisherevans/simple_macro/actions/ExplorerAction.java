package com.fisherevans.simple_macro.actions;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fisherevans.simple_macro.UIHelper;

public class ExplorerAction implements SimpleAction {
	private File _file;
	
	public ExplorerAction(File file) {
		_file = file;
	}
	
	@Override
	public Object run(Map<Object, Object> data) {
		try {
			Desktop.getDesktop().open(_file);
		} catch (IOException e) {
			UIHelper.errorThenExit(e);
		}
		return null;
	}
}
