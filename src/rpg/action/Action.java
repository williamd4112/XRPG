package rpg.action;

public class Action {
	
	public static enum ActionResult{
		DONE,
		WAIT,
		ABORT
	};
	
	public ActionResult execute(){
		return ActionResult.DONE;
		
	}
}
