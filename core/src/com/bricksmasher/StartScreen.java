package com.bricksmasher;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * The intro screen displayed when the game is launched
 */
public class StartScreen implements Screen{
    final BrickSmasher game;
    OrthographicCamera camera;

    /**
     * constructor for StartScreen
     * @param game the game used throughout runtime
     */
    public StartScreen(final BrickSmasher game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "BRICKSMASHER", 230, 300);
        game.font.draw(game.batch, "Click anywhere to begin!", 120, 200);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game, new GameScreen(game)));
            dispose();
        }
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
