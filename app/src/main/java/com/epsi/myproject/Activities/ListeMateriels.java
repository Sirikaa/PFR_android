package com.epsi.myproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.epsi.myproject.Adapters.MaterielAdapter;
import com.epsi.myproject.Adapters.TypeMaterielSpinner;
import com.epsi.myproject.Client;
import com.epsi.myproject.Materiel;
import com.epsi.myproject.Persistence.JsonApiPersistence;
import com.epsi.myproject.R;
import java.util.ArrayList;
import java.util.List;

public class ListeMateriels extends AppCompatActivity {
    private Client c;
    private List<Materiel> materielList;
    private List<Materiel> materielsCorrespondants;
    private Spinner spinner;
    private Button addMaterielButton;
    private ExpandableListView listView;
    private List<String> categories = new ArrayList<>();
    private static final String ALLCAT = "Tous";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_materiels);
        initializeView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initializeView(){
        Bundle extras = getIntent().getExtras();
        int idClient = extras.getInt("idClient");
        this.c = JsonApiPersistence.getInfosClient(idClient);

        listView = findViewById(R.id.listViewMateriels);
        materielList = c.getMateriels();
        addMaterielButton =findViewById(R.id.addMateriel);

        categories.add(ALLCAT);
        spinner = TypeMaterielSpinner.initTypeMaterielSpinner(this,(Spinner) findViewById(R.id.mySpinner), categories);

        //Barre de sélection de catégorie
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                if (position >= 0 && position < categories.size()) {
                    String categorieSelected = categories.get(position);
                    //On affiche les matériels correspondants à la catégories
                    getSelectedCategoryData(categorieSelected, listView, c);
                } else {
                    Toast.makeText(ListeMateriels.this, "@string/erreurCategorie", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //On click Listeners
        addMaterielButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO A FAIRE QUAND ON VOUDRA MODIFIER OU SUPPRIMER UN CONTACT
                //Materiel materiel = materielList.get(position);
                Intent intent = new Intent(ListeMateriels.this, FormAddMateriel.class);
                intent.putExtra("idClient", c.getId());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                Materiel m = materielsCorrespondants.get(pos);
                Intent intent = new Intent(ListeMateriels.this, FormAddMateriel.class);
                intent.putExtra("idClient", c.getId());
                intent.putExtra("idMateriel", m.getId());
                startActivity(intent);
                return true;
            }
        });
    }

    private void getSelectedCategoryData(String typeCat, ExpandableListView listView, Client c) {
        this.materielsCorrespondants = new ArrayList<>();
        //On ne va envoyer que les matériels qui sont de la bonne catégorie.
        for(int i=0;i<c.getMateriels().size();i++){
            if(c.getMateriels().get(i).getType().getLibelle().equals(typeCat) || typeCat.equals(ALLCAT)){
                this.materielsCorrespondants.add(c.getMateriels().get(i));
            }
        }
        listView.setAdapter(new MaterielAdapter(this, materielsCorrespondants));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ListeMateriels.this, FicheClient.class);
        intent.putExtra("idClient", c.getId());
        startActivity(intent);
    }
}
