package com.frommetoyou.lolitemrandomizer.sharelink;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frommetoyou.lolitemrandomizer.databinding.FragmentShareLinkBinding;
import com.frommetoyou.lolitemrandomizer.root.App;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;


public class ShareLinkFragment extends Fragment implements View.OnClickListener, ShareLinkMVP.View {
    private FragmentShareLinkBinding binding;
    private String mapSelected;
    private String itemPool;
    private String winDescription;
    private boolean playingSolo=false;

    @Inject
    ShareLinkMVP.Presenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getComponent().inject(ShareLinkFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShareLinkBinding.inflate(inflater);
        itemPool = ShareLinkFragmentArgs.fromBundle(getArguments()).getItemPool();
        mapSelected = ShareLinkFragmentArgs.fromBundle(getArguments()).getMapSelected();
        winDescription = ShareLinkFragmentArgs.fromBundle(getArguments()).getWinDescription();
        playingSolo = ShareLinkFragmentArgs.fromBundle(getArguments()).getPlayingSolo();
        binding.btnCreateGame.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.btnCopyCode.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        presenter.rxJavaUnsuscribe();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnCreateGame) {
            OptionsPOJO setupOptions=new OptionsPOJO(itemPool,mapSelected);
            presenter.createGame(setupOptions,ShareLinkFragmentArgs.fromBundle(getArguments()).getWinDescription());
        } else if (view == binding.btnBack) {
            presenter.deleteCurrentGame();
            NavHostFragment.findNavController(this).navigate(ShareLinkFragmentDirections.actionShareLinkFragmentToSetupFragment(playingSolo));
        } else if (view == binding.btnCopyCode) {
            presenter.copyCode(getActivity(), binding.etCode.getText().toString());
        }
    }

    @Override
    public void showWaitingBar(String message) {
        binding.tvWaitingForOppoennt.setText(message);
        binding.waitingForOpponentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableCreateGameButton() {
        binding.btnCreateGame.setEnabled(false);
    }

    @Override
    public void showSnackMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void updateGameCode(String code) {
        binding.etCode.setText(code);
    }

    @Override
    public void navigateToMainFragment() {
        NavHostFragment.findNavController(this).navigate(ShareLinkFragmentDirections.actionShareLinkFragmentToMainFragment(true,false));
    }
}