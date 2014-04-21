package rpg.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rpg.UI.UIFactory;
import rpg.UI.dialog.Dialog;
import rpg.core.Scene;
import rpg.event.Event;
import rpg.exception.DefinitionError;

public class Action {
	//Action Result : to notify callers the states of the action
	public static enum ActionResult{
		DONE,
		WAIT,
		ABORT
	};
	
	private Method method;
	private Object[] params;
	
	public Action(Method method , Object[] params)
	{
		this.method = method;
		this.params = params;
	}
	
	public ActionResult execute()
	{	
		ActionResult result = ActionResult.DONE;
		
		try {
			 method.invoke(null, (Object)params);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
