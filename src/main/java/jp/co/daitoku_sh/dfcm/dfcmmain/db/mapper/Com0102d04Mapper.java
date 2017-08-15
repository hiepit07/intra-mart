/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Com0102d04Mapper.java
 * 
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/10/12
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/12 1.00 ABV)グエン　リユオン　ギア 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.com.Com0102d04Model;

import java.util.List;
import java.util.Map;

/**
 * サービスから呼び出されるMapper（実行するSQLを定義）
 *
 * @author グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Com0102d04Mapper {

  // チェーンマスタからチェーン情報を取得する
  List<Com0102d04Model> getMstCustomerData(Map<String, Object> parms);

  // 一覧データ取得用SQL
  List<Com0102d04Model> getMstSSeihn(Map<String, Object> parms);

  // 一覧データ取得用SQL
  List<Com0102d04Model> getSearchMstSSeihn(Map<String, Object> parms);

  // 一覧データ取得用SQL
  List<Com0102d04Model> getMstCustTanKaSeihn(Map<String, Object> parms);

  // 一覧データ取得用SQL
  List<Com0102d04Model> getSearchMstCustTanKaSeihn(Map<String, Object> parms);


}
