package com.jacmobile.magicprices.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.SearchAutoComplete;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jacmobile.magicprices.App;
import com.jacmobile.magicprices.R;
import com.jacmobile.magicprices.network.DeckBrewService;
import com.jacmobile.magicprices.network.DeckBrewService.MTGCard;
import com.jacmobile.magicprices.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements URLLoader
{
    public static final String TAG = MainActivity.class.getSimpleName();

    @Inject DeckBrewService deckBrewService;

    @Bind(R.id.msv_main) MultiStateView multiStateView;
    @Bind(R.id.content_webview) WebView webView;
    @Bind(R.id.list_main) ListView listMain;
    @Bind(R.id.progress_main) ProgressBar progressBar;
    @Bind(R.id.search_main) SearchView searchCards;
    @Bind(R.id.empty_view_main) TextView emptyView;
    @Bind(android.support.v7.appcompat.R.id.search_src_text) SearchAutoComplete searchSuggestionsView;

    private MTGWebClient webClient;
    private MTGCardAdapter mtgCardAdapter;
    private ArrayAdapter<String> suggestionsAdapter;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);
    }

    @Override protected void onResume()
    {
        super.onResume();
        if (webClient == null) this.webClient = new MTGWebClient();
        initializeSearchView();
        initializeSuggestionsView();
    }

    @Override public void onBackPressed()
    {
        if (multiStateView != null) {
            switch (multiStateView.getViewState()) {
                case EMPTY:
                    showList();
                    break;
                case CONTENT:
                    super.onBackPressed();
                    break;
                case LOADING:
                    super.onBackPressed();
                    break;
                case WEB:
                    showList();
                    break;
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.info:
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(getString(R.string.info_body))
                        .setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener()
                        {
                            @Override public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Callback from edition selection modal.
     */
    @Override public void loadURL(@NonNull String url)
    {
        searchCards.clearFocus();
        showWeb(url);
    }

    private void initializeSearchView()
    {
        searchCards.setSubmitButtonEnabled(true);
        searchCards.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override public boolean onQueryTextSubmit(String query)
            {
                if (!TextUtils.isEmpty(query)) searchDeckBrew(query);
                return false;
            }

            @Override public boolean onQueryTextChange(String newText)
            {
                Log.wtf("onQueryTextChange", "newText: " + newText);
                if (!TextUtils.isEmpty(newText)) {
                    deckBrewService
                            .predictSearch(newText)
                            .enqueue(new Callback<DeckBrewService.PredictSearch>()
                            {
                                @Override
                                public void onResponse(final Response<DeckBrewService.PredictSearch> response)
                                {
                                    if (response.body().results.size() > 0) {
                                        suggestionsAdapter.clear();
                                        suggestionsAdapter.addAll(response.body().results);
                                        suggestionsAdapter.notifyDataSetChanged();
//                                        searchSuggestionsView.setAdapter(null);
//                                        searchSuggestionsView.setAdapter(suggestionsAdapter);
                                        searchSuggestionsView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                            {
                                                searchDeckBrew(response.body().results.get(position));
                                            }
                                        });
                                        searchSuggestionsView.showDropDown();
                                        searchCards.requestFocus();
                                    } else {
                                        suggestionsAdapter.clear();
                                        suggestionsAdapter.notifyDataSetChanged();
                                        searchSuggestionsView.setAdapter(null);
                                        searchSuggestionsView.dismissDropDown();
                                    }
                                }

                                @Override public void onFailure(Throwable t) {}
                            });
                } else {
                    suggestionsAdapter.clear();
                    suggestionsAdapter.notifyDataSetChanged();
                    searchSuggestionsView.setAdapter(null);
                    searchSuggestionsView.dismissDropDown();
                }
                return true;
            }
        });
    }

    private void initializeSuggestionsView()
    {
        this.suggestionsAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<>(Arrays.asList(new String[0])));
        searchSuggestionsView.setAdapter(suggestionsAdapter);
        searchSuggestionsView.setThreshold(1);
    }

    private void searchDeckBrew(String query)
    {
        DeviceUtils.hideKeyboard(MainActivity.this);
        showLoading();

        deckBrewService
                .searchCards(query.toLowerCase())
                .enqueue(new Callback<List<MTGCard>>()
                {
                    @Override
                    public void onResponse(Response<List<MTGCard>> response)
                    {
                        if (response.body().size() > 0) {
                            updateCardAdapter(response.body());
                            listMain.setAdapter(mtgCardAdapter);
                            listMain.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                    handleCardSelection(position);
                                }
                            });
                            showList();
                        } else {
                            showEmpty();
                        }
                    }

                    @Override public void onFailure(Throwable t)
                    {
                    }
                });
    }

    private void updateCardAdapter(List<MTGCard> cardList)
    {
        if (mtgCardAdapter == null) {
            this.mtgCardAdapter = new MTGCardAdapter(getApplicationContext(), R.layout.card_search_result, cardList);
        } else {
            mtgCardAdapter.clear();
            mtgCardAdapter.addAll(cardList);
            mtgCardAdapter.notifyDataSetChanged();
        }
    }

    private void handleCardSelection(int position)
    {
        final MTGCard selectedItem = ((MTGCardAdapter) listMain.getAdapter()).getItem(position);
        if (selectedItem.editions.length == 1) {
            showWeb(selectedItem.store_url);
        } else {
            final ArrayList<String> storeUrls = new ArrayList<>();
            final ArrayList<String> editionNames = new ArrayList<>();
            for (DeckBrewService.Edition e : selectedItem.editions) {
                storeUrls.add(e.store_url);
                editionNames.add(e.set);
            }
            EditionListDialog
                    .newInstance(selectedItem.name, storeUrls, editionNames)
                    .setUrlLoaderAndShow(getFragmentManager(), this);
        }
    }

    private void showView(MultiStateView.ViewState ordinal)
    {
        if (multiStateView != null) {
            multiStateView.setViewState(ordinal);
        } else {
            Log.d(TAG, "NullPointerException attempting to show view. -->multiStateView");
        }
    }

    private void showList()
    {
        showView(MultiStateView.ViewState.CONTENT);
    }

    private void showEmpty()
    {
        showView(MultiStateView.ViewState.EMPTY);
    }

    private void showWeb(String url)
    {
        showLoading();
        if (webView != null) {
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);//this is also required to get JS running
            webView.setWebViewClient(webClient);
            webView.loadUrl(url);
        } else {
            showList();
        }
    }

    private void showLoading()
    {
        showView(MultiStateView.ViewState.LOADING);
    }

    /**
     * Custom client to show WebView when page finishes loading
     */
    class MTGWebClient extends WebViewClient
    {
        @Override
        public void onReceivedSslError(WebView view, @NonNull SslErrorHandler handler, SslError error)
        {
            handler.proceed();
        }

        //could prevent loading any URLs that aren't intended
        @Override public boolean shouldOverrideUrlLoading(WebView view, String urlToLoad)
        {
            view.loadUrl(urlToLoad);
            return true;
        }

        @Override public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
            searchCards.clearFocus();
            showView(MultiStateView.ViewState.WEB);
        }
    }
}