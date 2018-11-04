/**  
 * Copyright © 2017Wang. All rights reserved.
 *
 * @Title: DesUtils.java
 * @Prject: demo.nuaa.launcher
 * @Package: com.nuaa.desutil
 * @Description: TODO
 * @author: wang  
 * @date: 2017 3 12  下午7:42:29
 * @version: V1.0  
 */
package com.nuaa.desutil;

/**
 * @ClassName: DesUtils
 * @Description: DES加密和解密工 ,可以对字符串进行加密和解密操   
 * @author: wang
 * @date: 2017 3 12  下午7:42:29
 */
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import java.security.*;

public class DesUtils {
  
  /** 字符串默认键      */
  private static String strDefaultKey = "national";

  /** 加密工具     */
  private Cipher encryptCipher = null;

  /** 解密工具     */
  private Cipher decryptCipher = null;

  /**  
   * 将byte数组转换为表 16进制值的字符串， 如：byte[]{8,18}转换为：0813  和public static byte[]  
   * hexStr2ByteArr(String strIn) 互为�? 成的转换过程  
   *   
   * @param arrB  要转换的byte数组  
   * @return 转换后的字符   
   * @throws Exception 本方法不处理任何异常，所有异常全部抛   
   */
  public static String byteArr2HexStr(byte[] arrB) throws Exception {
    int iLen = arrB.length;
    // 每个byte用两个字符才能表示， 以字符串的长度是数组长度的两    
    StringBuffer sb = new StringBuffer(iLen * 2);
    for (int i = 0; i < iLen; i++) {
      int intTmp = arrB[i];
      // 把负数转换为正数   
      while (intTmp < 0) {
        intTmp = intTmp + 256;
      }
      // 小于0F的数 要在前面 0   
      if (intTmp < 16) {
        sb.append("0");
      }
      sb.append(Integer.toString(intTmp, 16));
    }
    return sb.toString();
  }

  /**  
   * 将表 16进制值的字符串转换为byte数组  和public static String byteArr2HexStr(byte[] arrB)  
   * 互为�? 成的转换过程  
   * @param strIn  要转换的字符   
   * @return 转换后的byte数组  
   * @throws Exception  本方法不处理任何异常，所有异常全部抛   
   * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>  
   */
  public static byte[] hexStr2ByteArr(String strIn) throws Exception {
    byte[] arrB = strIn.getBytes();
    int iLen = arrB.length;

    // 两个字符表示 个字节， 以字节数组长度是字符串长度除 2   
    byte[] arrOut = new byte[iLen / 2];
    for (int i = 0; i < iLen; i = i + 2) {
      String strTmp = new String(arrB, i, 2);
      arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
    }
    return arrOut;
  }

  /**  
   * 默认�? 成方法，使用默认密钥  
   *   
   * @throws Exception  
   */
  public DesUtils() throws Exception {
    this(strDefaultKey);
  }

  /**  
   * 指定密钥�? 成方   
   *   
   * @param strKey 指定的密   
   * @throws Exception  
   */
  public DesUtils(String strKey) throws Exception {
	  
    Security.addProvider(new com.sun.crypto.provider.SunJCE());
    Key key = getKey(strKey.getBytes());

    encryptCipher = Cipher.getInstance("DES");
    encryptCipher.init(Cipher.ENCRYPT_MODE, key);

    decryptCipher = Cipher.getInstance("DES");
    decryptCipher.init(Cipher.DECRYPT_MODE, key);
  }

  /**  
   * 加密字节数组  
   *   
   * @param arrB  加密的字节数   
   * @return 加密后的字节数组  
   * @throws Exception  
   */
  public byte[] encrypt(byte[] arrB) throws Exception {
    return encryptCipher.doFinal(arrB);
  }

  /**  
   * 加密字符   
   *   
   * @param strIn  加密的字符串  
   * @return 加密后的字符   
   * @throws Exception  
   */
  public String encrypt(String strIn) throws Exception {
    return byteArr2HexStr(encrypt(strIn.getBytes()));
  }

  /**  
   * 解密字节数组  
   *   
   * @param arrB  解密的字节数   
   * @return 解密后的字节数组  
   * @throws Exception  
   */
  public byte[] decrypt(byte[] arrB) throws Exception {
    return decryptCipher.doFinal(arrB);
  }

  /**  
   * 解密字符   
   *   
   * @param strIn  解密的字符串  
   * @return 解密后的字符   
   * @throws Exception  
   */
  public String decrypt(String strIn) throws Exception {
    return new String(decrypt(hexStr2ByteArr(strIn)));
  }

  /**  
   * 从指定字符串生成密钥，密钥所 的字节数组长度为8  不足8位时后面 0，超 8位只取前8   
   *   
   * @param arrBTmp  构成该字符串的字节数   
   * @return 生成的密   
   * @throws java.lang.Exception  
   */
  private Key getKey(byte[] arrBTmp) throws Exception {
    // 创建 个空 8位字节数组（默认值为0    
    byte[] arrB = new byte[8];

    // 将原始字节数组转换为8    
    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
      arrB[i] = arrBTmp[i];
    }

    // 生成密钥   
    Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

    return key;
  }

  /**
   * main方法   
   * @author wang
   * @param args
   */
  public static void main(String[] args) {
    try {
      String test = "123456789";
      DesUtils des = new DesUtils("testdestkey");//自定义密    
      System.out.println("加密前的字符 " + test);
      System.out.println("加密后的字符 " + des.encrypt(test));
      System.out.println("解密后的字符 " + des.decrypt(des.encrypt(test)));
      System.out.println("解密后的字符 " + des.decrypt("cdefe0879f1f26686b0f38b23abcf99e"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
