package com.mola.molablogv2.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author : molamola
 * @Project: molablogv2
 * @Description:
 * @date : 2020-11-06 14:26
 **/
@Slf4j
public class IOUtils {

    public static void writeString2OutputStream(String text, HttpServletResponse response) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            bw.write(text, 0, text.length());
            bw.flush();
        } catch (IOException e) {
            log.error("response输出流写入异常", e);
            response.setStatus(500);
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                log.error("response输出流关闭异常", e);
            }
        }
    }
}
