package com.transpositor.logic;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author G
 */
public class Transposition {

    private final boolean ISFLATKEYFROM;
    private final boolean ISFLATKEYTO;

    public Transposition(boolean ISFLATKEYFROM, boolean ISFLATKEYTO) {
        this.ISFLATKEYFROM = ISFLATKEYFROM;
        this.ISFLATKEYTO = ISFLATKEYTO;
    }

    public boolean isISFLATKEYFROM() {
        return ISFLATKEYFROM;
    }

    public boolean isISFLATKEYTO() {
        return ISFLATKEYTO;
    }

    public static final String[] FLATKEY
            = {"F", "Hb", "Eb", "Ab", "Db", "d", "g", "c", "f", "ab", "db"};

    private static String chordRegex
            = "(?<=[/\\s(]|^)(c#|cb|c|C#|Cb|C|d#|db|d|Db|D#|D|eb|e|Eb|E|f#|f|F#|F|g#|gb|g|Gb|G#|G|a#|ab|a|Ab|A#|A|hb|h|H|Hb)";

//// Szöveg beolvasása byte tömbbe
//    public static void transposition(byte[] docxData, int transpositionDistence) {
//        String text = new String(docxData, StandardCharsets.UTF_8);
//
//// Az akkordok transzponálása
//    }
    public static boolean isFlatKey(String accord) {
        return Arrays.asList(FLATKEY).
                contains(accord);
    }

    public XWPFDocument transposeChordsInDocx(XWPFDocument doc, int semitones) {
        TransposeAccord transposeAccord;
        if (ISFLATKEYFROM && ISFLATKEYTO) {
            transposeAccord = new TransposeFlatToFlat();
        } else if (!ISFLATKEYFROM && !ISFLATKEYTO) {
            transposeAccord = new TransposeSharpToSharp();
        } else if (ISFLATKEYFROM && !ISFLATKEYTO) {
            transposeAccord = new TransposeFlatToSharp();
        } else if (!ISFLATKEYFROM && ISFLATKEYTO) {
            transposeAccord = new TransposeSharpToFlat();
        } else {
            throw new IllegalArgumentException(
                    "Rossz típusok a transposition osztályban");
        }
        correctNoteNames(doc, Util.CORRECTNOTES);
        Pattern pattern = Pattern.compile(chordRegex);
        for (XWPFParagraph para : doc.getParagraphs()) {
            for (XWPFRun run : para.getRuns()) {
                String text = run.getText(0);
                if (run.getText(0) == null || run.getText(0).
                        trim().
                        isEmpty()) {
                    continue;
                }
                if (text != null && run.isBold()) {
                    Matcher matcher = pattern.matcher(text);
                    int index = 0;
                    StringBuilder newText = new StringBuilder();
                    while (matcher.find()) {
                        int start = matcher.start();
                        int end = matcher.end();

                        String chord = matcher.group();
                        String transposedChord = transposeAccord.
                                transposeAccord(chord, semitones);
                        newText.append(text.substring(index, start));
                        newText.append(transposedChord);
                        index = end;
                    }
                    newText.append(text.substring(index));
                    run.setText("", 0); // remove the original text
                    run.setText(newText.toString(), 0); // set the new text
                }
            }
        }

        return doc;

    }

    public static void correctNoteNames(XWPFDocument doc,
            HashMap<String, String> map) {
        for (XWPFParagraph para : doc.getParagraphs()) {
            for (XWPFRun run : para.getRuns()) {
                String text = run.getText(0);
                if (text != null && run.isBold()) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        replaceTinySharpAndFlat(text);
                        text = text.replaceAll(key, value);
                    }
                    run.setText(text, 0);
                }
            }
        }
    }

    public static void replaceTinySharpAndFlat(String text) {
        text.replace("#", "♯"); // karaktercserélés
        text.replaceAll("\u266d", "b");
    }

}
