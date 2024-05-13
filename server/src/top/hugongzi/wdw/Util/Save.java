package top.hugongzi.wdw.Util;

import com.badlogic.gdx.utils.Json;
import top.hugongzi.wdw.entity.Player.Player;
import top.hugongzi.wdw.entity.Player.PlayerData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Save {
    String[] dirs = {"users", "maps"};

    public Save() {
        for (String dir : dirs) {
            File d = new File(dir);
            if (d.mkdir()) {
                System.out.println(dir + "not found,created.");
            }
        }
    }

    public Player loadSave(String id) {
        String dir = "users/" + id.charAt(0) + "/";
        File d = new File(dir);
        if (d.mkdir()) {
            System.out.println(dir + "not found,created.");
            return null;
        }
        dir += id + ".json";
        File save = new File(dir);
        if (!save.exists()) {
            System.out.println(dir + "not found,please check.");
            return null;
        }
        FileInputStream fis;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = new FileInputStream(save);
            byte[] bys = new byte[100];
            while (fis.read(bys, 0, bys.length) != -1) {
                stringBuilder.append(new String(bys));
            }
            fis.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        Json json = new Json();
        return json.fromJson(Player.class, stringBuilder.toString());
    }

    public void saveplayer(Player player) {
        String dir = "users/";
        PlayerData data = player.getPlayerData();
        File alphaname = new File(dir + data.getId().charAt(0));
        if (alphaname.mkdir()) {
            System.out.println(dir + "not found,created.");
        }
        dir += data.getId().charAt(0) + "/" + data.getId() + ".json";
        File file = new File(dir);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            Json json = new Json();
            json.toJson(player);
            fos.write(json.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
