import java.util.*;

public class Logic {

	Player player;
	Location[][] map = new Location[9][9];
	Location outerlands;
	Data data;
	
	public String[][] location_correlations = new String[12][2];
	
	public Logic() {
		String[][] correlations = {
			{"The Goldstone Desert", "The Forest of Light"},
			{"The Grasslands", "Shimmerleaf Forest"},
			{"Gravel Lake", "The Wolfpack Tundra"},
			{"The Hills of Wind", "Brown-Rock Canyon"},
			{"The Sleeper Mountains", "The Ashlands"},
			{"The Ezonem Graveyard", "Viper Island"},
			{"The Volcano", "The Outland Cliffs"},
			{"The Ahibian Desertlands", "Silverwood Forest"},
			{"The Jungle of Flies", "The Swamplands"},
			{"The Abyssal Caves", "The Crystal Caverns"},
			{"The Sea of Glass", "The Frozen Lake"},
			{"The Junkyard", "The Valley of Souls"}
		};
		
		for (int i = 0; i < correlations.length; i++) {
			String first = correlations[i][0];
			String second = correlations[i][1];
			
			if (Math.random() > 0.5) {
				String temp = first;
				first = second;
				second = temp;
			}
			
			location_correlations[i][0] = first;
			location_correlations[i][1] = second;
		}
	}
		
	public void StartGame() {
		String intro_a = 
			"You climb through the window of the house, searching for something to steal."
			+ "\n" + "You aren't proud of what you do, but considering the money you make"
			+ "\n" + "from it, it's worth the risk."
			+ "\n" + "As you head to the door you hear rapid footsteps outside."
			+ "\n" + "\"Great.\" You think to yourself, \"This is just what I need right now.\"."
			+ "\n" + "\"HALT!\". The guards shout as they burst through the door."
			+ "\n" + "\"Who are you?! You better start explaining yourself!\".";
		System.out.println(intro_a);
		
		CreateNewPlayer();
		
		if (player.skills.get("Luck") >= 10 && (int)(Math.random() * 4) == 2) {
			String luck_msg = "You say to the guards."
				+ "\n" + "\"What is the meaning of this? Why have you broken down my door?\""
				+ "\n" + "The guards, seemingly convinced by your story respond."
				+ "\n" + "\"Oh uh, we're sorry " + player.name + ", we saw commotion"
				+ "\n" + "and thought you were a thief. Truly, we apologize.\""
				+ "\n" + "You watch as the guards file out of the house. You think to yourself."
				+ "\n" + "\"Wow, didn't think that was going to work.\""
				+ "\n" + "After the guards are gone you leave the house and walk home."
				+ "\n" + "You later find out that the owner of the house has the same name as you."
				+ "\n" + "\"Well, that was lucky.\"";
			System.out.println(luck_msg);
			System.out.println();
			System.out.println("You win!");
			System.exit(0);
		}
		
		String intro_msg = "The guard responds, \"Likely story. Do you think we're dumb?"
				+ "\n" + "We know who you truly are. You're coming with us.\""
				+ "\n" + "The Highland City guards drag you to the dungeon and throw you in."
				+ "\n" + "The door slams shut behind you."
				+ "\n" + "On the other side of the entrance, you hear them conversing."
				+ "\n" + "\"Well, he's rat food now.\""
				+ "\n" + "\"Rascal thieves.\""
				+ "\n" + "\"Sometimes I wonder, what if one of these prisoners find the Rune Key in there?"
				+ "\n" + "\"Impossible. He won't survive in there for 5 minutes.\""
				+ "\n" + "\"Stumbling around in the dark, unarmed, as the rats gnaw on his ankles.\""
				+ "\n" + "\"Besides, I doubt the rune key is even in there, or that it's even real at all.\""
				+ "\n" + "\"It's probably just a myth, or the king already has it.\""
				+ "\n" + "You hear their voices trail off as they walk away."
				+ "\n" + "It's a good thing the guards didn't notice the night vision ring you slipped"
				+ "\n" + "on your finger. You might have a chance here afterall.";
		System.out.println(intro_msg);
		
		String stick_msg = "\nYou look down and see a stick. You figure, \"Well, better than nothing\".";
		System.out.println(stick_msg);
		
		if (player.skills.get("Luck") <= 0) {
			System.out.println("As you bend over to pick up the stick you fall over.");
			System.out.println("You land on the stick and die.");
			System.out.println("If only you weren't so unlucky.");
			player.health = 0;
			player.Die();
		}
		
		if (player.skills.get("Strength") <= 0) {
			System.out.println("You try to pick up the stick but it's too heavy.");
			System.out.println("You try getting up instead, but you realize that your legs are too");
			System.out.println("weak to get yourself off the ground.");
			System.out.println("As you lay in the dirt, pathetically struggling, the noise attracts the rats.");
			System.out.println("The rats slowly eat you alive.");
			System.out.println("If only you were stronger.");
			player.health = 0;
			player.Die();
		}
		
		if (player.skills.get("Endurance") <= 0) {
			System.out.println("As you look at the stick your vision begins to blur.");
			System.out.println("You realize you are injured, the guards must have broken");
			System.out.println("something when they threw you in here.");
			System.out.println("The rats eat you.");
			System.out.println("If only you had more endurance.");
			player.health = 0;
			player.Die();
		}
		
		if (player.skills.get("Intelligence") <= 0) {
			System.out.println("As you pick up the stick you feel your throat close up.");
			System.out.println("You realize that you have forgotten how to breathe again.");
			System.out.println("You die from asphyxiation as the rats eat your corpse.");
			System.out.println("If only you were more intelligent.");
			player.health = 0;
			player.Die();
		}
		
		if (player.skills.get("Charisma") <= 0) {
			System.out.println("As you go to pick it up, you hold your abdomen in pain.");
			System.out.println("\"I think one of those guards stabbed me on the way here.\"");
			System.out.println("Your vision blurs, and you fall over. You die from blood loss.");
			System.out.println("If only you weren't so uncharismatic.");
			player.health = 0;
			player.Die();
		}
		
		System.out.println("\nYou pick up the stick.");
		
		if (player.skills.get("Luck") <= (int)(Math.random() * 4)) {
			System.out.println("Ow! A splinter!");
			player.TakeDamage(1, "Stab");
		}
		
		System.out.println();
		
		Location spawnpoint = map[4][4];
		
		if (!player.attribute.equals("Debug")) TraverseDungeon(SetupDungeon(spawnpoint), spawnpoint);
		
		GameEngine(spawnpoint.cords);
	}
	
