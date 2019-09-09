package com.mygdx.myszjumps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Coin extends Rectangle{

    private Texture texture;

    public Coin (Texture texture) {

        this.texture = texture;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
    }


    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}
