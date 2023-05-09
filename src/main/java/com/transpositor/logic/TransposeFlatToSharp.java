package com.transpositor.logic;

import java.util.Arrays;

/**
 * @author G
 */
public class TransposeFlatToSharp implements TransposeAccord {

    private final String[] SHARPNOTES
            = Util.SHARPNOTES;
    private final String[] FLATNOTES
            = Util.FLATNOTES;

    @Override
    public String transposeAccord(String note, int semitones) {

        int indexNote = Arrays.asList(FLATNOTES).
                indexOf(note);
        indexNote += semitones * 2;
        if (indexNote < 0) {
            indexNote = (SHARPNOTES.length) + indexNote;
        }
        return SHARPNOTES[indexNote % 24];
    }
}
