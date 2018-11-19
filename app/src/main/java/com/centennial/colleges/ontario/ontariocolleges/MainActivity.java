package com.centennial.colleges.ontario.ontariocolleges;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    CollegeAdapter collegeAdapter;
    Colleges colleges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);


        Gson gson = new GsonBuilder().create();

        colleges = gson.fromJson(loadJSONFromAsset(), Colleges.class);
        collegeAdapter = new CollegeAdapter(this, colleges);
        recyclerView.setAdapter(collegeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Log.e("count", "size " + colleges.collegeList.size());


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.e("MainActivity", "RecyclerView Pos: " + position);
                        Intent intent = new Intent(MainActivity.this, BranchActivity.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                })
        );

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("MainActivity.java", "Error while reading file : " + ex.getMessage());
            return null;
        }
        return json;
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to Exit ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            finishAndRemoveTask();
                        } else {
                            finish();
                            System.exit(0);
                        }
                    }

                }
        );
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener()

                {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }

        );
        AlertDialog alert = builder.create();
        alert.show();

    }
}
