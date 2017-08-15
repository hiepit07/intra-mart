/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Get0101D00Dao.java
 * 作成者:TuanNguyen
 * 作成日:2015/08/24
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)TuanNguyen 新規開発
 * -------------------------------------------------------------------------
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.get.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Get0101D00Mapper;

/**
 * DAOクラス
 * 
 * @author TuanNguyen
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Get0101D00Dao {

  // Get0101D00Mapper
  @Autowired
  private Get0101D00Mapper get0101D00Mapper;

  public Get0101D00Mapper getGet0101D00Mapper() {
    return get0101D00Mapper;
  }

  public void setGet0101D00Mapper(Get0101D00Mapper get0101D00Mapper) {
    this.get0101D00Mapper = get0101D00Mapper;
  }
}