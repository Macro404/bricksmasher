package com.bricksmasher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Iterator;

/**
 * Class containing the main game screen, where the in-game logic is executed.
 *
 * @author  Marcus Westlund, Patrik Johansson
 */
public class GameScreen implements Screen {
    final BrickSmasher game;
    GameScreen gameScreen = this;

    Texture platformImage;
    Texture ballImage;
    Texture brickImage;
    private Array<Rectangle> bricks;
    ParticleEffect blockDestruction;

    OrthographicCamera camera;
    Rectangle platform;

    Rectangle ball;
    Rectangle brick;
    float xBallSpeed;
    float yBallSpeed;
    Stage stage;
    float explosionx;
    float explosiony;

    int level;
    int currentScore;

    /**
     * Constructor for the main GameScreen, use the same GameScreen until game over.
     * @param game the game object created on app launch
     */
    public GameScreen(final BrickSmasher game){
        this.game = game;
        level = 1;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        platform = new Rectangle();
        platform.x = 800/2 - 64 / 2;
        platform.y = 20;
        platform.width = 96;
        platform.height = 18;

        bricks = new Array<Rectangle>();

        xBallSpeed = (-200);
        yBallSpeed = (-200);

        ball = spawnBall();
        stage = new Stage(new ScreenViewport());
        spawnBricks();
    }

    /**
     * Spawns bricks in a number of rows depending on the level
     */
    public void spawnBricks(){
        int i = 0;
        int j = 0;
        int numRows;
        switch (level){
            case 1:
                numRows = 2;
                break;
            case 2:
                numRows = 3;
                break;
            case 3:
                numRows = 4;
                break;
            case 4:
                numRows = 5;
                break;
            case 5:
                numRows = 6;
                break;
            default:
                numRows = 6;
                break;
        }
        while(j < numRows){
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

        ScreenUtils.clear(0, 0, 0.1f, 1);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.getData().setScale(0.6f);
        game.font.draw(game.batch, "Score:"+ currentScore, 10, 17);
        game.font.draw(game.batch, "Level "+ level, 690, 17);
        game.batch.draw(platformImage, platform.x, platform.y, platform.width, platform.height);
        game.batch.draw(ballImage, ball.x, ball.y, ball.width, ball.height);
        for(Rectangle brick : bricks){
            if(brick != null){
                game.batch.draw(brickImage, brick.x, brick.y, brick.width, brick.height);
            }
        }

        blockDestruction.setPosition(explosionx, explosiony);
        blockDestruction.draw(game.batch, Math.min(Gdx.graphics.getDeltaTime(), 1/90f));

        game.batch.end();

        move(camera, platform);
        moveBall();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if(bricks.size == 0 && level != 5){
            game.setScreen(new NextLevelScreen(game, this));
        }
        if(bricks.size == 0 && level == 5){
            game.setScreen(new WinScreen(game, currentScore, this));
        }
    }

    /**
     * Method responsible for moving the player platform
     * @param camera object used throughout the class
     * @param platform the Rectangle object to be controlled by the player
     */
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

    /**
     * Spawns a ball at a predetermined location
     * @return the ball
     */
    public Rectangle spawnBall(){
        Rectangle ball = new Rectangle();

        ball.width = 12;
        ball.height = 12;
        ball.x = 200/2 - ball.width;
        ball.y = 250;
        return ball;
    }

    /**
     * Function responsible for updating the balls location based on speed.
     */
    public void moveBall(){
        ball.x += xBallSpeed * Gdx.graphics.getDeltaTime();
        ball.y += yBallSpeed * Gdx.graphics.getDeltaTime();

        bounce();
    }

    /**
     * Function responsible for the balls bouncing logic
     */
    public void bounce(){
        if(ball.x < 0) {
            if(game.soundEnabled){
                game.bounceSound.play(game.getSettings().getSoundVolume());
            }
            xBallSpeed = Math.abs(xBallSpeed);
        }
        if(ball.x > 800 - ball.width){
            if(game.soundEnabled){
                game.bounceSound.play(game.getSettings().getSoundVolume());
            }
            xBallSpeed = -Math.abs(xBallSpeed);
        }
        //Ball is approaching from left and hits left half of platform
        if(ball.overlaps(platform) && xBallSpeed > 0 &&  ball.x < platform.x + platform.width/2){
            if(game.soundEnabled){
                game.bounceSound.play(game.getSettings().getSoundVolume());
            }
            yBallSpeed = Math.abs(-yBallSpeed);
            xBallSpeed = -(platform.width/2 - (ball.x - platform.x)) * 10;
        }
        //Ball is approaching from right and hits left half of platform
        else if(ball.overlaps(platform) && xBallSpeed < 0  && ball.x < platform.x + platform.width/2){
            if(game.soundEnabled){
                game.bounceSound.play(game.getSettings().getSoundVolume());
            }
            yBallSpeed = Math.abs(-yBallSpeed);
            xBallSpeed = -((platform.width/2) - (ball.x - platform.x)) * 10;
        }
        //Ball is approaching from left and hits right half of platform
        else if(ball.overlaps(platform) && xBallSpeed > 0 && ball.x > platform.x + platform.width/2){
            if(game.soundEnabled){
                game.bounceSound.play(game.getSettings().getSoundVolume());
            }
            yBallSpeed = Math.abs(-yBallSpeed);
            xBallSpeed = (-platform.width/2 + (ball.x - platform.x)) * 10;
        }
        //Ball is approaching from right and hits right half of platform
        else if(ball.overlaps(platform) && xBallSpeed < 0 && ball.x > platform.x + platform.width/2){
            if(game.soundEnabled) {
                game.bounceSound.play(game.getSettings().getSoundVolume());
            }
            yBallSpeed = Math.abs(-yBallSpeed);
            xBallSpeed = (-platform.width/2 + (ball.x - platform.x)) * 10;
        }
        for (Iterator<Rectangle> iter = bricks.iterator(); iter.hasNext(); ) {
            Rectangle brick = iter.next();
            if(ball.overlaps(brick)){
                if(game.soundEnabled) {
                    game.breakingBlockSound.play(game.getSettings().getSoundVolume());
                }
                if(!(ball.y+ball.height > brick.y + 4 && ball.y < brick.y + brick.height - 4)){
                    yBallSpeed = (-brick.height/2 + (ball.y - brick.y)) * 10;
                }
                if(!(ball.x > brick.x + 4 && ball.x < brick.x + brick.width - 4)){
                    xBallSpeed = -xBallSpeed;
                }
                explosionx = brick.x + brick.width/2;
                explosiony = brick.y + brick.height/2;
                blockDestruction.start();
                iter.remove();
                currentScore += 10;
            }
        }

        if(ball.y > 480 - ball.height){
            if(game.soundEnabled){
                game.bounceSound.play(game.getSettings().getSoundVolume());
            }
            yBallSpeed = -yBallSpeed;
        }
        if(ball.y + 64 < 0) {
            ball = spawnBall();
            game.setScreen(new LoseScreen(game, currentScore));
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.right();
        table.padTop(-7);
        table.padRight(-8);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        TextButton menu = new TextButton("Menu", skin);
        table.add(menu).width(65).height(45);
        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game, gameScreen));
                dispose();
            }
        });
        platformImage = new Texture(Gdx.files.internal("platform.png"));
        ballImage = new Texture(Gdx.files.internal("ball.png"));
        brickImage = new Texture(Gdx.files.internal("brick.png"));
        blockDestruction = new ParticleEffect();
        blockDestruction.load(Gdx.files.internal("sparks.p"), Gdx.files.internal("Effects"));
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
    }
}
