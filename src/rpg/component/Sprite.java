package rpg.component;

import rpg.factory.TextureFactory;
import rpg.gameobject.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Sprite implements Component{
	
	//Graphics info
	private String Name;
	
	public Sprite(String Name)
	{
		this.Name = Name;
	}
	
	//Accessor
	public Texture getTexture()
	{
		return TextureFactory.getInstance().genTexture(Name);
	}
	
	public void setTexure(String Name)
	{
		this.Name = Name;
	}
	
	@Override
	public void install(GameObject bind) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unintall() {
		// TODO Auto-generated method stub
		
	}
}
