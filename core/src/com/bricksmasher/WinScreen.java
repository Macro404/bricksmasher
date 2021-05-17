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
 * The screen that is displayed when a player beats all the main levels.
 */
public class WinScreen implements Screen {
    int score;
    Stage stage;
    BrickSmasher game;
    GameScreen gameScreen;

    /**
     * Constructor for WinScreen
     * @param game the game used throughout runtime
     * @param score the current score to be displayed
     * @param gameScreen the active GameScreen
     */
    public WinScreen(BrickSmasher game, int score, GameScreen gameScreen){
        this.game = game;
        this.score = score;
        this.gameScreen = gameScreen;
        game.font.getData().setScale(1.0f);

        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        TextButton mainMenu = new TextButton("Menu", skin);
        TextButton keepPlaying = new TextButton("Keep playing", skin);
        table.padTop(30);
        table.add(mainMenu).fillX().uniformX().width(300).height(80);
        table.add(keepPlaying).fillX().uniformX().width(300).height(80);
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game, gameScreen));
            }
        });
        keepPlaying.addListener(new ChangeListener() {
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
        game.font.draw(game.batch, "Game completed!", 220, 300);
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

    }
}
