package com.chint;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;



public class ExceltoPdf {

//    public static void main(String[] args) {
//        excelToPDF("D:\\工作簿2.xlsx","D:\\3.pdf");
//    }

    public static void excelToPDF(String sourceFilePath, String destinPDFFilePath) {
        ComThread.InitSTA();
        Dispatch sheet = null;
        Dispatch sheets = null;
        //建立ActiveX部件
        ActiveXComponent excelCom = new ActiveXComponent("Excel.Application");
        //定义所有pdf的数组
        String outFiles[] = null;
        excelCom.setProperty("Visible", new Variant(false));
        //返回wrdCom.Documents的Dispatch
        Dispatch wordbooks = excelCom.getProperty("Workbooks").toDispatch();
        //调用excelCom.Documents.Open方法打开指定的excel文档，返回excelDoc
        //Dispatch wordbook = Dispatch.invoke(wordbooks, "Open", Dispatch.Method, new Object[] { sourceFilePath ,new Variant(false) ,new Variant(true)}, new int[3]).toDispatch();
        Dispatch wordbook = Dispatch.call(wordbooks, "Open", sourceFilePath, false,true).toDispatch();
        sheets= Dispatch.get(wordbook, "Sheets").toDispatch();
        int count = Dispatch.get(sheets, "Count").getInt();
        System.out.println("---------合并的excel的sheet个数："+count+"个-----------");
        outFiles = new String[count];
        for (int j = 1; j <= count; j++) {
            try {
                String outFile = "";
                sheet = Dispatch.invoke(sheets, "Item", Dispatch.Get,new Object[] { new Integer(j) }, new int[1]).toDispatch();
                String sheetname = Dispatch.get(sheet, "name").toString();
                Dispatch.call(sheet, "Activate");
                Dispatch.call(sheet, "Select");
                outFile = destinPDFFilePath.substring(0,destinPDFFilePath.length()-4) + sheetname + j +".pdf";
                Dispatch.call(wordbook, "ExportAsFixedFormat", 0, destinPDFFilePath);
                Dispatch.call(wordbook, "Close", false);
                excelCom.invoke("Quit");
                outFiles[j] = outFile;
            }catch (Exception ex) {
                //ex.printStackTrace();
                System.out.println("--------pdf合成---------");
            }
        }

		 		/*Dispatch.call(wordbook, "Close",new Variant(false));
		 		System.out.println("---------转换pdf成功---------");
		 		excelCom.invoke("Quit",new Variant[]{});*/
        ComThread.Release();
        MergePdf mp = new MergePdf();
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        // 合并正文附件的pdf和表单的pdf
        // 遍历 去掉 有问题的 文件
        int count2 = outFiles.length;
        for(int i = 0 ; i< outFiles.length;i++){
            if(outFiles[i]== null){
                count2 --;
            }
        }
        String outFiles2[] = null;
        outFiles2 = new String[count2];
        int  j = 0;
        for(int i = 0 ; i< outFiles.length;i++){
            if(outFiles[i]!= null){
                outFiles2[j] = outFiles[i];
                j++;
            }
        }
        //mp.mergePdfFiles(outFiles2, destinPDFFilePath);
        // 删除pdf
        for (String otf : outFiles2) {
            if(otf != null && !otf.equals("")){
                File file = new File(otf);
                if(file.exists()){
                    file.delete();
                }
            }
        }
    }
}

