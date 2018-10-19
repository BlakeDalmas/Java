import java.util.*;

public class Player {
	public String name;
	public int health;
	public int maxhealth;
	public int mana;
	public int maxmana;
	public int xp;
	public int next_lvl_xp;
	public int level;
	public int keystage;
	public int armor_value;
	public int gold;
	public String attribute = "None";
	public HashMap<String, ArrayList> inventory; 
	public HashMap<String, Integer> skills;
	
	public Weapon eq_weapon;
	public Armor[] eq_armor;
	public Spell eq_spell;
	public Item eq_item;
	
	public List<Location> locations_with_shops;
	
	public boolean in_dungeon;
	
	public Logic logic;
	public Data data;
	
	public Player(String name, HashMap<String, Integer> skills, String attribute, Logic logic) {	
		this.logic = logic;
		this.data = logic.data;
		this.name = name;
		this.skills = skills;
		this.attribute = attribute;
		this.maxhealth = 4 + (int)Math.ceil(skills.get("Endurance") / 2);
		this.health = maxhealth;
		this.maxmana = (int)(skills.get("Intelligence") * 2.5);
		this.mana = maxmana;
		this.xp = 0;
		this.next_lvl_xp = 10;
		this.level = 0;
		this.keystage = 0;
		this.in_dungeon = false;
		this.armor_value = 0;
		this.gold = 0;
		
		this.locations_with_shops = new ArrayList<Location>();
		
		this.inventory = new HashMap<>();
		inventory.put("Weapons", new ArrayList<Weapon>());
		inventory.put("Armor", new ArrayList<Armor>());
		inventory.put("Spells", new ArrayList<Spell>());
		inventory.put("Potions", new ArrayList<Potion>());
		inventory.put("Items", new ArrayList<Item>());
		inventory.get("Weapons").add(data.weapons.get("Stick"));
		
		eq_weapon = data.weapons.get("Fists");
		eq_spell = data.spells.get("Flare");
		
		Armor[] temp = {
			data.armors.get("None"),
			data.armors.get("None"),
			data.armors.get("None"),
			data.armors.get("None"),
			data.armors.get("None")
		};
		
		eq_armor = temp;
		eq_item = data.items.get("Stolen Ring of Night Vision");
	}
	
	public void RefreshShops(int level) {
		for (Location place : locations_with_shops) {
			int local_level = (level + 1) - place.lock;
			if (local_level < 0) local_level = 0;
						
			// Weapons
			int weapon_base = (int)(Math.pow(Math.sqrt(local_level + 2), 3.25));
			weapon_base = logic.LuckRoll(weapon_base, skills.get("Luck"));
						
			Collection<Weapon> wep_values = data.weapons.values();
			ArrayList<Weapon> wep_list = new ArrayList<Weapon>(wep_values);
			ArrayList<Weapon> valid_weps = new ArrayList<Weapon>();
					
			for (Weapon wep : wep_list) {
				if (wep.lootable && wep.damage <= weapon_base && wep.damage > weapon_base - 15) valid_weps.add(wep);
			}
			
			Collections.shuffle(valid_weps);
			
			int wep_max = 3 + (int)(Math.random() * 4);
			for (int i = 0; i < wep_max; i++) {
				if (valid_weps.size() > i) {
					Weapon prize = valid_weps.get(i);
					place.shop_weapons.add(prize);
				}
			}
			
			// Armor
			int armor_base = local_level + 1;
			
			boolean lost = false;
			int tracker = 10;
			
			while (!lost) {
				if (Math.random() * tracker <= skills.get("Luck")) {
					armor_base++;
					tracker *= 10;
				} else lost = true;
			}
			
			Collection<Armor> armor_values = data.armors.values();
			ArrayList<Armor> armor_list = new ArrayList<Armor>(armor_values);
			ArrayList<Armor> valid_armor = new ArrayList<Armor>();
			
			for (Armor armor : armor_list) {
				if (armor.lootable && armor.quality <= armor_base && armor.quality > (armor_base - 2)) valid_armor.add(armor);
			}
			
			Collections.shuffle(valid_armor);
			
			int i_max = 3 + (int)(Math.random() * 4);
			for (int i = 0; i < i_max; i++) {
				if (valid_armor.size() > i) {
					Armor prize = valid_armor.get(i);
					place.shop_armor.add(prize);
				}
			}
			
			// Potions
			int base_potion_quality = 1 + (int)(local_level / 2);
			
			Collection<Potion> potion_values = data.potions.values();
			ArrayList<Potion> potion_list = new ArrayList<Potion>(potion_values);
			ArrayList<Potion> valid_potion = new ArrayList<Potion>();
			
			for (Potion potion : potion_list) {
				if (potion.lootable && potion.quality <= base_potion_quality && potion.quality > (base_potion_quality - 1)) valid_potion.add(potion);
			}
			
			Collections.shuffle(potion_list);
			
			i_max = 3 + (int)(Math.random() * 4);
			for (int i = 0; i < i_max; i++) {
				if (valid_potion.size() > i) {
					Potion prize = valid_potion.get(i);
					place.shop_potions.add(prize);
				}
			}
			
			// Spells
			Collection<Spell> spell_values = data.spells.values();
			ArrayList<Spell> spell_list = new ArrayList<Spell>(spell_values);
			ArrayList<Spell> valid_spell = new ArrayList<Spell>();
			
			for (Spell spell : spell_list) {
				if (spell.cost < maxmana && !inventory.get("Spells").contains(spell)) {
					if (level <= 3) {
						if (spell.cost < ((level + 1) * 5)) valid_spell.add(spell);	
					} else {
						valid_spell.add(spell);	
					}
				}
			}
			
			Collections.shuffle(spell_list);
			
			i_max = 5 + (int)(Math.random() * 5);
			for (int i = 0; i < i_max; i++) {
				if (valid_spell.size() > i) {
					Spell prize = valid_spell.get(i);
					place.shop_spells.add(prize);
				}
			}
		}
	}
	
