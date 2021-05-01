package com.morrth.libaryspringboot.util;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * 百度语音工具类
 */
@Slf4j
public class SpeechUtil {


    public static final String APP_ID = "*";

    public static final String API_KEY = "*";

    public static final String SECRET_KEY = "*";

    private static AipSpeech client;

    /**
     * 单例 懒加载模式 返回实例
     * @return
     */
    public static AipSpeech getInstance(){
        if (client==null){
            synchronized (AipSpeech.class){
                if (client==null) {
                    client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
                }
            }
        }
        return client;
    }

    /**
     * 文件流转base64
     * @param filePath
     * @return
     */
    private static String ToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * jacob语音合成
     * @param text 文字内容
     * @param speed 语速
     * @param volume 音量
     * @return
     */
    public static String ToSpeech(String text,int speed,int volume ) {
        ActiveXComponent ax = null;
        String b64="";
        try {
            ax = new ActiveXComponent("Sapi.SpVoice");
            // 运行时输出语音内容
            Dispatch spVoice = ax.getObject();
//            // 音量 0-100
//            ax.setProperty("Volume", new Variant(100));
//            // 语音朗读速度 -10 到 +10
//            ax.setProperty("Rate", new Variant(1));
            // 执行朗读
//            Dispatch.call(spVoice, "Speak", new Variant(text));

            // 下面是构建文件流把生成语音文件
            ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();
            // 设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            // 设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            // 调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant("./audio.wav"), new Variant(3), new Variant(true));
            // 设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            // 设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(volume));
            // 设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(speed));
            // 开始输出
            Dispatch.call(spVoice, "Speak", new Variant(text));

            // 关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);
            //转b64
            b64=ToBase64("./audio.wav");
            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
            return b64;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return b64;
        }
    }
    //方法重载实现默认参数
    public static String ToSpeech(String text){
        return ToSpeech(text,1,100);
    }
    public static String ToSpeech(String text,int speed){
        return ToSpeech(text,speed,100);
    }

    //    百度语音合成
//    public static boolean SpeechSynthesizer(String word, String outputPath) {
//        /*
//        最长的长度
//         */
//        int maxLength = 1024;
//        if (word.getBytes().length >= maxLength) {
//            return false;
//        }
//        // 初始化一个AipSpeech
//        client = getInstance();
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
////        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
////        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 调用接口
//        TtsResponse res = client.synthesis(word, "zh", 1, null);
//        byte[] data = res.getData();
//        org.json.JSONObject res1 = res.getResult();
//        if (data != null) {
//            try {
//                Util.writeBytesToFileSystem(data, outputPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return true;
//        }
//        if (res1 != null) {
//            log.info(" result : " + res1.toString());
//        }
//        return false;
//
//    }
    // 对语音二进制数据进行识别
    /**
     * 百度语音识别
     * @param client 文字内容
     * @param data 文字内容
     * @param format 文字内容
     * @return
     */
//    public static void asr(AipSpeech client,byte[] data,String format)
//    {
//        // 对本地语音文件进行识别
//       String path = "D:\\code\\java-sdk\\speech_sdk\\src\\test\\resources\\16k_test.pcm";
//       JSONObject asrRes = client.asr(path, "pcm", 16000, null);
//        System.out.println(asrRes);
//        //识别二进制数据流
//        JSONObject asrRes2 = client.asr(data, format, 16000, null);
//        System.out.println("json:"+asrRes2);
//
//    }
    /**
     * 百度语音识别
     * @param data
     * @param videoType
     * @return
     */
    public static String SpeechRecognitiondata(byte[] data, String videoType) {
        // 初始化一个AipSpeech
        client = getInstance();

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        JSONObject res = client.asr(data, videoType, 16000, null);
        log.info(" SpeechRecognition : " + res.toString());
        System.out.println("json:"+res);
        return res.toString(2);
    }
    public static String SpeechRecognition(String videoPath, String videoType) {
        // 初始化一个AipSpeech
        client = getInstance();

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理


        // 调用接口
        JSONObject res = client.asr(videoPath, videoType, 16000, null);
        log.info(" SpeechRecognition : " + res.toString());
        return res.toString(2);
    }


    /**
     *  mp3转pcm
     * @param mp3filepath MP3文件存放路径
     * @param pcmfilepath pcm文件保存路径
     * @return
     */
    public static boolean convertMP32Pcm(String mp3filepath, String pcmfilepath){
        try {
            //获取文件的音频流，pcm的格式
            AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);
            //将音频转化为  pcm的格式保存下来
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmfilepath));
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得pcm文件的音频流
     * @param mp3filepath
     * @return
     */
    private static AudioInputStream getPcmAudioInputStream(String mp3filepath) {
        File mp3 = new File(mp3filepath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }

}
