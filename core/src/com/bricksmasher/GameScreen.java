package com.bricksmasher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final BrickSmasher game;

    Texture platformImage;
    Texture ballImage;
    Sound bounceSound;
    Sound breakingBlockSound;
    Music backgroundMusic;

    OrthographicCamera camera;
    Rectangle platform;

    Rectangle ball;
    float xBallSpeed;
    float yBallSpeed;

    int currentScore;

    public GameScreen(final BrickSmasher game){
        this.game = game;
        platformImage = new Texture(Gdx.files.internal("platform.png"));
        ballImage = new Texture(Gdx.files.internal("ball.png"));

        bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounceSound.wav"));
        breakingBlockSound = Gdx.audio.newSound(Gdx.files.internal("breakingBlock.wav"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("musicLoop.wav"));

        backgroundMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

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
    public void render (float delta) {

        ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(platformImage, platform.x, platform.y, platform.width, platform.height);
        game.batch.draw(ballImage, ball.x, ball.y, ball.width, ball.width);
        game.batch.end();

        move(camera, platform);
        moveBall();
    }

    public void move(Camera camera, Rectangle platform){
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

    public Rectangle spawnBall(){
        Rectangle ball = new Rectangle();

        ball.width = 12;
        ball.height = 12;
        ball.x = 200/2 - ball.width;
        ball.y = 320;
        return ball;
    }
    public void moveBall(){
        ball.x += xBallSpeed * Gdx.graphics.getDeltaTime();
        ball.y += yBallSpeed * Gdx.graphics.getDeltaTime();
        if(ball.x < 0 || ball.x > 800 - ball.width) {
            bounceSound.play();
            xBallSpeed = -xBallSpeed;
        }
        if(ball.overlaps(platform)){
            bounceSound.play();
            yBallSpeed = -yBallSpeed;
        }
        if(ball.y > 480 - ball.height){
            bounceSound.play();
            yBallSpeed = -yBallSpeed;
        }
        if(ball.y + 64 < 0) {
            ball = spawnBall();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        backgroundMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose () {
        platformImage.dispose();
        ballImage.dispose();
        bounceSound.dispose();
        breakingBlockSound.dispose();
        backgroundMusic.dispose();
    }
}
