package Repository;

import Classes.Heros.Hero;
import DAO.HeroDAO;

import java.sql.SQLException;

public class HeroRepository {
    private static volatile HeroRepository obj = null;
    private HeroRepository() {
        System.out.println("HeroRepository is instantiated.");
    }
    public static HeroRepository getInstance() {
        if (obj == null) {
            synchronized (HeroRepository.class) {
                if (obj == null) {
                    obj = new HeroRepository();
                }
            }
        }
        return obj;
    }

    private final HeroDAO heroDAO = HeroDAO.getInstance();

    public void saveHero(Hero hero, int userID) throws SQLException {
        heroDAO.saveHero(hero, userID);
    }

    public Hero getHero(int userID) throws SQLException {
        return heroDAO.getHero(userID);
    }
}
