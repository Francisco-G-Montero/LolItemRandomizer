package com.frommetoyou.lolitemrandomizer.common.model.dataAccess.repositories;

import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;

public class FirebaseRepository implements GameRepository {
    private final String TAG = FirebaseRepository.class.getName();
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ValueEventListener valueEventListener;
    private GamePOJO currentGame;
    private FirebaseUser firebaseAuth;

    public FirebaseRepository() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    @Override
    public Observable<String> createGame(GamePOJO gamePOJO) {
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        gamePOJO.getOwner().setName(firebaseAuth.getDisplayName());
        return Observable.create(emitter -> {
            gamePOJO.setKey(reference.child(GamePOJO.GAME_TABLE).push().getKey());
            reference.child(GamePOJO.GAME_TABLE).child(gamePOJO.getKey()).setValue(gamePOJO);
            currentGame = gamePOJO;
            emitter.onNext(gamePOJO.getKey());
        });
    }

    ValueEventListener playerListener, joinGame;

    @Override
    public void removeListeners() {
        if (playerListener != null) reference.removeEventListener(playerListener);
        if (joinGame != null) reference.removeEventListener(joinGame);
    }


    @Override
    public Observable<GamePOJO> joinGame(String gameKey, String winDescription) {
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        return Observable.create(emitter -> {
            reference.child(GamePOJO.GAME_TABLE).child(gameKey)
                    .addListenerForSingleValueEvent(joinGame = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                currentGame = dataSnapshot.getValue(GamePOJO.class);
                                currentGame.setKey(dataSnapshot.getKey());
                                PlayerPOJO opponent = new PlayerPOJO();
                                opponent.setName(firebaseAuth.getDisplayName());
                                opponent.setWinDescription(winDescription);
                                currentGame.setOpponent(opponent);
                                reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey()).setValue(currentGame);
                                addPlayerToTheGame();
                                emitter.onNext(currentGame);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            emitter.onError(error.toException());
                            reference.removeEventListener(this);
                        }
                    });
        });
    }

    @Override
    public void addPlayerToTheGame() {
        currentGame.setConnectedPlayers(currentGame.getConnectedPlayers() + 1);
        reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey()).setValue(currentGame);
    }

    @Override
    public Observable<GamePOJO> retrieveGameListener() {
        return Observable.create(emitter -> {
            reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                currentGame = dataSnapshot.getValue(GamePOJO.class);
                                emitter.onNext(currentGame);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            reference.removeEventListener(this);
                            emitter.onError(error.toException());

                        }
                    });
        });
    }


    @Override
    public Observable<Integer> playerCountListener() {
        addPlayerToTheGame();
        return Observable.create(emitter -> {
            reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey())
                    .addValueEventListener(playerListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                currentGame = dataSnapshot.getValue(GamePOJO.class);
                                emitter.onNext(currentGame.getConnectedPlayers());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            reference.removeEventListener(this);
                            emitter.onError(error.toException());

                        }
                    });
        });
    }

    @Override
    public void updateGame(List<ItemPOJO> itemList, List<ItemPOJO> spellList, boolean playingAsHost) {
        reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            currentGame = dataSnapshot.getValue(GamePOJO.class);
                            if (playingAsHost) {
                                PlayerPOJO playerPOJO=currentGame.getOwner();
                                playerPOJO.setSummoners(spellList);
                                playerPOJO.setItems(itemList);
                                reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey()).child(GamePOJO.GAME_OWNER).setValue(playerPOJO);
                            } else {
                                PlayerPOJO playerPOJO=currentGame.getOpponent();
                                playerPOJO.setSummoners(spellList);
                                playerPOJO.setItems(itemList);
                                reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey()).child(GamePOJO.GAME_OPPONENT).setValue(playerPOJO);
                            }
                           // reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey()).child(GamePOJO.GAME_OWNER).;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        reference.removeEventListener(this);
                    }
                });
    }

    @Override
    public void clearGameData() {
        reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey()).removeValue();
    }

    @Override
    public void deleteCurrentGame() {
        if (currentGame!=null) reference.child(GamePOJO.GAME_TABLE).child(currentGame.getKey()).removeValue();
    }

    @Override
    public OptionsPOJO getGameOptions() {
        return currentGame.getSetupOptions();
    }

    @Override
    public void setGameOptions(OptionsPOJO options) {
        this.currentGame=new GamePOJO();
        currentGame.setSetupOptions(options);
    }
}