	public void LevelUp() {
		while (xp >= next_lvl_xp) {
			level++;
			int lvl_adj = level + 3;
			next_lvl_xp += (lvl_adj * (lvl_adj - 1));
			
			System.out.println("\nYou have leveled up to level " + level + "!");
			System.out.println("Choose a skill to increase by 1.");
			
			String[] skill_plz = {"Strength", "Endurance", "Intelligence", "Charisma", "Luck"};
			String[] translation = {"STR", "END", "INT", "CHR", "LCK"};
			for (int i = 0; i < skill_plz.length; i++) {
				String temp = translation[i];
				System.out.print(temp + ": " + skills.get(skill_plz[i]) + " ");
			}
			String[][] skill_list = {
				{"Strength", "Add +1 to Strength"},
				{"Endurance", "Add +1 to Endurance"},
				{"Intelligence", "Add +1 to Intelligence"},
				{"Charisma", "Add +1 to Charisma"},
				{"Luck", "Add +1 to Luck"}
			};
			System.out.println();
			
			int choice = logic.InputScanner(skill_list);
			
			String[] skill_thing = {"Strength", "Endurance", "Intelligence", "Charisma", "Luck"};
			
			skills.put(skill_thing[choice], skills.get(skill_thing[choice]) + 1);
			
			System.out.println("Your " + skill_thing[choice] + " has been increased to " + skills.get(skill_thing[choice]));
		
			maxhealth += 1 + (int)Math.ceil(skills.get("Endurance") / 2);
			maxmana += skills.get("Intelligence");
			
			if (attribute.equalsIgnoreCase("Warrior")) {
				maxmana = 0;
			}
			
			mana = maxmana;
			health = maxhealth;
		}
		
		RefreshShops(level);
	}
	
	public void RegenMana(int mana_regen) {
		mana += mana_regen;
		if (mana > maxmana) mana = maxmana;
	}
	
	public void RegenHealth(int toheal) {
		health += toheal;
		if (health > maxhealth) health = maxhealth;
	}
	
