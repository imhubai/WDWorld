package top.hugongzi.wdw;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import top.hugongzi.wdw.Messages.*;
import top.hugongzi.wdw.Util.Save;
import top.hugongzi.wdw.entity.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerConnection implements MessageListener {
    private ServerWork wdWserver;
    private int tcpPORT, udpPORT;
    private List<Player> players;

    private OServer oServer;
    private Save save;
    private float deltaTime = 0;

    private LoginController loginController;

    public ServerConnection(ServerWork wdWserver, int tcpPORT, int udpPORT) {
        this.wdWserver = wdWserver;
        this.tcpPORT = tcpPORT;
        this.udpPORT = udpPORT;
        oServer = new OServer(this, tcpPORT, udpPORT);
        players = new ArrayList<>();

        loginController = new LoginController();
    }

    public void update(float deltaTime) {
        this.deltaTime = deltaTime;
        oServer.parseMessage();
        players.forEach(p -> p.update(deltaTime));
        GameWorldMessage m = MessageCreator.generateGWMMessage(players);
        oServer.sendToAllUDP(m);
    }

    @Override
    public void loginReceived(Connection con, LoginMessage m) {
        String id = m.getId();
        loginController.loginID(id);
        Player player = save.loadSave(id);
        if (player == null) {
            oServer.sendToTCP(con.getID(),new NewbieMessage());
            return;
        }
        //players.add(new Player(m.getX(), m.getY(), 50, id));
        ServerLog.i("Login Message recieved from : " + id);
        oServer.sendToUDP(con.getID(), m);
    }

    @Override
    public void logoutReceived(LogoutMessage m) {
        players.stream().filter(p -> Objects.equals(p.getPlayerData().getId(), m.getId())).findFirst().ifPresent(p -> {
            players.remove(p);
            loginController.logoutid(p.getPlayerData().getId());
        });
        ServerLog.i("Logout Message recieved from : " + m.getId() + " Size: " + players.size());
    }

    @Override
    public void playerMovedReceived(PositionMessage move) {
        players.stream().filter(p -> Objects.equals(p.getPlayerData().getId(), move.getId())).findFirst().ifPresent(p -> {
            Vector2 v = p.getPhysicalBody().getPosition();
            switch (move.getDirection()) {
                case LEFT:
                    v.x -= deltaTime * 200;
                    break;
                case RIGHT:
                    v.x += deltaTime * 200;
                    break;
                case UP:
                    v.y -= deltaTime * 200;
                    break;
                case DOWN:
                    v.y += deltaTime * 200;
                    break;
                default:
                    break;
            }
        });
    }
}
