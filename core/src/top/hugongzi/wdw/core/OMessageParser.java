package top.hugongzi.wdw.core;

import top.hugongzi.wdw.entity.Player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A parsers class which has methods to constructs objects like
 * Players,Enemy,Bulltes from GameWorldMessage.
 * 
 * @author oguz
 *
 */
public class OMessageParser {
/*
	private OMessageParser() {
	}

	*//**
	 * Returns a player list from gameworld message. Including clients itself. Thats
	 * why when clients are rendering players on their screen, they should exclude
	 * the player with the same id.
	 *//*
	public static List<Player> getPlayersFromGWM(GameWorldMessage m) {

		float[] tp = m.getPlayers();
		List<Player> plist = new ArrayList<>();
		for (int i = 0; i < tp.length / 4; i++) {

			float x = tp[i * 4];
			float y = tp[i * 4 + 1];
			float id = tp[i * 4 + 2];
			float health = tp[i * 4 + 3];
			Player p = new Player(x, y, 50);
			p.setHealth((int) health);
			p.setId((int) id);

			plist.add(p);

		}
		return plist;

	}

	*//** Returns a bullet list from gameworld message. *//*
	public static List<Bullet> getBulletsFromGWM(GameWorldMessage m) {

		float[] tb = m.getBullets();

		List<Bullet> blist = new ArrayList<>();
		for (int i = 0; i < tb.length / 3; i++) {
			float x = tb[i * 3];
			float y = tb[i * 3 + 1];
			float size = tb[i * 3 + 2];

			Bullet b = new Bullet(x, y, size);

			blist.add(b);
		}

		return blist;
	}*/

}
