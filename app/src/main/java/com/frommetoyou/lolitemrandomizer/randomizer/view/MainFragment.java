package com.frommetoyou.lolitemrandomizer.randomizer.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.frommetoyou.lolitemrandomizer.R;
import com.frommetoyou.lolitemrandomizer.databinding.FragmentMainBinding;
import com.frommetoyou.lolitemrandomizer.randomizer.adapters.LoLItemAdapter;
import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;
import com.frommetoyou.lolitemrandomizer.randomizer.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.root.App;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.GamePOJO;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainFragment extends Fragment implements com.frommetoyou.lolitemrandomizer.randomizer.view.View, View.OnClickListener {

    private static final String TAG = MainFragment.class.getName();
    private FragmentMainBinding binding;
    private LoLItemAdapter loLItemAdapter, summonerItemAdapter, opponentLoLItemAdapter, opponentSummonerItemAdapter;
    private List<ItemPOJO> itemPOJOList = new ArrayList<>();
    private List<ItemPOJO> summonerPOJOList = new ArrayList<>();
    private List<ItemPOJO> opponentItemPOJOList = new ArrayList<>();
    private List<ItemPOJO> opponentSummonerPOJOList = new ArrayList<>();
    private MediaPlayer tickSoundMediaPlayer = new MediaPlayer();
    private MediaPlayer chosenSoundMediaPlayer = new MediaPlayer();
    private RecyclerViewSkeletonScreen skeleton;
    private boolean playingAsHost = true, playingSolo = false;
    private String winDescriptionCached = "";

    @Inject
    Presenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getComponent().inject(MainFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater);
        playingAsHost = MainFragmentArgs.fromBundle(getArguments()).getPlayingAsHost();
        playingAsHost = MainFragmentArgs.fromBundle(getArguments()).getPlayingAsHost();
        playingSolo = MainFragmentArgs.fromBundle(getArguments()).getPlayingSolo();
        if (playingSolo) {
            binding.containerLoadingOpponent.setVisibility(View.GONE);
            binding.containerOpponent.setVisibility(View.GONE);
        }
        loLItemAdapter = new LoLItemAdapter(itemPOJOList);
        binding.itemRecyclerView.setAdapter(loLItemAdapter);
        summonerItemAdapter = new LoLItemAdapter(summonerPOJOList);
        binding.summonerRecyclerView.setAdapter(summonerItemAdapter);
        opponentLoLItemAdapter = new LoLItemAdapter(opponentItemPOJOList);
        binding.opponentItemsRecyclerView.setAdapter(opponentLoLItemAdapter);
        opponentSummonerItemAdapter = new LoLItemAdapter(opponentSummonerPOJOList);
        binding.opponentSummonersRecyclerView.setAdapter(opponentSummonerItemAdapter);

        binding.btnStop.setVisibility(View.GONE);
        binding.btnRandomize.setOnClickListener(this);
        binding.btnStop.setOnClickListener(this);
        binding.btnFinishGame.setOnClickListener(this);
        tickSoundMediaPlayer = MediaPlayer.create(getContext(), R.raw.tick_sound);
        chosenSoundMediaPlayer = MediaPlayer.create(getContext(), R.raw.chosen_items_sound);
        skeleton = Skeleton.bind(binding.itemRecyclerView)
                .adapter(loLItemAdapter)
                .load(R.layout.lol_item_list_item)
                .show();
        if (!playingSolo){
            binding.containerLoadingOpponent.setVisibility(View.VISIBLE);
            binding.containerOpponent.setVisibility(View.GONE);
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.setPlayingAsHost(playingAsHost);
        presenter.setPlayingSolo(playingSolo);
        if(itemPOJOList.size()<1){
            presenter.loadSummonerData();
            presenter.loadItemData();
        }
        //presenter.loadOpponentData();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.setPlayingAsHost(playingAsHost);
        presenter.setPlayingSolo(playingSolo);
        //presenter.loadSummonerData();
        //presenter.loadItemData();
        //presenter.loadOpponentData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //presenter.clearGameData();
        presenter.rxJavaUnsuscribe();
        itemPOJOList.clear();
        loLItemAdapter.notifyDataSetChanged();
        summonerPOJOList.clear();
        summonerItemAdapter.notifyDataSetChanged();
        opponentItemPOJOList.clear();
        opponentLoLItemAdapter.notifyDataSetChanged();
        opponentSummonerPOJOList.clear();
        opponentSummonerItemAdapter.notifyDataSetChanged();
        binding = null;
        tickSoundMediaPlayer.release();
        chosenSoundMediaPlayer.release();
        tickSoundMediaPlayer = null;
        chosenSoundMediaPlayer = null;
    }

    @Override
    public void addItemData(ItemPOJO item) {
        Log.v(TAG, "Item encontrado: " + item.getName());
        itemPOJOList.add(item);
        loLItemAdapter.notifyItemInserted(itemPOJOList.size() - 1);
        skeleton.hide();
    }

    @Override
    public void addSummonerData(ItemPOJO spellPOJO) {
        Log.v(TAG, "Summoner encontrado: " + spellPOJO.getName());
        summonerPOJOList.add(spellPOJO);
        summonerItemAdapter.notifyItemInserted(summonerPOJOList.size() - 1);
    }

    @Override
    public void showSnackMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void clearData() {
        itemPOJOList.clear();
        loLItemAdapter.notifyDataSetChanged();
        summonerPOJOList.clear();
        summonerItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateItemData(int index, ItemPOJO next) {
        itemPOJOList.set(index, next);
        loLItemAdapter.notifyItemChanged(index);

    }

    @Override
    public void showOpponentItems(GamePOJO gamePOJO) {
        if (getActivity() != null) {
            opponentItemPOJOList.clear();
            opponentSummonerPOJOList.clear();
            opponentSummonerItemAdapter.notifyDataSetChanged();
            opponentSummonerItemAdapter.notifyDataSetChanged();
            binding.containerOpponent.setVisibility(View.VISIBLE);
            Log.v("PASOUNAVEZ","playingAsHost: "+playingAsHost);
            if (playingAsHost && gamePOJO.getOpponent().getItems() != null) {
                opponentItemPOJOList.addAll(gamePOJO.getOpponent().getItems());
                opponentSummonerPOJOList.addAll(gamePOJO.getOpponent().getSummoners());
                if(gamePOJO.getOpponent().getName()!=null) binding.tvOpponentItems.setText(getString(R.string.main_item_opponent_title, gamePOJO.getOpponent().getName()));
                else binding.tvOpponentItems.setText(getString(R.string.main_item_opponent_title, "... esperando al oponente"));
                binding.tvOpponentSummoners.setText(getString(R.string.main_summoner_opponent_title, gamePOJO.getOpponent().getName()));
                winDescriptionCached = gamePOJO.getOwner().getWinDescription();
            } else if (!playingAsHost&&gamePOJO.getOwner().getItems() != null) {
                opponentItemPOJOList.addAll(gamePOJO.getOwner().getItems());
                opponentSummonerPOJOList.addAll(gamePOJO.getOwner().getSummoners());
                if(gamePOJO.getOwner().getName()!=null)binding.tvOpponentItems.setText(getString(R.string.main_item_opponent_title, gamePOJO.getOwner().getName()));
                else binding.tvOpponentItems.setText(getString(R.string.main_item_opponent_title, "... esperando al oponente"));
                binding.tvOpponentSummoners.setText(getString(R.string.main_summoner_opponent_title, gamePOJO.getOwner().getName()));
                winDescriptionCached = gamePOJO.getOpponent().getWinDescription();
            }
            binding.containerLoadingOpponent.setVisibility(View.GONE);
            opponentSummonerItemAdapter.notifyDataSetChanged();
            opponentLoLItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateSummonermData(int index, ItemPOJO next) {
        summonerPOJOList.set(index, next);
        summonerItemAdapter.notifyItemChanged(index);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnRandomize) presenter.randomize();
        else if (view == binding.btnStop) presenter.stopRandomize();
        else if (view == binding.btnFinishGame) {
            new MaterialAlertDialogBuilder(getActivity(), R.style.MaterialAlertDialog_MaterialComponents_Title_Icon)
                    .setTitle(R.string.main_are_you_sure_finish_game)
                    .setMessage(R.string.main_alert_message1_finish_game)
                    .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                        NavHostFragment.findNavController(this).navigate(MainFragmentDirections.actionMainFragmentToFinishGameFragment(winDescriptionCached));
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
        }
    }

    @Override
    public void reproduceTickSound() {
        if (tickSoundMediaPlayer != null) {
            if (tickSoundMediaPlayer.isPlaying()) {
                tickSoundMediaPlayer.pause();
                tickSoundMediaPlayer.seekTo(0);
            }
            tickSoundMediaPlayer.start();
        }
    }

    @Override
    public void reproduceChosenItemsSound() {
        if (chosenSoundMediaPlayer != null) {
            if (chosenSoundMediaPlayer.isPlaying()) {
                chosenSoundMediaPlayer.pause();
                chosenSoundMediaPlayer.seekTo(0);
            }
            chosenSoundMediaPlayer.seekTo(345);
            chosenSoundMediaPlayer.start();
        }
    }

    @Override
    public void hideOrShowStopRandomizeButton() {
        MaterialSharedAxis sharedAxis = new MaterialSharedAxis(MaterialSharedAxis.X, true);
        sharedAxis.setDuration(200L);
        TransitionManager.beginDelayedTransition(binding.buttonsContainer);
        if (binding.btnStop.getVisibility() == View.VISIBLE) {
            binding.btnStop.setVisibility(View.GONE);
            //binding.btnRandomize.setVisibility(View.VISIBLE);
            binding.btnRandomize.setEnabled(true);
            binding.btnFinishGame.setVisibility(View.GONE);
        } else {
            // binding.btnRandomize.setVisibility(View.GONE);
            binding.btnRandomize.setEnabled(false);
            binding.btnStop.setVisibility(View.VISIBLE);
            binding.btnFinishGame.setVisibility(View.GONE);
        }
    }

    @Override
    public void enableUIInteractions() {
        binding.btnRandomize.setEnabled(true);
        skeleton.hide();
    }

    @Override
    public void enableFinishGameButton() {
        binding.btnFinishGame.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableUIInteractions() {
        binding.btnRandomize.setEnabled(false);
    }

    @Override
    public List<ItemPOJO> getItemList() {
        return itemPOJOList;
    }

    @Override
    public List<ItemPOJO> getSpellList() {
        return summonerPOJOList;
    }
}