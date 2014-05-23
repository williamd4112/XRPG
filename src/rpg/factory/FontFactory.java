package rpg.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Values;

public class FontFactory {
	
	private static FontFactory instance;
	
	public static enum FontType{
			DIALOG,
			TITLE
	};
	
	private ObjectMap<String , BitmapFont> FontPool;
	private int currentHeight = 0;
	
	public static FontFactory getInstance()
	{
		if(instance == null){
			instance = new FontFactory();
			instance.FontPool = new ObjectMap<String , BitmapFont>();
		}
		return instance;
	}

	//@param : type - define usage of font
	//@param : height - font size will fit for the specific height
	public BitmapFont genFont(FontType type ,  int height)
	{
		//if height is diff from current , reset the pool
		if(height != currentHeight)
			resetSize(height);
		
		BitmapFont font;
		String name = null;
		int scale = 1;
		
		//Set font scale (title is larger
		switch(type){
		case DIALOG:
			scale = 30;
			name = "AGaramondPro-Regular.otf";
			break;
		case TITLE:
			scale = 20;
			name = "AGaramondPro-Italic.otf";
			break;
		default:
			System.out.println("No type of such font mode.");
			return null;
		}
		
		//First search in the pool , if not , create one
		if((font = this.FontPool.get(name)) != null)
			return font;
		else{
			font = new BitmapFont();
			
			FileHandle fontfile = Gdx.files.internal("Font/" + name);
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontfile);
			font = generator.generateFont(currentHeight / scale);
			FontPool.put(name, font);
			
			generator.dispose();
			
			return font;
		}

	}
	
	public void resetSize(int h)
	{
		//Dispose all resource
		for(Values<BitmapFont> it = FontPool.values() ; it.hasNext ;){
			BitmapFont font = (BitmapFont) it.next();
			font.dispose();
		}
		//Clear the pool
		FontPool.clear();
		currentHeight = h;
	}
	

}
