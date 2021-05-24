package com.example.demo.lab.itext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 替换PDF文件某个区域内的文本
 * @Author: Mr.Min
 * @Date: 2020-08-13
 **/
public class PdfReplacer {
    private static final Logger logger = LoggerFactory.getLogger(PdfReplacer.class);

    private int fontSize;
    private Map<String, ReplaceRegion> replaceRegionMap = new HashMap<>();
    private Map<String, Object> replaceTextMap = new HashMap<>();
    private ByteArrayOutputStream output;
    private PdfReader reader;
    private PdfStamper stamper;
    private PdfContentByte canvas;
    private Font font;

    public PdfReplacer(byte[] pdfBytes) throws DocumentException, IOException {
        init(pdfBytes);
    }

    public PdfReplacer(String fileName) throws IOException, DocumentException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileName);
            byte[] pdfBytes = new byte[in.available()];
            in.read(pdfBytes);
            init(pdfBytes);
        } finally {
            in.close();
        }
    }

    public PdfReplacer(URL url) throws IOException, DocumentException {
        init(url);
    }

    private void init(URL url) throws DocumentException, IOException {
        init(new PdfReader(url));
    }

    private void init(byte[] pdfBytes) throws DocumentException, IOException {
        init(new PdfReader(pdfBytes));
    }

    private void init(PdfReader pdfReader) throws DocumentException, IOException {
        logger.info("初始化开始");
        reader = pdfReader;
        output = new ByteArrayOutputStream();
        stamper = new PdfStamper(reader, output);
        canvas = stamper.getOverContent(1);
        setFont(10);
        logger.info("初始化成功");
    }

    private void close() throws DocumentException, IOException {
        if (reader != null) {
            reader.close();
        }
        if (output != null) {
            output.close();
        }

        output = null;
        replaceRegionMap = null;
        replaceTextMap = null;
    }

    public void replaceText(float x, float y, float w, float h, String text) {
        //用文本作为别名
        ReplaceRegion region = new ReplaceRegion(text);
        region.setH(h);
        region.setW(w);
        region.setX(x);
        region.setY(y);
        addReplaceRegion(region);
        this.replaceText(text, text);
    }

    public void replaceText(String name, String text) {
        this.replaceTextMap.put(name, text);
    }

    /**
     * 替换文本
     *
     * @throws DocumentException
     * @throws IOException
     */
    private void process() throws DocumentException, IOException {
        try {
            parseReplaceText();
            canvas.saveState();
            Set<Map.Entry<String, ReplaceRegion>> entries = replaceRegionMap.entrySet();
            for (Map.Entry<String, ReplaceRegion> entry : entries) {
                ReplaceRegion value = entry.getValue();

                canvas.setColorFill(BaseColor.WHITE);

                canvas.rectangle(value.getX(), value.getY(), value.getW(), value.getH());
            }
            canvas.fill();
            canvas.restoreState();
            //开始写入文本   
            canvas.beginText();
            for (Map.Entry<String, ReplaceRegion> entry : entries) {
                ReplaceRegion value = entry.getValue();
                //设置字体  
                canvas.setFontAndSize(font.getBaseFont(), getFontSize());
                //修正背景与文本的相对位置
                canvas.setTextMatrix(value.getX(), value.getY() + 2);
                canvas.showText((String) replaceTextMap.get(value.getAliasName()));
            }
            canvas.endText();
        } finally {
            if (stamper != null) {
                stamper.close();
            }
        }
    }

    /**
     * 未指定具体的替换位置时，系统自动查找位置
     */
    private void parseReplaceText() {
        PdfPositionParse parse = new PdfPositionParse(reader);
        Set<Map.Entry<String, Object>> entries = this.replaceTextMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (this.replaceRegionMap.get(entry.getKey()) == null) {
                parse.addFindText(entry.getKey());
            }
        }

        try {
            Map<String, ReplaceRegion> parseResult = parse.parse();
            if (null != parseResult) {
                Set<Map.Entry<String, ReplaceRegion>> parseEntries = parseResult.entrySet();
                for (Map.Entry<String, ReplaceRegion> entry : parseEntries) {
                    if (entry.getValue() != null) {
                        this.replaceRegionMap.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 生成新的PDF文件
     *
     * @param fileName
     * @throws DocumentException
     * @throws IOException
     */
    public void toPdf(String fileName) throws DocumentException, IOException {
        FileOutputStream fileOutputStream = null;
        try {
            process();
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(output.toByteArray());
            fileOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            close();
        }
        logger.info("文件生成成功");
    }

    /**
     * 将生成的PDF文件转换成二进制数组
     *
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public byte[] toBytes() throws DocumentException, IOException {
        try {
            process();
            logger.info("二进制数据生成成功");
            return output.toByteArray();
        } finally {
            close();
        }
    }

    /**
     * 添加替换区域
     *
     * @param replaceRegion
     */
    public void addReplaceRegion(ReplaceRegion replaceRegion) {
        this.replaceRegionMap.put(replaceRegion.getAliasName(), replaceRegion);
    }

    /**
     * 通过别名得到替换区域
     *
     * @param aliasName
     * @return
     */
    public ReplaceRegion getReplaceRegion(String aliasName) {
        return this.replaceRegionMap.get(aliasName);
    }

    public int getFontSize() {
        return fontSize;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize
     * @throws DocumentException
     * @throws IOException
     */
    public void setFont(int fontSize) throws DocumentException, IOException {
        if (fontSize != this.fontSize) {
            this.fontSize = fontSize;
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            font = new Font(bf, this.fontSize, Font.BOLD);
        }
    }

    public void setFont(Font font) {
        if (font == null) {
            throw new NullPointerException("font is null");
        }
        this.font = font;
    }

    public static void main(String[] args) throws IOException, DocumentException {
//        String filePath = "/Users/liangchaomin/Mr.Min/Develop/code/min/java-demo/laboratory-demo/src/main/resources/借款合同-中文版V2.0.pdf";
//        PdfPositionParse positionParse = new PdfPositionParse(filePath);
//        positionParse.addFindText("合同编号");
//        Map<String, ReplaceRegion> parse = positionParse.parse();
//        System.out.println(parse);
//
//        PdfReplacer textReplacer = new PdfReplacer("/Users/liangchaomin/Mr.Min/Develop/code/min/java-demo/laboratory-demo/src/main/resources/借款合同-中文版V2.0.pdf");
//        textReplacer.replaceText(195.9505462646484F, 726.2191772460938F, 9.645508F, 0.0F, "闵良超");
//        textReplacer.replaceText(150.95055F, 739.8192F, 52.559235F, 12.0F, "176623434");
//        textReplacer.toPdf("/Users/liangchaomin/Downloads/借款合同.pdf");

        String url = "https://useevip-img-test-1300551753.cos.ap-beijing.myqcloud.com/others/boan_contract_V1.0.pdf";

        PdfReplacer replacer = new PdfReplacer(new URL(url));

        replacer.replaceText(195.9505462646484F, 726.2191772460938F, 9.645508F, 0.0F, "闵良超");
        replacer.replaceText(150.95055F, 739.8192F, 52.559235F, 12.0F, "176623434");
        replacer.toPdf("/Users/liangchaomin/Downloads/借款合同.pdf");
    }
}  