public class SpellEffect {
	public String name;
	public String spell_type;
	public int damage;
	public int duration;
	
	public SpellEffect(String name, String spell_type, int damage, int duration) {
		this.name = name;
		this.damage = damage;
		this.duration = duration;
		this.spell_type = spell_type;
	}
	
	public String toString() {
		return name;
	}
}
