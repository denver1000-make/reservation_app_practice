package com.denprog.reservationsystem.ui.reservation_info;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.reservationsystem.room.AppDatabase;
import com.denprog.reservationsystem.room.dao.AppDao;
import com.denprog.reservationsystem.room.entities.ReservationInfo;
import com.denprog.reservationsystem.util.MainThreadRunner;
import com.denprog.reservationsystem.util.SimpleOperationCallback;
import com.denprog.reservationsystem.util.Validator;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReservationInfoViewModel extends ViewModel {
    public MutableLiveData<LocalTime> startTimeWrapperMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<LocalTime> endTimeWrapperMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<LocalDate> localDateMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<ReservationInfoFormState> reservationInfoFormStateMutableLiveData = new MutableLiveData<>();

    AppDao appDao;
    @Inject
    public ReservationInfoViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public void saveReservationInfo(ReservationInfo reservationInfo, SimpleOperationCallback<Long> onReservationInfoSaved) {
        onReservationInfoSaved.onLoading();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    long id = appDao.insertReservationInfo(reservationInfo);
                    MainThreadRunner.runOnMain(new Runnable() {
                        @Override
                        public void run() {
                            onReservationInfoSaved.onFinished(id);
                        }
                    });
                } catch (Exception e) {
                    MainThreadRunner.runOnMain(new Runnable() {
                        @Override
                        public void run() {
                            onReservationInfoSaved.onError(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    public static class TimeWrapper {
        public int hour;
        public int minutes;

        public TimeWrapper(int hour, int minutes) {
            this.hour = hour;
            this.minutes = minutes;
        }
    }
    public ReservationInfoFormState onDataChanged(String firstName, String middleName, String lastName, String numberOfAttendingDiners, LocalTime startTime, LocalTime endTime, LocalDate localDate) {
        ReservationInfoFormState formState;
        String firstNameError = Validator.validateName(firstName);
        String middleNameError = Validator.validateName(middleName);
        String lastNameError = Validator.validateName(lastName);
        String numOfGuestsError = Validator.numOfGuestsValidator(numberOfAttendingDiners);
        String startTimeError = startTime == null || endTime == null ? "Please select reservation times" : null;
        String dateError = localDate == null ? "Please select a reservation date" : null;
        formState = new ReservationInfoFormState(
                firstNameError,
                lastNameError,
                middleNameError,
                startTimeError,
                dateError,
                numOfGuestsError,
                isDataValid(firstNameError, middleNameError, lastNameError, numOfGuestsError, startTimeError, dateError));
        return formState;
    }

    private boolean isDataValid(String ...errors) {
        for (String error : errors) {
            if (error != null) {
                return false;
            }
        }
        return true;
    }
}