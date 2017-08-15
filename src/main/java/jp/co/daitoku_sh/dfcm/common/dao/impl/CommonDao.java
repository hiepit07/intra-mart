/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.dao.impl
 * ファイル名:CommonDao.java
 *
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/09/01
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/01 1.00 ACT)福田 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.BildCodeMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.CommonMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstDatIdxMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstGeneralMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstJobexecMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstListDistMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstListFormMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSJigyoMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSNohinMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSSeijgyoMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSysCtlMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUniqIdxMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUserMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblCom01JobExecMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblGet01JigConfirmMapper;

/**
 * 共通Daoクラス
 *
 * @author ACT)福田
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CommonDao {

  /** 共通Mapper. */
  @Autowired
  private CommonMapper comMapper;

  /** 事業所マスタMapper. */
  @Autowired
  private MstSJigyoMapper jgyMapper;

  /** 担当者マスタMapper. */
  @Autowired
  private MstUserMapper usrMapper;

  /** 帳票定義マスタMapper. */
  @Autowired
  private MstListFormMapper mstListFormMapper;

  /** 汎用マスタMapper. */
  @Autowired
  private MstGeneralMapper mstGeneralMapper;

  /** システムコントロールマスタMapper. */
  @Autowired
  private MstSysCtlMapper mstSysCtlMapper;

  /** 自社伝票No採番マスタMapper. */
  @Autowired
  private MstUniqIdxMapper mstUniqIdxMapper;

  /** 伝票No採番マスタMapper. */
  @Autowired
  private MstDatIdxMapper mstDatIdxMapper;

  /** 請求先コードMapper. */
  @Autowired
  private BildCodeMapper bildCodeMapper;

  /** 事業所月次確定情報Mapper. */
  @Autowired
  private TblGet01JigConfirmMapper getJigMapper;

  /** ジョブ管理マスタMapper */
  @Autowired
  private MstJobexecMapper mstJobexecMapper;

  /** ジョブ実行状況Mapper */
  @Autowired
  private TblCom01JobExecMapper tblCom01JobExecMapper;

  /** 帳票配信マスタ. */
  @Autowired
  private MstListDistMapper mstListDistMapper;

  /** 製品事業所マスタ. */
  @Autowired
  private MstSSeijgyoMapper mstSSeijgyoMapper;

  /** 納品先マスタ */
  @Autowired
  private MstSNohinMapper mstSNohinMapper;

  /**
   * getter/setter.
   */
  public CommonMapper getComMapper() {
    return comMapper;
  }

  public void setComMapper(CommonMapper comMapper) {
    this.comMapper = comMapper;
  }

  public MstSJigyoMapper getJgyMapper() {
    return jgyMapper;
  }

  public void setJgyMapper(MstSJigyoMapper jgyMapper) {
    this.jgyMapper = jgyMapper;
  }

  public MstUserMapper getUsrMapper() {
    return usrMapper;
  }

  public void setUsrMapper(MstUserMapper usrMapper) {
    this.usrMapper = usrMapper;
  }

  public MstListFormMapper getMstListFormMapper() {
    return mstListFormMapper;
  }

  public void setMstListFormMapper(MstListFormMapper mstListFormMapper) {
    this.mstListFormMapper = mstListFormMapper;
  }

  public MstGeneralMapper getMstGeneralMapper() {
    return mstGeneralMapper;
  }

  public void setMstGeneralMapper(MstGeneralMapper mstGeneralMapper) {
    this.mstGeneralMapper = mstGeneralMapper;
  }

  public MstSysCtlMapper getMstSysCtlMapper() {
    return mstSysCtlMapper;
  }

  public void setMstSysCtlMapper(MstSysCtlMapper mstSysCtlMapper) {
    this.mstSysCtlMapper = mstSysCtlMapper;
  }

  public MstUniqIdxMapper getMstUniqIdxMapper() {
    return mstUniqIdxMapper;
  }

  public void setMstUniqIdxMapper(MstUniqIdxMapper mstUniqIdxMapper) {
    this.mstUniqIdxMapper = mstUniqIdxMapper;
  }

  public MstDatIdxMapper getMstDatIdxMapper() {
    return mstDatIdxMapper;
  }

  public void setMstDatIdxMapper(MstDatIdxMapper mstDatIdxMapper) {
    this.mstDatIdxMapper = mstDatIdxMapper;
  }

  public BildCodeMapper getBildCodeMapper() {
    return bildCodeMapper;
  }

  public void setBildCodeMapper(BildCodeMapper bildCodeMapper) {
    this.bildCodeMapper = bildCodeMapper;
  }

  public TblGet01JigConfirmMapper getGetJigMapper() {
    return getJigMapper;
  }

  public void setGetJigMapper(TblGet01JigConfirmMapper getJigMapper) {
    this.getJigMapper = getJigMapper;
  }

  public MstJobexecMapper getMstJobexecMapper() {
    return mstJobexecMapper;
  }

  public void setMstJobexecMapper(MstJobexecMapper mstJobexecMapper) {
    this.mstJobexecMapper = mstJobexecMapper;
  }

  public TblCom01JobExecMapper getTblCom01JobExecMapper() {
    return tblCom01JobExecMapper;
  }

  public void setTblCom01JobExecMapper(
      TblCom01JobExecMapper tblCom01JobExecMapper) {
    this.tblCom01JobExecMapper = tblCom01JobExecMapper;
  }

  public MstListDistMapper getMstListDistMapper() {
    return mstListDistMapper;
  }

  public void setMstListDistMapper(MstListDistMapper mstListDistMapper) {
    this.mstListDistMapper = mstListDistMapper;
  }

  public MstSSeijgyoMapper getMstSSeijgyoMapper() {
    return mstSSeijgyoMapper;
  }

  public void setMstSSeijgyoMapper(MstSSeijgyoMapper mstSSeijgyoMapper) {
    this.mstSSeijgyoMapper = mstSSeijgyoMapper;
  }

  public MstSNohinMapper getMstSNohinMapper() {
    return mstSNohinMapper;
  }

  public void setMstSNohinMapper(MstSNohinMapper mstSNohinMapper) {
    this.mstSNohinMapper = mstSNohinMapper;
  }

}
