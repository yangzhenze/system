package com.swsk.data.util.generate.util;

import com.swsk.data.util.generate.db.BeanAttribute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.*;
import java.util.*;

/**
 * @author zzy
 * @Date 2019-07-17 21:16
 */
@Slf4j
public class Common {

    private static final String CREATE_AUTHOR = "yzz";
    public static final String JAVA_HEAD_STR = "/**\n" +
            " * @author " + CREATE_AUTHOR + "\n" +
            " * @Date " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "\n" +
            " */\n";
    public static String importPath;

    static{
        importPath = getPackagePath();
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String toFirstWordLow(String str) {
        String first = str.substring(0, 1);
        return first.toLowerCase() + str.substring(1);
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String toFirstWordUpp(String str) {
        String first = str.substring(0, 1);
        return first.toUpperCase() + str.substring(1);
    }


    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static List<BeanAttribute> getBeanAttributes(String beanPath) {
        List<BeanAttribute> beanAttributes = new ArrayList();
        File file = new File(beanPath);


        try (FileInputStream inputStream = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));) {

            String line = null;

            while ((line = reader.readLine()) != null) {
                if (line.contains("@Column")) {
                    BeanAttribute beanAttribute = new BeanAttribute();
                    String[] columnContent = line.trim().split("\"");
                    String[] content = reader.readLine().trim().split(" ");
                    if (line.contains("isPK = true")) {
                        beanAttribute.setIsPk(true);
                    }
                    beanAttribute.setColumn(columnContent[1]);
                    beanAttribute.setName(content[2].replaceAll(";", ""));
                    beanAttribute.setType(content[1]);
                    beanAttributes.add(beanAttribute);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return beanAttributes;
    }

    /**
     * 获取当前包
     * @return
     */
    public static String getPackagePath(){
        String classResource = Common.class.getResource("").getPath();
        String classStr = "classes";

        classResource = classResource.substring(classResource.indexOf(classStr)+classStr.length()+1,classResource.lastIndexOf("util"));
        return classResource.replaceAll("[\\\\ /]",".");
    }


    public static void main(String[] args) throws IOException {
        System.getProperty("user.dir");

        System.out.println(Common.importPath);
        System.out.println(Common.importPath);
    }


    public static void deleteDir(String dirPath) {
        dirPath = dirPath.replace("\\", "/");
        File file = new File(dirPath);
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                file.delete();
            } else {
                for (int i = 0; i < files.length; i++) {
                    deleteDir(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }

    public static void createFile(String filePath, String content) throws IOException {
        filePath = filePath.replace("\\", "/");
        if (filePath.contains(".")) {
            File file = new File(filePath.substring(0, filePath.lastIndexOf("/")));

            if (!file.exists()) {
                file.mkdirs();
            }
        }else{
            log.info("请确认文件后缀");
        }
        File file = new File(filePath);
        // 写入文件
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(content);
            out.flush();
        }
    }

    public static void sqlIn(StringBuffer sql,int paramLength){
        for (int i = 0; i < paramLength; i++) {

        }


        /*for (Object param : params) {
            if(flag){
                sql.append(",");
            }else{
                flag = true;
            }
            sql.append("?");
        }*/
        sql.append(")");
    }


}
