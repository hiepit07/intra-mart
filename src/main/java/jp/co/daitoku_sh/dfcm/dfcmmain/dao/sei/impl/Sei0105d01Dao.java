/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl
 * ファイル名:Sei0105d01Dao.java
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

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Sei0105d01Mapper;

/**
 * 取り纏め請求用 Dao
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class Sei0105d01Dao {

  @Autowired
  private Sei0105d01Mapper sei0105d01Mapper;

  @Autowired
  private SqlSession session;

  /**
   * sei0105d01Mapper getter.
   * 
   */
  public Sei0105d01Mapper getSei0105d01Mapper() {
    return sei0105d01Mapper;
  }

  /**
   * sei0105d01Mapper setter.
   * 
   */
  public void setSei0105d01Mapper(Sei0105d01Mapper sei0105d01Mapper) {
    this.sei0105d01Mapper = sei0105d01Mapper;
  }

  // ---------------------------------------------------------------------------
  // 実行
  // ---------------------------------------------------------------------------

  /**
   * 請求明細（取り纏め）の登録.
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int insertTorimatomeSeikyuMeisai(Map<String, Object> params) {
    return session.insert("insertTorimatomeSeikyuMeisai", params);
  }

  /**
   * 請求ヘッダ（取り纏め）の登録
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int insertTorimatomeSeikyuHeader(Map<String, Object> params) {
    return session.insert("insertTorimatomeSeikyuHeader", params);
  }

  /**
   * 売上明細の更新
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateUriageMeisaiToTorimatome(Map<String, Object> params) {
    return session.insert("updateUriageMeisaiToTorimatome", params);
  }

  /**
   * 売上ヘッダの更新
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateUriageHeaderToTorimatome(Map<String, Object> params) {
    return session.insert("updateUriageHeaderToTorimatome", params);
  }

  /**
   * 請求ヘッダ（各事業所）の更新
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateKobetsuSeikyuHeader(Map<String, Object> params) {
    return session.insert("updateKobetsuSeikyuHeader", params);
  }

  // ---------------------------------------------------------------------------
  // 取消
  // ---------------------------------------------------------------------------

  /**
   * 取り纏め請求ヘッダの更新（取消）
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateTorimatomeSeikyuHeaderToTorikeshi(
      Map<String, Object> params) {
    return session.insert("updateTorimatomeSeikyuHeaderToTorikeshi", params);
  }

  /**
   * 売上明細の更新
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateUriageMeisaiToTorimatomeTorikeshi(
      Map<String, Object> params) {
    return session.insert("updateUriageMeisaiToTorimatomeTorikeshi", params);
  }

  /**
   * 売上ヘッダの更新
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateUriageHeaderToTorimatomeTorikeshi(
      Map<String, Object> params) {
    return session.insert("updateUriageHeaderToTorimatomeTorikeshi", params);
  }

  /**
   * 個別請求ヘッダの更新
   * 
   * @param params パラメータ
   * @return 件数
   */
  public int updateKobetsuSeikyuHeaderReturn(Map<String, Object> params) {
    return session.insert("updateKobetsuSeikyuHeaderReturn", params);
  }

}
