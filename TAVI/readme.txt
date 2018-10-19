TAVI (Text Adventure VI) is the 6th texture adventure game I’ve made since high school. 
Each new version of this game has better programming practices, new features, and more automation. 
Unfortunately, I have never completed any of these games. 
This is because I have more fun creating the logic and framework of the game rather than the game itself. 
Specifically, all that is missing in this version is some enemies and items in Data.java, a final boss fight, and various balances. 
Some of the new features include:
1) The dungeons are generated randomly and are built from a tree data structure. 
As you traverse through the dungeon you are actually traversing through a tree.
2) You can speak with villagers that have randomly generated names and other new dialogue features.
3) Unique items.
4) Huge variety of enemies, weapons, armor, spells, etc.
5) Spells are actually implemented this time.
6) Rather than liner progression the game runs on a new location system, the position of the locations is randomized each new game and looks something like this:

O = outerlands
3 = outer ring
2 = inner ring
1 = starting location

O O O O O O O O O
O O O O O O O O O
O O 3 3 3 3 3 O O
O O 3 2 2 2 3 O O
O O 3 2 1 2 3 O O
O O 3 2 2 2 3 O O
O O 3 3 3 3 3 O O
O O O O O O O O O
O O O O O O O O O

The locations in the outer and inner rings are randomized.
