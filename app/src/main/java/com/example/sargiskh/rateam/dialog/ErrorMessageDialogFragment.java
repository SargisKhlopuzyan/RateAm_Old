package com.example.sargiskh.rateam.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.sargiskh.rateam.R;
import com.example.sargiskh.rateam.util.Constants;

public class ErrorMessageDialogFragment extends DialogFragment {

    public ErrorMessageDialogFragment() { }

    private TextView textViewErrorMessage;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.fragment_dialog_error_message, null);
        dialogBuilder.setView(dialogView);

        initSubViews(dialogView);
        populateSubViews();

        Dialog dialog = dialogBuilder.setCancelable(true).setTitle(getString(R.string.error_title)).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();

        return dialog;
    }

    private void initSubViews(View rootView) {
        textViewErrorMessage = rootView.findViewById(R.id.textViewErrorMessage);
    }

    private void populateSubViews() {
        String errorMessage = getArguments().getString(Constants.BUNDLE_ERROR_MESSAGE);
        textViewErrorMessage.setText(errorMessage);
    }

}