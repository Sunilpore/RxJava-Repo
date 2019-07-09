package com.rxjavaeg1.operator.publisher;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.rxjavaeg1.NetworkApi.ServiceGenerator;

import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * The makeReactiveQuery() method uses the fromPublisher() method to convert a Flowable into LiveData.

 * Notice that I'm subscribing on a background thread! This is very important.
 * All network operations must be done on a background thread.
 */

class PublisherRepository {

    private static PublisherRepository instance;

    static PublisherRepository getInstance(){
        if(instance == null){
            instance = new PublisherRepository();
        }
        return instance;
    }

    /**
     * This is where the magic happens.
     * The makeReactiveQuery() method uses the fromPublisher() method to convert a Flowable into LiveData.
     * @fromPublisher() - convert Flowable to LiveData
     * @toPublisher() - convert LiveData to Flowable
     * @return
     */
    LiveData<ResponseBody> makeReactiveQuery(){
        return LiveDataReactiveStreams.fromPublisher(ServiceGenerator.getRequestApi()
                .makeQuery()
                .subscribeOn(Schedulers.io()));
    }

}
