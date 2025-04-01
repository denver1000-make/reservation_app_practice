package com.denprog.reservationsystem.ui.register;

public class RegisterFormState {
    public String firstNameError = null;
    public String middleNameError = null;
    public String lastNameError = null;
    public String emailError = null;
    public String usernameError = null;
    public String passwordLengthError = null;
    public String passwordContentError = null;
    public String confirmPasswordError = null;
    public Boolean isDataValid = false;

    public RegisterFormState(String firstNameError, String middleNameError, String lastNameError, String emailError, String usernameError, String passwordLengthError, String passwordContentError, String confirmPasswordError, Boolean isDataValid) {
        this.firstNameError = firstNameError;
        this.middleNameError = middleNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.usernameError = usernameError;
        this.passwordLengthError = passwordLengthError;
        this.passwordContentError = passwordContentError;
        this.confirmPasswordError = confirmPasswordError;
        this.isDataValid = isDataValid;
    }
}
