package com.coderel.project1;

import android.graphics.BitmapFactory;

/**
 * Created by Prasanna on 12/13/2014.
 */
public class PhotoManager {

    public static int[] checkPhotoDimensions(String path) {

        int[] dimension = new int[3];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        dimension[0] = options.outWidth;
        dimension[1] = options.outHeight;
        dimension[2] = dimension[0] - dimension[1];
        return dimension;

    }

    public static int determineScaleFactor(int width, int height) {
        int scaleFactor;

        return (width / 600 < height / 600 ? width / 600 : height / 600);
    }
}