	public boolean FightEnemy(Enemy enemy, boolean escapable) {
		boolean done = false;
		boolean killed_enemy = true;
				
		int base_health = enemy.maxhealth;
		int base_damage = enemy.attack;
		double scale = 1.0;
		
		if (level > 1) scale = 1.0 + ((double)level / 5.0);
		
		enemy.maxhealth = (int)Math.ceil((double)enemy.maxhealth * scale);
		enemy.attack = (int)Math.ceil((double)enemy.attack * scale);
		enemy.health = enemy.maxhealth;
		
		while (!done) {
			PrintHealth();
			enemy.PrintHealth();
								
			if (enemy.spell_effects.size() > 0) {
				for (int i = 0; i < enemy.spell_effects.size(); i++) {
					SpellEffect effect = enemy.spell_effects.get(i);
					effect.duration = effect.duration - 1;
										
					System.out.println("The " + enemy.name + " took additional damage from " + effect.spell_type + "!");
					enemy.TakeDamage(effect.damage, effect.spell_type, this, false);
					
					if (effect.duration <= 0) {
						enemy.spell_effects.remove(i);
					}
				}
				
				enemy.PrintHealth();
			}
			
			System.out.println("\nWhat will you do?");
			System.out.println("1- Attack (" + eq_weapon.name + ")");
			System.out.println("2- Cast Spell (" + eq_spell.name + ")");
			System.out.println("3- Run");
			System.out.println("4- Inventory");
			
			int choice = logic.NumberScanner(1, 4);
			
			double perc = Math.pow(2.5, 1.0 + Math.sqrt(skills.get("Luck") / 1.5));
			boolean dodge = (perc > Math.random() * 100);
			
			switch (choice) {
				case 1:
					System.out.println(eq_weapon.damage_msg + enemy.name + " with your " + eq_weapon.name);
					enemy.TakeDamage(eq_weapon.GetAttack(this, enemy), eq_weapon.damage_type, this, true);
					System.out.println();
					
					if (!enemy.alive) {
						done = true;
					}
					else {
						if (dodge) {
							System.out.println("You dodge the enemy's attack!");
						} else enemy.Attack(this, false);
					}
				break;
				
				case 2:
					if (eq_spell.spell_type.equals("Health")) System.out.println(eq_spell.cast_message);
					else System.out.println(eq_spell.cast_message + enemy.name + " with " + eq_spell.name);
					PrintMana();
					
					if (mana >= eq_spell.cost) {
						mana -= eq_spell.cost;
						
						if (eq_spell.duration > 1) {
							for (int i = 0; i < enemy.spell_effects.size(); i++) {
								SpellEffect temp = enemy.spell_effects.get(i);
								
								if (temp.name.equalsIgnoreCase(eq_spell.name)) {
									enemy.spell_effects.remove(i);
								}
							}
							
							enemy.spell_effects.add(new SpellEffect(eq_spell.name, eq_spell.spell_type, eq_spell.damage, eq_spell.duration));
						}
						
						enemy.TakeDamage(eq_spell.damage, eq_spell.spell_type, this, true);
					} else {
						System.out.println("You failed to cast the spell!");
					}
					
					if (!enemy.alive) {
						done = true;
					}
					else {
						if (dodge) {
							System.out.println("You dodge the enemy's attack!");
						} else enemy.Attack(this, false);
					}
				break;
				
				case 3:
					if (escapable) {
						if (enemy.escapable && (IsWearing("Boots of Traveling") || (skills.get("Luck") > (int)(Math.random() * 10)))) {
							System.out.println("You escape the " + enemy.name + " and run away.");
							done = true;
							killed_enemy = false;
						} else {
							System.out.println("The " + enemy.name + " attacks you as you run away.");
							enemy.Attack(this, true);
						}
					} else {
						System.out.println("There's nowhere to run!");
					}
				break;
				
				case 4:
					Inventory(false);
				break;
			}
		}

		enemy.maxhealth = base_health;
		enemy.attack = base_damage;
		enemy.ResetStats();
		
		return killed_enemy;
	}
	
	public boolean IsWearing(String name) {
		for (int i = 0; i < eq_armor.length; i++) {
			if (eq_armor[i].name.equalsIgnoreCase(name)) return true;
		}
		
		return false;
	}
	
	public void PrintHealth() {
		System.out.println(name + " health: " + health + " / " + maxhealth);
	}
	
	public void PrintMana() {
		System.out.println(name + " mana: " + mana + " / " + maxmana);
	}
	
	public void TakeDamage(int damage, String dmg_type) {
		System.out.println("You took " + damage + " damage.");
		health -= damage;
		if (health <= 0) Die();
	}
	
	public void Die() {
		PrintHealth();
		System.out.println("R.I.P");
		System.exit(0);
	}
	
	public void Inventory(boolean selling) {
		boolean done = false;
				
		while (!done) {
			if (!selling) {
				PrintStatus();
				System.out.println("What are you looking for?");
			} else {
				System.out.println(name + " gold: " + gold);
				System.out.println("What would you like to sell?");
			}
			
			String[] choices = {"Weapons", "Armor", "Potions", "Spells", "Items"};
			
			for (int i = 0; i < choices.length; i++) {
				System.out.println((i + 1) + "- " + choices[i]);
			}
			
			System.out.println((choices.length + 1) + "- Exit Inventory");
			
			int choice = logic.NumberScanner(1, choices.length + 1);
			
			if (choice == choices.length + 1) done = true;
			else {
				switch (choice) {
					case 1: WeaponInventory(selling); break;
					case 2: ArmorInventory(selling); break;
					case 3: PotionInventory(selling); break;
					case 5: ItemInventory(selling); break;
					
					case 4: 
						if (selling) System.out.println("You cannot sell spells.");
						else SpellInventory(false); 
					break;
				}
			}
		}
	}
	
