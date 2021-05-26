package com.hero.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;


//Her-o the Shadow Slayer
public class HeroScreen implements Screen {

    private OrthographicCamera orthoCamera;
    public static float SCREEN_WIDTH = 1280;
    public static float SCREEN_HEIGHT = 720;
    private Hero game;
    private Preferences preferences;
    Music openingSong = Gdx.audio.newMusic(Gdx.files.internal("chamberofgoddess.mp3"));
    private Stage titleScreen;
    private Texture heroTexture;
    private Texture playTexture, instText;
    private Image heroImage;
    private Drawable playDraw, instDraw;
    private ImageButton playButton, instButton;

    HeroScreen(Hero game, Preferences preferences){
        this.game = game;
        this.preferences = preferences;
    }
    @Override
    public void show() {
        heroTexture = new Texture("titlescreen.png");
        playTexture = new Texture("playbutton.png");
        heroImage = new Image(heroTexture);
        playDraw = new TextureRegionDrawable(new TextureRegion(playTexture));
        playButton = new ImageButton(playDraw);
        playButton.setTransform(true);
        playButton.setScale(0.7F);

        instText = new Texture("instbutton.png");
        instDraw = new TextureRegionDrawable(new TextureRegion(instText));
        instButton = new ImageButton(instDraw);
        instButton.setTransform(true);
        instButton.setScale(0.2F);

        orthoCamera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        titleScreen = new Stage(new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT, orthoCamera));
        titleScreen.addActor(heroImage);
        titleScreen.addActor(playButton);
        titleScreen.addActor(instButton);

        Gdx.input.setInputProcessor(titleScreen);
        playButton.setPosition(SCREEN_WIDTH/2 - 200, 10);
        playButton.addListener(new ClickListener(){
            public void clicked(InputEvent input, float x, float y){
                openingSong.stop();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainScreen(game, preferences));
            }
        });
        instButton.setPosition(SCREEN_WIDTH/2 + 200, 10);
        instButton.addListener(new ClickListener(){
            public void clicked(InputEvent input, float x, float y){
                openingSong.stop();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new InstructionScreen(game, preferences));
            }
        });
        openingSong.setVolume(1.0F);
        openingSong.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        titleScreen.act();
        titleScreen.draw();
    }

    @Override
    public void resize(int width, int height) {
        titleScreen.getViewport().update(width, height, true);

        playButton.setSize(600, 200);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        heroTexture.dispose();
        titleScreen.dispose();
        openingSong.dispose();

    }
}
