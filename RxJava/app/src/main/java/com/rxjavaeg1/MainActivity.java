package com.rxjavaeg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rxjavaeg1.operators.OperatorActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @subscribe -> when not contains onSubscribe(), then it will return Disposable object.
 */

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivityTag";

    CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disposable = new CompositeDisposable();

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Exception {

                        //Used for non blocking UI thread operation
                        //After task.isComplete() onNext() is called for every iteration
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return task.isComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe: called.");
            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG,"onNext: "+Thread.currentThread().getName());
                Log.d(TAG,"onNext: "+task.getDescription());

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError: ",e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete: called.");
            }
        });


        disposable.add(taskObservable.subscribe(new Consumer<Task>() {
            @Override
            public void accept(Task task) throws Exception {

            }
        }));

        startActivity(new Intent(this, OperatorActivity.class));

    }


    /**
     * @disosable -> normally called on onDestroy() of an activity and onClear of an ViewModel()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //It will like soft delete.Delete all disposable object, but allow continue to subscribe on Observable
        disposable.clear();

        //It will like hard delete. Delete all objects, but it will disable the Observable to observe further
        disposable.dispose();
    }

}
