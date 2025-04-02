package com.denprog.reservationsystem.ui.reservation_info;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationInfoViewModel extends ViewModel {
    public MutableLiveData<LocalTime> startTimeWrapperMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<LocalTime> endTimeWrapperMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<LocalDate> localDateMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<ReservationInfoFormState> reservationInfoFormStateMutableLiveData = new MutableLiveData<>();
    public static class TimeWrapper {
        public int hour;
        public int minutes;

        public TimeWrapper(int hour, int minutes) {
            this.hour = hour;
            this.minutes = minutes;
        }
    }
}