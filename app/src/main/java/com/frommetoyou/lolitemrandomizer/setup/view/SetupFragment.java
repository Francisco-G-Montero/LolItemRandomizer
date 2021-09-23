package com.frommetoyou.lolitemrandomizer.setup.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frommetoyou.lolitemrandomizer.R;
import com.frommetoyou.lolitemrandomizer.databinding.FragmentSetupBinding;
import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;
import com.frommetoyou.lolitemrandomizer.root.App;
import com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories.OptionsPOJO;
import com.frommetoyou.lolitemrandomizer.setup.presenter.Presenter;

import javax.inject.Inject;

public class SetupFragment extends Fragment implements View.OnClickListener, com.frommetoyou.lolitemrandomizer.setup.view.View {
    private FragmentSetupBinding binding;
    private boolean playingSolo=false;

    @Inject
    Presenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getComponent().inject(SetupFragment.this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSetupBinding.inflate(inflater);
        playingSolo=SetupFragmentArgs.fromBundle(getArguments()).getPlayingSolo();
        if (!playingSolo) binding.winDescriptionContainer.setVisibility(View.VISIBLE);
        binding.btnStart.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
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
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnStart){
            String mapSelected;
            if(binding.radioGroup.getCheckedRadioButtonId()==binding.rbAram.getId()) mapSelected=getResources().getString(R.string.setup_radio_aram_map);
            else mapSelected=ItemPOJO.SPELL_MODE_CLASSIC;
            if (playingSolo){
                OptionsPOJO optionsPOJO=new OptionsPOJO();
                optionsPOJO.setMapSelected(mapSelected);
                optionsPOJO.setItemPool(getItemPool());
                presenter.setSetupOptions(optionsPOJO);
                NavHostFragment.findNavController(this)
                        .navigate(SetupFragmentDirections.actionSetupFragmentToMainFragment(true,true));
            }else{
                NavHostFragment.findNavController(this)
                        .navigate(SetupFragmentDirections.actionSetupFragmentToShareLinkFragment(getItemPool(),mapSelected,binding.etWinDescription.getText().toString(),false));
            }
        }else if (view == binding.btnBack){
            NavHostFragment.findNavController(this)
                    .navigate(SetupFragmentDirections.actionSetupFragmentToCreateOrJoinFragment());
        }
    }
    private String getItemPool(){
        String item_pool;
        int id = binding.radioItemTypeGroup.getCheckedRadioButtonId();
        if(id==binding.rbComplete.getId()) item_pool=ItemPOJO.ITEM_POOL_ONLY_COMPLETE;
        else if(id==binding.rbIncomplete.getId()) item_pool=ItemPOJO.ITEM_ONLY_INCOMPLETE;
        else item_pool=ItemPOJO.ITEM_POOL_BOTH;
        return item_pool;
    }
}