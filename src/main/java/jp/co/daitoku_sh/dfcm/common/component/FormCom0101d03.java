/**
 * パッケージ名:jjp.co.daitoku_sh.dfcm.common.component
 * ファイル名:FormCom0101d03.java
 * 
 * <p>
 * 作成者:QuanTran
 * 作成日:2015/09/21
 * 
 * <p>
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * 
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormCom0101d03 {

  /** 現在のパスワード */
  private String password;
  /** 新しいパスワード */
  private String newPass;
  /** 新しいパスワード（再入力 */
  private String reEnterNewPass;

  private String errMessage;
  
  private String haitaDate;
  
  private String haitaTime;
  
  public String getHaitaDate() {
    return haitaDate;
  }

  
  public void setHaitaDate(String haitaDate) {
    this.haitaDate = haitaDate;
  }

  
  public String getHaitaTime() {
    return haitaTime;
  }

  
  public void setHaitaTime(String haitaTime) {
    this.haitaTime = haitaTime;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNewPass() {
    return newPass;
  }

  public void setNewPass(String newPass) {
    this.newPass = newPass;
  }

  public String getReEnterNewPass() {
    return reEnterNewPass;
  }

  public void setReEnterNewPass(String reEnterNewPass) {
    this.reEnterNewPass = reEnterNewPass;
  }

  public String getErrMessage() {
    return errMessage;
  }

  public void setErrMessage(String errMessage) {
    this.errMessage = errMessage;
  }
}
