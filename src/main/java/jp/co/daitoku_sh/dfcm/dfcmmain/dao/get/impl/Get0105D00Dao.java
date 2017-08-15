/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Get0105D00Dao.java
 * 
 * 作成日:2015/10/21
 * 
 *<p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/21 1.00 ABV)HiepTruong
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.get.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Get0105D00Mapper;

/**
 * DAOクラス
 * 
 * @author HiepTruong
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Get0105D00Dao {

  // Get0105D00Mapper
  @Autowired
  private Get0105D00Mapper get0105D00Mapper;

  public Get0105D00Mapper getGet0105D00Mapper() {
    return get0105D00Mapper;
  }

  public void setGet0105D00Mapper(Get0105D00Mapper get0105D00Mapper) {
    this.get0105D00Mapper = get0105D00Mapper;
  }
}