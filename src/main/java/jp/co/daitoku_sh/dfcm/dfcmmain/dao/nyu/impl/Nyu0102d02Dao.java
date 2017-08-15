/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl
 * ファイル名:Nyu0102d02Dao.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/02
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/02 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Nyu0102d02Mapper;

/**
 * 締め請求未回収設定用 Dao
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class Nyu0102d02Dao {

  @Autowired
  private Nyu0102d02Mapper nyu0102d02Mapper;

  /**
   * nyu0102d02Mapper getter.
   * 
   */
  public Nyu0102d02Mapper getNyu0102d02Mapper() {
    return nyu0102d02Mapper;
  }

  /**
   * nyu0102d02Mapper setter.
   * 
   */
  public void setNyu0102d02Mapper(Nyu0102d02Mapper nyu0102d02Mapper) {
    this.nyu0102d02Mapper = nyu0102d02Mapper;
  }

}
