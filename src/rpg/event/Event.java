package rpg.event;

import rpg.action.Action;
import rpg.action.Action.ActionResult;
import rpg.action.ActionFactory;
import rpg.core.GameSystem;
import rpg.core.Scene;
import rpg.gameobject.GameObject;
import rpg.item.Item;

import com.badlogic.gdx.utils.Array;

public class Event {
	
	//Parameters format ( don't revise this , all action will use Object[] as the only parameter format )
	private static Class[] paramsFormat;
	
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
	
	//ObjectEvent args
	private GameObject bind;
	
	//SceneEvent args
	private Scene scene;
	private int x;
	private int y;
	
	//ItemEvent args
	private Item item;
	
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
		//set up params format
		if(paramsFormat == null){
			paramsFormat = new Class[1];
			paramsFormat[0] = Object[].class;
		}
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
		//set up params format
		if(paramsFormat == null){
			paramsFormat = new Class[1];
			paramsFormat[0] = Object[].class;
		}
	}
	//Item event (always trigger when use
	public Event(Item item , InvokeType invokeType , CycleType cycleType)
	{
		this.item = item;
		this.invokeType = invokeType;
		this.triggerType = TriggerType.INTERACT;
		this.cycleType = cycleType;
		//set up params format
		if(paramsFormat == null){
			paramsFormat = new Class[1];
			paramsFormat[0] = Object[].class;
		}
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
	
	//Pass method and parameters (method must be in ActionsLibrary) to create a action 
	public void addAction(String type , Object[] params)
	{
		if(actionslist == null)
			actionslist = new Array<Action>();
		
		Action action = ActionFactory.getInstance().createAction(type,params);
		
		if(action != null){
			actionslist.add(action);
			//System.out.println("Event : " + type +  " added");
		}
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
				if(index == actionslist.size){
					return Result.DONE;
				}
				break;
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
