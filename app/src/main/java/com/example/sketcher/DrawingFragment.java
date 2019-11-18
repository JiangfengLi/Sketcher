package com.example.sketcher;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
 * @author: Jiangfeng Li
 * @description: This is the DrawingFragment class of Sketcher, which is the subclass of fragment.
 * it sets up the Fragment for user to draw on the drawingview, adjust the color as well as size of
 * outlines, clear the drawing panel and creates the contact fragment according to specific buttons
 *
 */
public class DrawingFragment extends Fragment {
    DrawingView dv = null;
    private static Activity activity = null;
    private View FragV;
    private String authorities = "";
    private File shareImage = null;

    /**
     * This is the constructor of DrawingFragment function, it sets up the environment and drawing
     * view. It accepts an Activity of mainActivity.
     */
    public DrawingFragment(Activity mainActivity) {
        // Required empty public constructor
        this.activity = mainActivity;
        dv = new DrawingView(mainActivity, null);
    }

    /**
     * This onCreateView function, it sets up the configurations of fragment and set drawing view to
     * specific LinearLayout. It accepts an LayoutInflater of inflater, ViewGroup of container,
     * Bundle of savedInstanceState and return a View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragV = inflater.inflate(R.layout.fragment_drawing, container, false);

        LinearLayout ll = FragV.findViewById(R.id.drawing);
        ll.addView(dv);
        return FragV;
    }

    /**
     * This onPause function, it removes the drawing view from LinearLayout once the fragment is
     * leaving the foreground. It accepts and return nothing.
     */
    @Override
    public void onPause() {
        super.onPause();
        LinearLayout ll = FragV.findViewById(R.id.drawing);
        ll.removeView(dv);
    }

    /**
     * This switchStrokeColor function, it changes the color of Stroking according to the view that
     * user clicks. It accepts View of buttonView and return nothing.
     */
    public void switchStrokeColor (View buttonView) {
        if(buttonView.getId() == R.id.red){
            dv.changeStrokeColor(Color.RED);
        } else if (buttonView.getId() == R.id.green){
            dv.changeStrokeColor(Color.GREEN);
        }else if (buttonView.getId() == R.id.blue){
            dv.changeStrokeColor(Color.BLUE);
        } else if (buttonView.getId() == R.id.purple){
            dv.changeStrokeColor(Color.rgb(78,16,187));
        }
    }

    /**
     * This switchStrokeWidth function, it changes the width of Stroking according to the view that
     * user clicks. It accepts View of buttonView and return nothing.
     */
    public void switchStrokeWidth (View buttonView) {
        if(buttonView.getId() == R.id.stroke_width_small){
            dv.changeStrokeSize(15.0f);
        } else if (buttonView.getId() == R.id.stroke_width_medium){
            dv.changeStrokeSize(30.0f);
        }else if (buttonView.getId() == R.id.stroke_width_large){
            dv.changeStrokeSize(45.0f);
        }
    }

    /**
     * This clearDrawing function, it passes a view to fragment to clear the drawing view.
     * It accepts view of v and return nothing.
     */
    public void clearDrawing(View v) {
        dv.startNew();
    }

    /**
     * This getBitmap function, which returns a Bitmap of the image that users draw.
     * It accepts nothing and return a Bitmap of image.
     */
    public Bitmap getBitMap() {
        return dv.getBitmap();
    }

    /**
     * This showContacts function, which creates a ContactsFragment and transact the Ccurrent View
     * to ContactsFragment. It accepts nothing and return a Bitmap of image.
     */
    public void showContacts() {
        ContactsFragment cf = new ContactsFragment();
        cf.setContainerActivity(activity);
        cf.setBitMapDir(shareImage.getAbsolutePath());
        cf.setAuthor(authorities);
        FragmentTransaction transaction = getActivity().
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, cf);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Share an image of collage from the four images
     * @param v the view of the linearlayout that includes four image view of collage
     */
    public void shareTask(View v) {

        // resize the picture
        Bitmap newBitmap = getBitMap();

        FileOutputStream fOut = null;
        try {
            shareImage = createImageFile("PNG_", ".png");

            authorities = activity.getApplicationContext().getPackageName() + ".fileprovider";
            fOut = new FileOutputStream(shareImage);
            newBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();

            this.showContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This is createImageFile function, it creates the image file in current directory, Opens up
     * the file image file that was created, Crop it to be square and return it. It accepts a
     * 'preFix' string as the prefix of filename and a 'suffix' string as the suffix of filename.
     * It returns the file of image created.
     */
    private File createImageFile(String preFix, String suffix) throws Exception {
        // Implement
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = preFix + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                suffix,       /* suffix */
                storageDir      /* directory */
        );

        return image;
    }
}
