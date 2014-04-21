package rpg.action;

import rpg.UI.UIFactory;
import rpg.UI.dialog.Dialog;
import rpg.action.Action.ActionResult;
import rpg.component.RigidBody;
import rpg.core.Scene;
import rpg.exception.DefinitionError;
import rpg.gameobject.GameObject;

public class ActionLibrary {
	
	//@param : Scene - to show on which scene
	//@param : context - what context to show
	public static ActionResult showDialog(Object[] params)
	{
		Scene scene = (Scene) params[0];
		String[] context = (String[]) params[1];
		
		try {
			Dialog dlg = UIFactory.getInstance().createDialog(scene, "Normal", context);
			dlg.requestControl();
			dlg.reset();
			dlg.postDialog();
		} catch (DefinitionError e) {
			e.printStackTrace();
			return ActionResult.ABORT;
		}
		
		return ActionResult.DONE;
	}
	
	//@param : obj - controlled object
	//@param : cmdlist - an int array storing rigidbody commands
	public static ActionResult setMoveRoute(Object[] params)
	{
		GameObject obj = (GameObject) params[0];
		int[] cmdlist = (int[]) params[1];
		
		RigidBody body = (RigidBody) obj.getRigidBody();
		
		//if no rigidbody , return done always without do anything
		if(body == null)
			return ActionResult.DONE;
		
		body.addCommand(cmdlist);
		
		return ActionResult.DONE;
	}
}
