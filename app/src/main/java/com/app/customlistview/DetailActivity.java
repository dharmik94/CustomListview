package com.app.customlistview;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textview1 = (TextView) findViewById(R.id.text1detail);
        TextView textview2 = (TextView) findViewById(R.id.text2detail);
        ImageView imagedetail = (ImageView) findViewById(R.id.imgdetail);

        String name = getIntent().getExtras().getString("title");
        String url = getIntent().getExtras().getString("url");

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");

        textview1.setText(name);
        textview2.setText(url);
        imagedetail.setImageBitmap(bmp );
    }
}
