package com.fisherevans.simple_macro.actions;

import java.util.Map;

public class ExitAction implements SimpleAction {
	public Object run(Map<Object, Object> data) {
		System.exit(0);
		return null;
	}
}
