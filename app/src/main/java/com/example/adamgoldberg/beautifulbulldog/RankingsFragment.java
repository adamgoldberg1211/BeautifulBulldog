package com.example.adamgoldberg.beautifulbulldog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingsFragment extends Fragment {
    private ListView bulldogList;
    private MainActivity mainActivity;

    public RankingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rankings, container, false);

        bulldogList = (ListView) view.findViewById(R.id.bulldog_list);

        mainActivity = (MainActivity) this.getActivity();

        RankingsAdapter adapter = new RankingsAdapter(this.getActivity(), this.getRankings());
        bulldogList.setAdapter(adapter);

        bulldogList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                final Bulldog bulldog = (Bulldog) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), BulldogActivity.class);
                intent.putExtra("bulldog", bulldog.getId());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        RankingsAdapter adapter = new RankingsAdapter(this.getActivity(), this.getRankings());
        bulldogList.setAdapter(adapter);
    }

    public ArrayList<Bulldog> getRankings() {
        ArrayList<Bulldog> bulldogs = new ArrayList(mainActivity.realm.where(Bulldog.class).findAll());
        Collections.sort(bulldogs, new Comparator<Bulldog>(){
            @Override
            public int compare(Bulldog bulldog, Bulldog bulldog2){
                return ((Double) bulldog2.getVotes().average("rating")).compareTo((Double) bulldog.getVotes().average("rating"));
            }
        });

        return bulldogs;
    }
}
