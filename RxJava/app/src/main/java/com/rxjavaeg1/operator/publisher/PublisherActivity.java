package com.rxjavaeg1.operator.publisher;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * fromPublisher() is used to convert LiveData objects to reactive streams, or from reactive streams to LiveData objects.
 */

public class PublisherActivity extends AppCompatActivity {

    private String TAG = "PublsrOptTag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PublisherViewModel publisherViewModel = ViewModelProviders.of(this).get(PublisherViewModel.class);
        publisherViewModel.makeQuery().observe(this, new androidx.lifecycle.Observer<ResponseBody>(){
            @Override
            public void onChanged(ResponseBody responseBody) {

                Log.d(TAG, "onChanged: this is a live data response!");
                try {
                    Log.d(TAG, "onChanged: " + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }


}
