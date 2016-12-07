package com.clara.grid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by clara on 12/7/16.
 */

public class Grid extends View {

	final static String TAG = "GRID VIEW";

	int height = 800;    //todo figure out length of shorter side of screen and use that for size of grid
	int width = 800;

	int numberOfXSquares = 11;
	int numberOfYSquares = 11;

	float squareWidth = width / numberOfXSquares;      //width, height of a square in pixels
	float squareHeight = height / numberOfYSquares;

	int[][] imageResources;

	Paint linePaint;

	public Grid(Context context) {
		super(context);
		imageResources = new int[numberOfXSquares][numberOfYSquares];   //10 squares plus marker for grid letters/numbers todo make more flexible
		linePaint = new Paint();
		linePaint.setColor(Color.BLACK);
	}


	//sets a particular square in the array
	public void setImage(int x, int y, int resource) {

		if (x >= numberOfXSquares || y >= numberOfYSquares) {
			Log.w(TAG, "x, y is outside grid " + x + ", " + y);
				return;
		}

		imageResources[x][y] = resource;

	}

	public void setAllImages(int resource) {
		for (int x = 0 ; x < numberOfXSquares; x++) {
			for (int y = 0; y < numberOfYSquares; y ++) {
				imageResources[x][y] = resource;
			}
		}

	}

	@Override
	public void onDraw(Canvas canvas) {

		//draw a grid
		int squareWidth = width/numberOfXSquares;
		int squareHeight = height/numberOfYSquares;


		//draw views in grid

		for (int x = 0 ; x < numberOfXSquares; x++) {
			for (int y = 0; y < numberOfYSquares; y++) {

				Bitmap bitmap = createBitmap(imageResources[x][y]);
				canvas.drawBitmap(bitmap, x*squareWidth, y*squareHeight, null);

			}
		}

		//vertical lines
		for (int x = 0 ; x <= width; x+=squareWidth) {

			canvas.drawLine(x, 0, x, height, linePaint);
		}

		//horizontal lines
		for (int y = 0 ; y < height ; y+=squareHeight) {
			canvas.drawLine(0, y, width, y, linePaint);

		}


	}

	private Bitmap createBitmap(int resourceId) {

		//Turn resource id into bitmap of the correct height and width for a grid square, scale to correct size

		Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resourceId);
		Bitmap scaled = Bitmap.createScaledBitmap(bitmap, (int)squareWidth, (int)squareHeight, true);
		return scaled;
	}


	public Square whichSquareTapped(float x, float y) {

		//which square in grid?

		//if x or y outside grid, return null

		if (x > width || y > height) {
			return null;
		}

		//example: tap at x=22, square width = 10. x/width = 2.2, want tap to be at square index 2
		//example: tap at x=15, square width = 10. x/width = 1.5, want tap to be at square index 1
		//example: tap at x=7, square width = 10. x/width = 0.7, want tap to be at square index 0
		//example: tap at x=0, square width = 10. x/width = 0, want tap to be at square index 0


		int xLoc = (int) Math.floor(x / squareWidth);
		int yLoc = (int) Math.floor(y / squareHeight);

		Log.d(TAG, "x=" + x + " y = " + y + " xloc " + xLoc + " yloc" + yLoc);
		return new Square(xLoc, yLoc);

	}
}
