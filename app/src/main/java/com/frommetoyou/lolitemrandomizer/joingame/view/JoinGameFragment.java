package com.frommetoyou.lolitemrandomizer.joingame.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frommetoyou.lolitemrandomizer.databinding.FragmentJoinGameBinding;
import com.frommetoyou.lolitemrandomizer.joingame.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.root.App;

import javax.inject.Inject;

public class JoinGameFragment extends Fragment implements View.OnClickListener, com.frommetoyou.lolitemrandomizer.joingame.view.View {
    private FragmentJoinGameBinding binding;

    @Inject
    Presenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getComponent().inject(JoinGameFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));

        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJoinGameBinding.inflate(inflater);
        binding.btnStartWithCode.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.etInputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>15) binding.btnStartWithCode.setEnabled(true);
                else  binding.btnStartWithCode.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {  }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.rxJavaUnsuscribe();
        binding=null;
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnStartWithCode){
            presenter.joinGame(binding.etInputCode.getText().toString(),binding.etWinDescription.getText().toString());
        }else if ( view == binding.btnBack ){
            NavHostFragment.findNavController(this).navigate(JoinGameFragmentDirections.actionJoinGameFragmentToCreateOrJoinFragment());
        }
    }

    @Override
    public void navigateToMainFragment() {
        NavHostFragment.findNavController(this).navigate(
                JoinGameFragmentDirections.actionJoinGameFragmentToMainFragment(false,false));
    }

    @Override
    public void showWaitingForOpponent() {
        binding.waitingForOpponentContainer.setVisibility(View.VISIBLE);
    }
}