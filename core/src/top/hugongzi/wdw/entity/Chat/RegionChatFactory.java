package top.hugongzi.wdw.entity.Chat;

import com.badlogic.gdx.graphics.Color;
import top.hugongzi.wdw.Messages.ChatMessage;

public class RegionChatFactory implements ChatFactoryMethod {
    @Override
    public Chat createChat(ChatMessage message) {
        return new Chat("区域:" + message.getMsg(), Color.WHITE);
    }
}
