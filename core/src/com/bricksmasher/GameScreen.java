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
import java.util.Iterator;

public class GameScreen implements Screen {
    final BrickSmasher game;

    Texture platformImage;
    Texture ballImage;
    Texture brickImage;
    Sound bounceSound;
    Sound breakingBlockSound;
    private Array<Rectangle> bricks;

    OrthographicCamera camera;
    Rectangle platform;

    Rectangle ball;
    Rectangle brick;
    float xBallSpeed;
    float yBallSpeed;

    int currentScore;

    public GameScreen(final BrickSmasher game){
        this.game = game;
        platformImage = new Texture(Gdx.files.internal("platform.png"));
        ballImage = new Texture(Gdx.files.internal("ball.png"));
        brickImage = new Texture(Gdx.files.internal("brick.png"));


        bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounceSound.wav"));
        breakingBlockSound = Gdx.audio.newSound(Gdx.files.internal("breakingBlock.wav"));


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        platform = new Rectangle();
        platform.x = 800/2 - 64 / 2;
        platform.y = 20;
        platform.width = 96;
        platform.height = 18;

        // create the raindrops array and spawn the first raindrop
        bricks = new Array<Rectangle>();
        spawnBricks();

        xBallSpeed = (-200);
        yBallSpeed = (-200);

        ball = spawnBall();
    }

    public void spawnBricks(){
        int i = 0;
        int j = 0;
        while(j < 6){
            brick = new Rectangle();
            brick.x =50*i;
            brick.y = 420 - (30*j);
            brick.width = 50;
            brick.height = 30;
            if(i != 0 && i % 15 == 0){
                j = j + 1;
                i = 0;
            }
            else {
                i = i + 1;
            }
            bricks.add(brick);
        }
    }

    @Override
    public void render (float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(platformImage, platform.x, platform.y, platform.width, platform.height);
        game.batch.draw(ballImage, ball.x, ball.y, ball.width, ball.height);
        for(Rectangle brick : bricks){
            if(brick != null){
                game.batch.draw(brickImage, brick.x, brick.y, brick.width, brick.height);
            }
        }

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
        ball.y = 250;
        return ball;
    }
    public void moveBall(){
        ball.x += xBallSpeed * Gdx.graphics.getDeltaTime();
        ball.y += yBallSpeed * Gdx.graphics.getDeltaTime();

        bounce();
    }

    public void bounce(){
        if(ball.x < 0 || ball.x > 800 - ball.width) {
            bounceSound.play();
            xBallSpeed = -xBallSpeed;
        }
        //Ball is approaching from left and hits left half of platform
        if(ball.overlaps(platform) && xBallSpeed > 0 &&  ball.x < platform.x + platform.width/2){
            bounceSound.play();
            yBallSpeed = Math.abs(-yBallSpeed);
            xBallSpeed = -(platform.width/2 - (ball.x - platform.x)) * 10;
        }
        //Ball is approaching from right and hits left half of platform
        else if(ball.overlaps(platform) && xBallSpeed < 0  && ball.x < platform.x + platform.width/2){
            bounceSound.play();
            yBallSpeed = Math.abs(-yBallSpeed);
            xBallSpeed = -((platform.width/2) - (ball.x - platform.x)) * 10;
        }
        //Ball is approaching from left and hits right half of platform
        else if(ball.overlaps(platform) && xBallSpeed > 0 && ball.x > platform.x + platform.width/2){
            bounceSound.play();
            yBallSpeed = Math.abs(-yBallSpeed);
            xBallSpeed = (-platform.width/2 + (ball.x - platform.x)) * 10;
        }
        //Ball is approaching from right and hits right half of platform
        else if(ball.overlaps(platform) && xBallSpeed < 0 && ball.x > platform.x + platform.width/2){
            bounceSound.play();
            yBallSpeed = Math.abs(-yBallSpeed);
            xBallSpeed = (-platform.width/2 + (ball.x - platform.x)) * 10;
        }
        for (Iterator<Rectangle> iter = bricks.iterator(); iter.hasNext(); ) {
            Rectangle brick = iter.next();
            if(ball.overlaps(brick)){
                breakingBlockSound.play();
                yBallSpeed = (-brick.height/2 + (ball.y - brick.y)) * 10;;
                xBallSpeed = (-brick.width/2 + (ball.x - brick.x)) * 10;
                iter.remove();
            }
        }
        //for(int i = 0; i < bricks.size; i++) {
          //  if (bricks.get(i) != null && ball.overlaps(bricks.get(i))) {
            //    breakingBlockSound.play();
              //  yBallSpeed = -yBallSpeed;
               // bricks.set(i, null);
            //}
        //}
        //for(Rectangle brick : bricks) {
          //  if (brick != null && ball.overlaps(brick)) {
            //    breakingBlockSound.play();
            //    yBallSpeed = -yBallSpeed;
            //    brick = null;
            //}
        //}

        if(ball.y > 480 - ball.height){
            bounceSound.play();
            yBallSpeed = -yBallSpeed;
        }
        if(ball.y + 64 < 0) {
            ball = spawnBall();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
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
    }
}
