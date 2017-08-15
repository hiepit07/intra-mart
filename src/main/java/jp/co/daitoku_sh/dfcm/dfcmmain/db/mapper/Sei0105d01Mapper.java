/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Sei0105d01Mapper.java
 * 
 * 作成者:都築電気
 * 作成日:2015/10/29
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/29 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyusakiInfo;

import java.util.List;
import java.util.Map;

/**
 * 取り纏め請求画面用SQL Mapper
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface Sei0105d01Mapper {

  /**
   * 請求先情報リストの取得.
   * 
   * @param params パラメータ
   * @return 検索結果
   */
  List<SeikyusakiInfo> getSeikyusakiInfoList(Map<String, Object> params);
  
  /**
   * 取り纏め請求ヘッダの取得
   * @param params パラメータ
   * @return 取得結果
   */
  Map<String, Object> getTorimatomeSeikyuHeader(Map<String, Object> params);
  
  /**
   * 取り纏め排他件数の取得
   * @param params パラメータ
   * @return レコード数
   */
  int getTorimatomeHaitaCount(Map<String, Object> params);
  
  /**
   * 消費税の取得（消費税計算単位 = 請求先）
   * @param params　パラメータ
   * @return 消費税
   */
  int getTaxTorimatomeHeader(Map<String, Object> params);

}
