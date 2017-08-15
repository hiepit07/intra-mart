/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.component
 * ファイル名:FormCom0101d01.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/11/09
 *  
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/09 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 *  
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.component;

/**
 * このアノテーションをつけて、component-scanさせるとControllerとして扱われます。
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class FormCom0101d01 {
  private String userid;
  private String password;
  private boolean chkIdRemainder;
  private boolean focusPass;

  /**
   * @return the focusPass
   */
  public boolean isFocusPass() {
    return focusPass;
  }

  /**
   * @param focusPass the focusPass to set
   */
  public void setFocusPass(boolean focusPass) {
    this.focusPass = focusPass;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isChkIdRemainder() {
    return chkIdRemainder;
  }

  public void setChkIdRemainder(boolean chkIdRemainder) {
    this.chkIdRemainder = chkIdRemainder;
  }
}
