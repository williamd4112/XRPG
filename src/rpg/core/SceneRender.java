package rpg.core;

import rpg.component.Animator;
import rpg.component.RigidBody;
import rpg.component.Sprite;
import rpg.font.FontFactory;
import rpg.gameobject.GameObject;
import rpg.texturefactory.TextureFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

public class SceneRender {
	//Scene info
	final SpriteBatch batch;
	final Scene scene;
	
	//Level info
	final TiledMap level;
	final MapProperties levelInfo;
	final OrthogonalTiledMapRenderer levelRender;
	
	//Shape info
	final ShapeRenderer shapeRender;
	
	//Screen info
	private int width;
	private int height;
	
	//Dialog display temp
	private Texture background;
	private char[] tmp;
	private int key = 0;
	private BitmapFont font;
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public void setWidth(int w)
	{
		width = w;
	}
	public void setHeight(int h)
	{
		height = h;
	}
	public SceneRender(Scene scene , SpriteBatch batch , TiledMap level)
	{
		//init graphics utility
		this.batch = batch;
		this.scene = scene;
		
		//Set up level
		this.level = level;
		this.levelRender = new OrthogonalTiledMapRenderer(this.level , batch);
		this.levelInfo = this.level.getProperties();
		
		//build a shape renderer
		this.shapeRender = new ShapeRenderer();
		
		//Dialog
	
		this.background = TextureFactory.getInstance().genSkin("01-Dialog");
		
	}
	
	public void Render(float delta)
	{
		//clear buffer
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//render level
		levelRender.setView(this.scene.getCamera());
		levelRender.render();
		
		//render assistant shape
		//renderShape();
		
		//render gameobject
		batch.begin();
			renderGameObject();
			renderTopLayer();	
			renderDialog();
		batch.end();
	}
	
	public void resize(int w , int h)
	{
		this.width = w;
		this.height = h;
		this.font = FontFactory.genFont("AGaramondPro-Regular.otf", height);
		
	}
	
	private void renderDialog()
	{
		if(scene.UIManager.getDialogBox() == null || font == null || scene.UIManager.getBuffer() == null)
			return;
		if(scene.UIManager.getDialogBox().size < 1)
			return;
		Color c = batch.getColor();
		int x = (int) (scene.getCamera().position.x - scene.getRenderer().getWidth() / 2  + 32);
		int y = (int) (scene.getCamera().position.y - scene.getRenderer().getHeight() / 2  + 32);
		int w = scene.getRenderer().getWidth() - 64;
		int h = scene.getRenderer().getHeight()/3;
		
		batch.setColor(c.r, c.g ,c.b, 0.5f);
			batch.draw(background , x , y , w , h);
		batch.setColor(c.r, c.g ,c.b, 1.0f);
		
		font.drawWrapped(batch, String.valueOf(scene.UIManager.getBuffer()), x + 15 , y + h - 15, w);
	}
	private void renderGameObject()
	{
		Array<GameObject> gameobjectlist = scene.objectManager.getObjectlist();
		gameobjectlist.sort();
		
		for(GameObject obj : gameobjectlist){
			Sprite sprite = obj.getSprite();
			RigidBody body = obj.getRigidBody();
			Animator animator = obj.getAnimator();
			
			if(sprite != null){
				int facing = (body != null) ? body.getFacing()/2 - 1 : 2 ;
				int key = (animator != null) ? animator.getKey() : 0;
				
				this.batch.setProjectionMatrix(this.scene.observer.combined);
				this.batch.draw(sprite.getTexture()
						, obj.getX(), obj.getY(), 32, 48
						, 32*key , 48*facing, 32, 48 
						, false, false);
			}

		}
	}
	
	private void renderTopLayer()
	{
		TiledMapTileLayer top = (TiledMapTileLayer)level.getLayers().get("Top");
		levelRender.renderTileLayer(top);
	}
	
	private void renderShape()
	{
		TiledMapTileLayer layer = (TiledMapTileLayer)level.getLayers().get(0);
		int width = layer.getWidth()*32;
		int height = layer.getHeight()*32;
		
		shapeRender.setColor(Color.BLACK);
		shapeRender.setProjectionMatrix(this.scene.observer.combined);
		shapeRender.begin(ShapeType.Line);
			for(int y = 0 ; y < height ; y += 32){
				shapeRender.line(0 , y , width , y);
			}
			for(int x = 0 ; x < width ; x += 32){
				shapeRender.line(x , 0 , x , height);
			}
		shapeRender.end();
		
		//Gdx.gl.glEnable(GL20.GL_BLEND);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		//shapeRender.setColor(1.0f , 0.0f , 0.0f , 0.3f);
		//shapeRender.setProjectionMatrix(this.scene.observer.combined);
		
		//shapeRender.begin(ShapeType.Filled);
			//for(GameObject character : scene.getObjectlist()){
				//RigidBody body = character.getRigidBody();
				//if(body != null){
					//shapeRender.rect(body.getRealX(), body.getRealY(), 32, 32);
					//shapeRender.rect(body.getDestX(), body.getDestY(), 32, 32);
				//}
			
			//}
		//shapeRender.end();
		
		//Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
}
