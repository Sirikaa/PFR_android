package com.epsi.myproject.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.epsi.myproject.Client;
import com.epsi.myproject.Materiel;
import com.epsi.myproject.Adapters.MaterielAdapter;
import com.epsi.myproject.R;

import java.util.ArrayList;
import java.util.List;


public class ListeMateriels extends AppCompatActivity {
    /*private Client c;
    private List<Materiel> materielList;
    private ExpandableListView listView;
    private MaterielAdapter adapter;
    private Spinner spinner;
    ArrayList<String> categories = new ArrayList<>();
    String categorie;
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
    private void setCategories(){
        ArrayList<Materiel> listeCat = (ArrayList) c.getListe_materiels();
        boolean exists = true;
        categories.add(ALLCAT);
        for(int i=0;i<listeCat.size();i++){
            for(int j=0;j<categories.size();j++){
                if(listeCat.get(i).getTypeMateriel().equals(categories.get(j))){
                    exists = true;
                }else{
                    exists = false;
                }
            }
           if(!exists){
               categories.add(listeCat.get(i).getTypeMateriel());
           }
        }
    }
    private ArrayList<String> getCategories(){
        return this.categories;
    }
    private void initializeView(){
        Bundle extras = getIntent().getExtras();
        c = (Client) extras.getSerializable("objClient");
        materielList = c.getListe_materiels();
        Log.d("valeur", "Client : "+c.getNom());
        setCategories();
        Log.d("valeur", "Catégories : "+categories.toString());

        spinner = findViewById(R.id.mySpinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, categories));
        listView = findViewById(R.id.listViewMateriels);
        //On va initialiser la vue sur TOUS - ALLCAT -  les matériels
        categorie = getCategories().get(0);
        adapter = new MaterielAdapter(this, materielList);
        listView.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                if (position >= 0 && position < categories.size()) {
                    String cat = getCategories().get(position);
                    Log.d("valeur", "Catégorie en cours : "+cat);
                    getSelectedCategoryData(cat, categorie);
                } else {
                    Toast.makeText(ListeMateriels.this, "@string/erreurCategorie", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO A FAIRE QUAND ON VOUDRA MODIFIER OU SUPPRIMER UN CONTACT
                Materiel materiel = materiels.get(position);
                //Intent intent = new Intent(ListeContacts.this, .class);
                //intent.putExtra("", );
                //startActivity(intent);
            }
        });*/
        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                Materiel materiel = materielList.get(pos);
                Log.d("valeur", "Matériel envoyé : "+materiel.getLibelle());
                Toast.makeText(ListeMateriels.this, "Matériel cliqué : "+materiel.getLibelle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void getSelectedCategoryData(String typeCat, String defaultCat) {
        ArrayList<Materiel> materielsCorrespondants = new ArrayList<>();
        //On ne va envoyer que les matériels qui sont de la bonne catégorie.
        for(int i=0;i<c.getListe_materiels().size();i++){
            if(c.getListe_materiels().get(i).getTypeMateriel().equals(typeCat) || typeCat.equals(ALLCAT)){
                materielsCorrespondants.add(c.getListe_materiels().get(i));
            }
        }
        adapter = new MaterielAdapter(this, materielsCorrespondants);
        listView.setAdapter(adapter);
    }
    /*@Override
    protected void onPause(){
        // do something urgent
        super.onPause();
    }*/

}
