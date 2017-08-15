/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl
 * ファイル名:Sei0102d01Dao.java
 * 
 * 作成者:都築電気株式会社
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

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Sei0102d01Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 請求締め処理用 Dao
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class Sei0102d01Dao {

  @Autowired
  private Sei0102d01Mapper sei0102d01Mapper;

  /**
   * sei0102d01Mapper getter.
   * 
   */
  public Sei0102d01Mapper getSei0102d01Mapper() {
    return sei0102d01Mapper;
  }

  /**
   * sei0102d01Mapper setter.
   * 
   */
  public void setSei0102d01Mapper(Sei0102d01Mapper sei0102d01Mapper) {
    this.sei0102d01Mapper = sei0102d01Mapper;
  }

}
