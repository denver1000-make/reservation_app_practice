package com.denprog.reservationsystem;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.reservationsystem.ui.dialog.SearchQueryAndRange;

public class ReservationActivityViewModel extends ViewModel {
    public MutableLiveData<Float> totalMutableLiveData = new MutableLiveData<>(0f);
    public MutableLiveData<Boolean> showPrevBtn = new MutableLiveData<>(false);
    public MutableLiveData<SearchQueryAndRange> restaurantSearchQuery = new MutableLiveData<>(new SearchQueryAndRange());
    public MutableLiveData<String> cuisineSearchQuery = new MutableLiveData<>("");
}
