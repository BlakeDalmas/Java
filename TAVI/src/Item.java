public class Item {
	public String name;
	public boolean equippable;
	public int price;
	
	Logic logic;
	
	public Item() {
	}
	
	public String toString() {
		return name;
	}
	
	public void OnEquip(Player player) {
		if (name.equals("Amulet of Persuasion")) {
			System.out.println("As you slip on the amulet, you feel more charismatic.");
			player.skills.put("Charisma", player.skills.get("Charisma") + 2);
		}
		
		if (name.equals("Goat Horn Necklace")) {
			System.out.println("As you slip on the Goat Horn Necklace, you feel your strength increase.");
			player.skills.put("Strength", player.skills.get("Strength") + 1);
		}
	}
	
	public void OnUnequip(Player player) {
		if (name.equals("Amulet of Persuasion")) {
			System.out.println("After taking off the amulet, you feel less charismatic.");
			player.skills.put("Charisma", player.skills.get("Charisma") - 2);
		}
		
		if (name.equals("Goat Horn Necklace")) {
			System.out.println("As you take off the Goat Horn Necklace, you feel your strength diminish.");
			player.skills.put("Strength", player.skills.get("Strength") + 1);
		}
	}
	
	public void Use(Player player) {
		if (name.equalsIgnoreCase("Mysterious Cube")) {
			if (player.health <= 1) {
				player.Die();
			} else {
				System.out.println("You hold the cube in your hand, the spike comes out and draws blood.");
				System.out.println("You feel your mana regenerate.");
				
				player.health -= 1;		
				player.RegenMana(3);
			}

			return;
		}
		
		if (name.equalsIgnoreCase("Mysterious Tome")) {
			if (player.mana > 0 && player.health < player.maxhealth) {
				player.mana = 0;
				player.health = player.maxhealth;
				System.out.println("You look at the strange glyphs in the text, they almost seem to pop out.");
				System.out.println("You are drawn to them, and absorb them. After which, your wounds heal before your eyes.");
				System.out.println("You also feel the mana drain out of your body to depletion.");
			} else System.out.println("You read the strange text but nothing seems to happen.");
			return;
		}
		
		if (name.equalsIgnoreCase("Map")) {

			System.out.println("Map:");			
			for (int i = 0; i < logic.map.length; i++) {
				System.out.println();
				for (int k = 0; k < logic.map.length; k++) {
					String temp = logic.map[i][k].name;
					System.out.print(temp + " ");
					for (int v = 0; v < 24 - temp.length(); v++) {
						System.out.print(" ");
					}
				}
			}
			System.out.println();
			
			return;
		}
		
		System.out.println("The " + name + "doesn't seem to do anything.");
	}
}
