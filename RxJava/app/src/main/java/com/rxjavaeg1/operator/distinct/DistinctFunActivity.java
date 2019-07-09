package com.rxjavaeg1.operator.distinct;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.rxjavaeg1.DataSource;
import com.rxjavaeg1.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * The Distinct operator filters an Observable by only allowing items through that have not already been emitted
 * @distinct() -> remove duplicates operator
 */

public class DistinctFunActivity extends AppCompatActivity {

    private String TAG = "DistinctActTag";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable<Task> taskObservable = Observable
                .fromIterable(createTasksList())
                //.distinct(new Function<Task, Task>() { // <--- WRONG
                .distinct(new Function<Task, Integer>() { // <--- RIGHT
                    @Override
                    public Integer apply(Task task) throws Exception {         // <--- RIGHT
                        return task.getPriority();
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
                Log.d(TAG, "onNext: " + task.getDescription());
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        });


    }

    private List<Task> createTasksList(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Take out the trash", true, 3));
        tasks.add(new Task("Walk the dog", false, 2));
        tasks.add(new Task("Make my bed", true, 1));
        tasks.add(new Task("Unload the dishwasher", false, 0));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5)); //duplicate for testing distinct operator
        return tasks;
    }


}
