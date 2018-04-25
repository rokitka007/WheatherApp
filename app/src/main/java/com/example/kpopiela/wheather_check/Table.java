package com.example.kpopiela.wheather_check;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.widget.ListView;

public class Table extends AppCompatActivity {

    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist_tab;
    static String KEY = "key";
    static String VALUE = "value";
    //static String POPULATION = "population";
    //static String FLAG = "flag";
    // URL Address 1
    String url = "http://www.meteo.pg.gda.pl";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        // Execute DownloadJSON AsyncTask
        new JsoupListView().execute();

    }

    // Title AsyncTask
    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Table.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Wheather WheatherTable http://www.meteo.pg.gda.pl");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist_tab = new ArrayList<HashMap<String, String>>();

            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url).get();
                // Identify First WheatherTable
                for (Element table1 : doc.select("table")) {
                    // Identify table row index 0(tr)
                    for (Element row1 : table1.select("tr:eq(0)")) {

                        //Identify Second WheatherTable
                        for (Element table2 : row1.select("table")) {
                            //Identify table column
                            for (Element col2 : table2.select("td:eq(0)")){
                                //Identify Third WheatherTable
                                for (Element table3 : col2.select("table")) {
                                    //Identify table row
                                    for (Element row3 : table3.select("tr:eq(0)")) {
                                        //Identify Fourth WheatherTable
                                        for (Element table : row3.select("table")) {
                                            // Identify all the table row's(tr)
                                            for (Element row : table.select("tr:gt(0)")) {
                                                HashMap<String, String> map = new HashMap<String, String>();
                                                Log.i("WeatherCheck", "Last row" + row);
                                                // Identify all the table cell's(td)

                                                Elements tds1 = row.select("td b");
                                                Elements tds2 = row.select("td strong");
                                                Log.i("WeatherCheck", "Columns 1" + tds1);
                                                Log.i("WeatherCheck", "Columns 2" + tds2);
                                                // Identify all img src's
                                                //   Elements imgSrc = row.select("img[src]");
                                                // Get only src from img src
                                                //  String imgSrcStr = imgSrc.attr("src");

                                                // Retrive Jsoup Elements
                                                // Get the first td
                                                map.put("key", tds2.text());
                                                map.put("value", tds1.text());
                                                // Get the second td
                                                // Get the third td
                                                //map.put("population", tds.get(2).text());
                                                // Get the image src links
                                                //map.put("flag", imgSrcStr);
                                                // Set all extracted Jsoup Elements into the array
                                                arraylist_tab.add(map);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(Table.this, arraylist_tab);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
