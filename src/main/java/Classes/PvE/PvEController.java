package Classes.PvE;

import Classes.Heros.Hero;
import Factory.CampaignFactory;
import GlobalVariables.ApplicationStates;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class PvEController {

    @FXML private Button attackButton;
    @FXML private Button defendButton;
    @FXML private Button startButton;
    @FXML private Button nextRoomButton;

    @FXML private Label playerHealthLabel;
    @FXML private Label enemyHealthLabel;
    @FXML private Label roomLabel;
    @FXML private TextArea battleLog;

    private Campaign currentCampaign;

    private Hero playerHero;
    private Player player;
    private Enemy currentEnemy;
    private Room[] rooms;
    private int currentRoomIndex = 0;

    CampaignFactory campaignFactory = new CampaignFactory();

    public void saveCurrentCampaign(ActionEvent actionEvent) {
        // Save campaign in the DB. Should use Repository for this.
    }

    public void initializeCurrentCampaign(ActionEvent actionEvent) {
        // It should use the Campaign Object.
        if (ApplicationStates.isNewCampaign) {
            // Make a default new campaign.
            System.out.println("New default campaign is being made.");
            currentCampaign = campaignFactory.createCampaign();
            currentCampaign.isCompleted = false;
            currentCampaign.campaignName = ApplicationStates.campaignName;
            currentCampaign.playerHero = ApplicationStates.PvEPlayerHero;
            currentCampaign.roomNumberIndex = 0;
        } else {
            // Load in the campaign by getting the Campaign from the Repository which gets it from the DAO which gets it from the DB.
            System.out.println("Campaign is being loaded.");
        }
    }

    public void loadCurrentCampaign(ActionEvent actionEvent) {
        // A
        currentRoomIndex = currentCampaign.roomNumberIndex;
        playerHero = currentCampaign.playerHero;

    }

    @FXML
    private void handleStartBattle(ActionEvent event) {

        attackButton.setVisible(true);
        defendButton.setVisible(true);

        startButton.setDisable(true);

        // ApplicationStates.inPvEBattle being false means that PvE battle is being loaded. If true it means already in PvE battle.
        if (!ApplicationStates.inPvEBattle) {
            initializeCurrentCampaign(event);
            loadCurrentCampaign(event);
            ApplicationStates.inPvEBattle = true;
        }

         player = new Player("Hero", playerHero.getMaxHp(), playerHero.getAttack());

        Enemy zombie = new Enemy("Zombie", 50, 5);
        Enemy skeleton = new Enemy("Skeleton", 60, 6);
        Enemy spider = new Enemy("Spider", 40, 4);

        rooms = new Room[] {
                new Room(1, zombie),
                new Room(2, skeleton),
                new Room(3, spider)
        };

        // currentRoomIndex = 0;

        battleLog.clear();

        loadRoom();

        attackButton.setDisable(false);
        defendButton.setDisable(false);
        nextRoomButton.setDisable(true);

        battleLog.appendText("Battle started!\n");
    }

    @FXML
    private void handleRestart(ActionEvent event) {
        currentRoomIndex = 0;

        player.setHealth(100);
        loadRoom();

        attackButton.setDisable(false);
        defendButton.setDisable(false);
        nextRoomButton.setDisable(true);

        nextRoomButton.setText("Next Room");

        battleLog.clear();
        battleLog.appendText("Campaign Restarted!\n");
    }

    @FXML
    private void handleAttack(ActionEvent event) {
        int damage = player.attack();
        currentEnemy.takeDamage(damage);
        battleLog.appendText("Player attacks " + currentEnemy.getName() +
                " for " + damage + " damage.\n");

        enemyTurn();
        updateLabels();
        checkBattleEnd();
    }

    @FXML
    private void handleDefend(ActionEvent event) {
        player.defend();
        battleLog.appendText("Player defends!\n");

        enemyTurn();
        updateLabels();
        checkBattleEnd();
    }

    private void enemyTurn() {
        if (currentEnemy.getHealth() > 0) {
            int damage = currentEnemy.attack();
            player.takeDamage(damage);
            battleLog.appendText("Enemy attacks for " + damage + " damage.\n");
        }
    }

    private void updateLabels() {
        playerHealthLabel.setText(String.valueOf(player.getHealth()));
        enemyHealthLabel.setText(String.valueOf(currentEnemy.getHealth()));
        roomLabel.setText("Room " + rooms[currentRoomIndex].getRoomNumber() +
                ": " + currentEnemy.getName());
    }

    private void checkBattleEnd() {
        if (player.getHealth() <= 0) {
            battleLog.appendText("Player defeated!\n");
            disableButtons();
            startButton.setDisable(false);
        } else if (currentEnemy.getHealth() <= 0) {
            battleLog.appendText("Enemy defeated!\n");
            disableButtons();
            nextRoomButton.setDisable(false);
            playerHero.setLevel(playerHero.getLevel()+1);
        }
    }

    @FXML
    private void handleNextRoom(ActionEvent event) {

        currentRoomIndex++;
        if (currentRoomIndex < rooms.length) {
            loadRoom();
            attackButton.setVisible(true);
            defendButton.setVisible(true);
            attackButton.setDisable(false);
            defendButton.setDisable(false);

            nextRoomButton.setDisable(true);
        } else {
            battleLog.appendText("\nCampaign Complete!\n");
            attackButton.setVisible(false);
            defendButton.setVisible(false);
            nextRoomButton.setDisable(true);
            startButton.setDisable(false);
            currentCampaign.isCompleted = true;
        }
    }

    private void loadRoom() {
        currentEnemy = rooms[currentRoomIndex].getEnemy();
        battleLog.appendText("\nEntering Room " + rooms[currentRoomIndex].getRoomNumber() +
                " - Enemy: " + currentEnemy.getName() + "\n");
        updateLabels();
    }

    private void disableButtons() {
        attackButton.setDisable(true);
        defendButton.setDisable(true);

    }

    @FXML
    private void initialize() {
        attackButton.setVisible(false);
        defendButton.setVisible(false);
        nextRoomButton.setDisable(true);
    }

}