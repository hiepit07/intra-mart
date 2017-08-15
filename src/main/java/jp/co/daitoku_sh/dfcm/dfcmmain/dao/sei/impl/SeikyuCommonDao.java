/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl
 * ファイル名:SeikyuCommonDao.java
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

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl;

import jp.co.daitoku_sh.dfcm.common.db.mapper.TblSei01HeadMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblSei01SeiBodyMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.SeikyuCommonMapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 請求共通 Dao
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class SeikyuCommonDao {

  @Autowired
  private TblSei01HeadMapper tblSei01HeadMapper;

  @Autowired
  private TblSei01SeiBodyMapper tblSei01SeiBodyMapper;

  @Autowired
  private SeikyuCommonMapper seikyuCommonMapper;

  @Autowired
  private SqlSession session;

  // -------------------------------------------------------------------------
  // getter / setter
  // -------------------------------------------------------------------------

  /**
   * tblSei01HeadMapper getter.
   * 
   */
  public TblSei01HeadMapper getTblSei01HeadMapper() {
    return tblSei01HeadMapper;
  }

  /**
   * tblSei01HeadMapper setter.
   * 
   */
  public void setTblSei01HeadMapper(TblSei01HeadMapper tblSei01HeadMapper) {
    this.tblSei01HeadMapper = tblSei01HeadMapper;
  }

  /**
   * tblSei01SeiBodyMapper getter.
   * 
   */
  public TblSei01SeiBodyMapper getTblSei01SeiBodyMapper() {
    return tblSei01SeiBodyMapper;
  }

  /**
   * tblSei01SeiBodyMapper setter.
   * 
   */
  public void setTblSei01SeiBodyMapper(
      TblSei01SeiBodyMapper tblSei01SeiBodyMapper) {
    this.tblSei01SeiBodyMapper = tblSei01SeiBodyMapper;
  }

  /**
   * seikyuCommonMapper getter.
   * 
   */
  public SeikyuCommonMapper getSeikyuCommonMapper() {
    return seikyuCommonMapper;
  }

  /**
   * seikyuCommonMapper setter.
   * 
   */
  public void setSeikyuCommonMapper(SeikyuCommonMapper seikyuCommonMapper) {
    this.seikyuCommonMapper = seikyuCommonMapper;
  }

  // -------------------------------------------------------------------------
  // 請求データの作成
  // -------------------------------------------------------------------------

  /**
   * 請求明細（集約店舗）の更新.
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateSeikyuMeisaiForShuyakuTempo(Map<String, Object> params) {
    return session.update("updateSeikyuMeisaiForShuyakuTempo", params);
  }

  /**
   * 請求ヘッダ.消費税の更新.
   * 
   * @return 更新件数
   */
  public int updateTaxOnSeikyuHeader(Map<String, Object> params) {
    return session.update("updateTaxOnSeikyuHeader", params);
  }

  /**
   * 売掛明細の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUrikakeMeisai(Map<String, Object> params) {
    return session.update("updateUrikakeMeisai", params);
  }

  /**
   * 未収明細の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateMishuMeisai(Map<String, Object> params) {
    return session.update("updateMishuMeisai", params);
  }

  /**
   * 前回請求ヘッダの更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updatePrevSeikyuHeader(Map<String, Object> params) {
    return session.update("updatePrevSeikyuHeader", params);
  }

  /**
   * 売上明細（売掛）の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUriageMeisaiUrikake(Map<String, Object> params) {
    return session.update("updateUriageMeisaiUrikake", params);
  }

  /**
   * 売上明細（未収）の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUriageMeisaiMishu(Map<String, Object> params) {
    return session.update("updateUriageMeisaiMishu", params);
  }

  /**
   * 売上ヘッダ（売掛）の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUriageHeaderUrikake(Map<String, Object> params) {
    return session.update("updateUriageHeaderUrikake", params);
  }

  /**
   * 売上ヘッダ（未収）の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUriageHeaderMishu(Map<String, Object> params) {
    return session.update("updateUriageHeaderMishu", params);
  }

  // -------------------------------------------------------------------------
  // 請求書印刷フラグ更新
  // -------------------------------------------------------------------------

  /**
   * 売上明細（売掛）.請求書印刷フラグの更新.
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateInsatsuFlagOnUriageMeisaiUrikake(
      Map<String, Object> params) {
    return session.update("updateInsatsuFlagOnUriageMeisaiUrikake", params);
  }

  /**
   * 売上明細（未収）.請求書印刷フラグの更新.
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateInsatsuFlagOnUriageMeisaiMishu(Map<String, Object> params) {
    return session.update("updateInsatsuFlagOnUriageMeisaiMishu", params);
  }

  /**
   * 売上ヘッダ（売掛）.請求書印刷フラグの更新.
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateInsatsuFlagOnUriageHeaderUrikake(
      Map<String, Object> params) {
    return session.update("updateInsatsuFlagOnUriageHeaderUrikake", params);
  }

  /**
   * 売上ヘッダ（未収）.請求書印刷フラグの更新.
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateInsatsuFlagOnUriageHeaderMishu(Map<String, Object> params) {
    return session.update("updateInsatsuFlagOnUriageHeaderMishu", params);
  }

  // -------------------------------------------------------------------------
  // 請求データ送信フラグ
  // -------------------------------------------------------------------------

  /**
   * 請求ヘッダ.請求データ送信フラグの更新.
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateSeikyuSendFlagOnSeikyuHeader(Map<String, Object> params) {
    return session.update("updateSeikyuSendFlagOnSeikyuHeader", params);
  }

}