	public void CreateNewPlayer() {
		data = new Data(this);
		data.GenerateItemData();
								
		System.out.println("\nWhat is your name?");
		Scanner name_scan = new Scanner(System.in);
		String name = name_scan.nextLine();
		
		boolean done = false;
		
		int skillpoints = 18;
		HashMap<String, Integer> skills = new HashMap<String, Integer>();
		String[] skill_list = {"Strength", "Endurance", "Intelligence", "Charisma", "Luck"};
		
		for (int i = 0; i < skill_list.length; i++) {
			skills.put(skill_list[i], 0);
		}
				
		while (!done) {
			int i = 0;
			
			System.out.println("Designate your skills.");
			
			System.out.println("\nStrength: Increases your melee damage.");
			System.out.println("Endurance: Increases your max health.");
			System.out.println("Intelligence: Increases your maximum mana.");
			System.out.println("Charisma: Influences prices and dialogue.");
			System.out.println("Luck: Influences your overall luck.\n");
			
			while (skillpoints > 0) {
				System.out.println("You have " + skillpoints + " skillpoints.");
				for (int k = 0; k < skill_list.length; k++) {
					System.out.println(skill_list[k] + ": " + skills.get(skill_list[k]));
				}
				System.out.println("How many points do you want to put into " + skill_list[i] + "?");
				
				int answer = SkillScanner(skillpoints);
				skills.put(skill_list[i], answer + skills.get(skill_list[i]));
				skillpoints -= answer;
				
				i++;
				if (i == skill_list.length) i = 0;
			}
			
			System.out.println();
			for (int k = 0; k < skill_list.length; k++) {
				System.out.println(skill_list[k] + ": " + skills.get(skill_list[k]));
			}
			System.out.println("Finalize Skills?");
			System.out.println("- Yes");
			System.out.println("- No");
			System.out.println("* Type Yes or No");
			
			boolean local_done = false;
			
			while (!local_done) {
				Scanner verify_scan = new Scanner(System.in);
				String verify = verify_scan.next();
				
				if (verify.equalsIgnoreCase("yes")) {
					local_done = true;
					done = true;
				}
				else if (verify.equalsIgnoreCase("no")) {
					local_done = true;
					
					for (int k = 0; k < skill_list.length; k++) {
						skills.put(skill_list[k], 0);
						skillpoints = 18;
					}
				}	
			}
		}

		String chosen_attribute = "None";
		done = false;
		
		while (!done) {
			System.out.println("(Optional) Choose an Attribute");
			System.out.println("If you do not want an attribute, type None.");
			System.out.println("Otherwise type the name of an attribute from this list.");
			
			String[][] attributes = {
				{"Agilic",
				"You can traverse great distances with ease. Thus, encountering less enemies in the wild."},
				{"Warrior", 
				"You are completely inept at magic but start with +2 Strength and +2 Endurance."},
				{"Wizard", 
				"You start with +3 Intelligence but cannot use weapons."},
				{"Masochist", 
				"You gain health from attacking enemies. Merchants find you unsettling and charge more."},
				{"Necromage",
				"You used outlawed incantations to increase your intelligence by +5. You are being hunted by assassins."},
				{"Charming",
				"You tend to get better deals with merchants, but enemies are more aggressive towards you."},
				{"Debug", "Skips stuff."}
			};
			
			// one that increases magic but causes you to be hunted by something
			// etc
			// maybe a vampire one? or that could be an in game thing
			
			for (int i = 0; i < attributes.length; i++) {
				System.out.println(attributes[i][0] + ":");
				System.out.println(attributes[i][1]);
				System.out.println();
			}
			
			Scanner att_scan = new Scanner(System.in);
			String choice = att_scan.next();
			
			if (choice.equalsIgnoreCase("None")) done = true;
			else {
				for (int i = 0; i < attributes.length; i++) {
					if (attributes[i][0].equalsIgnoreCase(choice)) {
						chosen_attribute = attributes[i][0];
						done = true;
						break;
					}
				}
			}
		}
			
		if (chosen_attribute.equalsIgnoreCase("Warrior")) {
			skills.put("Strength", skills.get("Strength") + 2);
			skills.put("Endurance", skills.get("Endurance") + 2);
		}
		
		if (chosen_attribute.equalsIgnoreCase("Wizard")) {
			skills.put("Intelligence", skills.get("Intelligence") + 2);
		}
		
		player = new Player(name, skills, chosen_attribute, this);
		
		if (player.attribute.equalsIgnoreCase("Warrior")) {
			player.mana = 0;
			player.maxmana = 0;
		}
						
		CreateMap();
	}
	
