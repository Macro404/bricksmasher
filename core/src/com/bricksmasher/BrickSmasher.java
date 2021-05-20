package com.bricksmasher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Game;


/**
 * The entry point to the game
 */
public class BrickSmasher extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	private GameSettings settings;
	public Music backgroundMusic;
	public Sound bounceSound;
	public Sound breakingBlockSound;
	public boolean soundEnabled;

	@Override
	public void create () {
		batch = new SpriteBatch();
		settings = new GameSettings();

		font = new BitmapFont(Gdx.files.internal("introFont.fnt"), Gdx.files.internal("introFont.png"), false); // use libGDX's default Arial font
		bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounceSound.wav"));
		breakingBlockSound = Gdx.audio.newSound(Gdx.files.internal("breakingBlock.wav"));
		soundEnabled = getSettings().isSoundEffectsEnabled();

		this.setScreen(new StartScreen(this));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("musicLoop.wav"));

		backgroundMusic.setVolume(getSettings().getMusicVolume());
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
		breakingBlockSound.dispose();
		bounceSound.dispose();
	}
}
