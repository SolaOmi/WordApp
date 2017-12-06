package com.solaomi.wordapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/** {@link WordAttributesAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each attribute (definitions, examples, antonym/synonyms) of the Word object to be looked up.
 */
public class WordAttributesAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;

    /**
     * Create a new {@link WordAttributesAdapter} object.
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public WordAttributesAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DefinitionsFragment();
        } else if (position == 1) {
            return new AntonymSynonymFragment();
        } else {
            return new ExamplesFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() { return PAGE_COUNT; }
}
