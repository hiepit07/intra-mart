/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.dao.uri.impl
 * ファイル名:Uri0105d01Dao.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/12/09
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/12/09 1.00 ABV)コイー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.dao.uri.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.daitoku_sh.dfcm.dfcmmain.db.mapper.Uri0105d01Mapper;

/**
 * DAOクラス
 * 
 * @author アクトブレーンベトナム コイー
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Uri0105d01Dao {

  @Autowired
  private Uri0105d01Mapper uri0105d01Mapper;

  public Uri0105d01Mapper getUri0105d01Mapper() {
    return uri0105d01Mapper;
  }

  public void setUri0105d01Mapper(Uri0105d01Mapper uri0105d01Mapper) {
    this.uri0105d01Mapper = uri0105d01Mapper;
  }  

}