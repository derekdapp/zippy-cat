package com.derkydapps.gameworld;

import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.derkydapps.TweenAccessors.Value;
import com.derkydapps.TweenAccessors.ValueAccessor;
import com.derkydapps.gameobjects.Cat;
import com.derkydapps.gameobjects.Branch;
import com.derkydapps.gameobjects.ScrollHandler;
import com.derkydapps.gameobjects.Water;
import com.derkydapps.zchelpers.AssetLoader;
import com.derkydapps.zchelpers.InputHandler;

public class GameRenderer {
	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private Vector2 velocity, position, cloudVector, cloudPosition;
	private SpriteBatch batcher;
	private int gameHeight,cloudWidth;
	Random r;
	// Game Objects
	private Cat cat;
	private Water water;
	private ScrollHandler scroller;
	private Branch branch1, branch2, branch3, branch4, branch5;

	// Game Assets
	private Animation catAnimation, waterAnimation;
	private TextureRegion  leftBranch, rightBranch;
	
	// Tween stuff
    private TweenManager manager;
    private Value alpha = new Value();
    
    private Stage stage;


    // Buttons
  //  private List<SimpleButton> menuButtons;
    public static TextureAtlas buttonAtlas;
	public static TextButtonStyle buttonStyle, homebuttonStyle, leadbuttonStyle, donatebuttonstyle, donate1style, donate2style, donate3style, generalstyle, skinstyle;
	public static Button playbutton, homeButton, leadbutton, donatebutton, donate1,donate2,donate3,skinButton;
	public static TextButton  orange, bw;
	public static Skin skin;
	
	FitViewport viewp;


	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		myWorld = world;
		
		r = new Random();
		this.gameHeight = gameHeight;
		velocity = new Vector2(-1,.15f);
		position = new Vector2(136,35);
		cloudVector = new Vector2(-10,0);
		cloudPosition = new Vector2(-300,0);
		
		skin = new Skin();
		buttonAtlas = new TextureAtlas("buttonsheet.pack");
		skin.addRegions(buttonAtlas);
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("playbutton");
		buttonStyle.over = skin.getDrawable("playbuttondown");
		buttonStyle.down = skin.getDrawable("playbuttondown");
		leadbuttonStyle = new TextButtonStyle();
		leadbuttonStyle.up = skin.getDrawable("leader");
		leadbuttonStyle.over = skin.getDrawable("leaderdown");
		leadbuttonStyle.down = skin.getDrawable("leaderdown");
		homebuttonStyle = new TextButtonStyle();
		homebuttonStyle.up = skin.getDrawable("homebutton");
		homebuttonStyle.over = skin.getDrawable("homebuttondown");
		homebuttonStyle.down = skin.getDrawable("homebuttondown");
		donatebuttonstyle = new TextButtonStyle();
		donatebuttonstyle.up = skin.getDrawable("donate");
		donatebuttonstyle.over = skin.getDrawable("donate");
		donatebuttonstyle.down = skin.getDrawable("donate");
		donate1style = new TextButtonStyle();
		donate1style.up = skin.getDrawable("donate1");
		donate1style.over = skin.getDrawable("donate1");
		donate1style.down = skin.getDrawable("donate1");
		donate2style = new TextButtonStyle();
		donate2style.up = skin.getDrawable("donate2");
		donate2style.over = skin.getDrawable("donate2");
		donate2style.down = skin.getDrawable("donate2");
		donate3style = new TextButtonStyle();
		donate3style.up = skin.getDrawable("donate3");
		donate3style.over = skin.getDrawable("donate3");
		donate3style.down = skin.getDrawable("donate3");
		generalstyle = new TextButtonStyle();
		generalstyle.up = skin.getDrawable("water");
		generalstyle.over = skin.getDrawable("water");
		generalstyle.down = skin.getDrawable("water");
		generalstyle.font= AssetLoader.font2;
		skinstyle = new TextButtonStyle();
		skinstyle.up = skin.getDrawable("skin");
		skinstyle.over = skin.getDrawable("skin");
		skinstyle.down = skin.getDrawable("skin");
		
