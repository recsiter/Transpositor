package com.transpositor.logic;

import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.xml.sax.SAXException;

/**
 * @author G
 */
public class FileHandler {

    public static XWPFDocument readDocxFile(InputStream input) throws IOException {
        XWPFDocument document = new XWPFDocument(input);
        input.close();
        return document;
    }

    public static void writeToDocx(XWPFDocument document, String path) throws IOException {
        // Write the modified document to a file
        FileOutputStream fos = new FileOutputStream(path);
        document.write(fos);
        fos.close();
    }

    public static byte[] readDocxFileArray(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        fis.close();
        baos.flush(); // flush a ByteArrayOutputStream
        baos.close(); // close a ByteArrayOutputStream
        return baos.toByteArray();
    }

    public static void writeToDocxArray(byte[] data, String path) throws IOException {
        // Create a new document
        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(data));

        // Modify the document as needed (e.g. add or modify text, formatting, etc.)
        // Write the modified document to a byte array
        FileOutputStream fos = new FileOutputStream(path);
        document.write(fos);

    }
}
