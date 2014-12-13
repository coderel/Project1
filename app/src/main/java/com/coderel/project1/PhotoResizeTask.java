package com.coderel.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Prasanna on 12/6/2014.
 */
public class PhotoResizeTask extends AsyncTask<Uri, Void, Void> {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Bitmap squareBitmap;
    private final Context mContext;

    PhotoResizeTask(Context context) {
        mContext = context;
    }


    @Override
    protected Void doInBackground(Uri... uris) {

        int photoShape; // to determine the shape of the picture (Portrait, landscape or square). Positive = landscape, Negative = portrait, 0 = square
        int scaleFactor;
        int cropValue;

        String photoPath = uris[0].getPath();
        Log.v("Photopath", photoPath);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        int[] dimensions = PhotoManager.checkPhotoDimensions(photoPath);
        scaleFactor = PhotoManager.determineScaleFactor(dimensions[0], dimensions[1]);
        options.inSampleSize = scaleFactor;

        //load the bitmap in most minimum size possible
        Bitmap originalBitmap = BitmapFactory.decodeFile(photoPath, options);

        //delete the original photo after loading
        if (PhotoManager.deleteFile(photoPath)) {
            Log.v("FILE DELETED", "COMPLETELY");
        }


        cropValue = Math.abs(originalBitmap.getWidth() - originalBitmap.getHeight());


        photoShape = (int) Math.signum(dimensions[2]);
        // photoShape = d.intValue();


        switch (photoShape) {
            case 1: {
                squareBitmap = resizePhoto(originalBitmap, cropValue / 2, 0);
                break;

            }
            case -1: {
                squareBitmap = resizePhoto(originalBitmap, 0, cropValue / 2);
                break;
            }
            case 0: {
                squareBitmap = originalBitmap;
                break;
            }
        }


        squareBitmap = Bitmap.createScaledBitmap(squareBitmap, 600, 600, false);

        //saving bitmap to JPEG in sd card
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        squareBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File f = PhotoManager.getOutputMediaFile(mContext, false);


        try {
            Log.v(LOG_TAG, "CREATING FILE");
            f.createNewFile();

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();

            MediaScannerConnection.scanFile(mContext,
                    new String[]{f.toString()}, null, null);


            Log.v(LOG_TAG, "FILE CREATED");
        } catch (IOException e) {
            Log.v(LOG_TAG, "CANNOT CREATE FILE");
            e.printStackTrace();
        }

        return null;
    }

    private Bitmap resizePhoto(Bitmap originalBitmap, int cropX, int cropY) {


        Bitmap returnBitmap;

        returnBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth() - cropX, originalBitmap.getHeight() - cropY);
        returnBitmap = Bitmap.createBitmap(returnBitmap, cropX, cropY, returnBitmap.getWidth() - cropX - 1, returnBitmap.getHeight() - cropY - 1);
        Log.v(LOG_TAG, "width:" + returnBitmap.getWidth() + " Height:" + returnBitmap.getHeight());

        return returnBitmap;

    }
}

