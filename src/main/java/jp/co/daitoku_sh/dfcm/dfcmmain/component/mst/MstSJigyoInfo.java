/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:MstSJigyoInfo.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * 所属事業所のクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */

public class MstSJigyoInfo {

  private String jgycd;
  private String jgymei;

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