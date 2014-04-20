package rpg.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ObjectMap;

public class FontFactory {
	private ObjectMap<String , BitmapFont> FontPool;
	
	public static BitmapFont genFont(String type , int height)
	{
		BitmapFont font = new BitmapFont();
		
		FileHandle fontfile = Gdx.files.internal("Font/" + type);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontfile);
		font = generator.generateFont(height / 30);
		generator.dispose();
		
		return font;
	}
}
