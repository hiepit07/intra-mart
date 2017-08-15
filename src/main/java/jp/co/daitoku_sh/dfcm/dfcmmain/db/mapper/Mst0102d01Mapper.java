/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.db.mapper
 * ファイル名:CommonMapper.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/09/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/01 1.00 ACT)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.mst.Mst0102d01CustomerList;

import org.apache.ibatis.session.ResultHandler;

import java.util.List;
import java.util.Map;

/**
 * Mst0102d01 Mapperクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mst0102d01Mapper {

  /**
   * 得意先一覧データ取得.
   *
   * @param parms データ取得条件
   * @return 得意先一覧情報
   */
  List<Mst0102d01CustomerList> getCustomerList(Map<String, Object> parms);

  /**
   * CSV出力データ取得.
   *
   * @param parms データ取得条件
   * @return CSV出力データ情報
   */
  void getCsvOutputData(Map<String, Object> parms, ResultHandler handler);

  /**
   * チェーンマスタからチェーン名称取得.
   *
   * @param parms データ取得条件
   * @return チェーン名称
   */
  String getChainName(Map<String, Object> parms);
}
