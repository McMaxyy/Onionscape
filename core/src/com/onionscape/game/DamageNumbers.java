package com.onionscape.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

import scenes.FightScene;
import storage.Storage;

public class DamageNumbers extends Actor {
    private BitmapFont font;
    private Color color;
    private float damage;
    private float timer;
    private Storage storage;   
    public boolean isTimerExpired() {
        return timer <= 0;
    }
    private static final float FALL_SPEED = 100f;
    private String text;
    
    public DamageNumbers(float x, float y, float damage, String text) {
    	storage = Storage.getInstance();
    	if(text != null)
    		this.font = storage.fontMedium;
    	else
    		this.font = storage.fontBig;
    	this.color = Color.WHITE;
        this.damage = damage;
        this.timer = 2.0f;
        this.text = text;
        setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        timer -= delta;

        // Fading effect based on the timer
        Color modifiedColor = new Color(color);
        modifiedColor.a = Math.max(0, timer / 2.0f);
        font.setColor(modifiedColor);

        // Move the damage number downward
        float deltaY = FALL_SPEED * delta;
        setPosition(getX(), getY() - deltaY);

        if (timer <= 0) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        String displayText = (text != null) ? text : String.format("%.0f", damage);
        if(text == null)
        	font.draw(batch, displayText, getX(), getY());
        else {
        	GlyphLayout layout = new GlyphLayout(storage.fontMedium, text);
        	float textWidth = layout.width;
        	font.draw(batch, displayText, getX() - textWidth / 2f, getY());
        }
    }
}