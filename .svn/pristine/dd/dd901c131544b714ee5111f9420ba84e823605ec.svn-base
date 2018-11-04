/**  
 * Copyright © 2017Wang. All rights reserved.
 *
 * @Title: EnumDefine.java
 * @Prject: demo.nuaa.launcher
 * @Package: com.nuaa.accesscontrol
 * @Description: TODO
 * @author: wang  
 * @date: 2017�?3�?10�? 下午8:15:12
 * @version: V1.0  
 */
package com.nuaa.accesscontrol;

/**
 * @ClassName: EnumDefine
 * @Description: TODO
 * @author: wang
 * @date: 2017�?3�?10�? 下午8:15:12
 */
public class EnumDefine {
    public enum TokenValidationResultsEnum {
        ErrorTokenCode, //令牌编码错误
        ErrorTokenTimeout, //令牌超时,
        ErrorDBTimeout, //数据库访问超�?,
        ErrorClientID,//令牌客户端错�?,
        ErrorUserID, //用户ID错误,
        Success //验证成功
    }

    public enum AccountValidationResultsEnum {
        ErrorDBTimeout,//数据库访问超�?,
        ErrorUserID,// 用户ID不存�?,
        ErrorUserIDLocked,//用户账户被锁�?,
        ErrorPassword,//密码错误,
        Success //验证成功
    }

    public enum EditPasswordResultEnum {
        ErrorDBTimeout,// 数据库访问超�?,
        ErrorUserID,// 用户ID不存�?,
        ErrorPassword,// 密码错误,
        Success //修改密码成功
    }
}
