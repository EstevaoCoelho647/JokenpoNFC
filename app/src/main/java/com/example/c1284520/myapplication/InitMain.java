package com.example.c1284520.myapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by c1284520 on 23/10/2015.
 */
public class InitMain extends Activity {

    TextView btnSair;
    TextView btnJogar;
    Switch swith;
    boolean twoPlayers = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        bindSwitch();
        bindBtnJogar(R.id.jogar);
        bindBtnSair(R.id.sair);


    }

    private void bindBtnSair(int sair) {
        TextView btnSair = (TextView) findViewById(sair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void bindBtnJogar(int jogar) {
        TextView btnJogar = (TextView) findViewById(jogar);
        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (twoPlayers) {
                    Intent goToGame = new Intent(InitMain.this, Main2Activity.class);
                    startActivity(goToGame);
                }
                else{
                    Intent goToGame = new Intent(InitMain.this, MainActivity.class);
                    startActivity(goToGame);
                }

            }
        });
    }

    private void bindSwitch() {
        final Switch swith = (Switch) findViewById(R.id.switch1);

        swith.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (swith.getShowText()) {
                    swith.setShowText(false);
                    twoPlayers = false;
                } else {
                    swith.setShowText(true);
                    twoPlayers = true;
                }

            }
        });
    }
}
