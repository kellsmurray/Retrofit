package com.example.gadsleaderboard.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gadsleaderboard.Adapter.SkillsAdapter;
import com.example.gadsleaderboard.Api.HoursApi;
import com.example.gadsleaderboard.Models.Skills;
import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.Retrofit.Retrofits;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabSkillsFragment extends Fragment {

    public TabSkillsFragment() {}

    private RecyclerView recyclerView;
    private SkillsAdapter skillsAdapter;
    private View view;
    List<Skills> list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_skills, container, false);

        list = new ArrayList<>();
        getList();

        return view;
    }

    private void getList() {
        HoursApi learnerskills = Retrofits.getRetrofitInstance().create(HoursApi.class);
        Call<List<Skills>> call = learnerskills.getSkills();
        call.enqueue(new Callback<List<Skills>>() {
            @Override
            public void onResponse(Call<List<Skills>> call, Response<List<Skills>> response) {
                list = response.body();
                initView(list);

            }

            @Override
            public void onFailure(Call<List<Skills>> call, Throwable throwable) {
                Toast.makeText(getActivity(), "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(List<Skills> skillsList) {
        recyclerView = view.findViewById(R.id.recyclerviewskills);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        skillsAdapter = new SkillsAdapter(getActivity(), skillsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(skillsAdapter);
        skillsAdapter.notifyDataSetChanged();
    }
}