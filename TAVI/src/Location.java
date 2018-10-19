import java.util.*;

public class Location {
	public String name;
	public List<Enemy> land_enemies = new ArrayList<Enemy>();
	public List<Enemy> dungeon_enemies = new ArrayList<Enemy>();
	public int[] cords;
	public boolean dungeon_raided = false;
	public boolean key_dungeon = false;
	public int lock = 0;
	
	public List<Weapon> shop_weapons = new ArrayList<Weapon>();
	public List<Armor> shop_armor = new ArrayList<Armor>();
	public List<Potion> shop_potions = new ArrayList<Potion>();
	public List<Spell> shop_spells = new ArrayList<Spell>();
	
	public Enemy boss;
	public Logic logic;
	public Data data;
	
	public Location(Logic logic, Data data) {
		this.logic = logic;
		this.data = data;
	}
	
	public Enemy GetRandomDungeonEnemy() {
		if (dungeon_enemies.size() > 0) {
			return dungeon_enemies.get((int)(Math.random() * dungeon_enemies.size()));
		} else return new Enemy();
	}
	
	public Enemy GetRandomLandEnemy() {
		if (land_enemies.size() > 0) {
			return land_enemies.get((int)(Math.random() * land_enemies.size()));
		} else return new Enemy();
	}
	
	public void EnterVillage(Player player) {		
		System.out.println("You enter into the village, what do you do?");
		
		boolean done = false;
		
		while (!done) {
			String[][] options = {
				{"Shop", "Shop for new items."},
				{"Sleep", "Sleep to regain health."},
				{"Talk", "Talk to the locals for advice."},
				{"Inventory", "Access your inventory."},
				{"Leave", "Leave the village."}
			};
			
			System.out.println();
			int choice = logic.InputScanner(options);
			
			switch (choice) {
				case 0:
					Shop(player);
				break;
				
				case 1:
					System.out.println("You wake up feeling well rested.");
					player.health = player.maxhealth;
					player.mana = player.maxmana;
					player.PrintHealth();
					player.PrintMana();
				break;
				
				case 2:
					VillagerDialogue(player);
					System.out.println();
				break;
				
				case 3:
					player.Inventory(false);
				break;
				
				case 4:
					done = true;
				break;
			}
		}
	}
	
