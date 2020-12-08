package com.java.yangnj;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author ynj
 * @program: ToolBox
 * @description: Character  Convert Tools
 * @date 2020-12-01 19:10:26
 */
public class CharTools {
    /**
     * Unicode max code point in Basic Multilingual Plane (BMP) is 0xFFFF。
     */
    private static int UNICODE_BMP_LIMIT = 0xFFFF;

    /**
     * @param src(String)
     * @param charsetName(String)
     * @return Hex for String(by Charset)
     * @throws UnsupportedEncodingException
     */
    public static String stringToHexStringByCharsetName(String src,String charsetName) throws UnsupportedEncodingException {
        byte[] byteArray = src.getBytes(charsetName);
        return bytesToHexString(byteArray);
    }

    /**
     * @param hexStr
     * @param charsetName
     * @return String(Hex to String by Charset name)
     * @throws UnsupportedEncodingException
     */
    public static String hexStringToStringByCharsetName(String hexStr,String charsetName) throws UnsupportedEncodingException {
        return(new String(hexStringToBytes(hexStr),charsetName));
    }

    public static String bytesToHexString(byte[] byteArray){
        StringBuffer sb = new StringBuffer(byteArray.length);
        String sTemp;
        for(int i = 0;i < byteArray.length;i++){
            sTemp = Integer.toHexString(byteArray[i]&0XFF);
            if(sTemp.length()<2){
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String hexString){
        byte[]  byteArray = new byte[hexString.length()/2];
        for(int i = 0;i < hexString.length()/2;i++){
            String item = hexString.substring(2*i,2*i+2);
            byteArray[i] = (byte) Integer.parseInt(item, 16);
        }
        return byteArray;
    }

    /**
     * @param str
     * @return Unicode code point
     */
    public static String getUnicodeFormUtf16(String str){
        int unicodeCodePoint;
        StringBuffer sb = new StringBuffer();
        char[] charArray = str.toCharArray();
        for(int i=0;i<charArray.length;){
            unicodeCodePoint = Character.codePointAt(charArray, i);
            /**
             * Code point greater than UNICODE_BMP_LIMIT(0xFFFF),The character
             * in Supplementary Planes, It requires two 16-bit code units
             *  from UTF-16.
             */
            if (UNICODE_BMP_LIMIT<unicodeCodePoint){
                i = i + 2;
            }else{
                i++;
            }
            sb.append(Integer.toHexString(unicodeCodePoint).toUpperCase());
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String charsToHexString(char[] charArray){
        StringBuffer sb = new StringBuffer(charArray.length);
        String sTemp;
        for(int i = 0;i < charArray.length;i++){
            sTemp = Integer.toHexString(charArray[i]);
            switch(sTemp.length()){
                case 1:
                    sb.append("000");
                    break;
                case 2:
                    sb.append("00");
                    break;
                case 3:
                    sb.append("0");
                    break;
                default:
                    break;
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] charsToBytes(char[] charArray){
        byte[] byteArray = new byte[charArray.length*2];
        ByteBuffer byteBuffer = ByteBuffer.allocate(charArray.length*2);
        for(int i = 0;i < charArray.length;i++){
            byteBuffer.putChar(charArray[i]);
        }
        byteBuffer.rewind();
        byteBuffer.get(byteArray,0,2);
        return byteArray;
    }


}