	public void WeaponInventory(boolean selling) {		
		List<Weapon> weapons = inventory.get("Weapons");
		
		if (selling) System.out.println("What do you want to sell?");
		else System.out.println("What do you want to equip?");
		
		System.out.println("0- Back");
		for (int i = 0 ; i < weapons.size(); i++) {
			System.out.println((i + 1) + "- " + weapons.get(i).name + " (" + weapons.get(i).damage + ")");
		}
		
		int choice = logic.NumberScanner(0, weapons.size());
		
		if (choice > 0) {
			Weapon new_eq = weapons.get(choice - 1);
			
			if (selling) {
				int final_price = (int)Math.ceil(new_eq.price * (skills.get("Charisma") / 10));
				if (final_price >= new_eq.price) final_price = new_eq.price - 1;
				if (final_price <= 0) final_price = 1;
				
				System.out.println("Merchant: \"I can offer you " + final_price + " gold for that.\"");
				
				String[][] verify = {
					{"Yes", ""},
					{"No", ""}
				};
				
				int verify_choice = logic.InputScanner(verify);
				
				if (verify_choice == 0) {
					inventory.get("Weapons").remove(new_eq);
					gold += final_price;
					System.out.println("Merchant: \"Pleasure doing business with you!\"");
				}
			} else {
				if (attribute.equalsIgnoreCase("Wizard")) {
					System.out.println("You cannot use weapons because you have the Wizard attribute!");
				} else {
					inventory.get("Weapons").add(eq_weapon);
					inventory.get("Weapons").remove(new_eq);
					eq_weapon = new_eq;
					System.out.println(eq_weapon.name + " equipped.");
				}
			}
		}
	}
	
	public void ArmorInventory(boolean selling) {		
		List<Armor> armor = inventory.get("Armor");
		
		if (selling) System.out.println("What do you want to sell?");
		else System.out.println("What do you want to equip?");
		
		System.out.println("0- Back");
		for (int i = 0 ; i < armor.size(); i++) {
			System.out.println((i + 1) + "- " + armor.get(i).name + " (" + armor.get(i).protection + ")");
		}
		
		int choice = logic.NumberScanner(0, armor.size());
		
		if (choice > 0) {
			Armor new_eq = armor.get(choice - 1);
			
			if (selling) {
				int final_price = (int)Math.ceil(new_eq.price * (skills.get("Charisma") / 10));
				if (final_price >= new_eq.price) final_price = new_eq.price - 1;
				if (final_price <= 0) final_price = 1;
				System.out.println("Merchant: \"I can offer you " + final_price + " gold for that.\"");
				
				String[][] verify = {
					{"Yes", ""},
					{"No", ""}
				};
				
				int verify_choice = logic.InputScanner(verify);
				
				if (verify_choice == 0) {
					inventory.get("Armor").remove(new_eq);
					gold += final_price;
					System.out.println("Merchant: \"Pleasure doing business with you!\"");
				}
			} else {
				if (!eq_armor[new_eq.type].name.equalsIgnoreCase("None")) {
					eq_armor[new_eq.type].OnUnequip(this);
					inventory.get("Armor").add(eq_armor[new_eq.type]);
				}
				
				inventory.get("Armor").remove(new_eq);
				eq_armor[new_eq.type] = new_eq;
				eq_armor[new_eq.type].OnEquip(this);
				
				armor_value = 0;
				for (int i = 0; i < 5; i++) {
					armor_value += eq_armor[i].protection;
				}
				
				System.out.println(new_eq.name + " equipped.");
			}
		}
	}
	
	public void PotionInventory(boolean selling) {		
		List<Potion> potions = inventory.get("Potions");
		
		if (selling) System.out.println("What do you want to sell?");
		else System.out.println("What do you want to drink?");
		
		System.out.println("0- Back");
		for (int i = 0 ; i < potions.size(); i++) {
			System.out.println((i + 1) + "- " + potions.get(i).name + " (" + potions.get(i).strength + ")");
		}
		
		int choice = logic.NumberScanner(0, potions.size());
		
		if (choice > 0) {
			Potion drink_potion = potions.get(choice - 1);
			
			if (selling) {
				int final_price = (int)Math.ceil(drink_potion.price * (skills.get("Charisma") / 10));
				if (final_price >= drink_potion.price) final_price = drink_potion.price - 1;
				if (final_price <= 0) final_price = 1;
				System.out.println("Merchant: \"I can offer you " + final_price + " gold for that.\"");
				
				String[][] verify = {
					{"Yes", ""},
					{"No", ""}
				};
				
				int verify_choice = logic.InputScanner(verify);
				
				if (verify_choice == 0) {
					inventory.get("Potions").remove(drink_potion);
					gold += final_price;
					System.out.println("Merchant: \"Pleasure doing business with you!\"");
				}
			} else {
				inventory.get("Potions").remove(choice - 1);
				drink_potion.Drink(this);
			}
		}
	}
	
