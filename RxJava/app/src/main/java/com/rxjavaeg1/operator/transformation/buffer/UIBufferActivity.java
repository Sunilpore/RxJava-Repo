package com.rxjavaeg1.operator.transformation.buffer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jakewharton.rxbinding3.view.RxView;
import com.rxjavaeg1.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import kotlin.Unit;

public class UIBufferActivity extends AppCompatActivity {

    private String TAG = "UIBufActTag";
    // global disposables object
    CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);

        /*Note:-
        Periodically gather items from an Observable into bundles and emit the bundles rather than emitting items one at a time*/

        // detect clicks to a button
        RxView.clicks(findViewById(R.id.btn_click))
                .map(new Function<Unit, Integer>() { // convert the detected clicks to an integer
                    @Override
                    public Integer apply(Unit unit) throws Exception {
                        return 1;
                    }
                })
                .buffer(4, TimeUnit.SECONDS) // capture all the clicks during a 4 second interval
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d); // add to disposables to you can clear in onDestroy
                    }
                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG, "onNext: You clicked " + integers.size() + " times in 4 seconds!");
                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }


}
