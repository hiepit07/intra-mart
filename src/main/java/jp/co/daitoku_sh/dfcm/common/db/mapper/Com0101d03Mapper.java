/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.db.mapper
 * ファイル名:Com0101d03Mapper.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/09/22
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/22 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.common.db.mapper;

import java.util.ArrayList;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;

/**
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Com0101d03Mapper {
  /**
   * 担当者マスタから担当者情報を取得する
   * 
   * @param parms データ取得条件
   * @return 該当する担当者情報が
   */
  ArrayList<MstUser> getPersonalCharge(Map<String, Object> parms);
  
  /**
   * 担当者マスタを更新する
   * 
   * @param parms データ取得条件
   */
  void changePassword(Map<String, Object> parms);
}
