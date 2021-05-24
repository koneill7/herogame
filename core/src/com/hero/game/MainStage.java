package com.hero.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    private Boolean moveRight = false;
    private Boolean moveLeft = false;

    private Boolean enemyLeft = true;
    private Boolean enemyRight = false;
    private Texture buttonTextL, buttonTextR;
    private Drawable buttonDrawL, buttonDrawR;
    private ImageButton buttonL, buttonR;

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

        ground = worldGen.makeBody(0.0F, -1.0F, 20F, 3F, 0.5F, 0);
        left = worldGen.makeBody(-1F, 0.0F, 1.0F, 20F, 0.5F, 1);
        right = worldGen.makeBody(21F, 0.0F, 1.0F, 20F, 0.5F, 2);

        buttonGen(); //button gen needs to be after player and enemy gen
        world.setContactListener(new Listener());
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

        if(enemyLeft){enemy.left();}
        else if(enemyRight){enemy.right();}
        else{enemy.stop();}
    }
    @Override public void draw(){
        super.draw();
        render.render(world, getViewport().getCamera().combined);
    }

    private void buttonGen(){
        buttonTextL = new Texture("leftbutton.png");
        buttonTextR = new Texture("rightbutton.png");
        buttonDrawL = new TextureRegionDrawable(new TextureRegion(buttonTextL));
        buttonDrawR = new TextureRegionDrawable(new TextureRegion(buttonTextR));
        buttonL = new ImageButton(buttonDrawL);
        buttonR = new ImageButton(buttonDrawR);
        buttonL.setSize(0.5F, 0.5F);
        buttonR.setSize(0.5F, 0.5F);
        buttonL.setPosition(0.5F, 0.0F);
        buttonR.setPosition(1.2F, 0.0F);
        this.addActor(buttonL);
        this.addActor(buttonR);
        buttonL.addListener(new ActorGestureListener(){
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){ moveLeft = true;}
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){ moveLeft = false; }
        });
        buttonR.addListener(new ActorGestureListener(){
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){ moveRight = true; }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){ moveRight = false; }
        });
    }

    @Override
    public void dispose(){
        mainTexture.dispose();
        mainScreen.dispose();
    }
}
