package com.transpositor.logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author G
 */
public class Util {

    public static final String[] SHARPNOTES
            = {"C", "c", "C#", "c#", "D", "d", "D#", "d#", "E", "e", "F", "f", "F#", "f#", "G", "g", "G#", "g#", "A", "a", "A#", "a#", "H", "h"};
    public static final String[] FLATNOTES
            = {"C", "c", "Db", "db", "D", "d", "Eb", "eb", "E", "e", "F", "f", "Gb", "gb", "G", "g", "Ab", "ab", "A", "a", "Hb", "hb", "H", "h"};
    public static final String[] SPACIALCHARACTERS = {"#", "7", "/", "|"};
    private static final double WHITESPACERELATION = 0.6;
    public static HashMap<String, String> CORRECTNOTES
            = new HashMap<String, String>() {
        {
            put("Cisz", "C#");
            put("Disz", "D#");
            put("Fisz", "F#");
            put("Gisz", "G#");
            put("Aisz", "A#");
            put("cisz", "c#");
            put("disz", "d#");
            put("fisz", "f#");
            put("gisz", "g#");
            put("aisz", "a#");
            put("Esz", "Eb");
            put("Gesz", "Gb");
            put("Asz", "Ab");
            put("B", "H");
            put("Bb", "Hb");
            put("esz", "eb");
            put("gesz", "gb");
            put("asz", "ab");
            put("asz", "ab");

        }
    };

    public static boolean isAccordRow(String text) {
//        System.out.println("---" + text + "---");
        return calculateRelation(text) <= WHITESPACERELATION || Arrays.stream(
                SPACIALCHARACTERS).
                anyMatch(target -> text.contains(target));

    }

    private static double calculateRelation(String text) {
        double whiteSpace = 0.0;
        double nonWhiteSpace = 0.0;

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            if (Character.isWhitespace(letter)) {
                whiteSpace++;
            } else {
                nonWhiteSpace++;
            }
        }
        System.out.println("arány: " + nonWhiteSpace / whiteSpace
        );
        return nonWhiteSpace / whiteSpace;
    }
}
