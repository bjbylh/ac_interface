/**  
 * Copyright © 2017Wang. All rights reserved.
 *
 * @Title: IAccessControl.java
 * @Prject: demo.nuaa.launcher
 * @Package: com.nuaa.accesscontrol
 * @Description: TODO
 * @author: wang  
 * @date: 2017�?3�?10�? 下午8:16:25
 * @version: V1.0  
 */
package com.nuaa.accesscontrol;

import java.util.List;

/**
 * @ClassName: IAccessControl
 * @Description: TODO
 * @author: wang
 * @date: 2017�?3�?10�? 下午8:16:25
 */
public interface IAccessControl {
    /**
     * 获取令牌接口
     * @param ClientBID 被调用客户端ID
     * @param UserID 用户ID
     * @return 令牌
     */
    String GetAccessToken(String ClientBID,String UserID);

    /**
     * 验证令牌
     * @param Token 令牌
     * @return 验证结果
     */
    EnumDefine.TokenValidationResultsEnum IsValidAccess(String Token,String Client_id);

    /**
     * 用户账号认证
     * @param UserID 用户ID
     * @param Password 密码
     * @return 验证结果
     */
    EnumDefine.AccountValidationResultsEnum IsValidAccess(String UserID,String Password,String Client_id);

    /**
     * 获取权限列表
     * 调用该方法前请先进行IsValidAccess，若认证失败则为空list，调用该方法�?次后，前�?次认证失�?
     * @return 权限列表
     */
    List<String> GetPermissonList(String Satellite_id);

    /**
     * 修改密码
     * @param UserID 用户ID
     * @param OLD_Password 老密�?
     * @param NEW_Password 新密�?
     * @return 修改结果
     */
    EnumDefine.EditPasswordResultEnum EditPassword(String UserID,String OLD_Password,String NEW_Password);
    
    /**
     * 获取当前用户ID，若未认证成功，返回空字符串（非null�?
     * @return 用户ID
     */
    String GetCurrentUserID();
    
}
