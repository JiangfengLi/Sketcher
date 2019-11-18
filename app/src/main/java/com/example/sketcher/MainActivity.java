package com.example.sketcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @author: Jiangfeng Li
 * @description: This is the MainActivity class of Sketcher, it sets up the Fragment for user to
 * draw on the drawingview, adjust the color as well as size of outline, pass the specific functions
 * to clear the drawingview and share the drawing with designated contact.
 *
 */
public class MainActivity extends AppCompatActivity {
    private DrawingFragment drawingFragment;
    public static MainActivity itself = null;

    /**
     * This onCreate function, it sets up the environment and Fragment. It accepts a
     * 'savedInstanceState' bundle to create activity and return nothing.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itself = this;
        setupFragments();
    }

    /**
     * This setupFragments function, it sets up the Fragment which enable the user to draw some
     * shapes. It accepts and return nothing.
     */
    private void setupFragments() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        drawingFragment = new DrawingFragment(this);

        transaction.replace(R.id.fragment_container,
                drawingFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * This switchStrokeColor function, it passes a buttonview to fragment to change the color of
     * stroke. It accepts view of buttonView and return nothing.
     */
    public void switchStrokeColor (View buttonView) {
        drawingFragment.switchStrokeColor(buttonView);
    }

    /**
     * This switchStrokeWidth function, it passes a buttonview to fragment to change the size of
     * stroke. It accepts view of buttonView and return nothing.
     */
    public void switchStrokeWidth (View buttonView) {
        drawingFragment.switchStrokeWidth(buttonView);
    }

    /**
     * This clearDrawing function, it passes a view to fragment to clear the drawing view.
     * It accepts view of v and return nothing.
     */
    public void clearDrawing(View v) {
        drawingFragment.clearDrawing(v);
    }

    /**
     * Share an image of collage from the four images
     * @param v the view of the linearlayout that includes four image view of collage
     */
    public void shareTask(View v) {
        drawingFragment.shareTask(v);
    }



}
