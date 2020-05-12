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

import com.google.android.material.tabs.TabItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class Second extends Fragment{


    private ListView listView;
    public ArrayList announcementsList = new ArrayList();
    public ArrayList hrefList = new ArrayList();
    private ArrayAdapter<String> adapter ;
    private static String URL = "https://www.aybu.edu.tr/muhendislik/bilgisayar/";
    private ProgressDialog progressDialog;
    String direct = "https://www.aybu.edu.tr/muhendislik/bilgisayar/";
    int executeCounter = 0;

    public Second() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
       listView = (ListView)rootView.findViewById(R.id.liste);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, announcementsList);

        new fetchData().execute();
        addClickListener();

        return rootView;
    }

    private void addClickListener () {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Opening Website" , Toast.LENGTH_SHORT).show();
                for (int index = 0 ; index < announcementsList.size(); index++){
                    if (announcementsList.get(position) == announcementsList.get(index)){
                        direct = URL + hrefList.get(index);
                    }
                }
                Object currentItem = announcementsList.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(direct));
                startActivity(i);
            }
        });
    }

    private class fetchData extends AsyncTask<Void, Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setTitle("Announcements");
            progressDialog.setMessage("Please Wait..");
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect(URL).timeout(30*1000).get();
                Elements announcements = doc.select ("a[title]");
                String announcementsId = "ContentPlaceHolder1_ctl02_rpData_hplink_";

                int annCounter = 0;
                String comparison ;

                for (int index=0 ; index < announcements.size(); index++){
                    if (executeCounter != 0) break;
                    comparison = announcementsId + Integer.toString(annCounter);
                    if (announcements.get(index).id().equals(comparison)){
                        announcementsList.add(announcements.get(index).text());
                        hrefList.add(announcements.get(index).attr("href"));
                        annCounter++;
                    }

                }

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
