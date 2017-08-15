/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0104d02Dao.java
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

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstCourseListMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstCourseMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstGeneralMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSJigyoMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstShopMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0104d02Mapper;

/**
 * DAOクラス
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0104d02Dao {

  @Autowired
  private MstSJigyoMapper mstSJgyoMapper;

  @Autowired
  private MstCourseMapper mstCourseMapper;

  @Autowired
  private Mst0104d02Mapper mst0104d02Mapper;

  @Autowired
  private MstGeneralMapper mstGeneralMapper;

  @Autowired
  private MstShopMapper mstShopMapper;

  @Autowired
  private MstCourseListMapper mstCourseListMapper;

  public MstSJigyoMapper getMstSJgyoMapper() {
    return mstSJgyoMapper;
  }

  public void setMstSJgyoMapper(MstSJigyoMapper mstSJgyoMapper) {
    this.mstSJgyoMapper = mstSJgyoMapper;
  }

  public MstCourseMapper getMstCourseMapper() {
    return mstCourseMapper;
  }

  public void setMstCourseMapper(MstCourseMapper mstCourseMapper) {
    this.mstCourseMapper = mstCourseMapper;
  }

  public Mst0104d02Mapper getMst0104d02Mapper() {
    return mst0104d02Mapper;
  }

  public void setMst0104d02Mapper(Mst0104d02Mapper mst0104d02Mapper) {
    this.mst0104d02Mapper = mst0104d02Mapper;
  }

  public MstGeneralMapper getMstGeneralMapper() {
    return mstGeneralMapper;
  }

  public void setMstGeneralMapper(MstGeneralMapper mstGeneralMapper) {
    this.mstGeneralMapper = mstGeneralMapper;
  }

  public MstShopMapper getMstShopMapper() {
    return mstShopMapper;
  }

  public void setMstShopMapper(MstShopMapper mstShopMapper) {
    this.mstShopMapper = mstShopMapper;
  }

  public MstCourseListMapper getMstCourseListMapper() {
    return mstCourseListMapper;
  }

  public void setMstCourseListMapper(MstCourseListMapper mstCourseListMapper) {
    this.mstCourseListMapper = mstCourseListMapper;
  }

}