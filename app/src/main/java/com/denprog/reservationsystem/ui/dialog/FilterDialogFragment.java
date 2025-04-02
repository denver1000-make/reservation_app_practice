package com.denprog.reservationsystem.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.reservationsystem.R;
import com.denprog.reservationsystem.databinding.FragmentFilterDialogBinding;
import com.google.android.material.slider.Slider;

public class FilterDialogFragment extends DialogFragment {

    FragmentFilterDialogBinding binding;
    public static final String RESULT_KEY = "FilterDialogResult";
    public static final String START_PRICE_BUNDLE_KEY = "start_price";
    public static final String END_PRICE_BUNDLE_KEY = "end_price";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
        binding = FragmentFilterDialogBinding.inflate(getLayoutInflater());
        alertDialogBuilder.setView(binding.getRoot());
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle bundle = new Bundle();
                bundle.putInt(START_PRICE_BUNDLE_KEY, (int) binding.startPriceSlider.getValue());
                bundle.putInt(END_PRICE_BUNDLE_KEY, (int) binding.endPriceSlider.getValue());
                getParentFragmentManager().setFragmentResult(RESULT_KEY, bundle);
                dismiss();
            }
        });
        binding.startPriceSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                float endValue = binding.endPriceSlider.getValue();
                if (endValue != 0) {
                    if (value + 200 > endValue) {
                        slider.setValue(value - 200);
                    } else {
                        slider.setValue(value);
                    }
                } else {
                    slider.setValue(0);
                }
            }
        });

        binding.endPriceSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                float startValue = binding.startPriceSlider.getValue();
                if (startValue != 0) {
                    if (value - 200 < startValue) {
                        slider.setValue(value + 200);
                    } else {
                        slider.setValue(value);
                    }
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return alertDialogBuilder.create();
    }
}