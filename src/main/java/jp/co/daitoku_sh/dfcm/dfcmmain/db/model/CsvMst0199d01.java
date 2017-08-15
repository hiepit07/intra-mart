/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.model
 * ファイル名:RstMst0199d01.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/10/07
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.model;

import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;

/**
 * 管理区分一覧データを取得する
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public class CsvMst0199d01 extends MstGeneral {
  /** 担当者マスタ.担当者氏名  */
  private String userNm;
  
  /** 分類名称 */
  private String classificationName;

  
  public String getUserNm() {
    return userNm;
  }

  
  public void setUserNm(String userNm) {
    this.userNm = userNm;
  }

  
  public String getClassificationName() {
    return classificationName;
  }

  
  public void setClassificationName(String classificationName) {
    this.classificationName = classificationName;
  }
  
}
