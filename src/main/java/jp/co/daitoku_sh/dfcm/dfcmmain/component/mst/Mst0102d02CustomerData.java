/**
 * パッケージ名:jp.co.daitoku_sh.dfcm.dfcmmain.component.mst
 * ファイル名:Mst0102d02CustomerData.java
 * 
 * <p>作成者:アクトブレーンベトナム
 * 作成日:2015/11/18
 * 
 * <p>履歴
 * -------------------------------------------------------------------------
 * 日付 指示者 担当 内容
 * -------------------------------------------------------------------------
 * 2015/11/18 1.00 ABV)コアー 新規開発
 * -------------------------------------------------------------------------
 * 
 * <p>ALL RIGHTS RESERVED COPYRIGHT (C)2015 TSUZUKI DENKI CO.,LTD.
 */

package jp.co.daitoku_sh.dfcm.dfcmmain.component.mst;

/**
 * Mst0102d02の得意先データオブジェクト
 * 
 * @author アクトブレーンベトナム コアー
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mst0102d02CustomerData {

  // 得意先コード
  private String custCode;
  // チェーンコード
  private Short cainCode;
  // チェーン枝番
  private Short cainIdx;
  // 得意先フラグ
  private String custFlg;
  // 請求先フラグ
  private String bildFlg;
  // 請求先コード
  private String bildCode;
  // 請求先名称
  private String bildSearchNm;
  // 得意先名称
  private String custNm;
  // 得意先名称カナ
  private String custNmKana;
  // 得意先略称
  private String custNmR;
  // 郵便番号
  private String zipCode;
  // 住所１
  private String adr1;
  // 住所２
  private String adr2;
  // 電話番号
  private String telNo;
  // FAX番号
  private String faxNo;
  // 得意先担当者
  private String custTan;
  // メールアドレス
  private String custTanMail;
  // 幹事事業所
  private String managerJigyoCode;
  // 得意先種別
  private String custCls;
  // 業態区分
  private String gyotaiKb;
  // 納品センター
  private String deliCenterCode;
  // 関係会社種別
  private String relComCls;
  // 補助科目
  private String relComSub;
  // 採番区分
  private String datidxKb;
  // 店舗区分
  private String shopKb;
  // YG取引区分
  private String ygKb;
  // 内税顧客区分
  private String taxIncKb;
  // 内税消費税端数処理
  private String taxIncFrcKb;
  // 集金有無
  private String colMonKb;
  // 現金集金マーク印字
  private String colMmrkKb;
  // 集計出力FLG
  private String sumsFlg;
  // 手入力伝票発行
  private String shipsKb;
  // 手入力出荷伝票ＩＤ
  private String shipsTypId;
  // 手入力出荷伝票種別
  private String shipsTypCls;
  // 伝票行計算金額まるめ
  private String shipsRudKb;
  // 出荷伝票出力品コード
  private String shipsCodeKb;
  // 請求先名称
  private String bildNm;
  // 請求先名称カナ
  private String bildNmKana;
  // 請求先略称
  private String bildNmR;
  // 請求先郵便番号
  private String bildZipCode;
  // 住所１
  private String bildAdr1;
  // 住所２
  private String bildAdr2;
  // 電話番号
  private String bildTelNo;
  // FAX番号
  private String bildFaxNo;
  // 請求単位
  private String bildUntKb;
  // 請求単価
  private String bildTanka;
  // 請求書種類ＩＤ
  private String bildTypId;
  // 請求書種類種別
  private String bildTypCls;
  // 取纏め請求
  private String sumBildKb;
  // 取纏め請求事業所
  private String sumBildJgyo;
  // 請求書パターン
  private String bildPtn;
  // 請求書住所印字
  private String bildAdrOutKb;
  // 請求集計表区分
  private String bildSumKb;
  // 消費税計算単位
  private String taxUntKb;
  // 消費税端数処理
  private String taxFrcKb;
  // 請求チェックリスト 出力対象
  private String bildChkKb;
  // 請求チェックリスト 出力順
  private String bildChkSrt;
  // 締日１
  private Short totalDate1;
  // 回収月区分１
  private String colTermKb1;
  // 回収日１
  private Short colDate1;
  // 締日２
  private Short totalDate2;
  // 回収月区分２
  private String colTermKb2;
  // 回収日２
  private Short colDate2;
  // 入金種別
  private String rcvmCls;
  // 入金口座
  private String rcvmAcc;
  // 手形サイト
  private Short receNoteSite;
  // 受領データ突合区分
  private String rcvDatKb;
  // 請求データ区分
  private String bildDatKb;
  // 修正データ突合区分
  private String modDatKb;
  // （修正種別）返品データ
  private String modKbHpn;
  // （修正種別）欠品データ
  private String modKbKpn;
  // （修正種別）修正データ
  private String modKbSsi;
  // 請求先支払い案内データ区分
  private String payDatKb;
  // 取引コード
  private String trCode;
  // 分類コード（定番、店直）
  private String bnCodeStS;
  // 伝票区分（定番、店直）
  private String dnKbStS;
  // 分類コード（定番、センター）
  private String bnCodeStC;
  // 伝票区分（定番、センター）
  private String dnKbStC;
  // 分類コード（特売、店直）
  private String bnCodeSpS;
  // 伝票区分（特売、店直）
  private String dnKbSpS;
  // 分類コード（特売、センター）
  private String bnCodeSpC;
  // 伝票区分（特売、センター）
  private String dnKbSpC;
  // 請求データ配信ＩＤ
  private String bildDatId;
  // 集計コード１
  private String sumCode1;
  // 集計コード２
  private String sumCode2;
  // 使用中止日
  private String closeDate;
  // 状況コード
  private String stsCode;
  // 送信フラグ
  private String sndFlg;

  public String getCustCode() {
    return custCode;
  }

  public void setCustCode(String custCode) {
    this.custCode = custCode;
  }

  public Short getCainCode() {
    return cainCode;
  }

  public void setCainCode(Short cainCode) {
    this.cainCode = cainCode;
  }

  public Short getCainIdx() {
    return cainIdx;
  }

  public void setCainIdx(Short cainIdx) {
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

  public String getBildSearchNm() {
    return bildSearchNm;
  }

  public void setBildSearchNm(String bildSearchNm) {
    this.bildSearchNm = bildSearchNm;
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

  public String getDatidxKb() {
    return datidxKb;
  }

  public void setDatidxKb(String datidxKb) {
    this.datidxKb = datidxKb;
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

  public Short getTotalDate1() {
    return totalDate1;
  }

  public void setTotalDate1(Short totalDate1) {
    this.totalDate1 = totalDate1;
  }

  public String getColTermKb1() {
    return colTermKb1;
  }

  public void setColTermKb1(String colTermKb1) {
    this.colTermKb1 = colTermKb1;
  }

  public Short getColDate1() {
    return colDate1;
  }

  public void setColDate1(Short colDate1) {
    this.colDate1 = colDate1;
  }

  public Short getTotalDate2() {
    return totalDate2;
  }

  public void setTotalDate2(Short totalDate2) {
    this.totalDate2 = totalDate2;
  }

  public String getColTermKb2() {
    return colTermKb2;
  }

  public void setColTermKb2(String colTermKb2) {
    this.colTermKb2 = colTermKb2;
  }

  public Short getColDate2() {
    return colDate2;
  }

  public void setColDate2(Short colDate2) {
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

  public Short getReceNoteSite() {
    return receNoteSite;
  }

  public void setReceNoteSite(Short receNoteSite) {
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
}