/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0103d01Dao.java
 * 
 * <p>作成者:ABV)TramChu
 * 作成日:2015/08/24
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付                 指示者      担当            内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00                  ABV)TramChu       新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
*/

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUserMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0103d01Mapper;

/**
 *　DAOクラス
 * 
 * @author ABV)TramChu
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0103d01Dao {

  @Autowired
  private Mst0103d01Mapper mst0103d01Mapper;

  @Autowired
  private MstUserMapper mstUserMapper;

  public Mst0103d01Mapper getMst0103d01Mapper() {
    return mst0103d01Mapper;
  }

  public void setMst0103d01Mapper(Mst0103d01Mapper mst0103d01Mapper) {
    this.mst0103d01Mapper = mst0103d01Mapper;
  }

  public MstUserMapper getMstUserMapper() {
    return mstUserMapper;
  }

  public void setMstUserMapper(MstUserMapper mstUserMapper) {
    this.mstUserMapper = mstUserMapper;
  }

}