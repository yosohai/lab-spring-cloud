package com.chint;

import com.alibaba.fastjson.JSONArray;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;


public class MergePdf {
    public String mergePdfFiles(String[] files, String newfile) {
        Document document = null;
        String[] fileValue = new String[files.length];
        JSONArray jsonArray = new JSONArray();
        try {
            document = new Document(new PdfReader(files[0]).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
            document.open();
            //int pages = 0;
            for (int i = 0; i < files.length; i++) {
                if (files[i] != null && !("").equals(files[i])) {
                    String file = files[i];
                    PdfReader reader = new PdfReader(file);
                    int n = reader.getNumberOfPages();
                    //pages += n;
                    for (int j = 1; j <= n; j++) {
                        document.newPage();
                        PdfImportedPage page = copy.getImportedPage(reader, j);
                        copy.addPage(page);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("合并表单出错");
            e.printStackTrace();
        } finally {
            document.close();
        }
        for (int i = 0; i < fileValue.length; i++) {
            if (fileValue[i] != null) {
                new File(fileValue[i]).delete();
            }
        }
        return jsonArray.toString();
    }
}

