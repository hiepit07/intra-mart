/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0104d01Dao.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSJigyoMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0104d01Mapper;

/**
 * DAOクラス
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0104d01Dao {

  @Autowired
  private MstSJigyoMapper mstSJgyoMapper;
  @Autowired
  private Mst0104d01Mapper mst0104d01Mapper;

  public MstSJigyoMapper getMstSJgyoMapper() {
    return mstSJgyoMapper;
  }

  public void setMstSJgyoMapper(MstSJigyoMapper mstSJgyoMapper) {
    this.mstSJgyoMapper = mstSJgyoMapper;
  }

  public Mst0104d01Mapper getMst0104d01Mapper() {
    return mst0104d01Mapper;
  }

  public void setMst0104d01Mapper(Mst0104d01Mapper mst0104d01Mapper) {
    this.mst0104d01Mapper = mst0104d01Mapper;
  }

}