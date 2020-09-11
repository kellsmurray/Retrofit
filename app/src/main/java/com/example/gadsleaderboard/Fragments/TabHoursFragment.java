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

import com.example.gadsleaderboard.Adapter.HoursAdapter;
import com.example.gadsleaderboard.Api.HoursApi;
import com.example.gadsleaderboard.Models.Hours;
import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.Retrofit.Retrofits;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabHoursFragment extends Fragment {

    public TabHoursFragment() {
    }

    private RecyclerView recyclerView;
    private HoursAdapter hoursAdapter;
    List<Hours> list;
    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hours, container, false);

        list = new ArrayList<>();
        getList();

        return view;
    }

    private void getList() {
        HoursApi hours = Retrofits.getRetrofitInstance().create(HoursApi.class);
        Call<List<Hours>> call = hours.getHours();
        call.enqueue(new Callback<List<Hours>>() {
            @Override
            public void onResponse(Call<List<Hours>> call, Response<List<Hours>> response) {
                list = response.body();
                initView(list);
            }
            @Override
            public void onFailure(Call<List<Hours>> call, Throwable throwable) {
                Toast.makeText(getActivity(), "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(List<Hours> hrsList) {
        recyclerView = view.findViewById(R.id.recyclerviewhrs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hoursAdapter = new HoursAdapter(getActivity(), hrsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(hoursAdapter);
        hoursAdapter.notifyDataSetChanged();
    }
}
