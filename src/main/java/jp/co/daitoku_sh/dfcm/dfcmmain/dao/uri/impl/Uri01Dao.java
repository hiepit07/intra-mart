/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.uri.impl
 * ファイル名:Uri01Dao.java
 * 
 * <p>作成者:アクトブレーン 前田
 * 作成日:2015/
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/15 1.00 AB)前田 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.uri.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSChainMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.MstUserMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblGet01JigConfirmMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblSei01MshBodyMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblSei01UrkBodyMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblSei01UrkMshHeadMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblUri01BodyMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblUri01HeadMapper;
import jp.co.daitoku_sh.dfcm.common.db.mapper.TblUri01JournalMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Uri01Mapper;

/**
 * 売上サブシステム共通DAOクラス
 * 画面固有のDAOを作成する場合は当クラスを継承する
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Uri01Dao {

  @Autowired
  private Uri01Mapper uri01Mapper;

  @Autowired
  private TblGet01JigConfirmMapper get01JigConfirmMapper;

  /** 売上ヘッダ情報 */
  @Autowired
  private TblUri01HeadMapper uri01HeadMapper;

  /** 売上明細情報 */
  @Autowired
  private TblUri01BodyMapper uri01BodyMapper;

  /** 売上仕訳情報 */
  @Autowired
  private TblUri01JournalMapper uri01JournalMapper;

  /** 売掛・未収ヘッダ情報 */
  @Autowired
  private TblSei01UrkMshHeadMapper sei01UrkMshHeadMapper;

  /** 売掛明細情報 */
  @Autowired
  private TblSei01UrkBodyMapper sei01UrkBodyMapper;

  /** 未収明細情報 */
  @Autowired
  private TblSei01MshBodyMapper sei01MshBodyMapper;

  /** 担当者マスタ情報 */
  @Autowired
  private MstUserMapper mstUserMapper;

  /** チェーンマスタ情報 */
  @Autowired
  private MstSChainMapper mstSChainMapper;

  
  /**
   * uri01Mapperを取得する.
   *
   * @return Uri01Mapper uri01Mapper
   */
  public Uri01Mapper getUri01Mapper() {
    return uri01Mapper;
  }

  
  /**
   * uri01Mapperをセットする.
   *
   * @param uri01Mapper uri01Mapper
   */
  public void setUri01Mapper(Uri01Mapper uri01Mapper) {
    this.uri01Mapper = uri01Mapper;
  }

  
  /**
   * get01JigConfirmMapperを取得する.
   *
   * @return TblGet01JigConfirmMapper get01JigConfirmMapper
   */
  public TblGet01JigConfirmMapper getGet01JigConfirmMapper() {
    return get01JigConfirmMapper;
  }

  
  /**
   * get01JigConfirmMapperをセットする.
   *
   * @param get01JigConfirmMapper get01JigConfirmMapper
   */
  public void setGet01JigConfirmMapper(
      TblGet01JigConfirmMapper get01JigConfirmMapper) {
    this.get01JigConfirmMapper = get01JigConfirmMapper;
  }

  
  /**
   * uri01HeadMapperを取得する.
   *
   * @return TblUri01HeadMapper uri01HeadMapper
   */
  public TblUri01HeadMapper getUri01HeadMapper() {
    return uri01HeadMapper;
  }

  
  /**
   * uri01HeadMapperをセットする.
   *
   * @param uri01HeadMapper uri01HeadMapper
   */
  public void setUri01HeadMapper(TblUri01HeadMapper uri01HeadMapper) {
    this.uri01HeadMapper = uri01HeadMapper;
  }

  
  /**
   * uri01BodyMapperを取得する.
   *
   * @return TblUri01BodyMapper uri01BodyMapper
   */
  public TblUri01BodyMapper getUri01BodyMapper() {
    return uri01BodyMapper;
  }

  
  /**
   * uri01BodyMapperをセットする.
   *
   * @param uri01BodyMapper uri01BodyMapper
   */
  public void setUri01BodyMapper(TblUri01BodyMapper uri01BodyMapper) {
    this.uri01BodyMapper = uri01BodyMapper;
  }

  
  /**
   * uri01JournalMapperを取得する.
   *
   * @return TblUri01JournalMapper uri01JournalMapper
   */
  public TblUri01JournalMapper getUri01JournalMapper() {
    return uri01JournalMapper;
  }

  
  /**
   * uri01JournalMapperをセットする.
   *
   * @param uri01JournalMapper uri01JournalMapper
   */
  public void setUri01JournalMapper(TblUri01JournalMapper uri01JournalMapper) {
    this.uri01JournalMapper = uri01JournalMapper;
  }

  
  /**
   * sei01UrkMshHeadMapperを取得する.
   *
   * @return TblSei01UrkMshHeadMapper sei01UrkMshHeadMapper
   */
  public TblSei01UrkMshHeadMapper getSei01UrkMshHeadMapper() {
    return sei01UrkMshHeadMapper;
  }

  
  /**
   * sei01UrkMshHeadMapperをセットする.
   *
   * @param sei01UrkMshHeadMapper sei01UrkMshHeadMapper
   */
  public void setSei01UrkMshHeadMapper(
      TblSei01UrkMshHeadMapper sei01UrkMshHeadMapper) {
    this.sei01UrkMshHeadMapper = sei01UrkMshHeadMapper;
  }

  
  /**
   * sei01UrkBodyMapperを取得する.
   *
   * @return TblSei01UrkBodyMapper sei01UrkBodyMapper
   */
  public TblSei01UrkBodyMapper getSei01UrkBodyMapper() {
    return sei01UrkBodyMapper;
  }

  
  /**
   * sei01UrkBodyMapperをセットする.
   *
   * @param sei01UrkBodyMapper sei01UrkBodyMapper
   */
  public void setSei01UrkBodyMapper(TblSei01UrkBodyMapper sei01UrkBodyMapper) {
    this.sei01UrkBodyMapper = sei01UrkBodyMapper;
  }

  
  /**
   * sei01MshBodyMapperを取得する.
   *
   * @return TblSei01MshBodyMapper sei01MshBodyMapper
   */
  public TblSei01MshBodyMapper getSei01MshBodyMapper() {
    return sei01MshBodyMapper;
  }

  
  /**
   * sei01MshBodyMapperをセットする.
   *
   * @param sei01MshBodyMapper sei01MshBodyMapper
   */
  public void setSei01MshBodyMapper(TblSei01MshBodyMapper sei01MshBodyMapper) {
    this.sei01MshBodyMapper = sei01MshBodyMapper;
  }

  
  /**
   * mstUserMapperを取得する.
   *
   * @return MstUserMapper mstUserMapper
   */
  public MstUserMapper getMstUserMapper() {
    return mstUserMapper;
  }

  
  /**
   * mstUserMapperをセットする.
   *
   * @param mstUserMapper mstUserMapper
   */
  public void setMstUserMapper(MstUserMapper mstUserMapper) {
    this.mstUserMapper = mstUserMapper;
  }

  
  /**
   * mstSChainMapperを取得する.
   *
   * @return MstSChainMapper mstSChainMapper
   */
  public MstSChainMapper getMstSChainMapper() {
    return mstSChainMapper;
  }

  
  /**
   * mstSChainMapperをセットする.
   *
   * @param mstSChainMapper mstSChainMapper
   */
  public void setMstSChainMapper(MstSChainMapper mstSChainMapper) {
    this.mstSChainMapper = mstSChainMapper;
  }

}