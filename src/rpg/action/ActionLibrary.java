package rpg.action;

import rpg.UI.UIFactory;
import rpg.UI.dialog.Dialog;
import rpg.UI.menu.Menu;
import rpg.UI.menu.Menu_Pause;
import rpg.action.Action.ActionResult;
import rpg.component.CharcterController;
import rpg.component.RigidBody;
import rpg.core.GameSystem;
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
			dlg.post();
		} catch (DefinitionError e) {
			e.printStackTrace();
			return ActionResult.ABORT;
		}
		
		return ActionResult.DONE;
	}
	
	//@param : scene - where to show
	public static ActionResult showMenu(Object[] params)
	{
		String type = (String) params[0];
		
		try {
			Menu menu = UIFactory.getInstance().createMenu(type);
			menu.post();
			return ActionResult.DONE;
		} catch (DefinitionError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ActionResult.ABORT;
		}
		
	}
	
	//@param : obj - controlled object
	//@param : cmdlist - an int array storing charactercontroller's commands
	public static ActionResult setMoveRoute(Object[] params)
	{
		GameObject obj = (GameObject) params[0];
		int[] cmdlist = (int[]) params[1];
		
		CharcterController controller = obj.getCharacterController();
		
		//if no rigidbody , return done always without do anything
		if(controller == null){
			//System.err.println("ActionLib : command added failed");
			return ActionResult.DONE;
		}
		//System.out.println("ActionLib : command added");
		controller.addCommand(cmdlist);
		
		return ActionResult.DONE;
	}
	
	//@param : obj - wait until this object's command list is empty
	public static ActionResult waitUntilMoveDone(Object[] params)
	{
		GameObject obj = (GameObject) params[0];
		CharcterController controller = obj.getCharacterController();
	
		if(controller.isEmptyCommandList())
			return ActionResult.DONE;
		else
			return ActionResult.WAIT;
	}
	
	//@param : obj - wait until this object's command list is empty
	public static ActionResult waitUntilMoveDoneJam(Object[] params)
	{
		GameObject obj = (GameObject) params[0];
		CharcterController controller = obj.getCharacterController();
	
		if(controller.isEmptyCommandList()){
			GameSystem.getInstance().resume();
			return ActionResult.DONE;
		}
		else{
			GameSystem.getInstance().stop();
			controller.update();
			return ActionResult.WAIT;
		}
	}
	

}
