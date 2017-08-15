/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl
 * ファイル名:Com0102d04Dao.java
 * 
 * <p>作成者:NghiaNguyen
 * 作成日:2015/09/16
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/16 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Com0102d04Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Com0102d04Dao {

  @Autowired
  private Com0102d04Mapper com0102d04Mapper;

  /*
   * Com0102d04Mapper getter.
   * 
   * @return com0102d04Mapper
   */
  public Com0102d04Mapper getCom0102d04Mapper() {
    return com0102d04Mapper;
  }

  /*
   * setCom0102d04Mapper setter.
   * 
   * @param com0102d04Mapper
   */
  public void setCom0102d04Mapper(Com0102d04Mapper com0102d04Mapper) {
    this.com0102d04Mapper = com0102d04Mapper;
  }

}
