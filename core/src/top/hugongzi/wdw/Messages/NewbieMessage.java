package top.hugongzi.wdw.Messages;

import top.hugongzi.wdw.entity.Player.Player;

public class NewbieMessage {
    private String id;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
