package top.hugongzi.wdw;

import java.util.ArrayList;

public class LoginController {
    ArrayList<String> playerids;

    public LoginController() {
        this.playerids = new ArrayList<>();
    }

    public boolean loginID(String id) {
        if (playerids.contains(id)) {
            return false;
        } else {
            playerids.add(id);
            return true;
        }
    }

    public void logoutid(String id) {
        playerids.remove(id);
    }
}
