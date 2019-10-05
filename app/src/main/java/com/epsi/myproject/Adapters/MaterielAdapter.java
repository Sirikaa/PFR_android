package com.epsi.myproject.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.epsi.myproject.Interface;
import com.epsi.myproject.Materiel;
import com.epsi.myproject.R;
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
        return materiels.get(i).getInterfaces().size();
    }

    @Override
    public Object getGroup(int i) {
        return materiels.get(i).getLibelle();
    }

    @Override
    public Object getChild(int i, int i1) {
        return materiels.get(i).getInterfaces().get(i1);
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

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(materiels.get(i).getInterfaces().size() >0){
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
        if(materiels.get(i).getInterfaces().size() >0){
            Interface itf = (Interface) getChild(i,i1);
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.materiel_adapter_expandable, null);
            }
            TextView libelleItfc = view.findViewById(R.id.libelleItfc);
            TextView typeItfc = view.findViewById(R.id.typeItfc);
            TextView ipV4 = view.findViewById(R.id.ipV4);
            TextView ipV6 = view.findViewById(R.id.ipV6);
            TextView typeAffectation = view.findViewById(R.id.typeAffectation);

            libelleItfc.setText(itf.getNom());
            typeItfc.setText(itf.getType().getLibelle());
            ipV4.setText(itf.getAdressesIp().get(0).getIpv4());

            if(itf.getAdressesIp().get(0).getIpv6() != "null"){
                ipV6.setText(itf.getAdressesIp().get(0).getIpv6());
            }else{
                //ipV6.setVisibility(View.GONE);
            }
            typeAffectation.setText(itf.getType().getLibelle());
        }
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
