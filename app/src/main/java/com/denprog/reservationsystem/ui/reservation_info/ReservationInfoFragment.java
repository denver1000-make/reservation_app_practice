package com.denprog.reservationsystem.ui.reservation_info;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.denprog.reservationsystem.ReservationActivityViewModel;
import com.denprog.reservationsystem.databinding.FragmentReservationInfoBinding;
import com.denprog.reservationsystem.room.entities.ReservationInfo;
import com.denprog.reservationsystem.ui.register.RegisterFormState;
import com.denprog.reservationsystem.util.SimpleOperationCallback;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReservationInfoFragment extends Fragment {
    private ReservationInfoViewModel mViewModel;
    private FragmentReservationInfoBinding binding;
    private ReservationActivityViewModel viewModel;
    private TimePickerDialog startTime;
    private TimePickerDialog endTime;
    private DatePickerDialog datePickerDialog;
    private ProgressDialog progressDialog;

    public static ReservationInfoFragment newInstance() {
        return new ReservationInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReservationInfoBinding.inflate(inflater, container, false);
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        startTime = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                LocalTime localTime = LocalTime.of(i, i1);
                if (localTime.isAfter(LocalTime.of(6, 30))) {
                    mViewModel.startTimeWrapperMutableLiveData.setValue(localTime);
                } else {
                    showError("The start time of your reservation should be after 6:30am", dialogInterface -> {
                        startTime.updateTime(6, 30);
                        startTime.show();
                    });
                }
            }
        }, 6, 30, false);

        endTime = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                LocalTime localTime = LocalTime.of(i, i1);
                if (mViewModel.startTimeWrapperMutableLiveData.getValue() == null) {
                    showError("Please pick a start time first.", null);
                } else {
                    Duration duration = Duration.between(mViewModel.startTimeWrapperMutableLiveData.getValue(), localTime);
                    if (duration.toMinutes() > 30) {
                        if (localTime.isBefore(LocalTime.of(17, 30))) {
                            mViewModel.endTimeWrapperMutableLiveData.setValue(localTime);
                        } else {
                            showError("Reservations should not be after 5:30pm", null);
                        }
                    } else {
                        showError("Reservation Should at least be 30 minutes", null);
                    }
                }
            }
        }, 17, 30, false);

        LocalDate localDate = LocalDate.now();
        datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                LocalDate selectedDate = LocalDate.of(i, i1, i2);
                if (selectedDate.isAfter(LocalDate.now())) {
                    mViewModel.localDateMutableLiveData.setValue(LocalDate.of(i, i1, i2));
                } else {
                    showError("Invalid Date", dialogInterface -> {
                        datePickerDialog.updateDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
                        datePickerDialog.show();
                    });
                }
            }
        }, localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());

        binding.pickStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!startTime.isShowing()) {
                    startTime.show();
                }
            }
        });

        binding.pickEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!endTime.isShowing()) {
                    endTime.show();
                }
            }
        });

        binding.pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!datePickerDialog.isShowing()) {
                    datePickerDialog.show();
                }
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.firstNameField.getText().toString();
                String middleName = binding.middleNameField.getText().toString();
                String lastName = binding.lastNameField.getText().toString();
                String numOfGuests = binding.numOfAttendingDinersField.getText().toString();
                LocalTime selectedStartTime = mViewModel.startTimeWrapperMutableLiveData.getValue();
                LocalTime selectedEndTime = mViewModel.endTimeWrapperMutableLiveData.getValue();
                LocalDate selectedDate = mViewModel.localDateMutableLiveData.getValue();
                ReservationInfoFormState formState = mViewModel.onDataChanged(
                        firstName,
                        middleName,
                        lastName,
                        numOfGuests,
                        mViewModel.startTimeWrapperMutableLiveData.getValue(),
                        mViewModel.endTimeWrapperMutableLiveData.getValue(),
                        mViewModel.localDateMutableLiveData.getValue());
                binding.firstNameField.setError(formState.usernameError);
                binding.middleNameField.setError(formState.middleNameError);
                binding.lastNameField.setError(formState.lastNameError);
                binding.numOfAttendingDinersField.setError(formState.numOfAttendingDinersError);

                if (formState.timeError != null) {
                    showError(formState.timeError, null);
                }

                if (formState.dateError != null) {
                    showError(formState.dateError, null);
                }

                if (formState.isDataValid) {
                    ReservationInfoFragmentArgs infoFragmentArgs = ReservationInfoFragmentArgs.fromBundle(getArguments());
                    mViewModel.saveReservationInfo(new ReservationInfo(
                            firstName,
                            middleName,
                            lastName,
                            infoFragmentArgs.getSelectedRestaurant().restaurantName,
                            infoFragmentArgs.getSelectedRestaurant().restaurantPrice,
                            infoFragmentArgs.getSelectedCuisine().cuisineName,
                            infoFragmentArgs.getSelectedCuisine().cuisinePrice,
                            Integer.parseInt(numOfGuests),
                            formatDate(selectedDate),
                            formatTime(selectedStartTime),
                            formatTime(selectedEndTime),
                            viewModel.totalMutableLiveData.getValue()
                    ), new SimpleOperationCallback<Long>() {
                        @Override
                        public void onLoading() {
                            if (!progressDialog.isShowing()) {
                                progressDialog.show();
                            }
                        }

                        @Override
                        public void onFinished(Long data) {
                            progressDialog.hide();
                            requireActivity().finish();
                        }

                        @Override
                        public void onError(String message) {
                            progressDialog.hide();
                            showError(message, null);
                        }
                    });
                }
            }
        });

        return binding.getRoot();
    }

    private String formatTime(LocalTime localTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return dateTimeFormatter.format(localTime);

    }

    public void showError(String message, DialogInterface.OnDismissListener onDismissListener) {
        new AlertDialog.Builder(requireContext()).setMessage(message).setTitle("Warning").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setOnDismissListener(onDismissListener).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservationInfoViewModel.class);
        this.viewModel = new ViewModelProvider(requireActivity()).get(ReservationActivityViewModel.class);
        ReservationInfoFragmentArgs args = ReservationInfoFragmentArgs.fromBundle(getArguments());
        this.viewModel.totalMutableLiveData.setValue(args.getSelectedRestaurant().restaurantPrice + args.getSelectedCuisine().cuisinePrice);

        mViewModel.startTimeWrapperMutableLiveData.observe(getViewLifecycleOwner(), localTime -> {
            if (localTime == null) return;
            binding.pickStartTime.setText(formatTime(localTime));
        });

        mViewModel.endTimeWrapperMutableLiveData.observe(getViewLifecycleOwner(), localTime -> {
            if (localTime == null) return;
            binding.pickEndTime.setText(formatTime(localTime));
        });

        mViewModel.localDateMutableLiveData.observe(getViewLifecycleOwner(), new Observer<LocalDate>() {
            @Override
            public void onChanged(LocalDate localDate) {
                if (localDate == null) {
                    return;
                }
                binding.pickDate.setText(formatDate(localDate));
            }
        });


    }

    private String formatDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM dd, EEEE");
        return formatter.format(localDate);
    }

}