package com.bricksmasher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The screen displayed when a level is beaten
 */
public class NextLevelScreen implements Screen {
    BrickSmasher game;
    GameScreen gameScreen;
    Stage stage;

    /**
     * constructor for NextLevelScreen
     * @param game the game used throughout runtime
     * @param gameScreen the active GameScreen
     */
    public NextLevelScreen(BrickSmasher game, GameScreen gameScreen){
        this.game = game;
        this.gameScreen = gameScreen;

        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        TextButton nextLevel = new TextButton("Next Level", skin);

        table.add(nextLevel).fillX().uniformX().width(300).height(80);
        nextLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.level += 1;
                game.setScreen(gameScreen);
                gameScreen.ball = gameScreen.spawnBall();
                gameScreen.spawnBricks();
                dispose();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);

        game.batch.begin();
        game.font.draw(game.batch, "Level " + gameScreen.level + " complete", 280, 300);
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