	public void GameEngine(int[] current_loc) {
		while (true) {
			Location current_location = map[current_loc[0]][current_loc[1]];
			
//			PrintMap(map, current_loc);
						
			System.out.println("You are in " + current_location.name);
			System.out.println("\nWhat would you like to do?");
			
			String[][] options = {
				{"Village", "Head into town."},
				{"Dungeon", "Enter the nearby dungeon."},
				{"Travel", "Explore for another land."},
				{"Inventory", "Access your inventory."}
			};
			int choice = InputScanner(options) + 1;
			
			switch (choice) {
				case 1:
					current_location.EnterVillage(player);
				break;
				
				case 2:
					if (!current_location.dungeon_raided) {								
						System.out.println("As you walk inside the dungeon the door slams shut behind you.");
						TraverseDungeon(SetupDungeon(current_location), current_location);
					} else {
						System.out.println("The entrance to the dungeon seems to be sealed.");
					}
				break;
				
				case 3:
					System.out.println("Which direction do you want to go?");
					String[] nesw = {"North", "East", "South", "West"};
						
					List<Location> dirs = new ArrayList<Location>();
					
					Location edge = new Location(this, data);
					edge.name = "Edge";
					
					if (current_loc[1] + 1 < map.length) {
						dirs.add(map[current_loc[0]][current_loc[1] + 1]);
					} else dirs.add(edge);
					
					if (current_loc[0] + 1 < map.length) {
						dirs.add(map[current_loc[0] + 1][current_loc[1]]);
					} else dirs.add(edge);
					
					if (current_loc[1] - 1 >= 0) {
						dirs.add(map[current_loc[0]][current_loc[1] - 1]);
					} else dirs.add(edge);
					
					if (current_loc[0] - 1 >= 0) {
						dirs.add(map[current_loc[0] - 1][current_loc[1]]);
					} else dirs.add(edge);
					
//					if (current_loc[0] + 1 < map.length) {
//						dirs.add(map[current_loc[0] + 1][current_loc[1]]);
//					} else dirs.add(edge);
//					
//					if (current_loc[0] - 1 >= 0) {
//						dirs.add(map[current_loc[0] - 1][current_loc[1]]);
//					} else dirs.add(edge);
//					
//					if (current_loc[1] + 1 < map.length) {
//						dirs.add(map[current_loc[0]][current_loc[1] + 1]);
//					} else dirs.add(edge);
//					
//					if (current_loc[1] - 1 >= 0) {
//						dirs.add(map[current_loc[0]][current_loc[1] - 1]);
//					} else dirs.add(edge);
									
					String[][] travel_options = {
						{nesw[0], "Travel towards " + dirs.get(0).name + justathing(dirs.get(0).lock)},
						{nesw[1], "Travel towards " + dirs.get(1).name + justathing(dirs.get(1).lock)},
						{nesw[2], "Travel towards " + dirs.get(2).name + justathing(dirs.get(2).lock)},
						{nesw[3], "Travel towards " + dirs.get(3).name + justathing(dirs.get(3).lock)},
						{"Nevermind", "Stay where you are."}
					};
					int travel_choice = InputScanner(travel_options);
										
					if (travel_choice < 4) {
						Location travel_dest = dirs.get(travel_choice);
						
						if (travel_dest.name.equals("Edge")) {
							System.out.println("You jump off the edge of the cliff, falling into the black waters below.");
							player.health = 0;
							player.Die();
						} else {
							if (travel_dest.lock > player.keystage) {
								System.out.println("A magical field is blocking the way.");
								System.out.println("Only a level " + travel_dest.lock + " Rune Key can open the way.");
							} else {
								current_loc = TravelTo(player, current_location, travel_dest.cords);
								System.out.println("You have finally reached " + travel_dest.name);
							}
						}
					}
				break;
				
				case 4:
					player.Inventory(false);
				break;
			}
		}
	}
		
	public String justathing(int lock) {
		if (lock == 1) return " (Level 1-8 Area)";
		if (lock == 2) return " (Level 8-16 Area)";
		if (lock > 2) return " (Level 16+ Area)";
		
		return "";
	}
	
