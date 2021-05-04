package com.bricksmasher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SettingsScreen implements Screen{
    private BrickSmasher parent;
    private Stage stage;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    private MainMenuScreen mainMenuScreen;

    public SettingsScreen(BrickSmasher game){
        parent = game;
        mainMenuScreen = new MainMenuScreen(game);

        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( parent.getSettings().getMusicVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getSettings().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getSettings().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getSettings().setMusicEnabled( enabled );
                return false;
            }
        });
        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(parent.getSettings().getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getSettings().setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainMenuScreen.changeScreen(mainMenuScreen.MENU);
            }
        });
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(parent.getSettings().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                parent.getSettings().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        titleLabel = new Label( "Settings", skin );
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin );
        musicOnOffLabel = new Label( "Music", skin );
        soundOnOffLabel = new Label( "Sound Effects", skin );

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(10,0,0,10);
        table.add(backButton).colspan(2);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
