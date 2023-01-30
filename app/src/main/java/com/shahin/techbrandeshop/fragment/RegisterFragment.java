package com.shahin.techbrandeshop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.activity.HomeActivity;


public class RegisterFragment extends Fragment {

    private FrameLayout frameLayout;
    private TextInputLayout mTxtLayoutPwd, mTxtLayoutConfirmPwd;
    private TextInputEditText mEdtEmail, mEdtUserName, mEdtPwd, mEdtConfirmPwd;
    private TextView txtHaveAccount;
    private Button btnSignUp;

    private static final String BTN_DISABLE = "#bdc3c7";
    private static final String BTN_ENABLE = "#EF984B";

    public RegisterFragment(FrameLayout frameLayout) {
        this.frameLayout = frameLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initUI(view);
        addActionForHaveAccountEvent();
        addActionForBtnSignUp();

        addListenerForEditText(mEdtEmail);
        addListenerForEditText(mEdtUserName);
        addListenerForEditText(mEdtPwd);
        addListenerForEditText(mEdtConfirmPwd);

        changeEndIconStateOnFocusEditText(mEdtPwd, mTxtLayoutPwd);
        changeEndIconStateOnFocusEditText(mEdtConfirmPwd, mTxtLayoutConfirmPwd);

        return view;
    }

    private void initUI(View view) {
        txtHaveAccount = view.findViewById(R.id.txt_have_account);
        txtHaveAccount.append(" " + Html.fromHtml("<u>Login now!</u>"));

        mEdtEmail = view.findViewById(R.id.edt_regis_email);
        mEdtUserName = view.findViewById(R.id.edt_regis_username);
        mEdtPwd = view.findViewById(R.id.edt_regis_pwd);
        mEdtConfirmPwd = view.findViewById(R.id.edt_confirm_pass);

        mTxtLayoutPwd = view.findViewById(R.id.textInputLayout2);
        mTxtLayoutConfirmPwd = view.findViewById(R.id.textInputLayout3);

        btnSignUp = view.findViewById(R.id.btn_signup);
        changeButtonState(false, BTN_DISABLE);
    }

    private void addActionForHaveAccountEvent() {
        txtHaveAccount.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left)
                    .replace(frameLayout.getId(), new LoginFragment(frameLayout)).commit();
        });
    }

    private void addActionForBtnSignUp() {
        btnSignUp.setOnClickListener(view1 -> {

            if (!isValidRegisterAccount(mEdtUserName.getText().toString(), mEdtPwd.getText().toString(), mEdtConfirmPwd.getText().toString()))
                return;

            Intent intent = new Intent(getContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        });
    }

    private void changeButtonState(boolean state, String color) {
        btnSignUp.setEnabled(state);
        btnSignUp.setBackgroundColor(Color.parseColor(color));
    }

    private void addListenerForEditText(TextInputEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!mEdtEmail.getText().toString().trim().isEmpty() && !mEdtPwd.getText().toString().trim().isEmpty() && !mEdtUserName.getText().toString().trim().isEmpty()
                        && !mEdtConfirmPwd.getText().toString().trim().isEmpty())
                    changeButtonState(true, BTN_ENABLE);
                else
                    changeButtonState(false, BTN_DISABLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void changeEndIconStateOnFocusEditText(TextInputEditText editText, TextInputLayout textInputLayout) {
        editText.setOnFocusChangeListener((view, isFocus) -> {
            textInputLayout.setEndIconActivated(isFocus);
        });
    }

    private boolean isValidRegisterAccount(String userName, String password, String confirmPassword) {
        if (!userName.matches("[a-zA-Z0-9]{8,}")) {
            mEdtUserName.setError("Must have at least 8 characters and only contain alphabet characters or number!");
            mEdtUserName.requestFocus();
            return false;
        }

        if (password == null || password.isEmpty()) {
            mEdtPwd.setError("Password can't contain whitespace!");
            mEdtPwd.requestFocus();
            return false;
        }

        if (!confirmPassword.equals(password)) {
            mEdtConfirmPwd.setError("Password not match!");
            mEdtConfirmPwd.requestFocus();
            return false;
        }

        return true;
    }
}