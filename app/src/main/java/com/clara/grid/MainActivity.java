package com.clara.grid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


	//TODO move Game logic to a different class. Activities should only have user interface code.
	//TODO some type of data structure to store game state

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FrameLayout mainGrid = (FrameLayout) findViewById(R.id.grid_layout);

		final Grid grid = new Grid(this);

		//fill grid with water

		grid.setAllImages(R.drawable.water);

		mainGrid.addView(grid);

		grid.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					Square whichSquare = grid.whichSquareTapped(motionEvent.getX(), motionEvent.getY());

					if (whichSquare != null) {
						grid.setImage(whichSquare.x, whichSquare.y, R.drawable.small_boat);
						grid.invalidate();
					}
				}

				return false;
			}
		});

	}

}
