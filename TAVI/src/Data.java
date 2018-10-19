import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {

	public String[] outer_biomes = {
		"The Sleeper Mountains", "The Volcano", "The Ezonem Graveyard", "The Ashlands", 
		"The Ahibian Desertlands", "Silverwood Forest", "Viper Island", "The Outland Cliffs", 
		"The Jungle of Flies", "The Abyssal Caves", "The Sea of Glass", "The Swamplands", 
		"The Junkyard", "The Crystal Caverns", "The Valley of Souls", "The Frozen Lake"
	};
		
	public String[] inner_biomes = {
		"The Grasslands", "The Goldstone Desert", "The Wolfpack Tundra", "The Forest of Light",
		"The Hills of Wind", "Shimmerleaf Forest", "Gravel Lake", "Brown-Rock Canyon"
	};

	HashMap<String, Weapon> weapons = new HashMap<String, Weapon>();
	HashMap<String, Spell> spells = new HashMap<String, Spell>();
	HashMap<String, Item> items = new HashMap<String, Item>();
	HashMap<String, Armor> armors = new HashMap<String, Armor>();
	HashMap<String, Potion> potions = new HashMap<String, Potion>();
	
	Logic logic;
	
	public Data(Logic logic) {
		this.logic = logic;
	}	
		
	public void GenerateItemData() {
		GenerateWeapons();
		GenerateSpells();
		GenerateItems();
		GenerateArmor();
		GeneratePotions();
	}
	
	public void GenerateWeapons() {
		// Unique Weapons
		Weapon stick = new Weapon();
		stick.name = "Stick";
		stick.damage = 1;
		stick.damage_msg = "You swing at the ";
		stick.damage_type = "Blunt";
		stick.price = 1;
		weapons.put("Stick", stick);
		
		Weapon fists = new Weapon();
		fists.name = "Fists";
		fists.damage = 1;
		fists.damage_msg = "You punch the ";
		fists.damage_type = "Blunt";
		fists.price = 0;
		weapons.put("Fists", fists);
		
		Weapon esod = new Weapon();
		esod.name = "Eldritch Scythe of Doom";
		esod.damage = 70;
		esod.damage_msg = "You annihilate the ";
		esod.damage_type = "Slash";
		esod.price = 1000;
		weapons.put(esod.name, esod);
		
		Weapon wep = new Weapon();
		wep.name = "Venomblade";
		wep.damage = 6;
		wep.damage_msg = "You poison the ";
		wep.damage_type = "Slash";
		wep.price = 20;
		weapons.put(wep.name, wep);
		
		wep = new Weapon();
		wep.name = "Mace of Destruction";
		wep.damage = 1;
		wep.damage_msg = "You smash the ";
		wep.damage_type = "Blunt";
		wep.price = 25;
		weapons.put(wep.name, wep);
		
		// Generated Weapons
		String[] conditions = {
			"Broken",
			"Damaged",
			"Reliable",
			"Acute",
			"Flawless",
		};
		
		String[] materials = {
			"Wooden",
			"Stone",
			"Iron",
			"Bronze",
			"Steel",
			"Silver",
			"Dark Iron",
			"Silverwood",
			"Elven",
			"Obsidian",
			"Demon Steel",
			"Dragonium",
			"Eldritch"
		};
		
		String[] types = {
			"Dagger",
			"Club",
			"Sword",
			"Mace",
			"Spear",
			"Axe",
			"Claymore",
			"Warhammer",
			"Scythe"
		};
		
		for (int i = 0; i < materials.length; i++) {
			String material = materials[i];
			
			String[] msgs = {
				"You cut the ",
				"You beat the ",
				"You slash the ",
				"You pummel the ",
				"You stab the ",
				"You slice the ",
				"You gash the ",
				"You pound the ",
				"You lacerate the "
			};
			
			String[] dmg_types = {
				"Slash",
				"Blunt",
				"Slash",
				"Blunt",
				"Stab",
				"Slash",
				"Slash",
				"Blunt",
				"Slash"
			};
			
			for (int v = 0; v < conditions.length; v++) {
				String condition = conditions[v];
				
				for (int k = 0; k < types.length; k++) {
					String type = types[k];
					
					Weapon current = new Weapon();
					current.name = condition + " " + material + " " + type;
					current.damage = (1 + (i * 5)) + (int)Math.ceil(k / 2);
					current.damage_msg = msgs[k];
					current.damage_type = dmg_types[k];
					current.price = 1 + (int)(Math.pow((i * 1.5) + (k / 4), 2));
				
					int reduc = (int)(current.damage - (current.damage * ((26.0 + v) / 30.0)));
					current.damage -= reduc;
					if (v < 3) current.damage -= (conditions.length - 1) - v;
					if (current.damage < 1) current.damage = 1;
					int price_reduc = (int)(current.price - (current.price * ((8.0 + v) / 12.0)));
					current.price -= price_reduc;
					
					current.quality = i + 1;
					current.lootable = true;
					weapons.put(current.name, current);
				}	
			}
		}
	}
	
	public void GenerateSpells() {
		// Unique Spells
				
		Spell flare = new Spell();
		flare.name = "Flare";
		flare.cast_message = "You burn the ";
		flare.spell_type = "Fire";
		flare.damage = 2;
		flare.duration = 0;
		flare.cost = 1;
		spells.put(flare.name, flare);
		
//		Spell weak_poison = new Spell();
//		weak_poison.name = "Weak Poison";
//		weak_poison.cast_message = "You poison the ";
//		weak_poison.spell_type = "Poison";
//		weak_poison.damage = 1;
//		weak_poison.duration = 3;
//		weak_poison.cost = 2;
//		spells.put("Weak Poison", weak_poison);
//		
//		Spell healing = new Spell();
//		healing.name = "Healing";
//		healing.cast_message = "You cast Healing to regenerate your health.";
//		healing.spell_type = "Health";
//		healing.damage = 3;
//		healing.cost = 5;
//		spells.put("Healing", healing);
		
		// Generated Spells
		
		String[] quality = {
			"Novice",
			"Apprentice",
			"Adept",
			"Potent",
			"Master",
			"Deadly",
			"Devastating",
			"Annihilating"
		};
				
		for (int i = 0; i < quality.length; i++) {
			Spell blaze = new Spell();
			blaze.name = quality[i] + " Blaze";
			blaze.cast_message = "You ignite the ";
			blaze.spell_type = "Fire";
			blaze.damage = i + 1;
			blaze.duration = 3 + i;
			blaze.cost = 3 + (5 * i);
			blaze.price = 5 * (i + 1);
			spells.put(blaze.name, blaze);
			
			Spell firebolt = new Spell();
			firebolt.name = quality[i] + " Firebolt";
			firebolt.cast_message = "You launch a bolt of fire at the ";
			firebolt.spell_type = "Fire";
			firebolt.damage = 3 + (i * 2);
			firebolt.duration = 0;
			firebolt.cost = 2 + (int)(i * 1.5);
			firebolt.price = 4 * (i + 1);
			spells.put(firebolt.name, firebolt);
			
			Spell fireball = new Spell();
			fireball.name = quality[i] + " Fireball";
			fireball.cast_message = "You shoot a ball of fire at the ";
			fireball.spell_type = "Fire";
			fireball.damage = 4 + (i * 3);
			fireball.duration = 0;
			fireball.cost = 3 + (i * 3);
			fireball.price = 6 * (i + 1);
			spells.put(fireball.name, fireball);
			
			Spell inferno = new Spell();
			inferno.name = quality[i] + " Inferno";
			inferno.cast_message = "The fire consumes the ";
			inferno.spell_type = "Fire";
			inferno.damage = 3 + (i * 2);
			inferno.duration = 4 + i;
			inferno.cost = 10 + (10 * i);
			inferno.price = 7 * (i + 1);
			spells.put(inferno.name, inferno);
			
			Spell meteor = new Spell();
			meteor.name = quality[i] + " Meteor Strike";
			meteor.cast_message = "You hurl a meteor at the ";
			meteor.spell_type = "Fire";
			meteor.damage = 10 + (i * 10);
			meteor.duration = 1 + (int)Math.ceil(i / 2);
			meteor.cost = 15 + (9 * i);
			meteor.price = 8 * (i + 1);
			spells.put(meteor.name, meteor);
			
			Spell starblast = new Spell();
			starblast.name = quality[i] + " Starblast";
			starblast.cast_message = "A blinding energy devastates the ";
			starblast.spell_type = "Fire";
			starblast.damage = 20 + (int)Math.ceil(Math.pow(3, 2 + (i / 4)));
			starblast.duration = 0;
			starblast.price = 10 * (2 * (i + 1));
			starblast.cost = (int)Math.ceil(Math.pow(5, 2 + (i / 4)));
			
			Spell shock = new Spell();
			shock.name = quality[i] + " Shock";
			shock.cast_message = "You zap the ";
			shock.spell_type = "Shock";
			shock.damage = 5 + (i * 5);
			shock.duration = 0;
			shock.cost = 5 + (i * 3);
			shock.price = 3 * (i + 1);
			spells.put(shock.name, shock);
			
			Spell lightning = new Spell();
			lightning.name = quality[i] + " Lightning Bolt";
			lightning.cast_message = "You strike the ";
			lightning.spell_type = "Shock";
			lightning.damage = 10 + (10 * i);
			lightning.duration = 0;
			lightning.cost = 15 + (i * 10);
			lightning.price = 4 * (i + 1) + 2;
			spells.put(lightning.name, lightning);
			
			Spell storm = new Spell();
			storm.name = quality[i] + " Thunder Storm";
			storm.cast_message = "You summon a Thunder Storm above the ";
			storm.spell_type = "Shock";
			storm.damage = 1 + i;
			storm.duration = 2 + i;
			storm.cost = 3 + (4 * i);
			storm.price = 5 * (i + 1) + 3;
			spells.put(storm.name, storm);
			
			Spell poison = new Spell();
			poison.name = quality[i] + " Poison";
			poison.cast_message = "You inflict a deadly poison on the ";
			poison.spell_type = "Poison";
			poison.damage = 1 + (i * 2);
			poison.duration = 2 + (4 * i);
			poison.cost = 5 + (6 * i);
			poison.price = 7 * (i + 2);
			spells.put(poison.name, poison);
			
			Spell viper = new Spell();
			viper.name = quality[i] + " Viper Bite";
			viper.cast_message = "You conjur a deadly viper that bites the ";
			viper.spell_type = "Poison";
			viper.damage = 10 + (12 * i);
			viper.duration = 0;
			viper.cost = 20 + (i * 10);
			viper.price = 8 * (i + 2);
			spells.put(viper.name, viper);
			
			Spell healing = new Spell();
			healing.name = quality[i] + " Healing";
			healing.cast_message = "You regenerate your health with " + healing.name;
			healing.spell_type = "Health";
			healing.damage = 2 + (2 * i);
			healing.duration = 0;
			healing.cost = 5 + (i * 5);
			healing.price = 10 * (i + 1);
			spells.put(healing.name, healing);
			
			Spell regen = new Spell();
			regen.name = quality[i] + " Regeneration";
			regen.cast_message = "You mend your wounds with " + healing.name;
			regen.spell_type = "Health";
			regen.damage = 1 + i;
			regen.duration = 1 + (i * 2);
			regen.cost = 10 + (i * 7);
			regen.price = 15 * (i + 1);
			spells.put(regen.name, regen);
		}
	}
	
	public void GenerateArmor() {
		Armor none = new Armor();
		none.name = "None";
		none.type = 1;
		none.price = 0;
		none.protection = 0;
		none.quality = 0;
		none.lootable = false;
		armors.put(none.name, none);
		
		Armor spec = new Armor();
		spec.name = "Boots of Traveling";
		spec.type = 1;
		spec.price = 35;
		spec.protection = 4;
		spec.quality = 0;
		spec.lootable = false;
		armors.put(spec.name, spec);
		
		spec = new Armor();
		spec.name = "Wizard Hat of Equivalence";
		spec.type = 2;
		spec.price = 30;
		spec.protection = 1;
		spec.quality = 0;
		spec.lootable = false;
		armors.put(spec.name, spec);
		
		String[] conditions = {
			"Broken",
			"Worn",
			"Sturdy",
			"Resilient",
			"Excellent"
		};
		
		String[] pieces = {
			"Gloves",
			"Boots",
			"Helmet",
			"Greaves",
			"Cuirass"
		};
		
		String[] materials = {
			"Ancient Leather",
			"Leather",
			"Rusty Iron",
			"Iron",
			"Steel",
			"Silver",
			"Knight",
			"Dark Iron",
			"Elven",
			"Demon Steel",
			"Eldritch",
			"Dragonium",
			"Eldritch Knight"
		};
		
		for (int i = 0; i < pieces.length; i++) {
			String piece = pieces[i];
			
			for (int v = 0; v < conditions.length; v++) {
				String condition = conditions[v];
				
				for (int k = 0; k < materials.length; k++) {
					String material = materials[k];
					
					Armor armor = new Armor();
					armor.name = condition + " " + material + " " + piece;
					armor.type = i;
					armor.price = ((int)Math.pow(1.4, 1 + k)) * (i + 1);
					armor.protection = 1 + (int)(Math.ceil((i + 1) * (0.5 + (((k + 1) * 2) / 2))) / 2.25);
					
					int reduc = (int)(armor.protection - (armor.protection * ((12.0 + v) / 16.0)));
					armor.protection -= reduc;
					int price_reduc = (int)(armor.price - (armor.price * ((12.0 + v) / 16.0)));
					armor.price -= price_reduc;
					
					armor.quality = k + 1;
					armor.lootable = true;
					armors.put(armor.name, armor);
				}
			}
		}
	}
	
	public void GeneratePotions() {
		// make have luck potions or strenght potiosn or something later?
		
		String[] qualities = {
			"Spoiled",
			"Cheap",
			"Weak",
			"Standard",
			"Potent",
			"Powerful",
			"Ahibian"
		};
		
		for (int i = 0; i < qualities.length; i++) {
			String quality = qualities[i];
			
			Potion health = new Potion();
			health.name = quality + " Health Potion";
			health.price = 3 + i + (int)Math.pow(2.1, i + 1);
			health.strength = 1 + (int)(Math.pow(1.75, 1.25 * (i + 1)));
			health.quality = i + 1;
			health.lootable = true;
			health.type = "Health";
			potions.put(health.name, health);
			
			Potion mana = new Potion();
			mana.name = quality + " Mana Potion";
			mana.price = 4 + (int)Math.pow(1.9, i + 1); 
			mana.strength = 15 * (i + 1);
			mana.quality = i + 1;
			mana.lootable = true;
			mana.type = "Mana";
			potions.put(mana.name, mana);
		}
	}
		
	public List<Enemy> GetEnemies(Location location) {
		List<Enemy> result = new ArrayList<Enemy>();
		
//		if (location.name.equals("")) {
//			
//		}
		
		if (location.name.equals("The Highlands")) {
			Enemy boss = new Enemy();
				boss.name = "Highlands Skeleton";
				boss.maxhealth = 10;
				boss.health = boss.maxhealth;
				boss.attack = 1;
				boss.level = 1;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "The " + boss.name + " slashes you with his sword";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
			
			Enemy enemy = new Enemy();
				enemy.name = "Rat";
				enemy.maxhealth = 4;
				enemy.health = enemy.maxhealth;
				enemy.attack = 1;
				enemy.level = 1;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The Rat nibbles at your ankles";
				enemy.run_attack_msg = "The Rat pounces onto your calf and bites you as you run away";
				enemy.escapable = true;
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Grass Wasp";
				enemy.maxhealth = 3;
				enemy.health = enemy.maxhealth;
				enemy.attack = 3;
				enemy.level = 1;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The Grass Wasp stings you";
				enemy.run_attack_msg = "The Grass Wasp stings you as you try to run away";
				enemy.escapable = true;
				enemy.weakness.put("Fire", 50);
			result.add(enemy);
		}
				
		// inner ring
		if (location.name.equals("The Goldstone Desert")) {
			Enemy boss = new Enemy();
				boss.name = "Golstone Mummy Priest";
				boss.maxhealth = 25;
				boss.health = boss.maxhealth;
				boss.attack = 2;
				boss.level = 4;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Magical";
				boss.attack_msg = "The " + boss.name + " mutters ancient incantations and steals health from you";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
				boss.resistances.put("Poison", 100);
			location.boss = boss;
		
			Enemy enemy = new Enemy();
				enemy.name = "Mummy";
				enemy.maxhealth = 15;
				enemy.health = enemy.maxhealth;
				enemy.attack = 1;
				enemy.level = 2;
				enemy.surface = false;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The Mummy hits you";
				enemy.run_attack_msg = "The Mummy grabs you as you try to run away";
				enemy.escapable = true;
				enemy.weakness.put("Fire", 75);
				enemy.resistances.put("Poison", 50);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Cactus Monster";
				enemy.maxhealth = 25;
				enemy.health = enemy.maxhealth;
				enemy.attack = 1;
				enemy.level = 2;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The Cactus Monster pricks you";
				enemy.run_attack_msg = "The Cactus Monster attacks you as you try to run away";
				enemy.escapable = true;
				enemy.weakness.put("Fire", 50);
				enemy.weakness.put("Slash", 25);
				enemy.resistances.put("Blunt", 25);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Blood Vulture";
				enemy.maxhealth = 5;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 1;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " pecks at you";
				enemy.run_attack_msg = "The " + enemy.name + " bites your neck as you try to run away";
				enemy.escapable = false;
				enemy.weakness.put("Fire", 25);
				enemy.resistances.put("Poison", 100);
			result.add(enemy);
		}
		
		if (location.name.equals("The Forest of Light")) {
			Enemy boss = new Enemy();
				boss.name = "Corrupted Wizard";
				boss.maxhealth = 25;
				boss.health = boss.maxhealth;
				boss.attack = 3;
				boss.level = 4;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Magical";
				boss.attack_msg = "The " + boss.name + " shoots dark magical energy at you";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
				boss.resistances.put("Fire", 25);
				boss.resistances.put("Shock", 25);
			location.boss = boss;
			
			Enemy enemy = new Enemy();
				enemy.name = "Magical Orb";
				enemy.maxhealth = 10;
				enemy.health = enemy.maxhealth;
				enemy.attack = 3;
				enemy.level = 2;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Magical";
				enemy.attack_msg = "The " + enemy.name + " electrocutes you";
				enemy.run_attack_msg = "The " + enemy.name + " chases you, electrocuting you in the process";
				enemy.escapable = true;
				enemy.resistances.put("Fire", 75);
				enemy.resistances.put("Poison", 100);
				enemy.weakness.put("Shock", 50);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Tree Stalker";
				enemy.maxhealth = 5;
				enemy.health = enemy.maxhealth;
				enemy.attack = 5;
				enemy.level = 3;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " stabs you with a serrated knife";
				enemy.run_attack_msg = "You escape the " + enemy.name + ". But a few moments later it leaps out of a tree and slashes your arm";
				enemy.escapable = false;
				enemy.weakness.put("Fire", 100);
				enemy.resistances.put("Blunt", 25);
			result.add(enemy);
				
			enemy = new Enemy();
				enemy.name = "Giant Mantis";
				enemy.maxhealth = 20;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 2;
				enemy.surface = false;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " slashes you with its arms";
				enemy.run_attack_msg = "The " + enemy.name + " slashes your leg as you try to run away";
				enemy.escapable = true;
				enemy.resistances.put("Poison", 50);
				enemy.weakness.put("Fire", 25);
				enemy.weakness.put("Slash", 25);
			result.add(enemy);
		}
			
		if (location.name.equals("The Grasslands")) {
			Enemy boss = new Enemy();
				boss.name = "Ancient Cheetah";
				boss.maxhealth = 15;
				boss.health = boss.maxhealth;
				boss.attack = 4;
				boss.level = 4;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "The " + boss.name + " pounces at you, aiming for your neck";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
	
			Enemy enemy = new Enemy();
				enemy.name = "Root Guardian";
				enemy.maxhealth = 30;
				enemy.health = enemy.maxhealth;
				enemy.attack = 1;
				enemy.level = 2;
				enemy.surface = false;
				enemy.dungeon = true;
				enemy.damage_type = "Magical";
				enemy.attack_msg = "The " + enemy.name + " shoots green orbs at you";
				enemy.run_attack_msg = "The " + enemy.name + " slices your leg as you try to run away";
				enemy.escapable = true;
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Giant Rat";
				enemy.maxhealth = 10;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 2;
				enemy.surface = false;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " gnaws at your ankles";
				enemy.run_attack_msg = "The " + enemy.name + " leaps onto your back and bites you as you try to flee";
				enemy.escapable = true;
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Carnivorous Hawk";
				enemy.maxhealth = 5;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 1;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " claws at you with its talons";
				enemy.run_attack_msg = "The " + enemy.name + " bites you as you try running away";
				enemy.escapable = false;
				enemy.weakness.put("Fire", 50);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Uprooted Tree";
				enemy.maxhealth = 40;
				enemy.health = enemy.maxhealth;
				enemy.attack = 1;
				enemy.level = 3;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " hits you with its branches";
				enemy.run_attack_msg = "The " + enemy.name + " slices you with its branches as you try to run away";
				enemy.escapable = true;
				enemy.weakness.put("Fire", 50);
			result.add(enemy);
		}
		
		if (location.name.equals("Shimmerleaf Forest")) {
			Enemy boss = new Enemy();
				boss.name = "Shimmerleaf Queen";
				boss.maxhealth = 35;
				boss.health = boss.maxhealth;
				boss.attack = 2;
				boss.level = 4;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "The " + boss.name + " shoots razor sharp leaves at you";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
			
			Enemy enemy = new Enemy();
				enemy.name = "Shimmerleaf Warrior";
				enemy.maxhealth = 10;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 3;
				enemy.surface = false;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " slices you with its sword";
				enemy.run_attack_msg = "The " + enemy.name + " slashes your leg as you try to run away";
				enemy.escapable = true;
				enemy.weakness.put("Shock", 50);
				enemy.resistances.put("Poison", 25);
				enemy.resistances.put("Fire", 25);
				enemy.resistances.put("Slash", 75);
				enemy.resistances.put("Blunt", 50);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Shimmerleaf Patrol";
				enemy.maxhealth = 10;
				enemy.health = enemy.maxhealth;
				enemy.attack = 1;
				enemy.level = 2;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " stabs you with its dagger";
				enemy.run_attack_msg = "The " + enemy.name + " throws its dagger into your back when you try to flee";
				enemy.escapable = true;
				enemy.weakness.put("Shock", 50);
				enemy.resistances.put("Poison", 25);
				enemy.resistances.put("Slash", 25);
			result.add(enemy);
		}
		
		if (location.name.equals("Gravel Lake")) {
			Enemy boss = new Enemy();
				boss.name = "Living Statue";
				boss.maxhealth = 50;
				boss.health = boss.maxhealth;
				boss.attack = 1;
				boss.level = 4;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "The " + boss.name + " punches you";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
				boss.weakness.put("Blunt", 50);
				boss.resistances.put("Slash", 50);
				boss.resistances.put("Fire", 50);
				boss.resistances.put("Shock", 50);
				boss.resistances.put("Poison", 100);
			location.boss = boss;
		
			Enemy enemy = new Enemy();
				enemy.name = "Fish Bandit";
				enemy.maxhealth = 15;
				enemy.health = enemy.maxhealth;
				enemy.attack = 3;
				enemy.level = 2;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " stabs you with a spear";
				enemy.run_attack_msg = "The " + enemy.name + " throws its spear into your back as you try to run away";
				enemy.escapable = true;
				enemy.resistances.put("Fire", 50);
			result.add(enemy);
						
			enemy = new Enemy();
				enemy.name = "Gravel Crab";
				enemy.maxhealth = 5;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 1;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " pinches you with its stone claws";
				enemy.run_attack_msg = "The " + enemy.name + " pinches your leg when you try to run away";
				enemy.escapable = true;
				enemy.weakness.put("Blunt", 50);
				enemy.resistances.put("Slash", 75);
				enemy.resistances.put("Fire", 90);
				enemy.resistances.put("Shock", 90);
				enemy.resistances.put("Poison", 100);
			result.add(enemy);
		}
		
		if (location.name.equals("The Wolfpack Tundra")) {
			Enemy boss = new Enemy();
				boss.name = "Alpha Bloodwolf";
				boss.maxhealth = 15;
				boss.health = boss.maxhealth;
				boss.attack = 5;
				boss.level = 4;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "The " + boss.name + " bites you";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
				boss.weakness.put("Fire", 50);
			location.boss = boss;
			
			Enemy enemy = new Enemy();
				enemy.name = "Bloodwolf";
				enemy.maxhealth = 10;
				enemy.health = enemy.maxhealth;
				enemy.attack = 3;
				enemy.level = 2;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " bites you";
				enemy.run_attack_msg = "The " + enemy.name + " chases you and pounces on you as you try to run away";
				enemy.escapable = false;
				enemy.weakness.put("Fire", 50);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Werewolf Hunter";
				enemy.maxhealth = 25;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 3;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " fires his crossbow, piercing you with a silver tipped bolt";
				enemy.run_attack_msg = "The " + enemy.name + " shoots you in the back as you try to flee";
				enemy.escapable = true;
				enemy.resistances.put("Slash", 40);
				enemy.resistances.put("Blunt", 30);
			result.add(enemy);
		}
		
		if (location.name.equals("The Hills of Wind")) {
			Enemy boss = new Enemy();
				boss.name = "Lord of Wind";
				boss.maxhealth = 40;
				boss.health = boss.maxhealth;
				boss.attack = 1;
				boss.level = 4;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Magical";
				boss.attack_msg = "The " + boss.name + " launches you across the room with a mighty gust of wind";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
			
			Enemy enemy = new Enemy();
				enemy.name = "Horserat";
				enemy.maxhealth = 15;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 2;
				enemy.surface = false;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " bites your leg";
				enemy.run_attack_msg = "The " + enemy.name + " scratches your calf as you run away";
				enemy.escapable = true;
				enemy.weakness.put("Fire", 25);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Deer";
				enemy.maxhealth = 10;
				enemy.health = enemy.maxhealth;
				enemy.attack = 1;
				enemy.level = 1;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " gets aggressive and charges into you";
				enemy.run_attack_msg = "The " + enemy.name + " shoots a fireball out of its mouth as you try to run away";
				enemy.escapable = false;
				enemy.weakness.put("Poison", 1000);
				enemy.weakness.put("Fire", 1000);
				enemy.weakness.put("Shock", 1000);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Goblin";
				enemy.maxhealth = 15;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 2;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " shoots you with a bow";
				enemy.run_attack_msg = "The " + enemy.name + " shoots you in the back as you try to flee";
				enemy.escapable = true;
				enemy.resistances.put("Poison", 100);
				enemy.weakness.put("Shock", 50);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Snake";
				enemy.maxhealth = 5;
				enemy.health = enemy.maxhealth;
				enemy.attack = 5;
				enemy.level = 3;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " bites you, injecting devastating venom";
				enemy.run_attack_msg = "The " + enemy.name + " bites your ankle as you try to run away, injecting devastating venom";
				enemy.escapable = true;
			result.add(enemy);
		}
		
		if (location.name.equals("Brown-Rock Canyon")) {
			Enemy boss = new Enemy();
				boss.name = "Brown-Rock Necromancer";
				boss.maxhealth = 40;
				boss.health = boss.maxhealth;
				boss.attack = 2;
				boss.level = 4;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Magical";
				boss.attack_msg = "The " + boss.name + " shoots a fireball at you.";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
			
			Enemy enemy = new Enemy();
				enemy.name = "Spearman";
				enemy.maxhealth = 20;
				enemy.health = enemy.maxhealth;
				enemy.attack = 3;
				enemy.level = 3;
				enemy.surface = false;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " stabs you with its spear";
				enemy.run_attack_msg = "The " + enemy.name + " throws its spear into your leg as you try to run away";
				enemy.escapable = true;
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Armored Swordman";
				enemy.maxhealth = 30;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 3;
				enemy.surface = false;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " slashes you with its shortsword";
				enemy.run_attack_msg = "The " + enemy.name + " stabs you as you try to run away";
				enemy.escapable = true;
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Coyote";
				enemy.maxhealth = 10;
				enemy.health = enemy.maxhealth;
				enemy.attack = 2;
				enemy.level = 1;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " bites you";
				enemy.run_attack_msg = "The " + enemy.name + " bites your leg as you try to flee";
				enemy.escapable = true;
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Cougar";
				enemy.maxhealth = 10;
				enemy.health = enemy.maxhealth;
				enemy.attack = 4;
				enemy.level = 3;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " mauls you";
				enemy.run_attack_msg = "The " + enemy.name + " pounces you as you try to run away";
				enemy.escapable = true;
			result.add(enemy);
		}
		
		/**
		  Outer Ring
		*/
		
		if (location.name.equals("The Sleeper Mountains")) {
			Enemy boss = new Enemy();
				boss.name = "Lord of Dreams";
				boss.maxhealth = 100;
				boss.health = boss.maxhealth;
				boss.attack = 10;
				boss.level = 9;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Magical";
				boss.attack_msg = "The " + boss.name + " casts a devastating fire spell that weaknes you";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
				boss.resistances.put("Slash", 25);
				boss.resistances.put("Blunt", 25);
				boss.resistances.put("Fire", 50);
				boss.resistances.put("Poison", 50);
			location.boss = boss;
			
			Enemy enemy = new Enemy();
				enemy.name = "Sleeper";
				enemy.maxhealth = 50;
				enemy.health = enemy.maxhealth;
				enemy.attack = 5;
				enemy.level = 6;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " punches and claws at you";
				enemy.run_attack_msg = "The " + enemy.name + " grabs you as you try to run away";
				enemy.escapable = true;
				enemy.resistances.put("Fire", 25);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Armed Sleeper";
				enemy.maxhealth = 50;
				enemy.health = enemy.maxhealth;
				enemy.attack = 7;
				enemy.level = 7;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " hits you with a club";
				enemy.run_attack_msg = "The " + enemy.name + " grabs you and hits you as you try to run away";
				enemy.escapable = true;
				enemy.resistances.put("Fire", 25);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Dream Servant";
				enemy.maxhealth = 50;
				enemy.health = enemy.maxhealth;
				enemy.attack = 10;
				enemy.level = 8;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Magical";
				enemy.attack_msg = "The " + enemy.name + " shoots lightning at you";
				enemy.run_attack_msg = "The " + enemy.name + " blasts your legs with lightning as you try to run away";
				enemy.escapable = true;
				enemy.resistances.put("Fire", 50);
			result.add(enemy);
		}
		
		if (location.name.equals("The Ashlands")) {
			Enemy boss = new Enemy();
				boss.name = "Ashlord";
				boss.maxhealth = 100;
				boss.health = boss.maxhealth;
				boss.attack = 15;
				boss.level = 9;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Magical";
				boss.attack_msg = "The " + boss.name + " casts a caustic spell of fire and poison at you";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
				boss.resistances.put("Fire", 100);
			location.boss = boss;
			
			Enemy enemy = new Enemy();
				enemy.name = "Ash Mage";
				enemy.maxhealth = 50;
				enemy.health = enemy.maxhealth;
				enemy.attack = 10;
				enemy.level = 8;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Magical";
				enemy.attack_msg = "The " + enemy.name + " shoots lightning at you";
				enemy.run_attack_msg = "The " + enemy.name + " shoots your legs with lightning as you try to flee";
				enemy.escapable = true;
				enemy.resistances.put("Fire", 100);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Ash Zombie";
				enemy.maxhealth = 75;
				enemy.health = enemy.maxhealth;
				enemy.attack = 5;
				enemy.level = 7;
				enemy.surface = true;
				enemy.dungeon = true;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " claws at you";
				enemy.run_attack_msg = "The " + enemy.name + " grabs you as you try to run away";
				enemy.escapable = true;
				enemy.resistances.put("Fire", 100);
			result.add(enemy);
			
			enemy = new Enemy();
				enemy.name = "Ash Raptor";
				enemy.maxhealth = 30;
				enemy.health = enemy.maxhealth;
				enemy.attack = 12;
				enemy.level = 6;
				enemy.surface = true;
				enemy.dungeon = false;
				enemy.damage_type = "Physical";
				enemy.attack_msg = "The " + enemy.name + " bites your arm";
				enemy.run_attack_msg = "The " + enemy.name + " bites your legs as you try to run away";
				enemy.escapable = false;
				enemy.resistances.put("Fire", 50);
			result.add(enemy);
		}
		
		if (location.name.equals("The Ezonem Graveyard")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("Viper Island")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Volcano")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Outland Cliffs")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Ahibian Desertlands")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("Silverwood Forest")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Jungle of Flies")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Swamplands")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Abyssal Caves")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Crystal Caverns")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Sea of Glass")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Frozen Lake")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Junkyard")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		if (location.name.equals("The Valley of Souls")) {
			Enemy boss = new Enemy();
				boss.name = "";
				boss.maxhealth = 313210;
				boss.health = boss.maxhealth;
				boss.attack = 0;
				boss.level = 400;
				boss.surface = false;
				boss.dungeon = false;
				boss.damage_type = "Physical";
				boss.attack_msg = "";
				boss.run_attack_msg = "\"You can't esacpe me fool.\"";
				boss.escapable = false;
			location.boss = boss;
		}
		
		return result;
	}	
	
	public void GenerateItems() {
		// all of these are unique
		Item cheap_ring = new Item();
		cheap_ring.name = "Stolen Ring of Night Vision";
		cheap_ring.equippable = true;
		cheap_ring.price = 5;
		cheap_ring.logic = logic;
		items.put(cheap_ring.name, cheap_ring);
		
		Item map = new Item();
		map.name = "Map";
		map.equippable = false;
		map.price = 5;
		map.logic = logic;
		items.put(map.name, map);
		
		Item item = new Item();
		item.name = "Mysterious Tome";
		item.equippable = false;
		item.price = 25;
		item.logic = logic;
		items.put(item.name, item);
		
		item = new Item();
		item.name = "Mysterious Cube";
		item.equippable = false;
		item.price = 25;
		item.logic = logic;
		items.put(item.name, item);
		
		item = new Item();
		item.name = "Amulet of Persuasion";
		item.equippable = true;
		item.price = 15;
		item.logic = logic;
		items.put(item.name, item);
		
		item = new Item();
		item.name = "Goat Horn Necklace";
		item.equippable = true;
		item.price = 25;
		item.logic = logic;
		items.put(item.name, item);
	}
	
	public String highlands_a = "I've heard a rumor that the guards were trying to throw"
			+ "\n" + "another prisoner into the dungeon. But, they were not able to re-open"
			+ "\n" + "the door as they always have in the past. It seems to be sealed shut"
			+ "\n" + "permanently. There's been talk that one of the prisoners may have"
			+ "\n" + "stolen the Rune Key from the dungeon."
			+ "\n" + "As much as I like the Highlands, we've been trapped here for decades"
			+ "\n" + "I would love to explore the neighbouring areas sometime, it gets"
			+ "\n" + "rather boring around here. I didn't even think the Rune Key was"
			+ "\n" + "real, I would be amazed if this rumor is true. Exciting times indeed.";
	
	public String highlands_b = "The legend says that centuries ago, a crazed Lich tried to"
			+ "\n" + "take over the world by creating an infectious magical substance we"
			+ "\n" + "call, \"The Blight\". The elder wizards could not find a way to stop"
			+ "\n" + "The Blight, so instead they created a magical forcefield that conatined"
			+ "\n" + "The Blight in an area we now call, \"The Outerlands\". The Blight"
			+ "\n" + "surrounds us from all sides, but the forcefields keep us safe."
			+ "\n" + "The Lich likely still lives in his castle somewhere out in The"
			+ "\n" + "Outerlands. The Legend also says that the only things that can"
			+ "\n" + "deactivate the forcefields are ancient relics called, \"Rune Keys\"."
			+ "\n" + "Personally, I feel that having three forcefields is a bit excessive."
			+ "\n" + "I would like to leave The Highlands sometime, but I can't because the"
			+ "\n" + "forcefield blocks the way. Our people have been trapped in here for"
			+ "\n" + "centuries. If the rumors are true, and the Rune Key has been recovered."
			+ "\n" + "Then that means we can finally get out of here."
			+ "\n" + "I just hope that whoever found this Rune Key never deactivates the outer-most"
			+ "\n" + "forcefield. Otherwise, we will all be doomed.";
	
	public String highlands_c = "Dear lord, it IS real!"
			+ "\n" + "You better keep that thing hidden. If the guards see it you're a dead man."
			+ "\n" + "You can finally get us out of here. Go, try to deactivate the forcefield."
			+ "\n" + "Just remember, if you decide to go any further, never deactivate the"
			+ "\n" + "final forcefield that separates us from The Outerlands."
			+ "\n" + "Otherwise, you will have doomed us all.";
	
	
}