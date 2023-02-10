package doc.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.deepoove.poi.XWPFTemplate;
import doc.utils.BrowserUtil;
import doc.utils.IotFileUtil;
import doc.utils.ParamUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SignatureController {

    private static final String SAVE_PATH_DOC = "D:" + File.separator + "file" + File.separator + "doc" + File.separator + "";
    private static final String SAVE_PATH_EXCEL = "D:" + File.separator + "file" + File.separator + "excel" + File.separator + "";


    /**
     * xls的ContentType
     */
    public static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

    /**
     * xlsx的ContentType
     */
    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @RequestMapping(value = "/word/signature")
    public void wordSignature(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final Map<String, String> map = new HashMap<>();
        map.put("sign1", "丁振.png");
        map.put("sign2", "叶怀花.png");
        String dateTime = "20221009";
        HashMap<String, Object> hashMap = ParamUtil.getWordFiller("dateTime1", dateTime, map);
        String fileName = String.format("设计任务书模板(软件)-%s.docx", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        String agent = request.getHeader("User-Agent").toLowerCase();
        String browserName = BrowserUtil.getBrowserName(agent);

        String downFileName;

        boolean isMSIE = browserName.indexOf("ie") != -1;
        if (isMSIE) {
            downFileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        } else {
            downFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }
        IotFileUtil.ensureFileExist(SAVE_PATH_DOC);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=\"" + downFileName + "\"");
        XWPFTemplate template = null;
        try (FileOutputStream outputStream = new FileOutputStream(SAVE_PATH_DOC + fileName); OutputStream out = response.getOutputStream();
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            template = XWPFTemplate.compile(new ClassPathResource("tml/设计任务书模板(软件).docx").getStream()).render(hashMap);
            template.write(outputStream);
            template.write(bos);
            outputStream.flush();
            bos.flush();
            out.flush();
            template.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (template != null) {
                template.close();
            }
        }
    }


    /**
     * excel填充
     */
    @RequestMapping(value = "/excel/signature")
    public void excelFill(HttpServletRequest request, HttpServletResponse response) throws Exception {

        final Map<String, String> param = new HashMap<>();
        param.put("sign1", "丁振.png");
        param.put("sign2", "叶怀花.png");
        String dateTime = "20221009";

        HashMap<String, Object> fillerMap = ParamUtil.getExcelFiller("dateTime1", dateTime, param);
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName = new ClassPathResource("tml/2022(ZTY)-1279.xls").getAbsolutePath();
        IotFileUtil.ensureFileExist(SAVE_PATH_EXCEL);
        String fileName = String.format("2022(ZTY)-1279-%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        // 根据Map填充
        String fullFileName = SAVE_PATH_EXCEL + String.format("2022(ZTY)-1279-%s.xls", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        ExcelWriter excelWriter = EasyExcel.write(fullFileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.fill(fillerMap, writeSheet);
        excelWriter.finish();

        if (fileName.toUpperCase().endsWith(".XLS")) {
            response.setContentType(this.XLS_CONTENT_TYPE);
        } else if (fileName.toUpperCase().endsWith(".XLSX")) {
            response.setContentType(this.XLSX_CONTENT_TYPE);
        }
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode可以防止中文乱码
        String downFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + downFileName);
        EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).sheet().doFill(fillerMap);
    }
}
