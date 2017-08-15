/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl
 * ファイル名:Sei0103d01Dao.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/11/27
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/27 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Sei0103d01Mapper;

/**
 * 臨時請求締め処理用 Dao
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class Sei0103d01Dao {

  @Autowired
  private Sei0103d01Mapper sei0103d01Mapper;

  /**
   * sei0103d01Mapper getter.
   * 
   */
  public Sei0103d01Mapper getSei0103d01Mapper() {
    return sei0103d01Mapper;
  }

  /**
   * sei0103d01Mapper setter.
   * 
   */
  public void setSei0103d01Mapper(Sei0103d01Mapper sei0103d01Mapper) {
    this.sei0103d01Mapper = sei0103d01Mapper;
  }

}
