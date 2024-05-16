package top.hugongzi.wdw.core;

import com.badlogic.gdx.graphics.Color;
import top.hugongzi.wdw.Messages.ChatMessage;

import java.util.ArrayList;

public class ChatFactory {
    public ArrayList<Chat> chatList;

    public ChatFactory() {
        chatList = new ArrayList<>();
    }

    public Chat addChat(ChatMessage c) {
        Chat chat = makeChatFactory(c);
        if (chatList.size() > 5000) {
            chatList.remove(0);
        }
        return chat;
    }

    public Chat makeChatFactory(ChatMessage c) {
        switch (c.getChannel()) {
            case CHANNEL_HELP:
                chat_help chat_help = new chat_help();
                Chat parsedchat_help = chat_help.makeChat(c.getMsg());
                chatList.add(parsedchat_help);
                return parsedchat_help;
            case CHANNEL_FAKE:
                chat_fake chat_fake = new chat_fake();
                Chat parsedchat_fake = chat_fake.makeChat(c.getMsg());
                chatList.add(parsedchat_fake);
                return parsedchat_fake;
            case CHANNEL_REGION:
                chat_region chat_region = new chat_region();
                Chat parsedchat_region = chat_region.makeChat(c.getMsg());
                chatList.add(parsedchat_region);
                return parsedchat_region;
            case CHANNEL_PRIVATE:
                chat_private chat_private = new chat_private();
                Chat parsedchat_private = chat_private.makeChat(c.getMsg());
                chatList.add(parsedchat_private);
                return parsedchat_private;
            case CHANNEL_SYSTEM:
                chat_system chat_system = new chat_system();
                Chat parsedchat_system = chat_system.makeChat(c.getMsg());
                chatList.add(parsedchat_system);
                return parsedchat_system;
            case CHANNEL_GLOBAL:
                chat_nosign chat_global = new chat_nosign();
                Chat parsedchat_global = chat_global.makeChat(c.getMsg());
                chatList.add(parsedchat_global);
                return parsedchat_global;
        }
        return new Chat("", Color.WHITE);
    }
}

class chat_nosign implements ChatFactoryimpl {
    @Override
    public Chat makeChat(String msg) {
        return new Chat(msg, Color.CYAN);
    }
}

class chat_help implements ChatFactoryimpl {
    @Override
    public Chat makeChat(String msg) {
        return new Chat("求助:" + msg, Color.GREEN);
    }
}

class chat_region implements ChatFactoryimpl {
    @Override
    public Chat makeChat(String msg) {
        return new Chat("区域:" + msg, Color.WHITE);
    }
}

class chat_private implements ChatFactoryimpl {
    @Override
    public Chat makeChat(String msg) {
        return new Chat(msg, Color.WHITE);
    }
}

class chat_system implements ChatFactoryimpl {
    @Override
    public Chat makeChat(String msg) {
        return new Chat("公告:" + msg, Color.YELLOW);
    }
}

class chat_fake implements ChatFactoryimpl {
    @Override
    public Chat makeChat(String msg) {
        return new Chat("有传闻称" + msg, Color.MAGENTA);
    }
}
