package br.com.bravoti.pgmadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.app.ProgressDialog;

/**
 * Created by Vitor on 27/03/2016.
 */

public class MainActivity extends Activity {
    ProgressBar pBar;
    Button btnConfig;
    ProgressDialog dialog;
    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*
    	super.onCreate(savedInstanceState);
        resultArea = new TextView(this);
        resultArea.setText("Please wait.");
        setContentView(resultArea);
        new FetchSQL().execute();*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConfig = (Button) findViewById(R.id.btnConecta);
        pBar = (ProgressBar) findViewById(R.id.pBar);
    }

    public void Conecta_click(View v) {
        pBar.setVisibility(View.VISIBLE);
        new ConectaBanco().execute();
    }

    public void Sair_click(View v) {
        finish();
    }

    private class ConectaBanco extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            // Cria a caixa de dialogo em quanto faz a conexão
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Carregando...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            if (ConectaPostgreSQL.getInstance().getConn() == null) {
                return "SEM CONEXÃO";
            } else {
                return "OK";
            }
        }

        @Override
        protected void onPostExecute(String sRetorno) {
            super.onPostExecute(sRetorno);

            if (sRetorno == "SEM CONEXÃO") {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Erro ao conectar no banco");
                pBar.setVisibility(View.INVISIBLE);
                alertDialog.setMessage(sRetorno);
                alertDialog.setButton("OK", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            } else {
// Abre a Tela
                Intent i = new Intent(context, DadosActivity.class);
                startActivity(i);
                finish();

            }
// Fecha a janela d;/ o carregando
            dialog.dismiss();
        }
    }
}