package com.epsi.myproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.epsi.myproject.Persistence.JsonApiPersistence;
import com.epsi.myproject.TypeInterface;
import java.util.List;

public abstract class TypeInterfaceSpinner {

    public static Spinner initSpinner(final Context ctx, Spinner spinner, List<String> categories){
        setCategories(categories);
        spinner.setAdapter(new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_activated_1, categories));
        return spinner;
    }

    private static void setCategories(List<String> categories){
        for (int i = 0; i< JsonApiPersistence.getTypesInterface().size(); i++){
            TypeInterface ti = JsonApiPersistence.getTypesInterface().get(i);
            categories.add(ti.getLibelle());
        }
    }

    public static List<Integer> getIdsTypes(List<Integer> list){
        for (int i = 0; i< JsonApiPersistence.getTypesInterface().size(); i++){
            TypeInterface ti = JsonApiPersistence.getTypesInterface().get(i);
            list.add(ti.getId());
        }
        return list;
    }
}
