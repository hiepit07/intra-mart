/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.db.mapper
 * ファイル名:Com0101d02Mapper.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/17
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/17 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.common.db.model.MstFunction;
import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;

/**
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Com0101d02Mapper {

  /**
   * 利用権限マスタ、機能マスタから業務権限情報を取得する
   * 
   * @param parms An object that maps keys to values
   * @return 業務権限情報を
   */
  ArrayList<MstFunction> getBusinessAuthoInfo(Map<String, Object> parms);

  /**
   * 利用権限マスタ、機能マスタから業務権限情報を取得する
   * 
   * @param parms An object that maps keys to values
   * @return 業務権限情報を
   */
  ArrayList<MstFunction> getFuncInfo(Map<String, Object> parms);

  /**
   * 連絡事項取得
   * 
   * @param parms An object that maps keys to values
   * @return 連絡事項
   */
  ArrayList<MstGeneral> getMatterContact(Map<String, Object> parms);
}
