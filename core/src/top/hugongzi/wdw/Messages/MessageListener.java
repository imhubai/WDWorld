package top.hugongzi.wdw.Messages;

import com.esotericsoftware.kryonet.Connection;

public interface MessageListener {
    public void loginReceived(Connection con, LoginMessage m);

    public void logoutReceived(LogoutMessage m);

    public void newPlayerReceived(Connection con, NewbieMessage m);

    public void chatReceived(ChatMessage m);

    public void playerMovedReceived(PlayerMovedMessage m);
}
