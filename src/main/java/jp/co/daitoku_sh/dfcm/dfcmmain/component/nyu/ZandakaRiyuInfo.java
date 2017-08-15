/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:ZandakaRiyuInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/04
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/04 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

/**
 * 残高理由情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class ZandakaRiyuInfo {

  private String code; // 残高理由コード
  private int kingaku; // 残高理由金額

  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public int getKingaku() {
    return kingaku;
  }
  
  public void setKingaku(int kingaku) {
    this.kingaku = kingaku;
  }

}
