/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl
 * ファイル名:Com0102d03Dao.java
 * 作成者:NghiaNguyen
 * 作成日:2015/09/23
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Com0102d03Mapper;

/**
 * 
 * 店舗のDAOクラス
 * @author NGUYEN LUONG NGHIA
 * @version 1.0.0
 * @since 1.0.0
 */

@Component
public class Com0102d03Dao {

  @Autowired
  private Com0102d03Mapper com0102d03Mapper;

  /**
   * Com0102d03Mapper getCom0102d03Mapper.
   * 
   * @return com0102d03Mapper
   */
  public Com0102d03Mapper getCom0102d03Mapper() {
    return com0102d03Mapper;
  }

  /**
   * Com0102d03Mapper setCom0102d03Mapper.
   * 
   * @param com0102d03Mapper:com0102d03Mapper
   */
  public void setCom0102d03Mapper(Com0102d03Mapper com0102d03Mapper) {
    this.com0102d03Mapper = com0102d03Mapper;
  }
}
