package com.bricksmasher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The main menu screen
 */
public class MainMenuScreen implements Screen{
    final BrickSmasher game;

    private SettingsScreen settingsScreen;
    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;

    private Stage stage;

    public final static int MENU = 0;
    public final static int SETTINGS = 1;
    public final static int GAME = 2;
    public final static int CONTINUE = 3;

    /**
     * constructor for MainMenuScreen
     * @param game the game used throughout runtime
     * @param gameScreen the active gamescreen, sending a new gamescreen to this class will override previous progress
     */
    public MainMenuScreen(final BrickSmasher game, GameScreen gameScreen){
        this.game = game;
        this.gameScreen = gameScreen;

        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    /**
     * Controls the logic to swap screens in the menu
     * @param screen the button that is clicked
     */
    public void changeScreen(int screen){
        switch(screen){
            case CONTINUE:
                if(gameScreen == null) gameScreen = new GameScreen(game);
                game.setScreen(gameScreen);
                break;
            case MENU:
                if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(game, gameScreen);
                game.setScreen(mainMenuScreen);
                break;
            case SETTINGS:
                if(settingsScreen == null) settingsScreen = new SettingsScreen(game, gameScreen);
                game.setScreen(settingsScreen);
                break;
            case GAME:
                game.setScreen(new GameScreen(game));
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
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        TextButton continueGame = new TextButton("Continue", skin);
        TextButton newGame = new TextButton("New Game", skin);
        TextButton settings = new TextButton("Settings", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.add(continueGame).fillX().uniformX().width(300).height(80);
        table.row();
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
                dispose();
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen(SETTINGS);
                dispose();
            }
        });
        continueGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen(CONTINUE);
                dispose();
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
