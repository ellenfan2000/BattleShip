## BattleShip

**Battleship** is a strategy-type game for two players. It is played on ruled grids on which each player's fleet of warships is marked. The locations of the fleets are concealed from the other player. Players alternate turns calling "shots" at the other player's ships, and the objective of the game is to destroy the opposing player's fleet.

Each player has the following ships:

* 2 **Submarines** that are 1x2 rectangles (represented by "s")

* 3 **Destroyers** that are 1x3 rectangles (represented by "d")

* 3 **Battleships** with the following shape:(represented by "b")
  <pre>
    b              b           bbb             b  
   bbb     OR      bb   OR      b     OR      bb  
                   b                           b   
      
    Up           Right        Down           Left
  </pre>
* 2 **Carriers** with the following shape(represented by "c")
  <pre>
    c                       c             
    c         cccc          cc           ccc  
    cc   OR     ccc   OR    cc   OR    cccc  
    cc                       c  
     c                       c  
     
    Up       Right         Down          Left
    </pre>
## How to play

In a terminal, run the following command:
* first run ` ./gradlew build` to build the game
* run `./gradlew run` to start the game
