package com.bamideleoguntuga.retrofitpagination;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.bamideleoguntuga.retrofitpagination.adapters.PaginationListAdapter;
import com.bamideleoguntuga.retrofitpagination.services.GitHubService;
import com.bamideleoguntuga.retrofitpagination.services.ServiceGenerator;
import com.bamideleoguntuga.retrofitpagination.services.models.GitHubRepo;
import com.bamideleoguntuga.retrofitpagination.services.models.PaginationItem;
import com.bamideleoguntuga.retrofitpagination.utils.GitHubPagelinksUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private PaginationListAdapter adapter;
    private List<PaginationItem> values = new ArrayList<>();
    private GitHubService service;

    private Callback<List<GitHubRepo>> callback =
            new Callback<List<GitHubRepo>>() {
                @Override
                public void onResponse(Call<List<GitHubRepo>> call,
                                       Response<List<GitHubRepo>> response) {
                    if (response.isSuccessful()) {
                        List<GitHubRepo> body = response.body();

                        for (GitHubRepo repo : body) {
                            values.add(new PaginationItem(repo.getId(), repo.getName()));
                        }

                        adapter.notifyDataSetChanged();
                        fetchReposNextPage(response);
                    } else {
                        Log.e("Request failed: ", "Cannot request GitHub repositories");
                    }
                }

                @Override
                public void onFailure(Call<List<GitHubRepo>> call,
                                      Throwable t) {
                    Log.e("Error fetching repos", t.getMessage());
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_pagination);

        ListView listView = (ListView) findViewById(R.id.pagination_list);
        adapter = new PaginationListAdapter(MainActivity.this, values);
        listView.setAdapter(adapter);


        // Change base url to GitHub API
       // ServiceGenerator.changeApiBaseUrl("https://api.github.com/");

        // Create a simple REST adapter which points to GitHub’s API
        service = ServiceGenerator.createService(GitHubService.class);

        // Fetch and print a list of repositories for user “fs-opensource”
        Call<List<GitHubRepo>> call = service.reposForUser("delaroy");
        call.enqueue(callback);
    }

    private void fetchReposNextPage(Response<List<GitHubRepo>> response) {
        GitHubPagelinksUtils pagelinksUtils =
                new GitHubPagelinksUtils(response.headers());
        String next = pagelinksUtils.getNext();

        Log.d("Header", response.headers().get("Link"));

        if (TextUtils.isEmpty(next)) {
            return; // nothing to do
        }

        Call<List<GitHubRepo>> call = service.reposForUserPaginate(next);
        call.enqueue(callback);
    }

}