package rpg.component;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.IntArray;

import rpg.event.Event;
import rpg.gameobject.GameObject;
import rpg.phys.Line2D;

//Usage : apply to game object making it have a "Instance" in the game world
public class RigidBody extends Rectangle implements Component{
	
	//Binding object
	final GameObject bind;
	
	//Movement info
	private IntArray commandlist;
	private boolean isFixFacing;
	private int direction;
	private int facing;
	private int speed;
	private int destx;
	private int desty;
	
	//Phys info ( only rigidbody can contact each other )
	private RigidBody contactRigidBody;
	private int isBlock = 0;
	
	//Binding Object
	public GameObject getBinding()
	{
		return bind;
	}
	
	//Real position
	public int getRealX()
	{
		return (int)this.x;
	}
	public int getRealY()
	{
		return (int)this.y;
	}
	//Destination position
	public int getDestX()
	{
		return destx;
	}
	public int getDestY()
	{
		return desty;
	}
	//Get direction ( not equal to facing
	public int getDirection()
	{
		return direction;
	}
	//Facing judge look direction
	public int getFacing()
	{
		return facing;
	}
	
	public RigidBody(GameObject bind , int initX , int initY)
	{
		//Sync with binding object at initial
		this.bind = bind;
		this.x = initX;
		this.y = initY;
		this.destx = initX;
		this.desty = initY;
		
		//finally initialize movement info
		this.commandlist = new IntArray();
		this.direction = 2;
		this.facing = 2;
		this.speed = 4; //temporary
	}
	
