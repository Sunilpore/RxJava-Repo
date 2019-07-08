package com.rxjavaeg1.operators;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.rxjavaeg1.DataSource;
import com.rxjavaeg1.R;
import com.rxjavaeg1.Task;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OperatorActivity extends AppCompatActivity {

    private String TAG = "OperatorActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);

        final Task task = new Task("walk with friends",false,2);

        Observable <Task> taskObservable = Observable
                .create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(ObservableEmitter<Task> emitter) throws Exception {

                        // Inside the subscribe method iterate through the list of tasks and call onNext(task)
                        for(Task task: DataSource.createTasksList()){
                            if(!emitter.isDisposed()){
                                emitter.onNext(task);
                            }
                        }
                        // Once the loop is complete, call the onComplete() method
                        if(!emitter.isDisposed()){
                            emitter.onComplete();
                        }
                        

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {

                Log.d(TAG,"onNext: "+task.getDescription());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                Log.d(TAG,"onComplete: "+task.getDescription());
            }
        });


    }


}
