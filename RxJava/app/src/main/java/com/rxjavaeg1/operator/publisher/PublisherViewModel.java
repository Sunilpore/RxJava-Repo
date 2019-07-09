package com.rxjavaeg1.operator.publisher;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import okhttp3.ResponseBody;

public class PublisherViewModel extends ViewModel {

    private PublisherRepository repository;

    public PublisherViewModel() {
        repository = PublisherRepository.getInstance();
    }

    public LiveData<ResponseBody> makeQuery(){
        return repository.makeReactiveQuery();
    }

}