	public void SpellInventory(boolean selling) {		
		List<Spell> spells = inventory.get("Spells");
		
		System.out.println("What do you want to equip?");
		
		System.out.println("0- Back");
		for (int i = 0 ; i < spells.size(); i++) {
			System.out.println((i + 1) + "- " + spells.get(i).name + " (" + spells.get(i).damage + "x" + (spells.get(i).duration + 1) + " [" + spells.get(i).cost + "])");
		}
		
		int choice = logic.NumberScanner(0, spells.size());
		
		if (choice > 0) {
			Spell new_eq = spells.get(choice - 1);
			inventory.get("Spells").add(eq_spell);
			inventory.get("Spells").remove(new_eq);
			eq_spell = new_eq;
			System.out.println(eq_spell.name + " equipped.");
		}
	}
	
	public void ItemInventory(boolean selling) {		
		List<Item> items = inventory.get("Items");
		
		if (selling) System.out.println("What do you want to sell?");
		else System.out.println("What do you want to use?");
		
		System.out.println("0- Back");
		for (int i = 0 ; i < items.size(); i++) {
			System.out.println((i + 1) + "- " + items.get(i).name);
		}
		
		int choice = logic.NumberScanner(0, items.size());
		
		if (choice > 0) {
			Item chosen_item = items.get(choice - 1);
			
			if (selling) {
				int final_price = (int)Math.ceil(chosen_item.price * (skills.get("Charisma") / 10));
				if (final_price >= chosen_item.price) final_price = chosen_item.price - 1;
				if (final_price <= 0) final_price = 1;
				System.out.println("Merchant: \"I can offer you " + final_price + " gold for that.\"");
				
				String[][] verify = {
					{"Yes", ""},
					{"No", ""}
				};
				
				int verify_choice = logic.InputScanner(verify);
				
				if (verify_choice == 0) {
					inventory.get("Items").remove(chosen_item);
					gold += final_price;
					System.out.println("Merchant: \"Pleasure doing business with you!\"");
				}
			} else {
				if (chosen_item.equippable) {
					eq_item.OnUnequip(this);
					inventory.get("Items").add(eq_item);
					inventory.get("Items").remove(chosen_item);
					eq_item = chosen_item;
					eq_item.OnEquip(this);
					
					System.out.println(eq_item.name + " equipped.");
				} else {
					chosen_item.Use(this);
				}
			}
		}
	}
		
	public void PrintStatus() {
		String[] skill_list = {"Strength", "Endurance", "Intelligence", "Charisma", "Luck"};
		String[] translation = {"STR", "END", "INT", "CHR", "LCK"};
		for (int i = 0; i < skill_list.length; i++) {
			String temp = translation[i];
			System.out.print(temp + ": " + skills.get(skill_list[i]) + " ");
		}
		System.out.println();
				
		System.out.println(name + " (Level " + level + ")");
		System.out.println("Health: " + health + " / " + maxhealth);
		System.out.println("Mana: " + mana + " / " + maxmana);
		System.out.println("XP: " + xp + " / " + next_lvl_xp);
		System.out.println("Gold: " + gold);
		System.out.println("Attribute: " + attribute);
		System.out.println();
		System.out.println("Weapon: " + eq_weapon.toString() + " (" + eq_weapon.damage + ")");
		System.out.println("Spell: " + eq_spell.toString() + " (" + eq_spell.damage + "x" + (eq_spell.duration + 1) + " [" + eq_spell.cost + "])");
		System.out.println("Item: " + eq_item.toString());
		System.out.println("Armor Rating: " + armor_value);
		System.out.print("Helmet: " + eq_armor[2].toString() + " (" + eq_armor[2].protection + ") ");
		System.out.print("Cuirass: " + eq_armor[4].toString() + " (" + eq_armor[4].protection + ") ");
		System.out.print("Gloves: " + eq_armor[0].toString() + " (" + eq_armor[0].protection + ") ");
		System.out.print("Greaves: " + eq_armor[3].toString() + " (" + eq_armor[3].protection + ") ");
		System.out.print("Boots: " + eq_armor[1].toString() + " (" + eq_armor[1].protection + ")");
		System.out.println("\n");
	}
}
