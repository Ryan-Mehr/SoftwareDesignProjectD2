package Classes.Heros;

public class Warrior extends Hero {
    public Warrior() {
        this.heroClass = HeroClass.WARRIOR;
        this.attackLevelUpIncrement += 2;
        this.defenseLevelUpIncrement += 3;
    }
}
