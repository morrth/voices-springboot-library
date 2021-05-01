package com.morrth.libaryspringboot.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;

/**
 * @author morrth
 * @create 2021-03-24-19:00
 */
public class change {
    public static byte[] blobToBytes(Blob blob) {
        BufferedInputStream is = null;

        try {
            is = new BufferedInputStream(blob.getBinaryStream());

            byte[] bytes = new byte[(int) blob.length()];

            int len = bytes.length;

            int offset = 0;

            int read = 0;

            while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
                offset += read;

            }

            return bytes;

        } catch (Exception e) {
            return null;

        } finally {
            try {
                is.close();

                is = null;

            } catch (IOException e) {
                return null;

            }

        }

    }
}
