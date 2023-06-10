package com.example.fisioterapi.location;

public interface LocationListener {
    void onLocationChanged(String latitude, String longitude);

    void onLocationFailed(String message);

    void onPermissionFailed(String message);

    void onPermissionGranted();

    void onPermissionDenied();


}
