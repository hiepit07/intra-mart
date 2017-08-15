/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl
 * ファイル名:Sei0106d01Dao.java
 * 
 * <p>作成者:
 * 作成日:2015/12/08
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/08 1.00 ABV)QuanTran 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.sei.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Sei0106d01Mapper;

/**
 * DAOクラス
 * 
 * @author QuanTran
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Sei0106d01Dao {

  @Autowired
  private Sei0106d01Mapper sei0106d01Mapper;

  public Sei0106d01Mapper getSei0106d01Mapper() {
    return sei0106d01Mapper;
  }

  public void setSei0106d01Mapper(Sei0106d01Mapper sei0106d01Mapper) {
    this.sei0106d01Mapper = sei0106d01Mapper;
  }
}
