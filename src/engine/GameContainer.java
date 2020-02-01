package engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameContainer implements Runnable
{
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	//True 16:9 ratio that goes well into 1080p: 320x180 (x6 scale), 384x216 (x5 scale), 480x270 (x4 scale) 640x360 (x3 scale), 240x135(x8 scale)
	// GBA resolution: 240 * 160
	private int width = 240, height = 160;
	//scaling seems to change framerate by dividing it by scale^2 (including for scales lower than 1)
	//a promising solution is to cache scaled versions of the images used to stop having to scale each time
	// https://stackoverflow.com/questions/148478/java-2d-drawing-optimal-performance
	private float scale = 4f; //4.5 is about what mekkah's game takes up on non-fullscreen youtube (240 x 160 resolution)
	private String title = "realEngine v 1.0";
	
	
	public GameContainer(AbstractGame game)
	{
		this.game = game;
	}
	
	public void start()
	{
		window = new Window(this);
		renderer = new Renderer(this);
		
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop()
	{
		
	}
	
	public void run()
	{
		running = true;
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime()/1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while (running) {
			//set to true to uncap framerate. Currently breaks smooth scrolling (move scrolling updates from drawCursor to 
			// and update method in map.
			render = true;
			firstTime = System.nanoTime()/1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while (unprocessedTime >= UPDATE_CAP)
			{
				unprocessedTime -= UPDATE_CAP;
				render = true;
				
				game.update(this, (float) UPDATE_CAP);
				
				input.update();
				
				if (frameTime >= 1)
				{
					frameTime = 0;
					fps = frames;
					frames = 0;
					
				}
			}
			if (render)
			{
				// Can delete once map rendering is finished
				//renderer.clear();
				game.render(this, renderer);
				renderer.process();
				renderer.drawText("FPS: " + fps, 0, 0, 0xff00ffff);
				window.update();
				frames++;
			}
			else
			{
				try
				{
					thread.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		dispose();
	}
	
	private void dispose()
	{
		
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
}
