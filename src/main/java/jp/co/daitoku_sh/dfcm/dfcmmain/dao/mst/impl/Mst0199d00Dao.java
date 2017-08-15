/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0199d00Dao.java
 * 
 * <p>
 * 作成者:QuanTran
 * 作成日:2015/09/23
 * 
 * <p>
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/23 1.00 ABV)QuanTran
 * -------------------------------------------------------------------------
 * 
 * <p>
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstGeneralMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0199d00Mapper;

/**
 * DAOクラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0199d00Dao {

  @Autowired
  private Mst0199d00Mapper mst0199d00Mapper;
  
  @Autowired
  private MstGeneralMapper mstGeneralMapper;
  
  
  public MstGeneralMapper getMstGeneralMapper() {
    return mstGeneralMapper;
  }

  
  public void setMstGeneralMapper(MstGeneralMapper mstGeneralMapper) {
    this.mstGeneralMapper = mstGeneralMapper;
  }

  public Mst0199d00Mapper getMst0199d00Mapper() {
    return mst0199d00Mapper;
  }

  public void setMst0199d00Mapper(Mst0199d00Mapper mst0199d00Mapper) {
    this.mst0199d00Mapper = mst0199d00Mapper;
  }

}
