package rpg.exception;

public class DefinitionError extends Exception{
	public DefinitionError(String type)
	{
		System.err.println(type + "has no definition.");
	}
}
