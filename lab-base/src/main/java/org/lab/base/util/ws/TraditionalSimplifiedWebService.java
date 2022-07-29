package org.lab.base.util.ws;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.webservice.SoapClient;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import java.util.List;

/**
 * 简繁互换
 *
 * @author lzqing
 * @date 2022-07-22
 * @vsrsion 1.0
 **/
public class TraditionalSimplifiedWebService {

    public static void main(String[] args) throws Exception {

        Excel07SaxReader reader = new Excel07SaxReader(createRowHandler());
        // 追踪类加载于哪个jar包 file:/D:/repository/org/apache/poi/poi-ooxml/5.2.2/poi-ooxml-5.2.2.jar
        System.out.println(XSSFReader.class.getProtectionDomain().getCodeSource().getLocation());
        reader.read("F:/创建文件夹结构.xlsx", 0);
        ExcelUtil.readBySax(FileUtil.file("F:/创建文件夹结构.xlsx"), 0, createRowHandler());
        SoapClient soapClient = SoapClient
                .create("http://www.webxml.com.cn/WebServices/TraditionalSimplifiedWebService.asmx?WSDL");
        QName qname = new QName("http://webxml.com.cn/", "toTraditionalChinese");
        soapClient.setMethod(qname);
//        soapClient.getMethodEle().addChildElement("sText").setValue("中国年加快速度放假开始");
        soapClient.getMethodEle().addTextNode("<sText>简繁转换666圣诞快乐非金属矿东风嘉实多看</sText>");
        Document document = XmlUtil.parseXml("<sText>简繁转换666圣诞快乐非金属矿东风嘉实多看</sText>");
        Element documentElement = document.getDocumentElement();
        Element rootElement = XmlUtil.getRootElement(document);
        System.out.println(rootElement.getTextContent());
        rootElement.getChildNodes();
        System.out.println(soapClient.getMsgStr(true));
        System.out.println(soapClient.send(true));

    }

    private static RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowList) {
                Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowList);
            }
        };
    }
}
@XmlRootElement(name = "book")
@XmlType(propOrder = {"author", "name", "publisher", "isbn"}) // Defining order
class Book {
    private String name;
    private String author;
    private String publisher;
    private String isbn;

    public Book() {
    }

    public Book(String name, String author, String publisher, String isbn) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }

    // Changing to title
    @XmlElement(name = "title")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("name='").append(name).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append(", isbn='").append(isbn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}