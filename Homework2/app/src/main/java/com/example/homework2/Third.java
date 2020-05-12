package com.example.homework2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Third extends Fragment {

    private ListView listView;
    public ArrayList foodList = new ArrayList();
    public ArrayList hrefList = new ArrayList();
    private ArrayAdapter<String> adapter ;
    private static String URL = "https://web.archive.org/web/20190406185041/https://aybu.edu.tr/sks/";
    private ProgressDialog progressDialog;
    String direct = "https://www.aybu.edu.tr/muhendislik/bilgisayar/";
    int executeCounter = 0;

    public Third() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_third, container, false);
        listView = (ListView)rootView.findViewById(R.id.foodList);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, foodList);

        new fetchData().execute();

        return rootView;
    }



    private class fetchData extends AsyncTask <Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait..");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(URL).timeout(600*1000).get();
                Elements food = doc.select ("p[style]");
                for (int index=0 ; index < food.size(); index++){
                    if (executeCounter != 0) break;
                    foodList.add(food.get(index).text());
                }
                executeCounter++;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listView.setAdapter(adapter);
            progressDialog.dismiss();

        }


    }

}
