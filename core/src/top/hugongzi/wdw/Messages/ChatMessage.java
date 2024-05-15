package top.hugongzi.wdw.Messages;

import top.hugongzi.wdw.entity.Player.Player;

public class ChatMessage {
    private String msg;
    private ChatChannel Channel;
    private Player player;
    /**
     * 私聊目标玩家ID，非私聊频道无需设置
     */
    private String targetPlayerID;
    /**
     * 区域聊天地图，非区域频道无需设置
     */
    private String regionMap;

    public String getRegionMap() {
        return regionMap;
    }

    public void setRegionMap(String regionMap) {
        this.regionMap = regionMap;
    }

    public String getTargetPlayerID() {
        return targetPlayerID;
    }

    public void setTargetPlayerID(String targetPlayerID) {
        this.targetPlayerID = targetPlayerID;
    }

    public ChatChannel getChannel() {
        return Channel;
    }

    public void setChannel(ChatChannel channel) {
        Channel = channel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