	public void VillagerDialogue(Player player) {		
		String[] male_first = {
			"Aldous", "Alistair", "Conrad", "Constantine", "Drake", "Everard",
			"Gawain", "Godwin", "Jeffery", "Joachim", "Ladislas", "Luther",
			"Odo", "Percival", "Randall", "Robin", "Theobald", "Wade",
			"Warner"
		};
				
		String[] female_first = {
			"Aethelu", "Agnes", "Alba", "Ava", "Beatrice", "Beverly", "Cecily", "Daisy",
			"Edith", "Ella", "Emma", "Heloise", "Isabella", "Margery", "Matilda", "Merry",
			"Odilia", "Reina", "Rhoslyn", "Sigourney", "Trea"
		};
				
		String[] sur_names = {
			"Baker", "Baxter", "Bennett", "Brickenden", "Brooker", "Brown", "Carpenter", "Carter",
			"Cheeseman", "Clarke", "Cook", "Cooper", "Draper", "Fisher", "Fletcher", "Foreman",
			"Forester", "Granger", "Gregory", "Hayward", "Hughes", "Kilner", "Lister", "Mannering",
			"Mason", "Mercer", "Nash", "Payne", "Rolfe", "Sawyer", "Shepherd", "Slater", "Taylor",
			"Tyler", "Walter", "Ward", "Webb", "Webster", "Wood", "Wright"
		};
		
		boolean male = Math.random() > 0.5;
		String person = "";
		String male_random = male_first[(int)(Math.random() * male_first.length)];
		String female_random = female_first[(int)(Math.random() * female_first.length)];
		String sur_random = sur_names[(int)(Math.random() * sur_names.length)];
		
		if (male) person = male_random;
		else person = female_random;
		person += " " + sur_random;
		
		int charisma = player.skills.get("Charisma");
		
		System.out.println("You speak with " + person + ".");
		System.out.println(QuoteFormat(person, DialogueGreeting(player, person, charisma)));
		
		boolean done = false;
		boolean flawless = true;
		boolean friendly = false;
		
		int max_tries = 2;
		if (Math.random() < player.skills.get("Luck") / 10) max_tries++;
		
		if (charisma <= 0) done = true;
		
		while (!done) {
			String[][] options = {
				{"Rumors", "Have you heard any rumors?"},
				{"Information", "Do you know anything about the local area?"},
				{"Directions", "Where is everything in the village?"},
				{"Goodbye", "Leave"}
			};
			
			System.out.println();
			int result = logic.InputScanner(options);
			
			if (result < 3) {				
				if (!friendly && (charisma < 3 || Math.random() > ((double)charisma / 10.0))) {
					String[] responses = {
						"No.",
						"I'm not answering your questions.",
						"No, go away.",
						"Who are you? Just go away.",
						"Go bother someone else.",
						"I'm busy, go ask someone else."
					};
					
					flawless = false;
					
					if (max_tries <= 0) {
						double a_chance = (player.skills.get("Luck") / 10.0) + (charisma / 10.0);
						if (!male) a_chance += 0.2;
						
						if (Math.random() > a_chance) {
							System.out.println(QuoteFormat(person, "You've asked for it now!"));
							
							int damage = 3;
							
							if (male) System.out.println(person + " punches you and runs away.");
							else {
								System.out.println(person + " stabs you and runs away.");
								damage = 6;
							}
																					
							player.TakeDamage(damage, "Slash");
						} else System.out.println(QuoteFormat(person, "Goodbye."));
						
						done = true;
					} else System.out.println(QuoteFormat(person, responses[(int)(Math.random() * responses.length)]));
					max_tries--;
				} else {
					if (!flawless) {
						System.out.println(person + ": \"Ugh, fine.\"");
						flawless = true;
					}
					
					friendly = true;
					
					switch (result) {
						case 0:
							DialogueRumors(person, charisma);
						break;
						
						case 1:
							DialogueInformation(player, person, charisma);
						break;
						
						case 2:
							List<String> directions = new ArrayList<String>();
							directions.add("North");
							directions.add("South");
							directions.add("East");
							directions.add("West");
							Collections.shuffle(directions);
							
							System.out.println(person + ": \"You can find the shops in the " + directions.get(0) + ".\"");
							System.out.println(person + ": \"There's an inn you can sleep at to the " + directions.get(1) + ".\"");
						break;
					}
				}
			} else done = true;
		}
	}
	
	public String QuoteFormat(String person, String quote) {
		return person + ": \"" + quote + "\"";
	}
	
	public void DialogueRumors(String person, int charisma) {		
		if (name.equals("The Highlands")) {
			System.out.println(QuoteFormat(person, data.highlands_a));
			System.out.println();			
			
			String[][] answer = {
				{"History", "What is all this talk about Rune Keys? What's the history behind that?"},
				{"Show", "You mean, THIS Rune Key? *Show Rune Key*"},
				{"Back", "I want to talk about something else."}
			};
			
			int choice = logic.InputScanner(answer);
			
			switch (choice) {
				case 0: System.out.println(QuoteFormat(person, data.highlands_b)); break;
				case 1: System.out.println(QuoteFormat(person, data.highlands_c)); break;
				case 2: System.out.println(QuoteFormat(person, "Ok.")); break;
			}
						
			return;
		}
		
		System.out.println(QuoteFormat(person, "I can't think of any rumors right now. Sorry.\n"));
	}
	
