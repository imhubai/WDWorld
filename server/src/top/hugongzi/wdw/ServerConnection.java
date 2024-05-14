package top.hugongzi.wdw;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.hugongzi.wdw.Messages.*;
import top.hugongzi.wdw.Util.Save;
import top.hugongzi.wdw.entity.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerConnection implements MessageListener {
    public Logger logger = LogManager.getLogger();
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
        logger.info("服务器ServerConnection初始化");
        oServer = new OServer(this, tcpPORT, udpPORT);
        players = new ArrayList<>();
        save = new Save();
        loginController = new LoginController();
    }

    public ServerWork getWdWserver() {
        return wdWserver;
    }

    public void setWdWserver(ServerWork wdWserver) {
        this.wdWserver = wdWserver;
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
        //玩家存档不存在
        if (player == null) {
            oServer.sendToTCP(con.getID(), new NewbieMessage());
            return;
        }
        m.setPlayer(player);
        System.out.println("Login Message recieved from : " + id);
        oServer.sendToUDP(con.getID(), m);
    }

    @Override
    public void logoutReceived(LogoutMessage m) {
        players.stream().filter(p -> Objects.equals(p.getId(), m.getId())).findFirst().ifPresent(p -> {
            players.remove(p);
            loginController.logoutid(p.getId());
        });
        System.out.println("Logout Message recieved from : " + m.getId() + " Size: " + players.size());
    }

    @Override
    public void playerMovedReceived(PositionMessage move) {
        players.stream().filter(p -> Objects.equals(p.getId(), move.getId())).findFirst().ifPresent(p -> {
            Vector2 v = p.getBody().getPosition();
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

    @Override
    public void newPlayerReceived(NewbieMessage m) {
        Player newPlayer = m.getPlayer();
        newPlayer.setItems(new ArrayList<>());
        newPlayer.setSkills(new ArrayList<>());
        newPlayer.setFriendid(new ArrayList<>());
        newPlayer.setTalentid(new ArrayList<>());
        newPlayer.setMissionid(new ArrayList<>());

        newPlayer.setLevel(1);
        newPlayer.setExp(1);
        newPlayer.setPVP_DAMAGE_SCALE(50);
        newPlayer.setPVP_WOUND_SCALE(50);
        newPlayer.setTickets(0);
        newPlayer.setYuanbao(0);
        newPlayer.setGold(0);
        newPlayer.setSliver(0);
        newPlayer.setCoin(0);

        newPlayer.setMaxhp(200);
        newPlayer.setHp(200);
        newPlayer.setMaxjing(100);
        newPlayer.setJing(100);
        newPlayer.setMaxqi(100);
        newPlayer.setQi(100);
        newPlayer.setMaxneili(100);
        newPlayer.setNeili(100);

        newPlayer.setAge(14);
        newPlayer.setId(m.getId());
        newPlayer.setSpeed(newPlayer.getPoint_speed() / 20f);
        newPlayer.setIsnewbie(true);
        newPlayer.setBanchat(false);
        newPlayer.setBirthday(System.currentTimeMillis());

        newPlayer.setMap("Maps/map003.tmx");
        newPlayer.setX(1000f);
        newPlayer.setY(1000f);

        save.saveplayer(newPlayer);
    }
}