	public int[] TravelTo(Player player, Location current_location, int[] cords) {
		int travel_cycles = 2 + (int)(Math.random() * 3);
		if (player.attribute.equalsIgnoreCase("Agilic")) travel_cycles -= 2;
		if (player.IsWearing("Boots of Traveling")) travel_cycles -= 2;
		if (travel_cycles < 0) travel_cycles = 0;
		
		for (int i = 0; i < travel_cycles; i++) {
			if (current_location.name.equalsIgnoreCase("The Wolfpack Tundra") && Math.random() < 0.1) {
				Enemy enemy = new Enemy();
					enemy.name = "Werewolf";
					enemy.maxhealth = 30;
					enemy.health = enemy.maxhealth;
					enemy.attack = 10;
					enemy.level = 5;
					enemy.surface = true;
					enemy.dungeon = false;
					enemy.damage_type = "Physical";
					enemy.attack_msg = "The " + enemy.name + " slashes you with its razor sharp claws";
					enemy.run_attack_msg = "The " + enemy.name + " pounces on you, knocking you to the ground while clawing at your chest";
					enemy.escapable = false;
					enemy.weakness.put("Fire", 100);
			
				System.out.println("As you tread through the snow of the Wolfpack Tundra you encounter a horrible beast.");
				player.FightEnemy(enemy, true);
			} else {
				if (Math.random() > .9 - ((double)player.skills.get("Luck") / 50.0)) {
					System.out.println("While walking throughout " + current_location.name + ", you stumbled upon a chest.");
					OpenChest("Regular");
				} else {
					Enemy enemy = current_location.GetRandomLandEnemy();
					System.out.println("As you journey across " + current_location.name + " you encounter a " + enemy.name);
					player.FightEnemy(enemy, true);	
				}
			}
		}
		
		return cords;
	}
	
	public int InputScanner(String[][] options) {
		for (int i = 0; i < options.length; i++) {
			System.out.print(options[i][0] + "- ");
			System.out.println(options[i][1]);
		}
		
		boolean done = false;
		
		while (!done) {
			Scanner scan = new Scanner(System.in);
			String input = scan.nextLine();
			boolean valid = false;
			
			for (int i = 0; i < options.length; i++) {
				if (options[i][0].equalsIgnoreCase(input)) {
					return i;
				}
			}
			
			System.out.println("Your input of " + input + " is not a valid option.");
		}
		
		return 0;
	}
	
	public int NumberScanner(int lower, int upper) {
		boolean done = false;
		
		while (!done) {
			Scanner scan = new Scanner(System.in);
			
			if (scan.hasNextInt()) {
				int answer = scan.nextInt();
				
				if (answer >= lower && answer <= upper) {
					return answer;
				} else System.out.println(answer + " is not a valid option.");
			} else {
				System.out.println("Please enter a number.");
			}
		}
		
		return lower;
	}
	
	public void PrintNuke() {
		for (int i = 0; i < 10; i++) {
			System.out.println();
		}
	}
	
	public int SkillScanner(int skillpoints) {
		if (skillpoints <= 0) return 0;
		
		boolean done = false;
		int result = 0;
		
		while (!done) {
			Scanner scan = new Scanner(System.in);
			
			if (scan.hasNextInt()) {
				result = scan.nextInt();
				
				if (result > skillpoints) System.out.println("Not enough skillpoints!");
				if (result < 0) System.out.println("Positive numbers only!");
				if (result <= skillpoints && result >= 0) done = true;
			} else {
				System.out.println("Please enter a valid number!");
			}
		}
		
		return result;
	}
	
