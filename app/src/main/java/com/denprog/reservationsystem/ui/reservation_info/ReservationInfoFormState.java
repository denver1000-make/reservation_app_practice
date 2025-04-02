package com.denprog.reservationsystem.ui.reservation_info;

public class ReservationInfoFormState {
    public String usernameError;
    public String lastNameError;
    public String middleNameError;
    public String timeError;
    public String dateError;
    public String numOfAttendingDinersError;
    public Boolean isDataValid;
    public ReservationInfoFormState(String usernameError, String lastNameError, String middleNameError, String timeError, String dateError, String numOfAttendingDinersError, Boolean isDataValid) {
        this.usernameError = usernameError;
        this.lastNameError = lastNameError;
        this.middleNameError = middleNameError;
        this.timeError = timeError;
        this.dateError = dateError;
        this.numOfAttendingDinersError = numOfAttendingDinersError;
        this.isDataValid = isDataValid;
    }
}
