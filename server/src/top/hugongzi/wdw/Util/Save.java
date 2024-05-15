package top.hugongzi.wdw.Util;

import com.badlogic.gdx.utils.Json;
import top.hugongzi.wdw.ServerConnection;
import top.hugongzi.wdw.entity.Player.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Save {
    String[] dirs = {"users", "maps"};

    public Save() {
        for (String dir : dirs) {
            File d = new File(dir);
            if (d.mkdir()) {
                ServerConnection.logger.info(dir + " not found,created.");
            }
        }
    }

    public Player loadSave(String id) {
        String dir = "users/" + id.charAt(0) + "/";
        File d = new File(dir);
        if (d.mkdir()) {
            ServerConnection.logger.info(dir + " not found,created.");
            return null;
        }
        dir += id + ".json";
        File save = new File(dir);
        if (!save.exists()) {
            ServerConnection.logger.info(dir + " not found.");
            return null;
        }
        StringBuilder jsonData = new StringBuilder();
        try (FileReader reader = new FileReader(dir)) {
            int character;
            while ((character = reader.read()) != -1) {
                jsonData.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Json json = new Json();
        return json.fromJson(Player.class, jsonData.toString());
    }

    public void saveplayer(Player player) {
        String dir = "users/";
        File alphaname = new File(dir + player.getId().charAt(0));
        if (alphaname.mkdir()) {
            ServerConnection.logger.info(dir + " not found,created.");
        }
        dir += player.getId().charAt(0) + "/" + player.getId() + ".json";
        File file = new File(dir);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                ServerConnection.logger.info(e);
            }
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            Json json = new Json();
            String jsonData = json.toJson(player);
            fos.write(jsonData.getBytes());
            fos.close();
        } catch (IOException e) {
            ServerConnection.logger.info(e);
        }
    }
}
