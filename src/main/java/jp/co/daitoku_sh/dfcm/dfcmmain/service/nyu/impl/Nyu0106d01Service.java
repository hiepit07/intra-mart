/*
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl
 * ファイル名:Nyu0106d01Service.java
 * 
 * 作成者:都築電気
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

package jp.co.daitoku_sh.dfcm.dfcmmain.service.nyu.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.OwnSlipNumberingData;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.TblNyu01KaikeiRenkeiRireki;
import jp.co.daitoku_sh.dfcm.common.service.AbsService;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;
import jp.co.daitoku_sh.dfcm.common.util.CommonGetSystemCom;
import jp.co.daitoku_sh.dfcm.common.util.DateUtil;
import jp.co.daitoku_sh.dfcm.common.util.Dfcmlogger;
import jp.co.daitoku_sh.dfcm.common.util.Util;
import jp.co.daitoku_sh.dfcm.dfcmmain.cnst.NyuConst;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.DefaultMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.FormNyu0106d01;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.Nyu0106d01ShiwakeInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.component.nyu.Nyu0106d01ShiwakeSakuseiRirekiInfo;
import jp.co.daitoku_sh.dfcm.dfcmmain.dao.nyu.impl.Nyu0106d01Dao;

/**
 * 会計入金実績作成用 Service
 * 
 * @author anzai
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Service
public class Nyu0106d01Service extends AbsService {

  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  @Autowired
  private Nyu0106d01Dao nyu0106d01Dao;

  @Autowired
  private CommonDao commonDao;

  @Autowired
  private ApplicationContext appContext;

  private Dfcmlogger logger = new Dfcmlogger();

  /**
   * DEFAULT MESSAGE の取得.
   * 
   * @return メッセージリスト
   */
  public List<DefaultMessages> getDefaultMess() {

    // TODO: 要修正
    DefaultMessages defaultMsg = new DefaultMessages();

    defaultMsg.setMessageCode("COM001-I");
    defaultMsg.setMessageTitle("確認メッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-I", null));

    List<DefaultMessages> defaultMsgList = new ArrayList<DefaultMessages>();
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM001-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM001-E", null));
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM006-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM006-E", null));
    defaultMsgList.add(defaultMsg);

    defaultMsg = new DefaultMessages();
    defaultMsg.setMessageCode("COM008-E");
    defaultMsg.setMessageTitle("エラーメッセージ");
    defaultMsg.setMessageContent(readPropertiesFileService.getMessage(
        "COM008-E", null));
    defaultMsgList.add(defaultMsg);

    return defaultMsgList;
  }

  /**
   * 仕訳作成履歴リストの取得
   * 
   * @param form フォーム
   */
  public void getShiwakeSakuseiRirekiList(FormNyu0106d01 form) {

    List<Nyu0106d01ShiwakeSakuseiRirekiInfo> rirekiList = nyu0106d01Dao
        .getNyu0106d01Mapper().getShiwakeSakuseiRirekiList();

    // 履歴リスト
    form.setRirekiList(rirekiList);

    // 前回作成日付・時刻
    for (Nyu0106d01ShiwakeSakuseiRirekiInfo rireki : rirekiList) {

      if (rireki.getShubetsu().equals("1")) {

        form.setPrevCreateDate(rireki.getCreateDate());
        form.setPrevCreateTime(rireki.getCreateTime());

        break;
      }

    }
  }

  /**
   * 作成対象件数の取得
   * 
   * @param form
   * @return 仕訳件数
   */
  public int getSakuseiKensu(FormNyu0106d01 form) {

    int nyukinKensu = 0;
    int shiwakeKensu = 0;

    Map<String, Object> resultMap = nyu0106d01Dao.getNyu0106d01Mapper()
        .getKonkaiNyukinShiwakeSakuseiCount();

    if (resultMap != null && resultMap.size() > 0) {

      String strNyukinKensu = String.valueOf(resultMap.get("NYUKIN_KENSU"));
      String strShiwakeKensu = String.valueOf(resultMap.get("SHIWAKE_KENSU"));

      nyukinKensu = Integer.valueOf(strNyukinKensu);
      shiwakeKensu = Integer.valueOf(strShiwakeKensu);

      form.setCurNyukinCount(nyukinKensu);
      form.setCurShiwakeCount(shiwakeKensu);

    } else {

      form.setCurNyukinCount(0);
      form.setCurShiwakeCount(0);

    }
    return shiwakeKensu;
  }

  /**
   * 入金仕訳CSVデータの作成
   * 
   * @param form フォーム
   * @param userId ユーザID
   * @param errorList エラーリスト
   */
  public void createNyukinShiwakeCsv(FormNyu0106d01 form, String userId,
      ArrayList<ErrorMessages> errorList) {

    // ---------------------------------------------------------------------
    // CSV出力
    // ---------------------------------------------------------------------

    // 保存フォルダ
    String path = readPropertiesFileService.getSetting("PATH_CSV");

    // 仕訳データ一覧
    NyukinShiwakeResultHandler handler = new NyukinShiwakeResultHandler();

    handler.csvPrinter = null;
    handler.outputStreamWriter = null;

    handler.fileNameList = new ArrayList<String>();
    handler.folderPath = path;
    handler.nyukinKejobiWrk = "";
    handler.outCount = 0;
    handler.btnName = form.getBtnName();

    logger.debug("▽▽▽ CSV出力 ▽▽▽");
    nyu0106d01Dao.getNyu0106d01Mapper().getNyukinShiwakeList(null, handler);
    logger.debug("△△△ CSV出力 outCount=[" + handler.outCount + "] △△△");

    try {
      handler.outputStreamWriter.close();
      handler.csvPrinter.close();
    } catch (IOException ex) {
      logger.debug("ファイルクローズエラー");
      throw new RuntimeException(ex);
    }

    // ---------------------------------------------------------------------
    // 会計連携IDの取得
    // ---------------------------------------------------------------------

    // システム共通部品
    CommonGetSystemCom commonGetSysCom = new CommonGetSystemCom(commonDao, null,
        appContext,
        readPropertiesFileService);

    List<OwnSlipNumberingData> idList = null;
    try {

      idList = commonGetSysCom.ownSlipNumbering("14", 1L);

    } catch (Exception ex) {

      logger.error("会計連携ID 採番エラー [" + ex.getMessage() + "]");
      ex.printStackTrace();

      // COM015-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("会計連携IDの採番");

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM015-E", params));
      errorList.add(errorMsg);

      return;
    }

    if (idList == null || idList.size() == 0) {

      logger.error("会計連携ID  採番エラー idList == empty");

      // COM015-E
      ArrayList<String> params = new ArrayList<String>();
      params.add("会計連携IDの採番");

      ErrorMessages errorMsg = new ErrorMessages();
      errorMsg.setMessageContent(readPropertiesFileService.getMessage(
          "COM015-E", params));
      errorList.add(errorMsg);

      return;
    }

    Long renkeiId = idList.get(0).getOwnSlipNumber();

    logger.debug("会計連携ID=[" + renkeiId + "]");

    // -------------------------------------------------------------------------
    // DB更新
    // -------------------------------------------------------------------------

    // トランザクション変数
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
        .getBean("txManager");
    TransactionStatus status = null;

    try {
      logger.debug("▼▼▼ トランザクション開始 ▼▼▼");
      status = txManager.getTransaction(def);
      logger.debug("▲▲▲ トランザクション開始 ▲▲▲");

      // 入金仕訳の更新
      logger.debug("▽▽▽ 入金仕訳 UPDATE ▽▽▽");
      int updCount = nyu0106d01Dao.updateRenkeiSumiOnNyukinShiwake(String
          .valueOf(
              renkeiId));
      logger.debug("△△△ 入金仕訳 UPDATE updCount = [" + updCount + "]△△△");

      // 会計連携テーブルの登録
      TblNyu01KaikeiRenkeiRireki rirekiObject = new TblNyu01KaikeiRenkeiRireki();

      String createDate = DateUtil.getSysDate();
      String createTime = DateUtil.getSysTime();

      // 会計連携ID
      rirekiObject.setKaikeiRenkeiId(renkeiId);
      // 作成日付
      rirekiObject.setCreateDate(createDate);
      // 作成時刻
      rirekiObject.setCreateTime(createTime);
      // 種別
      rirekiObject.setNyuUriCls("1");
      // 入金／売上件数
      rirekiObject.setNyuUriCount(1L);
      // 仕訳件数
      rirekiObject.setShiwakeCount(1L);
      // 出力回数
      rirekiObject.setOutputCount(1L);
      // 状況コード
      rirekiObject.setStsCode("1");
      // 共通項目
      rirekiObject.setInsUserid(userId);
      rirekiObject.setInsPgid(NyuConst.PG_ID_KAIKEI_NYUKIN_JISSEKI_SAKUSEI);
      rirekiObject.setInsYmd(createDate);
      rirekiObject.setInsTime(createTime);
      rirekiObject.setUpdUserid(userId);
      rirekiObject.setUpdPgid(NyuConst.PG_ID_KAIKEI_NYUKIN_JISSEKI_SAKUSEI);
      rirekiObject.setUpdYmd(createDate);
      rirekiObject.setUpdTime(createTime);

      logger.debug("▽▽▽ 会計連携テーブル INSERT ▽▽▽");
      nyu0106d01Dao.getTblNyu01KaikeiRenkeiRirekiMapper().insert(rirekiObject);
      logger.debug("△△△ 会計連携テーブル INSERT △△△");

      logger.debug("▼▼▼ コミット ▼▼▼");
      txManager.commit(status);
      logger.debug("▲▲▲ コミット ▲▲▲");

      form.setCsvLinkList(handler.fileNameList);

    } catch (Exception ex) {

      logger.debug("テーブル更新エラー");
      ex.printStackTrace();

      logger.debug("▼▼▼ ロールバック ▼▼▼");
      txManager.rollback(status);
      logger.debug("▲▲▲ ロールバック ▲▲▲");

      throw new RuntimeException(ex);

    }

  }

  /**
   * CSVファイルの出力
   * 
   * @param form フォーム
   * @param userId ユーザID
   * @return ファイル名
   */
  public String outputCsv(FormNyu0106d01 form, String userId) {

    String rtnFileName = "";
    String fileName = "";

    String renkeiId = form.getCsvRenkeiId();
    String shubetsu = form.getCsvShubetsu();
    String createDate = form.getCsvCreateDate();
    String createTime = form.getCsvCreateTime();

    // ---------------------------------------------------------------------
    // CSV出力
    // ---------------------------------------------------------------------

    // 保存フォルダ
    String path = readPropertiesFileService.getSetting("PATH_CSV");
    int outputCount = 0;

    if (shubetsu.equals("1")) {

      fileName = "入金_" + createDate + "_" + createTime + ".csv";
      // 入金仕訳ハンドラ
      NyukinShiwakeResultHandler handler = new NyukinShiwakeResultHandler();
      
      try {
        
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
            new FileOutputStream(path + "\\" + fileName), "Windows-31J");
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
            CommonConst.NEW_LINE_SEPARATOR);
        CSVPrinter csvPrinter = new CSVPrinter(outputStreamWriter,
            csvFileFormat);

        handler.csvPrinter = csvPrinter;
        handler.outCount = 0;
        handler.btnName = form.getBtnName();

        logger.debug("▽▽▽ CSV出力 ▽▽▽");
        nyu0106d01Dao.getNyu0106d01Mapper().getNyukinShiwakeList(renkeiId, handler);
        outputCount = handler.outCount;
        logger.debug("△△△ CSV出力 outCount=[" + outputCount + "] △△△");

        outputStreamWriter.close();
        csvPrinter.close();

      } catch (IOException ex) {
        logger.debug("CSV出力エラー");
        throw new RuntimeException(ex);
      }
    } else {
      fileName = "売上_" + createDate + "_" + createTime + ".csv";
      // 売上仕訳ハンドラ
      UriageShiwakeResultHandler handler = new UriageShiwakeResultHandler();
      
      try {
        
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
            new FileOutputStream(path + "\\" + fileName), "Windows-31J");
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
            CommonConst.NEW_LINE_SEPARATOR);
        CSVPrinter csvPrinter = new CSVPrinter(outputStreamWriter,
            csvFileFormat);

        handler.csvPrinter = csvPrinter;
        handler.outCount = 0;

        logger.debug("▽▽▽ CSV出力 ▽▽▽");
        nyu0106d01Dao.getNyu0106d01Mapper().getNyukinShiwakeList(renkeiId, handler);
        outputCount = handler.outCount;
        logger.debug("△△△ CSV出力 outCount=[" + outputCount + "] △△△");

        outputStreamWriter.close();
        csvPrinter.close();

      } catch (IOException ex) {
        logger.debug("CSV出力エラー");
        throw new RuntimeException(ex);
      }
    }
    
    if (outputCount == 0) {
      logger.error("CSV出力件数 0件");
      return "";
    }

    // -------------------------------------------------------------------------
    // DB更新
    // -------------------------------------------------------------------------

    // トランザクション変数
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    PlatformTransactionManager txManager = (PlatformTransactionManager) appContext
        .getBean("txManager");
    TransactionStatus status = null;

    try {
      logger.debug("▼▼▼ トランザクション開始 ▼▼▼");
      status = txManager.getTransaction(def);
      logger.debug("▲▲▲ トランザクション開始 ▲▲▲");

      // 会計連携の更新
      logger.debug("▽▽▽ 会計連携 UPDATE ▽▽▽");
      int updCount = nyu0106d01Dao.updateOutputCountOnKaikeiRenkeiRireki(
          renkeiId);
      logger.debug("△△△ 会計連携 UPDATE updCount = [" + updCount + "]△△△");

      logger.debug("▼▼▼ コミット ▼▼▼");
      txManager.commit(status);
      logger.debug("▲▲▲ コミット ▲▲▲");

      rtnFileName = fileName;

    } catch (Exception ex) {

      logger.debug("テーブル更新エラー");
      ex.printStackTrace();

      logger.debug("▼▼▼ ロールバック ▼▼▼");
      txManager.rollback(status);
      logger.debug("▲▲▲ ロールバック ▲▲▲");

      throw new RuntimeException(ex);

    }

    return rtnFileName;

  }

  // ---------------------------------------------------------------------------
  // INNER CLASS
  // ---------------------------------------------------------------------------

  /**
   * 入金仕訳CSV 出力用 Result Handler.
   * 
   * @author anzai
   * @version 1.0.0
   * @since 1.0.0
   *
   */
  private class NyukinShiwakeResultHandler implements ResultHandler {

    // ボタン名
    private String btnName;

    // ファイルハンドル
    private CSVPrinter csvPrinter;
    private OutputStreamWriter outputStreamWriter;

    // 出力フォルダ
    private String folderPath;

    // ファイル名リスト
    private List<String> fileNameList;

    // 入金計上日WRK
    private String nyukinKejobiWrk;

    // カウンタ
    private int outCount;

    /**
     * 逐次処理.
     */
    @Override
    public void handleResult(ResultContext context) {

      Nyu0106d01ShiwakeInfo shiwake = (Nyu0106d01ShiwakeInfo) context
          .getResultObject();

      outCount++;

      try {
        if (btnName.equals("btnProc")) {
          if (!shiwake.getNyukinKeijobi().equals(nyukinKejobiWrk)) {

            // 入金計上日 BREAK
            if (outputStreamWriter != null) {
              outputStreamWriter.close();
            }
            if (csvPrinter != null) {
              csvPrinter.close();
            }

            // ファイル名
            String fileName = "入金_" + shiwake.getNyukinKeijobi() + "_"
                + DateUtil
                    .getSysTime() + ".csv";
            fileNameList.add(fileName);

            // CSVファイルを作成する
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(
                folderPath + "\\" + fileName), "Windows-31J");
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(
                CommonConst.NEW_LINE_SEPARATOR);
            csvPrinter = new CSVPrinter(outputStreamWriter, csvFileFormat);

          }
        }

        // 適用
        csvPrinter.print(Util.convertUnsanitize(shiwake.getTokuisakiName()));
        // 借方事業所
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrJigyoshoCd()));
        // 借方発生場所
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrHasseiBasho()));
        // 借方勘定科目
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrKanjoKamoku()));
        // 借方勘定科目コード
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrKanjoKamokuCd()));
        // 借方補助項目
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrHojoKamoku()));
        // 借税区分
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrTaxKbn()));
        // 借税区分名
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrTaxKbnName()));
        // 貸方事業所
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrJigyoshoCd()));
        // 貸方発生場所
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrHasseiBasho()));
        // 貸方勘定科目
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrKanjoKamoku()));
        // 貸方勘定科目コード
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrKanjoKamokuCd()));
        // 貸方補助項目
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrHojoKamoku()));
        // 貸税区分
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrTaxKbn()));
        // 貸方区分名
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrTaxKbnName()));
        // 金額
        csvPrinter.print(Util.convertUnsanitize(String.valueOf(shiwake
            .getKingaku())));

        // 次のレコード
        csvPrinter.println();

      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

  }

  /**
   * 売上仕訳CSV 出力用 Result Handler.
   * 
   * @author anzai
   * @version 1.0.0
   * @since 1.0.0
   *
   */
  private class UriageShiwakeResultHandler implements ResultHandler {

    // ファイルハンドル
    private CSVPrinter csvPrinter;
    // カウンタ
    private int outCount;

    /**
     * 逐次処理.
     */
    @Override
    public void handleResult(ResultContext context) {

      Nyu0106d01ShiwakeInfo shiwake = (Nyu0106d01ShiwakeInfo) context
          .getResultObject();

      outCount++;

      try {

        // 適用
        csvPrinter.print(Util.convertUnsanitize(shiwake.getTokuisakiName()));
        // 借方事業所
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrJigyoshoCd()));
        // 借方発生場所
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrHasseiBasho()));
        // 借方勘定科目
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrKanjoKamoku()));
        // 借方勘定科目コード
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrKanjoKamokuCd()));
        // 借方補助項目
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrHojoKamoku()));
        // 借税区分
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrTaxKbn()));
        // 借税区分名
        csvPrinter.print(Util.convertUnsanitize(shiwake.getDrTaxKbnName()));
        // 貸方事業所
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrJigyoshoCd()));
        // 貸方発生場所
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrHasseiBasho()));
        // 貸方勘定科目
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrKanjoKamoku()));
        // 貸方勘定科目コード
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrKanjoKamokuCd()));
        // 貸方補助項目
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrHojoKamoku()));
        // 貸税区分
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrTaxKbn()));
        // 貸方区分名
        csvPrinter.print(Util.convertUnsanitize(shiwake.getCrTaxKbnName()));
        // 金額
        csvPrinter.print(Util.convertUnsanitize(String.valueOf(shiwake
            .getKingaku())));

        // 次のレコード
        csvPrinter.println();

      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

  }

}