		orange = new TextButton("Orange and White", generalstyle);
		orange.setPosition(3, 3);
		orange.setHeight(40);
		orange.setWidth(130);
		orange.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	     		AssetLoader.setCatSkin(0);
	     		AssetLoader.reloadSkin();
	     		catAnimation = AssetLoader.catAnimation;
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		myWorld.setCurrentStateMenu();
	     	}
	    });
		
		bw = new TextButton("Black and White", generalstyle);
		bw.setPosition(3, 46);
		bw.setHeight(40);
		bw.setWidth(130);
		bw.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	    		AssetLoader.setCatSkin(1);
	    		AssetLoader.reloadSkin();
	    		catAnimation = AssetLoader.catAnimation;
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		myWorld.setCurrentStateMenu();
	     	}
	    });
		skinButton = new Button();
		skinButton.setStyle(skinstyle);
		skinButton.setPosition(0, gameHeight-17);
		skinButton.setHeight(17);
	    skinButton.setWidth(46);
		skinButton.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	     		if(AssetLoader.getDonateState()){
	    		myWorld.setCurrentStateSkin();}			
	     		
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		
	     	}


	    });
		homeButton = new Button();
		homeButton.setStyle(homebuttonStyle);
		homeButton.setPosition(72, gameHeight-48);
		homeButton.setHeight(40);
	    homeButton.setWidth(56);
		homeButton.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	     		
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		myWorld.setCurrentStateMenu();
	     	}


	    });
		
		donatebutton = new Button();
		donatebutton.setStyle(donatebuttonstyle);
		donatebutton.setPosition(90, gameHeight-17);
		donatebutton.setHeight(17);
	    donatebutton.setWidth(46);
		donatebutton.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	    		if(myWorld.isDonate()){
	    			myWorld.setCurrentStateMenu();
	    		}else
	     		myWorld.setCurrentStateDonate();
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		//myWorld.setCurrentStateMenu();
	     	}
	    });
		donate1 = new Button();
		donate1.setStyle(donate1style);
		donate1.setPosition(3, 0);
		donate1.setHeight(32);
	    donate1.setWidth(128);
		donate1.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	     		AssetLoader.getGame().donate1();
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		myWorld.setCurrentStateMenu();
	     	}


	    });
		donate2 = new Button();
		donate2.setStyle(donate2style);
		donate2.setPosition(3, 33);
		donate2.setHeight(32);
	    donate2.setWidth(128);
		donate2.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	     		AssetLoader.getGame().donate2();
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		myWorld.setCurrentStateMenu();
	     	}


	    });
		donate3 = new Button();
		donate3.setStyle(donate3style);
		donate3.setPosition(3, 66);
		donate3.setHeight(32);
	    donate3.setWidth(128);
		donate3.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	     		AssetLoader.getGame().donate3();
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		myWorld.setCurrentStateMenu();
	     	}


	    });
		playbutton = new Button();
		playbutton.setStyle(buttonStyle);
		
		playbutton.setHeight(40);
	    playbutton.setWidth(56);
		playbutton.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	    		myWorld.setCurrentStateReady();
	    		myWorld.restart();
	    		cloudPosition.x=140;
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	    		
	     		
	     		Gdx.input.setInputProcessor(new GestureDetector(new InputHandler(myWorld)));
	     		
	     	}


	    });
		
		leadbutton = new Button();
		leadbutton.setStyle(leadbuttonStyle);
		leadbutton.setPosition(40, gameHeight-75);
		leadbutton.setHeight(40);
	    leadbutton.setWidth(56);
		leadbutton.addListener(new InputListener(){
	    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	    		AssetLoader.getGame().getLeaderboardGPGS();
	     		return true;
	     	}
	    	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	    		
	     		
	     		Gdx.input.setInputProcessor(new GestureDetector(new InputHandler(myWorld)));
	     		
	     	}


	    });
        
	//	this.menuButtons = (List<SimpleButton>) ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();
	

		
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);


		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		viewp = new FitViewport(136, gameHeight, cam);
		stage = new Stage(viewp, batcher);
		
		//stage.getViewport().update(136,gameHeight, true);
	
		stage.clear();
		stage.addActor(playbutton);
		stage.addActor(homeButton);
		//Gdx.input.setInputProcessor(stage);
		
		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
		setupTweens();
	}
	  private void setupTweens() {
	        Tween.registerAccessor(Value.class, new ValueAccessor());
	        manager = new TweenManager();
	        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
	                .start(manager);
	    }
	private void initGameObjects() {
		cat = myWorld.getCat();
		scroller = myWorld.getScroller();
		water = myWorld.getWater();
		branch1 = scroller.getPipe1();
		branch2 = scroller.getPipe2();
		branch3 = scroller.getPipe3();
		branch4 = scroller.getPipe4();
		branch5 = scroller.getPipe5();
	}

	private void initAssets() {
		catAnimation = AssetLoader.catAnimation;
		waterAnimation = AssetLoader.waterAnimation;	
	}

	

	private void drawBranches() {
		
		
		//draws random branches
		if(branch1.getLeftCode() == 1){
			leftBranch = AssetLoader.branchleft1;
		}else if(branch1.getLeftCode()==2){
			leftBranch = AssetLoader.branchleft2;
		}else{
			leftBranch = AssetLoader.branchleft3;
		}
		if(branch1.getRightCode() == 1){
			rightBranch = AssetLoader.branchright1;
		}else if(branch1.getRightCode()==2){
			rightBranch = AssetLoader.branchright2;
		}else{
			rightBranch = AssetLoader.branchright3;
		}
		batcher.draw(leftBranch, branch1.getX(), branch1.getY(), branch1.getWidth(),
				branch1.getHeight());
		batcher.draw(rightBranch, branch1.getX() + 136+ 40, branch1.getY(),
				branch1.getWidth(), branch1.getHeight());
		
		if(branch2.getLeftCode() == 1){
			leftBranch = AssetLoader.branchleft1;
		}else if(branch2.getLeftCode()==2){
			leftBranch = AssetLoader.branchleft2;
		}else{
			leftBranch = AssetLoader.branchleft3;
		}
		if(branch2.getRightCode() == 1){
			rightBranch = AssetLoader.branchright1;
		}else if(branch2.getRightCode()==2){
			rightBranch = AssetLoader.branchright2;
		}else{
			rightBranch = AssetLoader.branchright3;
		}
		batcher.draw(leftBranch, branch2.getX(), branch2.getY(), branch2.getWidth(),
			branch2.getHeight());
		batcher.draw(rightBranch, branch2.getX() + 136+ 40, branch2.getY(),
				branch2.getWidth(), branch2.getHeight());
		if(branch3.getLeftCode() == 1){
			leftBranch = AssetLoader.branchleft1;
		}else if(branch3.getLeftCode()==2){
			leftBranch = AssetLoader.branchleft2;
		}else{
			leftBranch = AssetLoader.branchleft3;
		}
		if(branch3.getRightCode() == 1){
			rightBranch = AssetLoader.branchright1;
		}else if(branch3.getRightCode()==2){
			rightBranch = AssetLoader.branchright2;
		}else{
			rightBranch = AssetLoader.branchright3;
		}

		batcher.draw(leftBranch, branch3.getX(), branch3.getY(), branch3.getWidth(),
			branch3.getHeight());
		batcher.draw(rightBranch, branch3.getX() + 136+ 40, branch3.getY(),
				branch3.getWidth(), branch3.getHeight());
		if(branch4.getLeftCode() == 1){
			leftBranch = AssetLoader.branchleft1;
		}else if(branch4.getLeftCode()==2){
			leftBranch = AssetLoader.branchleft2;
		}else{
			leftBranch = AssetLoader.branchleft3;
		}
		if(branch4.getRightCode() == 1){
			rightBranch = AssetLoader.branchright1;
		}else if(branch4.getRightCode()==2){
			rightBranch = AssetLoader.branchright2;
		}else{
			rightBranch = AssetLoader.branchright3;
		}
		
		batcher.draw(leftBranch, branch4.getX(), branch4.getY(), branch4.getWidth(),
				branch4.getHeight());
		batcher.draw(rightBranch, branch4.getX() + 136+ 40, branch4.getY(),
				branch4.getWidth(), branch4.getHeight());
		if(branch5.getLeftCode() == 1){
			leftBranch = AssetLoader.branchleft1;
		}else if(branch5.getLeftCode()==2){
			leftBranch = AssetLoader.branchleft2;
		}else{
			leftBranch = AssetLoader.branchleft3;
		}
		if(branch5.getRightCode() == 1){
			rightBranch = AssetLoader.branchright1;
		}else if(branch5.getRightCode()==2){
			rightBranch = AssetLoader.branchright2;
		}else{
			rightBranch = AssetLoader.branchright3;
		}
		
		batcher.draw(leftBranch, branch5.getX(), branch5.getY(), branch5.getWidth(),
				branch5.getHeight());
		batcher.draw(rightBranch, branch5.getX() + 136+ 40, branch5.getY(),
				branch5.getWidth(), branch5.getHeight());
		
	}
	
	public void render(float delta,float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cloudPosition.add(cloudVector.cpy().scl(delta));
		if(cloudPosition.x < -100){
			cloudPosition.x=137;
			cloudPosition.y= r.nextInt(gameHeight-30);
			cloudWidth= r.nextInt(50)+50;
		}
		batcher.begin();
		batcher.draw(buttonAtlas.findRegion("bg"), 0, 0,136, gameHeight);
		batcher.draw(buttonAtlas.findRegion("cloud"),cloudPosition.x, cloudPosition.y,cloudWidth,cloudWidth*.64f);
		//Draw Branches
		drawBranches();
		batcher.enableBlending();

	
		if(cat.isAlive() == false){
			batcher.draw(AssetLoader.deathFrame, cat.getX(), cat.getY(),
					cat.getWidth() / 2.0f, cat.getHeight() / 2.0f,
					cat.getWidth(), cat.getHeight(), 1, 1, cat.getRotation());
		}
		else if(branch1.isScrolling() || branch2.isScrolling() ||branch3.isScrolling() ||branch4.isScrolling() ||branch5.isScrolling()) {
			batcher.draw(AssetLoader.jumpFrame, cat.getX(), cat.getY(),
				cat.getWidth() / 2.0f, cat.getHeight() / 2.0f,
				cat.getWidth(), cat.getHeight(), 1, 1, cat.getRotation());

		} else {
			batcher.draw(catAnimation.getKeyFrame(runTime, true), cat.getX(),
					cat.getY(), cat.getWidth() / 2.0f,
					cat.getHeight() / 2.0f, cat.getWidth(), cat.getHeight(),
					1, 1, cat.getRotation());
		}
		batcher.draw(waterAnimation.getKeyFrame(runTime, true), water.getPositionx(),
				water.getPositiony()-23, 136,
				24);
		batcher.draw(buttonAtlas.findRegion("water"),water.getPositionx(), water.getPositiony(), water.getWidth(), water.getHeight());
		
		batcher.end();
		
		// Convert integer into String
		String score = myWorld.getScore() + "";
		
		
		if(myWorld.isGameOver()){
			batcher.begin();
			batcher.draw(buttonAtlas.findRegion("underlay"), 8, 8,120, 144);
			AssetLoader.font2.draw(batcher, "High Score: "+AssetLoader.getHighScore(),  (136 / 4)
						- (3 * score.length() - 1) , (gameHeight/14)+25);
			AssetLoader.font2.draw(batcher, "Games Played: "+AssetLoader.getGameCount(),  (136 / 6)
					- (3 * score.length() - 1) , (gameHeight/14)+40);
			AssetLoader.font2.draw(batcher, "Total Hops: "+AssetLoader.getHopCount(),  (136 / 4)
					- (3 * score.length() - 1) , (gameHeight/14)+55);
			int scoreint = myWorld.getScore();
			if(scoreint < 15){
			batcher.draw(buttonAtlas.findRegion("paper"), 12, 86, 30,30);}
			else if (scoreint < 30){
				batcher.draw(buttonAtlas.findRegion("paper"), 12, 86, 30,30);
				batcher.draw(buttonAtlas.findRegion("bronze"), 53, 86, 30,30);
			}else if (scoreint < 50){
				batcher.draw(buttonAtlas.findRegion("paper"), 12, 86, 30,30);
				batcher.draw(buttonAtlas.findRegion("bronze"), 53, 86, 30,30);
				batcher.draw(buttonAtlas.findRegion("silver"), 94, 86, 30,30);
			}else if (scoreint < 100){
				batcher.draw(buttonAtlas.findRegion("paper"), 12, 86, 30,30);
				batcher.draw(buttonAtlas.findRegion("bronze"), 53, 86, 30,30);
				batcher.draw(buttonAtlas.findRegion("silver"), 94, 86, 30,30);
					batcher.draw(buttonAtlas.findRegion("gold"), 30, 118, 30,30);
			}else if (scoreint >= 100){
				batcher.draw(buttonAtlas.findRegion("paper"), 12, 86, 30,30);
				batcher.draw(buttonAtlas.findRegion("bronze"), 53, 86, 30,30);
				batcher.draw(buttonAtlas.findRegion("silver"), 94, 86, 30,30);
					batcher.draw(buttonAtlas.findRegion("gold"), 30, 118, 30,30);
					batcher.draw(buttonAtlas.findRegion("plat"), 75, 118, 30,30);}
				
			
		
			batcher.end();
			Gdx.input.setInputProcessor(stage);
			playbutton.setPosition(8, gameHeight-48);
			stage.clear();
			stage.addActor(playbutton);
			stage.addActor(homeButton);
			stage.act(delta);
			stage.draw();
			}
		batcher.begin();
		
				if(myWorld.isReady()){
					batcher.draw(buttonAtlas.findRegion("overlay"), 0, gameHeight-70,136, 64);
				}
				// Draw shadow first
				AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2)
						- (3 * score.length()), gameHeight/14);
				// Draw text (score)
				AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2)
						- (3 * score.length() - 1), gameHeight/14);
				batcher.end();
				
				//drawMenuUI();
				if(myWorld.isMenu()){
					batcher.begin();
					if(position.y > 40 || position.y <30)
						velocity.y = velocity.y *-1;
					batcher.draw(buttonAtlas.findRegion("bg"), 0, 0,136, gameHeight);
					batcher.draw(buttonAtlas.findRegion("cloud"),cloudPosition.x, cloudPosition.y,cloudWidth,cloudWidth*.64f);
					batcher.draw(buttonAtlas.findRegion("title"), 0, position.y,136, 24);
					
					
					if(position.x < - 5600)
							position.x = 136;
					
					position.add(velocity).cpy().scl(delta);
					AssetLoader.font3.draw(batcher, "New in V.1.0.1: everything.                                     Tap or Swipe up to jump...                          Swipe right to hop over gaps...                                 Tip: Zippy Cat is able to jump until his back feet cross the edge of the branch.                                                        Note: Jumps are registered when your finger lifts up from the screen                                          Zippy Cat HATES water!                                Follow us on... Twitter: @derkydapps         Instagram: @derkydapps         Kik: @derkydapps                               Snapchat?                                              Five star ratings bring me great joy.                                    Check out our other apps like Cat or Dog!                                       Thanks for Playing!                                                          A bit about myself:  My name's Derek.                    I'm 18.                      I like to party.                                   I also enjoy romantic comedies.                               Donating any amount will unlock new and exciting features.                                                                 Always Ad Free!                          Don't be scared to support!...        college is expensive.                                                   Problems with the game? Email me, I'll work on it.          derkydapps@gmail.com                                         The skins section allows you to change your cat. New textures are coming.", position.x, 0);
					batcher.end();
					if(myWorld.isMenu())
						Gdx.input.setInputProcessor(stage);
					playbutton.setPosition(40, gameHeight- 120);
					
					stage.clear();
					stage.addActor(playbutton);
					stage.addActor(leadbutton);
					stage.addActor(donatebutton);
					stage.addActor(skinButton);
					stage.act(delta);
					stage.draw();
				}
				if(myWorld.isDonate()){
					batcher.begin();	
					batcher.draw(buttonAtlas.findRegion("bg"), 0, 0,136, gameHeight);
					
					batcher.end();
					
					stage.clear();
					stage.addActor(donate1);
					stage.addActor(donate2);
					stage.addActor(donate3);
					
					stage.addActor(donatebutton);
					stage.act(delta);
					stage.draw();
				}
				if(myWorld.isSkin()){
					batcher.begin();	
					batcher.draw(buttonAtlas.findRegion("bg"), 0, 0,136, gameHeight);
					batcher.end();
					stage.clear();
					stage.addActor(orange);
					stage.addActor(bw);
					stage.act();stage.draw();
					
					
					
				}
				
		
	
			
		
		
		drawTransition(delta);

	}
	private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }
	public static void dispose() {
		buttonAtlas.dispose();
		skin.dispose();
	}
	
}
