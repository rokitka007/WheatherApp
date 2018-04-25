package com.example.kpopiela.wheather_check;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.util.Log;
import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class Chart extends Activity {

    // URL Address
    String url = "http://m.meteo.pl/gdansk/60";
    ProgressDialog mProgressDialog;
    Context context;
    ImageLoader imageLoader;
    static String FLAG = "flag";
    ImageView flag;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chartview);
        flag = (ImageView) findViewById(R.id.chartImg);
        new ChartView().execute();

    }

    // Title AsyncTask
    private class ChartView extends AsyncTask<Void, Void, Void> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Chart.this);
            mProgressDialog.setTitle("Chart http://m.meteo.pl/gdansk/60");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the class data
                for (Element article : document.select("article[class=container podstrona]")) {
                    for (Element div1 : article.select("div[class=col4_60 miasto]")) {
                        for (Element div2 : div1.select("div[class=contenerImgWeather]")) {
                            Log.i("Look", "Div link!!! " + div2);
                            Elements img = div2.select("img[src]");


                            // Locate the src attribute
                            String imgSrc = img.attr("src");
                            Log.i("Look", "Image link!!! " + imgSrc);

                            Chart.FLAG = imgSrc;
                            Log.i("First", "Image link!!! " + Chart.FLAG);
                            Log.i("Second", "Image view!!! " + flag);

                            // Download image from URL
                            InputStream input = new java.net.URL(imgSrc).openStream();
                            // Decode Bitmap

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            context = Chart.this;
            imageLoader = new ImageLoader(context);

            // Set downloaded image into ImageView
            // ImageView chartimg = (ImageView) findViewById(R.id.chartImg);
            imageLoader.DisplayImage(FLAG, flag);
            //chartimg.setImageBitmap(bitmap);
            mProgressDialog.dismiss();
        }
    }
}

