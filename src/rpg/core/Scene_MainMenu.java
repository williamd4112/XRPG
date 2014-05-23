package rpg.core;
import rpg.factory.TextureFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;

public class Scene_MainMenu extends Scene implements Screen , InputProcessor{
	
	final XRPGGame game;
	final SceneCamera camera;
	final ShapeRenderer shape;
	
	private int width;
	private int height;
	
	SpriteBatch batch;
	BitmapFont font;
	
	//Menu Select
	private int index;
	
	//Intro
	private Texture background;
	private String title = "X-RPG";
	private String info = "Made by libGDX";
	private char[] tmp;
	
	//Animation
	private float alpha = 0.0f;
	private int frame = 0;
	private int key = 0;
	private int interval = 4;
	
	public Scene_MainMenu(final XRPGGame game)
	{
		this.game = game;
		this.camera = new SceneCamera();
		this.batch = new SpriteBatch();
		this.shape = new ShapeRenderer();
		this.index = 2;
		this.tmp = info.toCharArray();
		this.background = TextureFactory.getInstance().genPicture("Intro");
		Gdx.input.setInputProcessor(this);
	
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		frame();
		GraphicHandle();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		this.width = width;
		this.height = height;
		
		genFont("TrajanPro-Regular");
		
		camera.position.set(width/2, height/2, 0);
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		this.background.dispose();
		//this.batch.dispose();
		//this.shape.dispose();
		this.font.dispose();
		
	}
	
	public void GraphicHandle()
	{		
		renderBackground();
		renderCursor();
		
		this.batch.begin();
			renderOptions();
		this.batch.end();
		
	}
	
	//Animation
	private void frame()
	{
		//Background
		if(alpha < 1)
			alpha += Gdx.graphics.getDeltaTime() * 0.5f;		
		else
			alpha = 1.0f;
		//Text
		if(key >= tmp.length)
			key = 0;
		if(frame > interval){
			key++;
			frame = 0;
		}
		else
			frame++;
	}
	
	private void renderBackground()
	{		
		Color c = this.batch.getColor();
		this.batch.setColor(c.r , c.g, c.b , alpha);
		this.batch.begin();
			this.batch.draw(background, 0, 0 , width , height);
		this.batch.end();
	}
	
	private void renderOptions()
	{
		this.font.draw(this.batch, "新遊戲", 20 , 6*font.getLineHeight());
		this.font.draw(this.batch, "繼續遊戲", 20 , 4*font.getLineHeight());
		this.font.draw(this.batch, "離開", 20 , 2*font.getLineHeight());
		//this.font.draw(this.batch, String.valueOf(tmp, 0, key), width - tmp.length * (height / 30), font.getLineHeight() * 2);
	}
	private void renderCursor()
	{
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		shape.setColor(1.0f ,0.0f , 0.0f , 0.5f);
		shape.setProjectionMatrix(this.camera.combined);
		
		this.shape.begin(ShapeType.Line);
			shape.line(20, (index + 1)*2*font.getLineHeight() - font.getLineHeight() , 200, (index + 1)*2*font.getLineHeight() - font.getLineHeight());
		this.shape.end();
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
	}
	
	public void genFont(String type)
	{
		FileHandle fontfile = Gdx.files.internal("Font/wt003.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontfile);
		FreeTypeBitmapFontData fontdata  = generator.generateData(height/30, "新遊戲繼續離開", false);
		font = new BitmapFont(fontdata , fontdata.getTextureRegion() , false);
		generator.dispose();
	}
	

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		switch(keycode){
		case Input.Keys.ENTER:
			if(index == 2){
				game.setScreen(new Scene_Level("Level2"));
				this.dispose();
			}
		case Input.Keys.DOWN:
			if(index > 0)
				index--;
			else
				index = 2;
			break;
		case Input.Keys.UP:
			if(index < 2)
				index++;
			else
				index = 0;
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
