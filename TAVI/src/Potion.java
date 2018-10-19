public class Potion {
	public String name;
	public int price;
	public int strength;
	public int quality = 0;
	public boolean lootable = false;
	public String type;
	
	public Potion() {
	}
	
	public String toString() {
		return name;
	}
	
	public void Drink(Player player) {
		System.out.println("You drink the " + name + ".");
		
		if (type.equals("Health")) {
			player.health += strength;
			if (player.health > player.maxhealth) player.health = player.maxhealth;
			
			System.out.println("Your wounds heal as you regenerate " + strength + " health.");
		}
		
		if (type.equals("Mana")) {
			player.mana += strength;
			if (player.mana > player.maxmana) player.mana = player.maxmana;
			
			System.out.println("You regenerate " + strength + " mana.");
		}
	}
}
