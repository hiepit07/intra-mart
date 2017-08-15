/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:SearchCondMst0105d01.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/11/04
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/04 1.00 ABV)QuanTran 新規作成
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * 検索条件保持クラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class SearchCondMst0105d01 {

  /** オンラインセンターコード */
  private String txtOnlineCenterCode;
  /** オンライン取引先コード */
  private String txtOnlineTorihikiCode;
  /** 相手取引先コード */
  private String txtAtTorihikiCode;
  /** 取消データを対象とする */
  private boolean chkCancellationData;

  public String getTxtOnlineCenterCode() {
    return txtOnlineCenterCode;
  }

  public void setTxtOnlineCenterCode(String txtOnlineCenterCode) {
    this.txtOnlineCenterCode = txtOnlineCenterCode;
  }

  public String getTxtOnlineTorihikiCode() {
    return txtOnlineTorihikiCode;
  }

  public void setTxtOnlineTorihikiCode(String txtOnlineTorihikiCode) {
    this.txtOnlineTorihikiCode = txtOnlineTorihikiCode;
  }

  public String getTxtAtTorihikiCode() {
    return txtAtTorihikiCode;
  }

  public void setTxtAtTorihikiCode(String txtAtTorihikiCode) {
    this.txtAtTorihikiCode = txtAtTorihikiCode;
  }

  public boolean isChkCancellationData() {
    return chkCancellationData;
  }

  public void setChkCancellationData(boolean chkCancellationData) {
    this.chkCancellationData = chkCancellationData;
  }

}
