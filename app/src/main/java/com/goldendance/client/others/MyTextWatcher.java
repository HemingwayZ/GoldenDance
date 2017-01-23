package com.goldendance.client.others;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MyTextWatcher implements TextWatcher {
    private static final String TAG = MyTextWatcher.class.getSimpleName();
    private View clearView;

    public MyTextWatcher(final EditText editText, View clearView) {
        clearView.setVisibility(View.GONE);
        clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        this.clearView = clearView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        
    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = s.length();
        if (length > 0) {
            clearView.setVisibility(View.VISIBLE);
        } else {
            clearView.setVisibility(View.GONE);
        }
    }
}