	public void DialogueInformation(Player player, String person, int charisma) {
		String response = "";
		
		if (!player.inventory.get("Items").contains("Map") && charisma >= 10 && (Math.random() < ((double)charisma / 20.0))) {
			System.out.println(person + ": \"Actually, yes. I happen to have a map of the entire area.\"");
			System.out.println(person + ": \"You're a rather charming fellow, here, you can have it.\"");
			System.out.println(person + ": \"I don't have much use for it anyways.\"");
			player.inventory.get("Items").add(data.items.get("Map"));
			System.out.println("\nYou have acquired a Map.");
		} else {
			if (charisma >= 5) {
				int choice = (int)(Math.random() * 3);
				
				response = person + ": \"I heard that the shop has a ";
				
				switch (choice) {
					case 0:
						if (shop_weapons.size() > 0)
						response += shop_weapons.get((int)(shop_weapons.size() * Math.random())).name;
					break;
					case 1:
						if (shop_armor.size() > 0)
						response += shop_armor.get((int)(shop_armor.size() * Math.random())).name;
					break;
					case 2:
						if (shop_potions.size() > 0)
						response += shop_potions.get((int)(shop_potions.size() * Math.random())).name;
					break;
				}
				
				response += " for sale.";
			} else {
				String[] sorry = {
					"No, I don't have any information, sorry.",
					"Sorry, I don't have any information.",
					"Sorry, I don't know much about that.",
					"I don't think so, sorry."
				};
				
				response = person + ": \"" + sorry[(int)(sorry.length * Math.random())] + "\"";
			}
		}
		
		System.out.println(response);
	}
	
	public String DialogueGreeting(Player player, String person, int charisma) {	
		String result = "error";
		
		String[][] greetings = {
			{"What ARE you? Get away from me!", "What is that?! HELP!", "What, what are you? Get away from me!"},
			{"Get out of my face.", "Go away.", "Leave me alone.", "I don't want to deal with you."},
			{"You're rather annoying, hurry it up will you?", "Make it quick.", "I don't have time for this.", "What's your problem?"},
			{"What is it?", "What do you want?", "Why are you bothering me?"},
			{"Hello?", "What is it?", "Do you want something?"},
			{"Hello.", "Hi.", "Hey."},
			{"Hello!", "Greetings.", "Hey there."},
			{"Why hello! It's nice to meet you.", "Hello there!", "Hi!"},
			{"Nice to meet you!", "Nice weather out, huh? It's good to meet you!"},
			{"It's a pleasure to meet you!", "Please, I'm listening.", "Please, do tell."},
			{"Are you " + player.name + "? I've heard so much about you!", "It's nice to meet you " + player.name + "! What is it that you need?", "Why, hello there! I'm happy to talk."}
		};
		
		if (charisma > 10) charisma = 10;
		
		return greetings[charisma][(int)(Math.random() * greetings[charisma].length)];
	}
	
