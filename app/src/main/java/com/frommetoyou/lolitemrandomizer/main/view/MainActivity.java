package com.frommetoyou.lolitemrandomizer.main.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import nl.dionsegijn.konfetti.emitters.StreamEmitter;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.frommetoyou.lolitemrandomizer.R;
import com.frommetoyou.lolitemrandomizer.databinding.ActivityMainBinding;
import com.frommetoyou.lolitemrandomizer.main.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.root.App;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, com.frommetoyou.lolitemrandomizer.main.view.View {
    private static final String TAG = MainActivity.class.getName();
    private ActivityMainBinding binding;
    private NavController navController;
    private BottomSheetBehavior bottomSheetBehavior;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Inject
    Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot()); //R.layout.activity_main
        ((App) getApplication()).getComponent().inject(this);

        setSupportActionBar(binding.toolbar);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container); //fragment container
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            binding.toolbar.setTitle(destination.getLabel());
            binding.toolbar.setNavigationIcon(null);
            // binding.toolbar.setNavigationIcon(null);
        });
        bottomSheetBehavior = BottomSheetBehavior.from((binding.bottomSheet.container));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        binding.btnMusic.setOnClickListener(this);
        binding.bottomSheet.btnCancel.setOnClickListener(this);
        binding.bottomSheet.btnExit.setOnClickListener(this);
        binding.btnParticles.setOnClickListener(this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        mediaPlayer = MediaPlayer.create(this, R.raw.background_main_music);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.privacy_policy){
            Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy_url)));
            startActivity(browser);
        }else    if (item.getItemId()==R.id.developed_by){
            Snackbar.make(binding.getRoot(),getString(R.string.contact_me),Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void renderParticleEffectsBuilder() {
        binding.contentMain.viewKonfetti.build()
                .addColors(Color.rgb(250, 192, 0))
                .setDirection(-359.0, 0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(800L)
                .addShapes(Shape.Circle.INSTANCE)
                .addSizes(new Size(2, 20f))
                .setPosition((binding.contentMain.viewKonfetti.getWidth()) / 5f,
                        4f * (binding.contentMain.viewKonfetti.getWidth()) / 5f,
                        ((float) binding.contentMain.viewKonfetti.getHeight()) / 2 - 0f,
                        ((float) binding.contentMain.viewKonfetti.getHeight()) / 2 - 0f)
                .streamFor(10, StreamEmitter.INDEFINITE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.resumeBackgroundMusic();
        startRenderingParticles();

/*
        binding.contentMain.particle.resume();
*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pauseBackgroundMusic();
        binding.contentMain.viewKonfetti.stopGracefully();
        stopRenderingParticles();

/*
        binding.contentMain.particle.pause();
*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        Log.v("LLAMADO","ASD");
        presenter.deleteCurrentGame();
        presenter.destroyBackgroundMusic();
        presenter.rxJavaUnsuscribe();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.bottomSheet.btnCancel) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view == binding.btnMusic) {
            if (mediaPlayer.isPlaying()) {
                presenter.stopBackgroundMusic();
                binding.btnMusic.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_music_off_24));
            } else {
                presenter.startBackgroundMusic();
                binding.btnMusic.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_music_note_24));
            }
        } else if (view == binding.btnParticles) {

            if (binding.contentMain.viewKonfetti.isActive()) {
                stopRenderingParticles();
            } else {
                startRenderingParticles();
            }
        } else if (view == binding.bottomSheet.btnExit) finish();
    }

    private void startRenderingParticles() {
        renderParticleEffectsBuilder();
        binding.btnParticles.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_visibility_24));
    }

    private void stopRenderingParticles() {
        binding.contentMain.viewKonfetti.stopGracefully();
        binding.btnParticles.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_visibility_off_24));
    }

    @Override
    public void onBackPressed() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void showSnackMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void pauseMedia() {
        mediaPlayer.pause();
    }

    @Override
    public void resumeMedia() {
        mediaPlayer.start();
    }

    @Override
    public void destroyMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}