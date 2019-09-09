package com.mygdx.myszjumps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public abstract class MenuScreen implements Screen {
    SpriteBatch batch;
    Texture img;
    TextButton playButton;
    Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));


//    public void initPlayButton() {
//        playButton = new TextButton("Play!",mySkin,"small");
//        playButton.setSize(100,35);
//        playButton.setPosition(100*7,Gdx.graphics.getHeight()-35*3);
//        playButton.addListener(new ClickListener(){
//            touchDown(InputEvent event, float x, float y, int pointer, int button) {
//            return MenuScreen.this.clone();
//            }
//
//        });
//
//    }
//    @Override
//    public void show() {
//        batch = new SpriteBatch();
//        img = new Texture("szczur.jpg");
//        initPlayButton();
//
//
//    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 150, 300);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        img.dispose();
        batch = null;
        img = null;
    }

    @Override
    public void dispose() {

    }
}
