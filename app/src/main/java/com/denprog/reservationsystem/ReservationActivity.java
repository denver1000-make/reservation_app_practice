package com.denprog.reservationsystem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.denprog.reservationsystem.databinding.ActivityReservationBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReservationActivity extends AppCompatActivity {
    ReservationActivityViewModel viewModel;
    ActivityReservationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityReservationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(ReservationActivityViewModel.class);
        viewModel.totalMutableLiveData.observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                binding.totalDisplay.setText("Total: " + aFloat + " Pesos");
            }
        });
    }
}