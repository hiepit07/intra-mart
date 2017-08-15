/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu
 * ファイル名:Nyu0106d01ShiwakeSakuseiRirekiInfo.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/09
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu;

/**
 * 仕訳作成履歴情報 DTO
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class Nyu0106d01ShiwakeSakuseiRirekiInfo {

 private String renkeiId;       // 会計連携ID
  private String createDate;     // 作成日付
  private String createTime;     // 作成時刻
  private String shubetsu;       // 種別
  private int dempyoCount;       // 入金・売上件数
  private int shiwakeCount;      // 仕訳件数
  private int outputCount;       // 出力回数
  
  //---------------------------------------------------------------------------
  // getter / setter
  //---------------------------------------------------------------------------
  
  public String getRenkeiId() {
    return renkeiId;
  }
  
  public void setRenkeiId(String renkeiId) {
    this.renkeiId = renkeiId;
  }
  
  public String getCreateDate() {
    return createDate;
  }
  
  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
  
  public String getCreateTime() {
    return createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public String getShubetsu() {
    return shubetsu;
  }
  
  public void setShubetsu(String shubetsu) {
    this.shubetsu = shubetsu;
  }
  
  public int getDempyoCount() {
    return dempyoCount;
  }
  
  public void setDempyoCount(int dempyoCount) {
    this.dempyoCount = dempyoCount;
  }
  
  public int getShiwakeCount() {
    return shiwakeCount;
  }
  
  public void setShiwakeCount(int shiwakeCount) {
    this.shiwakeCount = shiwakeCount;
  }
  
  public int getOutputCount() {
    return outputCount;
  }
  
  public void setOutputCount(int outputCount) {
    this.outputCount = outputCount;
  }

}
