/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Com0101d01Mapper.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/16
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/16 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;

/**
 * 一覧画面でのマップ
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Com0101d01Mapper {

  /**
   * 担当者マスタから担当者情報を取得する
   * 
   * @param parms データ取得条件
   * @return 該当する担当者情報が
   */
  ArrayList<MstUser> getPersonalCharge(Map<String, Object> parms);

  ArrayList<MstSJigyo> getOfficeInformation(Map<String, Object> parms);

  void updateLoginErrCnt(Map<String, Object> parms);

  void lockoutUser(Map<String, Object> parms);
  
  void updateLastLoginDateTime(Map<String, Object> parms);
  
  ArrayList<MstGeneral> getGeneral(Map<String, Object> parms);
}
