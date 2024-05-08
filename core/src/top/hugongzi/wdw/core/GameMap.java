package top.hugongzi.wdw.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static net.dermetfan.gdx.math.GeometryUtils.decompose;
import static net.dermetfan.gdx.math.GeometryUtils.toPolygonArray;

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
        parseMap(map, world);
    }

    public BodyDef getBodyDef(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        return bodyDef;
    }

    public void parseMap(TiledMap map, World world) {
        AddStaticObjectCollisions(map, world);
    }

    private void AddStaticObjectCollisions(TiledMap map, World world) {
        MapObjects collisions = map.getLayers().get("Collision").getObjects();
        for (int i = 0; i < collisions.getCount(); i++) {
            MapObject mapObject = collisions.get(i);

            if (mapObject instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) mapObject;
                Rectangle rectangle = rectangleObject.getRectangle();

                BodyDef bodyDef = getBodyDef(rectangle.getX() + rectangle.getWidth() / 2f, rectangle.getY() + rectangle.getHeight() / 2f);

                Body body = world.createBody(bodyDef);
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(rectangle.getWidth() / 2f, rectangle.getHeight() / 2f);
                body.createFixture(polygonShape, 0.0f);
                polygonShape.dispose();
            } else if (mapObject instanceof EllipseMapObject) {
                EllipseMapObject circleMapObject = (EllipseMapObject) mapObject;
                Ellipse ellipse = circleMapObject.getEllipse();

                BodyDef bodyDef = getBodyDef(ellipse.x, ellipse.y);

                if (ellipse.width != ellipse.height)
                    Log.e("Only circles are allowed.", new IllegalArgumentException());

                Body body = world.createBody(bodyDef);
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(ellipse.width / 2f);
                body.createFixture(circleShape, 0.0f);
                circleShape.dispose();
            } else if (mapObject instanceof PolygonMapObject) {
                PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                Polygon polygon = polygonMapObject.getPolygon();

                float[][] polygonListfloat = decompose(polygon.getVertices());
                Polygon[] polygonList = toPolygonArray(polygonListfloat);
                PolygonShape polygonShape;
                for (Polygon value : polygonList) {
                    BodyDef bodyDef = getBodyDef(polygon.getX(), polygon.getY());
                    Body body = world.createBody(bodyDef);
                    polygonShape = new PolygonShape();
                    polygonShape.set(value.getVertices());
                    body.createFixture(polygonShape, 0.0f);
                    polygonShape.dispose();
                }
            }
        }
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public void setMapRenderer(OrthogonalTiledMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Box2DDebugRenderer getWorldRenderer() {
        return worldRenderer;
    }

    public void setWorldRenderer(Box2DDebugRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void draw() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        stage.getViewport().apply();
        mapRenderer.setView((OrthographicCamera) stage.getViewport().getCamera());
        mapRenderer.render();
        worldRenderer.render(world, stage.getViewport().getCamera().combined);
    }
}
