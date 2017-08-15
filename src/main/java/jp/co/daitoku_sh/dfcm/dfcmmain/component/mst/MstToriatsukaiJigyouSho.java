/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstToriatsukaiJigyouSho.java
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

/**
 * 取扱事業所のクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class MstToriatsukaiJigyouSho {

  // 取扱事業所
  private String ddlToriatsukaiJigyouSho;
  private String txtToriatsukaiJigyouShoHidden;
  // 営業担当者コード
  private String txtEigyouTantoushaCode;
  // 営業担当者名
  private String txtEigyouTantoushaName;
  // 事務担当者コード
  private String txtJimuTantoushaCode;
  // 事務担当者名
  private String txtJimuTantoushaName;

  public String getDdlToriatsukaiJigyouSho() {
    return ddlToriatsukaiJigyouSho;
  }

  public void setDdlToriatsukaiJigyouSho(String ddlToriatsukaiJigyouSho) {
    this.ddlToriatsukaiJigyouSho = ddlToriatsukaiJigyouSho;
  }

  public String getTxtToriatsukaiJigyouShoHidden() {
    return txtToriatsukaiJigyouShoHidden;
  }

  public void setTxtToriatsukaiJigyouShoHidden(
      String txtToriatsukaiJigyouShoHidden) {
    this.txtToriatsukaiJigyouShoHidden = txtToriatsukaiJigyouShoHidden;
  }

  public String getTxtEigyouTantoushaCode() {
    return txtEigyouTantoushaCode;
  }

  public void setTxtEigyouTantoushaCode(String txtEigyouTantoushaCode) {
    this.txtEigyouTantoushaCode = txtEigyouTantoushaCode;
  }

  public String getTxtEigyouTantoushaName() {
    return txtEigyouTantoushaName;
  }

  public void setTxtEigyouTantoushaName(String txtEigyouTantoushaName) {
    this.txtEigyouTantoushaName = txtEigyouTantoushaName;
  }

  public String getTxtJimuTantoushaCode() {
    return txtJimuTantoushaCode;
  }

  public void setTxtJimuTantoushaCode(String txtJimuTantoushaCode) {
    this.txtJimuTantoushaCode = txtJimuTantoushaCode;
  }

  public String getTxtJimuTantoushaName() {
    return txtJimuTantoushaName;
  }

  public void setTxtJimuTantoushaName(String txtJimuTantoushaName) {
    this.txtJimuTantoushaName = txtJimuTantoushaName;
  }

}