package com.denprog.reservationsystem;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReservationActivityViewModel extends ViewModel {
    public MutableLiveData<Float> totalMutableLiveData = new MutableLiveData<>(0f);


}
