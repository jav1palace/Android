package com.example.javierpalaciocuenca.myapplication.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.javierpalaciocuenca.myapplication.R;

/**
 * Created by javierpalaciocuenca on 17/10/2016.
 */

public class ExceptionDialogBuilder {
    public static AlertDialog alertDialog;

    public static AlertDialog createExceptionDialog(Context context, String message){
        try{
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Exception");
            alertDialog.setIcon(R.drawable.atm_citizen_icon);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        }catch(Exception e){
            e.printStackTrace();
        }

        return alertDialog;
    }
}
