public class Armor {
	public String name;
	public int type;
	public int price;
	public int protection;
	public int quality = 0;
	public boolean lootable = false;
	
	public Armor() {
	}
	
	public void OnEquip(Player player) {
		if (name.equals("Wizard Hat of Equivalence")) {
			System.out.println("You feel your magical abilities enhance as your strength diminishes.");
			player.skills.put("Strength", player.skills.get("Strength") - 2);
			player.skills.put("Intelligence", player.skills.get("Intelligence") + 2);
		}
	}
	
	public void OnUnequip(Player player) {
		if (name.equals("Wizard Hat of Equivalence")) {
			System.out.println("You feel the magical effects of the hat wear off.");
			player.skills.put("Strength", player.skills.get("Strength") + 2);
			player.skills.put("Intelligence", player.skills.get("Intelligence") - 2);
		}
	}
	
	public String toString() {
		return name;
	}
}
