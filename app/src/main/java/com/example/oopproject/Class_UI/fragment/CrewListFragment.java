package com.example.oopproject.Class_UI.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oopproject.R;
import com.example.oopproject.core.GameManager;
import com.example.oopproject.playerJob.CrewLocation;
import com.example.oopproject.playerJob.CrewMember;
import com.example.oopproject.Class_UI.adapter.SelectableCrewAdapter;

import java.util.List;

public class CrewListFragment extends Fragment {

    private CrewLocation filterLocation;
    private RecyclerView recyclerView;
    private SelectableCrewAdapter adapter;
    private TextView textTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterLocation = (CrewLocation) getArguments().getSerializable("location");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crew_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_crew_fragment);
        textTitle = view.findViewById(R.id.text_fragment_title);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        if (filterLocation != null) {
            textTitle.setText("Location: " + filterLocation.name());
        }

        refreshList();
        return view;
    }

    public void setLocation(CrewLocation location) {
        this.filterLocation = location;
        if (textTitle != null) {
            textTitle.setText("Location: " + filterLocation.name());
        }
        refreshList();
    }

    public void refreshList() {
        if (recyclerView == null) return;
        
        List<CrewMember> members = GameManager.getInstance().getCrewAt(filterLocation);
        adapter = new SelectableCrewAdapter(members);
        recyclerView.setAdapter(adapter);
    }

    public List<CrewMember> getSelectedMembers() {
        return adapter != null ? adapter.getSelectedMembers() : null;
    }
}