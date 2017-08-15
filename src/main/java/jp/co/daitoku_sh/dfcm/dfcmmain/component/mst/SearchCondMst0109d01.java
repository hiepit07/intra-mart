/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:SearchCondMst0109d01.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/11/2
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/2 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * 検索条件のクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class SearchCondMst0109d01 {

  //入力項目
  private String ddlShozoku;
  private String txtCustCode;
  private String txtCustNmR;
  private String txtCorrectKb;
  private String txtZeroDlvdatFlg;
  private String chkStsCode;
  private String chkStKb;
  
  public String getDdlShozoku() {
    return ddlShozoku;
  }
  
  public void setDdlShozoku(String ddlShozoku) {
    this.ddlShozoku = ddlShozoku;
  }
  
  public String getTxtCustCode() {
    return txtCustCode;
  }
  
  public void setTxtCustCode(String txtCustCode) {
    this.txtCustCode = txtCustCode;
  }
  
  public String getTxtCustNmR() {
    return txtCustNmR;
  }
  
  public void setTxtCustNmR(String txtCustNmR) {
    this.txtCustNmR = txtCustNmR;
  }
  
  public String getTxtCorrectKb() {
    return txtCorrectKb;
  }
  
  public void setTxtCorrectKb(String txtCorrectKb) {
    this.txtCorrectKb = txtCorrectKb;
  }
  
  public String getTxtZeroDlvdatFlg() {
    return txtZeroDlvdatFlg;
  }
  
  public void setTxtZeroDlvdatFlg(String txtZeroDlvdatFlg) {
    this.txtZeroDlvdatFlg = txtZeroDlvdatFlg;
  }
  
  public String getChkStsCode() {
    return chkStsCode;
  }
  
  public void setChkStsCode(String chkStsCode) {
    this.chkStsCode = chkStsCode;
  }
  
  public String getChkStKb() {
    return chkStKb;
  }
  
  public void setChkStKb(String chkStKb) {
    this.chkStKb = chkStKb;
  }
  
 
}