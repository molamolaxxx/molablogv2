package com.mola.molablogv2.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: molamola
 * @Date: 19-6-21 下午1:51
 * @Version 1.0
 */
@Slf4j
public class MD5UtilsTest {

    @Test
    public void md5Encode() {

        log.info(MD5Utils.Md5Encode("molamolaxxx"));
    }
}