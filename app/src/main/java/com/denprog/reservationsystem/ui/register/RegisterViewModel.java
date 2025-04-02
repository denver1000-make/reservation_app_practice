package com.denprog.reservationsystem.ui.register;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.reservationsystem.room.AppDatabase;
import com.denprog.reservationsystem.room.dao.AppDao;
import com.denprog.reservationsystem.room.entities.User;
import com.denprog.reservationsystem.util.FileUtil;
import com.denprog.reservationsystem.util.MainThreadRunner;
import com.denprog.reservationsystem.util.SimpleOperationCallback;
import com.denprog.reservationsystem.util.Validator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    public MutableLiveData<RegisterFormState> registerFormStateMutableLiveData = new MutableLiveData<>(null);
    public MutableLiveData<Bitmap> profilePictureMutableLiveData = new MediatorLiveData<>();
    public ObservableField<String> firstNameField = new ObservableField<>("");
    public ObservableField<String> middleNameField = new ObservableField<>("");
    public ObservableField<String> lastNameField = new ObservableField<>("");
    public ObservableField<String> emailNameField = new ObservableField<>("");
    public ObservableField<String> usernameField = new ObservableField<>("");
    public ObservableField<String> passwordField = new ObservableField<>("");

    public ObservableField<String> confirmPasswordField = new ObservableField<>("");
    private AppDao appDao;

    @Inject
    public RegisterViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public void onDataChanged(String firstName, String middleName, String lastName, String username, String email,  String password, String confirmPassword) {
        String firstNameError = Validator.validateName(firstName);
        String lastNameError = Validator.validateName(lastName);
        String middleNameError = Validator.validateName(middleName);
        String emailError = Validator.validateEmail(email);
        String passwordContentError = Validator.isPasswordContentValid(password) ? null : "Password Content Invalid";
        String passwordLengthError = Validator.isPasswordLengthValid(password) ? null : "Password should be at least 8 characters long";
        String confirmPasswordError = password.equals(confirmPassword) ? null : "Password Does Not Match";
        String usernameError = Validator.validateName(username);
        registerFormStateMutableLiveData.setValue(
                new RegisterFormState(
                        firstNameError,
                        middleNameError,
                        lastNameError,
                        emailError,
                        usernameError,
                        passwordLengthError,
                        passwordContentError,
                        confirmPasswordError,
                        doErrorExist(
                                firstNameError,
                                middleNameError,
                                lastNameError,
                                emailError,
                                usernameError,
                                passwordLengthError,
                                passwordContentError,
                                confirmPasswordError)));
    }

    public void register(User user, SimpleOperationCallback<User> userSimpleOperationCallback) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    appDao.insertUser(user);
                } catch (Exception e) {
                    MainThreadRunner.runOnMain(new Runnable() {
                        @Override
                        public void run() {
                            userSimpleOperationCallback.onError(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    public void saveImage(User user, Bitmap bitmap, Context context, SimpleOperationCallback<String> onImageSaved) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                // FileUtil.insertBitmapToInternalStorage(context, bitmap, FileUtil.profileImagesFolder, user.userId + FileUtil.personalProfileFolderAppend + "");
            }
        });
    }


    private boolean doErrorExist (String... errors) {
        for (String error : errors) {
            if (error != null) {
                return false;
            }
        }
        return true;
    }

}