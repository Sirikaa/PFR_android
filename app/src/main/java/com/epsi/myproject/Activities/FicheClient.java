package com.epsi.myproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.epsi.myproject.Client;
import com.epsi.myproject.Persistence.JsonApiPersistence;
import com.epsi.myproject.R;

public class FicheClient extends AppCompatActivity{
    private Client c;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiche_client);

        Bundle extras = getIntent().getExtras();
        int idClient = (int) extras.getSerializable("idClient");

        this.c = JsonApiPersistence.getInfosClient(idClient);

        initializeView();
    }

    private void initializeView(){
        TextView ficheClientNom = (TextView) findViewById(R.id.fiche_client_nom);
        ficheClientNom.setText(c.getNom());

        TextView adresseClient = (TextView) findViewById(R.id.adresseClient);
        if(c.getAdresse1() == "null"){
            TextView adresseClientLabel = (TextView) findViewById(R.id.adresseClientLabel);
            adresseClientLabel.setVisibility(View.GONE);
            adresseClient.setVisibility(View.GONE);
        }else{
            adresseClient.setText(c.getAdresse1());
        }


        TextView adresseClient2 = (TextView) findViewById(R.id.adresseClient2);
        if(c.getAdresse2() == "null"){
            TextView adresseClient2Label = (TextView) findViewById(R.id.adresseClient2Label);
            adresseClient2Label.setVisibility(View.GONE);
            adresseClient2.setVisibility(View.GONE);
        }else{
            adresseClient2.setText(c.getAdresse2());
        }


        TextView villeClient = (TextView) findViewById(R.id.villeClient);
        if(c.getVille().getVille() == "null"){
            TextView villeClientLabel = (TextView) findViewById(R.id.villeClientLabel);
            villeClientLabel.setVisibility(View.GONE);
            villeClient.setVisibility(View.GONE);
        }else{
            villeClient.setText(c.getVille().getVille());
        }


        TextView cpClient = (TextView) findViewById(R.id.cpClient);
        if(c.getVille().getCp() == "null"){
            TextView cpClientLabel = (TextView) findViewById(R.id.cpClientLabel);
            cpClientLabel.setVisibility(View.GONE);
            cpClient.setVisibility(View.GONE);
        }else{
            cpClient.setText(c.getVille().getCp());
        }


        if(c.getAdresse1() == "null" && c.getAdresse2() == "null" && c.getVille().getVille() == "null" && c.getVille().getCp() == "null"){
            TextView aucuneInfo = (TextView) findViewById(R.id.aucuneInfo);
            aucuneInfo.setText("Aucune info");
        }else{
            TextView aucuneInfo = (TextView) findViewById(R.id.aucuneInfo);
            aucuneInfo.setVisibility(View.GONE);
        }

        Button contactButton = (Button) findViewById(R.id.contactButton);
        //Classe interne pour réagir au clique sur le bouton CONTACTS
        contactButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(FicheClient.this, ListeContacts.class);
                intent.putExtra("idClient", c.getId());
                startActivity(intent);
            }
        });

        Button materielButton = (Button) findViewById(R.id.materielButton);
        materielButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(FicheClient.this, ListeMateriels.class);
                intent.putExtra("idClient", c.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        //RIEN
    }

}
