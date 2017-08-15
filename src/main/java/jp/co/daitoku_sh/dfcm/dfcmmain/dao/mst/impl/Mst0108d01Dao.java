/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0108d01Dao.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/11/01
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/01 1.00 ABV)NghiaNguyen 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSJigyoMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0108d01Mapper;

/**
 * DAOクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0108d01Dao {

  @Autowired
  private MstSJigyoMapper mstSJigyoMapper;

  @Autowired
  private Mst0108d01Mapper mst0108d01Mapper;

  public Mst0108d01Mapper getMst0108d01Mapper() {
    return mst0108d01Mapper;
  }

  public void setMst0108d01Mapper(Mst0108d01Mapper mst0108d01Mapper) {
    this.mst0108d01Mapper = mst0108d01Mapper;
  }

  public MstSJigyoMapper getMstSJigyoMapper() {
    return mstSJigyoMapper;
  }

  public void setMstSJigyoMapper(MstSJigyoMapper mstSJigyoMapper) {
    this.mstSJigyoMapper = mstSJigyoMapper;
  }

}