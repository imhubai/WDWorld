package top.hugongzi.wdw.entity.Chat;

import com.badlogic.gdx.graphics.Color;
import top.hugongzi.wdw.Messages.ChatMessage;

public class FakeChatFactory implements ChatFactoryMethod {
    @Override
    public Chat createChat(ChatMessage message) {
        return new Chat("有传闻称" + message.getMsg(), Color.MAGENTA);
    }
}
