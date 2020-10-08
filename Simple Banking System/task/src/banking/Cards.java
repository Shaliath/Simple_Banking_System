package banking;

public class Cards {

    private static Cards instance;
    private String activeUser;

    private Cards() {}

    public static Cards getInstance() {
        if (instance == null) {
            instance = new Cards();
        }
        return instance;
    }

    public String getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }

}
