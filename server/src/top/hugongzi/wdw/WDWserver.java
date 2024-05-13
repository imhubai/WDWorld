package top.hugongzi.wdw;

/**
 * WDWserver启动类
 * 主要负责初始化服务器端口，并通过ServerWork类开启服务器连接，
 * 实现服务器的循环tick处理以及休眠机制。
 *
 * @author Hubai
 */
public class WDWserver {
    public static int TCPport = 22333;  // TCP端口号，默认值为22333
    public static int UDPport = 22334;  // UDP端口号，默认值为22334
    private static final Object lock = new Object();  // 用于同步的锁对象
    static long startTime = System.nanoTime();  // 记录程序启动时间

    /**
     * 程序入口点，启动服务器。
     * 支持通过命令行参数修改TCP和UDP端口号。
     * 主要完成以下功能：
     * 1. 解析命令行参数，更新端口号；
     * 2. 初始化服务器工作模块；
     * 3. 进入服务器循环处理周期。
     *
     * @param args 命令行参数，可接受两个参数分别用于设置TCP和UDP端口号。
     */
    public static void main(String[] args) {
        // 获得服务器jar附加参数，更新端口号
        if (args.length >= 2) {
            TCPport = Integer.parseInt(args[0]);
            UDPport = Integer.parseInt(args[1]);
        }

        // 初始化服务器工作对象
        ServerWork server = new ServerWork(TCPport, UDPport);

        // 服务器tick循环，负责服务器的常规处理和休眠
        while (true) {
            try {
                long then = System.nanoTime();
                server.tick(System.nanoTime() - startTime);
                long now = System.nanoTime();
                long sleeptime = 16 - (now - then) / 1000000L;

                // 保证休眠时间在10毫秒以上
                if (sleeptime > 0 && sleeptime < 10) {
                    sleeptime = 10;
                }

                // 同步锁定，休眠等待
                synchronized (lock) {
                    lock.wait(sleeptime);
                }
            } catch (InterruptedException e) {
                // 中断异常处理，清理现场并退出循环
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                // 通用异常处理，输出错误信息
                System.err.println("Error in ticker loop: " + e.getMessage());
            }
        }
    }

    /**
     * 唤醒等待中的线程。
     * 该方法用于外部触发服务器循环的继续进行。
     */
    public void wakeUp() {
        synchronized (lock) {
            lock.notifyAll();  // 唤醒所有等待的线程
        }
    }
}
