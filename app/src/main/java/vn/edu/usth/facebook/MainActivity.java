package vn.edu.usth.facebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ProgressBar;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.facebook.databinding.ActivityMainBinding;
import vn.edu.usth.facebook.fragment.FbMenuFragment;
import vn.edu.usth.facebook.fragment.FriendsFragment;
import vn.edu.usth.facebook.fragment.HomeFragment;
import vn.edu.usth.facebook.fragment.NotificationFragment;


public class MainActivity extends AppCompatActivity {
    private CircleImageView profile_menu_ava;

    ActivityMainBinding binding;
    private ProgressBar progressBar;
//    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
//                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                replaceFragment(new HomeFragment());
//                MainActivity.this.startActivities(new Intent[]{intent});
//                progressBar = findViewById(R.id.idLoadingPB);

            } else if (item.getItemId() == R.id.friends) {
                replaceFragment(new FriendsFragment());
            } else if (item.getItemId() == R.id.notification) {
                replaceFragment(new NotificationFragment());
            } else {
                replaceFragment(new FbMenuFragment());
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
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