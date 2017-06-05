package com.delaroystudios.sqlitelogin.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.delaroystudios.sqlitelogin.R;
import com.delaroystudios.sqlitelogin.bean.User;
import com.delaroystudios.sqlitelogin.dao.UserDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by intel on 4/06/2017.
 */

public class Tab01Users extends android.support.v4.app.Fragment {


    public static List<UserDAO> listUsers = new ArrayList<>();
    public int usuarioId = 0;

    public static int layout = android.R.layout.simple_list_item_1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab01_users, container, false);
        UserDAO userDAO = new UserDAO(getActivity());
        listUsers = userDAO.listaUser();
        ListView listUser = (ListView) view.findViewById(R.id.listUser);
        ArrayAdapter<UserDAO> arrayAdapter = new ArrayAdapter<>(getActivity(), layout, listUsers);
        listUser.setAdapter(arrayAdapter);

        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User users = (User) parent.getItemAtPosition(position);
                usuarioId = users.getId();
                Toast.makeText(getActivity(), users.getId() + users.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.flotEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), RegisterActivity.class);
                i.putExtra("usuarioId", usuarioId);
                startActivity(i);
            }
        });
        /*FloatingActionButton floDelt = (FloatingActionButton) view.findViewById(R.id.floDelt);
        floDelt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deltePerson();
                Intent e=new Intent(getActivity(),Tab01Users.class);
                startActivity(e);
            }
        });*/

        return view;
    }



    /*public void deltePerson(){

        List<User> lista2 = new ArrayList<User>();
        for (User user:Tab01Users.listUsers){
            if (user.getId()!=usuarioId){

                lista2.add(user);

            }
        }

        Tab01Users.listUsers=lista2;

    }*/
}
