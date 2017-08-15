/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl
 * ファイル名:Uri01Service.java
 * 
 * <p>作成者:アクトブレーン
 * 作成日:2015/10/15
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

package jp.co.daitoku_sh.dfcm.dfcmmain.service.uri.impl;

import java.io.IOException;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.OwnSlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.component.SlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01MshBody;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkBody;
import jp.co.daitoku_sh.dfcm.common.db.model.TblSei01UrkMshHead;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Body;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Head;
import jp.co.daitoku_sh.dfcm.common.db.model.TblUri01Journal;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.UriConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UriageData;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.uri.UriageItem;

/**
 * 売上サブシステム共通サービスクラス（Transaction使用）
 * Transactionを使用しない関数についてはUri01ServiceExに定義されている（売上計上Baｔｃｈ対応）
 * 画面固有のサービスを作成する場合は当クラスを継承する
 * 
 * @author アクトブレーン 前田
 * @version 1.0.0
 * @since 1.0.0
 */
public class Uri01Service extends Uri01ServiceEx {

  @Autowired
  @Qualifier("txManager")
  private PlatformTransactionManager txManager;

  @Autowired
  private ApplicationContext appContext;

  /* ログ出力用 */
  protected Dfcmlogger logger = new Dfcmlogger();

  /**
   * 共通関数を使用し、自社伝票Noを取得する.
   * 
   * @param strSlipKb 伝票区分
   * @param lngGetCnt 取得件数
   * 
   * @return List&lt;OwnSlipNumberingData&gt; 採番値（自社伝票No採番情報リスト）
   */
  public List<OwnSlipNumberingData> getSlipNo(String strSlipKb,
      long lngGetCnt) {
    List<OwnSlipNumberingData> lstSlipNo = null;
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, txManager,
        appContext, readPropertiesFileService);

    try {
      // 自社伝票No取得
      lstSlipNo = sysCom.ownSlipNumbering(strSlipKb, lngGetCnt);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return lstSlipNo;
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(ex.getMessage());
      return lstSlipNo;
    }

