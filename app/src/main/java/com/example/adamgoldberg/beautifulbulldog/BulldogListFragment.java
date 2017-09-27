package com.example.adamgoldberg.beautifulbulldog;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class BulldogListFragment extends Fragment {
    private ListView bulldogList;
    private MainActivity mainActivity;

    public BulldogListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bulldog_list, container, false);

        bulldogList = (ListView) view.findViewById(R.id.bulldog_list);

        mainActivity = (MainActivity) this.getActivity();

        BulldogArrayAdapter adapter = new BulldogArrayAdapter(this.getActivity(), this.getAvailableBulldogs());
        bulldogList.setAdapter(adapter);

        bulldogList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                final Bulldog bulldog = (Bulldog) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), BulldogActivity.class);
                intent.putExtra("bulldog", bulldog.getId());
                intent.putExtra("username", mainActivity.user.getUsername());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        BulldogArrayAdapter adapter = new BulldogArrayAdapter(this.getActivity(), this.getAvailableBulldogs());
        bulldogList.setAdapter(adapter);
    }

    public ArrayList<Bulldog> getAvailableBulldogs() {
        ArrayList<Bulldog> bulldogs2 = new ArrayList<Bulldog>();

        RealmResults<Bulldog> bulldogs = mainActivity.realm.where(Bulldog.class).findAll();
        for (Bulldog bulldog:bulldogs){
            Boolean isPresent = false;
            for(Vote vote : bulldog.getVotes()){
                if(vote.getOwner().getUsername().equals(mainActivity.user.getUsername())){
                    isPresent = true;
                }
            }
            if(!isPresent){
                bulldogs2.add(bulldog);
            }
        }

        return bulldogs2;
    }



}
