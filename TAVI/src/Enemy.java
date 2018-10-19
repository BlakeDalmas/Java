import java.util.*;

public class Enemy {
	public String name = "Dee Bug";
	public int health = 10;
	public int maxhealth = 10;
	public int attack = 1;
	public int level = 1;
	public boolean escapable = true;
	public String damage_type = "Physical";
	public String attack_msg = "The Dee Bug bites you";
	public String run_attack_msg = "The Dee Bug bites your ankle as you run away";
	public HashMap<String, Integer> resistances = new HashMap<String, Integer>();
	public HashMap<String, Integer> weakness = new HashMap<String, Integer>();
	
	public List<SpellEffect> spell_effects = new ArrayList<SpellEffect>();
	
	public boolean surface = true;
	public boolean dungeon = true;
	
	public Object unique;
	
	public boolean alive = true;
	
	public Enemy() {
	}
	
	/* Damage Comparisons
	5-7 damage - someone slapping or punching you
	7-9 damage - someone cutting you with a small knife
	9-11 damage - someone slashing you with a sword or casting typical spells
	11-13 damage - someone hitting you with a warhammer or casting a devastating spell
	13-15 damage - someone slitting your throat
*/

/* Level Comparisons
  	Level 6 - A really weak enemy like a rat
  	Level 7 - A typical enemy for the area
  	Level 8 - A stronger enemy for the area
  	Level 9 - A boss
  	Level 10 - An exceedingly rare enemy
*/

/* Health Comparisons
 * 	25-40 health: A rodent
 	40-50 health: A deer
 	50-60 health: A weak monster
 	60-70 health: A goblin
 	70-80 health: A slightly stronger goblin
 	80-90 health: An unarmored human or plant monster
 	90-100 health: A lightly armored human or lizard goblin thing
 	100-110 health: A heavily armored human or knight or not giant stone thing
 	110-120 health: A tree monster thing or big stone thing
 	120+ health: A literal statue or gigantic stone thing
*/
	
	public void TakeDamage(int damage, String damagetype, Player player, boolean print_dmg_msg) {
		boolean take_damage = true;
		
		if (resistances.containsKey(damagetype)) {
			damage = (int)Math.ceil((double)damage - ((double)damage * (resistances.get(damagetype) / 100.0)));
			
			if (resistances.get(damagetype) >= 100) {
				System.out.println("The " + name + " is immune to that form of damage.");
				take_damage = false;
			} else {
				System.out.println("The " + name + " resisted your attack!");	
			}
		}
		
		if (weakness.containsKey(damagetype)) {
			damage = (int)Math.ceil((double)damage + ((double)damage * (weakness.get(damagetype) / 100.0)));
		}
		
		if (player.eq_weapon.name.equals("Mace of Destruction") && weakness.containsKey("Blunt")) {
			damage = weakness.get("Blunt");
		}
		
		if (take_damage) {
			if (print_dmg_msg) System.out.println("You deal " + damage + " damage against the " + name);	
			
			health -= damage;
			if (health <= 0) Die(player);
		}
		
		UniqueEffects(player);
	}
	
	public void UniqueEffects(Player player) {
		String wep = player.eq_weapon.name;
		
		if (wep.equalsIgnoreCase("Venomblade")) {
			health -= 2;
			if (health <= 0) Die(player);
			System.out.println("The " + name + " was poisoned for 2 damage!");
		}
	}
	
	public void Die(Player player) {
		alive = false;
		
		int bonus = (int)Math.ceil((double)(maxhealth * attack) / 100.0);
		int xp_gain = (int)Math.ceil(level / 2) + bonus;
		
		System.out.println("You gained " + xp_gain + " xp for killing the " + name + "!");
		
		player.xp += xp_gain;
		if (player.xp >= player.next_lvl_xp) {
			player.LevelUp();
		}
	}
	
	public void ResetStats() {
		spell_effects = new ArrayList<SpellEffect>();
		health = maxhealth;
		alive = true;
	}
	
	public void PrintHealth() {
		System.out.println(name + " health: " + health + " / " + maxhealth);
	}
	
	public void Attack(Player player, boolean running) {
		int damage = (int)((double)attack - Math.ceil((double)attack * ((double)player.armor_value / 100.0)));
		if (damage <= 0) damage = 1;
		
		if (Math.random() > .75) {
			if (Math.random() > 0.5) {
				if (damage > 10) {
					damage = (int)(damage - (damage / 10));
				} else damage--;
				
				if (damage <= 0) damage = 1;
			} else {
				if (damage > 10) {
					damage = (int)(damage + (damage / 10));
				} else damage++;
			}
		}
		
		if (name.equalsIgnoreCase("Brown-Rock Necromancer") && Math.random() > 0.5) {
			System.out.println("The Brown-Rock Necromancer summons a skeleton to fight you.");
			
			Enemy skeleton = new Enemy();
				skeleton.name = "Skeleton";
				skeleton.maxhealth = 10;
				skeleton.health = skeleton.maxhealth;
				skeleton.attack = 1;
				skeleton.level = 1;
				skeleton.surface = false;
				skeleton.dungeon = false;
				skeleton.damage_type = "Physical";
				skeleton.attack_msg = "The " + skeleton.name + " slashes you with its sword";
				skeleton.run_attack_msg = "\"You can't esacpe me fool.\"";
				skeleton.escapable = false;
			player.FightEnemy(skeleton, false);
		} else {
			if (running) {
				System.out.println(run_attack_msg + ", dealing " + damage + " damage.");
			} else System.out.println(attack_msg  + ", dealing " + damage + " damage.");
			
			if (name.equalsIgnoreCase("Goldstone Mummy Priest")) {
				health += damage;
				if (health > maxhealth) health = maxhealth;
			}
		}
		
		player.TakeDamage(damage, damage_type);
				
		if (name.equalsIgnoreCase("Lord of Dreams")) {
			attack += (int)Math.ceil(attack / 5);
		}
		
		System.out.println();
	}
		
