package com.lab.cas.util;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * SFTP工具类
 *
 * @author lzqing
 * @version 1.0
 * @date 2022-8-25
 */
public class SFtpUtils {

    private static Logger logger = LoggerFactory.getLogger(SFtpUtils.class);

    // FTP名称
    private String ftpName;
    // FTP 登录用户名
    private String username;
    // FTP 登录密码
    private String password;
    // FTP 服务器地址IP地址
    private String host;
    // FTP 端口
    private int port;
    // 属性集
    private ResourceBundle property = null;
    // 配置文件的路径名
    private String configFile = "classpath:ext-test.properties";
    // 编码格式
    private String encoding = "utf-8";
    // 根目录
    private String homeDir = "/order";

    private ChannelSftp sftp = null;
    private Session sshSession = null;

    public SFtpUtils(String ftpName) {
        this.ftpName = ftpName;
        setArg(configFile);
    }

    public static void main(String[] args) {
        SFtpUtils sftp = null;
        // 本地存放地址
        String localPath = "E:\\logs\\";
        // Sftp下载路径
        String sftpPath = "/SFTP_FILES/TEST/";
        try {
            sftp = new SFtpUtils("mes.");
            sftp.createDir("order");
/*            sftp.uploadFile(new FileInputStream(new File(localPath + "logs.txt")), "logs.txt", sftpPath);
            sftp.downFile("/SFTP_FILES/TEST/", "logs.txt", "E:\\logs\\", "logs2.txt");
            sftp.writeToFtp("/SFTP_FILES/TEST/logs1.txt", "有新的特殊订单待处理。");
            StringBuffer sb = sftp.readFile("/SFTP_FILES/TEST/logs1.txt");
            System.out.println(sb.toString());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置参数
     *
     * @param configFile 参数的配置文件
     */
    private void setArg(String configFile) {
        try {
            property = ResourceBundle.getBundle(configFile);
            username = property.getString(ftpName + "sftpUserName");
            password = property.getString(ftpName + "sftpPassword");
            host = property.getString(ftpName + "sftpHost");
            port = Integer.parseInt(property.getString(ftpName + "sftpPort"));
        } catch (Exception e) {
            System.out.println("配置文件 " + configFile + " 无法读取！");
        }
    }

    /**
     * 通过SFTP连接服务器
     */
    private void connect() {
        try {
            JSch jsch = new JSch();
            sshSession = jsch.getSession(username, host, port);
            if (logger.isInfoEnabled()) {
                logger.info("Session created.");
            }
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            if (logger.isInfoEnabled()) {
                logger.info("Session connected.");
            }
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            if (logger.isInfoEnabled()) {
                logger.info("Opening Channel.");
            }
            sftp = (ChannelSftp) channel;
            if (logger.isInfoEnabled()) {
                logger.info("Connected to " + host + ".");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    private void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                if (logger.isInfoEnabled()) {
                    logger.info("sftp is closed already");
                }
            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                if (logger.isInfoEnabled()) {
                    logger.info("sshSession is closed already");
                }
            }
        }
    }

    /**
     * 批量下载文件
     *
     * @param remotePath：远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
     * @param localPath：本地保存目录(以路径符号结束,D:\Duansha\sftp\)
     * @param fileFormat：下载文件格式(以特定字符开头,为空不做检验)
     * @param fileEndFormat：下载文件格式(文件格式)
     * @param del：下载后是否删除sftp文件
     * @return
     */
    public List<String> batchDownFile(String remotePath, String localPath, String fileFormat, String fileEndFormat, boolean del) {
        List<String> filenames = new ArrayList<String>();
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }
            Vector v = listFiles(remotePath);
            if (v.size() > 0) {
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    LsEntry entry = (LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        boolean flag = false;
                        String localFileName = localPath + filename;
                        fileFormat = fileFormat == null ? "" : fileFormat.trim();
                        fileEndFormat = fileEndFormat == null ? "" : fileEndFormat.trim();
                        // 三种情况
                        if (fileFormat.length() > 0 && fileEndFormat.length() > 0) {
                            if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat)) {
                                flag = downFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else if (fileFormat.length() > 0 && "".equals(fileEndFormat)) {
                            if (filename.startsWith(fileFormat)) {
                                flag = downFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else if (fileEndFormat.length() > 0 && "".equals(fileFormat)) {
                            if (filename.endsWith(fileEndFormat)) {
                                flag = downFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else {
                            flag = downFile(remotePath, filename, localPath, filename);
                            if (flag) {
                                filenames.add(localFileName);
                                if (flag && del) {
                                    deleteSFTP(remotePath, filename);
                                }
                            }
                        }
                    }
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("download file is success:remotePath=" + remotePath + "and localPath=" + localPath + ",file size is" + v.size());
            }
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            if (_flag)
                disconnect();
        }
        return filenames;
    }

    /**
     * 下载单个文件
     *
     * @param remotePath：远程下载目录(以路径符号结束)
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public boolean downFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        FileOutputStream fieloutput = null;
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }
            File file = new File(localPath + localFileName);
            fieloutput = new FileOutputStream(file);
            sftp.get(homeDir + remotePath + remoteFileName, fieloutput);
            if (logger.isInfoEnabled()) {
                logger.info("DownloadFile:" + remoteFileName + " success from sftp.");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fieloutput) {
                try {
                    fieloutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (_flag)
                disconnect();
        }
        return false;
    }

    /**
     * 下载单个文件
     *
     * @param remotePath：远程下载目录
     * @param remoteFileName：下载文件名
     * @param response
     * @return
     */
    public boolean downFile(String remotePath, String remoteFileName, HttpServletResponse response) {
        OutputStream outputStream = null;
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }
            // 输出文件流
            response.reset(); // 非常重要
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(remoteFileName.getBytes("GBK"), "ISO-8859-1"));

            outputStream = response.getOutputStream();
            sftp.get(homeDir + remotePath, outputStream);
            outputStream.flush();
            outputStream.close();
            if (logger.isInfoEnabled()) {
                logger.info("DownloadFile:" + remoteFileName + " success from sftp.");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (_flag)
                disconnect();
        }
        return false;
    }

    /**
     * 读取文件
     *
     * @param remoteFileName
     * @return
     */
    public StringBuffer readFile(String remoteFileName) {
        StringBuffer resultBuffer = new StringBuffer();
        InputStream in = null;
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }

            in = sftp.get(homeDir + remoteFileName);
            if (in == null) return resultBuffer;

            BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding));
            String data = null;
            try {
                while ((data = br.readLine()) != null) {
                    resultBuffer.append(data + "\n");
                }
            } catch (IOException e) {
                logger.error("文件读取错误。");
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("本地文件下载失败！", e);
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
//				e.printStackTrace();
            }
            if (_flag)
                disconnect();
        }
        return resultBuffer;
    }

    /**
     * 写入字符串到FTP文件
     *
     * @param path
     * @param content
     */
    public void writeToFtp(String path, String content) throws Exception {
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }

            String remoteFileName = path.substring(path.lastIndexOf("/") + 1);
            String remotePath = path.substring(0, path.lastIndexOf("/") + 1);

            InputStream in = new ByteArrayInputStream(content.getBytes(encoding));
            uploadFile(in, remoteFileName, remotePath);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件写入失败！", e);
        } finally {
            if (_flag)
                disconnect();
        }
    }

    /**
     * 上传单个文件
     *
     * @param remotePath：远程保存目录
     * @param remoteFileName：保存文件名
     * @return
     */
    public boolean uploadFile(InputStream in, String remoteFileName, String remotePath) {
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }
            createDir(remotePath);
            sftp.put(in, remoteFileName);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (_flag)
                disconnect();
        }
        return false;
    }

    /**
     * 批量上传文件
     *
     * @param remotePath：远程保存目录
     * @param localPath：本地上传目录(以路径符号结束)
     * @param del：上传后是否删除本地文件
     * @return
     */
    public boolean bacthUploadFile(String remotePath, String localPath, boolean del) {
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && files[i].getName().indexOf("bak") == -1) {
                    if (this.uploadFile(new FileInputStream(files[i]), files[i].getName(), remotePath) && del) {
                        deleteFile(localPath + files[i].getName());
                    }
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("upload file is success:remotePath=" + remotePath + "and localPath=" + localPath + ",file size is " + files.length);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (_flag)
                this.disconnect();
        }
        return false;

    }

    /**
     * 删除本地文件
     *
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        if (!file.isFile()) {
            return false;
        }
        boolean rs = file.delete();
        if (rs && logger.isInfoEnabled()) {
            logger.info("delete file success from local.");
        }
        return rs;
    }

    /**
     * 创建目录
     *
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath) {
        boolean _flag = false;
        createpath = homeDir + createpath;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }
            if (isDirExist(createpath)) {
                this.sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }

            }
            this.sftp.cd(createpath);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            if (_flag)
                disconnect();
        }
        return false;
    }

    /**
     * 判断目录是否存在
     *
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        } finally {
            if (_flag)
                disconnect();
        }
        return isDirExistFlag;
    }

    /**
     * 删除stfp文件
     *
     * @param directory：要删除文件所在目录
     * @param deleteFile：要删除的文件
     */
    public void deleteSFTP(String directory, String deleteFile) {
        boolean _flag = false;
        try {
            if (!isConnect()) {
                connect();
                _flag = true;
            }
            sftp.rm(homeDir + directory + deleteFile);
            if (logger.isInfoEnabled()) {
                logger.info("delete file success from sftp.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (_flag)
                disconnect();
        }
    }

    /**
     * 如果目录不存在就创建目录
     *
     * @param path
     */
    public void mkdirs(String path) {
        File f = new File(path);

        String fs = f.getParent();

        f = new File(fs);

        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory：要列出的目录
     * @return Vector
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException {
        boolean _flag = false;
        if (!isConnect()) {
            connect();
            _flag = true;
        }
        Vector ls = sftp.ls(homeDir + directory);
        if (_flag)
            disconnect();
        return ls;
    }

    public boolean isConnect() {
        return (this.sftp != null && this.sftp.isConnected() && this.sshSession != null && this.sshSession.isConnected());
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }
}