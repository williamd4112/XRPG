package rpg.action;

import java.lang.reflect.Method;

import rpg.UI.UIFactory;
import rpg.UI.dialog.Dialog;
import rpg.action.Action.ActionResult;
import rpg.core.Scene;
import rpg.exception.DefinitionError;

public class ActionFactory {
	
	private static ActionFactory instance;
	private static Class[] paramsTypes;
	
	public static ActionFactory getInstance()
	{
		if(instance == null){
			instance = new ActionFactory();
			paramsTypes = new Class[1];
			paramsTypes[0] = Object[].class;
		}
		return instance;
	}
	
	public Action createAction(String type , Object[] params)
	{
		Action action = null;
		Method method = null;
		
		try {
			if((method = ActionLibrary.class.getMethod(type, paramsTypes)) != null)
				action = new Action(method , params);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return action;
	}
	

}