	public void TraverseDungeon(DungeonNode<String> node, Location location) {		
		switch (node.toString()) {
			case "EnemyHallway":
				System.out.println("You are in a hallway, where will you go?");
			break;
		
			case "Door":
				System.out.println("You are in a room, where will you go?");
			break;
			
			case "Chest":
				System.out.println("You are in a secret skeleton party");
			break;
			
			case "Entrance":
				System.out.println("You are at the entrance, where will you go?");
			break;
			
			case "Boss":
				System.out.println("You are in a large room.");
			break;
			
			default: System.out.println("You are in a " + node.toString() + ", where will you go?");
		}
		
		int size = node.children.size();
		
//		if (size > 0) {
//			System.out.print("To your left is ");
//			DungeonRoomPrint(node.children.get(0));
//		}
//		
//		if (size > 2) {
//			System.out.print("In front of you is ");
//			DungeonRoomPrint(node.children.get(1));
//			
//			System.out.print("To your right is ");
//			DungeonRoomPrint(node.children.get(2));
//		} else if (size > 1) {
//			System.out.print("To your right is ");
//			DungeonRoomPrint(node.children.get(1));
//		}
//				
//		System.out.println("What will you do?");
		
		String[] dir_seq = {"left", "center", "right"};
		
		if (size == 1) {
			dir_seq = new String[1];
			dir_seq[0] = "left";
		}
		
		if (size == 2) {
			dir_seq = new String[2];
			dir_seq[0] = "left";
			dir_seq[1] = "right";
		}
		
		int i = 0;
		for (DungeonNode<String> child : node.children) {
			i++;
			System.out.println(i + "- " + DungeonMovePrint(child, dir_seq[i-1]));
		}
		
		i++;
		System.out.println(i + "- Go Back");
				
		System.out.println((i + 1) + "- Open your inventory.");
		
		boolean valid_input = false;
		
		int choice = NumberScanner(1, i + 1) - 1;
				
		if (choice == node.children.size() + 1) {
			player.Inventory(false);
			TraverseDungeon(node, location);
		} else {
			if (choice == node.children.size()) {
				if (node.IsRoot()) {
					if (node.raided) {
						System.out.println("You leave the dungeon.\n");
						location.dungeon_raided = true;
					} else {
						System.out.println("The door is sealed shut.\n");
						TraverseDungeon(node, location);
					}
				} else {
					TraverseDungeon(node.parent, location);
				}
			} else {
				DungeonNode<String> local_node = node.children.get(choice);
				
				if (local_node.data.equals("Enemy")) {
					if (!local_node.raided) {
						Enemy enemy = location.GetRandomDungeonEnemy();
						System.out.println("You have encountered a " + enemy.name + "!");
						if (player.FightEnemy(enemy, true)) local_node.raided = true;
					}
					
					TraverseDungeon(node, location);
				}
				
				if (local_node.data.equals("Hallway")) {
					local_node.raided = true;
					TraverseDungeon(local_node, location);
				}
				
				if (local_node.data.equals("EnemyHallway")) {
					if (!local_node.raided) {
						Enemy enemy = location.GetRandomDungeonEnemy();
						System.out.println("You have encountered a " + enemy.name + "!");
						if (player.FightEnemy(enemy, true)) local_node.raided = true;
					}
					
					if (local_node.raided) {
						TraverseDungeon(local_node, location);	
					} else {
						TraverseDungeon(node, location);
					}
				}
				
				if (local_node.data.equals("Door")) {
					if (local_node.raided) {
						System.out.println("You walk through the door.\n");
					} else {
						System.out.println("You open the door and walk through.\n");
						local_node.raided = true;
					}
					
					TraverseDungeon(local_node, location);
				}
				
				if (local_node.data.equals("Chest")) {
					if (local_node.raided) {
						System.out.println("You already opened this chest.\n");
					} else {
						local_node.raided = true;
						OpenChest("Regular");
					}
					
					TraverseDungeon(node, location);
				}
				
				if (local_node.data.equals("Boss")) {
					if (local_node.raided) {
						System.out.println("The door seems to be sealed.");
					} else {
						local_node.raided = true;
						
						System.out.println("As you walk into the room, the door closes behind you.");
						
						Enemy boss = location.boss;
						
						System.out.println("Suddenly, you are attacked by The " + boss.name + "!");
						
						player.FightEnemy(boss, false);
						
						if (location.key_dungeon) {
							System.out.println("As the life of the " + boss.name + " leaves its corpse, you notice that");
							System.out.println("there's a podium on the other side of the room.");
							System.out.println("The podium seems to have an exquisite looking object on it that is eminating some form of light.");
							System.out.println("You walk over to the podium and pick it up.");
							System.out.println("You have acquired a level " + (player.keystage + 1) + " Rune Key");
							player.keystage++;
						} else {
							boss.SpecialLoot(player, this, location, data, boss.name);
						}
						
						System.out.println();
						System.out.println("In the distance you hear a rumbling. The entrance might be open now.");
						
						if (location.name.equals("The Highlands") && player.level < 1) {
							player.xp += (player.next_lvl_xp - player.xp);
							player.LevelUp();
						}
						
						node.GetRoot().raided = true;
					}
					
					TraverseDungeon(node, location);
				}
			}
		}
	}
	
	public int LuckRoll(int original, int luck) {
		original = (int)(original - (Math.random() * (original / (1 + (luck / 5)))));
		if (((luck / 10) * 10) > (Math.random() * 20)) original += (int)(original / 4);
		
		return original;
	}
		
