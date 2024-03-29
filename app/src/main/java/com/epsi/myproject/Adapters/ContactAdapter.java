package com.epsi.myproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.epsi.myproject.Personne;
import com.epsi.myproject.R;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    protected Context _context;
    List<Personne> contactsList;

    public ContactAdapter(Context context, List<Personne> cList){
        _context = context;
        contactsList = cList;
    }

    @Override
    public int getCount() {
        return contactsList.size();
    }
    @Override
    public Object getItem(int arg0) {
        return contactsList.get(arg0);
    }
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if (arg1 == null) {

            LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = mInflater.inflate(R.layout.contact_adapter, null);
        }

        TextView nomContact = (TextView) arg1.findViewById(R.id.nomContact);
        TextView telContact = (TextView) arg1.findViewById(R.id.telContact);
        TextView emailContact = (TextView) arg1.findViewById(R.id.emailContact);
        //TextView fonctionContact = (TextView) arg1.findViewById(R.id.fonctionContact);

        Personne contact = contactsList.get(arg0);

        nomContact.setText(contact.getNom()+" "+contact.getPrenom());
        if(contact.getTelephone() != "" && contact.getTelephone() != null){
            telContact.setText("Tél: " + contact.getTelephone());

        }else{
            telContact.setText("Tél: ");
        }
        if(contact.getEmail() == null){
            emailContact.setText("E-mail: ");
        }else{
            emailContact.setText("E-mail: "+contact.getEmail());
        }
        //fonctionContact.setText("Fonction: " + contact.getFonction());

        return arg1;
    }

}
