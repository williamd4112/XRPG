package rpg.component;

import rpg.gameobject.GameObject;
import rpg.texture.TextureFactory;

import com.badlogic.gdx.graphics.Texture;

public class Sprite implements Component{
	//Graphics info
	protected Texture[] textures;
	protected int State = 0;
	
	public Sprite(String Name , int cState)
	{
		//Load textures from assets (depending on name
		textures = new Texture[cState];
		for(int i = 0 ; i < cState ; i ++)
		{
			textures[i] = TextureFactory.getInstance().genTexture(Name + i);
			if(textures[i] != null){
				break;
			}
		}
	}
	
	//Accessor
	public Texture getTexture()
	{
		return textures[State];
	}
	
	public void setState(int state)
	{
		this.State = state;
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