	public void OpenChest(String special) {
		System.out.println("You open the chest and find: ");
		
		boolean nothing = true;
		
		// Gold
		int gold = (int)((Math.random() * (player.level + 1)) * (player.skills.get("Luck") / 5));
		
		if (gold <= 0) {
			if (Math.random() > 0.5) gold = 1;
		}
		
		if (gold > 0) {
			System.out.println("* " + gold + " gold");
			nothing = false;
		}
		
		player.gold += gold;
		
		// Weapons
		int weapon_base = (int)(Math.pow(Math.sqrt(player.level + 2), 3.25));
		weapon_base = LuckRoll(weapon_base, player.skills.get("Luck"));
		
		Collection<Weapon> wep_values = data.weapons.values();
		ArrayList<Weapon> wep_list = new ArrayList<Weapon>(wep_values);
		ArrayList<Weapon> valid_weps = new ArrayList<Weapon>();
		
		for (Weapon wep : wep_list) {
			if (wep.lootable && wep.damage <= weapon_base && wep.damage > weapon_base - 15) valid_weps.add(wep);
		}
		
		Collections.shuffle(valid_weps);
		
		int i_max = (int)(Math.random() * (1.5 + player.skills.get("Luck") / 10));
		if (i_max >= 4) i_max = 4;
		for (int i = 0; i < i_max; i++) {
			if (valid_weps.size() > i) {
				nothing = false;
				Weapon prize = valid_weps.get(i);
				System.out.println("* " + prize.name);
				player.inventory.get("Weapons").add(prize);
			}
		}
		
		// Armor
		int armor_base = player.level + 1;
		
		boolean lost = false;
		int tracker = 10;
		
		while (!lost) {
			if (Math.random() * tracker <= player.skills.get("Luck")) {
				armor_base++;
				tracker *= 10;
			} else lost = true;
		}
		
		Collection<Armor> armor_values = data.armors.values();
		ArrayList<Armor> armor_list = new ArrayList<Armor>(armor_values);
		ArrayList<Armor> valid_armor = new ArrayList<Armor>();
		
		for (Armor armor : armor_list) {
			if (armor.lootable && armor.quality == armor_base) valid_armor.add(armor);
		}
		
		Collections.shuffle(valid_armor);
		
		i_max = (int)(Math.random() * (1.5 + player.skills.get("Luck") / 10));
		if (i_max >= 4) i_max = 4;
		for (int i = 0; i < i_max; i++) {
			if (valid_armor.size() > i) {
				nothing = false;
				Armor prize = valid_armor.get(i);
				System.out.println("* " + prize.name);
				player.inventory.get("Armor").add(prize);
			}
		}
		
		// Potions
		int base_potion_quality = 1 + (int)(player.level / 2);
		
		Collection<Potion> potion_values = data.potions.values();
		ArrayList<Potion> potion_list = new ArrayList<Potion>(potion_values);
		ArrayList<Potion> valid_potion = new ArrayList<Potion>();
		
		for (Potion potion : potion_list) {
			if (potion.lootable && potion.quality == base_potion_quality) valid_potion.add(potion);
		}
		
		Collections.shuffle(potion_list);
		
		i_max = (int)(Math.random() * (1.5 + player.skills.get("Luck") / 10));
		if (i_max >= 4) i_max = 4;
		for (int i = 0; i < i_max; i++) {
			nothing = false;
			Potion prize = valid_potion.get(i);
			System.out.println("* " + prize.name);
			player.inventory.get("Potions").add(prize);
		}
		
		if (nothing) System.out.println("Nothing.");
		System.out.println();
	}
	
//	public void OpenChest(String special) {
//		System.out.println("You open the chest and find: ");
//		
//		int gold = 1 + ((player.level + 1) * (int)(Math.random() * 5) * (int)(player.skills.get("Luck") / 5));
//		
//		System.out.println("* " + gold + " gold");
//		player.gold += gold;
//		
//		int base_potion_quality = 1 + (int)(player.level / 2);
//		int base_quality = 1 + player.level;
//		boolean lost = false;
//		int tracker = 10;
//		
//		while (!lost) {
//			if (Math.random() * tracker <= player.skills.get("Luck")) {
//				base_quality++;
//				tracker *= 10;
//			} else lost = true;
//		}
//		
//		Collection<Weapon> wep_values = data.weapons.values();
//		ArrayList<Weapon> wep_list = new ArrayList<Weapon>(wep_values);
//		ArrayList<Weapon> valid_weps = new ArrayList<Weapon>();
//		
//		Collection<Armor> armor_values = data.armors.values();
//		ArrayList<Armor> armor_list = new ArrayList<Armor>(armor_values);
//		ArrayList<Armor> valid_armor = new ArrayList<Armor>();
//		
//		Collection<Potion> potion_values = data.potions.values();
//		ArrayList<Potion> potion_list = new ArrayList<Potion>(potion_values);
//		ArrayList<Potion> valid_potion = new ArrayList<Potion>();
//		
//		for (Weapon wep : wep_list) {
//			if (wep.lootable && wep.quality == base_quality) valid_weps.add(wep);
//		}
//		
//		for (Armor armor : armor_list) {
//			if (armor.lootable && armor.quality == base_quality) valid_armor.add(armor);
//		}
//		
//		for (Potion potion : potion_list) {
//			if (potion.lootable && potion.quality == base_potion_quality) valid_potion.add(potion);
//		}
//		
//		Collections.shuffle(valid_weps);
//		Collections.shuffle(valid_armor);
//		Collections.shuffle(valid_potion);
//		
//		int i_max = (int)(Math.random() * (1.5 + player.skills.get("Luck") / 10));
//		if (i_max >= 4) i_max = 4;
//		for (int i = 0; i < i_max; i++) {
//			Weapon prize = valid_weps.get(i);
//			System.out.println("* " + prize.name);
//			player.inventory.get("Weapons").add(prize);
//		}
//		
//		i_max = (int)(Math.random() * (1.5 + player.skills.get("Luck") / 10));
//		if (i_max >= 4) i_max = 4;
//		for (int i = 0; i < i_max; i++) {
//			Armor prize = valid_armor.get(i);
//			System.out.println("* " + prize.name);
//			player.inventory.get("Armor").add(prize);
//		}
//		
//		i_max = (int)(Math.random() * (1.5 + player.skills.get("Luck") / 10));
//		if (i_max >= 4) i_max = 4;
//		for (int i = 0; i < i_max; i++) {
//			Potion prize = valid_potion.get(i);
//			System.out.println("* " + prize.name);
//			player.inventory.get("Potions").add(prize);
//		}
//	}
	
