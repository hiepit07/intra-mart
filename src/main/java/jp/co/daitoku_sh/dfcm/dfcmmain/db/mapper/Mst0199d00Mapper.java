/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Mst0199d00Mapper.java
 * 
 * <p>作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.Gen0199d02Data;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstMst0199d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.model.RstMst0199d02;

/**
 * 一覧画面でのマップ
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */

public interface Mst0199d00Mapper {
  
  /**
   * 管理区分一覧データを取得する
   * 
   * @return list
   */
  ArrayList<MstGeneral> getCategoryList();
  
  /**
   * 管理区分一覧データを取得する
   * 
   * @param parms An object that maps keys to values
   * @return list
   */
  ArrayList<RstMst0199d01> getDivisionList(Map<String, Object> parms);
  
  /**
   * 管理区分一覧データを取得する
   * 
   * @param parms  An object that maps keys to values
   * @param handler A completion handler for consuming the result of an Ldap operation.
   * @return list
   */
  void getCsvMst0199d01Data(Map<String, Object> parms, ResultHandler handler);
  
  /**
   * 汎用マスタから管理レコード情報を取得する
   * 
   * @param parms  An object that maps keys to values
   * @return list
   */
  ArrayList<RstMst0199d02> getRstMst0199d02Data(Map<String, Object> parms);
  
  /**
   * 
   * 
   * @param parms  An object that maps keys to values
   * @return list
   */
  ArrayList<Gen0199d02Data> getDataList(Map<String, Object> parms);
  
  /**
   * 汎用マスタデータ検索
   * 
   * @param parms An object that maps keys to values
   * @return list
   */
  ArrayList<MstGeneral> searchGeneralData(Map<String, Object> parms);
  
  /**
   * 行削除にて汎用区分設定一覧から削除したコードをＤＢから削除する
   * 
   * @param parms An object that maps keys to values
   * @return The number of deleted records.
   */
  int deleteCode(Map<String, Object> parms);
}
