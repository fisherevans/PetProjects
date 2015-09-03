package com.fisherevans.simple_macro.actions;

import java.util.Map;

import com.fisherevans.simple_macro.SimpleMacro;

public class ReloadAction implements SimpleAction {
	public Object run(Map<Object, Object> data) {
		SimpleMacro.getInstance().reloadMenu();
		return null;
	}
}
