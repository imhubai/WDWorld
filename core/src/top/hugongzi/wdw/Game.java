package top.hugongzi.wdw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class Game extends com.badlogic.gdx.Game {
    @Override
    public void create() {

    }
    public static Stage stage(){
        Stage stage = new Stage(viewport(), GameEntry.batch);

        return stage;
    }

    public static ScalingViewport viewport(){
        /*return new ScalingViewport(Game.setting.fitScaling ? Scaling.fit : Scaling.stretch, Game.STAGE_WIDTH, Game.STAGE_HEIGHT, new OrthographicCamera());*/
        return new ScalingViewport(Scaling.fit, 1280, 720, new OrthographicCamera());
    }
}
