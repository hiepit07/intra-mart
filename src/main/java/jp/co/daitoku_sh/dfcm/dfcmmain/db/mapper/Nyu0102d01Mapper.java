/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:Nyu0102d01Mapper.java
 * 
 * 作成者:都築電気
 * 作成日:2015/12/04
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/04 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper;

import java.util.List;
import java.util.Map;

/**
 * 入金登録用SQL Mapper.
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface Nyu0102d01Mapper {

  /**
   * 権限区分の取得.
   * 
   * @param params パラメータ
   * @return 検索結果
   */
  String getAuthKbn(Map<String, Object> params);
  
  /**
   * 科目リストの取得
   *   科目コード == null 初期表示（明細部表示）
   *   科目コード != null 行追加部（選択科目の情報を取得）
   * @param kamokuCd 科目コード
   * @return 検索結果
   */
  List<Map<String, Object>> getKamokuInfoList(String kamokuCd);
  
  /**
   * 発生場所リストの取得
   * @return 検索結果
   */
  List<Map<String, Object>> getHasseiBashoList();
  
  /**
   * 追加科目リストの取得
   * （行追加部）
   * @return 検索結果
   */
  List<Map<String, Object>> getAddKamokuList();
  
  /**
   * 入金調整売上登録 調整額の取得
   * @param params パラメータ
   * @return 検索結果
   */
  int getNyukinChoseiGaku(Map<String, Object> params);
  
  /**
   * 都度請求情報リストの取得
   * @param seikyuId 請求ID
   * @return 検索結果
   */
  List<Map<String, Object>> getTsudoSeikyuInfoList(String seikyuId);
  
  /**
   * 選択科目（一般・関係会社・G会社）情報の取得
   * @param params パラメータ
   * @return 検索結果
   */
  List<Map<String, Object>> getSelectKamokuInfo(Map<String, Object> params);
  
  /**
   * 貸方科目データの取得
   * @param params パラメータ
   * @return 検索結果
   */
  List<Map<String, Object>> getKashikataKamokuData(Map<String, Object> params);
  
  /**
   * 入金データの取得
   * @param nyukinDempyoNo 入金伝票No
   * @return 検索結果
   */
  List<Map<String, Object>> getNyukinInfo(String nyukinDempyoNo);
  
  /**
   * 入金仕訳データリストの取得
   * @param params パラメータ
   * @return 検索結果
   */
  List<Map<String, Object>> getNyukinShiwakeList(Map<String, Object> params);
  
  /**
   * 残高理由入力データリストの取得
   * @param nyukinDempyoNo 入金伝票No
   * @return 検索結果
   */
  List<Map<String, Object>> getZandakaRiyuInputDataList(String nyukinDempyoNo);
  
}
