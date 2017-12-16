package com.solaomi.wordapp;

/**
 * {@link RelatedWord} represents a vocabulary word related to the word being looked up.
 * It contains the type (antonym/synoym) in relation to the word and the related word itself.
 */
public class RelatedWord {
    /** Relationship type of word */
    private String mRelatedType;
    /** Related word */
    private String mRelatedWord;

    /**
     * Create a new RelatedWord object.
     * @param relatedType is the relationship type of the word
     * @param relatedWord is the related word
     */
    public RelatedWord(String relatedType, String relatedWord) {
        mRelatedType = relatedType;
        mRelatedWord = relatedWord;
    }

    /**
     * Return the Type of the word.
     */
    public String getRelatedType() { return mRelatedType; }

    /**
     * Return the related word.
     */
    public String getRelatedWord() { return  mRelatedWord; }
}



