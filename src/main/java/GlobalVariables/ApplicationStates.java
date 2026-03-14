package GlobalVariables;

import Classes.User;

public class ApplicationStates {
    private ApplicationStates() {
        throw new AssertionError();
    }

    public static User theUser = null;
}
