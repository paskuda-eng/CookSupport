package com.example.diplomcooksupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MealDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView meal_link;
    private TextView meal_name;
    private TextView meal_description;
    private ImageView meal_image;
    public String image;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        databaseReference = FirebaseDatabase.getInstance().getReference("Favourites");

        toolbar = (Toolbar) findViewById(R.id.meal_details_toolbar);
        setSupportActionBar(toolbar);

        meal_link  = (TextView) findViewById(R.id.meal_link);
        meal_name  = (TextView) findViewById(R.id.meal_name_detail);
        meal_image = (ImageView) findViewById(R.id.meal_image);
        meal_description = (TextView) findViewById(R.id.meal_description_detail);

        setUp();

        BottomNavigationView bottomNavigation = findViewById(R.id.my_navigation_items);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()) {

                    case R.id.nav_home:

                        Intent homeIntent = new Intent(MealDetailActivity.this, MainActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(homeIntent);

                        break;

                    case R.id.nav_search:

                        Intent addIntent = new Intent(MealDetailActivity.this, SearchActivity.class);
                        addIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(addIntent);

                        break;

                    case R.id.nav_favourites:

                        Intent favouritesIntent = new Intent(MealDetailActivity.this, FavouritesActivity.class);
                        favouritesIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(favouritesIntent);

                        break;

                }

                return false;
            }
        });


    }

    //Sets up the view with the content passed in the intent from the Home screen.
    public void setUp() {

        Intent intent = getIntent();

        String meal = intent.getStringExtra("meal");
        String link = intent.getStringExtra("link");
        String description = intent.getStringExtra("description");
        image = intent.getStringExtra("image");

        meal_link.setText(link);
        meal_name.setText(meal);
        meal_description.setText(description);
        Picasso.get().load(image).into(meal_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_details_menu, menu);
        return true;
    }

    //Controls which database activity is taking place by selecting the favourite or delete button on the toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if((item.getItemId() == R.id.add_to_favourites)) {
            addToFavouites();
        } else if(item.getItemId() == R.id.delete_from_favourites) {
            removeFromFavouites();
        }
        return super.onOptionsItemSelected(item);
    }

    //Controls the addition of meals to the database which is added to favourites.
    public void addToFavouites () {

        final String meal = meal_name.getText().toString().trim();
        final String description = meal_description.getText().toString().trim();

        databaseReference.orderByChild("name").equalTo(meal)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Toast.makeText(MealDetailActivity.this, R.string.add_to_favourites_exists, Toast.LENGTH_LONG).show();
                        } else {

                            String id = databaseReference.push().getKey();
                            Item item = new Item(image, meal, null, description);
                            databaseReference.child(id).setValue(item);

                            Toast.makeText(MealDetailActivity.this, R.string.add_to_favourites_success, Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MealDetailActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //Controls the deletion of meals from the database which removes from favourites.
    public void removeFromFavouites() {
        final String meal = meal_name.getText().toString().trim();

        databaseReference.orderByChild("name").equalTo(meal)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!(dataSnapshot.exists())) {
                            Toast.makeText(MealDetailActivity.this, R.string.remove_from_favourites_exists, Toast.LENGTH_LONG).show();
                        } else {

                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                ds.getRef().removeValue();
                            }

                            Toast.makeText(MealDetailActivity.this, R.string.remove_from_favourites_success, Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MealDetailActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

}
