package com.derkydapps.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {
	
	
	// Protected is similar to private, but allows inheritance by subclasses.
	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 movedposition;
	protected int width;
	protected int height;
	protected boolean isScrolledOut;
	float gameHeight;
	float oneJump;
	float oldPosition= 0;
	float positionCount = 0;
	boolean scrolling = false;
	protected Vector2 acceleration;
	protected boolean passedCat, falling, rising = false, scorable = false;
	

	public Scrollable(float x, float y, int width, int height, float scrollSpeed, float gameheight) {
		position = new Vector2(x, y);
		movedposition = new Vector2(0,0);
		velocity = new Vector2(0, scrollSpeed);
		this.width = width;
		this.height = height;
		isScrolledOut = false;
		gameHeight = gameheight;
		oneJump = gameheight / 5;
		acceleration = new Vector2(0, 0);//460
		passedCat = false;
		falling = false;
	}

	public void update(float delta) {
		if(rising == false){
		if(velocity.y != 0) {scrolling = true;}	
		if(position.y  > ((gameHeight/5)*2)){
			passedCat = true;
		}
		velocity.add(acceleration.cpy().scl(delta));
		if (velocity.y < 0){
			falling = true;
		}}
		position.add(velocity.cpy().scl(delta));
		
		
	


		
		
		
		// If the Scrollable object is no longer visible:
		if (position.y > gameHeight ) {
			isScrolledOut = true;
		}
	}

	// Reset: Should Override in subclass for more specific behavior.
	public void reset(float newY) {
		position.y = newY;
		isScrolledOut = false;
	}

	public void stop() {
		passedCat = false;
		scrolling = false;
		falling = false;
		velocity.y = 0;
		acceleration.y = 0;
		
	}
	public void scrollUp(){
		velocity.y = -100;
		rising  = true;
	}
	
	// Getters for instance variables
	public boolean isScrolledOut() {
		return isScrolledOut;
	}

	public float getTailY() {
		return position.y + height;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	public void startScrolling(int numba, boolean b){
		scorable = b;
		velocity.y = numba;
		acceleration.y = -400;
		scrolling = true;
	}
	public boolean isScrolling(){
		return scrolling;
	}
	public float getVelocityY(){
		return velocity.y;
	}

	
	

}
