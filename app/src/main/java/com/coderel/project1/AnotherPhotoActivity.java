package com.coderel.project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.zip.Inflater;


public class AnotherPhotoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_another_photo,null,false);

        setContentView(R.layout.activity_another_photo);

        PhotoResizeTask photoResizeTask = new PhotoResizeTask(getApplicationContext(),rootView.findViewById(R.id.imageView_picture));
        photoResizeTask.execute(MainActivity.fileUri);


//        Log.v("PHOTOPATH", filePath);
//        Bitmap imageBitmap = BitmapFactory.decodeFile(PhotoResizeTask.f.getAbsolutePath());
//        photoPreview.setImageBitmap(imageBitmap);

    }

    public void onTakeAnotherPicture(View view) {
        finish();
    }

    public void onExit(View view) {
        Intent intent = new Intent(this, ShareActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_another_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
