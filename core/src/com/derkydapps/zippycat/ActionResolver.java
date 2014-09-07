package com.derkydapps.zippycat;

public interface ActionResolver {
	  public boolean getSignedInGPGS();
	  public void loginGPGS();
	  public void submitScoreGPGS(String id, int score);
	  public void unlockAchievementGPGS(String achievementId);
	  public void getLeaderboardGPGS();
	  public void getAchievementsGPGS();
	  public void donate1();
	  public void donate2();
	  public void donate3();
	  public void processPurchases(); 
	}