package com.example.diplomcooksupport;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class FavouritesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerOptions<Item> options;
    private FirebaseRecyclerAdapter<Item, FavouritesViewHolder> adapter;

    public DatabaseReference databaseReference;

    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites_activity);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Favourites");
        databaseReference.keepSynced(true);

        firebaseConfig();

        buildRecyclerView();

        BottomNavigationView bottomNavigation = findViewById(R.id.my_navigation_items);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent homeIntent = new Intent(FavouritesActivity.this, MainActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(homeIntent);

                        break;

                    case R.id.nav_search:

                        Intent addIntent = new Intent(FavouritesActivity.this, SearchActivity.class);
                        addIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(addIntent);

                        break;

                }

                return false;
            }
        });

    }

    //Sets up the recycler view
    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_favourites);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(FavouritesActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    /*
        Configures Firebase with the references
        Controls the display of the content on the screen by pulling from the database.
     */

    public void firebaseConfig() {

        options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(databaseReference, Item.class).build();

        adapter = new FirebaseRecyclerAdapter<Item, com.example.diplomcooksupport.FavouritesViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull com.example.diplomcooksupport.FavouritesViewHolder holder, int position, @NonNull final Item model) {

                holder.fav_meal_name.setText(model.getName());
                Picasso.get()
                        .load(model.getImage())
                        .into(holder.fav_meal_image);
            }

            @NonNull
            @Override
            public com.example.diplomcooksupport.FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new com.example.diplomcooksupport.FavouritesViewHolder(LayoutInflater.from(FavouritesActivity.this).inflate(R.layout.favourites_item, parent, false));
            }
        };
    }

}

