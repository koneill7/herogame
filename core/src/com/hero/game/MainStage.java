package com.hero.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    FreeTypeFont generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/timesBold.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    private Boolean moveRight = false;
    private Boolean moveLeft = false;
    private Boolean jumping = false;

    private Boolean enemyLeft = true;
    private Boolean enemyRight = false;
    private Boolean attack = false;
    private Texture buttonTextL, buttonTextR,attackText, jumpText;
    private Drawable buttonDrawL, buttonDrawR, attackDraw, jumpDraw;
    private ImageButton buttonL, buttonR, attackButton, jumpButton;

    private int score = 0;
    private String newScore = "Score: 0";
    BitmapFont bitmapFont = new BitmapFont();

    public MainStage(){
        super(new ExtendViewport(20F, 10F, new OrthographicCamera(20f, 10f)));
        Gdx.input.setInputProcessor(this);

        mainTexture = new Texture("background.png");
        mainImage = new Image(mainTexture);
        mainImage.setWidth(20F);
        mainImage.setHeight(10F);
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

        ground = worldGen.makeBody(0.0F, -0.5F, 20F, 3F, 50.0F, 0);
        left = worldGen.makeBody(-1F, 0.0F, 1.0F, 20F, 50.0F, 1);
        right = worldGen.makeBody(21F, 0.0F, 1.0F, 20F, 50.0F, 2);

        buttonGen(); //button gen needs to be after player and enemy gen
        world.setContactListener(listener);



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

        if(listener.getContactMade() && !attack){
            player.injured();

        }
        if(listener.getContactMade() && attack){
            enemy.injured(15);
        }

        if(listener.getGroundContact()){
            player.land();
        }
    }
    @Override public void draw(){
        super.draw();
        render.render(world, getViewport().getCamera().combined);
        getBatch().begin();
        bitmapFont.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        bitmapFont.draw(getBatch(), newScore, 8, 10);
        bitmapFont.getData().setScale(3.0F,0.05F);
        getBatch().end();
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
        buttonL.setSize(0.5F, 0.5F);
        buttonR.setSize(0.5F, 0.5F);
        attackButton.setSize(0.9F, 0.5F);
        jumpButton.setSize(0.5F, 0.5F);
        buttonL.setPosition(0.5F, 0.0F);
        buttonR.setPosition(1.3F, 0.0F);
        attackButton.setPosition(2.2F, 0.0F);
        jumpButton.setPosition(3.1F, 0.0F);
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
        mainScreen.dispose();
    }
}
