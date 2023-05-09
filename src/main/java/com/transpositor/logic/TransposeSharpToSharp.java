package com.transpositor.logic;

import java.util.Arrays;

/**
 * @author G
 */
public class TransposeSharpToSharp implements TransposeAccord {

    private final String[] SHARPNOTES
            = Util.SHARPNOTES;

    @Override
    public String transposeAccord(String note, int semitones) {
        int indexNote = Arrays.asList(SHARPNOTES).
                indexOf(note);
        indexNote += semitones * 2;
        if (indexNote < 0) {
            indexNote = (SHARPNOTES.length) + indexNote;
        }
        return SHARPNOTES[indexNote % 24];
    }
}
