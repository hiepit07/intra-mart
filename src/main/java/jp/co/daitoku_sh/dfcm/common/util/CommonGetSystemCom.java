/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:CommonGetMstGeneral
 *
 * 作成者:都築電気株式会社
 * 作成日:2015/09/11
 *
 * 履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/09/11 1.00 TDK)関口 新規開発
 * -------------------------------------------------------------------------
 *
 * ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.CustomerData;
import jp.co.daitoku_sh.dfcm.common.component.DeliPriceMaxData;
import jp.co.daitoku_sh.dfcm.common.component.ItemClassData;
import jp.co.daitoku_sh.dfcm.common.component.JigyoData;
import jp.co.daitoku_sh.dfcm.common.component.MstGeneralData;
import jp.co.daitoku_sh.dfcm.common.component.OneSpecPriceData;
import jp.co.daitoku_sh.dfcm.common.component.OwnSlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.component.SlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.component.TaxRateData;
import jp.co.daitoku_sh.dfcm.common.component.UnitTaxPriceData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstDatIdx;
import jp.co.daitoku_sh.dfcm.common.db.model.MstDatIdxExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneral;
import jp.co.daitoku_sh.dfcm.common.db.model.MstGeneralExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstJobexec;
import jp.co.daitoku_sh.dfcm.common.db.model.MstJobexecExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSysCtl;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSysCtlExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUniqIdx;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUniqIdxExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUser;
import jp.co.daitoku_sh.dfcm.common.db.model.MstUserExample;
import jp.co.daitoku_sh.dfcm.common.db.model.TblCom01JobExec;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;

