package rpg.core;

import java.io.IOException;

import rpg.UI.dialog.Dialog;
import rpg.action.Action_showDialog;
import rpg.controller.SceneCamera;
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
		initializeGameObjectManager();
		initializeUIManager();
		initializePlayer();
		initializeGameObject();
		initializeCamera();
		initializeGraphics(level);
		initializePhys();
		initializeEvents();
	}
	
	//initializing events
	public void initializeEvents()
	{
		//init the event handler
		this.eventsManager = new SceneEventManager(this);
		
		//Test Dialog
		Dialog dialog = new Dialog(this);
		String p1 = "NPC1 : \nMy name is Kirito. Welcome to Sword Art Online.\n";
		String p2 = "NPC1 : \nThis is second page.\n";
		String p3 = "NPC1 : \nThis is third page.\n";
 		dialog.addPage(p1);
 		dialog.addPage(p2);
 		dialog.addPage(p3);
 		
		Dialog dialog2 = new Dialog(this);
		String p12 = "NPC2 : \nMy name is Kirito. Welcome to Sword Art Online.\n";
		String p22 = "NPC2 : \nThis is second page.\n";
		String p32 = "NPC2 : \nThis is third page.\n";
 		dialog2.addPage(p12);
 		dialog2.addPage(p22);
 		dialog2.addPage(p32);
 		
		Event event1 = new Event(this.objectManager.getObjectlist().get(1) 
				, Event.InvokeType.PARALLEL
				, Event.TriggerType.INTERACT 
				, Event.CycleType.REPEATABLE);
		event1.addAction(new Action_showDialog(this , dialog , event1));
		event1.addAction(new Action_showDialog(this , dialog2 , event1));
		
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

		UIManager.processDialog();
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
