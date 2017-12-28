package com.solaomi.wordapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.jeremybrooks.knicker.dto.Definition;
import net.jeremybrooks.knicker.dto.Example;

import java.util.List;

/**
 * {@link AttributesArrayAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * item based on a data source, which is a list of {@link Definition} or {@link RelatedWord} objects.
 */
public class AttributesArrayAdapter<Type> extends ArrayAdapter<Type> {

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

        String currentTypeText;
        String currentContentText;
        int currentTypeColor;

        // Check if the object is of type Definition
        if ( currentItem instanceof Definition) {
            // Get the type and word from the currentItem object and set this text on
            // their respective TextView's.
            currentTypeText = ((Definition) currentItem).getPartOfSpeech();
            currentTypeText = getGeneralPartOfSpeech(currentTypeText);
            currentContentText =  ((Definition) currentItem).getText();
            currentTypeColor = getRelatedTypeColor(currentTypeText);

            typeTextView.setText(currentTypeText);
            typeTextView.setBackgroundColor(currentTypeColor);
            contentTextView.setText(currentContentText);
        }

        // Check if the object is of type RelatedWord
        if ( currentItem instanceof RelatedWord) {
            // Get the type and word from the currentItem object and set this text on
            // their respective TextView's.
            currentTypeText = ((RelatedWord) currentItem).getRelatedType();
            currentContentText = ((RelatedWord) currentItem).getRelatedWord();
            currentTypeColor = getRelatedTypeColor(currentTypeText);

            typeTextView.setText(currentTypeText);
            typeTextView.setBackgroundColor(currentTypeColor);
            contentTextView.setText(currentContentText);
        }

        // Check if the object is of type Example
        if ( currentItem instanceof Example) {
            // Get the word from the currentItem object and set this text on
            // its respective TextView's and make the type TextView invisible.
            currentContentText = ((Example) currentItem).getText();

            typeTextView.setVisibility(View.GONE);
            contentTextView.setText(currentContentText);
        }

        // Return the whole definitions list item layout (containing 2 TextViews) so that it can be
        // shown in the ListView.
        return listItemView;
    }

    private int getRelatedTypeColor(String type) {
        int relatedTypeColorResourceId;
        switch (type) {
            case "antonym":
                relatedTypeColorResourceId = R.color.antonym;
                break;
            case "synonym":
                relatedTypeColorResourceId = R.color.synonym;
                break;
            case "noun":
                relatedTypeColorResourceId = R.color.noun;
                break;
            case "pronoun":
                relatedTypeColorResourceId = R.color.pronoun;
                break;
            case "adjective":
                relatedTypeColorResourceId = R.color.adjective;
                break;
            case "verb":
                relatedTypeColorResourceId = R.color.verb;
                break;
            case "adverb":
                relatedTypeColorResourceId = R.color.adverb;
                break;
            case "preposition":
                relatedTypeColorResourceId = R.color.preposition;
                break;
            case "conjuction":
                relatedTypeColorResourceId = R.color.conjunction;
                break;
            case "interjection":
                relatedTypeColorResourceId = R.color.interjection;
                break;
            default:
                relatedTypeColorResourceId = R.color.defaultColor;
                break;
        }
        return ContextCompat.getColor(getContext(), relatedTypeColorResourceId);
    }

    // Returns general form of specialized part of speech
    private String getGeneralPartOfSpeech(String partOfSpeech) {
        String res;
        switch (partOfSpeech) {
            case "noun-plural":
            case "noun-posessive":
            case "proper-noun":
            case "proper-noun-plural":
            case "proper-noun-possessive":
                res = getContext().getString(R.string.noun);
                break;
            case "verb-transitive":
            case "verb-intransitive":
                res = getContext().getString(R.string.verb);
                break;
            default:
                res = partOfSpeech;
        }
        return res;
    }
}
