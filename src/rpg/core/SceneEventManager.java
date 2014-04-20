package rpg.core;

import rpg.event.Event;

import com.badlogic.gdx.utils.Array;

public class SceneEventManager {
	//Scene
	final Scene scene;
	//Hold the scene's events including object event and scene event
	//if jam , then the scene will skip user player control and the other game logic except this
	//To be clear , when jam , only process event and graphic
	private Array<Event> eventslist;
	private boolean jam = false;
	private Event jamEvent;
	
	public SceneEventManager (Scene scene)
	{
		this.scene = scene;
		this.eventslist = new Array<Event>();
	}
	
	//Check is jam
	public boolean isJam()
	{
		return jam;
	}
	
	//Add a event to this handler
	public void registerEvent(Event event)
	{
		this.eventslist.add(event);
	}
	
	//remove a event from this 
	public void unregisterEvent(Event event)
	{
		this.eventslist.removeValue(event, true);
	}
	
	//Scene will call this method to process event
	public void processEvent()
	{
		if(jam)
			processJamEvent();
		else
			processParallelEvent();
	}
	
	//Traverse the list , find invoked event
	public void processParallelEvent()
	{
		//Generally handling parallel events
		//One event execute a action and go to next
		for(Event event : eventslist){
			if(event != null){
				if(event.isInvoke()){
					if(event.getInvokeType() == Event.InvokeType.PARALLEL){
						//if return DONE , event handler will do something according to its type (ex : dispose or reset)
						if(event.execute() == Event.Result.DONE)
							afterProcess(event);
					}
					else{
						//switch to jam event process
						jamEvent = event;
						jam = true;
						processJamEvent();
						break;
					}
				}
				
			}
		}
	}
	//When encounter a jam event , event handler will use this method to process events
	public void processJamEvent()
	{
		//jam the event pipeline until execute method return DONE
		if(jamEvent != null)
			if(jamEvent.execute() == Event.Result.DONE)
				afterProcess(jamEvent);
	}
	//After a event done , destroy or reset it
	public void afterProcess(Event event)
	{
		if(event.getCycleType() == Event.CycleType.ONCE){
			if(event == jamEvent)
				jam = false;
			event.dispose();
			unregisterEvent(event);
		}
		else if(event.getCycleType() == Event.CycleType.REPEATABLE){
			if(event == jamEvent)
				jam = false;
			event.reset();
		}
		else if(event.getCycleType() == Event.CycleType.LOOP){
			event.reinvoke();
		}
	}
	
}
