package rpg.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import rpg.action.ActionLibrary;
import rpg.core.GameSystem;
import rpg.core.SceneCamera;
import rpg.gameobject.*;
import rpg.gameobject.Character;

public class PlayerControlPanel implements Component , InputProcessor {
	
	//Controlled Player
	private Character player;
	private CharcterController controller;
	
	//Viewport ( will post to gamesystem
	private SceneCamera camera;
	
	public SceneCamera getCamera()
	{
		return camera;
	}
	
	@Override
	public void install(GameObject bind) {
		//Bind the player
		this.player = (Character)bind;
		//Bind the rigidbody
		this.controller = player.getCharacterController();
		//Create a camera to monitor
		this.camera = new SceneCamera();
		//Bind the camera to player
		this.camera.bind(player);
		//Set the player on the system
		GameSystem.getInstance().setPlayer(player);
		//Request control
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void update() {
		if(player == null)
			return;
		
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			controller.stepDash();
		else
			controller.stepWalk();
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			controller.stepDown();
			return;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			controller.stepLeft();
			//player.getAnimator().doAnimation();
			return;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			controller.stepRight();
			//player.getAnimator().doAnimation();
			return;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			controller.stepUp();
			//player.getAnimator().doAnimation();
			return;
		}
		
	}

	@Override
	public void unintall() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if(player == null)
			return false;
		switch(keycode){
		case Input.Keys.C:
			break;
		case Input.Keys.D:
			player.getSprite().setTexure("Hero_Dash");
			player.getAnimator().setFrequency(4);
			break;
		case Input.Keys.ENTER:
			controller.interact();
			break;
		case Input.Keys.ESCAPE:
			ActionLibrary.showMenu(new Object[]{"Pause"});
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(player == null)
			return false;
		switch(keycode){
		case Input.Keys.D:
			player.getSprite().setTexure("Hero");
			player.getAnimator().setFrequency(6);
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
