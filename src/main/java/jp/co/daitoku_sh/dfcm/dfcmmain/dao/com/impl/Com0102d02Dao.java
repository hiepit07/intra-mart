/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl
 * ファイル名:Com0102d02Dao
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/03
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/03 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstCustomerMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Com0102d02Mapper;

/**
 * サービスから呼び出されるDao（呼び出すMapperを定義）
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Com0102d02Dao {

  // 得意先検索画面専用Mapper
  @Autowired
  private Com0102d02Mapper com0102d02Mapper;

  // 得意先情報用共通Mapper
  @Autowired
  private MstCustomerMapper mstCustomerMapper;

  /**
   * 一覧検索用Mapperのgetter
   *
   * @return com0102d02Mapper 一覧検索用のMapper
   */
  public Com0102d02Mapper getCom0102d02Mapper() {
    return com0102d02Mapper;
  }

  /**
   * 一覧検索用Mapperのsetter
   *
   * @param com0102d02Mapper 一覧検索用のMapper
   */
  public void setCom0102d02Mapper(Com0102d02Mapper com0102d02Mapper) {
    this.com0102d02Mapper = com0102d02Mapper;
  }

  /**
   * 一覧選択用Mapperのgetter
   *
   * @return mstCustomerMapper 一覧選択用のMapper
   */
  public MstCustomerMapper getMstCustomerMapper() {
    return mstCustomerMapper;
  }

  /**
   * 一覧選択用Mapperのsetter
   *
   * @param mstCustomerMapper 一覧選択用のMapper
   */
  public void setMstCustomerMapper(MstCustomerMapper mstCustomerMapper) {
    this.mstCustomerMapper = mstCustomerMapper;
  }

}
