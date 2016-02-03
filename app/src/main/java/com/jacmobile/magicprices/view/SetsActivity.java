package com.jacmobile.magicprices.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.jacmobile.magicprices.App;
import com.jacmobile.magicprices.R;
import com.jacmobile.magicprices.network.DeckBrewService;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetsActivity extends AppCompatActivity
{
    public static final String TAG = SetsActivity.class.getSimpleName();

    public static void launch(Context context)
    {
        context.startActivity(new Intent(context, SetsActivity.class));
    }

    @Inject DeckBrewService deckBrewService;

    @Bind(R.id.list_main) ListView listMain;
    @Bind(R.id.progress_main) ProgressBar progressBar;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        hideList();
    }

    @Override protected void onResume()
    {
        super.onResume();
        getSetsList();
    }

    private void getSetsList()
    {
        Call<List<DeckBrewService.MTGSet>> setsCall = deckBrewService.listSets();
        setsCall.enqueue(new Callback<List<DeckBrewService.MTGSet>>()
        {
            @Override public void onResponse(Response<List<DeckBrewService.MTGSet>> response)
            {
                listMain.setAdapter(new SetAdapter(response.body()));
                showList();
            }

            @Override public void onFailure(Throwable t)
            {

            }
        });
    }

    private void hideList()
    {
        progressBar.setVisibility(View.VISIBLE);
        listMain.setVisibility(View.GONE);
    }

    private void showList()
    {
        listMain.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
