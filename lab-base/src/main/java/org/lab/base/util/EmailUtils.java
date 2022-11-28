package org.lab.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件发送工具类
 */
public class EmailUtils {


    /**
     * 构建用于连接邮件服务器的参数配置
     *
     * @param obj (json字符串 JSONObject 或者 Map)
     * @return Properties
     */
    public static Properties buildPros(Object obj) {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.smtp.connectiontimeout", "15000"); // 与邮件服务器建立连接的时间，单位毫秒
        props.setProperty("mail.smtp.timeout", "15000"); // 邮件接收时间限制，单位毫秒
        props.setProperty("mail.smtp.writetimeout", "15000"); // 邮件发送时间限制，单位毫秒
        props.setProperty("mail.smtp.auth", "true");//开启认证
        JSONObject jsonConfig = null;
        // 支持 json字符串 Map JSONObject
        if (obj instanceof JSONObject) {
            jsonConfig = (JSONObject) obj;
        } else if (obj instanceof Map) {
            jsonConfig = JSON.parseObject(JSON.toJSONString((Map) obj));
        } else if (obj instanceof String) {
            jsonConfig = JSON.parseObject((String) obj);
        }

        String emailProtocol = (String) jsonConfig.getOrDefault("emailProtocol", "smtp");
        emailProtocol = emailProtocol.toLowerCase();
        String emailHost = (String) jsonConfig.getOrDefault("emailHost", "smtp.qiye.aliyun.com");
        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        boolean sslBool = (boolean) jsonConfig.getOrDefault("SSL", true);
        String smtpPort = (String) jsonConfig.getOrDefault("emailPort", "465");
        if (sslBool) {
            props.setProperty("mail.smtp.port", smtpPort);
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        }
        props.setProperty("mail.transport.protocol", emailProtocol);   // 设置使用的协议
        props.setProperty("mail.smtp.host", emailHost);   // 发件人的邮箱的 SMTP 服务器地址
        return props;
    }

    /**
     * 创建邮件（文本+附件）
     *
     * @param session    会话
     * @param sender     发件人
     * @param recipients 收件人列表
     * @param cc         抄送人列表
     * @param bcc        密抄人列表
     * @param subject    邮件主题
     * @param content    邮件内容
     * @param filePath   附件列表
     * @return MimeMessage
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sender, String[] recipients, String[] cc, String[] bcc,
                                                String subject, String content, String[] filePath) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sender, "", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        int len0 = 0;
        if (recipients != null && (len0 = recipients.length) > 0) {
            Address[] recipientArr = new InternetAddress[len0];
            for (int i = 0; i < len0; i++) {
                recipientArr[i] = new InternetAddress(recipients[i], "", "UTF-8");
            }
            message.addRecipients(MimeMessage.RecipientType.TO, recipientArr);
        }

        int ccLen = 0;
        if (cc != null && (ccLen = cc.length) > 0) {
            Address[] ccArr = new InternetAddress[ccLen];
            for (int i = 0; i < ccLen; i++) {
                ccArr[i] = new InternetAddress(cc[i], "", "UTF-8");
            }
            message.addRecipients(MimeMessage.RecipientType.CC, ccArr);
        }

        int bccLen = 0;
        if (bcc != null && (bccLen = bcc.length) > 0) {
            Address[] bccArr = new InternetAddress[bccLen];
            for (int i = 0; i < bccLen; i++) {
                bccArr[i] = new InternetAddress(bcc[i], "", "UTF-8");
            }
            message.addRecipients(MimeMessage.RecipientType.BCC, bccArr);
        }

        // 4. Subject: 邮件主题
        if (StringUtils.isNotBlank(subject)) {
            message.setSubject(subject, "UTF-8");
        }

        /*
         * 下面是邮件内容的创建:
         */
        // 5. 创建文本“节点”
        MimeBodyPart text = new MimeBodyPart();
        //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent(content, "text/html;charset=UTF-8");

        // 6. 设置（文本）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);

        // 7. 创建附件“节点”并添加
        int fileLen = 0;
        if (filePath != null && (fileLen = filePath.length) > 0) {
            for (int i = 0; i < fileLen; i++) {
                MimeBodyPart attachment = new MimeBodyPart();
                DataHandler dh = new DataHandler(new FileDataSource(filePath[i]));  // 读取本地文件
                attachment.setDataHandler(dh);                                                // 将附件数据添加到“节点”
                attachment.setFileName(MimeUtility.encodeText(dh.getName()));                // 设置附件的文件名（需要编码）
                mm.addBodyPart(attachment);        // 如果有多个附件，可以创建多个多次添加
            }
        }

        mm.setSubType("mixed");            // 混合关系

        // 8. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);

        // 9. 设置发件时间
        message.setSentDate(new Date());

        // 10. 保存上面的所有设置
        message.saveChanges();

        return message;
    }
}