	@Override
	public void install(GameObject bind) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		//Update new coordinate to binding object
		updateMove();
		bind.setX(x);
		bind.setY(y);
	}

	@Override
	public void unintall() {
		// TODO Auto-generated method stub
		
	}
	
	
	/***********************Interact**************************/
	/***********************Start*****************************/
	//Initiative
	public void interact()
	{
		if(contactRigidBody != null)
			contactRigidBody.onContact(this);
	}
	//Passive
	public void onContact(RigidBody obj)
	{
		if(obj.getRealY() > this.getRealY())
			facing = 8;
		if(obj.getRealY() < this.getRealY())
			facing = 2;
		if(obj.getRealX() > this.getRealX())
			facing = 6;
		if(obj.getRealX() < this.getRealX())
			facing = 4;
		
		//Invoke interact type event
		for(Event event : this.getBinding().getEventList()){
			event.invoke();
		}
	}
	/***********************Interact**************************/
	/*************************End*****************************/
	
	
	/**********************Movement info**********************/
	/***********************Start*****************************/
	//executeCommand : execute commands in the commands list
	public void executeCommand()
	{
			//command list is empty
			if(commandlist.size < 0)
				return ;
			
			boolean result = false;
			int index = 0;
			
			//find the command which not yet execute
			while(commandlist.get(index) == -1){
				index++;
				//no commands in the list , reset the commandlist
				if(index == commandlist.size){
					commandlist.clear();
					return;
				}
			}
			
			//execute command
			switch(commandlist.get(index)){
			case 0:
				result = forward();
				break;
			case 1:
				result = moveDown();
				break;
			case 2:
				result = moveLeft();
				break;
			case 3:
				result = moveRight();
				break;
			case 4:
				result = moveUp();
				break;
			case 5:
				result = turnDown();
				break;
			case 6:
				result = turnLeft();
				break;
			case 7:
				result = turnRight();
				break;
			case 8:
				result = turnUp();
				break;
			}
			
			//if true , then that command is done , mark it with -1
			if(result)
				commandlist.set(index, -1); 
			
		}
		
		//Moving : check is on the move
	public boolean moving()
	{
			//dest != real x
			if((this.destx != getRealX() || this.desty != getRealY()))
				//direction != block
				if(direction != isBlock){
					return true;
				}
				else{
					//Push back the dest
					this.destx = getRealX();
					this.desty = getRealY();
				}
			
			return false;
	}
		
	//update move
	public void updateMove()
	{
			if(moving()){
				switch(direction){
				case 2:
					this.y -= speed;
					break;
				case 4:
					this.x -= speed;
					break;
				case 6:
					this.x += speed;
					break;
				case 8:
					this.y += speed;
					break;
				}
			}			
	}
	//forward : step one tile along current direction
	public boolean forward()
	{
			//if dest != real , that object is on the move
			if(moving()){
				return false;
			}
			
			//if dest same as self , then update dest depend on direction
			switch(direction){
			case 2:
				this.desty = getRealY() - 32;
				break;
			case 4:
				this.destx = getRealX() - 32;
				break;
			case 6:
				this.destx = getRealX() + 32;
				break;
			case 8:
				this.desty = getRealY() + 32;
				break;
			default:
				break;
			}
			
			//return true notify that route executer that this command is done
			return true;
	}
	public boolean moveDown()
	{
			if(!moving()){
				turnDown();
				forward();
				return true;
			}
			return false;
	}
	public boolean moveLeft()
	{
			if(!moving()){
				turnLeft();
				forward();
				return true;
			}
			return false;
	}
	public boolean moveRight()
	{
			if(!moving()){
				turnRight();
				forward();
				return true;
			}
			return false;
	}
	public boolean moveUp()
	{
			if(!moving()){
				turnUp();
				forward();
				return true;
			}
			return false;
	}
	public boolean turn(int dir)
	{
			return false;
		}
	public boolean turnDown()
	{
			if(!moving()){
				direction = 2;
				if(!isFixFacing)
					facing = 2;
				return true;
			}
			return false;
	}
	public boolean turnUp()
	{
			if(!moving()){
				direction = 8;
				if(!isFixFacing)
					facing = 8;
				return true;
			}
			return false;
	}
	public boolean turnLeft()
	{
			if(!moving()){
				direction = 4;
				if(!isFixFacing)
					facing = 4;
				return true;
			}
			return false;
	}
	public boolean turnRight()
	{
			if(!moving()){
				direction = 6;
				if(!isFixFacing)
					facing = 6;
				return true;
			}
			return false;
	}
	public void fixFacing(boolean fix)
	{
			isFixFacing = fix;
	}
	/**********************Movement info**********************/
	/*************************End****************************/
	
	
	/***********************Physic****************************/
	/***********************Start*****************************/
	public boolean isPassible(TiledMap level , int x , int y)
	{				
		int mapBlock = 0;
		
		for(int i = 0 ; i < level.getLayers().getCount() ; i++){
			//get layer
			TiledMapTileLayer layer = (TiledMapTileLayer)level.getLayers().get(i);
			
			//skip layers
			if(layer.getName().equals("Top"))
				continue;
			
			//Tile coordinate
			int tx = this.destx / 32;
			int ty = this.desty / 32;
			
			//check border ( when destination is on the bound , stop update destination
			if(tx < 0){
				mapBlock = 4;
				continue;
			}
			else if(tx > layer.getWidth() - 1){
				mapBlock = 6;
				continue;
			}
			else if(ty < 0){
				mapBlock = 2;
				continue;
			}
			else if(ty > layer.getHeight() - 1){
				mapBlock = 8;
				continue;
			}
			
			//check top tile property
			//if no cell , continue to next layer
			if(layer.getCell(tx, ty) == null)
				continue;
			
			TiledMapTile tile = layer.getCell(tx, ty).getTile();
			
			//first check tile if exist , if not continue to next layer
			if(tile == null)
				continue;
			
			//tile property
			Object property;
		
			switch(direction){
			//tile up border and down border should be checked
			case 2:
				if((property = tile.getProperties().get("Up")) != null){
					if(property.equals("0")){
						mapBlock = 2;
						continue;
					}
				}
				if((property = tile.getProperties().get("Down")) != null){
					if(property.equals("0")){
						mapBlock = 2;
						continue;
					}
				}
			case 4:
				if((property = tile.getProperties().get("Left")) != null){
					if(property.equals("0")){
						mapBlock = 4;
						continue;
					}
				}
				if((property = tile.getProperties().get("Right")) != null){
					if(property.equals("0")){
						mapBlock = 4;
						continue;
					}
				}
			case 6:
				if((property = tile.getProperties().get("Left")) != null){
					if(property.equals("0")){
						mapBlock = 6;
						continue;
					}
				}
				if((property = tile.getProperties().get("Right")) != null){
					if(property.equals("0")){
						mapBlock = 6;
						continue;
					}
				}
			case 8:
				if((property = tile.getProperties().get("Up")) != null){
					if(property.equals("0")){
						mapBlock = 8;
						continue;
					}
				}
				if((property = tile.getProperties().get("Down")) != null){
					if(property.equals("0")){
						mapBlock = 8;
						continue;
					}
				}
			}
			
			//if no collide border and no collide levelobject
			mapBlock = 0;
		}
		
		isBlock = mapBlock;
		if(mapBlock != 0)
			return false;
		else
			return true;
	}
	public boolean onCollision(RigidBody obj){
		
		//Define self tile info
		int selfX = getRealX();
		int selfY = getRealY();
		//int selfWidth = 32; // temp , maybe more larger but must be multiple of 32
		//int selfHeight = 32;
		
		//Define test obj tile info
		int objX = obj.getRealX();
		int objY = obj.getRealY();
		//int objWidth = 32; // temp
		//int objHeight = 32;
		
		//To Right
		if(Line2D.linesIntersect(selfX + 32, selfY , selfX + 32, selfY + 32,
				objX, objY, objX, objY + 32)
			&&!PointOverlap(selfX + 32 , selfY , objX , objY + 32)
			&&!PointOverlap(selfX + 32 , selfY + 32 , objX , objY)){
			isBlock = 6;
			this.contactRigidBody = obj;
			return true;
		}
		//To Left
		if(Line2D.linesIntersect(selfX, selfY , selfX, selfY + 32,
				objX + 32, objY, objX + 32, objY + 32)
			&&!PointOverlap(selfX , selfY , objX + 32 , objY + 32)
			&&!PointOverlap(selfX , selfY + 32 , objX + 32 , objY)){
			isBlock = 4;
			this.contactRigidBody = obj;
			return true;
		}
		//To Up
		if(Line2D.linesIntersect(selfX , selfY + 32 , selfX + 32, selfY + 32,
				objX, objY, objX + 32, objY)
			&&!PointOverlap(selfX  , selfY + 32 , objX + 32 , objY)
			&&!PointOverlap(selfX + 32 , selfY + 32 , objX , objY)){
			isBlock = 8;
			this.contactRigidBody = obj;
			return true;
		}
		//To Down
		if(Line2D.linesIntersect(selfX , selfY  , selfX + 32, selfY ,
				objX, objY + 32, objX + 32, objY + 32)
			&&!PointOverlap(selfX , selfY , objX + 32 , objY + 32)
			&&!PointOverlap(selfX + 32 , selfY , objX , objY + 32)){
			isBlock = 2;
			this.contactRigidBody = obj;
			return true;
		}
		this.contactRigidBody = null;
		isBlock = 0;
		return false;
	}

	private boolean PointOverlap(int x1 , int y1 , int x2 , int y2)
	{
		if(x1 == x2 && y1 == y2)
			return true;
		return false;
	}
	/***********************Physic****************************/
	/***********************End*******************************/

}
