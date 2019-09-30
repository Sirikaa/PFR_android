package com.epsi.myproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.epsi.myproject.Client;
import com.epsi.myproject.Adapters.ContactAdapter;
import com.epsi.myproject.Personne;
import com.epsi.myproject.R;

import java.util.List;


public class ListeContacts extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_contacts);

        Bundle extras = getIntent().getExtras();
        Client c = (Client) extras.getSerializable("objClient");
        final List<Personne> contacts = c.getContacts();
        ContactAdapter adapter = new ContactAdapter(this, contacts);
        ListView contactView = (ListView) findViewById(R.id.listViewContact);
        contactView.setAdapter(adapter);

        contactView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO A FAIRE QUAND ON VOUDRA MODIFIER OU SUPPRIMER UN CONTACT
                Personne contact = contacts.get(position);
                //Intent intent = new Intent(ListeContacts.this, .class);
                //intent.putExtra("", );
                //startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}