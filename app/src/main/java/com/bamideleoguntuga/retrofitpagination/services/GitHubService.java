package com.bamideleoguntuga.retrofitpagination.services;

import java.util.List;

import com.bamideleoguntuga.retrofitpagination.services.models.GitHubRepo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);

    @GET
    Call<List<GitHubRepo>> reposForUserPaginate(@Url String url);
}
