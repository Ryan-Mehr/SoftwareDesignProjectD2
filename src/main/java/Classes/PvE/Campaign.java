package Classes.PvE;

import Classes.Heros.Hero;

public class Campaign {
    boolean isCompleted;
    String campaignName;
    Hero playerHero;
    /*
    Maybe have a table for HEROES. And that the Campaign only says HERO id. Once Campaign is found in the DB through name, it then finds the Hero by ID.
     */
    int roomNumberIndex;
}
