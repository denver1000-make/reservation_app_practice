package com.denprog.reservationsystem.ui.history;

import androidx.lifecycle.ViewModel;

import com.denprog.reservationsystem.room.AppDatabase;
import com.denprog.reservationsystem.room.dao.AppDao;
import com.denprog.reservationsystem.room.entities.ReservationInfo;
import com.denprog.reservationsystem.util.MainThreadRunner;
import com.denprog.reservationsystem.util.SimpleOperationCallback;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReservationHistoryViewModel extends ViewModel {
    AppDao appDao;
    @Inject
    public ReservationHistoryViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public void getAllReservation(SimpleOperationCallback<List<ReservationInfo>> listSimpleOperationCallback) {
        listSimpleOperationCallback.onLoading();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<ReservationInfo> reservationInfos = appDao.getAllReservationList();
                    MainThreadRunner.runOnMain(new Runnable() {
                        @Override
                        public void run() {

                            listSimpleOperationCallback.onFinished(reservationInfos);
                        }
                    });
                } catch (Exception e) {
                    MainThreadRunner.runOnMain(new Runnable() {
                        @Override
                        public void run() {

                            listSimpleOperationCallback.onError(e.getMessage());
                        }
                    });
                }
            }
        });
    }

}
