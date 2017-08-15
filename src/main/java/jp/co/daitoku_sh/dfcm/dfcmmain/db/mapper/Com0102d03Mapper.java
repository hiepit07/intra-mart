/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Com0102d0３Mapper.java
 * 
 * <p>作成者:グエン　リユオン　ギア
 * 作成日:2015/０９/２５
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/7 1.00 ABV)グエン　リユオン　ギア　 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstShop;

/**
 * サービスから呼び出されるMapper（実行するSQLを定義）
 *
 * @author グエン リユオン ギア
 * @version 1.0.0
 * @since 1.0.0
 */

public interface Com0102d03Mapper {

  /**
   * 得意先情報を取得する.
   * 
   * @param parms:parms
   * @return List
   */
  List<MstCustomer> getMstCustomerData(Map<String, Object> parms);

  /**
   * 検索した店舗情報を取得する.
   * 
   * @param parms:parms
   * @return List
   */
  List<MstShop> getSearchResult(Map<String, Object> parms);

  /**
   * 店舗情報を取得する.
   * 
   * @param params:parms
   * @return List
   */
  List<MstShop> getMstShopInfo(Map<String, Object> params);
}
