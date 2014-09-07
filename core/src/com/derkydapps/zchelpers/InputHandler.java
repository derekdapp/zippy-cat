package com.derkydapps.zchelpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.derkydapps.gameobjects.Cat;
import com.derkydapps.gameworld.GameWorld;


public class InputHandler implements InputProcessor, GestureListener {
    private Cat myCat;
    private GameWorld myWorld;



    // Ask for a reference to the Bird when InputHandler is created.
    public InputHandler(GameWorld myWorld) {
        // myBird now represents the gameWorld's bird.
        this.myWorld = myWorld;
        myCat = myWorld.getCat();
        

        //float midPointY = myWorld.getMidPointY();
        
    
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	 // screenX = scaleX(screenX);
        //  screenY = scaleY(screenY);
         // System.out.println(screenX + " " + screenY);
         // if (myWorld.isMenu()) {
         //     playButton.isTouchDown(screenX, screenY);
         // } else if (myWorld.isReady()) {
          //    myWorld.start();
         // }

         // myBird.onClick();

        //  if (myWorld.isGameOver() || myWorld.isHighScore()) {
        //      myWorld.restart();
        //  }

          return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	// screenX = scaleX(screenX);
        // screenY = scaleY(screenY);

        // if (myWorld.isMenu()) {
           //  if (playButton.isTouchUp(screenX, screenY)) {
             //    myWorld.ready();
             //    return true;
           //  }
        // }

         return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
    	myCat.onDrag();
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		 if (myWorld.isReady()) {
	        myWorld.setCurrentStateRunning();
	      }
	        myCat.onClick();

	       // if (myWorld.isGameOver()) {
	            // Reset all variables, go to GameState.READ
	            //myWorld.restart();
	        //}

	        return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	 @Override
	   public boolean fling(float velocityX, float velocityY, int button) {
		 	if (myWorld.isReady()){
		 		myWorld.setCurrentStateRunning();
		 	}
		 
	           if(Math.abs(velocityX)>Math.abs(velocityY)){
	                   if(velocityX>0){
	                           myCat.onDrag();
	                   }else if (velocityX<0){
	                          myCat.onDrag();
	                   } else {
	                     // Do nothing.
	                   }
	           }else{
	        	   myCat.onClick();
	              // Ignore the input, because we don't care about up/down swipes.
	           }
	       if(myWorld.isGameOver()){
	    	   myWorld.restart();
	       }
	     return true; 
	    }

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
	


}
