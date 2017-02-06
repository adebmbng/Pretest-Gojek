package com.debam.pretest_gojek.interfaces;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.debam.pretest_gojek.BaseView;

/**
 * Created by Debam on 2/6/2017.
 */

public interface AddContactView extends BaseView<AddContactPresenter>{

    void onValidationFailed(TextInputLayout til);
    void onValidationSuccess(TextInputLayout til);

    void onSuccessfull();

    void onLoad();

    void onFailed();

    TextInputLayout gettilFirstName();
    TextInputLayout gettilLastName();
    TextInputLayout gettilEmail();
    TextInputLayout gettilPhone();

    EditText getetFirstName();
    EditText getetLastName();
    EditText getetEmail();
    EditText getPhone();
}
