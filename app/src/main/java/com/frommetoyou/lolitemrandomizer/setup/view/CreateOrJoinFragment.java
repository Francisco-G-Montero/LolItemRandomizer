package com.frommetoyou.lolitemrandomizer.setup.view;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.frommetoyou.lolitemrandomizer.R;
import com.frommetoyou.lolitemrandomizer.databinding.FragmentCreateOrJoinBinding;
import com.google.android.material.transition.MaterialSharedAxis;

public class CreateOrJoinFragment extends Fragment implements View.OnClickListener {
    private FragmentCreateOrJoinBinding binding;
    private NavDirections navigation = CreateOrJoinFragmentDirections.actionCreateOrJoinFragmentToSetupFragment(false);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateOrJoinBinding.inflate(inflater);
        binding.btnBack.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.btnNext.setEnabled(false);
        binding.btnCreateVersusGame.setOnClickListener(this);
        binding.btnJoinGame.setOnClickListener(this);
        binding.btnSoloGame.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 50f, 0f);
        animator.setDuration(1000);//1sec
        animator.setInterpolator(new BounceInterpolator());
        if (view == binding.btnBack) {
            NavHostFragment.findNavController(this).navigate(CreateOrJoinFragmentDirections.actionCreateOrJoinFragmentToLoginFragment2());
        }  else if (view == binding.btnSoloGame) {
            navigation = CreateOrJoinFragmentDirections.actionCreateOrJoinFragmentToSetupFragment(true);
            binding.btnNext.setEnabled(true);
            binding.btnSoloGame.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_check_24));
            binding.btnCreateVersusGame.setIcon(null);
            binding.btnJoinGame.setIcon(null);
            animator.start();
        } else if (view == binding.btnCreateVersusGame) {
            navigation = CreateOrJoinFragmentDirections.actionCreateOrJoinFragmentToSetupFragment(false);
            binding.btnNext.setEnabled(true);
            binding.btnCreateVersusGame.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_check_24));
            binding.btnSoloGame.setIcon(null);
            binding.btnJoinGame.setIcon(null);
            animator.start();
        } else if (view == binding.btnJoinGame) {
            navigation = CreateOrJoinFragmentDirections.actionCreateOrJoinFragmentToJoinGameFragment();
            binding.btnNext.setEnabled(true);
            binding.btnJoinGame.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_check_24));
            binding.btnSoloGame.setIcon(null);
            binding.btnCreateVersusGame.setIcon(null);
            animator.start();
        } else if (view == binding.btnNext) {
            NavHostFragment.findNavController(this).navigate(navigation);
        }
    }
}