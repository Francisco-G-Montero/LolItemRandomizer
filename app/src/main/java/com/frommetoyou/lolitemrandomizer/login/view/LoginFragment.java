package com.frommetoyou.lolitemrandomizer.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frommetoyou.lolitemrandomizer.R;
import com.frommetoyou.lolitemrandomizer.databinding.FragmentLoginBinding;
import com.frommetoyou.lolitemrandomizer.login.presenter.Presenter;
import com.frommetoyou.lolitemrandomizer.root.App;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;


public class LoginFragment extends Fragment implements View.OnClickListener, com.frommetoyou.lolitemrandomizer.login.view.View {
    private FragmentLoginBinding binding;
    private final String TAG=LoginFragment.class.getName();
    private static final int RC_GOOGLE_SIGN_IN = 100;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    @Inject
    Presenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((App) context.getApplicationContext()).getComponent().inject(LoginFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater);
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(getContext(),googleSignInOptions);

        binding.btnGoogle.setOnClickListener(this);
        return binding.getRoot();
    }

    private void signInGoogle() {
        googleSignInClient.signOut();
        Intent signInIntent=googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_GOOGLE_SIGN_IN);
    }
    private void signInGoogleFirebase(String idToken){
        AuthCredential authCredential=GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v(TAG,"USUARIO: "+firebaseAuth.getCurrentUser().getDisplayName());
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
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
    }

    @Override
    public void onClick(View view) {
       /* if (view == binding.btnLogin) {
            presenter.loginUser();
        } else */
        if (view == binding.btnGoogle){
            signInGoogle();
        }
    }


    @Override
    public void showMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.btnLoginInvisible)
                .show();
    }

    @Override
    public void navigateToSetupFragment() {
        NavHostFragment.findNavController(this)
                .navigate(LoginFragmentDirections.actionLoginFragment2ToCreateOrJoinFragment());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult googleSignInResult= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                signInGoogleFirebase(account.getIdToken());
                presenter.loginUser();
            }catch (ApiException e){
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
}