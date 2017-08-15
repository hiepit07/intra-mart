/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl
 * ファイル名:Nyu0102d01Dao.java
 * 
 * 作成者:都築電気株式会社
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

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Nyu0102d01Mapper;

/**
 * 入金登録用 Dao
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class Nyu0102d01Dao {

  @Autowired
  private Nyu0102d01Mapper nyu0102d01Mapper;

  /**
   * nyu0102d01Mapper getter.
   * 
   */
  public Nyu0102d01Mapper getNyu0102d01Mapper() {
    return nyu0102d01Mapper;
  }

  /**
   * nyu0102d01Mapper setter.
   * 
   */
  public void setNyu0102d01Mapper(Nyu0102d01Mapper nyu0102d01Mapper) {
    this.nyu0102d01Mapper = nyu0102d01Mapper;
  }

}
