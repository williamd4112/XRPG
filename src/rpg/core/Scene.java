package rpg.core;

import java.util.Arrays;

import rpg.UI.dialog.Dialog;
import rpg.gameobject.GameObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Scene {
	
	//sceneRender : handle graphics
	//physHandler : handle physics
	//SceneEventHandler : handle events
	//objectManager : handle object adding , removing , loading
	//UIManager : handle UI data processing  , adding , removing
	protected SceneRender sceneRender;
	protected ScenePhysicHandler physHandler;
	protected SceneEventManager eventsManager;
	protected SceneObjectManger objectManager;
	protected SceneUIManager UIManager;
	
	//User Eye
	protected SceneCamera  observer;

	public OrthographicCamera getCamera()
	{
		return observer;
	}
	public SpriteBatch getBatch()
	{
		return sceneRender.batch;
	}
	public SceneRender getRenderer()
	{
		return sceneRender;
	}
	
	public SceneUIManager getUIManager()
	{
		return UIManager;
	}

	
}
