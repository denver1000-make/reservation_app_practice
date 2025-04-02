package com.denprog.reservationsystem;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.denprog.reservationsystem.databinding.ActivityReservationBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReservationActivity extends AppCompatActivity {
    ReservationActivityViewModel viewModel;
    ActivityReservationBinding binding;
    AlertDialog exitPrompt;

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
        this.exitPrompt =
                new AlertDialog
                        .Builder(this)
                        .setMessage("Warning")
                        .setTitle("Are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create();
        viewModel = new ViewModelProvider(this).get(ReservationActivityViewModel.class);
        viewModel.totalMutableLiveData.observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                binding.totalDisplay.setText("Total: " + aFloat + " Pesos");
            }
        });
        NavController navController = ((NavHostFragment) binding.reservationAcitvityContainer.getFragment()).getNavController();
        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });
        viewModel.showPrevBtn.observe(this, aBoolean -> {
            binding.prevBtn.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            OnBackPressedCallback onBackPressedCallback;
            if (aBoolean) {
                onBackPressedCallback = new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        setEnabled(false);
                        getOnBackPressedDispatcher().onBackPressed();
                    }
                };
            } else {
                onBackPressedCallback = new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        exitPrompt.show();
                    }
                };
            }
            getOnBackPressedDispatcher().addCallback(ReservationActivity.this, onBackPressedCallback);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reservation_interrupt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exitReservationProcess) {
            if (!exitPrompt.isShowing()) {
                exitPrompt.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}