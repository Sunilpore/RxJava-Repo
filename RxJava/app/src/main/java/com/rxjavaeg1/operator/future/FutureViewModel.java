package com.rxjavaeg1.operator.future;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.Future;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class FutureViewModel extends ViewModel {

    private FutureRepository repository;

    public FutureViewModel() {
        repository = FutureRepository.getInstance();
    }

    public Future<Observable<ResponseBody>> makeFutureQuery(){
        return repository.makeFutureQuery();
    }

}
