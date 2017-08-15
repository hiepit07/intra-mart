/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0102d02Dao.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/09/21
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/21 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */
package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstCustjgyoMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstCustomerMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstDatIdxMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstListFormMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSubjectCdMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0102d02Mapper;

/**
 * DAOクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0102d02Dao {

  /** Mst0102d02Mapper. */
  @Autowired
  private Mst0102d02Mapper mst0102d02Mapper;

  /** 帳票定義マスタMapper. */
  @Autowired
  private MstListFormMapper mstListFormMapper;

  /** 得意先マスタMapper. */
  @Autowired
  private MstCustomerMapper mstCustomerMapper;

  /** 得意先事業所マスタMapper. */
  @Autowired
  private MstCustjgyoMapper mstCustjgyoMapper;

  /** 勘定科目マスタMapper. */
  @Autowired
  private MstSubjectCdMapper mstSubjectCdMapper;

  /** 伝票採番マスタMapper. */
  @Autowired
  private MstDatIdxMapper mstDatIdxMapper;

  public Mst0102d02Mapper getMst0102d02Mapper() {
    return mst0102d02Mapper;
  }

  public void setMst0102d02Mapper(Mst0102d02Mapper mst0102d02Mapper) {
    this.mst0102d02Mapper = mst0102d02Mapper;
  }

  public MstListFormMapper getMstListFormMapper() {
    return mstListFormMapper;
  }

  public void setMstListFormMapper(MstListFormMapper mstListFormMapper) {
    this.mstListFormMapper = mstListFormMapper;
  }

  public MstCustomerMapper getMstCustomerMapper() {
    return mstCustomerMapper;
  }

  public void setMstCustomerMapper(MstCustomerMapper mstCustomerMapper) {
    this.mstCustomerMapper = mstCustomerMapper;
  }

  public MstCustjgyoMapper getMstCustjgyoMapper() {
    return mstCustjgyoMapper;
  }

  public void setMstCustjgyoMapper(MstCustjgyoMapper mstCustjgyoMapper) {
    this.mstCustjgyoMapper = mstCustjgyoMapper;
  }

  public MstSubjectCdMapper getMstSubjectCdMapper() {
    return mstSubjectCdMapper;
  }

  public void setMstSubjectCdMapper(MstSubjectCdMapper mstSubjectCdMapper) {
    this.mstSubjectCdMapper = mstSubjectCdMapper;
  }

  public MstDatIdxMapper getMstDatIdxMapper() {
    return mstDatIdxMapper;
  }

  public void setMstDatIdxMapper(MstDatIdxMapper mstDatIdxMapper) {
    this.mstDatIdxMapper = mstDatIdxMapper;
  }
}