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
 * The screen displayed when a player loses
 */
public class LoseScreen implements Screen {
    int score;
    BrickSmasher game;
    Stage stage;

    /**
     * Constructor for LoseScreen
     * @param game the game used throughout runtime
     * @param score the achieved score
     */
    public LoseScreen(BrickSmasher game, int score){
        this.score = score;
        this.game = game;

       stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        game.font.getData().setScale(1.0f);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        TextButton mainMenu = new TextButton("Menu", skin);

        table.add(mainMenu).fillX().uniformX().width(80).height(50);

        table.top();
        table.right();
        table.padTop(-7);
        table.padRight(-8);
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game, new GameScreen(game)));
                dispose();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);

        game.batch.begin();
        game.font.draw(game.batch, "GAME OVER", 270, 300);
        game.font.draw(game.batch, "Score:"+score, 270, 250);
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
