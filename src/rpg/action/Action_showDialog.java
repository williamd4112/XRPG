package rpg.action;

import rpg.UI.dialog.Dialog;
import rpg.core.GameSystem;
import rpg.core.Scene;
import rpg.event.Event;

public class Action_showDialog extends Action {
	
	final Scene scene;
	final Dialog dialog;
	final Event event;
	
	//@param Scene : where to show
	//@param Dialog : what dialog to show
	//@param Event : who call this action
	public Action_showDialog(Scene scene , Dialog dialog , Event event)
	{
		this.scene = scene;
		this.dialog = dialog;
		this.event = event;
	}
	
	@Override
	public ActionResult execute()
	{
		dialog.reset();
		dialog.requestControl();
		dialog.postDialog();
		
		return ActionResult.DONE;
		
	}
}
