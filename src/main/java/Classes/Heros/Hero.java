package Classes.Heros;

public abstract class Hero {
    String heroID = "";
    HeroClass heroClass;
    int level = 1;
    int maxLevel = 20;
    int hp; // hp and mana is set to maxHp during battle.
    int maxHp = 100;
    int mana;
    int maxMana = 50;
    int attack = 5;
    int defense = 5;
    int attackLevelUpIncrement = 1;
    int defenseLevelUpIncrement = 1;
    int healthPointsLevelUpIncrement = 5;
    int manaLevelUpIncrement = 2;
    boolean defeated = false;

    void checkDeath() {
        if (this.hp <= 0)
        {
            // Make this hero disabled / unable to take turn.
            System.out.println("Defeated");
            this.defeated = true;
        }
    }
    int checkSurplus(int amount, int maxAmount, int amountGetting) {
        return Math.min(amount + amountGetting, maxAmount);
    }
    void defendChoice() {
        this.hp = checkSurplus(this.hp, this.maxHp, 10);
        this.mana = checkSurplus(this.mana, this.maxMana, 5);
    }
    void takeDamage(int damage) {
        int damageToTake = damage - (this.defense);
        if (hp > 0 && damageToTake >= 0) {
            hp -= damageToTake;
        }
        checkDeath();
    }
    void levelUp() {
        if (this.level < this.maxLevel) {
            this.level++;
            this.attack += this.attackLevelUpIncrement;
            this.defense += this.defenseLevelUpIncrement;
            this.maxHp += this.healthPointsLevelUpIncrement;
            this.maxMana += this.manaLevelUpIncrement;
        }
    }
}
