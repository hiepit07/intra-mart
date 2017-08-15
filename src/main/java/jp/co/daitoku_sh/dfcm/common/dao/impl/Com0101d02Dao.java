/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.dao.impl
 * ファイル名:Com0101d02Dao.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/17
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/17 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 *<p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.Com0101d02Mapper;

/**
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Com0101d02Dao {

  @Autowired
  private Com0101d02Mapper com0101d02Mapper;

  /**
   * @return the com0101d02Mapper
   */
  public Com0101d02Mapper getCom0101d02Mapper() {
    return com0101d02Mapper;
  }

  /**
   * @param com0101d02Mapper the com0101d02Mapper to set
   */
  public void setCom0101d02Mapper(Com0101d02Mapper com0101d02Mapper) {
    this.com0101d02Mapper = com0101d02Mapper;
  }

}
