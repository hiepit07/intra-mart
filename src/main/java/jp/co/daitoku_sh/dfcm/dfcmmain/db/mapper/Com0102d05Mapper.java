/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.controller.mst.impl
 * ファイル名:Com0102d05Mapper.java
 * 
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/10/14
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/14 1.00 ABV)グエン　リユオン　ギア　 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;

/**
 * サービスから呼び出されるMapper（実行するSQLを定義）
 *
 * @author グエン　リユオン　ギア
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Com0102d05Mapper {

  /**
   * チェーンマスタからチェーン情報を取得する.
   * 
   * @param parms:parms
   * @return list
   */
  List<MstUser> getMstUserData(Map<String, Object> parms);

  /**
   * 一覧データ取得用SQL.
   * 
   * @param parms:parms
   * @return list
   */
  List<MstUser> getSearchResult(Map<String, Object> parms);

  /**
   * シェイン情報を見ます.
   * 
   * @param parms:parms
   * @return list
   */
  List<MstUser> getMstUserInfo(Map<String, Object> parms);
}
