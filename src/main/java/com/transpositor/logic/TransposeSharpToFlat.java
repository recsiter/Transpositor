package com.transpositor.logic;

import java.util.Arrays;

/**
 * @author G
 */
public class TransposeSharpToFlat implements TransposeAccord {

    private final String[] SHARPNOTES
            = Util.SHARPNOTES;
    private final String[] FLATNOTES
            = Util.FLATNOTES;

    @Override
    public String transposeAccord(String note, int semitones) {

        int indexNote = Arrays.asList(SHARPNOTES).
                indexOf(note);
        indexNote += semitones * 2;
        if (indexNote < 0) {
            indexNote = (FLATNOTES.length) + indexNote;
        }
        return FLATNOTES[indexNote % 24];
    }
}