	public void SpecialLoot(Player player, Logic logic, Location location, Data data, String enemy_name) {
		// Pendant of Highland Recall
		// Teleports player back to the highlands
		
		// Boots of Traveling
		// reduces enemy encounters while traveling
		
		for (int i = 0; i < logic.location_correlations.length; i++) {
			String[] correlation = logic.location_correlations[i];
			
			for (int k = 0; k < correlation.length; k++) {
				if (location.name.equalsIgnoreCase(correlation[k])) {
					String id = i + ":" + k;
					GiveSpecialLoot(player, id, data, enemy_name);
				}
			}
		}
	}
	
	public void GiveSpecialLoot(Player player, String id, Data data, String enemy) {
		if (id.equalsIgnoreCase("0:0")) {
			player.inventory.get("Items").add(data.items.get("Mysterious Tome"));
			
			String msg = "With the killing blow, the " + enemy + " disintegrates into ash."
				+ "\n" + "You notice a strange book on an altar in the room, you decide to pick it up."
				+ "\n" + "You have acquired a Mysterious Tome."
				+ "\n" + "Upon reading the strange texts, you feel your mana drain from your body as your wounds heal."
				+ "\n" + "You consider the potential uses for this in the future.";
			
			System.out.println(msg);
			System.out.println();
			
			player.mana = 0;
			player.health = player.maxhealth;
		}
		
		if (id.equalsIgnoreCase("0:1")) {
			player.inventory.get("Items").add(data.items.get("Mysterious Cube"));
			
			String msg = "With the killing blow, the " + enemy + " disintegrates into ash."
				+ "\n" + "You notice a strange little cube on a table in the room. You decide to pick it up."
				+ "\n" + "You have acquired a Mysterious Cube."
				+ "\n" + "As you roll the cube around in your hand, a spike comes out of it, stabbing you."
				+ "\n" + "It hurts a little, but immediately after you feel your mana regenerate.";
				
			System.out.println(msg);
			System.out.println();
			
			if (player.health > 1) {
				player.health -= 1;				
			}
			
			player.RegenMana(3);
		}
		
		if (id.equalsIgnoreCase("1:0")) {
			player.inventory.get("Weapons").add(data.weapons.get("Venomblade"));
			
			String msg = "After the death of the " + enemy + ", you notice a blade in the room."
					+ "\n" + "You pick up the dagger, it's marked with strange engravings and seems"
					+ "\n" + "to glisten a green hue."
					+ "\n" + "You have acquired Venomblade.";
					
			System.out.println(msg);
			System.out.println();
		}
		
		if (id.equalsIgnoreCase("1:1")) {
			player.inventory.get("Items").add(data.items.get("Amulet of Persuasion"));
			
			String msg = "After the death of the " + enemy + ", you notice a small jewelry box on a table."
				+ "\n" + "You open the box and find a small amulet embedded with a green jem.";
			
			System.out.println(msg);
			System.out.println("You have acquired The Amulet of Persuasion.");
			System.out.println();
		}
		
		if (id.equalsIgnoreCase("2:0")) {
			player.inventory.get("Armor").add(data.armors.get("Boots of Traveling"));
			
			String msg = "As the " + enemy + " falls to the ground you notice the corpse of a dead adventurer."
					+ "\n" + "You approach the body and catch a glimpse of a pair of leather boots with a glistening blue sheen."
					+ "\n" + "As you slip the boots, you realize you can move much faster.";
				
			System.out.println(msg);
			System.out.println("You have acquired The Boots of Traveling.");
			System.out.println();
		}
		
		if (id.equalsIgnoreCase("2:1")) {
			player.inventory.get("Armor").add(data.armors.get("Wizard Hat of Equivalence"));
			player.inventory.get("Potions").add(data.potions.get("Cheap Mana Potion"));
			
			String msg = "As the " + enemy + " falls to the ground you notice a chest in the corner of the room."
					+ "\n" + "You open the chest and find a stereotypical wizard hat with a purple sheen, and a mana potion."
					+ "\n" + "You try on the hat and feel your magical abilities increase."
					+ "\n" + "But your strength feels diminished as well.";
				
			System.out.println(msg);
			System.out.println("You have acquired The Wizard Hat of Equivalence.");
			System.out.println("You have acquired a Cheap Mana Potion.");
			System.out.println();
		}
		
		if (id.equalsIgnoreCase("3:0")) {
			player.inventory.get("Items").add(data.items.get("Goat Horn Necklace"));
			
			String msg = "As the " + enemy + " falls over, defeated, you notice something on the ground."
					+ "\n" + "You see a necklace with a goat horn as the pendant."
					+ "\n" + "You slip the necklace on and feel your strength increase.";
				
			System.out.println(msg);
			System.out.println("You have acquired The Goat Horn Necklace.");
			System.out.println();
		}
		
		if (id.equalsIgnoreCase("3:1")) {
			player.inventory.get("Weapons").add(data.weapons.get("Mace of Destruction"));
			
			String msg = "As the " + enemy + " falls over, defeated, you notice something on the ground."
					+ "\n" + "You see a mace with writing on it. The writing says:"
					+ "\n" + "\"The weak will perish.\"";
				
			System.out.println(msg);
			System.out.println("You have acquired The Mace of Destruction.");
			System.out.println();
		}
	}
}
