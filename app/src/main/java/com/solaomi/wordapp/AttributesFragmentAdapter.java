package com.solaomi.wordapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * {@link AttributesFragmentAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each attribute (definitions, examples, antonym/synonyms) of the Word object to be looked up.
 */
public class AttributesFragmentAdapter extends FragmentPagerAdapter {

    //    private static final String LOG_TAG = AttributesFragmentAdapter.class.getName();
    private Context mContext;
    private static final int PAGE_COUNT = 3;

    /**
     * Create a new {@link AttributesFragmentAdapter} object.
     *
     * @param context is the context of the app.
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     *                across swipes.
     */
    public AttributesFragmentAdapter(Context context, FragmentManager fm, String word) {
        super(fm);
        mContext = context;
        Bundle mBundle = new Bundle();
        mBundle.putString("word", word);
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DefinitionsFragment();
        } else if (position == 1) {
            return new RelatedFragment();
        } else {
            return new ExamplesFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    // Generate tab titles based on item position
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.attribute_definitions);
        } else if (position == 1) {
            return mContext.getString(R.string.attribute_related);
        } else {
            return mContext.getString(R.string.attribute_examples);
        }
    }
}
