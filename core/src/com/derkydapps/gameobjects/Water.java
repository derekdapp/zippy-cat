package com.derkydapps.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.derkydapps.gameworld.GameWorld;

public class Water {
	Vector2 position, velocity;
	ScrollHandler myScroller;
	float screenHeight;
	float width, height;
	Cat cat;
	GameWorld myGameWorld;
	
	public Water(float x, float y, int width, Cat cat, ScrollHandler scroller, GameWorld gameWorld){
		position = new Vector2(x,y);
		velocity = new Vector2(0,-150);
		myScroller = scroller;
		screenHeight = y;
		this.width = width;
		this.height = y+50;
		this.cat = cat;
		myGameWorld = gameWorld;
	}
	public void update(float delta){
		if(position.y > screenHeight){
			position.y = screenHeight;
		
		}
		if(position.y >= 0){
			
		if(myScroller.getPipe1().rising == true && cat.isAlive() == false){
			velocity.y = -150;
			position.add(velocity.cpy().scl(delta));
		}else if (myScroller.getPipe1().isScrolling()){
			velocity.y= myScroller.getPipe1().getVelocityY();
			position.add(velocity.cpy().scl(delta));
		}else{
			velocity.y = -30;
			position.add(velocity.cpy().scl(delta));
		}
		}else myGameWorld.setCurrentStateGameover();
	}
	public float getPositionx(){
		return position.x;
	}
	public float getPositiony(){
		return position.y;
	}
	public float getHeight(){
		return height;
	}
	public float getWidth(){
		return width;
	}
	public boolean collides() {
		if(position.y-20 <= (screenHeight/5)*2){
			return true;
		}
		return false;
	}
	public void onRestart(){
		position.x=0;
		position.y=screenHeight;
	}
	
}
