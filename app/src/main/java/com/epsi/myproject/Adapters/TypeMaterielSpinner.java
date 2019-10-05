package com.epsi.myproject.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.epsi.myproject.Persistence.JsonApiPersistence;
import com.epsi.myproject.TypeMateriel;
import java.util.List;

public abstract class TypeMaterielSpinner{

    public static Spinner initTypeMaterielSpinner(final Context ctx, Spinner spinner, List<String> categories){
        setCategories(categories);
        spinner.setAdapter(new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_activated_1, categories));
        return spinner;
    }

    private static void setCategories(List<String> categories){
        for (int i = 0; i< JsonApiPersistence.getTypesMateriel().size(); i++){
            TypeMateriel tm = JsonApiPersistence.getTypesMateriel().get(i);
            //On récupère l'id et le libellé à mettre dans le spinner
            categories.add(tm.getLibelle());
        }
    }

    public static List<Integer> getIdsTypeMateriel(List<Integer> list){
        for (int i = 0; i< JsonApiPersistence.getTypesMateriel().size(); i++){
            TypeMateriel tm = JsonApiPersistence.getTypesMateriel().get(i);
            //On récupère l'id et le libellé à mettre dans le spinner
            list.add(tm.getId());
        }
        return list;
    }
}
