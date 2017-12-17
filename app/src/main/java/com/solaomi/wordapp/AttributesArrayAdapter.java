package com.solaomi.wordapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Definition;

import java.util.List;

/**
 * {@link AttributesArrayAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * item based on a data source, which is a list of {@link Definition} or {@link RelatedWord} objects.
 */
public class AttributesArrayAdapter<Type> extends ArrayAdapter<Type> {
//    private static final String LOG_TAG = AttributesArrayAdapter.class.getName();

    /**
     * Create a new {@link AttributesArrayAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param list is the list of objects to be displayed.
     */
    public AttributesArrayAdapter(Context context, List<Type> list) {
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

        // Find the TextViews in the list_item xml layout with the ID word_attribute_type
        // and word_attribute_content.
        TextView typeTextView = listItemView.findViewById(R.id.word_attribute_type);
        TextView contentTextView = listItemView.findViewById(R.id.word_attribute_content);

        // Check if the object is of type Definition
        if ( currentItem instanceof Definition) {
            // Get part of speech and definition from the currentItem object and set this text on
            // their respective TextView's.
            typeTextView.setText(((Definition) currentItem).getPartOfSpeech());
            contentTextView.setText(((Definition) currentItem).getText());
        }

        // Check if the object is of type RelatedWord
        if ( currentItem instanceof RelatedWord) {
            // Get the type and word from the currentItem object and set this text on
            // their respective TextView's.
            typeTextView.setText(((RelatedWord) currentItem).getRelatedType());
            contentTextView.setText(((RelatedWord) currentItem).getRelatedWord());
        }

        // Return the whole definitions list item layout (containing 2 TextViews) so that it can be
        // shown in the ListView.
        return listItemView;
    }
}
