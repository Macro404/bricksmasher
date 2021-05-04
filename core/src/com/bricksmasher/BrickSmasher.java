package com.bricksmasher;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Game;



public class BrickSmasher extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	private GameSettings settings;
	Music backgroundMusic;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		settings = new GameSettings();

		font = new BitmapFont(Gdx.files.internal("introFont.fnt"), Gdx.files.internal("introFont.png"), false); // use libGDX's default Arial font
		this.setScreen(new StartScreen(this));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("musicLoop.wav"));

		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}
	public GameSettings getSettings(){
		return this.settings;
	}

	@Override
	public void render () {
		super.render();
	}

	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		backgroundMusic.dispose();
	}
}
