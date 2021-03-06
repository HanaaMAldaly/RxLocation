package com.sha.kamel.rxlocation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.annimon.stream.Stream;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by Sha on 1/7/18.
 */

public final class GoogleApiClientUtil {

    public static GoogleApiClient create(Context context, OnConnectedCallback connectedCallback, Api... apis){
        return create(context, new GoogleApiClient.ConnectionCallbacks() {
                          @Override
                          public void onConnected(@Nullable Bundle bundle) {
                              connectedCallback.run();
                          }

                          @Override
                          public void onConnectionSuspended(int i) {

                          }
                      },
                apis);
    }

    public static GoogleApiClient create(
            Context context, GoogleApiClient.ConnectionCallbacks callbacks, Api... apis){

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(connectionResult -> Log.d(
                        "RxLocation",
                        "connectionResult = " + connectionResult.getErrorMessage())
                );

        Stream.of(apis).forEach(builder::addApi);

        GoogleApiClient googleApiClient = builder.build();
        googleApiClient.connect();
        return googleApiClient;
    }
}

