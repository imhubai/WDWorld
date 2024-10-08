package top.hugongzi.wdw.entity.Chat;

import com.badlogic.gdx.graphics.Color;
import top.hugongzi.wdw.Messages.ChatMessage;

public class DefaultChatAdapter implements ChatAdapter {
    public Chat createChat(ChatMessage message) {
        return new Chat(message.getMsg(), Color.WHITE);
    }
}
