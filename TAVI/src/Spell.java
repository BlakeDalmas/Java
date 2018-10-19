import java.util.*;

public class Spell {
	public String name;
	public String cast_message;
	public String spell_type;
	public int damage;
	public int cost;
	public int duration = 0;
	public int price = 0;
	
	public Spell() {
	}
	
	public String toString() {
		return name;
	}
}
