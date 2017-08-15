/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstCourseInformation.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

import jp.co.daitoku_sh.dfcm.common.util.Util;

/**
 * 取扱事業所のクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class MstCourseInformation {

  // 事業所コード
  private String txtJigyouShoCode;
  // 事業所名
  private String txtJigyouShoName;
  // コースコード
  private String txtCourseCode;
  // コース略称
  private String txtCourseName;
  // 便区分
  private String txtBinKubun;
  // 納品区分
  private String txtDeliveryKubun;
  // 納品区分名
  private String txtDeliveryKubunName;

  public String getTxtJigyouShoCode() {
    return txtJigyouShoCode;
  }

  public void setTxtJigyouShoCode(String txtJigyouShoCode) {
    this.txtJigyouShoCode = Util.convertSanitize(txtJigyouShoCode);
  }

  public String getTxtJigyouShoName() {
    return txtJigyouShoName;
  }

  public void setTxtJigyouShoName(String txtJigyouShoName) {
    this.txtJigyouShoName = Util.convertSanitize(txtJigyouShoName);
  }

  public String getTxtCourseCode() {
    return txtCourseCode;
  }

  public void setTxtCourseCode(String txtCourseCode) {
    this.txtCourseCode = Util.convertSanitize(txtCourseCode);
  }

  public String getTxtCourseName() {
    return txtCourseName;
  }

  public void setTxtCourseName(String txtCourseName) {
    this.txtCourseName = Util.convertSanitize(txtCourseName);
  }

  public String getTxtBinKubun() {
    return txtBinKubun;
  }

  public void setTxtBinKubun(String txtBinKubun) {
    this.txtBinKubun = Util.convertSanitize(txtBinKubun);
  }

  public String getTxtDeliveryKubun() {
    return txtDeliveryKubun;
  }

  public void setTxtDeliveryKubun(String txtDeliveryKubun) {
    this.txtDeliveryKubun = Util.convertSanitize(txtDeliveryKubun);
  }

  public String getTxtDeliveryKubunName() {
    return txtDeliveryKubunName;
  }

  public void setTxtDeliveryKubunName(String txtDeliveryKubunName) {
    this.txtDeliveryKubunName = Util.convertSanitize(txtDeliveryKubunName);
  }

}