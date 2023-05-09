package com.transpositor.controller;

import com.transpositor.logic.FileHandler;
import com.transpositor.logic.Transposition;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author G
 */
@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

//    @RequestMapping(value = "/upload", method = RequestMethod.GET)
//    public ModelAndView showUploadForm() {
//        return new ModelAndView("uploadForm");
//    }
    private XWPFDocument originalDoc;
    private String documentName;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes, Model model) throws IOException {
        originalDoc = FileHandler.readDocxFile(file.getInputStream());
        documentName = file.getOriginalFilename();
        ModelAndView mav = new ModelAndView("transpose");
        return mav;
    }

    @PostMapping("/transpose")
    public ModelAndView transpose(
            @RequestParam("transposeBy") int transposeBy,
            @RequestParam(name = "isFlatFrom", required = false, defaultValue
                    = "false") boolean isFlatFrom,
            @RequestParam(name = "isFlatTo", required = false, defaultValue
                    = "false") boolean isFlatTo,
            HttpServletResponse response) throws IOException {
        originalDoc = new Transposition(isFlatFrom, isFlatTo)
                .transposeChordsInDocx(originalDoc, transposeBy);
        ModelAndView mav = new ModelAndView("result");
        return mav;

    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        // MIME típus beállítása
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        // letöltési fájl nevének beállítása a korábbi fájl nevére
        String fileName = "transposed_" + originalDoc.getPackagePart().
                getPartName().
                getName() + ".docx";
        response.setHeader("Content-Disposition",
                "attachment; filename=" + documentName);

        try ( // letöltés a böngésző alapértelmezett letöltési helyére
                 ServletOutputStream outStream = response.getOutputStream()) {
            originalDoc.write(outStream);
            originalDoc.close();
        }
    }

    public static boolean hasText(XWPFDocument document) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (!paragraph.getText().
                    trim().
                    isEmpty()) {
                return true;
            }
        }
        return false;
    }

//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public ModelAndView handleFileUpload(
//            @RequestParam("file") MultipartFile file,
//            RedirectAttributes redirectAttributes, Model model) throws IOException {
//        XWPFDocument readIn = FileHandler.readDocxFile(file.getInputStream());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        readIn.write(outputStream);
//        byte[] byteArray = outputStream.toByteArray();
//        ModelAndView mav = new ModelAndView("transpose");
//        mav.addObject("doc", byteArray);
//        return mav;
//    }
//
//    @PostMapping("/transpose")
//    public ModelAndView transpose(
//            @ModelAttribute("doc") byte[] docBytes,
//            @RequestParam("transposeBy") int transposeBy,
//            @RequestParam(name = "isFlatFrom", required = false, defaultValue
//                    = "false") boolean isFlatFrom,
//            @RequestParam(name = "isFlatTo", required = false, defaultValue
//                    = "false") boolean isFlatTo) throws IOException {
//        XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(docBytes));
//        XWPFDocument newDoc = new Transposition(isFlatFrom, isFlatTo)
//                .transposeChordsInDocx(doc, transposeBy);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        newDoc.write(outputStream);
//        byte[] byteArray = outputStream.toByteArray();
//        ModelAndView mav = new ModelAndView("result");
//        mav.addObject("doc", Base64.getEncoder().
//                encodeToString(byteArray));
//        return mav;
//    }
//
//    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    public void downloadFile(HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        String docByteString = request.getParameter("doc");
//        byte[] docBytes = Base64.getDecoder().
//                decode(docByteString);
//        XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(docBytes));
//        System.out.println("download: " + hasText(doc));
//        // MIME típus beállítása
//        response.setContentType(
//                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//
//        // letöltési fájl nevének beállítása a korábbi fájl nevére
//        String fileName = "transposed_" + doc.getPackagePart().
//                getPartName().
//                getName() + ".docx";
//        response.setHeader("Content-Disposition",
//                "attachment; filename=" + fileName);
//
//        try ( // letöltés a böngésző alapértelmezett letöltési helyére
//                 ServletOutputStream outStream = response.getOutputStream()) {
//            doc.write(outStream);
//            doc.close();
//        }
//    }
}
