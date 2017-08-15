/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:ExportCSVMst.java
 * 
 *<p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 1.00 HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.get;

/**
 * CSV用のクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
public class ExportCsvGet {

  private String strPathFile;
  private String strName;

  // エラーメッセージ
  private String message;
  private String type;
  private String tantoshaName;
  private String seikyuName;
  private String setFocus;
  
  public String getSetFocus() {
    return setFocus;
  }

  
  public void setSetFocus(String setFocus) {
    this.setFocus = setFocus;
  }

  public String getTantoshaName() {
    return tantoshaName;
  }

  public void setTantoshaName(String tantoshaName) {
    this.tantoshaName = tantoshaName;
  }

  public String getSeikyuName() {
    return seikyuName;
  }

  public void setSeikyuName(String seikyuName) {
    this.seikyuName = seikyuName;
  }

  public String getStrName() {
    return strName;
  }

  public void setStrName(String strName) {
    this.strName = strName;
  }

  public String getStrPathFile() {
    return strPathFile;
  }

  public void setStrPathFile(String strPathFile) {
    this.strPathFile = strPathFile;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
