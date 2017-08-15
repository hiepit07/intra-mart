/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.dao.impl
 * ファイル名:Com0101d01Dao.java
 * 
 * <p>
 * 作成者:QuanTran
 * 作成日:2015/09/16
 * 
 * <p>
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/16 1.00 ACT)福田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUserMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Com0101d01Mapper;

/**
 * 
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Com0101d01Dao {

  @Autowired
  private Com0101d01Mapper com0101d01Mapper;
  
  @Autowired
  private MstUserMapper mstUserMapper;

  /**
   * @return the com0101d01Mapper
   */
  public Com0101d01Mapper getCom0101d01Mapper() {
    return com0101d01Mapper;
  }

  /**
   * @param com0101d01Mapper the com0101d01Mapper to set
   */
  public void setCom0101d01Mapper(Com0101d01Mapper com0101d01Mapper) {
    this.com0101d01Mapper = com0101d01Mapper;
  }

  public MstUserMapper getMstUserMapper() {
    return mstUserMapper;
  }

  public void setMstUserMapper(MstUserMapper mstUserMapper) {
    this.mstUserMapper = mstUserMapper;
  }

}
