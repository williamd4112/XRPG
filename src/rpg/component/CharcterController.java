package rpg.component;

import com.badlogic.gdx.utils.IntArray;

import rpg.gameobject.GameObject;

/*CharacterController : obtain a series of method to control animator , rigidbody , sprite*/
/*Other components present its function , CharacterController handle controlling*/

//Used to associate with the following component's working
public class CharcterController implements Component{
	
	//Bind with character's basic component
	private GameObject obj;
	private RigidBody body;
	private Animator animator;
	private Sprite sprite;
	
	//Command list
	private IntArray commandlist;
	
	@Override
	public void install(GameObject bind) {
		obj = bind;
		
		body = bind.getRigidBody();
		animator = bind.getAnimator();
		sprite = bind.getSprite();
		
		if(body == null || animator == null || sprite == null)
			System.err.println("CharacterController : Install failed");
		
		commandlist = new IntArray();
	}
	@Override
	public void update() {
		//sync animation with rigidbody
		//do animation when body is on the move or an animation cycle is not yet done
		if(body.isMoving() || animator.getKey() != 0){
			animator.doAnimation();
		}
		executeCommand();
		body.updateMove();
		
	}
	@Override
	public void unintall() {
		// TODO Auto-generated method stub
		
	}
	
	//Interaction
	public void interact()
	{
		body.interact();
	}
	
	//Direct control without command list
	public void stepDash()
	{
		body.dash();
	}
	public void stepWalk()
	{	
		body.walk();
	}
	public void stepDown()
	{
		body.moveDown();
	}
	public void stepLeft()
	{
		body.moveLeft();
	}
	public void stepRight()
	{
		body.moveRight();
	}
	public void stepUp()
	{
		body.moveUp();
	}
	public void turnDown()
	{
		body.turnDown();
	}
	public void turnLeft()
	{
		body.turnLeft();
	}
	public void turnRight()
	{
		body.turnRight();
	}
	public void turnUp()
	{
		body.turnUp();
	}

	//is empty list
	public boolean isEmptyCommandList()
	{	
		return commandlist.size < 1;
	}
	//clear the command list (some action may not like to let any command consist with it
	public void clearCommandList()
	{
		commandlist.clear();
	}
	//executeCommand : execute commands in the commands list
	public void executeCommand()
	{
			//command list is empty
			if(commandlist.size < 1)
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
				result = body.forward();
				break;
			case 1:
				result = body.moveDown();
				break;
			case 2:
				result = body.moveLeft();
				break;
			case 3:
				result = body.moveRight();
				break;
			case 4:
				result = body.moveUp();
				break;
			case 5:
				result = body.turnDown();
				break;
			case 6:
				result = body.turnLeft();
				break;
			case 7:
				result = body.turnRight();
				break;
			case 8:
				result = body.turnUp();
				break;
			case 9:
				result = body.dash();
				break;
			case 10:
				result = body.walk();
				break;
			}
			
			//if true , then that command is done , mark it with -1
			if(result){
				commandlist.set(index, -1); 
			}
			
	}
	
	//Add a move command
	public void addCommand(int code)
	{
		if(this.commandlist == null)
			this.commandlist = new IntArray();
		this.commandlist.add(code);
	}
	public void addCommand(int[] codes)
	{
		if(this.commandlist == null)
			this.commandlist = new IntArray();
		this.commandlist.addAll(codes);
	}
}
