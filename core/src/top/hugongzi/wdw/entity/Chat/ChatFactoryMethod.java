package top.hugongzi.wdw.entity.Chat;

import top.hugongzi.wdw.Messages.ChatMessage;

public interface ChatFactoryMethod {
    public Chat createChat(ChatMessage msg);
}
