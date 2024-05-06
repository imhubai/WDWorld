package top.hugongzi.wdw.fcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameMap {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private World world;
    private Box2DDebugRenderer worldRenderer;
    private int tileSize;
    private Stage stage;

    public GameMap(String mapstr, Stage stage) {
        this.stage = stage;
        this.map = new TmxMapLoader().load(mapstr);
        world = new World(new Vector2(0, -10), true);
        worldRenderer = new Box2DDebugRenderer();
        tileSize = ((TiledMapTileLayer) map.getLayers().get(0)).getTileWidth();
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);
    }

    public void draw() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        stage.getViewport().apply();

        mapRenderer.setView((OrthographicCamera) stage.getViewport().getCamera());
        mapRenderer.render();

        worldRenderer.render(world, stage.getViewport().getCamera().combined);
    }

    private BodyDef getBodyDef(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        return bodyDef;
    }
}
