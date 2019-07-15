package com.rxjavaeg1.operator.transformation.debounce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;

import com.rxjavaeg1.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DebounceActivity extends AppCompatActivity {

    /**
     *TODO : use switchMap() to avoid parallel execution of search query
     *      when result of first query is not recieved yet and still second query is executed then it will cause a problem
     * @debounce()-> It will filter out conituous emission of emitted Observable data and pass last emitted data if mentioned time-span is pass
     */


    private static final String TAG = "DebActTag";

    //ui
    private SearchView searchView;

    // vars
    private CompositeDisposable disposables = new CompositeDisposable();
    private long timeSinceLastRequest; // for log printouts only. Not part of logic.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce);
        searchView = findViewById(R.id.search_view);

        timeSinceLastRequest = System.currentTimeMillis();

        //create the Observable
        Observable<String> observableQueryText = Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                        // Listen for text input into the SearchView
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                if(!emitter.isDisposed()){
                                    emitter.onNext(newText);    // Pass the query to the emitter
                                }
                                return false;
                            }
                        });

                    }
                })
                .debounce(1000, TimeUnit.MILLISECONDS)   // Apply Debounce() operator to limit requests
                .subscribeOn(Schedulers.io());

        // Subscribe an Observer
        observableQueryText.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: time  since last request: " + (System.currentTimeMillis() - timeSinceLastRequest));
                Log.d(TAG, "onNext: search query: " + s);
                timeSinceLastRequest = System.currentTimeMillis();

                // method for sending a request to the server
                sendRequestToServer(s);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }


    // Fake method for sending a request to the server
    private void sendRequestToServer(String query){
        // do nothing
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear(); // clear disposables
    }

}
