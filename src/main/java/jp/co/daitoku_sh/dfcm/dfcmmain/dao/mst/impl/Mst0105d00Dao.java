/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0105d00Dao.java
 * 
 * <p>
 * 作成者:QuanTran
 * 作成日:2015/10/01
 * 
 * <p>
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/01 1.00 ABV)QuanTran
 * -------------------------------------------------------------------------
 * 
 * <p>
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstCustConvMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0105d00Mapper;

/**
 * DAOクラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0105d00Dao {

  @Autowired
  private Mst0105d00Mapper mst0105d00Mapper;

  @Autowired
  private MstCustConvMapper mstCustConvMapper;

  public MstCustConvMapper getMstCustConvMapper() {
    return mstCustConvMapper;
  }

  public void setMstCustConvMapper(MstCustConvMapper mstCustConvMapper) {
    this.mstCustConvMapper = mstCustConvMapper;
  }

  public Mst0105d00Mapper getMst0105d00Mapper() {
    return mst0105d00Mapper;
  }

  public void setMst0105d00Mapper(Mst0105d00Mapper mst0105d00Mapper) {
    this.mst0105d00Mapper = mst0105d00Mapper;
  }

}
