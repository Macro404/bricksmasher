package com.bricksmasher;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

public class BrickSmasher extends ApplicationAdapter {
	Texture platformImage;
	Sound bounceSound;
	Sound breakingBlockSound;
	Music backgroundMusic;
	Rectangle platform;

	SpriteBatch batch;
	OrthographicCamera camera;
	Texture img;
	
	@Override
	public void create () {
		platformImage = new Texture(Gdx.files.internal("platform.png"));

		bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounceSound.wav"));
		breakingBlockSound = Gdx.audio.newSound(Gdx.files.internal("breakingBlock.wav"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("musicLoop.wav"));


		backgroundMusic.setLooping(true);
		backgroundMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		platform = new Rectangle();
		platform.x = 800/2 - 64 / 2;
		platform.y = 20;
		platform.width = 64;
		platform.height = 10;

	}

	@Override
	public void render () {
		ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(platformImage, platform.x, platform.y);
		batch.end();

		move(camera, platform);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void move(Camera camera, Rectangle platform){
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			platform.x = touchPos.x - 64 / 2;
		}
	}
}