	public void Shop(Player player) {		
		boolean done = false;
		
		System.out.println("Merchant: \"Take a look at my goods!\"");
		
		while (!done) {
			String[][] options = {
				{"Buy", "Buy items from the merchant."},
				{"Sell", "Sell items to the merchant."},
				{"Leave", "Leave the store."}
			};
		
			int choice = logic.InputScanner(options);
			
			if (choice == 0) {
				boolean shop_done = false;
				
				while (!shop_done) {
					System.out.println("Merchant: \"What are you looking for?\"");
					String[][] buy = {
						{"Weapons", "Buy weapons from the merchant."},
						{"Armor", "Buy armor from the merchant."},
						{"Potions", "Buy potions from the merchant."},
						{"Spells", "Buy spells from the merchant."},
						{"Inventory", "Open your inventory."},
						{"Nevermind", "Go back."}
					};
					
					int buy_choice = logic.InputScanner(buy);
					boolean local_done = false;
					switch (buy_choice) {
						case 0:
							local_done = false;
							
							System.out.println(player.name + " gold: " + player.gold);
							
							while (!local_done) {
								System.out.println("0- Back");
								
								int index = 1;
								for (Weapon wep : shop_weapons) {
									String price = "$" + wep.price + " ";
									System.out.println(index + "- " + price + wep.name + " (" + wep.damage + ")");
									index++;
								}
								
								int local_choice = logic.NumberScanner(0, shop_weapons.size());
								if (local_choice == 0) local_done = true;
								else {
									local_choice--;
									Weapon chosen = shop_weapons.get(local_choice);
									
									if (player.gold >= chosen.price) {
										System.out.println("Merchant: \"I hope the " + chosen.name + " will serve you well.\"");
										shop_weapons.remove(local_choice);
										player.inventory.get("Weapons").add(chosen);
										player.gold -= chosen.price;
									} else {
										System.out.println("You do not have enough gold!");
									}
								}
							}
						break;
						
						case 1:
							local_done = false;
														
							while (!local_done) {
								System.out.println(player.name + " gold: " + player.gold);
								System.out.println("0- Back");
								
								int index = 1;
								for (Armor armor : shop_armor) {
									String price = "$" + armor.price + " ";
									System.out.println(index + "- " + price + armor.name + " (" + armor.protection + ")");
									index++;
								}
								
								int local_choice = logic.NumberScanner(0, shop_armor.size());
								if (local_choice == 0) local_done = true;
								else {
									local_choice--;
									Armor chosen = shop_armor.get(local_choice);
									
									if (player.gold >= chosen.price) {
										System.out.println("Merchant: \"I hope the " + chosen.name + " will serve you well.\"");
										shop_armor.remove(local_choice);
										player.inventory.get("Armor").add(chosen);
										player.gold -= chosen.price;
									} else {
										System.out.println("You do not have enough gold!");
									}
								}
							}
						break;
						
						case 2:
							local_done = false;
							
							while (!local_done) {
								System.out.println(player.name + " gold: " + player.gold);
								System.out.println("0- Back");
								
								int index = 1;
								for (Potion potion : shop_potions) {
									String price = "$" + potion.price + " ";
									System.out.println(index + "- " + price + potion.name + " (" + potion.strength + ")");
									index++;
								}
								
								int local_choice = logic.NumberScanner(0, shop_potions.size());
								if (local_choice == 0) local_done = true;
								else {
									local_choice--;
									Potion chosen = shop_potions.get(local_choice);
									
									if (player.gold >= chosen.price) {
										System.out.println("Merchant: \"I hope the " + chosen.name + " will serve you well.\"");
//										shop_potions.remove(local_choice);
										player.inventory.get("Potions").add(chosen);
										player.gold -= chosen.price;
									} else {
										System.out.println("You do not have enough gold!");
									}
								}
							}
						break;
						
						case 3:
							local_done = false;
							
							while (!local_done) {
								System.out.println(player.name + " gold: " + player.gold);
								System.out.println("0- Back");
								
								int index = 1;
								for (Spell spell : shop_spells) {
									String price = "$" + spell.price + " ";
									System.out.println(index + "- " + price + spell.name + " (" + spell.damage + "x" + (spell.duration + 1) + " [" + spell.cost + "])");
									index++;
								}
								
								int local_choice = logic.NumberScanner(0, shop_spells.size());
								if (local_choice == 0) local_done = true;
								else {
									local_choice--;
									Spell chosen = shop_spells.get(local_choice);
									
									if (player.gold >= chosen.price) {
										System.out.println("Merchant: \"I hope the " + chosen.name + " will serve you well.\"");
										shop_spells.remove(local_choice);
										player.inventory.get("Spells").add(chosen);
										player.gold -= chosen.price;
									} else {
										System.out.println("You do not have enough gold!");
									}
								}
							}
						break;
						
						case 4:
							player.Inventory(false);
						break;
						
						case 5:
							shop_done = true;
						break;
					}
				}
			} 
			
			if (choice == 1) {
				player.Inventory(true);
			}
			
			if (choice == 2) {
				System.out.println("Merchant: \"Goodbye!\"");
				done = true;
			}
		}
	}
	
	public String toString() {
		return name;
	}
}

/**
World Format:
1 = inner ring (spawn)
2 = mid ring
3 = outer ring
M = the castle
0 = the outerlands

	0 0 0 0 0 0 0 0 0
	0 0 0 0 0 0 0 0 0
	0 0 3 3 3 3 3 0 0
	0 0 3 2 2 2 3 0 0
	0 0 3 2 1 2 3 0 0
	0 0 3 2 2 2 3 0 0
	0 0 3 3 3 3 3 0 0
	0 0 0 0 0 0 0 0 0
	0 0 0 0 0 0 M 0 0
	
The castle spawns somewhere completely random on the outer edge of the outerlands.
The outerlands are super dangerous and easy to get lost and die in.

Each number requires a key to go to the next number.
The dungeon in spawn gives the key for the Mid ring
A random dungeon in the mid ring gives a key for the outer ring
A random dungeon in the outer ring gives a key for the outerlands
Should the outerlands have dungeons? If so, then a random outerlands square should have the
key to the castle. Otherwise, you can just walk into the castle.
*/
