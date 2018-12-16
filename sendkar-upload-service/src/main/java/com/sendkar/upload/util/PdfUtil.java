package com.sendkar.upload.util;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.j256.simplemagic.ContentType;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;

public class PdfUtil {

    public static int efficientPDFPageCount(File file) throws IOException {
        ContentInfoUtil util = new ContentInfoUtil();
        File possiblePdfFile = new File("/path/to/possiblePdfFile.pdf");
        ContentInfo info = util.findMatch(possiblePdfFile);
        int count = 0;
        if(ContentType.PDF.equals(info.getContentType())) {
            PDDocument doc = PDDocument.load(file);
            count = doc.getNumberOfPages();
        } else {
            String mimetype = new MimetypesFileTypeMap().getContentType(file);
            String type = mimetype.split("/")[0];
            if (type.equals("image")) {
                count = 1;
            } else {
                count = 0;
            }
        }
        return count;
    }
}
