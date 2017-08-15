/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.common.util
 * ファイル名:CommonGetSystemError.java
 * 
 * <p>作成者:株式会社アクト・ブレーン
 * 作成日:2015/10/05
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/05 1.00 ABV)NhungMa 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.common.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

import jp.co.daitoku_sh.dfcm.common.cnst.CommonConst;
import jp.co.daitoku_sh.dfcm.common.component.ErrorInfo;
import jp.co.daitoku_sh.dfcm.common.component.ErrorMessages;
import jp.co.daitoku_sh.dfcm.common.dao.impl.CommonDao;
import jp.co.daitoku_sh.dfcm.common.db.model.MstCustomer;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListForm;
import jp.co.daitoku_sh.dfcm.common.db.model.MstListFormExample;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyo;
import jp.co.daitoku_sh.dfcm.common.db.model.MstSJigyoExample;
import jp.co.daitoku_sh.dfcm.common.service.impl.ReadPropertiesFileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * システム共通エラーリスト
 * 
 * @author ABV)NhungMa
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonGetSystemError {

  @Autowired
  private PlatformTransactionManager txManager;
  @Autowired
  private ApplicationContext appContext;
  @Autowired
  private ReadPropertiesFileService readPropertiesFileService;

  /** 共通DAO. */
  private CommonDao commonDao;

  private List<ErrorInfo> lstError;
  /** ログ出力用. */
  private Dfcmlogger logger = new Dfcmlogger();
  
  /**
   * コンストラクタ.
   * @param init:CommonDao
   * @param readPropertiesFileService:ReadPropertiesFileService
   * @param lstError :list components of ErrorInfo
   */
  public CommonGetSystemError(CommonDao init,
      ReadPropertiesFileService readPropertiesFileService,
      List<ErrorInfo> lstError) {
    this.commonDao = init;
    this.readPropertiesFileService = readPropertiesFileService;
    this.lstError = lstError;
  }

  /**
   * outputエラーリスト.
   * 
   * @return string
   * @throws Exception file create tsv
   */
  public String output() throws Exception {
    String result = null;
    String businessDate = null;
    String srvName = "";
    String directory = "";
    String listName = "";
    ArrayList<String> paramMess = new ArrayList<String>();
    // 共通部品初期化
    PrintUtil printUtil = new PrintUtil(commonDao);
    CommonGetSystemCom systemCom = new CommonGetSystemCom(commonDao, txManager,
        appContext, readPropertiesFileService);

    businessDate = systemCom.getAplDate();
    ErrorMessages msgError = new ErrorMessages();
    if (businessDate == null) {
      msgError = new ErrorMessages();
      paramMess = new ArrayList<String>();
      paramMess.add("システムコントロール");
      paramMess.add("業務日付");
      msgError.setMessageContent(readPropertiesFileService.getMessage(
          "COM009-E",
          paramMess));
      result = msgError.getMessageContent();
    } else {
      Collections.sort(lstError, new SystemErrorInfoComparator());

      // 帳票管理マスタ取得
      List<MstListForm> mstlstForm = null;
      mstlstForm = listForm();
      if (mstlstForm.size() <= 0) {
        msgError = new ErrorMessages();
        paramMess = new ArrayList<String>();
        paramMess.add("帳票定義マスタ");
        paramMess.add("帳票定義");
        msgError.setMessageContent(readPropertiesFileService.getMessage(
            "COM009-E",
            paramMess));
        result = msgError.getMessageContent();
        return result;
      } else { // 取得したデータを以下に設定する。
        srvName = mstlstForm.get(0).getListSvrNm();
        directory = mstlstForm.get(0).getListDir();
        listName = mstlstForm.get(0).getListNm();
      }

      String fileCreateDateTime = "";
      Date dateTsv = new Date();
      fileCreateDateTime = DateUtil.setFormat(dateTsv,
          CommonConst.DATE_FORMAT_YMDHMS);

      String pathFile = "COM01-05L01_" + fileCreateDateTime + ".tsv";

      String pathFolder = directory;
      String folderFile = pathFolder + "\\" + pathFile;
      
      CSVPrinter tsvFilePrinter = null;
      File file = null;
      String officeCodeWk = "";
      String customerCodeWk = "";
      int loopTime = 0;
      String officeName = "";
      String customerName = "";
      List<MstSJigyo> listOffice = new ArrayList<MstSJigyo>();
      try {
        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(
            folderFile), "UTF-8");
        fileWriter.write("\uFEFF");
        CSVFormat tsvFileFormat = CSVFormat.DEFAULT.withDelimiter('\t')
            .withRecordSeparator(CommonConst.NEW_LINE_SEPARATOR);
        
        tsvFilePrinter = new CSVPrinter(fileWriter, tsvFileFormat);

        for (int i = 0; i < lstError.size(); i++) {
          ErrorInfo errorItem = lstError.get(i);
          List<MstSJigyo> mstListSjigyo = null;
          // [変数]事業所コードWK <> [変数]エラー情報.事業所コード AND [変数]エラー情報.事業所コード <> Null And
          // [変数]エラー情報.事業所コード <> '' の場合、
          if (errorItem.getOfficeCode() != null && !errorItem.getOfficeCode()
              .equals("") && !officeCodeWk
                  .equals(errorItem.getOfficeCode())) {
            mstListSjigyo = getOfficeName(errorItem.getOfficeCode(),
                businessDate);

            // 該当データが存在しない場合、以下のメッセージで復帰する
            if (mstListSjigyo == null || mstListSjigyo.size() <= 0) {
              msgError = new ErrorMessages();
              paramMess = new ArrayList<String>();
              paramMess.add("事業所マスタ");
              paramMess.add("事業所名称");
              msgError.setMessageContent(readPropertiesFileService.getMessage(
                  "COM009-E",
                  paramMess));
              result = msgError.getMessageContent();
              return result;
            } else {
              officeName = mstListSjigyo.get(0).getJgymei();
            }
            // [変数]エラー情報.事業所コード = Null Or [変数]エラー情報.事業所コード = '' の場合、
          } else if (errorItem.getOfficeCode() == null || errorItem
              .getOfficeCode().equals("")) {
            officeName = "";
          }
          if (errorItem.getCustomerCode() != null && !errorItem
              .getCustomerCode().equals("") && !customerCodeWk.equals(errorItem
                  .getCustomerCode())) {
            // 得意先名称取得
            List<MstCustomer> cusItem = new ArrayList<MstCustomer>();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("custCode", errorItem.getCustomerCode());
            params.put("businessDate", businessDate);
            cusItem = commonDao.getComMapper().getCustomerName(params);
            if (cusItem.size() <= 0) {
              msgError = new ErrorMessages();
              paramMess = new ArrayList<String>();
              paramMess.add("得意先マスタ");
              paramMess.add("得意先略称");
              msgError.setMessageContent(readPropertiesFileService.getMessage(
                  "COM009-E",
                  paramMess));
              result = msgError.getMessageContent();
              return result;
            } else {
              customerName = cusItem.get(0).getCustNm();
            }
          } else if (errorItem.getCustomerCode() == null || errorItem
              .getCustomerCode().equals("")) {
            customerName = "";
          }

          // ブレーク判定
          if (officeCodeWk != null && !officeCodeWk.equals(errorItem
              .getOfficeCode())
              && loopTime != 0) {
            fileWriter.close();
            tsvFilePrinter.close();
            // [変数]事業所コードWK = Null Or [変数]事業所コードWK = '' の場合、
            if (officeCodeWk == null || officeCodeWk.equals("")) {
              listOffice = new ArrayList<MstSJigyo>();
              // 取得した事業所一覧を[変数]事業所リストに格納する
              listOffice = getSjgyCd(businessDate);
            }
            // LIST WORKS 印刷
            if (officeCodeWk != null && !officeCodeWk.equals("")) {
              file = new File(folderFile);

              // 共通部品［ListWorks印刷処理］
              printUtil.exePrint("COM01-05L01", CommonConst.ISSUE_TYPE, officeCodeWk, listName,
                  file);
            } else if (officeCodeWk == null || officeCodeWk.equals("")) {
              if (listOffice.size() > 0) {

                // [変数]事業所リストの件数分、以下呼び出しを繰り返す
                for (MstSJigyo itemLst : listOffice) {
                  
                  // 共通部品［ListWorks印刷処理］
                  printUtil.exePrint("COM01-05L01", CommonConst.ISSUE_TYPE, itemLst.getJgycd(),
                     listName, file);
                }
              }
            }
            fileWriter = new OutputStreamWriter(new FileOutputStream(
                folderFile, true), "UTF-8");
            fileWriter.write("\uFEFF");
            tsvFilePrinter = new CSVPrinter(fileWriter, tsvFileFormat);

            // エラーリスト出力
            outputErrorList(tsvFilePrinter, errorItem, officeName,
                customerName);
            // 以下の変数を更新
            officeCodeWk = errorItem.getOfficeCode();
            customerCodeWk = errorItem.getCustomerCode();
            loopTime = loopTime + 1;
          } else { // 11からの処理を行う
            // エラーリスト出力
            outputErrorList(tsvFilePrinter, errorItem, officeName,
                customerName);
            // 以下の変数を更新
            officeCodeWk = errorItem.getOfficeCode();
            customerCodeWk = errorItem.getCustomerCode();
            loopTime = loopTime + 1;
          }
        }
        fileWriter.flush();
        fileWriter.close();
        tsvFilePrinter.close();

        // [変数]事業所コードWK = Null Or [変数]事業所コードWK = '' の場合、
        if (officeCodeWk == null || officeCodeWk.equals("")) {
          listOffice = new ArrayList<MstSJigyo>();
          // 取得した事業所一覧を[変数]事業所リストに格納する
          listOffice = getSjgyCd(businessDate);
        }

        // LIST WORKS 印刷
        if (officeCodeWk != null && !officeCodeWk.equals("")) {
          file = new File(folderFile);

          // 共通部品［ListWorks印刷処理］
          printUtil.exePrint("COM01-05L01", CommonConst.ISSUE_TYPE, officeCodeWk, listName,
              file);
        } else if (officeCodeWk == null || officeCodeWk.equals("")) {
          if (listOffice.size() > 0) {

            // [変数]事業所リストの件数分、以下呼び出しを繰り返す
            for (MstSJigyo itemLst : listOffice) {

              // 共通部品［ListWorks印刷処理］
              printUtil.exePrint("COM01-05L01", CommonConst.ISSUE_TYPE, itemLst.getJgycd(),
                 listName, file);
            }
          }
        }
      } catch (Exception e) {
        logger.error(e.getMessage());
        throw e;
      }
    }
    return result;
  }
 
  /**
   * エラー発生データ情報出力.
   * @param tsvFilePrinter:CSVPrinter for file tsv
   * @param errorItem:ErrorInfo
   * @param officeName:事業所名称
   * @param customerName:得意先名称
   * @param fileWriter:FileWriter for file tsv
   */
  private void outputErrorList(CSVPrinter tsvFilePrinter,
      ErrorInfo errorItem, String officeName, String customerName) {
    try {
      // エラー発生データ情報出力
      tsvFilePrinter.print(CommonConst.VALUE_TYPE_ERROR_1);
      tsvFilePrinter.print(errorItem.getDateTime());
      tsvFilePrinter.print(errorItem.getDataName());
      tsvFilePrinter.print(errorItem.getOfficeCode());
      tsvFilePrinter.print(officeName);
      tsvFilePrinter.print(errorItem.getCustomerCode());
      tsvFilePrinter.print(customerName);

      tsvFilePrinter.print(errorItem.getDeadline());
      tsvFilePrinter.print(errorItem.getFlight());
      tsvFilePrinter.print(errorItem.getShopCode());
      tsvFilePrinter.print(errorItem.getShopName());
      tsvFilePrinter.print(errorItem.getItemCode());
      tsvFilePrinter.print(errorItem.getItemName());
      tsvFilePrinter.print(errorItem.getLineNumber());

      tsvFilePrinter.println();

      // エラーメッセージ１出力
      tsvFilePrinter.print(CommonConst.VALUE_TYPE_ERROR_2);
      tsvFilePrinter.print(errorItem.getDateTime());
      tsvFilePrinter.print(errorItem.getDataName());
      tsvFilePrinter.print(errorItem.getOfficeCode());
      tsvFilePrinter.print(officeName);
      tsvFilePrinter.print(errorItem.getCustomerCode());
      tsvFilePrinter.print(customerName);
      
      tsvFilePrinter.print(errorItem.getDeadline());
      tsvFilePrinter.print(errorItem.getFlight());
      tsvFilePrinter.print(errorItem.getShopCode());
      tsvFilePrinter.print(errorItem.getShopName());
      tsvFilePrinter.print(errorItem.getItemCode());
      tsvFilePrinter.print(errorItem.getItemName());
      tsvFilePrinter.print(errorItem.getLineNumber());

      // [変数]エラー情報.エラーメッセージ.Length < 51 の場合、
      if (errorItem.getErrorMessage().length() < 51) {
        tsvFilePrinter.print(errorItem.getErrorMessage());
      } else if (errorItem.getErrorMessage().length() > 50) { // [変数]エラー情報.エラーメッセージ.Length
        // > 50 の場合、
        tsvFilePrinter.print(errorItem.getErrorMessage().substring(0, 50));
      }

      tsvFilePrinter.println();

      // [変数]エラー情報.エラーメッセージ.Length > 50 の場合
      if (errorItem.getErrorMessage().length() > 50) {
        tsvFilePrinter.print(CommonConst.VALUE_TYPE_ERROR_3);
        tsvFilePrinter.print(errorItem.getDateTime());
        tsvFilePrinter.print(errorItem.getDataName());
        tsvFilePrinter.print(errorItem.getOfficeCode());
        tsvFilePrinter.print(officeName);
        tsvFilePrinter.print(errorItem.getCustomerCode());
        tsvFilePrinter.print(customerName);
        
        tsvFilePrinter.print(errorItem.getDeadline());
        tsvFilePrinter.print(errorItem.getFlight());
        tsvFilePrinter.print(errorItem.getShopCode());
        tsvFilePrinter.print(errorItem.getShopName());
        tsvFilePrinter.print(errorItem.getItemCode());
        tsvFilePrinter.print(errorItem.getItemName());
        tsvFilePrinter.print(errorItem.getLineNumber());
        
        if (errorItem.getErrorMessage().length() < 101) {
          tsvFilePrinter.print(errorItem.getErrorMessage().substring(50));
        } else if (errorItem.getErrorMessage().length() > 100) {
          tsvFilePrinter.print(errorItem.getErrorMessage().substring(50, 100));
        }
        tsvFilePrinter.println();
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  /**
   * 比較.
   *
   */
  public class SystemErrorInfoComparator implements Comparator<ErrorInfo> {
    
    @Override
    public int compare(ErrorInfo errorInfoA, ErrorInfo errorInfoB) {
      int compareCustCode = 0;
      int compareOfficeCode = 0;
      int result = 0;
      compareCustCode = errorInfoA.getCustomerCode().compareTo(errorInfoB
          .getCustomerCode());
      compareOfficeCode = errorInfoA.getOfficeCode().compareTo(errorInfoB
          .getOfficeCode());
      if (compareCustCode > 0) {
        result = 1;
      } else if (compareCustCode < 0) {
        result = -1;
      } else if (compareOfficeCode > 0) {
        result = 1;
      } else if (compareOfficeCode < 0) {
        result = -1;
      } else {
        result = 0;
      }
      return result;
    }
  }

  /**
   * 【帳票管理マスタ取得】.
   * @return List MstListForm
   */
  private List<MstListForm> listForm() {
    List<MstListForm> mstlstForm = null;
    try {
      MstListFormExample mstLstFormExp = new MstListFormExample();
      mstLstFormExp.createCriteria().andListIdEqualTo("COM01-05L01");
      mstlstForm = commonDao.getMstListFormMapper().selectByExample(
          mstLstFormExp);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return mstlstForm;
  }
  
  /**
   * 【事業所名称取得】.
   * @param officeCode:事業所コード
   * @param businessDate:業務日付
   * @return List MstSJigyo
   */
  private List<MstSJigyo> getOfficeName(String officeCode,
      String businessDate) {
    List<MstSJigyo> lstOfficeName = null;
    try {
      MstSJigyoExample mstSjigyoExp = new MstSJigyoExample();
      mstSjigyoExp.createCriteria().andJgycdEqualTo(officeCode)
          .andDelflgEqualTo(CommonConst.ZERO_VALUE)
          .andStrymdLessThanOrEqualTo(Integer.valueOf(businessDate))
          .andEndymdGreaterThanOrEqualTo(Integer.valueOf(businessDate));
      lstOfficeName = commonDao.getJgyMapper().selectByExample(mstSjigyoExp);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return lstOfficeName;
  }
  
  /**
   * 【事業所一覧取得】.
   * @param businessDate:業務日付
   * @return:List MstSJigyo
   */
  private List<MstSJigyo> getSjgyCd(String businessDate) {
    List<MstSJigyo> lstJgyoCd = new ArrayList<MstSJigyo>();
    try {
      MstSJigyoExample sjgyo = new MstSJigyoExample();
      sjgyo.createCriteria().andDelflgEqualTo(CommonConst.ZERO_VALUE)
          .andStrymdLessThanOrEqualTo(Integer.valueOf(businessDate))
          .andEndymdGreaterThanOrEqualTo(Integer.valueOf(businessDate));
      lstJgyoCd = commonDao.getJgyMapper().selectByExample(sjgyo);
    } catch (MyBatisSystemException e) {
      logger.error(e.getMessage());
      throw e;
    }
    return lstJgyoCd;
  }
}