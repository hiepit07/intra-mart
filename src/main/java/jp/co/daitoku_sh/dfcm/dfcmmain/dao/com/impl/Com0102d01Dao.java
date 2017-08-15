/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl
 * ファイル名:Com0102d01Dao.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Com0102d01Mapper;
/**
 * DAOクラス
 * 
 * @author NGUYEN LUONG NGHIA
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Com0102d01Dao {

  @Autowired
  private Com0102d01Mapper com0102d01Mapper;

  /**
   * チェーンマスタ Mapperのgetter.
   * 
   * @return com0102d01Mapper
   */
  public Com0102d01Mapper getCom0102d01Mapper() {
    return com0102d01Mapper;
  }

  /**
   * チェーンマスタMapperのsetter.
   *
   * @param com0102d01Mapper:一覧選択用のMapper
   */
  public void setCom0102d01Mapper(Com0102d01Mapper com0102d01Mapper) {
    this.com0102d01Mapper = com0102d01Mapper;
  } 
}
