package com.example.c1284520.myapplication;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c1284520.myapplication.NFCHelper.EnviaNFC;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;


public class Main2Activity extends Activity {
    private final int pedra = 0;
    private final int papel = 1;
    private final int tesoura = 2;

    public int escolhaJogador;
    public int escolhaPC;
    TextView textVencedor;
    ImageView ePC;
    ImageView eJogador;
    ImageView eTesoura;
    ImageView ePedra;
    ImageView ePapel;

    Bitmap imgTesoura;
    Bitmap imgPedra;
    Bitmap imgPapel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        bindEscPapel();
        bindEscPedra();
        bindEscTesoura();
        bindEscJogador();
        bindEscPC();
        bindTextVencedor();
    }

    private void bindTextVencedor() {
        textVencedor = (TextView) findViewById(R.id.textViewVencedor);
    }

    private void bindEscPC() {
        ePC = (ImageView) findViewById(R.id.EPc);
        Bitmap imagem = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.ic_question);
        ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
    }

    private void bindEscJogador() {
        eJogador = (ImageView) findViewById(R.id.Ejogador);
        Bitmap imagem = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.ic_question);
        eJogador.setImageBitmap(JokenpoHelper.getclip(imagem));
    }

    private void bindEscTesoura() {
        eTesoura = (ImageView) findViewById(R.id.Etesoura);
        eTesoura.getLayoutParams().width = 200;
        imgTesoura = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.tesoura);
        eTesoura.setImageBitmap(JokenpoHelper.getclip(imgTesoura));
        eTesoura.setOnClickListener(new View.OnClickListener() {
            Bitmap imagem;

            @Override
            public void onClick(View view) {
                imagem = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.tesoura);
                eJogador.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaJogador = tesoura;
                Bitmap imagem = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.ic_question);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                iniciaPartida();
            }
        });
    }

    private void bindEscPedra() {
        ePedra = (ImageView) findViewById(R.id.Epedra);
        ePedra.getLayoutParams().width = 200;
        imgPedra = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.pedra);
        ePedra.setImageBitmap(JokenpoHelper.getclip(imgPedra));

        ePedra.setOnClickListener(new View.OnClickListener() {
            Bitmap imagem;

            @Override
            public void onClick(View view) {
                imagem = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.pedra);
                eJogador.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaJogador = pedra;
                Bitmap imagem = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.ic_question);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                iniciaPartida();
            }
        });
    }

    private void bindEscPapel() {
        ePapel = (ImageView) findViewById(R.id.Epapel);
        ePapel.getLayoutParams().width = 200;
        imgPapel = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.papel);
        ePapel.setImageBitmap(JokenpoHelper.getclip(imgPapel));

        ePapel.setOnClickListener(new View.OnClickListener() {
            Bitmap imagem;

            @Override
            public void onClick(View view) {
                imagem = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.papel);
                eJogador.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaJogador = papel;
                Bitmap imagem = BitmapFactory.decodeResource(Main2Activity.this.getResources(), R.drawable.ic_question);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                iniciaPartida();
            }
        });
    }

    private void iniciaPartida() {
        escolhaPc();
        verificaVencedor();
    }


    private void verificaVencedor() {
        if (escolhaJogador == escolhaPC) {
            textVencedor.setTextColor(Color.BLACK);
            textVencedor.setText(getString(R.string.txt_draw));
        } else if ((escolhaJogador == 0 && escolhaPC == 2) ||
                (escolhaJogador == 1 && escolhaPC == 0) ||
                (escolhaJogador == 2 && escolhaPC == 1)) {
            textVencedor.setTextColor(Color.GREEN);
            textVencedor.setText(getString(R.string.txt_userWin));
        } else if ((escolhaJogador == 0 && escolhaPC == 1) ||
                (escolhaJogador == 1 && escolhaPC == 2) ||
                (escolhaJogador == 2 && escolhaPC == 0)) {
            textVencedor.setTextColor(Color.RED);
            textVencedor.setText(getString(R.string.txt_pcWins));
        }
    }

    private void escolhaPc() {
        Random gerador = new Random();
        int valor  = gerador.nextInt(3);
        Bitmap imagem;
        switch (valor) {
            case 0:
                imagem = BitmapFactory.decodeResource(this.getResources(), R.drawable.pedra);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaPC = pedra;
                break;
            case 1:
                imagem = BitmapFactory.decodeResource(this.getResources(), R.drawable.papel);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaPC = papel;
                break;
            case 2:
                imagem = BitmapFactory.decodeResource(this.getResources(), R.drawable.tesoura);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaPC = tesoura;
        }
    }
}
