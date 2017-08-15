/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl
 * ファイル名:Nyu0104d01Dao.java
 * 
 * 作成者:アクトブレーンベトナム
 * 作成日:2015/12/16
 * 
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/16 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.MstSJigyoMapper;

/**
 * 入金登録用 Dao
 * 
 * @author NhungMa
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Component
public class Nyu0104d01Dao {
  
  @Autowired
  private MstSJigyoMapper mstSJgyoMapper;
  
  public MstSJigyoMapper getMstSJgyoMapper() {
    return mstSJgyoMapper;
  }
  
  public void setMstSJgyoMapper(MstSJigyoMapper mstSJgyoMapper) {
    this.mstSJgyoMapper = mstSJgyoMapper;
  }
  
}
