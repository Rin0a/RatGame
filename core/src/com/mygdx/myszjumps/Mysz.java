package com.mygdx.myszjumps;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.myszjumps.Platform;
import com.mygdx.myszjumps.Cloud;
import com.mygdx.myszjumps.JumpPlayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Preferences;
import java.util.Collections;
import javax.xml.soap.Text;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Mysz extends ApplicationAdapter {

	private Texture playerTexture, platformTexture, cloud1Texture, coinTexture;
	private JumpPlayer player;
	private Array<Platform> platformArray;
	private Array<Cloud> cloud1Array;
	private OrthographicCamera camera;
	private Array<Coin> coinArray;
	private int score;
	private String coinScore;
	private BitmapFont yourBitmapFontName;
	private float genPoint=-1100;//punkt od którego beda sie generowaly platformy

	private ShapeRenderer shapeRenderer;
	private Sound coinSound;
	private Screen endScreen;





	private float gravity = -40;

	private static final int CAMERA_WIDTH = 1200;

	private SpriteBatch batch;

	@Override
	public void create() {
		loadData();//wgrywa wszystkie elementy
		init();
		score = 0;
		coinScore = "Score: 0";
		yourBitmapFontName = new BitmapFont();

		shapeRenderer = new ShapeRenderer();
	}

	private void init() {


		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();


		batch = new SpriteBatch(CAMERA_WIDTH);
		camera = new OrthographicCamera();

		camera = new OrthographicCamera(CAMERA_WIDTH, h * CAMERA_WIDTH / w);


		//2960x1440


		player = new JumpPlayer(playerTexture);
		player.x = CAMERA_WIDTH / 2 - 180 / 2;


		platformArray = new Array<Platform>();
		cloud1Array = new Array<Cloud>();
		coinArray = new Array<Coin>();

		//generuje nam platformy
/*		for (int i = 1; i < 1; i++) {

			Platform p = new Platform(platformTexture);
			p.x = MathUtils.random(CAMERA_WIDTH - 300);//losowo pomiedzy 480 szerokosci
			p.y = 250 * i;
			platformArray.add(p);
		}
*/
		/*for (int i = -5; i < 8; i++) {

			Platform p = new Platform(platformTexture);
			p.x = 320 * i;
			p.y = -58;
			platformArray.add(p);
		}*/

		for (int i = 1; i < 100; i++) {
			Cloud c = new Cloud(cloud1Texture, MathUtils.random(500) / 1000.0f + 0.5f);
			c.y = 100 * i;
			c.x = MathUtils.random(CAMERA_WIDTH * 2) - 1000;//losowo pomiedzy 480 szerokosci
			cloud1Array.add(c);
		}
/*
		for (int i = 1; i < 50; i++) {
			//Collections.shuffle(liczby);

			Platform p = platformArray.get(i);

			if ( MathUtils.random(100) < 40 ) {
				Coin cn = new Coin(coinTexture);
				cn.x = p.x + 145;
				cn.y = p.y + 150;
				coinArray.add(cn);
			}

		}
*/
	}

	private void loadData() {
		playerTexture = new Texture("szczur.png");
		platformTexture = new Texture("platform.png");
		cloud1Texture = new Texture("cloud1.png");
		coinTexture = new Texture("coin.png");
		coinSound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
		//endScreen = new Texture("cloud1.png");
	}

	@Override
	public void render() {
		update();





		//dzielimy na 2 zeby player byl na srodku, y nie dzielimy by byl na dole
		camera.position.set(CAMERA_WIDTH/2, player.y + 50, 0);
		camera.update();


		Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.setProjectionMatrix(camera.combined);


		yourBitmapFontName.setColor(Color.BLACK);
		yourBitmapFontName.getData().setScale(3);


		//laczy widok kamery z batchem, by widok byl tez "z poza kamery" jak obiekt sie rusza

		for (Cloud c : cloud1Array) {
			c.draw(batch, camera);
		}

		for (Platform p : platformArray) {
			p.draw(batch);
		}
		for (Coin cn : coinArray) {
			cn.draw(batch);
		}


		if (player.y>genPoint) {

			Platform p = new Platform(platformTexture);
			p.x = MathUtils.random(CAMERA_WIDTH - 300);//losowo pomiedzy 480 szerokosci
			p.y = genPoint + 1300;
			platformArray.add(p);


			if (MathUtils.random(100) < 40) {
				Coin cn = new Coin(coinTexture);
				cn.x = p.x + 145;
				cn.y = p.y + 150;
				coinArray.add(cn);
			}


			if (platformArray.size > 10) {
				platformArray.removeIndex(0);
			}

			genPoint = genPoint + 250;

			if (player.y < p.y +500) {
				//dispose();
			}
		}

		player.draw(batch);

		yourBitmapFontName.draw(batch, coinScore, CAMERA_WIDTH-300, player.y+1230);



		batch.end();

//		//draw getPoint
//
//		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//		shapeRenderer.setProjectionMatrix(camera.combined);
//		shapeRenderer.setColor(0, 0, 0, 1); // Red line
//		shapeRenderer.line(-1000, genPoint, 2000, genPoint);
//		shapeRenderer.end();

	}

	private void update() {


		for (Cloud c : cloud1Array) {
			c.setX(c.x - Gdx.graphics.getDeltaTime() * c.getDistance() * c.getDistance() * 200);

			if (c.x < -1500) {
				c.x = 3000;
			}
		}

		float accelX = Gdx.input.getAccelerometerX();
		//Gdx.app.log("dorota", "accelX = " + accelX);

		player.x -= accelX * Gdx.graphics.getDeltaTime() * 200;

		handleInput();


		player.y += player.jumpVelocity * Gdx.graphics.getDeltaTime();

		//logika skakania i grawitacji//

		//sprawdza czy nie jest na platformie (by nie spadl ponizej podlogi)
		if (player.y > 0) {
			player.jumpVelocity += gravity;
		} else {
			player.y = 0;
			player.canJump = true;
			player.jumpVelocity = 0;
		}

		//logika gdy jestesmy na platformie
		for (Platform p : platformArray) {
			isPlayerOnPlatform(p);

			//jesli gracz jest na platformie to:
			if (isPlayerOnPlatform(p)) {
				player.canJump = true;//moze skoczyc
				player.jumpVelocity = 0; //predkosc jest zero poki nie kliknie
				player.y = p.y + p.height; //y gracza jest jeest y platformy i jej wysokoscia
			}


			for (Coin cn : coinArray) {
				if (cn.x+30 > player.x && cn.x < player.x+180 &&
						cn.y+30 > player.y && cn.y < player.y+132) {

					coinSound.play();
					cn.x=-1000000;
					score++;
					coinScore = "Score: " + score;
					//Gdx.app.log("dorota", "coinScore = " + cn.x);
				}
			}

		}


		if (player.x > CAMERA_WIDTH) {
			player.x = CAMERA_WIDTH;
		}
		if (player.x < 0) {
			player.x = 0;
		}
	}



	private boolean isPlayerOnPlatform(Platform p) {
		//jesli ponizsze warunki sa spelnione to gracz jest na platformie
		return player.jumpVelocity <= 0 && player.overlaps(p) && !(player.y <= p.y);
	}
//	private void gameEnd() {
//		setScreen
//	}


	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.x -= 500 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.x += 500 * Gdx.graphics.getDeltaTime();
		}
		//dla klikniecia myszka do skoku
		player.jump();
	}

	@Override
	public void dispose() {
		batch.dispose();
		platformTexture.dispose();
		playerTexture.dispose();
		cloud1Texture.dispose();
	}
}