package top.hugongzi.wdw.Messages;

import top.hugongzi.wdw.entity.Player.Player;

import java.util.List;

public class MessageCreator {

    private MessageCreator() {

    }

    /**
     * Creates a GameWorldMessage. This message will be broadcasted to the all
     * clients over UDP.
     * <p>
     * Every objects in server like Enemies,Players,Bullets will be converted to the
     * float arrays and broadcasted.
     */
    public static GameWorldMessage generateGWMMessage(List<Player> players) {

        GameWorldMessage gwm = new GameWorldMessage();
        float[] pcord = new float[players.size() * 4];
        for (int i = 0; i < players.size(); i++) {

/*			pcord[i * 4] = players.get(i).getPosition().x;
			pcord[i * 4 + 1] = players.get(i).getPosition().y;
			pcord[i * 4 + 2] = players.get(i).getId();
			pcord[i * 4 + 3] = players.get(i).getHealth();*/
        }

        gwm.setPlayers(pcord);

        return gwm;
    }

}
