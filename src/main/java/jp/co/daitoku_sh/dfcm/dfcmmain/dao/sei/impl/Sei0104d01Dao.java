/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl
 * ファイル名:Sei0104d01Dao.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/11/29
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/29 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Sei0104d01Mapper;

/**
 * 請求締め取消処理用 Dao
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class Sei0104d01Dao {

  @Autowired
  private Sei0104d01Mapper sei0104d01Mapper;

  @Autowired
  private SqlSession session;

  /**
   * sei0104d01Mapper getter.
   * 
   */
  public Sei0104d01Mapper getSei0104d01Mapper() {
    return sei0104d01Mapper;
  }

  /**
   * sei0104d01Mapper setter.
   * 
   */
  public void setSei0104d01Mapper(Sei0104d01Mapper sei0104d01Mapper) {
    this.sei0104d01Mapper = sei0104d01Mapper;
  }

  // --------------------------------------------------------------------------
  // 請求締め取消処理
  // --------------------------------------------------------------------------

  /**
   * 請求ヘッダの更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateSeikyuHeader(Map<String, Object> params) {
    return session.update("updateSeikyuHeaderForTorikeshi", params);
  }

  /**
   * 前回請求ヘッダの更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updatePrevSeikyuHeader(Map<String, Object> params) {
    return session.update("updatePrevSeikyuHeaderForTorikeshi", params);
  }

  /**
   * 売上明細（売掛）の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUriageMeisaiUrikake(Map<String, Object> params) {
    return session.update("updateUriageMeisaiUrikakeForTorikeshi", params);
  }

  /**
   * 売上明細（未収）の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUriageMeisaiMishu(Map<String, Object> params) {
    return session.update("updateUriageMeisaiMishuForTorikeshi", params);
  }

  /**
   * 売上ヘッダ（売掛）の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUriageHeaderUrikake(Map<String, Object> params) {
    return session.update("updateUriageHeaderUrikakeForTorikeshi", params);
  }

  /**
   * 売上ヘッダ（未収）の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUriageHeaderMishu(Map<String, Object> params) {
    return session.update("updateUriageHeaderMishuForTorikeshi", params);
  }

  /**
   * 売掛明細の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateUrikakeMeisai(Map<String, Object> params) {
    return session.update("updateUrikakeMeisaiForTorikeshi", params);
  }

  /**
   * 未収明細の更新.
   * 
   * @param params パラメータ
   * @return 更新件数
   */
  public int updateMishuMeisai(Map<String, Object> params) {
    return session.update("updateMishuMeisaiForTorikeshi", params);
  }

}
