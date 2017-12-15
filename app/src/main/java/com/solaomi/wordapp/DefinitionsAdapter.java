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
 * {@link DefinitionsAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * item based on a data source, which is a list of {@link Definition} objects.
 */
public class DefinitionsAdapter extends ArrayAdapter<Definition> {

    /**
     * Create a new {@link DefinitionsAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param definitions is the list of {@link Definition}s to be displayed.
     */
    public DefinitionsAdapter(Context context, List<Definition> definitions) {
        super(context, 0, definitions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.definitions_list_item, parent, false);
        }

        // Get the {@link Definition} object located at this position in the list.
        Definition currentDefinition = getItem(position);

        // Find the TextViews in the definitions_list_item.xml layout with the ID part_of_speech
        // and definition.
        TextView partOfSpeechTextView = listItemView.findViewById(R.id.part_of_speech);
        TextView definitionTextView = listItemView.findViewById(R.id.definition);

        // Get part of speech and definition from the currentDefinition object and set this text on
        // their respective TextView's.
        if (currentDefinition != null) {
            partOfSpeechTextView.setText(currentDefinition.getPartOfSpeech());
            definitionTextView.setText(currentDefinition.getText());
        } else {
            partOfSpeechTextView.setText(R.string.sample_part_of_speech);
            definitionTextView.setText(R.string.sample_definition);
        }

        // Return the whole definitions list item layout (containing 2 TextViews) so that it can be
        // shown in the ListView.
        return listItemView;
    }
}
