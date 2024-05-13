package top.hugongzi.wdw.Messages;

import com.esotericsoftware.kryonet.Connection;

public interface MessageListener {
    /**
     * PlayerID, and location should be received.
     */
    public void loginReceived(Connection con, LoginMessage m);

    /**
     * PlayerID should be received.
     */
    public void logoutReceived(LogoutMessage m);

    /**
     * PlayerID and direction should be received.
     */
    public void playerMovedReceived(PositionMessage move);

}
