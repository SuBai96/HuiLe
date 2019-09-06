package com.mll.config;

/**
 * 利用蚂蚁金服官方的jdk
 */
import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2018-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016100100642807";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCd/0td/NwIkqBJqEZBqfHqpeBGYSUxPX8qrQ2nPnx/xrADaKZA1PMqu7IlAepYWBxLA4acWh0jqXKXC9ZqQjRxuwV8kB3dgjsiGcHNckKKcz+/QRh848/gxgKhr5eRJtUx9UoweJx6BuGKT+VnEU58hVPnObjg/67S+PYJiCW6YVGLVE7NHIMfdfCL9qphTMbC9yk/JrVlODBJGhZ/MEEqsTrBuiKwPnuRrhFK0faOEuZw5Uux9rWZzf1MzFP+ygQO5RRoMsn2CjoReq7SB1tE/nNH2d74lYOqCWorEGxa3ijc/2pFvj60ocMBeOIVBai4XKPeRjSAnyHc3ZWhm6p1AgMBAAECggEAKU3teRO6vi7+4mKwg0gXa12UBTIuU/IcxZ5aHGfUil758tov5HEHZRuOlegrSI84TlAMp+FTDtikkWJR2pOWPElFROw0Mo1yQhbzuG7vgFxixPASo8u2s69wTq6l9Bdv0tCqZYOSfMBBxDPg6BH5L8p3RdKsR33wSU02nQVPv6p6RhPrlYmAxLGxrch743Q7/2+zcYLuXGRmB8b+uk3xQzzroaPv1QE6BGsvSqoDUFW0fPbezZ7MV6cJ7512ePiZhwND/lWgWPcMXQY0wiqiypYYRAnQ3w0INxUaG5Ls+ZHS9AGF3A9qJlcxAq8e3VBmnAtKHbyQhXUyB4QeqTozQQKBgQDNgfh0kTCr9PyBplJW02WkBk+kwCjFbaVgkfWVHhkxTO2OiSO7wIQ5/TwoMJP/lk8qX/UnN9DFv8nYzlhKDY1ZUGIzo1bWx4ALcU8UjycYOld0dB+sx8oSwHSy8z7hSHNppsWTSwwpBnU48COXOem7kaIUp/Hs8c7ke+q676DfkQKBgQDE0QKI4iYcA+6loxpjLzqf2cA0E1CZFwPPlAsr2OJpwRK3rFG0/nHJmfxDHWuNiEwsB+w3Wz1DBs5utzYxYe2KwmJhFM3dTEiB6S7ZJW895fTnopysEumLiYeMjHqeLN4ASVRdGq6lGLwUxoRZ7QFrqG0ZHGE+yWAExJ++oQ1ypQKBgC0d/6H3ziuyWU1Nan5NYt8j05Bj74+IhuowoS3fscA1F2JXkVTC60Sf5/ka0SMnqLGw5214v4jqljHW+2MmnoWhNC4KCiKVIfNhKO9mjjqvdEvIwrt4EVEUl4pCBOgIs+3aAz5XF5BaLpoVfnrQ6BOZ4W+Pf3l5jOZ0ng/0U+lBAoGBAJjxkvasRtEefSVaSfcpjHUL0cCmAcv1hpCYitXYBJoJ33G8BwKPd/0iHFZLVnj1obvVrEqYLFMZxvFijkpxvxZFWA35DMEbtny9qBo2bOu890PJqdZOHA7DIQKnZ+HjG+E9GsH/KhhB0GaUneUDuEbg62bIFwmjk2ohIcIRhVHBAoGAHKHjLhZp6RRMeTl0eSgfInc21I1Jm09I+4hrMntodmbLvNvOj4BJvdepFmvQBhO31VUet21rfUfJjY3a+YQf1QO0o5kKxw/mamcYqT/Fckqhegm36Qq0RzPLRhQZnZ0WfDE3Q1gfy1Rjpjt7w48wVpQsuTm7mphVdIv7g54InZk=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnf9LXfzcCJKgSahGQanx6qXgRmElMT1/Kq0Npz58f8awA2imQNTzKruyJQHqWFgcSwOGnFodI6lylwvWakI0cbsFfJAd3YI7IhnBzXJCinM/v0EYfOPP4MYCoa+XkSbVMfVKMHicegbhik/lZxFOfIVT5zm44P+u0vj2CYglumFRi1ROzRyDH3Xwi/aqYUzGwvcpPya1ZTgwSRoWfzBBKrE6wboisD57ka4RStH2jhLmcOVLsfa1mc39TMxT/soEDuUUaDLJ9go6EXqu0gdbRP5zR9ne+JWDqglqKxBsWt4o3P9qRb4+tKHDAXjiFQWouFyj3kY0gJ8h3N2VoZuqdQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.1:81/notify_url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //public static String return_url = "http://127.1:8080/ssm/return_url";
    public static String return_url = "http://127.1:81/return_url";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "E:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}