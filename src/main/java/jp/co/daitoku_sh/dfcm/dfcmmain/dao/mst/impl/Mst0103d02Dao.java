/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0103d02Dao.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00                  ABV)TramChu        新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstDatIdxMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstShopMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUserMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0103d02Mapper;

/**
 *　DAOクラス
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0103d02Dao {

  @Autowired
  private Mst0103d02Mapper mst0103d02Mapper;

  @Autowired
  private MstUserMapper mstUserMapper;
  @Autowired
  private MstShopMapper mstShopMapper;
  @Autowired
  private MstDatIdxMapper mstDatIdxMapper;

  public Mst0103d02Mapper getMst0103d02Mapper() {
    return mst0103d02Mapper;
  }

  public void setMst0103d02Mapper(Mst0103d02Mapper mst0103d02Mapper) {
    this.mst0103d02Mapper = mst0103d02Mapper;
  }

  public MstUserMapper getMstUserMapper() {
    return mstUserMapper;
  }

  public void setMstUserMapper(MstUserMapper mstUserMapper) {
    this.mstUserMapper = mstUserMapper;
  }

  public MstShopMapper getMstShopMapper() {
    return mstShopMapper;
  }

  public void setMstShopMapper(MstShopMapper mstShopMapper) {
    this.mstShopMapper = mstShopMapper;
  }

  public MstDatIdxMapper getMstDatIdxMapper() {
    return mstDatIdxMapper;
  }

  public void setMstDatIdxMapper(MstDatIdxMapper mstDatIdxMapper) {
    this.mstDatIdxMapper = mstDatIdxMapper;
  }

}