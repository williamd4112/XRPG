package rpg.core;

import java.io.IOException;

import rpg.UI.dialog.Dialog;
import rpg.event.Event;
import rpg.gameobject.Character;
import rpg.gameobject.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

public class Scene_Level extends Scene implements Screen {
	
	private TiledMap level;

	public Scene_Level(String level)
	{
		super();
		register();
		initializeGameObjectManager();
		initializeUIManager();
		initializePlayer();
		initializeGameObject();
		initializeCamera();
		initializeGraphics(level);
		initializePhys();
		initializeEvents();
	}
	//register on game system
	public void register()
	{
		GameSystem.getInstance().setScene(this);
	}
	//initializing events
	public void initializeEvents()
	{
		//init the event handler
		this.eventsManager = new SceneEventManager(this);
		
		//Test Dialog
		Object[] params = new Object[2];
		
		String[] context = {"NPC1: \nWelcome to Sword Art Online." , "NPC1 :\nMy name is kirito , yoroshiku ."};
 		
		params[0] = this;
		params[1] = context;
		
		Event event1 = new Event(this.objectManager.getObjectlist().get(1) 
				, Event.InvokeType.PARALLEL
				, Event.TriggerType.INTERACT 
				, Event.CycleType.REPEATABLE);
		event1.addAction("showDialog", params);
		event1.addAction("setMoveRoute" , new Object[]{this.objectManager.getObjectlist().get(1) , new int[]{9 , 2 , 3 , 4 , 3 , 3 , 3 , 4 , 4 , 4 , 4 , 4  , 6 , 6 ,6 , 6 ,10 , 1 , 1, 1, 1, 1}} );
		event1.addAction("waitUntilMoveDoneJam", new Object[]{this.objectManager.getObjectlist().get(1)});
		this.eventsManager.registerEvent(event1);
		
	}
	
	//initializing player
	public void initializePlayer()
	{
		//every time gamesystem build , the player will be created
		objectManager.addObject(GameSystem.getInstance().getPlayer());
	}
	
	//initializing gameobject manager
	public void initializeGameObjectManager()
	{
		objectManager = new SceneObjectManger();
	}
	
	//initializing game objects from .xml file
	public void initializeGameObject()
	{
		objectManager.addObject(new Character("npc" , 0 , 1 , 96 , 128));
	}
	
	//initializing UI Manager
	public void initializeUIManager()
	{
		UIManager = new SceneUIManager();
	}
	
	//initializing Camera
	public void initializeCamera()
	{
		if(GameSystem.getInstance().getPlayer().getControlPanel() == null){
			System.out.println("Scene_Level : Default Camera");
			observer = new SceneCamera();
		}
		else
			observer = GameSystem.getInstance().getPlayer().getControlPanel().getCamera();
	}
	
	//initializing graphics
	public void initializeGraphics(String level)
	{
		//Load the map from asset
		TmxMapLoader loader = new TmxMapLoader();
		this.level = loader.load(Gdx.files.internal("Level/" + level + ".tmx").path());
		
		sceneRender = new SceneRender(this , new SpriteBatch() , this.level);
	}
	
	//initializing physics
	public void initializePhys()
	{
		physHandler = new ScenePhysicHandler(this);
	}
	
	//get Current Level
	public TiledMap getLevel()
	{
		return level;
	}
	
	@Override
	public void render(float delta) {
		
		//if jam will skip input and logic , only process event
		if(!this.eventsManager.isJam()){
			//Logic
			physHandler.collisionTest();
			observer.follow();
			
			if(GameSystem.getInstance().getState() == GameSystem.GameState.NORMAL)
				for(GameObject obj : objectManager.getObjectlist())
					obj.update();
		}

		UIManager.processUI();
		eventsManager.processEvent();
		
		//Graphic
		sceneRender.Render(delta);
	}

	@Override
	public void resize(int width, int height) {
		//if observer has been init
		if(this.observer != null){
			//resize the scenerender
			this.sceneRender.resize(width, height);
			
			//Set up default camera
			this.observer.viewportWidth = width;
			this.observer.viewportHeight = height;
			this.observer.update();
		}
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {

		
	}

	@Override
	public void pause() {
	
		
	}

	@Override
	public void resume() {

		
	}

	@Override
	public void dispose() {

		
	}
	
}
