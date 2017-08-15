/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.com
 * ファイル名:FormCom0102d05.java
 *
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/10/14
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/14 1.00 TDK)グエン　リユオン　ギア　 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.com;

/**
 * 変数格納用クラス
 * 
 * @author NGUYEN LUONG NGHIA
 * 
 * @version 1.0.0
 * 
 * @since 1.0.0
 */
public class FormCom0102d05 {

  private String userCodeWk;
  private String userRoleWk;
  private String myOfficeWk;
  private int searchMax;
  private String userNameWk;

  /**
   * 担当者コードWK getter.
   * 
   * @return 担当者コードWK userCodeWk
   */
  public String getUserCodeWk() {
    return userCodeWk;
  }

  /**
   * 担当者コードWK setter.
   * 
   * @param 担当者コードWK userCodeWk
   */
  public void setUserCodeWk(String userCodeWk) {
    this.userCodeWk = userCodeWk;
  }

  /**
   * 利用権限WK getter.
   * 
   * @return 利用権限WK userRoleWk
   */
  public String getUserRoleWk() {
    return userRoleWk;
  }

  /**
   * 利用権限WK setter.
   * 
   * @param 利用権限WK userRoleWk
   */
  public void setUserRoleWk(String userRoleWk) {
    this.userRoleWk = userRoleWk;
  }

  /**
   * 所属事業所WK getter.
   * 
   * @return 所属事業所WK myOfficeWk
   */
  public String getMyOfficeWk() {
    return myOfficeWk;
  }

  /**
   * 所属事業所WK setter.
   * 
   * @param 所属事業所WK myOfficeWk
   */
  public void setMyOfficeWk(String myOfficeWk) {
    this.myOfficeWk = myOfficeWk;
  }

  /**
   * 検索最大 getter.
   * 
   * @return 検索最大 searchMax
   */
  public int getSearchMax() {
    return searchMax;
  }

  /**
   * 検索最大 setter.
   * 
   * @param 検索最大 searchMax
   */
  public void setSearchMax(int searchMax) {
    this.searchMax = searchMax;
  }

  /**
   * 担当者氏名WK getter.
   * 
   * @return 担当者氏名WK userNameWk
   */
  public String getUserNameWk() {
    return userNameWk;
  }

  /**
   * 担当者氏名WK setter.
   * 
   * @param 担当者氏名WK userNameWk
   */
  public void setUserNameWk(String userNameWk) {
    this.userNameWk = userNameWk;
  }

}