	public String DungeonMovePrint(DungeonNode<String> node, String dir) {
		String result = "Enter the " + node.toString();
		
		switch (node.toString()) {
			case "EnemyHallway":
				if (dir.equals("center")) dir = "forwards";
				if (node.raided) result = "Go " + dir + " down the familiar hallway.";
				else result = "Go " + dir + " down the hallway.";
			break;
			
			case "Hallway":
				if (dir.equals("center")) dir = "forwards";
				if (node.raided) result = "Go " + dir + " down the familiar hallway.";
				else result = "Go " + dir + " down the hallway.";
			break;
			
			case "Chest":
				if (dir.equals("center")) dir = " in front of you.";
				if (dir.equals("left")) dir = " to the left.";
				if (dir.equals("right")) dir = " to the right.";
				if (node.raided) result = "Inspect the Chest" + dir;
				else result = "Open the Chest" + dir;
			break;
		
			case "Door":
				if (dir.equals("center")) dir = " in front of you.";
				if (dir.equals("left")) dir = " to the left.";
				if (dir.equals("right")) dir = " to the right.";
				if (node.raided) result = "Walk through the Door" + dir;
				else result = "Open the door" + dir;
			break;
			
			case "Enemy":
				if (dir.equals("center")) dir = " in front of you.";
				if (dir.equals("left")) dir = " to the left.";
				if (dir.equals("right")) dir = " to the right.";
				if (node.raided) result = "Inspect the corpse" + dir;
				else result = "Fight the Enemy" + dir;
			break;
			
			case "Boss":
				if (dir.equals("center")) dir = " in front of you.";
				if (dir.equals("left")) dir = " to the left.";
				if (dir.equals("right")) dir = " to the right.";
				if (node.raided) result = "Inspect the sealed door" + dir;
				else result = "Walk into the large room" + dir;
			break;
		}
		
		return result;
	}
	
//	public void DungeonRoomPrint(DungeonNode<String> node) {
//		switch (node.toString()) {
//			case "EnemyHallway":
//				System.out.println("a Hallway");
//			break;
//			
//			case "Chest":
//				if (node.raided) System.out.println("an Opened Chest");
//				else System.out.println("a Closed Chest");
//			break;
//			
//			case "Door":
//				if (node.raided) System.out.println("an Opened Door");
//				else System.out.println("a Closed Door");
//			break;
//				
//			case "Enemy":
//				if (node.raided) System.out.println("a Corpse");
//				else System.out.println("an Enemy");
//			break;
//			
//			case "Boss":
//				if (node.raided) System.out.println("a Sealed Door");
//				else System.out.println("a Large Room");
//			break;
//			
//			default: System.out.println("a " + node.toString());
//		}
//	}
	
	public DungeonNode<String> BuildDungeon(DungeonNode<String> current_node) {
		if (current_node.data.equals("Hallway") || current_node.data.equals("EnemyHallway")) {
			int max = (int)(Math.ceil(Math.random() * 3));
			String[] options = {"Hallway", "EnemyHallway", "Door", "Chest"};
			
			if (GetRootDistance(current_node) > 3) {
				options = new String[2];
				options[0] = "Door";
				options[1] = "Chest";
			}
			
			for (int i = 0; i < max; i++) {
				String choice = options[(int)(Math.random() * options.length)];
				current_node.addChild(choice);
			}
		}
		
		if (current_node.data.equals("Door")) {
			String[] options = {"Chest", "Enemy", "Hallway"};
			
			if (GetRootDistance(current_node) > 3) {
				options = new String[2];
				options[0] = "Chest";
				options[1] = "Enemy";
			}
			
			String choice = options[(int)(Math.random() * options.length)];
			current_node.addChild(choice);
		}
		
		for (DungeonNode<String> child : current_node.children) {
			BuildDungeon(child);
		}
		
		return current_node;
	}
	
	public DungeonNode<String> SetupDungeon(Location location) {
		DungeonNode<String> root = new DungeonNode<String>("Entrance");
		DungeonNode<String> hallway_a = root.addChild("Hallway");
		DungeonNode<String> hallway_b = root.addChild("Hallway");
		root = BuildDungeon(root);
		
		DungeonNode<String> next = root;
		while (next.children.size() > 0) {
			next = next.children.get((int)(Math.random() * next.children.size()));
		}
		
		DungeonNode<String> local_parent = next.parent;
		local_parent.children.remove(0);
		local_parent.children.add(new DungeonNode<String>("Boss"));
		
		return root;
	}
	
	public int GetRootDistance(DungeonNode<String> node) {
		int i = 0;
		
		while (!node.IsRoot()) {
			node = node.parent;
			i++;
		}
		
		return i;
	}
	
	public DungeonNode<String> GetRoot(DungeonNode<String> node) {
		while (!node.IsRoot()) {
			node = node.parent;
		}
		
		return node;
	}
				
