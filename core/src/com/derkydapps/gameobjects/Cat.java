package com.derkydapps.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.derkydapps.zchelpers.AssetLoader;

public class Cat{

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 xpos, xpos2;
	private Vector2 acceleration;

	private float rotation;
	private int width;
	private int height;
	float middle;
	float screenWidth;
	private boolean isAlive;
	boolean jumping = false, setNext = true;
	Branch pipe1,pipe2, pipe3, pipe4, pipe5;

	private Circle boundingCircle;
	private Polygon poly;
	ScrollHandler myScroller;
	int lastOn;

	public Cat(float x, float y, int width, int height, float screenwidth, float screenheight, ScrollHandler scroller) {
		myScroller = scroller;
		screenWidth = screenwidth;
		middle = y;
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		xpos = new Vector2(125,0);//125
		xpos2 = new Vector2(100,0);//100
		acceleration = new Vector2(0, 600);
		poly = new Polygon();
		poly.setOrigin(0, 0);
		
		boundingCircle = new Circle();
		isAlive = true;
		pipe1 = myScroller.getPipe1();
		pipe2 = myScroller.getPipe2();
		pipe3 = myScroller.getPipe3();
		pipe4 = myScroller.getPipe4();
		pipe5 = myScroller.getPipe5();
		
	}

	public void update(float delta) {
		if(isAlive ){
		poly.setVertices(new float[]{position.x+6,position.y+14,position.x+5,position.y+7,position.x+20,position.y+3,position.x+21,position.y+7});
		if(position.x > 136){
			position.set(-width, position.y);
		}
		if(pipe1.isScrolling() || pipe2.isScrolling() ||pipe3.isScrolling() ||pipe4.isScrolling() ||pipe5.isScrolling()) {
			position.add(xpos2.cpy().scl(delta));
		}else
		{
			position.add(xpos.cpy().scl(delta));
		}}else{
			pipe1.scrollUp();
			pipe2.scrollUp();
			pipe3.scrollUp();
			pipe4.scrollUp();
			pipe5.scrollUp();
			xpos.x = 50;
			xpos.y = 100;
			xpos.cpy().add(acceleration.cpy().scl(delta));
			position.add(xpos.cpy().scl(delta));}
		
	}
	
	
	
	

	public boolean isFalling() {
		return velocity.y > 110;
	}

	public boolean shouldntFlap() {
		return velocity.y > 70 || !isAlive;
	}

	public void onClick() {
		
		if (isAlive && pipe1.isScrolling() == false) {
			AssetLoader.flap.play();
			//velocity.y = -140;
			jumping = true;
			pipe1.startScrolling(210, true);
			pipe2.startScrolling(210, true);
			pipe3.startScrolling(210,true);
			pipe4.startScrolling(210,true);
			pipe5.startScrolling(210,true);
		}
	}
	public boolean isJumping(){
		return jumping;
	}
	public void setJumping(boolean jump){
		jumping = jump;
	}
	public void die() {
		isAlive = false;
		xpos.y = 150;
		xpos.x = 70;
	}
	
	public void decelerate() {
		acceleration.y = 0;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}
	public Polygon getBoundingPoly() {
		return poly;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void onDrag() {
		if (isAlive && pipe1.isScrolling() == false) {
			System.out.println("fling");
			AssetLoader.flap.play();
			//velocity.y = -140;
			jumping = true;
			pipe1.startScrolling(120, false);
			pipe2.startScrolling(120,false);
			pipe3.startScrolling(120,false);
			pipe4.startScrolling(120,false);
			pipe5.startScrolling(120,false);
		}
	}
	public void onRestart(){
		position.y = middle;
		position.x = 0;
		xpos.y = 0;
		xpos.x = 125;
		isAlive = true;
	}
}
