package top.hugongzi.wdw;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器工作类，用于管理和维护服务器连接。
 */
public class ServerWork implements Subject {
    public static int TCPport = 22333; // TCP端口号，默认值为22333
    public static int UDPport = 22334; // UDP端口号，默认值为22334
    public final ServerConnection serverConnection; // 服务器连接对象
    private List<Observer> observers = new ArrayList<>();

    /**
     * ServerWork构造函数，用于初始化服务器工作类实例。
     *
     * @param tcpport TCP端口号。
     * @param udpport UDP端口号。
     */
    public ServerWork(int tcpport, int udpport) {
        TCPport = tcpport;
        UDPport = udpport;
        this.serverConnection = new ServerConnection(this, TCPport, UDPport); // 创建服务器连接实例
        notifyObservers("Server started at " + TCPport + " / " + UDPport);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    /**
     * 执行服务器连接的周期性更新操作。
     * 该方法会将系统当前时间与上一次更新时间的差值（delta）传递给服务器连接对象，用于基于时间的更新逻辑。
     *
     * @param delta 自上次更新以来的时间差（以毫秒为单位）。这个参数用于计算和应用时间相关的更新，比如游戏循环或网络同步。
     */
    public void tick(long delta) {
        serverConnection.update(delta);
    }
}

