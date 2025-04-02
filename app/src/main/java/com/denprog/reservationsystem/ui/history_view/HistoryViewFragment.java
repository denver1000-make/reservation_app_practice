package com.denprog.reservationsystem.ui.history_view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.reservationsystem.R;
import com.denprog.reservationsystem.databinding.FragmentHistoryViewBinding;

public class HistoryViewFragment extends Fragment {
    private FragmentHistoryViewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}