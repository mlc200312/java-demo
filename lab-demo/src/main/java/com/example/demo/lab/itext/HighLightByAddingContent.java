package com.example.demo.lab.itext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2020-08-13
 **/
public class HighLightByAddingContent {

    public static final String SRC = "resources/pdfs/hello.pdf";
    public static final String DEST = "results/stamper/hello_highlighted.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new HighLightByAddingContent().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte canvas = stamper.getUnderContent(1);
        canvas.saveState();
        canvas.setColorFill(BaseColor.YELLOW);
        canvas.rectangle(36, 786, 66, 16);
        canvas.fill();
        canvas.restoreState();
        stamper.close();
        reader.close();
    }
}  