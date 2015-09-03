package com.fisherevans.simple_macro.actions;

import java.util.HashMap;
import java.util.Map;

public class ActionDirector {
	private Map<Object, SimpleAction> _actions;
	
	public ActionDirector() {
		_actions = new HashMap<Object, SimpleAction>();
	}
	
	public SimpleAction registerAction(Object actionID, SimpleAction action) {
		SimpleAction oldAction = unregisterAction(actionID);
		if(actionID != null && action != null)
			_actions.put(actionID, action);
		return oldAction;
	}
	
	public SimpleAction unregisterAction(Object actionID) {
		return _actions.remove(actionID);
	}
	
	public Object runAction(Object actionID, Map<Object, Object> data) {
		SimpleAction action = _actions.get(actionID);
		if(action == null)
			return null;
		else
			return action.run(data);
	}
}
