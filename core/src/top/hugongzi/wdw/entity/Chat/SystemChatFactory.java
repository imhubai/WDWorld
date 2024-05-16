package top.hugongzi.wdw.entity.Chat;

import com.badlogic.gdx.graphics.Color;
import top.hugongzi.wdw.Messages.ChatMessage;

public class SystemChatFactory implements ChatFactoryMethod {
    @Override
    public Chat createChat(ChatMessage message) {
        return new Chat("公告:" + message.getMsg(), Color.YELLOW);
    }
}
