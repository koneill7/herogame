package com.hero.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MainStage extends Stage {
    private World world;
    private WorldGen worldGen;
    private Body ground, left, right;
    private float actAccum;
    private float step;
    private Box2DDebugRenderer render;

    public MainStage(){
        super(new ExtendViewport(20f, 10f, new OrthographicCamera(20f, 10f)));
        world = new World(new Vector2(0, -10), true);
        worldGen = new WorldGen(world);
        step = 1/300F;
        actAccum = 0.0F;
        render = new Box2DDebugRenderer();

        ground = worldGen.makeBody(0.0F, 0.0F, 20F, 3F, 0.5F, 0);
        left = worldGen.makeBody(-1F, 0.0F, 1.0F, 20F, 0.5F, 1);
        right = worldGen.makeBody(21F, 0.0F, 1.0F, 20F, 0.5F, 2);
    }
    @Override public void act(float timeVal){
        super.act(timeVal);
        actAccum += timeVal;
        while(actAccum >= timeVal){
            world.step(step, 8, 4);
            actAccum -= timeVal;
        }
    }
    @Override public void draw(){
        super.draw();
        render.render(world, getViewport().getCamera().combined);
    }
}
