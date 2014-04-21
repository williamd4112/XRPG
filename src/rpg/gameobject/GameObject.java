package rpg.gameobject;

import rpg.component.Animator;
import rpg.component.Component;
import rpg.component.PlayerControlPanel;
import rpg.component.RigidBody;
import rpg.component.Sprite;
import rpg.event.Event;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Values;

//GameObjec is usually used as a base class
public class GameObject implements Disposable , Comparable{
	
	//Using for identifying something in scene
	final String Name;
	final long ID;
	
	//Virtual coordinate
	protected float x;
	protected float y;
	
	//Event info
	protected Array<Event> events;
	
	//Components
	protected ObjectMap<String , Component> components;
	
	//Accessor
	public String getName()
	{
		return Name;
	}
	public Array<Event> getEventList()
	{
		return this.events;
	}
	public long getID()
	{
		return ID;
	}
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	public Component getComponent(String Name)
	{
		if(components == null)
			return null;
		
		
		if(components.containsKey(Name)){
			//System.out.println("GameObject : " + Name + " was found.");
			return components.get(Name);
		}
		else{
			//System.out.println("GameObject : " + Name + " was not found.");
			return null;
		}
	}
	//For convenience
	public PlayerControlPanel getControlPanel()
	{
		return (PlayerControlPanel) getComponent("rpg.component.PlayerControlPanel");
	}
	public RigidBody getRigidBody()
	{
		return (RigidBody) getComponent("rpg.component.RigidBody");
	}
	public Sprite getSprite()
	{
		return (Sprite) getComponent("rpg.component.Sprite");
	}
	public Animator getAnimator()
	{
		return (Animator) getComponent("rpg.component.Animator");
	}
	//Setters
	public void setX(float x)
	{
		this.x = x;
	}
	public void setY(float y)
	{
		this.y = y;
	}
	public void addEvent(Event event)
	{
		this.events.add(event);
	}
	public void addComponent(Component comp)
	{
		if(components == null)
			components = new ObjectMap<String , Component>();
		
		//if it has the specific component , skip it 
		if(!components.containsKey(comp.getClass().getName())){
			//System.out.println("GameObject : " + comp.getClass().getName() + " added");
			components.put(comp.getClass().getName(), comp);
			comp.install(this);
		}
	}
	
	public GameObject(String Name , long ID , int cState ,int initX , int initY)
	{
		//initialize all variables
		this.Name = Name;
		this.ID = ID;
				
		//initiallize events
		this.events = new Array<Event>();
	}
		
	public void update()
	{
		//Update components
		if(components != null){
			for(Values<Component> it = components.values() ; it.hasNext ;){
				it.next().update();
			}
		}
		
	}
	
	@Override
	public int compareTo(Object obj) {
		GameObject cmp = (GameObject)obj;
		if(this.y < cmp.y)
			return 1;
		else if (this.y > cmp.y)
			return -1;
		else
			return 0;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	
}
