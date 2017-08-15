/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl
 * ファイル名:Com0103d01Dao
 *
 * <p>作成者:ABV)TramChu
 * 作成日:2015/09/03
 *
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/03 1.00 ABV)TramChu 新規開発
 * -------------------------------------------------------------------------
 *
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.com.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.common.db.mapper.TblCom01JobExecMapper;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Com0103d01Mapper;

/**
 * 
 *
 * @author TramChu
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Com0103d01Dao {

  // 得意先検索画面専用Mapper
  @Autowired
  private Com0103d01Mapper com0103d01Mapper;
  @Autowired
  private TblCom01JobExecMapper tblCom01JobExecMapper;

  public Com0103d01Mapper getCom0103d01Mapper() {
    return com0103d01Mapper;
  }

  public void setCom0103d01Mapper(Com0103d01Mapper com0103d01Mapper) {
    this.com0103d01Mapper = com0103d01Mapper;
  }

  public TblCom01JobExecMapper getTblCom01JobExecMapper() {
    return tblCom01JobExecMapper;
  }

  public void setTblCom01JobExecMapper(TblCom01JobExecMapper tblCom01JobExecMapper) {
    this.tblCom01JobExecMapper = tblCom01JobExecMapper;
  }
}