    return lstSlipNo;
  }

  /**
   * 共通関数を使用し、得意先伝票Noを取得する.
   *
   * @param strCustCode 得意先コード
   * @param strShopCode 店舗コード
   * @param intSlipCnt 伝票枚数
   * @param strNumberingKb 採番区分
   * 
   * @return List&gt;SlipNumberingData> 採番値（伝票No採番情報リスト）
   */
  public List<SlipNumberingData> getCustomerSlipNo(String strCustCode,
      String strShopCode, int intSlipCnt, String strNumberingKb) {
    List<SlipNumberingData> lstSlipNo = null;
    CommonGetSystemCom sysCom = new CommonGetSystemCom(commonDao, txManager,
        appContext, readPropertiesFileService);
    try {
      if (strShopCode == null || strShopCode.equalsIgnoreCase("")) {
        strShopCode = CommonConst.SHOP_CD_NONE;
      }
      // 得意先伝票No取得
      lstSlipNo = sysCom.slipNumbering(strCustCode, strShopCode, intSlipCnt,
          strNumberingKb);
    } catch (MyBatisSystemException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return lstSlipNo;
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(ex.getMessage());
      return lstSlipNo;
    }

    return lstSlipNo;
  }

  /**
   * 新規登録処理実行.
   * 
   * @param uriData 売上情報（黒伝票）
   * @param lstUriJournal 売上仕訳情報リスト
   * @param lstSeiUrkBody 売掛明細情報リスト
   * @param lstSeiMshBody 未収明細情報リスト
   * 
   * @throws Exception DB例外
   */
  public void execRegistry(UriageData uriData,
      List<TblUri01Journal> lstUriJournal, List<TblSei01UrkBody> lstSeiUrkBody,
      List<TblSei01MshBody> lstSeiMshBody) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    try {
      // トランザクション取得
      status = txManager.getTransaction(def);

      // 売上明細情報（黒伝票）登録
      for (TblUri01Body tblUriBody : uriData.getLstUriBody()) {
        this.insertUriBody(tblUriBody);
      }

      // 売上ヘッダ情報（黒伝票）登録
      this.insertUriHead(uriData.getUriHead());

      // 売上仕訳情報（黒伝票）登録
      if (lstUriJournal != null) {
        for (TblUri01Journal tblUriJournal : lstUriJournal) {
          this.insertUriJournal(tblUriJournal);
        }
      }

      // 売掛明細情報（黒伝票）登録
      if (lstSeiUrkBody != null) {
        for (TblSei01UrkBody tblUrkBody : lstSeiUrkBody) {
          this.insertSeiUrkBody(tblUrkBody);
        }
      }

      // 未収明細情報（黒伝票）登録
      if (lstSeiMshBody != null) {
        for (TblSei01MshBody tblMshBody : lstSeiMshBody) {
          this.insertSeiMshBody(tblMshBody);
        }
      }

      /* 売掛・未収ヘッダ情報は別トランザクションにて登録 */

      // コミット
      txManager.commit(status);
    } catch (Exception ex) {
      // ロールバック
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 訂正処理実行.
   * 
   * @param uriDataRed 売上情報（赤伝票）
   * @param uriData 売上情報（黒伝票）
   * @param lstUriJournal 売上仕訳情報リスト
   * @param lstSeiUrkBody 売掛明細情報リスト
   * @param lstSeiMshBody 未収明細情報リスト
   * 
   * @throws Exception DB例外
   */
  public void execModify(UriageData uriDataRed, UriageData uriData,
      List<TblUri01Journal> lstUriJournal, List<TblSei01UrkBody> lstSeiUrkBody,
      List<TblSei01MshBody> lstSeiMshBody) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    try {
      // トランザクション取得
      status = txManager.getTransaction(def);

      // 売上情報（元伝票）取得
      UriageData uriDataOrigin = this.getUriageData(uriData.getUriHead()
          .getOfficeCode(), uriData.getUriHead().getDenNo(),
          CommonConst.SHORI_KB_MNT);

      // 売上明細情報無効化
      for (TblUri01Body tblUriBodyOrigin : uriDataOrigin.getLstUriBody()) {
        this.invalidateUriBody(tblUriBodyOrigin, uriData.getUriHead()
            .getUserCodeReg());
      }

      // 売上ヘッダ情報無効化
      this.invalidateUriHead(uriDataOrigin.getUriHead(), uriData.getUriHead()
          .getUserCodeReg());

      // 売上明細情報（赤伝票）登録
      for (TblUri01Body tblUriBodyRed : uriDataRed.getLstUriBody()) {
        this.insertUriBody(tblUriBodyRed);
      }

      // 売上ヘッダ情報（赤伝票）登録
      this.insertUriHead(uriDataRed.getUriHead());

      // 売上明細情報（黒伝票）登録
      for (TblUri01Body tblUriBody : uriData.getLstUriBody()) {
        this.insertUriBody(tblUriBody);
      }

      // 売上ヘッダ情報（黒伝票）登録
      this.insertUriHead(uriData.getUriHead());

      // 売上仕訳情報（赤伝票・黒伝票）登録
      if (lstUriJournal != null) {
        for (TblUri01Journal tblUriJournal : lstUriJournal) {
          this.insertUriJournal(tblUriJournal);
        }
      }

      // 売掛明細情報（赤伝票・黒伝票）登録
      if (lstSeiUrkBody != null) {
        for (TblSei01UrkBody tblUrkBody : lstSeiUrkBody) {
          this.insertSeiUrkBody(tblUrkBody);
        }
      }

      // 未収明細情報（赤伝票・黒伝票）登録
      if (lstSeiMshBody != null) {
        for (TblSei01MshBody tblMshBody : lstSeiMshBody) {
          this.insertSeiMshBody(tblMshBody);
        }
      }

      /* 売掛・未収ヘッダ情報は別トランザクションにて登録 */

      // コミット
      txManager.commit(status);
    } catch (Exception ex) {
      // ロールバック
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 取消処理実行.
   * 
   * @param uriDataRed 売上情報（赤伝票）
   * @param lstUriJournal 売上仕訳情報リスト
   * @param lstSeiUrkBody 売掛明細情報リスト
   * @param lstSeiMshBody 未収明細情報リスト
   * @param strCancelMode 取消処理モード（0：取消モード、1：データ無効化）
   * 
   * 
   * @throws Exception DB例外
   */
  public void execCancel(UriageData uriDataRed,
      List<TblUri01Journal> lstUriJournal, List<TblSei01UrkBody> lstSeiUrkBody,
      List<TblSei01MshBody> lstSeiMshBody, String strCancelMode)
          throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    try {
      // トランザクション取得
      status = txManager.getTransaction(def);

      // 売上情報（元伝票）取得
      UriageData uriDataOrigin = this.getUriageData(uriDataRed.getUriHead()
          .getOfficeCode(), uriDataRed.getUriHead().getDenNo(),
          CommonConst.SHORI_KB_DEL);

      // 売上明細情報無効化
      for (TblUri01Body tblUriBodyOrigin : uriDataOrigin.getLstUriBody()) {
        this.invalidateUriBody(tblUriBodyOrigin, uriDataRed.getUriHead()
            .getUserCodeReg());
      }

      // 取消処理モード = '1'（無効化）の場合、修正区分を赤伝票からセット
      TblUri01Head uriHeadOrigin = uriDataOrigin.getUriHead();
      if (strCancelMode != null && strCancelMode.equalsIgnoreCase(
          UriConst.CANCEL_MODE_INVALID)) {
        uriHeadOrigin.setCollectKb(uriDataRed.getUriHead().getCollectKb());
      }
      // 売上ヘッダ情報無効化
      this.invalidateUriHead(uriHeadOrigin, uriDataRed.getUriHead()
          .getUserCodeReg());

      // 取消処理モード = '0'（取消モード）の場合、
      if (strCancelMode == null || strCancelMode.equalsIgnoreCase(
          UriConst.CANCEL_MODE_CANCEL)) {
        // 売上明細情報（赤伝票）登録
        for (TblUri01Body tblUriBodyRed : uriDataRed.getLstUriBody()) {
          this.insertUriBody(tblUriBodyRed);
        }

        // 売上ヘッダ情報（赤伝票）登録
        this.insertUriHead(uriDataRed.getUriHead());
      }

      // 売上仕訳情報（赤伝票）登録
      if (lstUriJournal != null) {
        for (TblUri01Journal tblUriJournal : lstUriJournal) {
          this.insertUriJournal(tblUriJournal);
        }
      }

      // 売掛明細情報（赤伝票）登録
      if (lstSeiUrkBody != null) {
        for (TblSei01UrkBody tblUrkBody : lstSeiUrkBody) {
          this.insertSeiUrkBody(tblUrkBody);
        }
      }
      // 未収明細情報（赤伝票）登録
      if (lstSeiMshBody != null) {
        for (TblSei01MshBody tblMshBody : lstSeiMshBody) {
          this.insertSeiMshBody(tblMshBody);
        }
      }

      /* 売掛・未収ヘッダ情報は別トランザクションにて登録 */

      // コミット
      txManager.commit(status);
    } catch (Exception ex) {
      // ロールバック
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * データ無効化処理実行.
   * 
   * @param strJigyoCode 事業所コード
   * @param lngSlipNo 自社伝票No
   * @param strUserCodeReg 登録ユーザコード
   * 
   * @throws Exception DB例外
   */
  public void execInvalid(String strJigyoCode, long lngSlipNo,
      String strUserCodeReg) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    try {
      // トランザクション取得
      status = txManager.getTransaction(def);

      // 売上情報（元伝票）取得
      UriageData uriDataOrigin = this.getUriageData(strJigyoCode, lngSlipNo,
          CommonConst.SHORI_KB_DEL);

      // 売上明細情報無効化
      for (TblUri01Body tblUriBodyOrigin : uriDataOrigin.getLstUriBody()) {
        this.invalidateUriBody(tblUriBodyOrigin, strUserCodeReg);
      }

      // 売上ヘッダ情報無効化
      this.invalidateUriHead(uriDataOrigin.getUriHead(), strUserCodeReg);

      // コミット
      txManager.commit(status);
    } catch (Exception ex) {
      // ロールバック
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * JavaScriptから渡された品目情報（JSON型）をUriageItemクラスに変換する.
   * 
   * @param strJsonData JavaScriptから渡された品目情報（JSON型）
   * 
   * @return UriageItem[] 売上品目情報リスト
   */
  public UriageItem[] convJson2UriItem(String strJsonData) {
    UriageItem[] uriItem = null;

    try {
      // JSON型の品目情報をクラス配列に格納
      ObjectMapper om = new ObjectMapper();
      uriItem = om.readValue(strJsonData, UriageItem[].class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      return null;
    }
    return uriItem;
  }

  /**
   * 売上ヘッダ情報登録.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * 
   * @throws Exception DB例外
   */
  private void insertUriHead(TblUri01Head tblUriHead) throws Exception {
    try {
      uri01Dao.getUri01HeadMapper().insert(tblUriHead);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 売上明細情報登録.
   * 
   * @param tblUriBody 売上明細情報
   * 
   * @throws Exception DB例外
   */
  private void insertUriBody(TblUri01Body tblUriBody) throws Exception {
    try {
      uri01Dao.getUri01BodyMapper().insert(tblUriBody);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 売上仕訳情報登録.
   * 
   * @param tblUriJournal 売上仕訳情報
   * 
   * @throws Exception DB例外
   */
  private void insertUriJournal(TblUri01Journal tblUriJournal)
      throws Exception {
    try {
      uri01Dao.getUri01JournalMapper().insert(tblUriJournal);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 売掛明細情報登録.
   * 
   * @param tblSeiUrkBody 売掛明細情報
   * 
   * @throws Exception DB例外
   */
  private void insertSeiUrkBody(TblSei01UrkBody tblSeiUrkBody)
      throws Exception {
    try {
      uri01Dao.getSei01UrkBodyMapper().insert(tblSeiUrkBody);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 未収明細情報登録.
   * 
   * @param tblSeiMshBody 未収明細情報
   * 
   * @throws Exception DB例外
   */
  private void insertSeiMshBody(TblSei01MshBody tblSeiMshBody)
      throws Exception {
    try {
      uri01Dao.getSei01MshBodyMapper().insert(tblSeiMshBody);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 売掛・未収ヘッダ情報登録処理実行.
   * 
   * @param tblUrkMshHead 売掛・未収ヘッダ情報
   * 
   * @throws Exception DB例外
   */
  public void insertSeiUrkMshHead(TblSei01UrkMshHead tblUrkMshHead)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    try {
      // トランザクション取得
      status = txManager.getTransaction(def);
      // insert
      uri01Dao.getSei01UrkMshHeadMapper().insert(tblUrkMshHead);
      // コミット
      txManager.commit(status);
    } catch (Exception ex) {
      // ロールバック
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 売掛・未収ヘッダ情報更新処理実行.
   * 
   * @param tblUrkMshHead 売掛・未収ヘッダ情報
   * 
   * @throws Exception DB例外
   */
  public void updateSeiUrkMshHead(TblSei01UrkMshHead tblUrkMshHead)
      throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = null;

    try {
      // トランザクション取得
      status = txManager.getTransaction(def);
      // insert
      uri01Dao.getSei01UrkMshHeadMapper()
          .updateByPrimaryKey(tblUrkMshHead);
      // コミット
      txManager.commit(status);
    } catch (Exception ex) {
      // ロールバック
      txManager.rollback(status);
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 売上ヘッダ情報無効化（状況コード '1'(登録)→'9'(取消)）.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param strUserCode 登録ユーザID
   * 
   * @throws Exception DB例外
   */
  private void invalidateUriHead(TblUri01Head tblUriHead, String strUserCode)
      throws Exception {
    try {
      tblUriHead.setStsCode(CommonConst.STS_CD_INVALID); // 状況コード
      this.setCommonDataUriHead(tblUriHead, strUserCode, tblUriHead.getUpdPgid(),
          false); // 共通項目
      uri01Dao.getUri01HeadMapper().updateByPrimaryKey(tblUriHead);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw ex;
    }
  }

  /**
   * 売上明細情報無効化（状況コード '1'(登録)→'9'(取消)）.
   * 
   * @param tblUriHead 売上ヘッダ情報
   * @param strUserCode 登録ユーザID
   * 
   * @throws Exception DB例外
   */
  private void invalidateUriBody(TblUri01Body tblUriBody, String strUserCode)
      throws Exception {
    try {
      tblUriBody.setStsCode(CommonConst.STS_CD_INVALID); // 状況コード
      this.setCommonDataUriBody(tblUriBody, strUserCode, tblUriBody.getUpdPgid(),
          false); // 共通項目
      uri01Dao.getUri01BodyMapper().updateByPrimaryKey(tblUriBody);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      throw ex;
    }
  }
}
