package com.example.diplomcooksupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {

    private TextView noResultText;
    private TextView carouselItemOne;
    private TextView carouselItemTwo;
    private TextView carouselItemThree;
    private TextView carouselItemFour;
    private TextView carouselItemFive;
    private ImageView noResultImage;
    
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;

    private FirebaseRecyclerOptions<Item> options;
    private FirebaseRecyclerAdapter<Item, SearchViewHolder> adapter;
    private FirebaseRecyclerAdapter<Item, SearchViewHolder> adapter1;
    private FirebaseRecyclerAdapter<Item, SearchViewHolder> adapter2;
    private FirebaseRecyclerAdapter<Item, SearchViewHolder> adapter3;
    private FirebaseRecyclerAdapter<Item, SearchViewHolder> adapter4;
    private FirebaseRecyclerAdapter<Item, SearchViewHolder> adapter5;
    private FirebaseRecyclerAdapter<Item, SearchViewHolder> adapter6;
    private FirebaseRecyclerAdapter<Item, SearchViewHolder> localAdapter;

    public DatabaseReference databaseReference1;
    public DatabaseReference databaseReference2;
    public DatabaseReference databaseReference3;
    public DatabaseReference databaseReference4;
    public DatabaseReference databaseReference5;
    public DatabaseReference databaseReference;

    protected void onStart() {
        super.onStart();
        adapter1.startListening();
        adapter2.startListening();
        adapter3.startListening();
        adapter4.startListening();
        adapter5.startListening();
        adapter6.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
        adapter2.stopListening();
        adapter3.stopListening();
        adapter4.stopListening();
        adapter5.stopListening();
        adapter6.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        noResultText = findViewById(R.id.noResultText);
        noResultImage = findViewById(R.id.imageSearch);

        recyclerView1 = findViewById(R.id.recyclerViewSearch1);
        recyclerView2 = findViewById(R.id.recyclerViewSearch2);
        recyclerView3 = findViewById(R.id.recyclerViewSearch3);
        recyclerView4 = findViewById(R.id.recyclerViewSearch4);
        recyclerView5 = findViewById(R.id.recyclerViewSearch5);
        recyclerView6 = findViewById(R.id.recyclerViewSearch6);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("id");
        databaseReference.keepSynced(true);

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Italian");
        databaseReference1.keepSynced(true);

        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("american");
        databaseReference2.keepSynced(true);

        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("franz");
        databaseReference3.keepSynced(true);

        databaseReference4 = FirebaseDatabase.getInstance().getReference().child("russian");
        databaseReference4.keepSynced(true);

        databaseReference5 = FirebaseDatabase.getInstance().getReference().child("europe");
        databaseReference5.keepSynced(true);

        carouselItemOne = findViewById(R.id.carouselTitleOne);
        carouselItemTwo = findViewById(R.id.carouselTitleTwo);
        carouselItemThree = findViewById(R.id.carouselTitleThree);
        carouselItemFour = findViewById(R.id.carouselTitleFour);
        carouselItemFive = findViewById(R.id.carouselTitleFive);

        adapter1 = firebaseConfig(databaseReference1);
        buildRecyclerView(recyclerView1, adapter1);

        adapter2 = firebaseConfig(databaseReference2);
        buildRecyclerView(recyclerView2, adapter2);

        adapter3 = firebaseConfig(databaseReference3);
        buildRecyclerView(recyclerView3, adapter3);

        adapter4 = firebaseConfig(databaseReference4);
        buildRecyclerView(recyclerView4, adapter4);

        adapter5 = firebaseConfig(databaseReference5);
        buildRecyclerView(recyclerView5, adapter5);

        adapter6 = firebaseConfig(databaseReference);
        buildRecyclerView(recyclerView6, adapter6);

        BottomNavigationView bottomNavigation = findViewById(R.id.my_navigation_items);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent homeIntent = new Intent(SearchActivity.this, MainActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(homeIntent);

                        break;


                    case R.id.nav_favourites:

                        Intent favouritesIntent = new Intent(SearchActivity.this, FavouritesActivity.class);
                        favouritesIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(favouritesIntent);

                        break;

                }

                return false;
            }
        });
    }

    public void onClickItalian(View view) {
        noResultText.setVisibility(View.INVISIBLE);
        noResultImage.setVisibility(View.INVISIBLE);
        recyclerView1.setVisibility(View.VISIBLE);
        recyclerView2.setVisibility(View.INVISIBLE);
        recyclerView3.setVisibility(View.INVISIBLE);
        recyclerView4.setVisibility(View.INVISIBLE);
        recyclerView5.setVisibility(View.INVISIBLE);
    }

    public void onClickAmerican(View view) {
        noResultText.setVisibility(View.INVISIBLE);
        noResultImage.setVisibility(View.INVISIBLE);
        recyclerView1.setVisibility(View.INVISIBLE);
        recyclerView2.setVisibility(View.VISIBLE);
        recyclerView3.setVisibility(View.INVISIBLE);
        recyclerView4.setVisibility(View.INVISIBLE);
        recyclerView5.setVisibility(View.INVISIBLE);
    }

    public void onClickFranz(View view) {
        noResultText.setVisibility(View.INVISIBLE);
        noResultImage.setVisibility(View.INVISIBLE);
        recyclerView1.setVisibility(View.INVISIBLE);
        recyclerView2.setVisibility(View.INVISIBLE);
        recyclerView3.setVisibility(View.VISIBLE);
        recyclerView4.setVisibility(View.INVISIBLE);
        recyclerView5.setVisibility(View.INVISIBLE);
    }

    public void onClickRussian(View view) {
        noResultText.setVisibility(View.INVISIBLE);
        noResultImage.setVisibility(View.INVISIBLE);
        recyclerView1.setVisibility(View.INVISIBLE);
        recyclerView2.setVisibility(View.INVISIBLE);
        recyclerView3.setVisibility(View.INVISIBLE);
        recyclerView4.setVisibility(View.VISIBLE);
        recyclerView5.setVisibility(View.INVISIBLE);

    }

    public void onClickEurope(View view) {
        noResultText.setVisibility(View.INVISIBLE);
        noResultImage.setVisibility(View.INVISIBLE);
        recyclerView1.setVisibility(View.INVISIBLE);
        recyclerView2.setVisibility(View.INVISIBLE);
        recyclerView3.setVisibility(View.INVISIBLE);
        recyclerView4.setVisibility(View.INVISIBLE);
        recyclerView5.setVisibility(View.VISIBLE);
    }

    public void buildRecyclerView(RecyclerView recyclerView, FirebaseRecyclerAdapter localAdapter) {
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(SearchActivity.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(localAdapter);

    }

    public FirebaseRecyclerAdapter firebaseConfig(DatabaseReference databaseReference) {

        options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(databaseReference, Item.class).build();

        adapter = new FirebaseRecyclerAdapter<Item, SearchViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull final Item model) {

                holder.meal.setText(model.getName());
                holder.description.setText(model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.image);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, MealDetailActivity.class);
                        intent.putExtra("meal", model.getName());
                        intent.putExtra("link", model.getLink());
                        intent.putExtra("image", model.getImage());
                        intent.putExtra("description", model.getDescription());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new SearchViewHolder(LayoutInflater.from(SearchActivity.this).inflate(R.layout.main_item, parent, false));
            }

        };
        return adapter;
    }


}
