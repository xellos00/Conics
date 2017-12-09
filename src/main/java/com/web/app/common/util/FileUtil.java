package com.web.app.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.web.app.common.commonMap.CommonMap;

public class FileUtil {
    
    protected static Log log = LogFactory.getLog(FileUtil.class);
    
    private MultipartFile fileItem;
    
    private String newFileName;
    
    /**
     * <p>
     * FormFile Ŭ������ �����ڷν� FileItem ��ü�� �����Ѵ�.
     * </p>
     *
     * @param fileItem
     *            ���� ���ε� ������Ʈ�κ��� ������� FileItem ��ü
     * @param key
     *            FileItem ��ü�� ���� Ű ��
     */
    public FileUtil(MultipartFile fileItem) {
        this.fileItem = fileItem;
    }
    
    /**
     * <p>
     * ���ε� ���Ͽ� ���� ����Ʈ Ÿ���� �����Ѵ�.
     * </p>
     *
     * @return ���ε� ������ ����Ʈ Ÿ�Կ� ���� ���ڿ�
     */
    public String getContentType() {
        return fileItem.getContentType();
    }
    
    /**
     * <p>
     * ������ ����� �����Ѵ�.
     * </p>
     *
     * @return ���� ������
     */
    public int getFileSize() {
        return (int) fileItem.getSize();
    }
    
    /**
     * <p>
     * ������ �̸��� �����Ѵ�.(��θ��� ��ȸ��Ų ���ϸ� ���ϵȴ�.)
     * </p>
     * 
     * @return ���ϸ�
     */
    public String getOriginalFilename() {
        return fileItem.getOriginalFilename();
    }
    
    /**
     * <p>
     * ������ ������ byte[] ���·� �����Ѵ�.
     * </p>
     *
     * @return ������ ����(byte[])
     * @throws FileNotFoundException
     *             �ش� ������ ã�� ���ϴ� ���
     * @throws IOException
     *             �ش� ���Ϸ� ���� ������ ���� ���ϴ� ���
     */
    public byte[] getFileData() throws FileNotFoundException, IOException {
        if (log.isDebugEnabled()) log.debug("empty true/false = " + this.isEmpty() + ", file size = " + this.getFileSize() + "file name = " + this.getFileName() + ", origin file name = " + this.getOriginalFilename());
        
        if (this.isEmpty()) {
            return null;
        }
        
        return fileItem.getBytes();
    }
    
    /**
     * <p>
     * ������ ������ InputStream ���·� �����Ѵ�.
     * </p>
     *
     * @return ������ ����(InputStream)
     * @throws FileNotFoundException
     *             �ش� ������ ã�� ���ϴ� ���
     * @throws IOException
     *             �ش� ���Ϸ� ���� ������ ���� ���ϴ� ���
     */
    public InputStream getInputStream() throws FileNotFoundException, IOException {
        return fileItem.getInputStream();
    }
    
    /**
     * <p>
     * Ŭ���̾�Ʈ ���Ͻý��ۿ��� ������ �ִ� ���� ���ϸ��� �����Ѵ�.
     * </p>
     * 
     * @return ���ε� ���Ͽ� ���� Ű
     */
    public String getFieldName() {
        return getBaseFileName(fileItem.getName());
    }
    
    /**
     * ���� ����� ���ϸ��� ����Ѵ�.
     * 
     * @param name
     *            ���� ���ϸ�
     */
    public void setFileName(String name) {
        this.newFileName = name;
    }
    
    /**
     * ���� ����� ���ϸ��� �����Ѵ�.
     * 
     * @return
     */
    public String getFileName() {
        return this.newFileName;
    }
    
    /**
     * ������ Ȯ���ڸ� ���Ѵ�.
     * 
     * @return
     */
    public String getOriginalExtension() {
        String fileName = getOriginalFilename();
        
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    /**
     * Mutipart ������ ������ ȭ�鿡 �����ش�.
     */
    public void printFileInfo() {
        if (log.isDebugEnabled()) {
            // log.debug("Content type = "+ fileItem.getContentType());
            // log.debug("Size = "+ fileItem.getSize());
            // log.debug("Original file name() = "+ fileItem.getOriginalFilename());
            // log.debug("File name() = "+ fileItem.getName());
            // log.debug("Save file name() = "+ newFileName);
        }
    }
    
    /**
     * ������ ���ϰ�ü�� ���� ������ �����Ѵ�.
     * 
     * @param file
     * @throws Exception
     */
    public void write(File file) throws Exception {
        fileItem.transferTo(file);
    }
    
    /**
     * ���ε� ������ ����ִ� ���. ��, Multifile form�ȿ� file ���ų�, ������ file ������ ���� ��� true�� �����Ѵ�.
     * 
     * @return
     */
    public boolean isEmpty() {
        return fileItem.isEmpty();
    }
    
    /**
     * <p>
     * ��θ��� ������ ���ϸ��� �����Ѵ�.
     * </p>
     * 
     * @param filePath
     *            ������ ��ü ��θ�
     * @return ��θ��� ������ ���ϸ�
     */
    protected String getBaseFileName(String filePath) {
        String fileName = (new File(filePath)).getName();
        
        int colonIndex = fileName.indexOf(":");
        if (colonIndex == -1) {
            colonIndex = fileName.indexOf("\\\\");
        }
        int backslashIndex = fileName.lastIndexOf("\\");
        if (colonIndex > -1 && backslashIndex > -1) {
            fileName = fileName.substring(backslashIndex + 1);
        }
        return fileName;
    }
    
    @SuppressWarnings("rawtypes")
    public static void removeFormFile(CommonMap commonMap) throws Exception {
        // ���� Map data�� �����Ѵ�.(loop ���� �� ������ ����� ��� ConcurrentModificationException�� �߻��ϰ� ��)
        
        for (Iterator it = commonMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            
            if (commonMap.get(key) instanceof FileUtil) {
                commonMap.remove(key);
            }
        }
    }
    
}
