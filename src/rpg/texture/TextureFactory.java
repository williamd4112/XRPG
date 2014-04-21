package rpg.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class TextureFactory {
	
	private static TextureFactory instance;
	
	private ObjectMap<String , Texture> pool;
	
	public Texture genSkin(String name)
	{
		//if specific texture has already in pool return it
		if(pool.containsKey(name)){
			return pool.get(name);
		}
		//if not , create a new one
		else{
			Texture texture = new Texture(Gdx.files.internal("Skin/" + name +".png"));
			pool.put(name, texture);
			return texture;
		}
		
	}
	
	//use for gameobject
	public Texture genTexture(String name)
	{
		//if specific texture has already in pool return it
		if(pool.containsKey(name)){
			return pool.get(name);
		}
		//if not , create a new one
		else{
			Texture texture = new Texture(Gdx.files.internal("Textures/" + name +".png"));
			pool.put(name, texture);
			return texture;
		}
	}
	//use for title , foreground , and some big picture
	public Texture genPicture(String name)
	{
		//if specific texture has already in pool return it
		if(pool.containsKey(name)){
			return pool.get(name);
		}
		//if not , create a new one
		else{
			Texture texture = new Texture(Gdx.files.internal("Pictures/" + name +".png"));
			pool.put(name, texture);
			return texture;
		}
	}
	
	public static TextureFactory getInstance()
	{
		if(instance == null){
			instance = new TextureFactory();
			instance.pool = new ObjectMap<String , Texture>();
		}
		return instance;
	}
}
