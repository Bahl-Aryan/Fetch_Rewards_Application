package com.example.fetchrewardsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapters.ItemsAdapter;
import Model.Item;

public class MainActivity extends AppCompatActivity {
    private List<Item> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.itemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchItems();
    }

    private void updateUI() {
        if (itemsAdapter == null) {
            itemsAdapter = new ItemsAdapter(items);
            recyclerView.setAdapter(itemsAdapter);
        } else {
            itemsAdapter.notifyDataSetChanged();
        }
    }
    private void fetchItems() {
        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            String name = object.getString("name");
                            if (name != "null" && !name.isEmpty()) {
                                int id = object.getInt("id");
                                int listId = object.getInt("listId");
                                items.add(new Item(id, listId, name));
                            }
                        }
                        Collections.sort(items);
                        updateUI();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {

            }
        );

        queue.add(jsonArrayRequest);
    }
}