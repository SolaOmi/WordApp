package com.solaomi.wordapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Definition;
import net.jeremybrooks.knicker.dto.Related;

import java.util.List;

/**
 * {@link AttributesAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * item based on a data source, which is a list of {@link Definition} or {@link Related} objects.
 */
public class AttributesAdapter<Type> extends ArrayAdapter<Type> {
//    private static final String LOG_TAG = AttributesAdapter.class.getName();

    /**
     * Create a new {@link AttributesAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param list is the list of {@link Object}s to be displayed.
     */
    public AttributesAdapter(Context context, List<Type> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the object located at this position in the list.
        Type currentItem = getItem(position);

        // Check if the object is of type Definition
        if ( currentItem instanceof Definition) {
            // Find the TextViews in the list_itemwith the ID part_of_speech
            // and definition.
            TextView partOfSpeechTextView = listItemView.findViewById(R.id.word_attribute_type);
            TextView definitionTextView = listItemView.findViewById(R.id.word_attribute_content);

            // Get part of speech and definition from the currentDefinition object and set this text on
            // their respective TextView's.
            partOfSpeechTextView.setText(((Definition) currentItem).getPartOfSpeech());
            definitionTextView.setText(((Definition) currentItem).getText());
        }

        // Return the whole definitions list item layout (containing 2 TextViews) so that it can be
        // shown in the ListView.
        return listItemView;
    }
}
