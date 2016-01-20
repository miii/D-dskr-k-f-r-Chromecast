package com.jacobandersson.dodskrok;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import java.util.HashMap;

/**
 * Created by Jacob on 2015-12-20.
 */
public class PlayingInputDialog {

    private PlayingArrayAdapter.Types type;
    private HashMap<String, Integer> locale;
    private BaseAdapter adapter;

    PlayingActivity activity;

    public PlayingInputDialog(PlayingActivity activity, PlayingArrayAdapter.Types type) {
        this.activity = activity;
        setLocale(type);
        buildDialog();
    }

    public void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(locale.get("dialog_add_title"));

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_element, null);

        final EditText inputEditText = (EditText) dialogView.findViewById(R.id.add_person_name);

        builder.setView(dialogView);
        builder.setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Leave empty
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputEditText.getText().toString();
                if (text.length() == 0)
                    return;

                CCBridge.add(text, type);

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                dialog.dismiss();
            }
        });

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void setLocale(PlayingArrayAdapter.Types type) {
        this.type = type;
        locale = new HashMap();

        switch (type) {
            case PERSON:
                locale.put("dialog_add_title", R.string.dialog_add_person_title);
                break;
            case MISSION:
                locale.put("dialog_add_title", R.string.dialog_add_mission_title);
                break;
        }
    }

}
