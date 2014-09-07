package com.derkydapps.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Branch extends Scrollable {

	private Random r;

	private Rectangle branchLeft, branchRight;
	private Polygon branchleftPoly, branchrightPoly;

	private float gameHeight;
	private int startXPoint;
	private int leftcode, rightcode;

	private boolean isScored = false;

	// When branch's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Branch(float x, float y, int width, int height, float scrollSpeed,
			float gameheight) {
		super(x, y, width, height, scrollSpeed, gameheight);
		// Initialize a Random object for Random number generation
		r = new Random();
		branchLeft = new Rectangle();
		branchleftPoly = new Polygon();
		branchleftPoly.setOrigin(0, 0);
		branchrightPoly = new Polygon();
		branchrightPoly.setOrigin(0, 0);
		branchRight = new Rectangle();
		leftcode= r.nextInt(2)+1;
		rightcode= r.nextInt(2)+1;
		gameHeight = gameheight;
	}

	@Override
	public void update(float delta) {
		// Call the update method in the superclass (Scrollable)
		super.update(delta);

		// The set() method allows you to set the top left corner's x, y
		// coordinates,
		// along with the width and height of the rectangle

		branchLeft.set(position.x, position.y+3, width, height/4);
		branchleftPoly.setVertices(new float[]{position.x,position.y+3,position.x+136,position.y+3,position.x+136,position.y+5,position.x,position.y+5});
		branchrightPoly.setVertices(new float[]{position.x+176,position.y+3,position.x+312,position.y+3,position.x+312,position.y+5,position.x+176,position.y+5});
		branchRight.set(position.x+176, position.y+3, width,
				height/4);


	}

	@Override
	public void reset(float newY) {
		// Call the reset method in the superclass (Scrollable)
		//make last used available anywhere
		startXPoint = r.nextInt(90) - 130;
		while(startXPoint >= LastUsed.getLastused()+10 && startXPoint <= LastUsed.getLastused()+50){
			startXPoint = r.nextInt(90) - 130;
			//System.out.println("called");
		}
		LastUsed.setLastused(startXPoint);
		super.reset(newY);
		position.x = startXPoint;
		leftcode= r.nextInt(2)+1;
		rightcode= r.nextInt(2)+1;
		// Change the height to a random number
		isScored = false;
	}

	//public Rectangle getBarUp() {
	//	return barUp;
	//}
	//public Polygon getBranchLeftPoly() {
		//return branchleftPoly;
	//}
	//public Polygon getBranchRightPoly() {
	//	return branchrightPoly;
	//}
	//public Rectangle getBarDown() {
		//return barDown;
	//}

	public boolean collides(Cat cat) {
	
			if(cat.getX()> position.x+130 && cat.getX() < position.x+156 && position.y == (gameHeight/5)*2){
				return true;
			}
		
			return (Intersector.overlapConvexPolygons(cat.getBoundingPoly(), branchleftPoly)
					|| Intersector.overlapConvexPolygons(cat.getBoundingPoly(), branchrightPoly));

	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean b) {
		isScored = b;
	}
	public int getLeftCode(){
		return leftcode;
	}
	public int getRightCode(){
		return rightcode;
	}
	public void onRestart(int x,float y){
		acceleration.y = 0;
		velocity.y = 0;
		position.x = x;
		position.y = y;passedCat = false; falling =false; rising = false; scorable = false;
	}

	
}
