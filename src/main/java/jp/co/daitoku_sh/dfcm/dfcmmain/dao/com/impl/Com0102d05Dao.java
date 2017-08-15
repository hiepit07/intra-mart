/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.dao.impl
 * ファイル名:Com0101d05Dao.java
 * 
 * <p>作成者:NghiaNguyen
 * 作成日:2015/10/14
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/14 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUserMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Com0102d05Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @author NGUYEN LUONG NGHIA
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Com0102d05Dao {

  @Autowired
  private Com0102d05Mapper com0102d05Mapper;

  @Autowired
  private MstUserMapper mstUserMapper;

  /**
   * チェーンマスタ Mapperのgetter.
   * 
   * @return com0102d01Mapper
   */
  public Com0102d05Mapper getCom0102d05Mapper() {
    return com0102d05Mapper;
  }

  /**
   * チェーンマスタMapperのsetter.
   *
   * @param com0102d01Mapper:一覧選択用のMapper
   */
  public void setCom0102d05Mapper(Com0102d05Mapper com0102d05Mapper) {
    this.com0102d05Mapper = com0102d05Mapper;
  }

  /**
   * チェーンマスタMapperのgetter.
   *
   * @return mstSChainMapper: 一覧選択用のMapper
   */
  public MstUserMapper getMstUserMapper() {
    return mstUserMapper;
  }

  /**
   * チェーンマスタMapperのsetter.
   *
   * @param mstSChainMapper:チェーンマスタのMapper
   */
  public void setMstUserMapper(MstUserMapper mstUserMapper) {
    this.mstUserMapper = mstUserMapper;
  }
}
