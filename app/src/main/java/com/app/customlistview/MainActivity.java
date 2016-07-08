package com.app.customlistview;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<String> title_array = new ArrayList<String>();
    ArrayList<String> notice_array = new ArrayList<String>();
    ArrayList<String> image_array = new ArrayList<String>();
    ListView list;
    BaseAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listview);
        new TheTask().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                TextView Text1 = (TextView) view.findViewById(R.id.txt1);
                TextView Text2 = (TextView) view.findViewById(R.id.txt2);
                ImageView imgvw = (ImageView) view.findViewById(R.id.img);

                Bundle extras = new Bundle();
                String title = Text1.getText().toString();
                String feedUrl = Text2.getText().toString();
                imgvw.buildDrawingCache();
                Bitmap bitmap = imgvw.getDrawingCache();
                extras.putParcelable("imagebitmap", bitmap);
                intent.putExtras(extras);

                intent.putExtra("title",title);
                intent.putExtra("url",feedUrl);
                startActivity(intent);
            }
        });
    }

    class TheTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String str = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        "http://api.androidhive.info/json/movies.json");
                HttpResponse response = httpclient.execute(httppost);
                str = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return str;

        }



        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            String response = result.toString();
            try {


                JSONArray new_array = new JSONArray(response);

                for (int i = 0, count = new_array.length(); i < count; i++) {
                    try {
                        JSONObject jsonObject = new_array.getJSONObject(i);
                        title_array.add(jsonObject.getString("title").toString());
                        notice_array.add(jsonObject.getString("rating").toString());
                        image_array.add(jsonObject.getString("image").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter = new BaseAdapter2(MainActivity.this, title_array, notice_array, image_array);
                list.setAdapter(adapter);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                // tv.setText("error2");
            }

        }
    }

    public class BaseAdapter2 extends BaseAdapter {

        BaseAdapter2 ctx=this;

        private Activity activity;
        // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
        private ArrayList title, notice ,imageput;
        private LayoutInflater inflater = null;

        public BaseAdapter2(Activity a, ArrayList b, ArrayList bod, ArrayList c) {
            activity = a;
            this.title = b;
            this.notice = bod;
            this.imageput = c;

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return title.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.listview_item, null);

            TextView title2 = (TextView) vi.findViewById(R.id.txt1); // title
            String song = title.get(position).toString();
            title2.setText(song);

            TextView title22 = (TextView) vi.findViewById(R.id.txt2); // notice
            String song2 = notice.get(position).toString();
            title22.setText(song2);

            ImageView image = (ImageView) vi.findViewById(R.id.img);
            String song3 = imageput.get(position).toString();
            Picasso.with(MainActivity.this).load(song3).into(image);

            return vi;
        }
    }

}
