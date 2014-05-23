package rpg.item;

import rpg.action.Action;
import rpg.event.Event;

import com.badlogic.gdx.utils.Array;

public class Item {
	
	//NOTE : NOT YET ADD TO XML
	//Item type declare
	public static enum ItemType{
		KEY, //no drop , no accumulation
		ACCUMULATATION, //drop , accumulating
		SEPARATE, //drop , no accumulating
	};
	
	//Name : store in xml
	//Desc : store in xml
	//Icon : store in xml (just a path
	private String Name;
	private String Desc;
	private String Icon;
	private int Count;
	
	//Item ' s function may be performed by action or event (to do complex , you should use event instead action
	//ItemEvent : trigger when use
	//ItemAction : trigger when use
	private Event itemEvent;
	private Action itemAction;
	
	//Load from .xml file
	public Item(String Name , String Icon ,  String Desc)
	{
		this.Name = Name;
		this.Icon = Icon;
		this.Desc = Desc;
		this.Count = 1;
	}
	
	public String getName()
	{
		return Name;
	}
	public String getDesc()
	{
		return Desc;
	}
	public String getIconPath()
	{
		return Icon;
	}
	public int getCount()
	{
		return Count;
	}
	public void setCount(int Count)
	{
		this.Count = Count;
		
	}
	public void addCount()
	{
		this.Count++;
	}
	
	//Use item will create a event to perform action , this event will be edit in Event Editor in the future
	public boolean use()
	{
		//Check action:
		if(itemAction != null)
			itemAction.execute();
		
		//Check event:
		if(itemEvent != null)
			itemEvent.execute();

		return false;
	}
}
