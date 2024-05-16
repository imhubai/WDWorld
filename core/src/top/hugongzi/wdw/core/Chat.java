package top.hugongzi.wdw.core;

import com.badlogic.gdx.graphics.Color;

public class Chat {
    String msg;
    Color color;

    public Chat(String msg, Color color) {
        this.msg = msg;
        this.color = color;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
