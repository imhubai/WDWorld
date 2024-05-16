package top.hugongzi.wdw.entity.Chat;

import top.hugongzi.wdw.GameEntry;
import top.hugongzi.wdw.Messages.ChatMessage;

public class ChatFactory {

    private ChatFactoryMethod currentFactory;

    public ChatFactory(ChatFactoryMethod factory) {
        this.currentFactory = factory;
    }

    public Chat addChat(ChatMessage c) {
        Chat chat = currentFactory.createChat(c);
        GameEntry.maingame.getChatList().add(chat);
        if (GameEntry.maingame.getChatList().size() > 5000) {
            GameEntry.maingame.getChatList().remove(0);
        }
        return chat;
    }
}

