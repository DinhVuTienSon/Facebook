package vn.edu.usth.facebook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler = new Handler();
    public static final long TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(runnable, TIME);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Check the user's login status by launching LoginActivity
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };
}