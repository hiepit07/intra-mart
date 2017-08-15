/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:Mst0102d01ExportCsvDataObject.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/10/07
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/10/07 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * Mst0102d01のＣＳＶファイル出力データオブジェクト
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mst0102d01ExportCsvDataObject {

  // 得意先マスタ 得意先コード
  private String custCode;
  
  // 得意先マスタ チェーンコード
  private String cainCode;
  
  // 得意先マスタ チェーン枝番
  private String cainIdx;
  
  // 得意先マスタ 得意先フラグ
  private String custFlg;
  
  // 得意先マスタ 請求先フラグ
  private String bildFlg;
  
  // 得意先マスタ 請求先コード
  private String bildCode;
  
  // 得意先マスタ 得意先名称
  private String custNm;
  
  // 得意先マスタ 得意先名称カナ
  private String custNmKana;
  
  // 得意先マスタ 得意先略称
  private String custNmR;
  
  // 得意先マスタ 郵便番号
  private String zipCode;
  
  // 得意先マスタ 住所１
  private String adr1;
  
  // 得意先マスタ 住所２
  private String adr2;
  
  // 得意先マスタ 電話番号
  private String telNo;
  
  // 得意先マスタ FAX番号
  private String faxNo;
  
  // 得意先マスタ 得意先担当者
  private String custTan;
  
  // 得意先マスタ 得意先担当者メールアドレス
  private String custTanMail;
  
  // 得意先マスタ 幹事事業所コード
  private String managerJigyoCode;
  
  // 得意先マスタ 得意先種別
  private String custCls;
  
  // 得意先マスタ 業態区分
  private String gyotaiKb;
  
  // 得意先マスタ 納品センターコード
  private String deliCenterCode;
  
  // 得意先マスタ 関係会社種別
  private String relComCls;
  
  // 得意先マスタ 関係会社補助科目
  private String relComSub;
  
  // 得意先マスタ 採番区分
  private String datIdxKb;
  
  // 得意先マスタ 店舗区分
  private String shopKb;
  
  // 得意先マスタ YG取引区分
  private String ygKb;
  
  // 得意先マスタ 内税顧客区分
  private String taxIncKb;
  
  // 得意先マスタ 内税消費税端数処理
  private String taxIncFrcKb;
  
  // 得意先マスタ 集金有無
  private String colMonKb;
  
  // 得意先マスタ 現金集金マーク印字
  private String colMmrkKb;
  
  // 得意先マスタ 集計出力FLG
  private String sumsFlg;
  
  // 得意先マスタ 手入力伝票発行
  private String shipsKb;
  
  // 得意先マスタ 手入力出荷伝票帳票ID
  private String shipsTypId;
  
  // 得意先マスタ 手入力出荷伝票種別
  private String shipsTypCls;
  
  // 得意先マスタ 伝票行計算金額まるめ
  private String shipsRudKb;
  
  // 得意先マスタ 出荷伝票出力品コード
  private String shipsCodeKb;
  
  // 得意先マスタ 請求先名称
  private String bildNm;
  
  // 得意先マスタ 請求先名称カナ
  private String bildNmKana;
  
  // 得意先マスタ 請求先略称
  private String bildNmR;
  
  // 得意先マスタ 請求先郵便番号
  private String bildZipCode;
  
  // 得意先マスタ 請求先住所１
  private String bildAdr1;
  
  // 得意先マスタ 請求先住所２
  private String bildAdr2;
  
  // 得意先マスタ 請求先電話番号
  private String bildTelNo;
  
  // 得意先マスタ 請求先FAX番号
  private String bildFaxNo;
  
  // 得意先マスタ 請求単位
  private String bildUntKb;
  
  // 得意先マスタ 請求書単価
  private String bildTanka;
  
  // 得意先マスタ 請求書種類帳票ID
  private String bildTypId;
  
  // 得意先マスタ 請求書種別
  private String bildTypCls;
  
  // 得意先マスタ 取纏め請求
  private String sumBildKb;
  
  // 得意先マスタ 取り纏め請求事業所
  private String sumBildJgyo;
  
  // 得意先マスタ 請求書パターン
  private String bildPtn;
  
  // 得意先マスタ 請求書住所印字
  private String bildAdrOutKb;
  
  // 得意先マスタ 請求集計表区分
  private String bildSumKb;
  
  // 得意先マスタ 消費税計算単位
  private String taxUntKb;
  
  // 得意先マスタ 消費税端数処理
  private String taxFrcKb;
  
  // 得意先マスタ 請求チェックリスト　出力対象
  private String bildChkKb;
  
  // 得意先マスタ 請求チェックリスト　出力順
  private String bildChkSrt;
  
  // 得意先マスタ 締日１
  private String totalDate1;
  
  // 得意先マスタ 回収月区分１
  private String colTermKb1;
  
  // 得意先マスタ 回収日１
  private String colDate1;
  
  // 得意先マスタ 締日２
  private String totalDate2;
  
  // 得意先マスタ 回収月区分２
  private String colTermKb2;
  
  // 得意先マスタ 回収日２
  private String colDate2;
  
  // 得意先マスタ 入金種別
  private String rcvmCls;
  
  // 得意先マスタ 入金口座
  private String rcvmAcc;
  
  // 得意先マスタ 手形サイト
  private String receNoteSite;
  
  // 得意先マスタ 受領データ突合区分
  private String rcvDatKb;
  
  // 得意先マスタ 請求データ区分
  private String bildDatKb;
  
  // 得意先マスタ 修正データ突合区分
  private String modDatKb;
  
  // 得意先マスタ 修正データ種別_返品
  private String modKbHpn;
  
  // 得意先マスタ 修正データ種別_欠品
  private String modKbKpn;
  
  // 得意先マスタ 修正データ種別_修正
  private String modKbSsi;
  
  // 得意先マスタ 請求先支払データ区分
  private String payDatKb;
  
  // 得意先マスタ 取引コード
  private String trCode;
  
  // 得意先マスタ 分類コード（定番、店直）
  private String bnCodeStS;
  
  // 得意先マスタ 伝票区分（定番、店直）
  private String dnKbStS;
  
  // 得意先マスタ 分類コード（定番、センター）
  private String bnCodeStC;
  
  // 得意先マスタ 伝票区分（定番、センター）
  private String dnKbStC;
  
  // 得意先マスタ 分類コード（特売、店直）
  private String bnCodeSpS;
  
  // 得意先マスタ 伝票区分（特売、店直）
  private String dnKbSpS;
  
  // 得意先マスタ 分類コード（特売、センター）
  private String bnCodeSpC;
  
  // 得意先マスタ 伝票区分（特売、センター）
  private String dnKbSpC;
  
  // 得意先マスタ 請求データ配信ＩＤ
  private String bildDatId;
  
  // 得意先マスタ 集計コード１
  private String sumCode1;
  
  // 得意先マスタ 集計コード２
  private String sumCode2;
  
  // 得意先マスタ 使用中止日
  private String closeDate;
  
  // 得意先マスタ 状況コード
  private String stsCode;
  
  // 得意先マスタ 送信済みフラグ
  private String sndFlg;

  // 得意先事業所マスタ 得意先コード
  private String custJigyoCustCode;
  
  // 得意先事業所マスタ 事業所コード
  private String custJigyoJigyoCode;
    
  // 得意先事業所マスタ 営業担当者コード
  private String custJigyoEgtCode;
  
  // 得意先事業所マスタ 事務担当者コード
  private String custJigyoJmtTanCode;

  // チェーンマスタ チェーン名
  private String schainChnMei;

  // 事業所マスタ 事業所名
  private String sjigyoJgyMei;

  // 担当者マスタ1 営業担当者氏名
  private String user1UserNm;

  // 担当者マスタ2 事務担当者氏名
  private String user2UserNm;

  // 汎用マスタ 得意先種別名称
  private String genCustCls;

  // 汎用マスタ 業態区分名称
  private String genGyotaiKb;

  // 汎用マスタ 関係会社種別名称
  private String genRelComCls;

  // 汎用マスタ 採番区分名称
  private String genDatIdxKb;  

  // 汎用マスタ 店舗区分名称
  private String genShopKb; 

  // 汎用マスタ YG取引区分名称
  private String genYgKb;

  // 汎用マスタ 内税顧客区分名称
  private String genTaxIncKb;

  // 汎用マスタ 内税消費税端数処理名称
  private String genTaxIncFrcKb;

  // 汎用マスタ 集金有無名称
  private String genColMonKb;

  // 汎用マスタ 現金集金マーク印字名称
  private String genColMmrkKb;

  // 汎用マスタ 集計出力FLG名称
  private String genSumsFlg;

  // 汎用マスタ 手入力伝票発行名称
  private String genShipsKb;

  // 汎用マスタ 伝票行計算金額まるめ
  private String genShipsRudKb;

  // 汎用マスタ 出荷伝票出力品コード
  private String genShipsCodeKb;

  // 汎用マスタ 請求単位
  private String genBildUntKb;

  // 汎用マスタ 請求書単価
  private String genBildTanka;
  
  // 汎用マスタ請求書種類
  private String genBildTyp;

  // 汎用マスタ 取締め請求
  private String genSumBildKb;

  // 汎用マスタ 請求書パターン
  private String genBildPtn;

  // 汎用マスタ 請求書住所印字
  private String genBildAdrOutKb;

  // 汎用マスタ 消費税計算単位
  private String genTaxUntKb;

  // 汎用マスタ 消費税端数処理
  private String genTaxFrcKb;

  // 汎用マスタ 請求チェックリスト_出力対象
  private String genBildChkKb;

  // 汎用マスタ 請求チェックリスト_出力順
  private String genBildChkSrt;

  // 汎用マスタ 回収月区分１
  private String genColTermKb1;

  // 汎用マスタ 回収月区分２
  private String genColTermKb2;

  // 汎用マスタ 入金種別
  private String genRcvmCls;

  // 汎用マスタ 入金口座
  private String genRcvmAcc;

  // 汎用マスタ 受領データ突合区分
  private String genRcvDatKb;

  // 汎用マスタ 請求データ区分
  private String genBildDatKb;

  // 汎用マスタ 修正データ突合区分
  private String genModDatKb;

  // 汎用マスタ 請求先支払データ区分
  private String genPayDatKb;

  
  public String getCustCode() {
    return custCode;
  }

  
  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }

  
  public String getCainCode() {
    return cainCode;
  }

  
  public void setCainCode(String cainCode) {
    this.cainCode = cainCode;
  }

  
  public String getCainIdx() {
    return cainIdx;
  }

  
  public void setCainIdx(String cainIdx) {
    this.cainIdx = cainIdx;
  }

  
  public String getCustFlg() {
    return custFlg;
  }

  
  public void setCustFlg(String custFlg) {
    this.custFlg = custFlg;
  }

  
  public String getBildFlg() {
    return bildFlg;
  }

  
  public void setBildFlg(String bildFlg) {
    this.bildFlg = bildFlg;
  }

  
  public String getBildCode() {
    return bildCode;
  }

  
  public void setBildCode(String bildCode) {
    this.bildCode = bildCode;
  }

  
  public String getCustNm() {
    return custNm;
  }

  
  public void setCustNm(String custNm) {
    this.custNm = custNm;
  }

  
  public String getCustNmKana() {
    return custNmKana;
  }

  
  public void setCustNmKana(String custNmKana) {
    this.custNmKana = custNmKana;
  }

  
  public String getCustNmR() {
    return custNmR;
  }

  
  public void setCustNmR(String custNmR) {
    this.custNmR = custNmR;
  }

  
  public String getZipCode() {
    return zipCode;
  }

  
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  
  public String getAdr1() {
    return adr1;
  }

  
  public void setAdr1(String adr1) {
    this.adr1 = adr1;
  }

  
  public String getAdr2() {
    return adr2;
  }

  
  public void setAdr2(String adr2) {
    this.adr2 = adr2;
  }

  
  public String getTelNo() {
    return telNo;
  }

  
  public void setTelNo(String telNo) {
    this.telNo = telNo;
  }

  
  public String getFaxNo() {
    return faxNo;
  }

  
  public void setFaxNo(String faxNo) {
    this.faxNo = faxNo;
  }

  
  public String getCustTan() {
    return custTan;
  }

  
  public void setCustTan(String custTan) {
    this.custTan = custTan;
  }

  
  public String getCustTanMail() {
    return custTanMail;
  }

  
  public void setCustTanMail(String custTanMail) {
    this.custTanMail = custTanMail;
  }

  
  public String getManagerJigyoCode() {
    return managerJigyoCode;
  }

  
  public void setManagerJigyoCode(String managerJigyoCode) {
    this.managerJigyoCode = managerJigyoCode;
  }

  
  public String getCustCls() {
    return custCls;
  }

  
  public void setCustCls(String custCls) {
    this.custCls = custCls;
  }

  
  public String getGyotaiKb() {
    return gyotaiKb;
  }

  
  public void setGyotaiKb(String gyotaiKb) {
    this.gyotaiKb = gyotaiKb;
  }

  
  public String getDeliCenterCode() {
    return deliCenterCode;
  }

  
  public void setDeliCenterCode(String deliCenterCode) {
    this.deliCenterCode = deliCenterCode;
  }

  
  public String getRelComCls() {
    return relComCls;
  }

  
  public void setRelComCls(String relComCls) {
    this.relComCls = relComCls;
  }

  
  public String getRelComSub() {
    return relComSub;
  }

  
  public void setRelComSub(String relComSub) {
    this.relComSub = relComSub;
  }

  
  public String getDatIdxKb() {
    return datIdxKb;
  }

  
  public void setDatIdxKb(String datIdxKb) {
    this.datIdxKb = datIdxKb;
  }

  
  public String getShopKb() {
    return shopKb;
  }

  
  public void setShopKb(String shopKb) {
    this.shopKb = shopKb;
  }

  
  public String getYgKb() {
    return ygKb;
  }

  
  public void setYgKb(String ygKb) {
    this.ygKb = ygKb;
  }

  
  public String getTaxIncKb() {
    return taxIncKb;
  }

  
  public void setTaxIncKb(String taxIncKb) {
    this.taxIncKb = taxIncKb;
  }

  
  public String getTaxIncFrcKb() {
    return taxIncFrcKb;
  }

  
  public void setTaxIncFrcKb(String taxIncFrcKb) {
    this.taxIncFrcKb = taxIncFrcKb;
  }

  
  public String getColMonKb() {
    return colMonKb;
  }

  
  public void setColMonKb(String colMonKb) {
    this.colMonKb = colMonKb;
  }

  
  public String getColMmrkKb() {
    return colMmrkKb;
  }

  
  public void setColMmrkKb(String colMmrkKb) {
    this.colMmrkKb = colMmrkKb;
  }

  
  public String getSumsFlg() {
    return sumsFlg;
  }

  
  public void setSumsFlg(String sumsFlg) {
    this.sumsFlg = sumsFlg;
  }

  
  public String getShipsKb() {
    return shipsKb;
  }

  
  public void setShipsKb(String shipsKb) {
    this.shipsKb = shipsKb;
  }

  
  public String getShipsTypId() {
    return shipsTypId;
  }

  
  public void setShipsTypId(String shipsTypId) {
    this.shipsTypId = shipsTypId;
  }

  
  public String getShipsTypCls() {
    return shipsTypCls;
  }

  
  public void setShipsTypCls(String shipsTypCls) {
    this.shipsTypCls = shipsTypCls;
  }

  
  public String getShipsRudKb() {
    return shipsRudKb;
  }

  
  public void setShipsRudKb(String shipsRudKb) {
    this.shipsRudKb = shipsRudKb;
  }

  
  public String getShipsCodeKb() {
    return shipsCodeKb;
  }

  
  public void setShipsCodeKb(String shipsCodeKb) {
    this.shipsCodeKb = shipsCodeKb;
  }

  
  public String getBildNm() {
    return bildNm;
  }

  
  public void setBildNm(String bildNm) {
    this.bildNm = bildNm;
  }

  
  public String getBildNmKana() {
    return bildNmKana;
  }

  
  public void setBildNmKana(String bildNmKana) {
    this.bildNmKana = bildNmKana;
  }

  
  public String getBildNmR() {
    return bildNmR;
  }

  
  public void setBildNmR(String bildNmR) {
    this.bildNmR = bildNmR;
  }

  
  public String getBildZipCode() {
    return bildZipCode;
  }

  
  public void setBildZipCode(String bildZipCode) {
    this.bildZipCode = bildZipCode;
  }

  
  public String getBildAdr1() {
    return bildAdr1;
  }

  
  public void setBildAdr1(String bildAdr1) {
    this.bildAdr1 = bildAdr1;
  }

  
  public String getBildAdr2() {
    return bildAdr2;
  }

  
  public void setBildAdr2(String bildAdr2) {
    this.bildAdr2 = bildAdr2;
  }

  
  public String getBildTelNo() {
    return bildTelNo;
  }

  
  public void setBildTelNo(String bildTelNo) {
    this.bildTelNo = bildTelNo;
  }

  
  public String getBildFaxNo() {
    return bildFaxNo;
  }

  
  public void setBildFaxNo(String bildFaxNo) {
    this.bildFaxNo = bildFaxNo;
  }

  
  public String getBildUntKb() {
    return bildUntKb;
  }

  
  public void setBildUntKb(String bildUntKb) {
    this.bildUntKb = bildUntKb;
  }

  
  public String getBildTanka() {
    return bildTanka;
  }

  
  public void setBildTanka(String bildTanka) {
    this.bildTanka = bildTanka;
  }

  
  public String getBildTypId() {
    return bildTypId;
  }

  
  public void setBildTypId(String bildTypId) {
    this.bildTypId = bildTypId;
  }

  
  public String getBildTypCls() {
    return bildTypCls;
  }

  
  public void setBildTypCls(String bildTypCls) {
    this.bildTypCls = bildTypCls;
  }

  
  public String getSumBildKb() {
    return sumBildKb;
  }

  
  public void setSumBildKb(String sumBildKb) {
    this.sumBildKb = sumBildKb;
  }

  
  public String getSumBildJgyo() {
    return sumBildJgyo;
  }

  
  public void setSumBildJgyo(String sumBildJgyo) {
    this.sumBildJgyo = sumBildJgyo;
  }

  
  public String getBildPtn() {
    return bildPtn;
  }

  
  public void setBildPtn(String bildPtn) {
    this.bildPtn = bildPtn;
  }

  
  public String getBildAdrOutKb() {
    return bildAdrOutKb;
  }

  
  public void setBildAdrOutKb(String bildAdrOutKb) {
    this.bildAdrOutKb = bildAdrOutKb;
  }

  
  public String getBildSumKb() {
    return bildSumKb;
  }

  
  public void setBildSumKb(String bildSumKb) {
    this.bildSumKb = bildSumKb;
  }

  
  public String getTaxUntKb() {
    return taxUntKb;
  }

  
  public void setTaxUntKb(String taxUntKb) {
    this.taxUntKb = taxUntKb;
  }

  
  public String getTaxFrcKb() {
    return taxFrcKb;
  }

  
  public void setTaxFrcKb(String taxFrcKb) {
    this.taxFrcKb = taxFrcKb;
  }

  
  public String getBildChkKb() {
    return bildChkKb;
  }

  
  public void setBildChkKb(String bildChkKb) {
    this.bildChkKb = bildChkKb;
  }

  
  public String getBildChkSrt() {
    return bildChkSrt;
  }

  
  public void setBildChkSrt(String bildChkSrt) {
    this.bildChkSrt = bildChkSrt;
  }

  
  public String getTotalDate1() {
    return totalDate1;
  }

  
  public void setTotalDate1(String totalDate1) {
    this.totalDate1 = totalDate1;
  }

  
  public String getColTermKb1() {
    return colTermKb1;
  }

  
  public void setColTermKb1(String colTermKb1) {
    this.colTermKb1 = colTermKb1;
  }

  
  public String getColDate1() {
    return colDate1;
  }

  
  public void setColDate1(String colDate1) {
    this.colDate1 = colDate1;
  }

  
  public String getTotalDate2() {
    return totalDate2;
  }

  
  public void setTotalDate2(String totalDate2) {
    this.totalDate2 = totalDate2;
  }

  
  public String getColTermKb2() {
    return colTermKb2;
  }

  
  public void setColTermKb2(String colTermKb2) {
    this.colTermKb2 = colTermKb2;
  }

  
  public String getColDate2() {
    return colDate2;
  }

  
  public void setColDate2(String colDate2) {
    this.colDate2 = colDate2;
  }

  
  public String getRcvmCls() {
    return rcvmCls;
  }

  
  public void setRcvmCls(String rcvmCls) {
    this.rcvmCls = rcvmCls;
  }

  
  public String getRcvmAcc() {
    return rcvmAcc;
  }

  
  public void setRcvmAcc(String rcvmAcc) {
    this.rcvmAcc = rcvmAcc;
  }

  
  public String getReceNoteSite() {
    return receNoteSite;
  }

  
  public void setReceNoteSite(String receNoteSite) {
    this.receNoteSite = receNoteSite;
  }

  
  public String getRcvDatKb() {
    return rcvDatKb;
  }

  
  public void setRcvDatKb(String rcvDatKb) {
    this.rcvDatKb = rcvDatKb;
  }

  
  public String getBildDatKb() {
    return bildDatKb;
  }

  
  public void setBildDatKb(String bildDatKb) {
    this.bildDatKb = bildDatKb;
  }

  
  public String getModDatKb() {
    return modDatKb;
  }

  
  public void setModDatKb(String modDatKb) {
    this.modDatKb = modDatKb;
  }

  
  public String getModKbHpn() {
    return modKbHpn;
  }

  
  public void setModKbHpn(String modKbHpn) {
    this.modKbHpn = modKbHpn;
  }

  
  public String getModKbKpn() {
    return modKbKpn;
  }

  
  public void setModKbKpn(String modKbKpn) {
    this.modKbKpn = modKbKpn;
  }

  
  public String getModKbSsi() {
    return modKbSsi;
  }

  
  public void setModKbSsi(String modKbSsi) {
    this.modKbSsi = modKbSsi;
  }

  
  public String getPayDatKb() {
    return payDatKb;
  }

  
  public void setPayDatKb(String payDatKb) {
    this.payDatKb = payDatKb;
  }

  
  public String getTrCode() {
    return trCode;
  }

  
  public void setTrCode(String trCode) {
    this.trCode = trCode;
  }

  
  public String getBnCodeStS() {
    return bnCodeStS;
  }

  
  public void setBnCodeStS(String bnCodeStS) {
    this.bnCodeStS = bnCodeStS;
  }

  
  public String getDnKbStS() {
    return dnKbStS;
  }

  
  public void setDnKbStS(String dnKbStS) {
    this.dnKbStS = dnKbStS;
  }

  
  public String getBnCodeStC() {
    return bnCodeStC;
  }

  
  public void setBnCodeStC(String bnCodeStC) {
    this.bnCodeStC = bnCodeStC;
  }

  
  public String getDnKbStC() {
    return dnKbStC;
  }

  
  public void setDnKbStC(String dnKbStC) {
    this.dnKbStC = dnKbStC;
  }

  
  public String getBnCodeSpS() {
    return bnCodeSpS;
  }

  
  public void setBnCodeSpS(String bnCodeSpS) {
    this.bnCodeSpS = bnCodeSpS;
  }

  
  public String getDnKbSpS() {
    return dnKbSpS;
  }

  
  public void setDnKbSpS(String dnKbSpS) {
    this.dnKbSpS = dnKbSpS;
  }

  
  public String getBnCodeSpC() {
    return bnCodeSpC;
  }

  
  public void setBnCodeSpC(String bnCodeSpC) {
    this.bnCodeSpC = bnCodeSpC;
  }

  
  public String getDnKbSpC() {
    return dnKbSpC;
  }

  
  public void setDnKbSpC(String dnKbSpC) {
    this.dnKbSpC = dnKbSpC;
  }

  
  public String getBildDatId() {
    return bildDatId;
  }

  
  public void setBildDatId(String bildDatId) {
    this.bildDatId = bildDatId;
  }

  
  public String getSumCode1() {
    return sumCode1;
  }

  
  public void setSumCode1(String sumCode1) {
    this.sumCode1 = sumCode1;
  }

  
  public String getSumCode2() {
    return sumCode2;
  }

  
  public void setSumCode2(String sumCode2) {
    this.sumCode2 = sumCode2;
  }

  
  public String getCloseDate() {
    return closeDate;
  }

  
  public void setCloseDate(String closeDate) {
    this.closeDate = closeDate;
  }

  
  public String getStsCode() {
    return stsCode;
  }

  
  public void setStsCode(String stsCode) {
    this.stsCode = stsCode;
  }

  
  public String getSndFlg() {
    return sndFlg;
  }

  
  public void setSndFlg(String sndFlg) {
    this.sndFlg = sndFlg;
  }

  
  public String getCustJigyoCustCode() {
    return custJigyoCustCode;
  }

  
  public void setCustJigyoCustCode(String custJigyoCustCode) {
    this.custJigyoCustCode = custJigyoCustCode;
  }

  
  public String getCustJigyoJigyoCode() {
    return custJigyoJigyoCode;
  }

  
  public void setCustJigyoJigyoCode(String custJigyoJigyoCode) {
    this.custJigyoJigyoCode = custJigyoJigyoCode;
  }

  
  public String getCustJigyoEgtCode() {
    return custJigyoEgtCode;
  }

  
  public void setCustJigyoEgtCode(String custJigyoEgtCode) {
    this.custJigyoEgtCode = custJigyoEgtCode;
  }

  
  public String getCustJigyoJmtTanCode() {
    return custJigyoJmtTanCode;
  }

  
  public void setCustJigyoJmtTanCode(String custJigyoJmtTanCode) {
    this.custJigyoJmtTanCode = custJigyoJmtTanCode;
  }

  
  public String getSchainChnMei() {
    return schainChnMei;
  }

  
  public void setSchainChnMei(String schainChnMei) {
    this.schainChnMei = schainChnMei;
  }

  
  public String getSjigyoJgyMei() {
    return sjigyoJgyMei;
  }

  
  public void setSjigyoJgyMei(String sjigyoJgyMei) {
    this.sjigyoJgyMei = sjigyoJgyMei;
  }

  
  public String getUser1UserNm() {
    return user1UserNm;
  }

  
  public void setUser1UserNm(String user1UserNm) {
    this.user1UserNm = user1UserNm;
  }

  
  public String getUser2UserNm() {
    return user2UserNm;
  }

  
  public void setUser2UserNm(String user2UserNm) {
    this.user2UserNm = user2UserNm;
  }

  
  public String getGenCustCls() {
    return genCustCls;
  }

  
  public void setGenCustCls(String genCustCls) {
    this.genCustCls = genCustCls;
  }

  
  public String getGenGyotaiKb() {
    return genGyotaiKb;
  }

  
  public void setGenGyotaiKb(String genGyotaiKb) {
    this.genGyotaiKb = genGyotaiKb;
  }

  
  public String getGenRelComCls() {
    return genRelComCls;
  }

  
  public void setGenRelComCls(String genRelComCls) {
    this.genRelComCls = genRelComCls;
  }

  
  public String getGenDatIdxKb() {
    return genDatIdxKb;
  }

  
  public void setGenDatIdxKb(String genDatIdxKb) {
    this.genDatIdxKb = genDatIdxKb;
  }

  
  public String getGenShopKb() {
    return genShopKb;
  }

  
  public void setGenShopKb(String genShopKb) {
    this.genShopKb = genShopKb;
  }

  
  public String getGenYgKb() {
    return genYgKb;
  }

  
  public void setGenYgKb(String genYgKb) {
    this.genYgKb = genYgKb;
  }

  
  public String getGenTaxIncKb() {
    return genTaxIncKb;
  }

  
  public void setGenTaxIncKb(String genTaxIncKb) {
    this.genTaxIncKb = genTaxIncKb;
  }

  
  public String getGenTaxIncFrcKb() {
    return genTaxIncFrcKb;
  }

  
  public void setGenTaxIncFrcKb(String genTaxIncFrcKb) {
    this.genTaxIncFrcKb = genTaxIncFrcKb;
  }

  
  public String getGenColMonKb() {
    return genColMonKb;
  }

  
  public void setGenColMonKb(String genColMonKb) {
    this.genColMonKb = genColMonKb;
  }

  
  public String getGenColMmrkKb() {
    return genColMmrkKb;
  }

  
  public void setGenColMmrkKb(String genColMmrkKb) {
    this.genColMmrkKb = genColMmrkKb;
  }

  
  public String getGenSumsFlg() {
    return genSumsFlg;
  }

  
  public void setGenSumsFlg(String genSumsFlg) {
    this.genSumsFlg = genSumsFlg;
  }

  
  public String getGenShipsKb() {
    return genShipsKb;
  }

  
  public void setGenShipsKb(String genShipsKb) {
    this.genShipsKb = genShipsKb;
  }

  
  public String getGenShipsRudKb() {
    return genShipsRudKb;
  }

  
  public void setGenShipsRudKb(String genShipsRudKb) {
    this.genShipsRudKb = genShipsRudKb;
  }

  
  public String getGenShipsCodeKb() {
    return genShipsCodeKb;
  }

  
  public void setGenShipsCodeKb(String genShipsCodeKb) {
    this.genShipsCodeKb = genShipsCodeKb;
  }

  
  public String getGenBildUntKb() {
    return genBildUntKb;
  }

  
  public void setGenBildUntKb(String genBildUntKb) {
    this.genBildUntKb = genBildUntKb;
  }

  
  public String getGenBildTanka() {
    return genBildTanka;
  }

  
  public void setGenBildTanka(String genBildTanka) {
    this.genBildTanka = genBildTanka;
  }

  
  public String getGenBildTyp() {
    return genBildTyp;
  }

  
  public void setGenBildTyp(String genBildTyp) {
    this.genBildTyp = genBildTyp;
  }

  
  public String getGenSumBildKb() {
    return genSumBildKb;
  }

  
  public void setGenSumBildKb(String genSumBildKb) {
    this.genSumBildKb = genSumBildKb;
  }

  
  public String getGenBildPtn() {
    return genBildPtn;
  }

  
  public void setGenBildPtn(String genBildPtn) {
    this.genBildPtn = genBildPtn;
  }

  
  public String getGenBildAdrOutKb() {
    return genBildAdrOutKb;
  }

  
  public void setGenBildAdrOutKb(String genBildAdrOutKb) {
    this.genBildAdrOutKb = genBildAdrOutKb;
  }

  
  public String getGenTaxUntKb() {
    return genTaxUntKb;
  }

  
  public void setGenTaxUntKb(String genTaxUntKb) {
    this.genTaxUntKb = genTaxUntKb;
  }

  
  public String getGenTaxFrcKb() {
    return genTaxFrcKb;
  }

  
  public void setGenTaxFrcKb(String genTaxFrcKb) {
    this.genTaxFrcKb = genTaxFrcKb;
  }

  
  public String getGenBildChkKb() {
    return genBildChkKb;
  }

  
  public void setGenBildChkKb(String genBildChkKb) {
    this.genBildChkKb = genBildChkKb;
  }

  
  public String getGenBildChkSrt() {
    return genBildChkSrt;
  }

  
  public void setGenBildChkSrt(String genBildChkSrt) {
    this.genBildChkSrt = genBildChkSrt;
  }

  
  public String getGenColTermKb1() {
    return genColTermKb1;
  }

  
  public void setGenColTermKb1(String genColTermKb1) {
    this.genColTermKb1 = genColTermKb1;
  }

  
  public String getGenColTermKb2() {
    return genColTermKb2;
  }

  
  public void setGenColTermKb2(String genColTermKb2) {
    this.genColTermKb2 = genColTermKb2;
  }

  
  public String getGenRcvmCls() {
    return genRcvmCls;
  }

  
  public void setGenRcvmCls(String genRcvmCls) {
    this.genRcvmCls = genRcvmCls;
  }

  
  public String getGenRcvmAcc() {
    return genRcvmAcc;
  }

  
  public void setGenRcvmAcc(String genRcvmAcc) {
    this.genRcvmAcc = genRcvmAcc;
  }

  
  public String getGenRcvDatKb() {
    return genRcvDatKb;
  }

  
  public void setGenRcvDatKb(String genRcvDatKb) {
    this.genRcvDatKb = genRcvDatKb;
  }

  
  public String getGenBildDatKb() {
    return genBildDatKb;
  }

  
  public void setGenBildDatKb(String genBildDatKb) {
    this.genBildDatKb = genBildDatKb;
  }

  
  public String getGenModDatKb() {
    return genModDatKb;
  }

  
  public void setGenModDatKb(String genModDatKb) {
    this.genModDatKb = genModDatKb;
  }

  
  public String getGenPayDatKb() {
    return genPayDatKb;
  }

  
  public void setGenPayDatKb(String genPayDatKb) {
    this.genPayDatKb = genPayDatKb;
  }

}