package top.hugongzi.wdw.entity.Player;

import top.hugongzi.wdw.entity.Item.Item;
import top.hugongzi.wdw.entity.Skills.Skill;

import java.util.ArrayList;

public class PlayerData {
    private ArrayList<Item> items;
    private ArrayList<Skill> skills;
    private ArrayList<String> friendid;
    private long level;
    private long exp;
    private int PVP_DAMAGE_SCALE;
    private int PVP_WOUND_SCALE;
    private long yuanbao, gold, sliver, tickets;
    private long hp, maxhp;
    private long jing, maxjing;
    private long qi, maxqi;
    private long neili, maxneili;
    private int age;
    private String id;
    private int gameday;
    private int speed;
    private int point_magic, point_power, point_brain, point_speed;
    private Boolean isnewbie;
    private String name, purname;
    private Boolean banchat;
    private Boolean birthday;
    private String map;
    private float x, y;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getFriendid() {
        return friendid;
    }

    public void setFriendid(ArrayList<String> friendid) {
        this.friendid = friendid;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public int getPVP_DAMAGE_SCALE() {
        return PVP_DAMAGE_SCALE;
    }

    public void setPVP_DAMAGE_SCALE(int PVP_DAMAGE_SCALE) {
        this.PVP_DAMAGE_SCALE = PVP_DAMAGE_SCALE;
    }

    public int getPVP_WOUND_SCALE() {
        return PVP_WOUND_SCALE;
    }

    public void setPVP_WOUND_SCALE(int PVP_WOUND_SCALE) {
        this.PVP_WOUND_SCALE = PVP_WOUND_SCALE;
    }

    public long getYuanbao() {
        return yuanbao;
    }

    public void setYuanbao(long yuanbao) {
        this.yuanbao = yuanbao;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getSliver() {
        return sliver;
    }

    public void setSliver(long sliver) {
        this.sliver = sliver;
    }

    public long getTickets() {
        return tickets;
    }

    public void setTickets(long tickets) {
        this.tickets = tickets;
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public long getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(long maxhp) {
        this.maxhp = maxhp;
    }

    public long getJing() {
        return jing;
    }

    public void setJing(long jing) {
        this.jing = jing;
    }

    public long getMaxjing() {
        return maxjing;
    }

    public void setMaxjing(long maxjing) {
        this.maxjing = maxjing;
    }

    public long getQi() {
        return qi;
    }

    public void setQi(long qi) {
        this.qi = qi;
    }

    public long getMaxqi() {
        return maxqi;
    }

    public void setMaxqi(long maxqi) {
        this.maxqi = maxqi;
    }

    public long getNeili() {
        return neili;
    }

    public void setNeili(long neili) {
        this.neili = neili;
    }

    public long getMaxneili() {
        return maxneili;
    }

    public void setMaxneili(long maxneili) {
        this.maxneili = maxneili;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGameday() {
        return gameday;
    }

    public void setGameday(int gameday) {
        this.gameday = gameday;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPoint_magic() {
        return point_magic;
    }

    public void setPoint_magic(int point_magic) {
        this.point_magic = point_magic;
    }

    public int getPoint_power() {
        return point_power;
    }

    public void setPoint_power(int point_power) {
        this.point_power = point_power;
    }

    public int getPoint_brain() {
        return point_brain;
    }

    public void setPoint_brain(int point_brain) {
        this.point_brain = point_brain;
    }

    public int getPoint_speed() {
        return point_speed;
    }

    public void setPoint_speed(int point_speed) {
        this.point_speed = point_speed;
    }

    public Boolean getIsnewbie() {
        return isnewbie;
    }

    public void setIsnewbie(Boolean isnewbie) {
        this.isnewbie = isnewbie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurname() {
        return purname;
    }

    public void setPurname(String purname) {
        this.purname = purname;
    }

    public Boolean getBanchat() {
        return banchat;
    }

    public void setBanchat(Boolean banchat) {
        this.banchat = banchat;
    }

    public Boolean getBirthday() {
        return birthday;
    }

    public void setBirthday(Boolean birthday) {
        this.birthday = birthday;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
