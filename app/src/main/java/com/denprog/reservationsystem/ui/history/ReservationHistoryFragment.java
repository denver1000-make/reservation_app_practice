package com.denprog.reservationsystem.ui.history;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.reservationsystem.R;
import com.denprog.reservationsystem.databinding.FragmentReservationListBinding;
import com.denprog.reservationsystem.room.entities.ReservationInfo;
import com.denprog.reservationsystem.ui.history.placeholder.PlaceholderContent;
import com.denprog.reservationsystem.util.SimpleClickCallback;
import com.denprog.reservationsystem.util.SimpleOperationCallback;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReservationHistoryFragment extends Fragment {
    private FragmentReservationListBinding binding;
    private MyReservationHistoryRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentReservationListBinding.inflate(inflater, container, false);
        this.adapter = new MyReservationHistoryRecyclerViewAdapter(data -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(ReservationHistoryFragmentDirections.actionNavHistoryToHistoryViewFragment(data));
        });
        this.binding.list.setAdapter(adapter);
        this.binding.list.setLayoutManager(new LinearLayoutManager(requireActivity()));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ReservationHistoryViewModel viewModel = new ViewModelProvider(this).get(ReservationHistoryViewModel.class);
        viewModel.getAllReservation(new SimpleOperationCallback<List<ReservationInfo>>() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onFinished(List<ReservationInfo> data) {
                adapter.refreshList(data);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}