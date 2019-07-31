package dev.rodni.ru.rxjavawithretrofit.service;

import dev.rodni.ru.rxjavawithretrofit.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostAppService {

    @POST("posts")
    Call<User> getPosts(@Body User user);
}
