/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl
 * ファイル名:Mst0109d01Dao.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/20
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/20 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.mst.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Mst0109d01Mapper;

/**
 * DAOクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Mst0109d01Dao {

  @Autowired
  private Mst0109d01Mapper mst0109d01Mapper;

  public Mst0109d01Mapper getMst0109d01Mapper() {
    return mst0109d01Mapper;
  }

  public void setMst0109d01Mapper(Mst0109d01Mapper mst0109d01Mapper) {
    this.mst0109d01Mapper = mst0109d01Mapper;
  }

  

}