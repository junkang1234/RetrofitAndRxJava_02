package com.bewei.retrofitandrxjava.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bewei.retrofitandrxjava.R;
import com.bewei.retrofitandrxjava.api.Api;
import com.bewei.retrofitandrxjava.bean.Data;
import com.bewei.retrofitandrxjava.bean.News;
import com.bewei.retrofitandrxjava.bean.User;
import com.bewei.retrofitandrxjava.inter.ApiService;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //    getNoParams();
       // getHasParams();
        getHasParams2();

    }

    private void getHasParams2() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        //得到Observable
        Observable<List<Data>> observable = apiService.getHasParams2("square", "retrofit");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<Data>, Observable<Data>>() {
                    @Override
                    public Observable<Data> call(List<Data> datas) {//一对多的关系

                        return Observable.from(datas);//循环取Data 代替了迷之缩进的方式获取
                    }
                }).subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Data data) {//根据被观察生产多少个事件就消费多少个
                String avatar_url = data.getAvatar_url();
                Log.i("xxx", avatar_url);

            }

        });
    }

    private void getHasParams() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        //得到Observable
        Observable<User> observable = apiService.getHasParams("forever");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<User, User>() {//直接变换 一对一的关系
                    @Override
                    public User call(User user) {
                        return user;
                    }
                }).subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                String avatar_url = user.getAvatar_url();
                Log.i("xxx", avatar_url);

            }
        });
    }

    private void getNoParams() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_PATH).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        //得到Observable
        Observable<News> observable = apiService.getNoParams();//生产事件
        //被观察者订阅观察 默认在同一个线程
         observable.subscribeOn(Schedulers.io())//指定IO做耗时操作
                .observeOn(AndroidSchedulers.mainThread())//指定更新UI在主线程
                .subscribe(new Observer<News>() {
                    @Override
                    public void onCompleted() {//完成

                    }

                    @Override
                    public void onError(Throwable e) {//失败
                        Log.i("xxx", e.getMessage());
                    }

                    @Override
                    public void onNext(News news) {//消费事件
                        List<News.AdsBean> ads = news.getAds();
                        for (int i = 0; i < ads.size(); i++) {
                            News.AdsBean adsBean = ads.get(i);
                            String gonggaoren = adsBean.getGonggaoren();
                            Log.i("xxx", gonggaoren);
                        }

                    }
                });
    }
}
