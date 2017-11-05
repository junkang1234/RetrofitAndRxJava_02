package com.bewei.retrofitandrxjava.inter;

import com.bewei.retrofitandrxjava.bean.Data;
import com.bewei.retrofitandrxjava.bean.News;
import com.bewei.retrofitandrxjava.bean.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 1. 类的用途
 * 2. @author forever
 * 3. @date 2017/11/3 10:13
 */


public interface ApiService {
    /**
     * 结合Retrofit+RxJava
     * thtp://service.meiyinkeqiu.com/service/ads/cptj
     * @param
     * @return
     */
    @GET("ads/cptj")
    Observable<News> getNoParams();
    /**
     * 结合RxJava
     * @param user
     * @return
     * https://api.github.com/users/forever
     */
    @GET("users/{user}")
  Observable<User>  getHasParams(@Path("user")String user);

    //第三个接口,,兼容json解析功能，，集成rxjava  square  retrofit
    //https://api.github.com/repos/square/retrofit/contributors
    @GET("repos/{square}/{retrofit}/contributors")

   Observable<List<Data>> getHasParams2(@Path("square")String square, @Path("retrofit")String retrofit);
}
