/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0102d01Dao.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSChainMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSJigyoMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUserMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0102d01Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DAOクラス
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0102d01Dao {

  /** 事業所マスタMapper. */
  @Autowired
  private MstSJigyoMapper mstSJigyoMapper;

  /** チェーンマスタMapper. */
  @Autowired
  private MstSChainMapper mstSChainMapper;

  /** 担当者マスタMapper. */
  @Autowired
  private MstUserMapper mstUserMapper;

  /** Mst0102d01Mapper. */
  @Autowired
  private Mst0102d01Mapper mst0102d01Mapper;

  public MstSJigyoMapper getMstSJigyoMapper() {
    return mstSJigyoMapper;
  }

  public void setMstSJigyoMapper(MstSJigyoMapper mstSJigyoMapper) {
    this.mstSJigyoMapper = mstSJigyoMapper;
  }

  public MstSChainMapper getMstSChainMapper() {
    return mstSChainMapper;
  }

  public void setMstSChainMapper(MstSChainMapper mstSChainMapper) {
    this.mstSChainMapper = mstSChainMapper;
  }

  public MstUserMapper getMstUserMapper() {
    return mstUserMapper;
  }

  public void setMstUserMapper(MstUserMapper mstUserMapper) {
    this.mstUserMapper = mstUserMapper;
  }

  public Mst0102d01Mapper getMst0102d01Mapper() {
    return mst0102d01Mapper;
  }

  public void setMst0102d01Mapper(Mst0102d01Mapper mst0102d01Mapper) {
    this.mst0102d01Mapper = mst0102d01Mapper;
  }
}