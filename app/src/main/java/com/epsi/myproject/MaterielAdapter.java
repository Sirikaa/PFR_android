package com.epsi.myproject;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

public class MaterielAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Materiel> materiels;

    public MaterielAdapter(Context context, List<Materiel> m) {
        this.context = context;
        this.materiels = m;
    }

    @Override
    public int getGroupCount() {
        return materiels.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return materiels.get(i).getListe_interfaces().size();
    }

    @Override
    public Object getGroup(int i) {
        return materiels.get(i).getLibelle();
    }

    @Override
    public Object getChild(int i, int i1) {
        return materiels.get(i).getListe_interfaces().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        //return Integer.parseInt(c.getListe_materiels().get(i).getId());
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        //return (long) Integer.parseInt(c.getListe_materiels().get(i).getListe_interfaces().get(i1).getId());
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    public String getTypeMateriel(int i){
        return materiels.get(i).getTypeMateriel();
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Log.d("valeur", "nb interfaces : "+materiels.get(i).getListe_interfaces().size());
        if(materiels.get(i).getListe_interfaces().size() >0){
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.materiel_adapter, null);
            }
            Materiel m = materiels.get(i);
            String s = m.getLibelle();

            TextView libelleMateriel = view.findViewById(R.id.libelleMateriel);
            libelleMateriel.setTypeface(null, Typeface.BOLD);
            libelleMateriel.setText(s);
            return view;
        }else{
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.materiel_adapter_no_interface, null);
            }
            Materiel m = materiels.get(i);
            String s = m.getLibelle();

            TextView libelleMateriel = view.findViewById(R.id.libelleMateriel);
            libelleMateriel.setTypeface(null, Typeface.BOLD);
            libelleMateriel.setText(s);
            return view;
        }

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Interface infoM = (Interface) getChild(i,i1);
        Log.d("valeur", "nom Interface : "+infoM.getNom());
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.materiel_adapter_expandable, null);
        }
        TextView libelleItfc = (TextView) view.findViewById(R.id.libelleItfc);
        TextView typeItfc = (TextView) view.findViewById(R.id.typeItfc);
        TextView ipV4 = (TextView) view.findViewById(R.id.ipV4);
        TextView ipV6 = (TextView) view.findViewById(R.id.ipV6);
        TextView typeAffectation = (TextView) view.findViewById(R.id.typeAffectation);

        libelleItfc.setText(infoM.getNom());
        typeItfc.setText(infoM.getType());
        ipV4.setText(infoM.getAdresseIpv4());
        if(infoM.getAdresseIpv6() != "null"){
            ipV6.setText(infoM.getAdresseIpv6());
        }else{
            ipV6.setVisibility(View.GONE);
        }
        typeAffectation.setText(infoM.getTypeAffectation());
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
