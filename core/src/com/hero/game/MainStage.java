package com.hero.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class MainStage extends Stage {
    OrthographicCamera orthoCamera;
    public static float SCREEN_WIDTH = 1280;
    public static float SCREEN_HEIGHT = 720;
    public static float CAMERA_X  = SCREEN_WIDTH/2;
    public static float CAMERA_Y = SCREEN_HEIGHT/2;

    private Stage mainScreen;
    private Texture mainTexture;
    private Image mainImage;

    private int health = 1000;
    private Boolean injured = false;
    public World getWorld() {
        return world;
    }

    private World world;
    private WorldGen worldGen;
    private Body ground, left, right;
    private float actAccum;
    private float step;
    private Box2DDebugRenderer render;
    Player player;
    Enemy enemy;
    private Listener listener = new Listener();

    Batch batch = getBatch();
    private Boolean moveRight = false;
    private Boolean moveLeft = false;
    private Boolean jumping = false;

    private Boolean enemyLeft = true;
    private Boolean enemyRight = false;
    private Boolean attack = false;
    private Texture buttonTextL, buttonTextR,attackText, jumpText;
    private Drawable buttonDrawL, buttonDrawR, attackDraw, jumpDraw;
    private ImageButton buttonL, buttonR, attackButton, jumpButton;

    private int score;
    private String newScore;
    BitmapFont bitmapFont;

    public MainStage(){
        super(new ExtendViewport(200F, 100F, new OrthographicCamera(200f, 100f)));
        Gdx.input.setInputProcessor(this);

        mainTexture = new Texture("background.png");
        mainImage = new Image(mainTexture);
        mainImage.setWidth(200F);
        mainImage.setHeight(100F);
        this.addActor(mainImage);

        world = new World(new Vector2(0, -10), true);
        worldGen = new WorldGen(world);
        step = 1/300F;
        actAccum = 0.0F;
        render = new Box2DDebugRenderer();


        player = new Player();
        player.makePlayer(world);
        this.addActor(player);

        enemy = new Enemy();
        enemy.makeEnemy(world);
        this.addActor(enemy);

        ground = worldGen.makeBody(0.0F, -10F, 200F, 30F, 50.0F, 0);
        left = worldGen.makeBody(-10F, 0.0F, 10.0F, 200F, 50.0F, 1);
        right = worldGen.makeBody(210F, 0.0F, 10.0F, 200F, 50.0F, 2);

        buttonGen(); //button gen needs to be after player and enemy gen
        world.setContactListener(listener);

        score = 0;
        newScore = "Score: 0";
        bitmapFont = new BitmapFont();

    }
    @Override public void act(float timeVal){
        super.act(timeVal);
        actAccum += timeVal;
        while(actAccum >= timeVal){
            world.step(step, 8, 4);
            actAccum -= timeVal;
        }

        if(moveLeft){player.left();}
        else if(moveRight){player.right();}
        else{player.stop();}

        if(enemy != null){
            enemyLeft = enemy.left;
            enemyRight = enemy.right;
            if(enemyLeft){enemy.left();}
            else if(enemyRight){enemy.right();}
            else{enemy.stop();}

            if(enemy.getHealth() < 1){
                score += 100;
                newScore = "Score: " + score;

                enemy.remove();
                world.destroyBody(enemy.body);
                enemy = new Enemy();
                enemy.makeEnemy(world);
                this.addActor(enemy);
            }
        }

        if(player != null){
            if(player.health < 1){
                player.remove();
                world.destroyBody(player.playerBod);
                injured = true;
            }
        }

        if(listener.getContactMade() && !attack){
            player.injured();
            health -= 5;
            if(enemyLeft) enemy.right();
            else if (enemyRight) enemy.left();
        }
        if(listener.getContactMade() && attack){
            enemy.injured(50);
        }

        if(listener.getGroundContact()){
            player.land();
        }
    }
    @Override public void draw(){
        super.draw();
        render.render(world, getViewport().getCamera().combined);
        String scoreStr =String.valueOf(score);


        batch.begin();
        bitmapFont.setColor(1.0F, 0.0F, 0.0F, 1.0F);
        bitmapFont.draw(batch, "Health: "+ health, 150, 95);
        bitmapFont.getData().setScale(0.5F);

        if(injured){
            bitmapFont.setColor(0.0F, 0.0F, 1.0F, 1.0F);
            bitmapFont.draw(batch, "Game Over!", 70, 60);
            bitmapFont.getData().setScale(0.7F);
        }

        bitmapFont.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        bitmapFont.draw(batch, "Score: "+ score, 1, 95);
        bitmapFont.getData().setScale(0.5F);
        batch.end();
    }

    private void buttonGen(){
        buttonTextL = new Texture("leftbutton.png");
        buttonTextR = new Texture("rightbutton.png");
        attackText = new Texture("attackbutton.png");
        jumpText = new Texture("jumpbutton.png");
        buttonDrawL = new TextureRegionDrawable(new TextureRegion(buttonTextL));
        buttonDrawR = new TextureRegionDrawable(new TextureRegion(buttonTextR));
        attackDraw = new TextureRegionDrawable(new TextureRegion(attackText));
        jumpDraw = new TextureRegionDrawable(new TextureRegion(jumpText));
        buttonL = new ImageButton(buttonDrawL);
        buttonR = new ImageButton(buttonDrawR);
        attackButton = new ImageButton(attackDraw);
        jumpButton = new ImageButton(jumpDraw);
        buttonL.setSize(8F, 8F);
        buttonR.setSize(8F, 8F);
        attackButton.setSize(12F, 8F);
        jumpButton.setSize(8F, 8F);
        buttonL.setPosition(8F, 8F);
        buttonR.setPosition(16F, 8F);
        attackButton.setPosition(23F, 8F);
        jumpButton.setPosition(35F, 8F);
        this.addActor(buttonL);
        this.addActor(buttonR);
        this.addActor(attackButton);
        this.addActor(jumpButton);
        buttonL.addListener(new ActorGestureListener(){
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){ moveLeft = true;}
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){ moveLeft = false; }
        });
        buttonR.addListener(new ActorGestureListener(){
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){ moveRight = true; }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){ moveRight = false; }
        });
        attackButton.addListener(new ActorGestureListener(){
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){ attack = true; }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){ attack = false; }
        });
        jumpButton.addListener(new ActorGestureListener(){
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){player.jump();}
        });



    }

    @Override
    public void dispose(){
        mainTexture.dispose();
        //mainScreen.dispose();
    }
}
