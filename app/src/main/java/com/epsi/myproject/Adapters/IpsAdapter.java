package com.epsi.myproject.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.epsi.myproject.AdresseIp;
import com.epsi.myproject.R;
import java.util.List;

public class IpsAdapter extends BaseExpandableListAdapter {
    protected Context _context;
    List<AdresseIp> ipsList;

    public IpsAdapter(Context context, List<AdresseIp> ipsList){
        this._context = context;
        this.ipsList = ipsList;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        return ipsList.size();
    }

    @Override
    public String getGroup(int i) {
        return "IP";
    }

    @Override
    public Object getChild(int i, int i1) {
        return ipsList.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return ipsList.get(i).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adresses_ip_listview_adapter, null);
        }
        TextView libelleMateriel = view.findViewById(R.id.libelle);
        libelleMateriel.setTypeface(null, Typeface.BOLD);
        libelleMateriel.setText("Adresses IP");
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(ipsList.size()>0) {
            AdresseIp ip = ipsList.get(i);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.materiel_adapter_ipsview_layout, null);
            }
            TextView ipV4 = view.findViewById(R.id.ipV4);
            TextView ipV6 = view.findViewById(R.id.ipV6);
            TextView masque = view.findViewById(R.id.masque);
            TextView typeAffectation = view.findViewById(R.id.typeAffectation);

            ipV4.setText(ip.getIpv4());
            ipV6.setText(ip.getIpv6());
            masque.setText(ip.getMasque());
            typeAffectation.setText(ip.getTypeAffectation().getLibelle());
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
