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
public class First extends Fragment {

    private ListView listView;
    public ArrayList newsList = new ArrayList();
    public ArrayList hrefList = new ArrayList();
    private ArrayAdapter<String> adapter ;
    private static String URL = "https://www.aybu.edu.tr/muhendislik/bilgisayar/";
    private ProgressDialog progressDialog;
    String direct = "https://www.aybu.edu.tr/muhendislik/bilgisayar/";
    int executeCounter = 0;

    public First() {
        //Cons
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        listView = (ListView)rootView.findViewById(R.id.newsList);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newsList);
        new fetchData().execute();
        addClickListener();

        return rootView;
    }

    private void addClickListener () {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Opening Website", Toast.LENGTH_SHORT).show();
                for (int index = 0 ; index < newsList.size(); index++){
                    if (newsList.get(position) == newsList.get(index)){
                        direct = URL + hrefList.get(index);
                    }
                }
                Object currentItem = newsList.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(direct));
                startActivity(i);
            }
        });
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
                Document doc = Jsoup.connect(URL).timeout(30*1000).get();
                Elements news = doc.select ("a[title]");
                String newsId = "ContentPlaceHolder1_ctl01_rpData_hplink_";

                int annCounter = 0;
                String comparison ;

                for (int index=0 ; index < news.size(); index++){
                    if (executeCounter != 0) break;
                    comparison = newsId + Integer.toString(annCounter);
                    if (news.get(index).id().equals(comparison)){
                        newsList.add(news.get(index).text());
                        hrefList.add(news.get(index).attr("href"));
                        annCounter++;
                    }

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
