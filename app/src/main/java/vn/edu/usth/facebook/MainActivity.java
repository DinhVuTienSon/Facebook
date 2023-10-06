package vn.edu.usth.facebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;

import vn.edu.usth.facebook.adapter.ViewPagerAdapter;
import vn.edu.usth.facebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    MaterialButton search_main_fragment;
    MaterialButton upload_post_main_fragment;
    private static final int MENU_HOME = R.id.home;
    private static final int MENU_FRIENDS = R.id.friends;
    private static final int MENU_NOTIFICATION = R.id.notification;
    private static final int MENU_MENU = R.id.fb_menu;

    ActivityMainBinding binding;
    private ProgressBar progressBar;
//    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(pagerAdapter);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == MENU_HOME) {
                binding.viewPager.setCurrentItem(0, true);
            } else if (itemId == MENU_FRIENDS) {
                binding.viewPager.setCurrentItem(1, true);
            } else if (itemId == MENU_NOTIFICATION) {
                binding.viewPager.setCurrentItem(2, true);
            } else if (itemId == MENU_MENU) {
                binding.viewPager.setCurrentItem(3, true);
            }
            return true;
        });
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.bottomNavigationView.setSelectedItemId(getNavigationMenuItemId(position));
            }
        });

        search_main_fragment = findViewById(R.id.search_main_fragment);
        search_main_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);


            }
        });
        upload_post_main_fragment = findViewById(R.id.upload_post_main_fragment);
        upload_post_main_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadPostActivity.class);
                startActivity(intent);
            }
        });

    }

    private int getNavigationMenuItemId(int position) {
        switch (position) {
            case 0:
                return R.id.home;
            case 1:
                return R.id.friends;
            case 2:
                return R.id.notification;
            case 3:
                return R.id.fb_menu;
            default:
                return 0;
        }
    }
}


//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, " " + error, Toast.LENGTH_SHORT).show();
//                }
//            });
//        mRequestQueue.add(jsonObjectRequest);
//    }

//    Post postModal = new Post("https://picsum.photos/600/300?random&","ST","now","testing","https://picsum.photos/600/300?random&", "2", "3");
//            posts.add(postModal);
//
//            PostAdapter adapter = new PostAdapter(posts, MainActivity.this);
//            RecyclerView recyclerView = findViewById(R.id.recyclerView);
//
//            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
//
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(adapter);