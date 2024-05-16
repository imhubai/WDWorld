package top.hugongzi.wdw.entity.Chat;

import com.badlogic.gdx.graphics.Color;
import top.hugongzi.wdw.Messages.ChatMessage;

// 具体的聊天类型工厂类
public class HelpChatFactory implements ChatFactoryMethod {
    @Override
    public Chat createChat(ChatMessage message) {
        return new Chat("求助:" + message.getMsg(), Color.GREEN);
    }
}
