package com.epsi.myproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.epsi.myproject.Client;
import com.epsi.myproject.R;

import java.util.List;


public class ClientAdapter extends BaseAdapter {
    protected Context _context;
    List<Client> clientList;

    public ClientAdapter(Context context, List<Client> cList){
        _context = context;
        clientList = cList;
    }


    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return clientList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        if (arg1 == null) {

            LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = mInflater.inflate(R.layout.client_adapter, null);
        }

        TextView nomEntreprise = (TextView) arg1.findViewById(R.id.nomEntreprise);
        TextView nombreContacts = (TextView) arg1.findViewById(R.id.nombreContacts);
        TextView nombreMateriels = (TextView) arg1.findViewById(R.id.nombreMateriels);

        Client client = clientList.get(arg0);


        nomEntreprise.setText(client.getNom());
        nombreContacts.setText(String.valueOf(client.getContacts().size()));
        nombreMateriels.setText(String.valueOf(client.getMateriels().size()));
        return arg1;
    }
}
