package com.derkydapps.zippycat;

import com.badlogic.gdx.Gdx;

public class DesktopInterface implements ActionResolver {
	  public boolean getSignedInGPGS() {
		return false;
	}
	  public void loginGPGS() {
		  Gdx.app.log("", "message");
	}
	  public void submitScoreGPGS(String id,int score) {
		  
	}
	  public void unlockAchievementGPGS(String achievementId) {
	}
	  public void getLeaderboardGPGS() {
		  Gdx.app.log("", "would have opened those leaderboards");
	}
	  public void getAchievementsGPGS() {
	}
	@Override
	public void donate1() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void processPurchases() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void donate2() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void donate3() {
		// TODO Auto-generated method stub
		
	}
}
