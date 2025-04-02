package com.denprog.reservationsystem.ui.register;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denprog.reservationsystem.R;
import com.denprog.reservationsystem.databinding.FragmentRegisterBinding;
import com.denprog.reservationsystem.util.FileUtil;

import java.io.FileNotFoundException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private FragmentRegisterBinding bind;
    private ActivityResultLauncher<PickVisualMediaRequest> imagePicker = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if (o == null) {return;}
            try {
                mViewModel.profilePictureMutableLiveData.setValue(FileUtil.convertUriToBitmap(requireContext(), o));
            } catch (FileNotFoundException e) {
                mViewModel.profilePictureMutableLiveData.setValue(null);
                Toast.makeText(requireContext(), "Something went wrong with picking the image.", Toast.LENGTH_SHORT).show();
            }
        }
    });

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bind = FragmentRegisterBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        bind.setRegisterFragmentViewModel(mViewModel);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mViewModel.onDataChanged(
                        mViewModel.firstNameField.get(),
                        mViewModel.middleNameField.get(),
                        mViewModel.lastNameField.get(),
                        mViewModel.usernameField.get(),
                        mViewModel.emailNameField.get(),
                        mViewModel.passwordField.get(),
                        mViewModel.confirmPasswordField.get());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onDataChanged(
                        mViewModel.firstNameField.get(),
                        mViewModel.middleNameField.get(),
                        mViewModel.lastNameField.get(),
                        mViewModel.usernameField.get(),
                        mViewModel.emailNameField.get(),
                        mViewModel.passwordField.get(),
                        mViewModel.confirmPasswordField.get());
            }
        };

        bind.registerAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = mViewModel.profilePictureMutableLiveData.getValue();
                if (bitmap == null) {
                    Toast.makeText(requireContext(), "Please Select A Profile Picture", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

        bind.firstNameField.addTextChangedListener(textWatcher);
        bind.middleNameField.addTextChangedListener(textWatcher);
        bind.lastNameField.addTextChangedListener(textWatcher);
        bind.usernameField.addTextChangedListener(textWatcher);
        bind.emailField.addTextChangedListener(textWatcher);
        bind.passwordField.addTextChangedListener(textWatcher);
        bind.confirmPasswordField.addTextChangedListener(textWatcher);
        mViewModel.registerFormStateMutableLiveData.observe(getViewLifecycleOwner(), new Observer<RegisterFormState>() {
            @Override
            public void onChanged(RegisterFormState registerFormState) {
                if (registerFormState == null) return;
                bind.registerAction.setEnabled(registerFormState.isDataValid);
                bind.firstNameField.setError(registerFormState.firstNameError);
                bind.middleNameField.setError(registerFormState.middleNameError);
                bind.lastNameField.setError(registerFormState.lastNameError);
                bind.usernameField.setError(registerFormState.usernameError);
                bind.emailField.setError(registerFormState.emailError);
                bind.passwordField.setError(registerFormState.passwordContentError != null || registerFormState.passwordLengthError != null ? "Password Invalid" : null);
                bind.passwordContentStatus.setChecked(registerFormState.passwordContentError == null);
                bind.passwordLengthStatus.setChecked(registerFormState.passwordLengthError == null);
                bind.confirmPasswordField.setError(registerFormState.confirmPasswordError);
            }
        });

        mViewModel.profilePictureMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if (bitmap == null) {
                    bind.imageButton.setImageResource(R.drawable.ic_launcher_foreground);
                } else {
                    bind.imageButton.setImageBitmap(bitmap);
                }
            }
        });
    }

}