	public void CreateMap() {			
		// make the outerlands location
		outerlands = new Location(this, data);
		outerlands.name = "Outerlands";
		List<Enemy> o_enemies = data.GetEnemies(outerlands);
		List<Enemy> o_land_enemies = new ArrayList<Enemy>();
		List<Enemy> o_dungeon_enemies = new ArrayList<Enemy>();
		
		for (Enemy enemy : o_enemies) {
			if (enemy.surface) o_land_enemies.add(enemy);
			if (enemy.dungeon) o_dungeon_enemies.add(enemy);
		}
					
		outerlands.land_enemies = o_land_enemies;
		outerlands.dungeon_enemies = o_dungeon_enemies;	
		
		outerlands.key_dungeon = false;
		outerlands.dungeon_raided = false;
		outerlands.lock = 3;
		
		// set 2 outside edges of map to outerlands
		int outerlands_key = (int)Math.ceil(Math.random() * 56);
		
		for (int i = 0; i < map.length; i++) {
			for (int k = 0; k < map.length; k++) {
				if (i <= 1 || i >= map.length - 2 || k <= 1 || k >= map.length - 2) {
					map[i][k] = outerlands;
					
					if (outerlands_key == 0) map[i][k].key_dungeon = true;
					outerlands_key--;
				}
			}
		}
		
		// make the biome locations
		List<Location> outer_ring = new ArrayList<Location>();
		List<Location> inner_ring = new ArrayList<Location>();
				
		String[] outer_biomes = data.outer_biomes;
		String[] inner_biomes = data.inner_biomes;
		String[][] biome_sets = {inner_biomes, outer_biomes};
		
		for (int i = 0; i < biome_sets.length; i++) {
			String[] biome_set = biome_sets[i];
			int key_spot = (i + 1) * (int)Math.ceil((Math.random() * 8));
			
			for (int k = 0; k < biome_set.length; k++) {
				String biome = biome_set[k];
				
				Location loc = new Location(this, data);
				loc.name = biome;
				
				List<Enemy> enemies = data.GetEnemies(loc);
				List<Enemy> land_enemies = new ArrayList<Enemy>();
				List<Enemy> dungeon_enemies = new ArrayList<Enemy>();
				
				for (Enemy enemy : enemies) {
					if (enemy.surface) land_enemies.add(enemy);
					if (enemy.dungeon) dungeon_enemies.add(enemy);
				}
							
				loc.land_enemies = land_enemies;
				loc.dungeon_enemies = dungeon_enemies;	
				
				loc.dungeon_raided = false;
				loc.key_dungeon = key_spot == 0;
				key_spot--;
				
				player.locations_with_shops.add(loc);
												
				if (i == 1) {
					loc.lock = 2;
					outer_ring.add(loc);
				}
				if (i == 0) {
					loc.lock = 1;
					inner_ring.add(loc);
				}
			}
		}
		
		// randomize the biome locations
		Collections.shuffle(outer_ring);
		Collections.shuffle(inner_ring);
				
		// Place the biome stuff in the map
		for (int i = 2; i < map.length - 2; i++) {
			for (int k = 2; k < map.length - 2; k++) {
				if (i == 2 || i == map.length - 3 || k == 2 || k == map.length - 3) {
					map[i][k] = outer_ring.get(0);
					outer_ring.remove(0);
				} else if (i == 3 || i == map.length - 4 || k == 3 || k == map.length - 4) {
					map[i][k] = inner_ring.get(0);
					inner_ring.remove(0);
				}
			}
		}
		
		// Create the spawn location and place it in the map.
		Location spawn = new Location(this, data);
		spawn.name = "The Highlands";
		List<Enemy> spawn_enemies = data.GetEnemies(spawn);
		List<Enemy> spawn_land_enemies = new ArrayList<Enemy>();
		List<Enemy> spawn_dungeon_enemies = new ArrayList<Enemy>();
		
		for (Enemy enemy : spawn_enemies) {
			if (enemy.surface) spawn_land_enemies.add(enemy);
			if (enemy.dungeon) spawn_dungeon_enemies.add(enemy);
		}
					
		spawn.land_enemies = spawn_land_enemies;
		spawn.dungeon_enemies = spawn_dungeon_enemies;	
		
		spawn.dungeon_raided = false;
		spawn.key_dungeon = true;
		spawn.lock = 0;
		
		player.locations_with_shops.add(spawn);
		
		map[4][4] = spawn;
		
		// make the dark castle location
		Location castle = new Location(this, data);
		castle.name = "Dark Castle";
		List<Enemy> enemies = data.GetEnemies(castle);
		List<Enemy> land_enemies = new ArrayList<Enemy>();
		List<Enemy> dungeon_enemies = new ArrayList<Enemy>();
		
		for (Enemy enemy : enemies) {
			if (enemy.surface) land_enemies.add(enemy);
			if (enemy.dungeon) dungeon_enemies.add(enemy);
		}
					
		castle.land_enemies = land_enemies;
		castle.dungeon_enemies = dungeon_enemies;	
		
		castle.dungeon_raided = false;
		castle.key_dungeon = false;
		castle.lock = 4;
						
		// randomly place the castle on the outside edge
		int castle_place = (int)Math.ceil(Math.random() * ((map.length * 2) - 4));
		for (int i = 0; i < map.length; i++) {
			for (int k = 0; k < map.length; k++) {
				if (i == 0 || i == map.length - 1 || k == 0 || k == map.length - 1) {
					if (castle_place == 0) {
						int[] castle_cords = {i, k};
						castle.cords = castle_cords;
						map[i][k] = castle;
					}
					castle_place--;
				}
			}
		}
		
		// set map coordinates
		for (int i = 0; i < map.length; i++) {
			for (int k = 0; k < map.length; k++) {
				int[] cords = {i, k};
				map[i][k].cords = cords;
			}
		}
		
		player.RefreshShops(player.level);
	}
	
	public void PrintMap() {		
		for (int i = 0; i < map.length; i++) {
			System.out.println();
			
			for (int k = 0; k < map.length; k++) {
				System.out.print(map[i][k].name + " ");
			}
		}
	}	
}
