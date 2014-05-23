package rpg.alogrithm;

public class Alogrithm {
	
	//@param : start - start value
	//@param : end - target value
	//@param : speed - step scale
	//@param : origin - if reach end , go back to origin
	public static float lerp(float start , float end , float speed , float origin)
	{
		if(start == end)
			return origin;

		float range = end - start;
		float value = start + range * speed;

		if(value >= end)
			return origin;
		else
			return value;
	}
	
	//@param : start - start value
	//@param : end - target value
	//@param : speed - step scale
	public static float lerp(float start , float end , float speed)
	{
		if(start == end)
			return end;

		float range = end - start;
		float value = start + range * speed;

		if(value >= end)
			return end;
		else
			return value;
	}
	
	//@param : cur - current number
	//@param : dir - fix direction ( + or -
	public static int fix(int cur , boolean dir)
	{		
		int lvalue = cur;
		int rvalue = cur;
		while(true){
			if(lvalue % 32 != 0){
				lvalue--;
			}
			else
				return lvalue;
			
			if(rvalue % 32 != 0){
				rvalue++;
			}
			else
				return rvalue;
		}
		
	}
}
