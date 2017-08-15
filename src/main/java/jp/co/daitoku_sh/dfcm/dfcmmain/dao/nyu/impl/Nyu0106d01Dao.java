/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl
 * ファイル名:Nyu0106d01Dao.java
 * 
 * 作成者:都築電気株式会社
 * 作成日:2015/12/09
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ACT)ANZAI 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.TblNyu01KaikeiRenkeiRirekiMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Nyu0106d01Mapper;

/**
 * 会計入金実績作成用 Dao
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class Nyu0106d01Dao {

  @Autowired
  private Nyu0106d01Mapper nyu0106d01Mapper;
  
  @Autowired
  private TblNyu01KaikeiRenkeiRirekiMapper tblNyu01KaikeiRenkeiRirekiMapper;

  @Autowired
  private SqlSession session;

  /**
   * nyu0106d01Mapper getter.
   * 
   */
  public Nyu0106d01Mapper getNyu0106d01Mapper() {
    return nyu0106d01Mapper;
  }

  /**
   * nyu0106d01Mapper setter.
   * 
   */
  public void setNyu0106d01Mapper(Nyu0106d01Mapper nyu0106d01Mapper) {
    this.nyu0106d01Mapper = nyu0106d01Mapper;
  }
  
  public TblNyu01KaikeiRenkeiRirekiMapper getTblNyu01KaikeiRenkeiRirekiMapper() {
    return tblNyu01KaikeiRenkeiRirekiMapper;
  }

  
  public void setTblNyu01KaikeiRenkeiRirekiMapper(
      TblNyu01KaikeiRenkeiRirekiMapper tblNyu01KaikeiRenkeiRirekiMapper) {
    this.tblNyu01KaikeiRenkeiRirekiMapper = tblNyu01KaikeiRenkeiRirekiMapper;
  }

  /**
   * 入金仕訳の更新（連携済みとする）
   * @param renkeiId 連携ID
   * @return 更新件数
   */
  public int updateRenkeiSumiOnNyukinShiwake(String renkeiId) {
    return session.update("updateRenkeiSumiOnNyukinShiwake", renkeiId);
  }

  /**
   * 会計連携履歴テーブルの更新（出力回数の更新）
   * @param renkeiId 連携ID
   * @return 更新件数
   */
  public int updateOutputCountOnKaikeiRenkeiRireki(String renkeiId) {
    return session.update("updateOutputCountOnKaikeiRenkeiRireki", renkeiId);
  }

}
