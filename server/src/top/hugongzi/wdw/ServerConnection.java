package top.hugongzi.wdw;

import com.esotericsoftware.kryonet.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.hugongzi.wdw.Messages.*;
import top.hugongzi.wdw.Util.Save;
import top.hugongzi.wdw.entity.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 服务器连接类，负责处理客户端的登录、退出、聊天和新手创建等消息。
 */
public class ServerConnection implements MessageListener {
    public static Logger logger = LogManager.getLogger();
    private ServerWork wdWserver;
    private int tcpPORT, udpPORT;
    private List<Player> players;
    private OServer oServer;
    private Save save;
    private float deltaTime = 0;
    private LoginController loginController;

    /**
     * 构造函数，初始化服务器连接。
     *
     * @param wdWserver 服务器工作类实例。
     * @param tcpPORT   TCP端口号。
     * @param udpPORT   UDP端口号。
     */
    public ServerConnection(ServerWork wdWserver, int tcpPORT, int udpPORT) {
        this.wdWserver = wdWserver;
        this.tcpPORT = tcpPORT;
        this.udpPORT = udpPORT;
        logger.info("服务器ServerConnection初始化");
        oServer = new OServer(this, tcpPORT, udpPORT);
        wdWserver.registerObserver(oServer);
        players = new ArrayList<>();
        save = new Save();
        loginController = new LoginController();
    }

    // Getter 和 Setter 方法

    public ServerWork getWdWserver() {
        return wdWserver;
    }

    public void setWdWserver(ServerWork wdWserver) {
        this.wdWserver = wdWserver;
    }

    /**
     * 更新服务器状态，包括处理网络消息和更新玩家状态。
     *
     * @param deltaTime 上一帧到这一帧的时间差。
     */
    public void update(float deltaTime) {
        this.deltaTime = deltaTime;
        oServer.parseMessage();
        //players.forEach(p -> p.update(deltaTime));
        WorldMessage worldMessage = new WorldMessage();
        worldMessage.setPlayermap(players);
        oServer.sendToAllUDP(worldMessage);
    }

    /**
     * 处理登录消息。
     *
     * @param con 连接对象。
     * @param m   登录消息。
     */
    @Override
    public void loginReceived(Connection con, LoginMessage m) {
        String id = m.getId();
        loginController.loginID(id);
        Player player = save.loadSave(id);
        // 如果玩家存档不存在，则发送新手消息
        if (player == null) {
            oServer.sendToTCP(con.getID(), new NewbieMessage());
            return;
        }
        player.setConid(con.getID());
        m.setPlayer(player);
        if (!players.contains(player)) {
            players.add(player);
        }
        ServerConnection.logger.info("Login Message recieved from : " + id);
        oServer.sendToUDP(con.getID(), m);
    }

    /**
     * 处理退出消息。
     *
     * @param m 退出消息。
     */
    @Override
    public void logoutReceived(LogoutMessage m) {
        players.stream().filter(p -> Objects.equals(p.getId(), m.getId())).findFirst().ifPresent(p -> {
            save.saveplayer(p);
            players.remove(p);
            loginController.logoutid(p.getId());
        });
        ServerConnection.logger.info("Logout Message recieved from : " + m.getId() + " Size: " + players.size());
    }

    /**
     * 处理新建玩家消息。
     *
     * @param con 连接对象。
     * @param m   新建玩家消息。
     */
    @Override
    public void newPlayerReceived(Connection con, NewbieMessage m) {
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
        newPlayer.setConid(con.getID());
        save.saveplayer(newPlayer);
    }

    /**
     * 处理聊天消息。
     *
     * @param m 聊天消息。
     */
    @Override
    public void chatReceived(Connection con, ChatMessage m) {
        // 根据频道类型将消息发送给相应的玩家
        switch (m.getChannel()) {
            case CHANNEL_GLOBAL:
            case CHANNEL_SYSTEM:
            case CHANNEL_FAKE:
                oServer.sendToAllTCP(m);
                return;
            case CHANNEL_REGION:
                for (Player onlineplayer : players) {
                    if (Objects.equals(onlineplayer.getMap(), m.getRegionMap()))
                        oServer.sendToTCP(onlineplayer.getConid(), m);
                }
            case CHANNEL_PRIVATE:
                players.stream().filter(p -> Objects.equals(p.getId(), m.getTargetPlayerID())).findFirst().ifPresent(p -> {
                    oServer.sendToTCP(p.getConid(), m);
                });
        }
    }

    @Override
    public void playerMovedReceived(PlayerMovedMessage m) {
        players.stream().filter(p -> Objects.equals(p.getId(), m.getPlayerid())).findFirst().ifPresent(p -> {
            p.setX(m.getX());
            p.setY(m.getY());
            //logger.info(m.getX()+" "+m.getY());
            p.setCurrentVelocity(m.getCurrentVelocity());
            p.setCurrentState(m.getCurrentState());
        });
    }
}
