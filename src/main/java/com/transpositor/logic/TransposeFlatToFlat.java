package com.transpositor.logic;

import java.util.Arrays;

/**
 * @author G
 */
public class TransposeFlatToFlat implements TransposeAccord {

    private final String[] FLATNOTES
            = Util.FLATNOTES;

    @Override
    public String transposeAccord(String note, int semitones) {
        int indexNote = Arrays.asList(FLATNOTES).
                indexOf(note);
        indexNote += semitones * 2;
        if (indexNote < 0) {
            indexNote = (FLATNOTES.length) + indexNote;
        }
        return FLATNOTES[indexNote % 24];
    }

}
