package com.reikai.runtimepermissiondemo.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

public class PermissionHelper implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Context mContext;
    private Activity mActivity;
    private View mLayout;
    private int permissionCode;

    public PermissionHelper(Activity activity,Context context, View layout, int code) {
        mContext = context;
        mActivity = activity;
        mLayout = layout;
        permissionCode = code;
    }

    public void CheckPermission() {
        // Check if permission is GRANTED
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission already available
            Toast.makeText(mContext, "Permission already given.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Permission missing.", Toast.LENGTH_SHORT).show();
            RequestPermission();
        }
    }

    private void RequestPermission() {
        // This will be called if the permission has never been requested before.
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)) {
            // Permission has not been requested, will show info about it and request permission.
            Snackbar.make(mLayout, "Camera permission is required", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            ActivityCompat
                                    .requestPermissions(mActivity,
                                            new String[]{Manifest.permission.CAMERA},
                                            permissionCode);
                        }
                    })
                    .show();
        } else {
            Snackbar.make(mLayout, "Camera unavailable", Snackbar.LENGTH_LONG)
                    .show();

            ActivityCompat
                    .requestPermissions(mActivity,
                            new String[]{Manifest.permission.CAMERA},
                            permissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
        if (i == permissionCode) {
            if (ints.length == 1 && ints[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, "Permission already granted", Snackbar.LENGTH_LONG);
            } else {
                Snackbar.make(mLayout, "Permission denied", Snackbar.LENGTH_LONG);
            }
        }
    }
}