/**
 * 共通部品（システム共通）
 *
 * @author TSUZUKI DENKI
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonGetSystemCom {

  /** 共通Dao */
  private CommonDao commonDao;

  /** トランザクション Commit / Rollback */
  private PlatformTransactionManager txManager;
  private ApplicationContext appContext;

  /** プロパティファイル読み込み */
  private ReadPropertiesFileService readPropertiesFileService;
  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * コンストラクタ
   *
   * @param commonDao 共通Dao
   * @param txManager PlatformTransactionManager
   * @param appContext ApplicationContext
   * @param readPropertiesFileService ReadPropertiesFileService
   */
  public CommonGetSystemCom(CommonDao commonDao,
      PlatformTransactionManager txManager, ApplicationContext appContext,
      ReadPropertiesFileService readPropertiesFileService) {
    this.commonDao = commonDao;
    this.txManager = txManager;
    this.appContext = appContext;
    this.readPropertiesFileService = readPropertiesFileService;
  }

  /**
   * 汎用マスタ設定取得
   *
   * @param strGlKb 汎用マスタ区分
   * @param strGlCode 汎用マスタコード
   * @return lstMstGeneralData 汎用マスタ設定情報格納クラス
   */
  public List<MstGeneralData> getMstGeneralConf(String strGlKb,
      String strGlCode) {

    // 変数宣言

    MstGeneralExample mstGeneralExample = new MstGeneralExample(); // 検索条件格納クラス
    List<MstGeneral> lstMstGeneral; // SQLデータ取得用リスト
    ArrayList<MstGeneralData> lstMstGeneralData = new ArrayList<MstGeneralData>(); // 返却用データ格納リスト

    // SQL抽出条件生成
    if (strGlCode != null && !strGlCode.equals("")) {
      // [引数]汎用マスタコードがNOT NULLの場合
      mstGeneralExample.createCriteria().andGlKbEqualTo(strGlKb)
          .andGlCodeEqualTo(strGlCode); // 汎用マスタコードがNULLでない場合、汎用マスタコードを検索条件に追加する
    } else {
      // [引数]汎用マスタコードがNULLの場合
      mstGeneralExample.createCriteria().andGlKbEqualTo(strGlKb); // 汎用マスタ区分のみ追加
    }
    mstGeneralExample.setOrderByClause("Gl_Code"); // OrderByに汎用マスタコードを指定

    // SQL実行
    lstMstGeneral = commonDao.getMstGeneralMapper().selectByExample(
        mstGeneralExample);

    // 取得データ判定
    if (lstMstGeneral.size() == 0) {

      // 取得結果が０件の場合、NULLを返却
      return null;

    } else {

      // 取得データ編集
      for (MstGeneral mstGeneral : lstMstGeneral) {

        MstGeneralData mstGeneralData = new MstGeneralData();

        // 汎用マスタコード、汎用マスタ項目１のみ抽出
        mstGeneralData.setGlCode(mstGeneral.getGlCode());
        mstGeneralData.setTarget1(mstGeneral.getTarget1());

        lstMstGeneralData.add(mstGeneralData);
      }

      return lstMstGeneralData;

    }

  }

  /**
   * 検索上限値取得
   *
   * @return int intSearchMax 検索上限値
   */
  public int getCodeSearchMax() {

    // 変数宣言

    MstGeneralExample mstGeneralExample = new MstGeneralExample(); // 検索条件格納クラス
    List<MstGeneral> lstMstGeneral; // SQLデータ取得用リスト
    int intSearchMax; // 戻り値

    // SQL抽出条件生成
    mstGeneralExample.createCriteria().andGlKbEqualTo(
        CommonConst.CODE_SEARCH_MAX);

    // SQL実行
    lstMstGeneral = commonDao.getMstGeneralMapper().selectByExample(
        mstGeneralExample);

    // 取得データ判定
    if (lstMstGeneral.size() == 0) {

      // 取得結果が０件の場合、-1を返却
      intSearchMax = -1;

    } else {

      // 取得データを返却
      intSearchMax = Integer.parseInt(lstMstGeneral.get(0).getTarget1());

      // 共通部品【入力チェック.数値フォーマットチェック】
      String strErrMsg = InputCheckCom.chkNum(String.valueOf(intSearchMax),
          false);

      // [変数]処理結果 <> Nullの場合、エラーとする
      if (strErrMsg != null) {
        intSearchMax = -1;
      }

    }

    return intSearchMax;

  }

  /**
   * 業務日付取得
   *
   * @return String strAplDatte 業務日付
   */
  public String getAplDate() {

    // 変数宣言
    String strDateFlg; // 日付フラグ（プロパティファイル）
    String strAplDate = ""; // 戻り値

    // プロパティファイルから日付フラグを取得する（ID：system_date_kb）
    strDateFlg = readPropertiesFileService.getSetting("SYSTEM_DATE_KB");

    // 日付フラグ判定
    if (strDateFlg.equals(CommonConst.DATE_FLG_SYS)) {
      // 日付フラグ=0の場合、システム日付を返却
      Date date = new Date(); // 現在日時取得
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 日付フォーマットクラス
      strAplDate = sdf.format(date); // システム日付を格納

    } else {
      // 日付フラグ=1の場合
      MstSysCtlExample mstSysCtlExample = new MstSysCtlExample(); // 検索条件格納クラス（検索条件無しだが、引数として渡すため）
      List<MstSysCtl> lstMstSysCtl; // SQLデータ取得用リスト

      // SQL実行（検索条件無し）
      lstMstSysCtl = commonDao.getMstSysCtlMapper().selectByExample(
          mstSysCtlExample);

      // 取得データ判定
      if (lstMstSysCtl.size() == 0) {
        // 取得に失敗した場合、NULLを返却
        strAplDate = null;

      } else {

        // 取得できた場合、業務日付を格納
        strAplDate = lstMstSysCtl.get(0).getAplDate();

        // 共通部品を使って、取得した日付のフォーマットをチェックする（YYYYMMDD）
        String strErrMsg = InputCheckCom.chkDate(strAplDate, "YYYYMMDD");

        if (strErrMsg != null) {
          // フォーマットチェックエラーの場合、NULLを返却
          strAplDate = null;
        }

      }

    }

    return strAplDate;
  }

  /**
   * 数値丸め
   *
   * @param dblInValue 数値（数値丸め処理前）
   * @param intRoundPoint 丸め小数桁（丸めを行う小数点第○位）
   * @param strRoundClass 丸め種別（１：四捨五入、２：切り捨て、３：切り上げ）
   * @return BigDecimal bdlOutValue 数値（数値丸め処理後）
   */
  public BigDecimal getNumberRound(double dblInValue, int intRoundPoint,
      String strRoundClass) {

    // 元データをBigDecimal型にする
    BigDecimal bdlOutValue = BigDecimal.valueOf(dblInValue);

    // 入力チェック
    if (bdlOutValue.scale() < intRoundPoint) {
      // [入力]数値の小数桁 < [入力]丸め小数桁の場合、[入力]数値で復帰する
      return bdlOutValue;

    } else if (!strRoundClass.equals(CommonConst.ROUND_HALF_UP)
        && !strRoundClass.equals(
            CommonConst.ROUND_DOWN) && !strRoundClass.equals(
                CommonConst.ROUND_UP)) {
      // [入力]丸め種別 ≠ 1 or 2 or 3 の場合、[入力]数値で復帰する
      return bdlOutValue;
    }

    // 丸め種別に応じて、丸め処理を実行する
    if (strRoundClass.equals(CommonConst.ROUND_HALF_UP)) {
      // 四捨五入（[入力]丸め種別 = '1'）
      bdlOutValue = bdlOutValue.setScale((intRoundPoint - 1),
          BigDecimal.ROUND_HALF_UP);
    } else if (strRoundClass.equals(CommonConst.ROUND_DOWN)) {
      // 切り捨て（[入力]丸め種別 = '2'）
      bdlOutValue = bdlOutValue.setScale((intRoundPoint - 1),
          BigDecimal.ROUND_DOWN);
    } else if (strRoundClass.equals(CommonConst.ROUND_UP)) {
      // 切り上げ（[入力]丸め種別 = '3'）
      bdlOutValue = bdlOutValue.setScale((intRoundPoint - 1),
          BigDecimal.ROUND_UP);
    }

    return bdlOutValue;

  }

  /**
   * 自社伝票No採番
   *
   * @param strDenKb 伝票区分
   * @param lngGetCnt 取得件数
   * @return List<OwnSlipNumberingData> lstOwnSlipNumberingData
   *         自社伝票No採番情報格納クラスリスト
   * @throws Exception 例外
   */
  public List<OwnSlipNumberingData> ownSlipNumbering(String strDenKb,
      Long lngGetCnt) throws Exception {

    MstUniqIdxExample mstUniqIdxExample = new MstUniqIdxExample(); // 検索条件格納クラス
    List<MstUniqIdx> lstMstUniqIdx; // SQLデータ取得用リスト
    ArrayList<OwnSlipNumberingData> lstOwnSlipNumberingData = new ArrayList<OwnSlipNumberingData>(); // 返却用データ格納リスト
    OwnSlipNumberingData ownSlipNumberingData = new OwnSlipNumberingData(); // 配列情報格納用変数クラス
    Long lngConfWk; // 設定値取得用変数

    // データ取得SQL生成
    mstUniqIdxExample.createCriteria().andDenKbEqualTo(strDenKb)
        .andStsCodeEqualTo("1");

    // SQL実行
    lstMstUniqIdx = commonDao.getMstUniqIdxMapper().selectByExample(
        mstUniqIdxExample);

    // 取得データ判定
    if (lstMstUniqIdx.size() == 0) {
      // 該当する採番情報が取得できなかった場合、エラーとする（COM009-E）
      ownSlipNumberingData.setMsgCd(CommonConst.MSG_CD_COM009E);
      lstOwnSlipNumberingData.add(ownSlipNumberingData);
    } else {
      // SQL結果を変数に格納
      Long lngMaxIdx = lstMstUniqIdx.get(0).getMaxIdx(); // 上限値
      Long lngMinIdx = lstMstUniqIdx.get(0).getMinIdx(); // 下限値
      Long lngDatIdx = lstMstUniqIdx.get(0).getDatIdx(); // 採番番号

      // 設定値取得判定
      if (lngMaxIdx == (lngDatIdx + lngGetCnt - 1)) {
        // UNQ1.上限値 = UNQ.採番番号 ＋ [入力]取得件数 － 1 の場合
        lngConfWk = lngMinIdx; // 下限値
      } else if (lngMaxIdx < (lngDatIdx + lngGetCnt - 1)) {
        // UNQ1.上限値 < UNQ1.採番番号 ＋ [入力]取得件数 － 1 の場合
        // UNQ1.下限値 ＋ （[入力]取得件数 － UNQ1.上限値 ＋ UNQ.採番番号 － 1）
        lngConfWk = lngMinIdx + (lngGetCnt - lngMaxIdx + lngDatIdx - 1);
      } else {
        // UNQ1.上限値 > UNQ1.採番番号 ＋ [入力]取得件数の場合
        // UNQ1.採番番号 ＋ [入力]取得件数
        lngConfWk = lngDatIdx + lngGetCnt;
      }

      // トランザクション取得
      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
      txManager = (PlatformTransactionManager) appContext
          .getBean(
              "txManager");
      TransactionStatus status = txManager.getTransaction(def);

      // 更新項目取得（共通項目）
      Date date = new Date(); // 現在日時取得
      SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyyMMdd"); // 日付フォーマットクラス
      SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss"); // 日付フォーマットクラス

      String strUpdUserIdWk = readPropertiesFileService.getSetting(
          "BAT_USER_CODE"); // 仕様変更：バッチ処理担当者コード
      String strUpdPgidWk = CommonConst.UPD_PGID; // 更新プログラム
      String strUpdYmdWk = sdfYmd.format(date); // 更新年月日
      String strUpdTimeWk = sdfTime.format(date); // 更新時刻

      // 更新項目セット
      MstUniqIdx mstUniqIdx = new MstUniqIdx();
      mstUniqIdx.setDatIdx(lngConfWk); // 採番番号
      mstUniqIdx.setDenKb(strDenKb); // 伝票区分
      mstUniqIdx.setStsCode("1"); // 状況コード

      // 更新項目セット（共通項目）
      mstUniqIdx.setUpdUserid(strUpdUserIdWk); // 更新ユーザー
      mstUniqIdx.setUpdPgid(strUpdPgidWk); // 更新プログラム
      mstUniqIdx.setUpdYmd(strUpdYmdWk); // 更新年月日
      mstUniqIdx.setUpdTime(strUpdTimeWk); // 更新時間

      try {
        // 更新SQL実行
        commonDao.getMstUniqIdxMapper().updateByPrimaryKeySelective(mstUniqIdx);
        // コミット
        txManager.commit(status);
      } catch (Exception ex) {
        // ロールバック
        txManager.rollback(status);
        throw ex;
      }

      // 採番番号退避
      Long lngDatIdxWk = lngDatIdx;

      // 出力項目へ格納（[入力]取得件数の分ループ）
      for (int i = 0; i < lngGetCnt; i++) {
        ownSlipNumberingData = new OwnSlipNumberingData(); // 初期化
        ownSlipNumberingData.setOwnSlipNumber(lngDatIdxWk); // 採番番号をセット
        lstOwnSlipNumberingData.add(ownSlipNumberingData);// 出力クラスにセット

        if (lngGetCnt > 1) {
          // [入力]伝票枚数 > 1 の場合
          lngDatIdxWk = lngDatIdxWk + 1; // [変数]採番番号WK + 1
        } else if (lngDatIdxWk > lngMaxIdx) {
          // [変数]採番番号WK > UNQ1.上限値 の場合
          lngDatIdxWk = lngMinIdx; // UNQ1.下限値
        }
      }
    }
    return lstOwnSlipNumberingData;
  }

  /**
   * 上限金額エラーチェック
   *
   * @param dblDeliPrice 納品金額
   * @return DeliPriceMaxData 納品金額上限格納クラス
   */
  public DeliPriceMaxData getDeliPriceMax(double dblDeliPrice) {

    // 変数宣言
    DeliPriceMaxData deliPriceMaxData = new DeliPriceMaxData(); // 戻り値
    List<MstGeneralData> lstMstGeneralData; // 返却用データ格納リスト
    double dblDeliPriceMaxWk; // 納品金額上限格納用変数
    String strMsgCode; // メッセージコード格納用変数

    // 共通部品を使用して、納品金額上限を取得する
    // 共通部品【汎用マスタ取得.汎用マスタ設定取得】
    lstMstGeneralData = getMstGeneralConf(CommonConst.DELI_PRICE_MAX, "");

    // 戻り値チェック
    if (lstMstGeneralData == null) {
      // [変数]汎用マスタ名称格納クラス.汎用マスタ名称 = Null の場合
      dblDeliPriceMaxWk = -1; // 上限無し

    } else {
      // [変数]汎用マスタ名称格納クラス.汎用マスタ名称 <> Null の場合
      dblDeliPriceMaxWk = Double.parseDouble(lstMstGeneralData.get(0)
          .getTarget1()); // [変数]汎用マスタ名称格納クラス.汎用マスタ名称[0]
    }

    // 納品金額上限チェック
    if (dblDeliPriceMaxWk < 0) {
      // [変数]納品金額上限 < 0 の場合、正常終了
      strMsgCode = null;
    } else {
      // [変数]納品金額上限 >= 0 の場合
      if (dblDeliPrice > dblDeliPriceMaxWk) {
        // [入力]納品金額 > [変数]納品金額上限の場合、エラーとする
        strMsgCode = CommonConst.MSG_CD_COM004E;
      } else {
        // [入力]納品金額 =< [変数]納品金額上限の場合、正常とする
        strMsgCode = null;
      }
    }

    // 返却項目セット
    deliPriceMaxData.setMaxDeliPrice(dblDeliPriceMaxWk); // 納品金額上限値
    deliPriceMaxData.setMsgCode(strMsgCode); // メッセージコード

    return deliPriceMaxData;

  }

  /**
   * 便区分取得
   *
   * @param strBinKb 便区分
   * @return String 便区分
   */
  public String getBinKb(String strBinKb) {

    // 変数宣言
    MstGeneralExample mstGeneralExample = new MstGeneralExample(); // 検索条件格納クラス
    List<MstGeneral> lstMstGeneral; // SQLデータ取得用リスト
    String strBinKbWk; // 戻り値

    // SQL抽出条件生成
    mstGeneralExample.createCriteria().andGlKbEqualTo(CommonConst.BIN_KB);

    // SQL実行
    lstMstGeneral = commonDao.getMstGeneralMapper().selectByExample(
        mstGeneralExample);

    // 取得データ判定
    if (lstMstGeneral.size() > 0) {
      // 汎用マスタに該当レコードが存在する場合
      // [入力]便区分とGEN.便区分1～10を比較し、いずれかと合致した場合、
      // 合致したGEN.便区分1～10を返却
      if (strBinKb.equals(lstMstGeneral.get(0).getTarget1())) {
        // 便区分１と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget1();
      } else if (strBinKb.equals(lstMstGeneral.get(0).getTarget2())) {
        // 便区分２と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget2();
      } else if (strBinKb.equals(lstMstGeneral.get(0).getTarget3())) {
        // 便区分３と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget3();
      } else if (strBinKb.equals(lstMstGeneral.get(0).getTarget4())) {
        // 便区分４と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget4();
      } else if (strBinKb.equals(lstMstGeneral.get(0).getTarget5())) {
        // 便区分５と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget5();
      } else if (strBinKb.equals(lstMstGeneral.get(0).getTarget6())) {
        // 便区分６と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget6();
      } else if (strBinKb.equals(lstMstGeneral.get(0).getTarget7())) {
        // 便区分７と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget7();
      } else if (strBinKb.equals(lstMstGeneral.get(0).getTarget8())) {
        // 便区分８と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget8();
      } else if (strBinKb.equals(lstMstGeneral.get(0).getTarget9())) {
        // 便区分９と合致した場合
        strBinKbWk = lstMstGeneral.get(0).getTarget9();
      } else {
        // いずれとも合致しない場合
        strBinKbWk = null;
      }
    } else {
      // 汎用マスタにて該当レコードが存在しない場合、Nullで復帰する
      strBinKbWk = null;
    }
    return strBinKbWk;
  }

  /**
   * HULFT配信起動
   *
   * @param strHulftId HULFT_ID
   * @return String HULFT終了コード
   * @throws IOException 例外
   * @throws InterruptedException 例外
   */
  public String startUpHulft(String strHulftId) throws IOException,
      InterruptedException {

    // 変数宣言
    String strHulftResult = ""; // 戻り値
    String strBatPath = readPropertiesFileService.getSetting("PATH_HUL_SND_BAT")
        + "\\" + readPropertiesFileService.getSetting("FILE_NM_HUL_SND_BAT"); // バッチパス取得

    // コマンド生成
    String cmd = "cmd.exe /c call " + strBatPath + " " + strHulftId;

    // バッチ実行
    Process process = Runtime.getRuntime().exec(cmd);

    // バッチの戻り値取得
    strHulftResult = String.valueOf(process.waitFor());

    // バッチの戻り値を返却
    return strHulftResult;

  }

  /**
   * 伝票No採番
   *
   * @param strCustCode 得意先コード
   * @param strShopCode 店舗コード
   * @param intSlipCnt 伝票枚数
   * @param strNumberingKb 採番区分（１：請求先毎、２：得意先毎、３：店舗）仕様変更：請求先と得意先入れ替え
   * @return List lstSlipNumberingData 伝票No採番情報格納クラス
   * @throws Exception 例外
   */
  public List<SlipNumberingData> slipNumbering(
      String strCustCode, String strShopCode, int intSlipCnt,
      String strNumberingKb) throws Exception {

    // 変数宣言
    MstDatIdxExample mstDatIdxExample = new MstDatIdxExample(); // 検索条件格納クラス（伝票採番マスタ）
    List<MstDatIdx> lstMstDatIdx; // SQLデータ取得用リスト（伝票採番マスタ）
    SlipNumberingData slipNumberingData; // 配列情報格納用変数クラス
    ArrayList<SlipNumberingData> lstSlipNumberingData = new ArrayList<SlipNumberingData>(); // 返却用データ格納リスト
    String strBildCodeWk = null; // 請求先コード格納用ワーク
    String strCustCodeWk; // 得意先コード格納用ワーク
    String strShopCodeWk; // 店舗コード格納用ワーク
    Long lngConfWk; // 設定値格納用ワーク

    // [入力]採番区分 = '1'（請求先毎）の場合、得意先マスタから請求先コードを取得する
    if (strNumberingKb.equals(CommonConst.NUMBERING_KB_BILD)) {

      // SQL生成
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("Cust_Code", strCustCode); // 得意先コード
      params.put("businessDate", getAplDate()); // 業務日付

      // SQL実行
      strBildCodeWk = commonDao.getBildCodeMapper().getBildCode(params);

      if (strBildCodeWk == null) {
        // 該当する請求先コードが取得できなかった場合、エラーとする（COM009-E）
        slipNumberingData = new SlipNumberingData(); // 初期化
        slipNumberingData.setMsgCd(CommonConst.MSG_CD_COM009E); // メッセージコード
        slipNumberingData.setGeneMstKb(CommonConst.GENE_MST_KB_CUST); // 発生マスタ区分
        lstSlipNumberingData.add(slipNumberingData);// 出力クラスにセット
        return lstSlipNumberingData; // 処理終了
      }
    }

    // コード設定
    if (strNumberingKb.equals(CommonConst.NUMBERING_KB_BILD)) {
      // [入力]採番区分 = '1'（請求先毎）の場合
      strCustCodeWk = strBildCodeWk; // CSJ.請求先コード
      strShopCodeWk = readPropertiesFileService.getSetting("TEN_CODE_NONE"); // プロパティファイルから取得
    } else if (strNumberingKb.equals(CommonConst.NUMBERING_KB_CUST)) {
      // [入力]採番区分 = '2'（得意先毎）の場合
      strCustCodeWk = strCustCode; // [入力]得意先コード
      strShopCodeWk = readPropertiesFileService.getSetting("TEN_CODE_NONE"); // プロパティファイルから取得
    } else {
      // [入力]採番区分 = '3'（店舗毎）の場合
      strCustCodeWk = strCustCode; // [入力]得意先コード
      strShopCodeWk = strShopCode; // [入力]店舗コード
    }

    // 伝票採番マスタから採番値を取得する

    // SQL生成
    mstDatIdxExample.createCriteria().andCustCodeEqualTo(strCustCodeWk)
        .andShopCodeEqualTo(strShopCodeWk).andStsCodeEqualTo("1");

    // SQL実行
    lstMstDatIdx = commonDao.getMstDatIdxMapper().selectByExample(
        mstDatIdxExample);

    if (lstMstDatIdx.size() == 0) {
      // 該当する採番情報が取得できなかった場合、エラーとする（COM009-E）
      slipNumberingData = new SlipNumberingData(); // 初期化
      slipNumberingData.setMsgCd(CommonConst.MSG_CD_COM009E); // メッセージコード
      slipNumberingData.setGeneMstKb(CommonConst.GENE_MST_KB_IDX); // 発生マスタ区分
      lstSlipNumberingData.add(slipNumberingData);// 出力クラスにセット
      return lstSlipNumberingData; // 処理終了
    }

    // SQL結果取得
    Long lngMaxIdx = lstMstDatIdx.get(0).getMaxIdx(); // 上限値
    Long lngMinIdx = lstMstDatIdx.get(0).getMinIdx(); // 下限値
    Short shtValidDigit = lstMstDatIdx.get(0).getValidDigit(); // 有効桁数
    String strZeroSup = lstMstDatIdx.get(0).getZeroSuppress(); // ０埋め
    Long lngDatIdx = lstMstDatIdx.get(0).getDatIdx(); // 採番番号

    // 設定値設定
    if (lngMaxIdx == (lngDatIdx + intSlipCnt - 1)) {
      // DAT1.上限値 = DAT1.採番番号 ＋ [入力]伝票枚数 － 1 の場合
      lngConfWk = lngMinIdx; // DAT1.下限値
    } else if (lngMaxIdx < (lngDatIdx + intSlipCnt - 1)) {
      // DAT1.上限値 < DAT1.採番番号 ＋ [変数]伝票枚数 － 1 の場合
      // DAT1.下限値 ＋ （[入力].伝票枚数 － DAT1.上限値 ＋ DAT1.採番番号 － 1）
      lngConfWk = lngMinIdx + (intSlipCnt - lngMaxIdx + lngDatIdx - 1);
    } else {
      // DAT1.上限値 > DAT1.採番番号 ＋ [変数]伝票枚数の場合
      lngConfWk = lngDatIdx + intSlipCnt; // DAT1.採番番号 ＋ [入力]伝票枚数
    }

    // トランザクション取得
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext
        .getBean(
            "txManager");
    TransactionStatus status = txManager.getTransaction(def);

    // 更新項目取得（共通項目）
    Date date = new Date(); // 現在日時取得
    SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyyMMdd"); // 日付フォーマットクラス
    SimpleDateFormat sdfTime = new SimpleDateFormat("HHmmss"); // 日付フォーマットクラス

    String strUpdUserIdWk = readPropertiesFileService.getSetting(
        "BAT_USER_CODE"); // 仕様変更：バッチ処理担当者コード
    String strUpdPgidWk = CommonConst.UPD_PGID; // 更新プログラム
    String strUpdYmdWk = sdfYmd.format(date); // 更新年月日
    String strUpdTimeWk = sdfTime.format(date); // 更新時刻

    // 更新項目セット
    MstDatIdx mstDatIdx = new MstDatIdx();
    mstDatIdx.setDatIdx(lngConfWk); // 採番番号
    mstDatIdx.setCustCode(strCustCodeWk); // 得意先コード
    mstDatIdx.setShopCode(strShopCodeWk); // 店舗コード
    mstDatIdx.setStsCode("1"); // 状況コード

    // 更新項目セット（共通項目）
    mstDatIdx.setUpdUserid(strUpdUserIdWk); // 更新ユーザーID
    mstDatIdx.setUpdPgid(strUpdPgidWk); // 更新プログラム
    mstDatIdx.setUpdYmd(strUpdYmdWk); // 更新年月日
    mstDatIdx.setUpdTime(strUpdTimeWk); // 更新時間

    try {
      // 更新SQL実行
      commonDao.getMstDatIdxMapper().updateByPrimaryKeySelective(mstDatIdx);
      // コミット
      txManager.commit(status);
    } catch (Exception ex) {
      // ロールバック
      txManager.rollback(status);
      throw ex;
    }

    // 採番番号退避
    Long lngDatIdxWk = lngDatIdx;

    String strDenNoWk; // 伝票No格納用ワーク

    // 出力項目へ格納（[入力]伝票枚数の分ループ）
    for (int i = 0; i < intSlipCnt; i++) {

      // 伝票No整形
      if (strZeroSup.equals("1")) {
        // DAT1.0埋め = '1'（対象）の場合
        // DAT1.有効桁数 - DAT1.採番番号.lengthの数だけ、'0'を繰り返した文字列に[変数]採番番号WKを連結した文字列
        strDenNoWk = String.format("%" + shtValidDigit + "s", String.valueOf(
            lngDatIdxWk)).replace(" ", "0");
      } else {
        // DAT1.0埋め = '2'（対象外）の場合
        // DAT1.有効桁数 - DAT1.採番番号.lengthの数だけ、' '（半角スペース）を繰り返した文字列に[変数]採番番号WKを連結した
        strDenNoWk = String.format("%" + shtValidDigit + "s", String.valueOf(
            lngDatIdxWk)).toString();
      }

      slipNumberingData = new SlipNumberingData(); // 初期化
      slipNumberingData.setSlipNumber(strDenNoWk); // 伝票Noをセット
      lstSlipNumberingData.add(slipNumberingData);// 出力クラスにセット

      if (intSlipCnt > 1) {
        // [入力]伝票枚数 > 1 の場合
        lngDatIdxWk = lngDatIdxWk + 1; // [変数]採番番号WK + 1
      } else if (lngDatIdxWk > lngMaxIdx) {
        // [変数]伝票No.WK > DAT1.上限値 の場合
        lngDatIdxWk = lngMinIdx; // DAT1.下限値
      }
    }

    return lstSlipNumberingData;

  }

  /**
   * 消費税率取得
   *
   * @param strDeliDate 納品日
   * @return taxRateData 消費税率情報格納クラス
   */
  public TaxRateData getTaxRate(String strDeliDate) {

    // 変数宣言
    MstGeneralExample mstGeneralExample = new MstGeneralExample(); // 検索条件格納クラス
    List<MstGeneral> lstMstGeneral; // SQLデータ取得用リスト
    TaxRateData taxRateData = new TaxRateData(); // 戻り値
    double dblTaxRateWk = 0; // 消費税率格納用ワーク
    boolean blnProcResultWk = false; // 処理結果格納用ワーク

    // 汎用マスタ消費税率取得
    // SQL生成
    mstGeneralExample.createCriteria().andGlKbEqualTo(CommonConst.TAX_RATE)
        .andGlCodeLessThan(strDeliDate);
    mstGeneralExample.setOrderByClause("Gl_Code DESC"); // OrderByに汎用マスタコードの降順を指定

    // SQL実行
    lstMstGeneral = commonDao.getMstGeneralMapper().selectByExample(
        mstGeneralExample);

    if (lstMstGeneral.size() > 0) {
      // 汎用マスタに該当レコードが存在する場合
      // 変数[WK消費税率] に取得した消費税率をセットする
      dblTaxRateWk = Double.parseDouble(lstMstGeneral.get(0).getTarget1());
      blnProcResultWk = true; // 変数[WK処理結果] = True
    }

    // 出力変数をセット
    taxRateData.setTaxRate(dblTaxRateWk); // 仕様変更：消費税率セット⇒割る１００を廃止
    taxRateData.setProcResult(blnProcResultWk); // 処理結果をセット

    return taxRateData;

  }

  /**
   * 商品分類取得
   *
   * @param strItemCode 品目コード
   * @return itemClassData 商品分類情報格納クラス
   * @throws Exception 例外
   */
  public ItemClassData getItemClass(String strItemCode) throws Exception {

    // 変数宣言
    ItemClassData itemClassData = new ItemClassData(); // 戻り値
    String strItemClassWk = "1"; // 商品分類格納用ワーク
    boolean blnProcResultWk = false; // 処理結果格納用ワーク
    MstGeneralExample mstGeneralExamplePostege = new MstGeneralExample(); // 検索条件格納クラス（送料品目）
    MstGeneralExample mstGeneralExampleFee = new MstGeneralExample(); // 検索条件格納クラス（手数料品目）
    List<MstGeneral> lstMstGeneralPostege; // SQLデータ取得用リスト（送料品目）
    List<MstGeneral> lstMstGeneralFee; // SQLデータ取得用リスト（手数料品目）

    try {

      // 汎用マスタ送料品目取得
      // SQL生成
      mstGeneralExamplePostege.createCriteria().andGlKbEqualTo(
          CommonConst.POSTEGE_ITEM);

      // SQL実行
      lstMstGeneralPostege = commonDao.getMstGeneralMapper().selectByExample(
          mstGeneralExamplePostege);

      if (lstMstGeneralPostege.size() > 0) {
        // 汎用マスタに該当レコードが存在する場合
        // 変数[品目コード]と汎用マスタ項目[送料品目コード]1～10を比較

        if (strItemCode.equals(lstMstGeneralPostege.get(0).getTarget1())
            || strItemCode
                .equals(lstMstGeneralPostege.get(0).getTarget2()) || strItemCode
                    .equals(
                        lstMstGeneralPostege.get(0).getTarget3()) || strItemCode
                            .equals(
                                lstMstGeneralPostege.get(0).getTarget4())
            || strItemCode.equals(
                lstMstGeneralPostege.get(0).getTarget5()) || strItemCode.equals(
                    lstMstGeneralPostege.get(0).getTarget6()) || strItemCode
                        .equals(lstMstGeneralPostege.get(0).getTarget7())
            || strItemCode.equals(lstMstGeneralPostege.get(0).getTarget8())
            || strItemCode.equals(lstMstGeneralPostege.get(0).getTarget9())
            || strItemCode.equals(lstMstGeneralPostege.get(0).getTarget10())) {

          // 合致した時点で、変数[WK商品分類]に固定値[2]を代入し、終了処理へ
          strItemClassWk = CommonConst.ITEM_CLASS_POSTEGE;

        }

      }

      if (blnProcResultWk == false) {
        // 汎用マスタ手数料品目取得
        // SQL生成
        mstGeneralExampleFee.createCriteria().andGlKbEqualTo(
            CommonConst.FEE_ITEM);

        // SQL実行
        lstMstGeneralFee = commonDao.getMstGeneralMapper().selectByExample(
            mstGeneralExampleFee);

        if (lstMstGeneralFee.size() > 0) {
          // 汎用マスタに該当レコードが存在する場合
          // 変数[品目コード]と汎用マスタ項目[手数料品目コード]1～10を比較

          if (strItemCode.equals(lstMstGeneralFee.get(0).getTarget1())
              || strItemCode
                  .equals(lstMstGeneralFee.get(0).getTarget2()) || strItemCode
                      .equals(
                          lstMstGeneralFee.get(0).getTarget3()) || strItemCode
                              .equals(
                                  lstMstGeneralFee.get(0).getTarget4())
              || strItemCode.equals(
                  lstMstGeneralFee.get(0).getTarget5()) || strItemCode.equals(
                      lstMstGeneralFee.get(0).getTarget6()) || strItemCode
                          .equals(lstMstGeneralFee.get(0).getTarget7())
              || strItemCode.equals(lstMstGeneralFee.get(0).getTarget8())
              || strItemCode.equals(lstMstGeneralFee.get(0).getTarget9())
              || strItemCode.equals(lstMstGeneralFee.get(0).getTarget10())) {

            // 合致した時点で、変数[WK商品分類]に固定値[3]を代入
            strItemClassWk = CommonConst.ITEM_CLASS_FEE;

          }
        }
      }

      // 仕様変更：送料品目・手数料品目の両方取得に失敗した場合でも
      // 「１」を返却するため、処理結果にtrueをセットする
      blnProcResultWk = true; // 処理結果セット

    } catch (Exception e) {
      throw e;
    } finally {

      // 出力変数セット
      itemClassData.setItemClass(strItemClassWk); // 商品分類セット
      itemClassData.setProcResult(blnProcResultWk); // 処理結果セット

    }

    return itemClassData;

  }

  /**
   * ジョブ監視更新.
   *
   * @param hulftId String
   * @param userCode String
   * @param jgyCode String
   * @param segmentCode Integer
   * @param code String
   * @param result String
   * @param startDate String
   * @param startTime String
   * @param endDate String
   * @param endTime String
   * @param message ArrayList String
   * @param parameters String
   * @param reFlg String
   * @return 正常終了：True、異常終了：False
   * @throws Exception for function ownSlipNumbering()
   */
  public boolean createJob(String hulftId, String userCode,
      String jgyCode,
      Integer segmentCode, String code, String result, String startDate,
      String startTime,
      String endDate, String endTime, ArrayList<String> message,
      String parameters, String reFlg) throws Exception {
    boolean resultStatus = true;
    String deliveryDivisionWk = "";
    String dataNameWk = "";
    String typeWk = "";
    String fileNameWk = "";
    String userNameWk = "";
    String jobNameWk = "";
    // 名称WK
    String nameWk = "";
    List<MstJobexec> mstListJob = new ArrayList<MstJobexec>();
    MstJobexecExample mstJobExp = new MstJobexecExample();
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    txManager = (PlatformTransactionManager) appContext.getBean("txManager");
    TransactionStatus status = txManager.getTransaction(def);
    try {
      // SQL生成
      mstJobExp.createCriteria().andHulftIdEqualTo(hulftId);
      // SQL実行
      mstListJob = commonDao.getMstJobexecMapper().selectByExample(mstJobExp);
      if (mstListJob.size() > 0) { // データが存在する場合、
        deliveryDivisionWk = mstListJob.get(0).getRsKb();
        dataNameWk = mstListJob.get(0).getDatNm();
        typeWk = mstListJob.get(0).getDatCls();
        fileNameWk = mstListJob.get(0).getFilePath();
      } else { // データが存在しない場合、
        deliveryDivisionWk = "";
        dataNameWk = "";
        typeWk = "";
        fileNameWk = "";
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    List<MstUser> mstUser = new ArrayList<MstUser>();
    MstUserExample mstUserExp = new MstUserExample();
    try {
      // SQL生成
      mstUserExp.createCriteria().andUserCodeEqualTo(userCode);
      // SQL実行
      mstUser = commonDao.getUsrMapper().selectByExample(mstUserExp);
      if (mstUser.size() > 0) { // 該当するレコードが存在する場合、
        userNameWk = mstUser.get(0).getUserNm();
      } else { // 該当するレコードが存在しない場合、
        userNameWk = "";
      }
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }

    CommonGetData sysCom = new CommonGetData(commonDao,
        readPropertiesFileService);

    // [入力]コード区分 = Null or [入力]コード = Null の場合
    if (segmentCode == null || code == null) {
      nameWk = "";
    } else if (segmentCode == 1 || segmentCode == 2) {
      CustomerData cstData = new CustomerData();
      cstData = sysCom.getCustomerData(code, jgyCode, segmentCode.intValue());

      // [変数]得意先情報格納クラス.メッセージコード <> Nullの場合、
      if (cstData.getMsgCd() != null) {
        nameWk = "UNKNOWN";
      } else { // [変数]得意先情報格納クラス.メッセージコード = Null の場合、
        nameWk = cstData.getCst().getCustNm();
      }
    } else if (segmentCode == 3) { // [入力]コード区分 = '3'（事業所コード）の場合、
      JigyoData jyoData = new JigyoData();
      jyoData = sysCom.getJigyoData(jgyCode);

      // [変数]事業所名称格納クラス.メッセージコード <> Nullの場合、
      if (jyoData.getMsgCd() != null) {
        nameWk = "UNKNOWN";
      } else { // [変数]事業所名称格納クラス.メッセージコード = Null の場合、
        nameWk = jyoData.getJgy().getJgymei();
      }
    }
    if (segmentCode == null || code == null) { // [入力]コード区分 = Null or
      // [入力]コード = Null の場合、
      jobNameWk = dataNameWk;
    } else {
      // 上記以外の場合、
      jobNameWk = dataNameWk + "" + code + ":" + nameWk;
    }
    Long jobNo = null;
    String jobType = "";
    // ジョブ実行状況データセット
    List<OwnSlipNumberingData> slipNoData = new ArrayList<OwnSlipNumberingData>();

    slipNoData = ownSlipNumbering(CommonConst.NO_JOB, Long.valueOf(
        CommonConst.NUMBER_CNT));

    if (slipNoData == null) {
      resultStatus = false;
      return resultStatus;
    } else {
      jobNo = slipNoData.get(0).getOwnSlipNumber();
    }

    // ジョブ種別設定
    if (typeWk.equals(CommonConst.TYPE_ORDINARY)) { // [変数]種別 = '1'（定時）の場合、
      jobType = CommonConst.JOB_TYPE_ORDINARY;
    } else if (typeWk.equals(CommonConst.TYPE_ANY_TIME)) { // [変数]種別 =
      // '2'（随時）の場合、
      jobType = CommonConst.JOB_TYPE_ANY_TIME;
    } else if (typeWk.equals(CommonConst.TYPE_FIXED)) { // [変数]種別 = '3'（定周期）の場合、
      jobType = CommonConst.JOB_TYPE_ORDINARY;
    } else { // 上記以外の場合、
      jobType = "";
    }

    String parameterWk = "";
    String messageWk = "";
    String paramJgyCode = "";
    // パラメータ設定
    // [入力]パラメータ = Null の場合、
    if (parameters == null) {
      parameterWk = "";
    } else { // [入力]パラメータ <> Null の場合、
      parameterWk = parameters;
    }

    // メッセージ設定
    // [入力]メッセージ = Null の場合、
    if (message == null) {
      messageWk = "";
    } else {
      // [入力]メッセージ <> Null の場合、
      // [入力]メッセージは一次配列で渡されるので、全ての要素を改行（0x0a＋0x0d）で繋げて、文字列に[変数]メッセージWKへ格納する。
      for (int i = 0; i < message.size(); i++) {
        messageWk = messageWk + message.get(i) + 0x0a + 0x0d;
      }
    }

    // ジョブ実行状況データ登録
    // [入力]事業所コード = Null の場合、
    if (jgyCode == null) {
      paramJgyCode = CommonConst.COMPANY_WIDE;
    } else { // [入力]事業所コード <> Null の場合、
      paramJgyCode = jgyCode;
    }

    try {
      // ジョブ実行状況情報をジョブ実行状況テーブルに登録
      TblCom01JobExec com01JobExec = new TblCom01JobExec();
      com01JobExec.setJobNo(jobNo);
      com01JobExec.setJobId(hulftId);
      com01JobExec.setJobNm(jobNameWk);
      com01JobExec.setJobTyp(jobType);
      com01JobExec.setJigyoCode(paramJgyCode);
      com01JobExec.setUserCodeExec(userCode);
      com01JobExec.setUserNmExec(userNameWk);
      com01JobExec.setFileNmExec(fileNameWk);
      com01JobExec.setParameters(parameterWk);
      com01JobExec.setStartDate(startDate);
      com01JobExec.setStartTime(startTime);
      com01JobExec.setEndDate(endDate);
      com01JobExec.setEndTime(endTime);
      com01JobExec.setResult(result);
      com01JobExec.setMessage(messageWk);
      com01JobExec.setRsFlg(deliveryDivisionWk);
      com01JobExec.setReFlg(reFlg);
      setCommonData(com01JobExec, userCode, "COM01", true);

      commonDao.getTblCom01JobExecMapper().insert(com01JobExec);

      // コミット
      txManager.commit(status);
    } catch (Exception e) {
      logger.error(e.getMessage());
      resultStatus = false;
      // ロールバック
      txManager.rollback(status);
    }

    return resultStatus;
  }

  /**
   * 共通項目の設定.
   *
   * @param data MstUser
   * @param strUserId ユーザID
   * @param strProgId プログラムID
   * @param isNew 新規／更新
   * @return TblCom01JobExec
   */
  public TblCom01JobExec setCommonData(TblCom01JobExec data, String strUserId,
      String strProgId,
      boolean isNew) {

    String currentDate = DateUtil.getSysDate();
    String currentTime = DateUtil.getSysTime();

    if (isNew) {
      data.setInsUserid(strUserId);
      data.setInsPgid(strProgId);
      data.setInsYmd(currentDate);
      data.setInsTime(currentTime);
    }

    data.setUpdUserid(strUserId);
    data.setUpdPgid(strProgId);
    data.setUpdYmd(currentDate);
    data.setUpdTime(currentTime);

    return data;
  }

  /**
   * １明細当たり金額算出
   *
   * @param unitPrice 単価
   * @param orderCnt 受注数
   * @param taxRate 消費税率
   * @param taxIncKb 内税顧客区分
   * @param taxIncFrcKb 内税消費税端数処理
   * @param shipsRudKb 伝票行計算金額まるめ
   * @return １明細当たり金額情報格納クラス
   * @throws Exception
   */
  public OneSpecPriceData getOneSpecPrice(double unitPrice, Integer orderCnt,
      double taxRate, String taxIncKb, String taxIncFrcKb, String shipsRudKb)
          throws Exception {

    // 変数宣言
    BigDecimal priceWk; // 金額
    BigDecimal unitPriceHWk; // 単価（本体）
    BigDecimal bodyPriceWk; // 金額（本体）
    BigDecimal taxPriceWk; // 消費税額

    // 初期化
    Boolean flgSuccess = false; // 処理結果格納用フラグ（0:処理失敗、1:処理成功）
    UnitTaxPriceData unitTaxPriceData = new UnitTaxPriceData(); // 単価情報格納クラス
    OneSpecPriceData oneSpecPriceData = new OneSpecPriceData();  // 戻り値

    try {

      // 1. 単価無編集にて、金額を算出する。
      // 変数[金額] = 入力[単価] ×入力[受注数]
      priceWk = BigDecimal.valueOf(NumberUtil.multiply(unitPrice, orderCnt));

      // 2. 本体価格算出
      if (taxIncKb.equals("1")) {
        // 変数[内税顧客区分] = 1 の場合（外税顧客）

        // ①変数[単価(本体)] = 入力[単価]
        unitPriceHWk = BigDecimal.valueOf(unitPrice);
        // ②変数[金額(本体)] = 変数[金額]
        bodyPriceWk = priceWk;

      } else {
        // 変数[内税顧客区分] = 2 の場合（内税顧客）

        // ①単価(本体)算出
        // 変数[単価(本体)] = 共通部品【単価消費税計算(モジュール名)】
        unitTaxPriceData = getUnitTaxPrice(unitPrice, taxRate,
            taxIncFrcKb);
        unitPriceHWk = BigDecimal.valueOf(unitTaxPriceData.getWithoutTax());

        // ②金額(本体)算出
        // 変数[金額(本体)] = 変数[単価(本体)] × 入力[受注数]
        bodyPriceWk = BigDecimal.valueOf(NumberUtil.multiply(unitPriceHWk
            .doubleValue(), orderCnt));

      }

      // 3. 消費税額算出
      if (taxIncKb.equals("1")) {
        // 変数[内税顧客区分] = 1 の場合（外税顧客）
        // 変数[消費税額] = 変数[金額(本体)] × 入力[消費税率] / 100
        taxPriceWk = BigDecimal.valueOf(NumberUtil.divide(NumberUtil.multiply(bodyPriceWk
            .doubleValue(), taxRate), (double)100));
      } else {
        // 変数[内税顧客区分] = 2 の場合（内税顧客）
        // 変数[消費税額] = 変数[単価消費税] × 入力[受注数]
        taxPriceWk = BigDecimal.valueOf(NumberUtil.multiply(unitTaxPriceData
            .getUnitPriceTax(), orderCnt));
      }

      // 4. 端数処理
      // 引数［伝票行計算金額丸め］に応じて、端数処理を行う。
      if (shipsRudKb.equals("1") || shipsRudKb.equals("2") || shipsRudKb.equals(
          "3")) {
        priceWk = getNumberRound(priceWk.doubleValue(), 1, shipsRudKb);
        bodyPriceWk = getNumberRound(bodyPriceWk.doubleValue(), 1, shipsRudKb);
        taxPriceWk = getNumberRound(taxPriceWk.doubleValue(), 1, shipsRudKb);
      }

      // 処理結果設定
      // 1. 内部編集初期化

      // 変数[WK処理結果] = True
      flgSuccess = true;

      // 出力[金額情報格納クラス.単価（本体）] = 変数[単価(本体)]
      oneSpecPriceData.setBodyUnitPrice(unitPriceHWk.doubleValue());

      // 出力[金額情報格納クラス.金額（本体）] = 変数[金額(本体)]
      oneSpecPriceData.setBodyPrice(bodyPriceWk.doubleValue());

      // 出力[金額情報格納クラス.金額] = 変数[金額]
      oneSpecPriceData.setPrice(priceWk.doubleValue());

      // 出力[金額情報格納クラス.消費税額] = 変数[消費税額]
      oneSpecPriceData.setTaxPrice(taxPriceWk.doubleValue());

    } catch (Exception e) {
      throw e;
    } finally {

      // 【終了処理・例外処理】
      // 1.出力変数セット
      // 変数[WK処理結果] = Trueの場合、出力[金額情報格納クラス]で復帰する
      if (flgSuccess == false) {
        // falseの場合、nullで復帰する
        oneSpecPriceData = null;
      }
    }

    return oneSpecPriceData;

  }

  /**
   * 単価消費税計算
   *
   * @param unitPrice 単価
   * @param taxRate 消費税率
   * @param taxIncFrcKb 内税消費税端数処理
   * @return unitTaxPriceData 単価情報格納クラス
   */
  public UnitTaxPriceData getUnitTaxPrice(double unitPrice,
      double taxRate, String taxIncFrcKb) {
    UnitTaxPriceData unitTaxPriceData = new UnitTaxPriceData(); // 戻り値

    // 単価消費税を算出

    // [変数]単価消費税WK = [入力]単価 × [入力]消費税率 ÷ （100 ＋ [入力]消費税率）
    double taxRateWk = NumberUtil.add(taxRate, 100);
    double unitPriceTaxWk = NumberUtil.multiply(unitPrice, taxRate);
    unitPriceTaxWk = NumberUtil.divide(unitPriceTaxWk, taxRateWk);

    // 共通部品【システム共通.数値丸め】向け引数設定
    int intRoundPoint; // 丸め少数桁
    String strRoundClass; // 丸め種別

    if (taxIncFrcKb.equals("1")) {
      intRoundPoint = 1;
      strRoundClass = "2";
    } else if (taxIncFrcKb.equals("2")) {
      intRoundPoint = 1;
      strRoundClass = "3";
    } else if (taxIncFrcKb.equals("3")) {
      intRoundPoint = 1;
      strRoundClass = "3";
    } else if (taxIncFrcKb.equals("4")) {
      intRoundPoint = 2;
      strRoundClass = "2";
    } else if (taxIncFrcKb.equals("5")) {
      intRoundPoint = 2;
      strRoundClass = "3";
    } else if (taxIncFrcKb.equals("6")) {
      intRoundPoint = 2;
      strRoundClass = "1";
    } else if (taxIncFrcKb.equals("7")) {
      intRoundPoint = 3;
      strRoundClass = "2";
    } else if (taxIncFrcKb.equals("8")) {
      intRoundPoint = 3;
      strRoundClass = "3";
    } else {
      intRoundPoint = 3;
      strRoundClass = "1";
    }

    // 数値丸め処理
    // [出力]単価情報格納クラス.単価消費税 = 共通部品【システム共通.数値丸め】
    BigDecimal unitTaxWk = getNumberRound(unitPriceTaxWk, intRoundPoint,
        strRoundClass);
    unitTaxPriceData.setUnitPriceTax(unitTaxWk.doubleValue());

    // 税抜き単価を算出
    double withoutTaxWk = NumberUtil.minus(unitPrice, unitTaxPriceData
        .getUnitPriceTax());
    unitTaxPriceData.setWithoutTax(withoutTaxWk);

    return unitTaxPriceData;

  }

}
