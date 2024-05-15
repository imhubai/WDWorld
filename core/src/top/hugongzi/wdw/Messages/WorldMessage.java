package top.hugongzi.wdw.Messages;

import top.hugongzi.wdw.entity.Player.Player;

import java.util.List;

public class WorldMessage {
    List<Player> playermap;

    public List<Player> getPlayermap() {
        return playermap;
    }

    public void setPlayermap(List<Player> playermap) {
        this.playermap = playermap;
    }
}
