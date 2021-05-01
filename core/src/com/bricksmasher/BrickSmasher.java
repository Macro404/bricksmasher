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

public class BrickSmasher extends ApplicationAdapter {
	private Texture platformImage;
	private Texture ballImage;

	private Sound bounceSound;
	private Sound breakingBlockSound;
	private Music backgroundMusic;
	private Rectangle platform;
	private Rectangle ball;
	private float xBallSpeed;
	private float yBallSpeed;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	@Override
	public void create () {
		platformImage = new Texture(Gdx.files.internal("platform.png"));
		ballImage = new Texture(Gdx.files.internal("ball.png"));

		bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounceSound.wav"));
		breakingBlockSound = Gdx.audio.newSound(Gdx.files.internal("breakingBlock.wav"));
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("musicLoop.wav"));


		backgroundMusic.setLooping(true);
		backgroundMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		platform = new Rectangle();
		platform.x = 800/2 - 64 / 2;
		platform.y = 20;
		platform.width = 96;
		platform.height = 18;

		xBallSpeed = (-200);
		yBallSpeed = (-200);

		ball = spawnBall();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(platformImage, platform.x, platform.y, platform.width, platform.height);
		batch.draw(ballImage, ball.x, ball.y, ball.width, ball.width);
		batch.end();

		move(camera, platform);
		moveBall();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void move(Camera camera, Rectangle platform){
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			platform.x = touchPos.x - 64 / 2;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) platform.x -= 400 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) platform.x += 400 * Gdx.graphics.getDeltaTime();
		if(platform.x < 0) platform.x = 0;
		if(platform.x > 800 - platform.width) platform.x = 800 - platform.width;
	}

	private Rectangle spawnBall(){
		Rectangle ball = new Rectangle();

		ball.width = 12;
		ball.height = 12;
		ball.x = 200/2 - ball.width;
		ball.y = 320;
		return ball;
	}
	private void moveBall(){
		ball.x += xBallSpeed * Gdx.graphics.getDeltaTime();
		ball.y += yBallSpeed * Gdx.graphics.getDeltaTime();
		if(ball.x < 0 || ball.x > 800 - ball.width) {
			xBallSpeed = -xBallSpeed;
		}
		if((ball.x > platform.x && ball.x < platform.x + platform.width) && (ball.y >= platform.y && ball.y <= platform.y + platform.height && yBallSpeed<0)){
			yBallSpeed = -yBallSpeed;
		}
		if(ball.y > 480 - ball.height){
			yBallSpeed = -yBallSpeed;
		}
		if(ball.y + 64 < 0) {
			ball = spawnBall();
		}
	}
}
