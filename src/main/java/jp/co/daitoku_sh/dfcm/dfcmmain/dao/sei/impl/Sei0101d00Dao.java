/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl
 * ファイル名:Sei0101d00Dao.java
 * 
 * <p>作成者:
 * 作成日:2015/10/27
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/08/24 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSChainMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSJigyoMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUserMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Sei0101d00Mapper;

/**
 * DAOクラス
 * 
 * @author
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Sei0101d00Dao {
  @Autowired
  private Sei0101d00Mapper sei0101d01Mapper;

  @Autowired
  private MstUserMapper mstUserMapper;
  
  /** 事業所マスタMapper. */
  @Autowired
  private MstSJigyoMapper mstSJigyoMapper;

  /** チェーンマスタMapper. */
  @Autowired
  private MstSChainMapper mstSChainMapper;
  
  public MstSChainMapper getMstSChainMapper() {
    return mstSChainMapper;
  }

  public void setMstSChainMapper(MstSChainMapper mstSChainMapper) {
    this.mstSChainMapper = mstSChainMapper;
  }
  
  public Sei0101d00Mapper getSei0101d01Mapper() {
    return sei0101d01Mapper;
  }

  public void setSei0101d01Mapper(Sei0101d00Mapper sei0101d01Mapper) {
    this.sei0101d01Mapper = sei0101d01Mapper;
  }

  public MstUserMapper getMstUserMapper() {
    return mstUserMapper;
  }

  public void setMstUserMapper(MstUserMapper mstUserMapper) {
    this.mstUserMapper = mstUserMapper;
  }

  public MstSJigyoMapper getMstSJigyoMapper() {
    return mstSJigyoMapper;
  }

  public void setMstSJigyoMapper(MstSJigyoMapper mstSJigyoMapper) {
    this.mstSJigyoMapper = mstSJigyoMapper;
  }
}
