public class Weapon {
	public String name = "Stick";
	public int damage = 1;
	public String damage_msg = "You swing at the ";
	public String damage_type = "Blunt";
	public int price = 0;
	public int quality = 0;
	public boolean lootable = false;
	
	public Weapon() {

	}
	
	public int GetAttack(Player player, Enemy enemy) {		
		int result = (int)Math.ceil((double)damage * (Math.sqrt((double)player.skills.get("Strength")) / 2.5));
				
		if (name.equals("Eldritch Scythe of Doom")) {
			int temp = (int)Math.ceil(enemy.maxhealth / 10);
			enemy.TakeDamage(temp, "Burn", player, false);
		}
		
		if (name.equals("Fists")) {
			if (player.skills.get("Strength") < 5) {
				result = 0;
			}
			
			if (player.skills.get("Strength") >= 10) {
				result = 2;
			}
		}
		
		return result;
	}
	
	public String toString() {
		if (this == null) return "Fists";
		return name;
	}
}
