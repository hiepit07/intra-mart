/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0109d02Dao.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/11/04
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/04 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstCorrectKbMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0109d02Mapper;

/**
 * DAOクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0109d02Dao {

  @Autowired
  private MstCorrectKbMapper mstCorrectKbMapper;
  
  @Autowired
  private Mst0109d02Mapper mst0109d02Mapper;

  public MstCorrectKbMapper getMstCorrectKbMapper() {
    return mstCorrectKbMapper;
  }

  public void setMstCorrectKbMapper(MstCorrectKbMapper mstCorrectKbMapper) {
    this.mstCorrectKbMapper = mstCorrectKbMapper;
  }

  public Mst0109d02Mapper getMst0109d02Mapper() {
    return mst0109d02Mapper;
  }

  public void setMst0109d02Mapper(Mst0109d02Mapper mst0109d02Mapper) {
    this.mst0109d02Mapper = mst0109d02Mapper;
  }

}