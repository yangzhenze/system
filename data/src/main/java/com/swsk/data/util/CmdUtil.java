package com.swsk.data.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @program: rgyx_hb
 * @description: cmd执行工具
 * @author: 杨明
 * @version: 1.0
 * @create: 2019-09-04 10:38
 **/
@Slf4j
public class CmdUtil {
    /**
     * 执行Cmd命令
     * @param cmdStr 执行命令
     * @param dataFileDirPath 程序存放路径(不需要执行程序时为空)
     * @return
     */
    public static boolean excuteCmd(String cmdStr, String dataFileDirPath) {
        System.out.println(cmdStr + " --- " + dataFileDirPath);
        try {
            // 执行命令
            Process process = null;
            if(dataFileDirPath == null) {
                process = Runtime.getRuntime().exec(cmdStr);
            }else {
                process = Runtime.getRuntime().exec(cmdStr, null, new File(dataFileDirPath));
            }
            // 输出信息（try(){}会自动关闭()里的流）
            try(InputStream in = process.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));){
                String line = br.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = br.readLine();
                }
            }
            // 输出错误信息
            try(InputStream err = process.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(err, "GBK"));){
                String line = br.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = br.readLine();
                }
            }
            // 输出错误信息
            /*try(OutputStream out = process.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"GBK"))){

            }*/
            // 执行结果（0成功，其余都失败）
            int status = process.waitFor();
            return 0 == status;
        } catch (Exception e) {
            log.error("执行Cmd命令失败", e);
            return false;
        }
    }

    public static void main(String[] args) {
    }
}
