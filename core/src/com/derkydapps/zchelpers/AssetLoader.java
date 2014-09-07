package com.derkydapps.zchelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import com.derkydapps.zippycat.ActionResolver;


public class AssetLoader {

	public static Texture logoTexture, buttonTexture,branch,sheet, waterTexture;
	
	public static TextureRegion logo,buttonUp, buttonDown;

	public static TextureRegion  branchleft1, branchleft2, branchleft3, branchright1, branchright2, branchright3,jumpFrame, deathFrame;
	

	public static Sound dead, flap, coin;

	public static BitmapFont font, shadow, font2,font3;
	
	public static TextureRegion[] sheet_frames;
	//public static TextureRegion current_frame;

	public static Animation catAnimation, waterAnimation;
	private static Preferences prefs;

	private static ActionResolver game;
	private static boolean donated;
	public static void load(ActionResolver Game, boolean Donated) {
	game = Game;
	donated = Donated;	
	// Create (or retrieve existing) preferences file
	prefs = Gdx.app.getPreferences("ZippieCat");

	if (!prefs.contains("highScore")) {
		prefs.putInteger("highScore", 0);
	}
	if (!prefs.contains("hopCount")) {
		prefs.putInteger("highScore", 0);
	}
	if (!prefs.contains("gameCount")) {
		prefs.putInteger("highScore", 0);
	}
		
		buttonTexture = new Texture(Gdx.files.internal("buttonsheet.png"));
		buttonUp = new TextureRegion(buttonTexture,0,0,64,40);
		buttonDown = new TextureRegion(buttonTexture,0,64,64,40);
		
		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
	    logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    logo = new TextureRegion(logoTexture, 0, 0, 512, 114);    
		
	    if (!prefs.contains("catSkin")) {
			prefs.putInteger("catSkin", 0);
		}
	    int tempint = prefs.getInteger("catSkin");
	    if(tempint ==0){
		sheet = new Texture(Gdx.files.internal("spritesheet.png"));}
	    else if(tempint==1){
	    sheet = new Texture(Gdx.files.internal("blackwhitesheet.png"));}
	    
		TextureRegion[][] temp = TextureRegion.split(sheet, 64, 48);
		sheet_frames = new TextureRegion[5];
		
		
		sheet_frames[0] = temp[0][0];
		sheet_frames[0].flip(false, true);
		sheet_frames[1] = temp[0][1];
		sheet_frames[1].flip(false, true);
		sheet_frames[2] = temp[0][2];
		sheet_frames[2].flip(false, true);
		sheet_frames[3] = temp[0][3];
		sheet_frames[3].flip(false, true);
		sheet_frames[4] = temp[1][0];
		sheet_frames[4].flip(false, true);
		
		
		catAnimation = new Animation(0.1F, sheet_frames);
		jumpFrame= temp[1][1];
		jumpFrame.flip(false, true);
		deathFrame = temp[1][2];
		deathFrame.flip(false, true);
		
		

		//texture = new Texture(Gdx.files.internal("data/texture.png"));
		//texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		branch = new Texture("branchsheet.png");
		temp = TextureRegion.split(branch, 136, 16);
		
		branchleft1 = temp[0][0];
		branchleft1.flip(false, true);
		branchleft2 = temp[1][0];
		branchleft2.flip(false, true);
		branchleft3 = temp[2][0];
		branchleft3.flip(false, true);
		branchright1 = temp[3][0];
		branchright1.flip(false, true);
		branchright2 = temp[4][0];
		branchright2.flip(false, true);
		branchright3 = temp[5][0];
		branchright3.flip(false, true);
		
		
		waterTexture = new Texture(Gdx.files.internal("watersheet.png"));
		temp = TextureRegion.split(waterTexture, 136, 24);
		sheet_frames = new TextureRegion[6];
		
		
		sheet_frames[0] = temp[0][0];
		sheet_frames[0].flip(false, true);
		sheet_frames[1] = temp[1][0];
		sheet_frames[1].flip(false, true);
		sheet_frames[2] = temp[2][0];
		sheet_frames[2].flip(false, true);
		sheet_frames[3] = temp[3][0];
		sheet_frames[3].flip(false, true);
		sheet_frames[4] = temp[4][0];
		sheet_frames[4].flip(false, true);
		sheet_frames[5] = temp[5][0];
		sheet_frames[5].flip(false, true);
		
		waterAnimation = new Animation(.15F, sheet_frames);
		waterAnimation.setPlayMode(PlayMode.LOOP_PINGPONG);
		
		//bg = new TextureRegion(texture, 0, 0, 136, 43);
		//bg.flip(false, true);

		//grass = new TextureRegion(texture, 0, 43, 143, 11);
		//grass.flip(false, true);


		//skullUp = new TextureRegion(texture, 192, 0, 24, 14);
		// Create by flipping existing skullUp
		//skullDown = new TextureRegion(skullUp);
		//skullDown.flip(false, true);

		

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.25f, -.25f);

		font2 = new BitmapFont(Gdx.files.internal("data/text.fnt"), false);
		font2.setScale(.15f, -.15f);
		font3 = new BitmapFont(Gdx.files.internal("data/text.fnt"), false);
		font3.setScale(.1f, -.1f);
	
        
		
	}
	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}
	public static void setHopCount(int val) {
		prefs.putInteger("hopCount", val);
		prefs.flush();
	}

	public static int getHopCount() {
		return prefs.getInteger("hopCount");
	}
	public static void setGameCount(int val) {
		prefs.putInteger("gameCount", val);
		prefs.flush();
	}

	public static int getGameCount() {
		return prefs.getInteger("gameCount");
	}


	public static void dispose() {
		// We must dispose of the texture when we are finished.
		logoTexture.dispose();
		buttonTexture.dispose();
		branch.dispose();sheet.dispose();waterTexture.dispose();

		// Dispose sounds
		dead.dispose();
		flap.dispose();
		coin.dispose();

		font.dispose();
		font2.dispose();
		font3.dispose();
		shadow.dispose();
	}
	public static ActionResolver getGame(){
		return game;
	}
	public static void setCatSkin(int val) {
		prefs.putInteger("catSkin", val);
		prefs.flush();
	}
	public static boolean getDonateState(){
		return donated;
	}
	public static void reloadSkin(){
		 int tempint = prefs.getInteger("catSkin");
		    if(tempint ==0){
			sheet = new Texture(Gdx.files.internal("spritesheet.png"));}
		    else if(tempint==1){
		    sheet = new Texture(Gdx.files.internal("blackwhitesheet.png"));}
		    
			TextureRegion[][] temp = TextureRegion.split(sheet, 64, 48);
			sheet_frames = new TextureRegion[5];
			
			
			sheet_frames[0] = temp[0][0];
			sheet_frames[0].flip(false, true);
			sheet_frames[1] = temp[0][1];
			sheet_frames[1].flip(false, true);
			sheet_frames[2] = temp[0][2];
			sheet_frames[2].flip(false, true);
			sheet_frames[3] = temp[0][3];
			sheet_frames[3].flip(false, true);
			sheet_frames[4] = temp[1][0];
			sheet_frames[4].flip(false, true);
			
			
			catAnimation = new Animation(0.1F, sheet_frames);
			jumpFrame= temp[1][1];
			jumpFrame.flip(false, true);
			deathFrame = temp[1][2];
			deathFrame.flip(false, true);
	}

}