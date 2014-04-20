package rpg.event;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;

import rpg.action.Action;
import rpg.action.Action.ActionResult;
import rpg.core.GameSystem;
import rpg.core.Scene;
import rpg.gameobject.GameObject;

public class Event {
	
	//Type enum
	
	//default : PARALLEL
	public static enum InvokeType{
		PARALLEL, // one frame , one action
		JAM //stuck the traverse in event handler until its all action be done
	};
	//default : INTERACT
	public static enum TriggerType{
		INTERACT, // Press "Enter" to interact , invoked by game object
		AUTO, // Invoke without reason
		ENCOUNTER // Contact to invoke 
	};
	//default : ONCE
	public static enum CycleType{
		ONCE, //Dispose after all action done
		REPEATABLE, //put the index back to 0 and reset invoke to false for next execute
		LOOP //put the index to 0 , no reset invoke state
	};
	
	//DONE : return to handler to notify handler to do after process on this event
	//DOING : return to handler to notify handler this event was not yet done
	public static enum Result{
		DONE,
		DOING,
	}
	
	//judge if invoke
	private boolean invoke = false;
	
	//a list of actions which be executed when trigger
	private Array<Action> actionslist;
	private int index = 0;
	
	//if choose binding object method to create event
	private GameObject bind;
	
	//binding coordinate
	private Scene scene;
	private int x;
	private int y;
	
	//Type
	private InvokeType invokeType;
	private TriggerType triggerType;
	private CycleType cycleType;

	//Object Event
	public Event(GameObject bind , InvokeType invokeType , TriggerType triggerType , CycleType cycleType)
	{
		//set up invoke source
		this.bind = bind;
		this.bind.addEvent(this);
		//set up type
		this.invokeType = invokeType;
		this.triggerType = triggerType;
		this.cycleType = cycleType;
	}
	//Scene Event
	public Event(Scene scene , int x , int y , InvokeType invokeType , TriggerType triggerType , CycleType cycleType)
	{
		//set up invoke source
		this.scene = scene;
		this.x = x;
		this.y = y;
		
		//set up type
		this.invokeType = invokeType;
		this.triggerType = triggerType;
		this.cycleType = cycleType;
	}
	//eventhandler will use this method to check is invoke
	public boolean isInvoke()
	{
		return invoke;
	}
	
	public InvokeType getInvokeType()
	{
		return this.invokeType;
	}
	
	public TriggerType getTriggerType()
	{
		return triggerType;
	}
	
	public CycleType getCycleType()
	{
		return cycleType;
	}
	
	public void addAction(Action action)
	{
		//Lazy instantiate
		if(this.actionslist == null)
			this.actionslist = new Array<Action>();
		this.actionslist.add(action);
	}
	
	//Set invoke true , in that way , EventHandler will process this event when it check
	public void invoke()
	{
		invoke = true;
	}
	
	public Result execute()
	{
		invoke = true;
		
		if(GameSystem.getInstance().getState() != GameSystem.GameState.STOP){
			
			//get actions execute result
			ActionResult result = actionslist.get(index).execute();
			
			//according to result , judge if action is done 
			switch(result){
			case DONE:
				index++;
				if(index == actionslist.size)
					return Result.DONE;
			case WAIT:
				return Result.DOING;
			case ABORT:
				return Result.DONE;
			}
		}
		
		return Result.DOING;
	}
	//Reset to do next invoke
	public void reset()
	{
		index = 0;
		invoke = false;
	}
	//Reset to do next action (Note : loop type doesn't need to invoke again
	public void reinvoke()
	{
		index = 0;
	}
	//Destroy the event
	public void dispose()
	{
		actionslist.clear();
		bind = null;
	}
}
