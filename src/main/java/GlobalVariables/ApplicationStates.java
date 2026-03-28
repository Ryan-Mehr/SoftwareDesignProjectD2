package GlobalVariables;

import Classes.Heros.Hero;
import Classes.User;
import Classes.Party;

public class ApplicationStates {

    private ApplicationStates() {
        throw new AssertionError();
    }

    // Logged-in user
    public static User theUser = null;

    // PvP opponent username
    public static String opponentUsername = null;

    // Selected parties for PvP
    public static Party selectedParty = null;
    public static Party opponentParty = null;

    // For PvE
    public static Hero selectedHero = null;
    public static boolean isNewCampaign = false;
    public static boolean inPvEBattle = false;
    public static String campaignName = "";
    public static Hero PvEPlayerHero = null;
}
