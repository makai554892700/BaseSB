package www.mys.com.basesb.utils.file;

import www.mys.com.basesb.common.base.StaticParam;
import www.mys.com.basesb.utils.LogUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StreamUtils {

    public static String sequenceInputStream2Str(SequenceInputStream inputStream) {
        return inputStream2Str(inputStream);
    }

    public static String inputStream2Str(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            try {
                line = bufferedReader.readLine();
            } catch (Exception e) {
                LogUtils.log("e=" + e);
                return stringBuilder.toString();
            }
            if (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(StaticParam.LINE);
            }
        } while (line != null);
        CloseUtils.closeSilently(bufferedReader);
        CloseUtils.closeSilently(inputStreamReader);
        return stringBuilder.toString();
    }

    public static boolean inputStream2OutputStream(InputStream inputStream, OutputStream outputStream) {
        if (inputStream != null && outputStream != null) {
            byte[] tempByte = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(tempByte)) != -1) {
                    outputStream.write(tempByte, 0, len);
                }
                return true;
            } catch (Exception e) {
                LogUtils.log("e=" + 3);
            }
        }
        return false;
    }

}
