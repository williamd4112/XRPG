package rpg.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import rpg.controller.SceneCamera;
import rpg.core.GameSystem;
import rpg.gameobject.*;
import rpg.gameobject.Character;

public class PlayerControlPanel implements Component , InputProcessor {
	
	//Controlled Player
	private Character player;
	private RigidBody playerRigidbody;
	
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
		this.playerRigidbody = (RigidBody) player.getRigidBody();
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
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			playerRigidbody.moveDown();
			return;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			playerRigidbody.moveLeft();
			return;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			playerRigidbody.moveRight();
			return;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			playerRigidbody.moveUp();
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
		case Input.Keys.ENTER:
			playerRigidbody.interact();
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
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
