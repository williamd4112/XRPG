package rpg.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class UISkin {
	
	private Texture texture;
	private Texture cursor;
	
	public UISkin(String texture , String cursor)
	{
		this.texture = new Texture(Gdx.files.internal(texture));
		this.cursor = new Texture(Gdx.files.internal(cursor));
	}
}
