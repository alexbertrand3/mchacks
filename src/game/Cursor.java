package game;

import engine.GameContainer;
import engine.Renderer;
import engine.audio.SoundClip;
import engine.gfx.ImageTile;

public class Cursor {
	private ImageTile cursorTile = new ImageTile("/cursor.png", 20, 20);
	
	int cursorX, cursorY;
	int framesU;
	int framesD;
	int framesL;
	int framesR;
	int secondMove = 12;
	int moveEvery = 4;
	int fastMove = 2;
	int fastFrames;
	int fastThreshold = 20;
	SoundClip cursorSound = new SoundClip("/Audio/CheckoutE.wav"); // set volume when this is used
	
	public Cursor (int x, int y) {
		this.cursorX = x;
		this.cursorY = y;
		cursorSound.setVolume(-20f);
	}
	
	public void update(GameContainer gc, Controls controls) {
		// Y Axis cursor movement (also handles diagonals if applicable)
		if (gc.getInput().isKeyDown(controls.mU)) {
			moveCursorUp();
			framesD = 0;
		}
		if (gc.getInput().isKeyDown(controls.mD)) {
			moveCursorDown();
			framesU = 0;
		}
		if ((gc.getInput().isKey(controls.mU) ^ gc.getInput().isKey(controls.mD)))
		{
			if (gc.getInput().isKeyUp(controls.mU)) {
				framesU = 0;
			}
			if (gc.getInput().isKeyUp(controls.mD)) {
				framesD = 0;
			}
			if (gc.getInput().isKey(controls.mU))
			{
				framesU++;
				if (framesU == secondMove)
					moveCursorUp();
				// fast cursor movement
				if (framesU > fastThreshold) {
					if (framesR > fastThreshold && framesU%moveEvery == 0) {
						moveCursorUp();
						moveCursorRight();
					} else if (framesL > fastThreshold && framesU%moveEvery == 0) {
						moveCursorUp();
						moveCursorLeft();
					} else if (framesU%moveEvery == 0)
						moveCursorUp();
				}		
			}
			if (gc.getInput().isKey(controls.mD))
			{
				framesD++;
				if (framesD == secondMove)
					moveCursorDown();
				// fast cursor movement
				if (framesD > fastThreshold) {
					if (framesR > fastThreshold && framesD%moveEvery == 0) {
						moveCursorDown();
						moveCursorRight();
					} else if (framesL > fastThreshold && framesD%moveEvery == 0) {
						moveCursorDown();
						moveCursorLeft();
					} else if (framesD%moveEvery == 0)
						moveCursorDown();
				}
			}
		} else {
			framesU = 0;
			framesD = 0;
		}

		// X Axis cursor movement
		if (gc.getInput().isKeyDown(controls.mL)) {
			moveCursorLeft();
			framesR = 0;
		}
		if (gc.getInput().isKeyDown(controls.mR)) {
			moveCursorRight();
			framesL = 0;
		}

		if (gc.getInput().isKey(controls.mL) ^ gc.getInput().isKey(controls.mR))
		{
			if (gc.getInput().isKey(controls.mL))
			{
				framesL++;
				if (framesL == secondMove || (framesL%moveEvery == 0 && framesL > fastThreshold && framesU < fastThreshold && framesD < fastThreshold)) {
					moveCursorLeft();
				}
			}
			if (gc.getInput().isKey(controls.mR))
			{
				framesR++;
				if (framesR == secondMove || (framesR%moveEvery == 0 && framesR > fastThreshold && framesU < fastThreshold && framesD < fastThreshold)) {
					moveCursorRight();
				}
			}
		} else {
			framesL = 0;
			framesR = 0;
		}
		fastFrames = 0;
	}
	
	public void updateFast(GameContainer gc, Controls controls) {
		if (fastFrames%fastMove == 0) {
			if (gc.getInput().isKey(controls.mU)) {
				moveCursorUp();
			}
			if (gc.getInput().isKey(controls.mD)) {
				moveCursorDown();
			}
			if (gc.getInput().isKey(controls.mL)) {
				moveCursorLeft();
			}
			if (gc.getInput().isKey(controls.mR)) {
				moveCursorRight();
			}
		}
		fastFrames++;
	}
	
	public void moveCursorUp() {
		if (cursorY > 0) {
			cursorY--;
			cursorSound.play();
		}
	}
	public void moveCursorDown() {
		if (cursorY < Map.height - 1) {
			cursorY++;
			cursorSound.play();
		}
	}
	public void moveCursorLeft() {
		if (cursorX > 0) {
			cursorX--;
			cursorSound.play();
		}	
	}
	public void moveCursorRight() {
		if (cursorX < Map.width - 1) {
			cursorX++;
			cursorSound.play();
		}	
	}
}
