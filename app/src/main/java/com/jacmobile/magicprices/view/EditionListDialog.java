package com.jacmobile.magicprices.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jacmobile.magicprices.App;
import com.jacmobile.magicprices.R;
import com.jacmobile.magicprices.utils.DeviceUtils;

import java.util.ArrayList;

/**
 * Usage:
 * Implement URLLoader.
 * Create a newInstance() of this.
 * Call setUrlLoaderAndShow on that instance.
 */
public class EditionListDialog extends DialogFragment
{
    public static final String TAG = EditionListDialog.class.getSimpleName();
    private static final String LIST = "list";
    private static final String TITLE = "title";
    private static final String URLS = "urls";

    private URLLoader urlLoader;

    public static EditionListDialog newInstance(@NonNull String cardName,
                                                 @NonNull ArrayList<String> storeUrls,
                                                 @NonNull ArrayList<String> editions)
    {
        Bundle args = new Bundle();
        args.putStringArrayList(LIST, editions);
        args.putString(TITLE, cardName);
        args.putStringArrayList(URLS, storeUrls);
        EditionListDialog dialog = new EditionListDialog();
        dialog.setArguments(args);
        return dialog;
    }

    public void setUrlLoaderAndShow(FragmentManager fm, URLLoader urlLoader)
    {
        this.urlLoader = urlLoader;
        show(fm, TAG);
    }

    private boolean firstAttach = true;

    @Override public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (firstAttach) {
            firstAttach = false;
            ((App) getActivity().getApplication()).getAppComponent().inject(this);
        }
    }

    @Override public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), DialogFragment.STYLE_NO_FRAME);

        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.list_dialog,
                new LinearLayout(getActivity()),
                false);

        this.setUpView(view, getArguments());

        return builder.setView(view).create();
    }

    @Override public void onResume()
    {
        //set dialog height
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setLayout(params.width,
                (DeviceUtils.getScreenHeight(getActivity()) / 2));

        super.onResume();
    }

    private void setUpView(View view, Bundle args)
    {
        final String title = args.getString(TITLE);
        final ArrayList<String> urls = args.getStringArrayList(URLS);
        final ArrayList<String> list = args.getStringArrayList(LIST);

        ((TextView) view.findViewById(R.id.list_dialog_title)).setText(title);
        ListView listView = (ListView) view.findViewById(R.id.list_dialog);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.edition_list_item, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                urlLoader.loadURL(urls.get(position));
                dismissAllowingStateLoss();
            }
        });
    }
}
