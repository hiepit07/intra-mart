/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:GetOfficeInformationGet0101d00.java
 * 
 *<p>作成者:HiepTruong
 * 作成日:2015/10/07
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)HiepTruong 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.get;

/**
 * 事業所情報のクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
public class GetOfficeInformationGet0101d00 {

  // 事業所マスタ .事業所コード.
  private String jgymei;

  // 事業所マスタ .事業所名
  private String jgycd;

  public String getJgymei() {
    return jgymei;
  }

  public void setJgymei(String jgymei) {
    this.jgymei = jgymei;
  }

  public String getJgycd() {
    return jgycd;
  }

  public void setJgycd(String jgycd) {
    this.jgycd = jgycd;
  }
}