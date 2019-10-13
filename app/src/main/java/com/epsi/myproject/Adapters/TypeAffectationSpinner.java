package com.epsi.myproject.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.epsi.myproject.Persistence.JsonApiPersistence;
import com.epsi.myproject.TypeAffectation;

import java.util.List;

public abstract class TypeAffectationSpinner {

    public static Spinner initSpinner(final Context ctx, Spinner spinner, List<String> categories){
        setCategories(categories);
        spinner.setAdapter(new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_activated_1, categories));
        return spinner;
    }

    private static void setCategories(List<String> categories){
        for (int i = 0; i< JsonApiPersistence.getTypesAffectation().size(); i++){
            TypeAffectation ta = JsonApiPersistence.getTypesAffectation().get(i);
            //On récupère l'id et le libellé à mettre dans le spinner
            categories.add(ta.getLibelle());
        }
    }

    public static List<Integer> getIdsTypes(List<Integer> list){
        for (int i = 0; i< JsonApiPersistence.getTypesAffectation().size(); i++){
            TypeAffectation ta = JsonApiPersistence.getTypesAffectation().get(i);
            //On récupère l'id et le libellé à mettre dans le spinner
            list.add(ta.getId());
        }
        return list;
    }
}
