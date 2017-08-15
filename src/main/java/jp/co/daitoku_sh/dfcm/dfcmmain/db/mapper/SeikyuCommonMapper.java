/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper
 * ファイル名:SeikyuCommonMapper.java
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

import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.OkuwaTempoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyuHeaderInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyuTempoInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.sei.SeikyuTokuisakiInfo;

import org.apache.ibatis.session.ResultHandler;

import java.util.List;
import java.util.Map;

/**
 * 請求共通用SQL Mapper.
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface SeikyuCommonMapper {

  /**
   * 事業所リストの取得.
   * 
   * @param businessDate
   *          業務日付
   * @return 検索結果
   */
  List<Map<String, Object>> getJigyoshoList(String businessDate);

  /**
   * 当月売掛月度の取得.
   * 
   * @param jigyoshoCd
   *          事業所コード
   * @return 当月売掛月度
   */
  String getTogetsuUrikakeGetsudo(String jigyoshoCd);

  /**
   * 帳票情報の取得.
   * 
   * @param chohyoId
   *          帳票ID
   * @return 検索結果
   */
  Map<String, Object> getChohyoInfo(String chohyoId);

  /**
   * 請求データ配信バッチパスの取得.
   * 
   * @param id
   *          HULFT-ID
   * @return 検索結果
   */
  String getSeikyuDataHaishinBatchPath(String id);

  /**
   * 請求締め済みの確認
   * @param params パラメータ
   * @return 請求ID
   */
  String checkSeikyuShimeSumi(Map<String, Object> params);
  
  // -------------------------------------------------------------------------
  // 請求データの作成
  // -------------------------------------------------------------------------

  /**
   * 請求明細元データ取得（逐次処理）.
   * 
   * @param params パラメータ
   * @param handler ハンドラ
   * @return void
   */
  void getSeikyuMeisaiMotoDataList(Map<String, Object> params,
      ResultHandler handler);

  /**
   * 得意先計・店舗計消費税の取得.
   * 
   * @param params パラメータ
   * @return 消費税
   */
  int getTaxTokuisakiOrTempoSummary(Map<String, Object> params);

  /**
   * 請求ヘッダ数の取得.
   * 
   * @param params パラメータ
   * @return レコード数
   */
  int getSeikyuHeaderCount(Map<String, Object> params);

  // -------------------------------------------------------------------------
  // 請求書出力
  // -------------------------------------------------------------------------

  /**
   * 請求ヘッダー情報の取得.
   * 
   * @param params パラメータ
   * @return 請求ヘッダ情報
   */
  SeikyuHeaderInfo getSeikyuHeaderInfo(Map<String, Object> params);

  // -------------------------------------------------------------------------
  // 自社請求書
  // -------------------------------------------------------------------------

  /**
   * 得意先情報リストの取得.
   * 
   * @param params パラメータ
   * @return 検索結果
   */
  List<SeikyuTokuisakiInfo> getSeikyuTokuisakiInfoList(
      Map<String, Object> params);

  /**
   * 店舗情報リストの取得.
   * 
   * @param params パラメータ
   * @return 検索結果
   */
  List<SeikyuTempoInfo> getSeikyuTempoInfoList(Map<String, Object> params);

  /**
   * 明細情報リストの取得（逐次処理）.
   * 
   * @param params パラメータ
   * @param handler ハンドラ
   * @return void
   */
  void getSeikyuMeisaiInfoList(Map<String, Object> params,
      ResultHandler handler);

  // -------------------------------------------------------------------------
  // イオン請求書
  // -------------------------------------------------------------------------

  /**
   * イオン明細情報リストの取得（逐次処理）.
   * 
   * @param seikyuId
   *          請求ID
   * @param handler ハンドラ
   * @return void
   */
  void getAeonMeisaiInfoList(String seikyuId, ResultHandler handler);

  /**
   * 当月納品・返品情報の取得.
   * 
   * @param seikyuId
   *          請求ID
   * @return 検索結果
   */
  Map<String, Object> getAeonTogetsuNohinHempinInfo(String seikyuId);

  // -------------------------------------------------------------------------
  // オークワ請求書
  // -------------------------------------------------------------------------

  /**
   * オークワ店舗情報リストの取得（鑑）.
   * 
   * @param seikyuId
   *          請求ID
   * @return 検索結果
   */
  List<OkuwaTempoInfo> getOkuwaTempoInfoList(String seikyuId);

  /**
   * オークワ明細情報リストの取得（逐次処理）.
   * 
   * @param seikyuId
   *          請求ID
   * @param handler ハンドラ
   * @return void
   */
  void getOkuwaMeisaiInfoList(String seikyuId, ResultHandler handler);

  // -------------------------------------------------------------------------
  // 大学生協請求書
  // -------------------------------------------------------------------------

  /**
   * 大学生協明細情報リストの取得（逐次処理）.
   * 
   * @param seikyuId
   *          請求ID
   * @param handler ハンドラ
   * @return void
   */
  void getDaigakuSeikyoMeisaiInfoList(String seikyuId, ResultHandler handler);

  /**
   * 大学生協鏡情報の取得（鑑）.
   * 
   * @param seikyuId
   *          請求ID
   * @return 検索結果
   */
  Map<String, Object> getDaigakuSeikyoKagamiInfo(String seikyuId);

}
