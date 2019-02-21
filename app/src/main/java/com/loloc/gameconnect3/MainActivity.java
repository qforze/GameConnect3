package com.loloc.gameconnect3;

import android.content.Intent;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    int activePlayer;

    boolean gameIsActive = true;

    // 2 means unplayed

   int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

   int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void showEnd(String string){

        ConstraintLayout overlay = findViewById(R.id.overlay);
        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);

        if(overlay.getVisibility() == View.GONE){

            overlay.startAnimation(slideUp);
            overlay.setVisibility(View.VISIBLE);

            TextView winnerMessage = findViewById(R.id.winnerMessage);
            winnerMessage.setText(string);

        }
    }

    public void appear(View view) {

        ImageView pawn = (ImageView) view;

        System.out.println(pawn.getTag().toString());

        int tappedPawn = Integer.parseInt(pawn.getTag().toString());

        if (gameState[tappedPawn] == 2 && gameIsActive) {

            gameState[tappedPawn] = activePlayer;

            pawn.setTranslationX(-1000);

            if (activePlayer == 0) {

                pawn.setImageResource(R.drawable.yellow);

                activePlayer = 1;

            } else {

                pawn.setImageResource(R.drawable.red);

                activePlayer = 0;
            }

            pawn.animate().translationXBy(1000).setDuration(500);

            for (int[] winningPosition : winningPositions) {

                if ((gameState[winningPosition[0]] == gameState[winningPosition[1]]) &&
                        (gameState[winningPosition[1]] == gameState[winningPosition[2]]) &&
                        (gameState[winningPosition[0]] != 2)) {

                    gameIsActive = false;

                    int winner = (int) Array.get(winningPosition, 0);

                    if (winner == 1) {

                       showEnd("RED WON!");

                    } else if (winner == 0) {

                        showEnd("YELLOW WON!");

                    } for (int i = 0; i < 9; i++) {

                        if (gameState[i] != 2){

                            showEnd("TRY AGAIN!");

                        }

                    }

                    }

                }

            }
        }
    public void playAgain(View view) {

        final ConstraintLayout overlay = findViewById(R.id.overlay);
        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        overlay.startAnimation(slideDown);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                overlay.setVisibility(View.GONE);
            }
        }, 500);

        activePlayer = 0;

        for(int i = 0; i < gameState.length; i++){

            gameState[i] = 2;

        }

        RelativeLayout pawns = findViewById(R.id.relativeLayout);

        for(int i = 0; i < pawns.getChildCount(); i++) {

            ((ImageView) pawns.getChildAt(i)).setImageResource(0);

        }

        gameIsActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
