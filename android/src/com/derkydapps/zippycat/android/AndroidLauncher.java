package com.derkydapps.zippycat.android;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.derkydapps.zippycat.ActionResolver;
import com.derkydapps.zippycat.MainGame;
import com.derkydapps.zippycat.android.GameHelper.GameHelperListener;
import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;
import com.google.android.gms.games.Games;


public class AndroidLauncher extends AndroidApplication implements GameHelperListener, ActionResolver {
	private GameHelper gameHelper;
	IabHelper mHelper;
	boolean donated;
	 IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
	 IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
	 SharedPreferences pref;
	 Editor edit;
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
		edit = pref.edit();
		donated = pref.getBoolean("donated", false);
		System.out.println("donated is" + donated);
		initialize(new MainGame(this, donated));
		
		// ...
	    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApZDNfpeSiAV4Wig6GnD3rg5ewL0qcqKgNagAj2AMyMLGGETF7hBOMoMoSOLickWGteZWnjrUkQwdIUprooTE9yq7zOxBfPjMmHwquYYKftsiw6Flhciuyj0dF+etEGxcmnxkG+D7NPI1tTF+f88g/A115tEe1A+t9kWjMn/S5ya0SOjlqJNpzUBUov5m6f01aZFO/lldXujUbkuIvVRxYHfEof6Abuj/494uK91lBkqopza7F35SQqiKyl+kNc/BYeafWM9GfmkAR30Vbqe4D03mnJ1ztf6UMGxUw5RKbOgF0lRkLWnIUa27ZYN1Bwz3oQPoqI+ctKrW3kcds0HS7wIDAQAB";

	    // compute your public key and store it in base64EncodedPublicKey
	    mHelper = new IabHelper(this, base64EncodedPublicKey);

	    mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
	           public void onIabSetupFinished(IabResult result) {
	              if (!result.isSuccess()) {
	                 // Oh noes, there was a problem.
	                 Log.d("IAB", "Problem setting up In-app Billing: " + result);
	              }            
	              // Hooray, IAB is fully set up!
	              Log.d("IAB", "Billing Success: " + result);
	              
	              processPurchases();
	            // donate1();
	           }
	           
	        });
	    
	    
	    
		
	    // Callback for when a purchase is finished
	   mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
	        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
	            if ( purchase == null) return;
	            Log.d("IAB", "Purchase finished: " + result + ", purchase: " + purchase);

	            // if we were disposed of in the meantime, quit.
	            if (mHelper == null) return;

	            if (result.isFailure()) {
	                //complain("Error purchasing: " + result);
	                //setWaitScreen(false);
	                return;
	            }
//	            if (!verifyDeveloperPayload(purchase)) {
//	                //complain("Error purchasing. Authenticity verification failed.");
//	                //setWaitScreen(false);
//	                return;
//	            }

	            Log.d("IAB", "Purchase successful.");

	            if (purchase.getSku().equals("donate1")|| purchase.getSku().equals("donatetwo")|| purchase.getSku().equals("donatethree")) {
	                // bought the premium upgrade!
	                Log.d("IAB", "Purchase is premium upgrade. Congratulating user.");

	                // Do what you want here maybe call your game to do some update
	                //
	            	// Maybe set a flag to indicate that ads shouldn't show anymore
	                donated = true;
	                edit.putBoolean("donated", true);
	                edit.commit();

	            }
	        }

	    };
		
	 // Listener that's called when we finish querying the items and subscriptions we own
	    mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
	        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
	            Log.d("IAB", "Query inventory finished.");

	            // Have we been disposed of in the meantime? If so, quit.
	            if (mHelper == null) return;

	            // Is it a failure?
	            if (result.isFailure()) {
	                // handle failure here
	                return;
	            }

	            // Do we have the premium upgrade?
	           // Purchase donate1Purchase = inventory.getPurchase("donatetwo");
	           // donated = (donate1Purchase != null);
	           // donated = inventory.hasPurchase("donate1");
	           // System.out.println("Donated is"+donated);
	            if(inventory.hasPurchase("donate1") || inventory.hasPurchase("donatetwo")||inventory.hasPurchase("donatethree")){
	            	edit.putBoolean("donated", true);
	            	edit.commit();
	            }
	        }

			
	    };
	
    if (gameHelper == null) {
      gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
      gameHelper.enableDebugLog(true);
    }
    gameHelper.setup(this);
	}

	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
		if (mHelper != null) {
	        // Pass on the activity result to the helper for handling
	        if (mHelper.handleActivityResult(request, response, data)) {
	            Log.d("IAB", "onActivityResult handled by IABUtil.");
	           
	        }
	    }
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(String id, int score) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), id, score);
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
	  Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void getLeaderboardGPGS() {
	  if (gameHelper.isSignedIn()) {
		  startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()),0);	
	  }
	  else if (!gameHelper.isConnecting()) {
	    loginGPGS();
	  }
	}

	@Override
	public void getAchievementsGPGS() {
	  if (gameHelper.isSignedIn()) {
	    startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
	  }
	  else if (!gameHelper.isConnecting()) {
	    loginGPGS();
	  }
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}

	@Override
	public void donate1() {
		mHelper.launchPurchaseFlow(this, "donate1", 1001,
			     mPurchaseFinishedListener, "HANDLE_PAYLOADS");
	}
	public void processPurchases() {
	    mHelper.queryInventoryAsync(mGotInventoryListener);
	}

	

	@Override
	public void donate2() {
		mHelper.launchPurchaseFlow(this, "donatetwo", 1002,
			     mPurchaseFinishedListener, "HANDLE_PAYLOADS");
		
	}

	@Override
	public void donate3() {
		mHelper.launchPurchaseFlow(this, "donatethree", 1003,
			     mPurchaseFinishedListener, "HANDLE_PAYLOADS");
		
	}
	
	
}