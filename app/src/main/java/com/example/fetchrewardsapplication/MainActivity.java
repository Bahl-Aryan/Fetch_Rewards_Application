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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        itemsAdapter = new ItemsAdapter(new ArrayList<>());

        setUpSpinner();
        fetchItems();
    }

    private void setUpSpinner() {
        Set<Integer> listIdSet = new HashSet<>();
        for (Item item : items) {
            listIdSet.add(item.getListId());
        }
        List<Integer> listIds = new ArrayList<>(listIdSet);
        Collections.sort(listIds);

        Spinner listIdSpinner = findViewById(R.id.listIdSpinner);
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listIds);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listIdSpinner.setAdapter(spinnerAdapter);

        listIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int selectedListId = listIds.get(position);
                updateRecyclerView(selectedListId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateRecyclerView(int listId) {
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getListId() == listId && item.getName() != "null" && !item.getName().isEmpty()) {
                filteredItems.add(item);
            }
        }

        Collections.sort(filteredItems);

        if (itemsAdapter != null) {
            itemsAdapter.updateItems(filteredItems);
        }
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