package rpg.gameobject;

import rpg.component.Animator;
import rpg.component.CharcterController;
import rpg.component.Component;
import rpg.component.ItemBox;
import rpg.component.PlayerControlPanel;
import rpg.component.RigidBody;
import rpg.component.Sprite;
import rpg.event.Event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	public ItemBox getItemBox()
	{
		return (ItemBox) getComponent("rpg.component.ItemBox");
	}
	public CharcterController getCharacterController()
	{
		return (CharcterController) getComponent("rpg.component.CharcterController");
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
	
	public void render(SpriteBatch batch)
	{
		Sprite sprite = getSprite();
		RigidBody body = getRigidBody();
		Animator animator = getAnimator();
			
		if(sprite != null){
			Texture texture = sprite.getTexture();
			int facing = (body != null) ? body.getFacing()/2 - 1 : 2 ;
			int key = (animator != null) ? animator.getKey() : 0;
			
			//Tile width and height
			int tw = texture.getWidth() / 4;
			int th = texture.getHeight() / 4;
			//if draw region is larger than real tile size (temp 32
			if(tw >= 32 && th >= 32){
				batch.draw(sprite.getTexture()
						, x - (tw - 32)/2, y  , tw, th
						, tw*key , th*facing, tw, th 
						, false, false);
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
