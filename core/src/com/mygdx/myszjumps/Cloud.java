package com.mygdx.myszjumps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.rmi.activation.ActivationGroup_Stub;

public class Cloud extends Rectangle {
    private Texture texture;
    private float distance;

    public Cloud(Texture texture, float distance){

        this.distance = distance;

        this.texture = texture;
        this.height = texture.getHeight() * distance;
        this.width = texture.getWidth() * distance;
    }
    public void draw (SpriteBatch batch, OrthographicCamera camera) {


        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, distance*0.9f);//set alpha to 0.3
        batch.draw(texture, x, y - camera.position.y/5 * distance, this.width, this.height);
        batch.setColor(c.r, c.g, c.b, 1f);

    }


    public float getDistance()
    {
        return distance;
    }

}
