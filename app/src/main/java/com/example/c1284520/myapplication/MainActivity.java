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
import android.widget.Toast;

import com.example.c1284520.myapplication.NFCHelper.EnviaNFC;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends Activity {
    private NfcAdapter mNfcAdapter;
    private NdefMessage mNdefMessage;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists;


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
        setContentView(R.layout.activity_main);


        bindEscPapel();
        bindEscPedra();
        bindEscTesoura();
        bindEscJogador();
        bindEscPC();
        bindTextVencedor();
    }

    private final String[][] techList = new String[][]{
            new String[]{
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };


    private void bindTextVencedor() {
        textVencedor = (TextView) findViewById(R.id.textViewVencedor);
    }

    private void bindEscPC() {
        ePC = (ImageView) findViewById(R.id.EPc);
        Bitmap imagem = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.ic_question);
        ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
    }

    private void bindEscJogador() {
        eJogador = (ImageView) findViewById(R.id.Ejogador);
        Bitmap imagem = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.ic_question);
        eJogador.setImageBitmap(JokenpoHelper.getclip(imagem));
    }

    private void bindEscTesoura() {
        eTesoura = (ImageView) findViewById(R.id.Etesoura);
        eTesoura.getLayoutParams().width = 200;
        imgTesoura = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.tesoura);
        eTesoura.setImageBitmap(JokenpoHelper.getclip(imgTesoura));
        eTesoura.setOnClickListener(new View.OnClickListener() {
            Bitmap imagem;

            @Override
            public void onClick(View view) {
                imagem = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.tesoura);
                eJogador.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaJogador = tesoura;
                Bitmap imagem = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.ic_question);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                textVencedor.setText("Encoste os dispositivos para jogar");
                iniciaPartida();
            }
        });
    }

    private void bindEscPedra() {
        ePedra = (ImageView) findViewById(R.id.Epedra);
        ePedra.getLayoutParams().width = 200;
        imgPedra = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.pedra);
        ePedra.setImageBitmap(JokenpoHelper.getclip(imgPedra));

        ePedra.setOnClickListener(new View.OnClickListener() {
            Bitmap imagem;

            @Override
            public void onClick(View view) {
                imagem = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.pedra);
                eJogador.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaJogador = pedra;
                Bitmap imagem = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.ic_question);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                textVencedor.setText("Encoste os dispositivos para jogar");
                iniciaPartida();
            }
        });
    }

    private void bindEscPapel() {
        ePapel = (ImageView) findViewById(R.id.Epapel);
        ePapel.getLayoutParams().width = 200;
        imgPapel = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.papel);
        ePapel.setImageBitmap(JokenpoHelper.getclip(imgPapel));

        ePapel.setOnClickListener(new View.OnClickListener() {
            Bitmap imagem;

            @Override
            public void onClick(View view) {
                imagem = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.papel);
                eJogador.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaJogador = papel;
                Bitmap imagem = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.ic_question);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                textVencedor.setText("Encoste os dispositivos para jogar");
                iniciaPartida();
            }
        });
    }

    private void iniciaPartida() {
        //escolhaPC();
        enviaEscolha();

    }

    private void enviaEscolha() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter != null) {
            textVencedor.setText("Encoste os dispositivos para jogar");
        } else {
            textVencedor.setText("NFC desligado. Para jogar ligue!");
        }
        mNdefMessage = new NdefMessage(
                new NdefRecord[]{
                        EnviaNFC.createNewTextRecord(String.valueOf(escolhaJogador), Locale.ENGLISH, true)});

        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundNdefPush(this, mNdefMessage);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {

            Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (data != null) {
                try {
                    for (int i = 0; i < data.length; i++) {
                        NdefRecord[] recs = ((NdefMessage) data[i]).getRecords();
                        for (int j = 0; j < recs.length; j++) {
                            if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                                    Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {
                                byte[] payload = recs[j].getPayload();
                                String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                                int langCodeLen = payload[0] & 0077;

                                escolhaPC = Integer.parseInt(new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1,
                                        textEncoding));
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("RecebeNFC", e.toString());
                }
            }
            escolhaPc(escolhaPC);

        }
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

    private void escolhaPc(int valor) {
        Bitmap imagem;
        switch (valor) {
            case 0:
                imagem = BitmapFactory.decodeResource(this.getResources(), R.drawable.pedra);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaPC = pedra;
                verificaVencedor();
                break;
            case 1:
                imagem = BitmapFactory.decodeResource(this.getResources(), R.drawable.papel);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaPC = papel;
                verificaVencedor();
                break;
            case 2:
                imagem = BitmapFactory.decodeResource(this.getResources(), R.drawable.tesoura);
                ePC.setImageBitmap(JokenpoHelper.getclip(imagem));
                escolhaPC = tesoura;
                verificaVencedor();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // creating pending intent:
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events:
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.mNFCTechLists);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
