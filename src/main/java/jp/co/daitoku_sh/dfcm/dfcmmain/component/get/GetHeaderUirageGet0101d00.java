/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:getHeaderUirageGet0101d00.java
 * 
 *<p>者:TuanNguyen
 * 作成日:2015/10/07
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)TuanNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.get;

/**
 * 請求締め処理のクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */

public class GetHeaderUirageGet0101d00 {

  // 売上ヘッダ情報.自社伝票No
  private String denno;
  // 売上ヘッダ情報.自社伝票枝番
  private String denidx;

  public String getDenno() {
    return denno;
  }

  public void setDenno(String denno) {
    this.denno = denno;
  }

  public String getDenidx() {
    return denidx;
  }

  public void setDenidx(String denidx) {
    this.denidx = denidx;
  }
}