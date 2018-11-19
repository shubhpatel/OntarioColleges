package com.centennial.colleges.ontario.ontariocolleges;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

public class BranchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BranchAdapter branchAdapter;
    Colleges colleges;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        position = getIntent().getIntExtra("position", 0);


        Gson gson = new GsonBuilder().create();

        colleges = gson.fromJson(loadJSONFromAsset(), Colleges.class);
        setTitle(colleges.collegeList.get(position).name + " College");
        branchAdapter = new BranchAdapter(this, colleges.collegeList.get(position).branches);
        recyclerView.setAdapter(branchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Log.e("count", "size " + colleges.collegeList.get(position).branches.size());

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int branchposition) {
                        Log.e("BranchActivity", "RecyclerView Pos: " + branchposition);
                        Intent intent = new Intent(BranchActivity.this, MapsActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("branchPosition", branchposition);
                        intent.putExtra("isBranch", true);
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
            Log.e("BranchActivity.java", "Error while reading file : " + ex.getMessage());
            return null;
        }
        return json;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.show_map:
                Intent intent = new Intent(BranchActivity.this, MapsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:

        }
        return false;
    }
}

