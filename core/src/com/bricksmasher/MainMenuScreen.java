package com.bricksmasher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;

public class MainMenuScreen implements Screen{
    final BrickSmasher game;
    OrthographicCamera camera;

    private SettingsScreen settingsScreen;
    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;

    private Stage stage;

    public final static int MENU = 0;
    public final static int SETTINGS = 1;
    public final static int GAME = 2;



    public MainMenuScreen(final BrickSmasher game){
        this.game = game;

        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(game);
                game.setScreen(mainMenuScreen);
                break;
            case SETTINGS:
                if(settingsScreen == null) settingsScreen = new SettingsScreen(game);
                game.setScreen(settingsScreen);
                break;
            case GAME:
                if(gameScreen == null) gameScreen = new GameScreen(game);
                game.setScreen(gameScreen);
                break;
        }
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        TextButton newGame = new TextButton("New Game", skin);
        TextButton settings = new TextButton("Settings", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.add(newGame).fillX().uniformX().width(300).height(80);
        table.row().pad(0, 0, 0, 0);
        table.add(settings).fillX().uniformX().width(300).height(80);
        table.row();
        table.add(exit).fillX().uniformX().width(300).height(80);

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen(GAME);
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen(SETTINGS);
            }
        });
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
