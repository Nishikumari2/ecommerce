package com.insilicoss.bizNty;

import com.insilicoss.App;
import com.insilicoss.database.DBManager;
import com.insilicoss.eventManager.CoreRequest;
import com.insilicoss.eventManager.CoreResponse;
import com.insilicoss.eventManager.cache.ReadOnlyCacheManager;
import com.insilicoss.exception.PresentableException;
import com.insilicoss.messaging.CoreMessage;
import com.insilicoss.sparc.SparcCnstntDbVar;
import com.insilicoss.sparc.cache.HldyGrpReadOnly;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
// *
 * author Alaknanda
 */
public class BizNtyWebControler {
  private CoreRequest cvoCoreRequest;
  private CoreResponse cvoCoreResponse;
  private  DBManager cvoDbManager;
  private Logger cvoLogger; 

  public BizNtyWebControler(CoreRequest pvoCoreRequest, CoreResponse pvoCoreResponse) {
    cvoCoreRequest = pvoCoreRequest;
    cvoCoreResponse = pvoCoreResponse;
    cvoDbManager = cvoCoreRequest.getDBManager();
    cvoLogger = LogManager.getLogger(); 
    cvoDbManager.addContextParamFromClass(SparcCnstntDbVar.class);
  }

    public void loadBizNty() {
        ResultSet lvoBizNty = cvoDbManager.selectResultSet("BizNtty\\sarBizNty&BizNtyLoc");
        cvoCoreResponse.setVal("svoBizNty", lvoBizNty);
        cvoCoreResponse.setVal("svoDsHldyGrp", lvoBizNty);
    }
    
    public void getFullNtyDts() throws SQLException{
      DBManager lvoDbManager = cvoCoreRequest.getDBManager();
      int lviBizNtyD = cvoCoreRequest.getIntVal("sviBizNtyD");
      lvoDbManager.addContextParam("rviBizNtyD",lviBizNtyD);
      ResultSet lvoFullNty = lvoDbManager.selectResultSet("BizNtty\\sarFullBizNty");
      if(lvoFullNty.next())
      {
        cvoCoreResponse.setVal("svsBizNtyIdy",lvoFullNty.getString("svsBizNtyIdy"));
        cvoCoreResponse.setVal("svsBizNtyName",lvoFullNty.getString("svsBizNtyName"));
        cvoCoreResponse.setVal("svsLegalIdy",lvoFullNty.getString("svsLegalIdy"));
        cvoCoreResponse.setVal("svsNcomTaxIdy",lvoFullNty.getString("svsNcomTaxIdy"));
        cvoCoreResponse.setVal("svsBizNtyNcomTaxWthdgIdy",lvoFullNty.getString("svsBizNtyNcomTaxWthdgIdy"));
        cvoCoreResponse.setVal("svsBizNtyMSMEIdy",lvoFullNty.getString("svsBizNtyMSMEIdy"));
        cvoCoreResponse.setVal("svsIsBizOnr",lvoFullNty.getString("svsIsBizOnr"));
        cvoCoreResponse.setVal("svsIsCstmr",lvoFullNty.getString("svsIsCstmr"));
        cvoCoreResponse.setVal("svsIsVndr",lvoFullNty.getString("svsIsVndr"));
        cvoCoreResponse.setVal("svdBizNtyFrom",lvoFullNty.getDate("svdBizNtyFrom").toLocalDate().format(App.DATE_FORMATTER));
        cvoCoreResponse.setVal("svdBizNtyTo",lvoFullNty.getDate("svdBizNtyTo").toLocalDate().format(App.DATE_FORMATTER));
      }
      else{
            throw new PresentableException("Invalid BizNty reference.");

      }
    }
    
    /*public void getCntry(){
       DBManager lvoDbManager = cvoCoreRequest.getDBManager();
        cvoCoreResponse.setVal("svsLocCntry","India");
   }*/
    
    public void getFullNtyLocDtls() throws SQLException {
        DBManager lvoDbManager = cvoCoreRequest.getDBManager();
        int lviBizNtyLocD = cvoCoreRequest.getIntVal("sviBizNtyLocD");
        lvoDbManager.addContextParam("rviBizNtyLocD", lviBizNtyLocD);
        lvoDbManager.addContextParam("rviBizNtyLocG",1);
        ResultSet lvoFullBizNtyBizNtyLoc = lvoDbManager.selectResultSet("BizNtty\\sarFullBizNty&BizNtyLoc");
        if (lvoFullBizNtyBizNtyLoc.next()) {
            cvoCoreResponse.setVal("svsBizNtyLocIdy", lvoFullBizNtyBizNtyLoc.getString("svsBizNtyLocIdy"));
            cvoCoreResponse.setVal("svsBizNtyLocName", lvoFullBizNtyBizNtyLoc.getString("svsBizNtyLocName"));
            cvoCoreResponse.setVal("svdBizNtyLocFrom", lvoFullBizNtyBizNtyLoc.getString("svdBizNtyLocFrom"));
            cvoCoreResponse.setVal("svdBizNtyLocTo", lvoFullBizNtyBizNtyLoc.getString("svdBizNtyLocTo"));
            cvoCoreResponse.setVal("svsBizNtyLocAdrsLine1", lvoFullBizNtyBizNtyLoc.getString("svsBizNtyLocAdrsLine1"));
            cvoCoreResponse.setVal("svsBizNtyLocAdrsLine2", lvoFullBizNtyBizNtyLoc.getString("svsBizNtyLocAdrsLine2"));
            cvoCoreResponse.setVal("svsBizNtyLocPostCode", lvoFullBizNtyBizNtyLoc.getString("svsBizNtyLocPostCode"));
            cvoCoreResponse.setVal("svdBizNtyLocFrom", lvoFullBizNtyBizNtyLoc.getDate("svdBizNtyLocFrom").toLocalDate().format(App.DATE_FORMATTER));
            cvoCoreResponse.setVal("svdBizNtyLocTo", lvoFullBizNtyBizNtyLoc.getDate("svdBizNtyLocTo").toLocalDate().format(App.DATE_FORMATTER));
            cvoCoreResponse.setVal("svsBizNtyLocIsSez", lvoFullBizNtyBizNtyLoc.getString("svsBizNtyLocIsSez"));
            cvoCoreResponse.setVal("sviBizNtyLocCrdtPrid", lvoFullBizNtyBizNtyLoc.getString("sviBizNtyLocCrdtPrid"));
            cvoCoreResponse.setVal("svsBizNtyLocPtAthry", lvoFullBizNtyBizNtyLoc.getString("svsBizNtyLocPtAthry"));
            cvoCoreResponse.setVal("svsBizNtyLocIsMtrReg", lvoFullBizNtyBizNtyLoc.getString("svsBizNtyLocIsMtrReg"));
            cvoCoreResponse.setVal("svsBizNtySysHostLocCanSell", lvoFullBizNtyBizNtyLoc.getString("svsBizNtySysHostLocCanSell"));
            cvoCoreResponse.setVal("svsBizNtySysHostLocCanPchs", lvoFullBizNtyBizNtyLoc.getString("svsBizNtySysHostLocCanPchs"));
            cvoCoreResponse.setVal("svsHldyGrpDesc", lvoFullBizNtyBizNtyLoc.getString("svsHldyGrpDesc"));
            cvoCoreResponse.setVal("svsLocState", lvoFullBizNtyBizNtyLoc.getString("svsLocState"));
            cvoCoreResponse.setVal("svsLocCntry", lvoFullBizNtyBizNtyLoc.getString("svsLocCntry"));

        } else {
            throw new PresentableException("Invalid BizNty reference.");
        }
    }
    
    public void saveBizNtyAll() throws SQLException{
      BizNtyModel bizNtyModel = BizNtyModel.load(cvoCoreRequest.getIntVal("sviBizNtyD",-1), cvoDbManager);
      bizNtyModel.setBizNtyIdy(cvoCoreRequest.getVal("svsBizNtyIdy"));
      bizNtyModel.setBizNtyFrom(cvoCoreRequest.getDateVal("svdBizNtyFrom", App.DEFAULT_MIN_DATE));
      bizNtyModel.setBizNtyTo(cvoCoreRequest.getDateVal("svdBizNtyTo", App.DEFAULT_MAX_DATE));
      bizNtyModel.setBizNtyName(cvoCoreRequest.getVal("svsBizNtyName"));
      bizNtyModel.setBizNtyNcomTaxIdy(cvoCoreRequest.getVal("svsNcomTaxIdy"));
      bizNtyModel.setBizNtyNcomTaxWthdgIdy(cvoCoreRequest.getVal("svsBizNtyNcomTaxWthdgIdy"));
      bizNtyModel.setBizNtyMSMEIdy(cvoCoreRequest.getVal("svsBizNtyMSMEIdy"));
      bizNtyModel.setBizNtyLegalIdy(cvoCoreRequest.getVal("svsLegalIdy"));
      String lvsIsBizOnr = cvoCoreRequest.getVal("svsIsBizOnr"); 
      if(lvsIsBizOnr == null || lvsIsBizOnr.isBlank()){ 
        lvsIsBizOnr = "0"; 
      } 
      bizNtyModel.setIsBizOnr(lvsIsBizOnr); 
      
      String lvsIsCstmr = cvoCoreRequest.getVal("svsIsCstmr");
       if(lvsIsCstmr == null || lvsIsCstmr.isBlank()){ 
        lvsIsCstmr = "0"; 
      } 
      bizNtyModel.setIsCstmr(lvsIsCstmr);
      
      String lvsIsVndr = cvoCoreRequest.getVal("svsIsVndr");
       if(lvsIsVndr == null || lvsIsVndr.isBlank()){ 
        lvsIsVndr = "0"; 
      } 
      bizNtyModel.setIsVndr(lvsIsVndr);
      bizNtyModel.save(); 

      BizNtyLocModel bizNtyLocModel = bizNtyModel.getBizNtyLocModel(cvoCoreRequest.getIntVal("sviBizNtyLocD",-1)); 

      bizNtyLocModel.setBizNtyLocIdy(cvoCoreRequest.getVal("svsBizNtyLocIdy"));
      bizNtyLocModel.setBizNtyLocName(cvoCoreRequest.getVal("svsBizNtyLocName"));
      bizNtyLocModel.setBizNtyLocFrom(cvoCoreRequest.getDateVal("svdBizNtyLocFrom", App.DEFAULT_MIN_DATE));
      bizNtyLocModel.setBizNtyLocTo(cvoCoreRequest.getDateVal("svdBizNtyLocTo", App.DEFAULT_MAX_DATE));
      bizNtyLocModel.setBizNtyLocAdrsLine1(cvoCoreRequest.getVal("svsBizNtyLocAdrsLine1"));
      bizNtyLocModel.setBizNtyLocAdrsLine2(cvoCoreRequest.getVal("svsBizNtyLocAdrsLine2"));
      bizNtyLocModel.setBizNtyLocPostCode(cvoCoreRequest.getVal("svsBizNtyLocPostCode","800020"));
      bizNtyLocModel.setBizNtyLocState(cvoCoreRequest.getVal("svsLocState","ab"));
      bizNtyLocModel.setBizNtyLocCntry(cvoCoreRequest.getVal("svsLocCntry"));
      bizNtyLocModel.setBizNtyLocSalesTaxIdy(cvoCoreRequest.getVal("svsLocSalesTaxIdy"));
      bizNtyLocModel.setIzNtyLocIsSez(cvoCoreRequest.getVal("svsBizNtyLocIsSez"));
      bizNtyLocModel.setBizNtyLocCrdtPrid(cvoCoreRequest.getIntVal("sviBizNtyLocCrdtPrid", 0));
      String lvsIsIsRegOff = cvoCoreRequest.getVal("svsLocIsRegOff"); 
      if(lvsIsIsRegOff == null || lvsIsIsRegOff.isBlank()){ 
        lvsIsIsRegOff = "0"; 
      } 
      bizNtyLocModel.setBizNtyLocIsRegOff(lvsIsIsRegOff);
      String lvsBizNtyLocPtAthry = cvoCoreRequest.getVal("svsBizNtyLocPtAthry");
      if(lvsBizNtyLocPtAthry == null || lvsBizNtyLocPtAthry.isBlank())
      {
        lvsBizNtyLocPtAthry = "0";
      }
      bizNtyLocModel.setBizNtyLocPtAthry(lvsBizNtyLocPtAthry);
      String lvsIsMtrReg = cvoCoreRequest.getVal("svsBizNtyLocIsMtrReg"); 
      if(lvsIsMtrReg == null || lvsIsMtrReg.isBlank()){ 
        lvsIsMtrReg = "0"; 
      } 
      bizNtyLocModel.setBizNtyLocIsMtrReg(lvsIsMtrReg);
      
      String lvsSysHostLocCanSell = cvoCoreRequest.getVal("svsBizNtySysHostLocCanSell"); 
      if(lvsSysHostLocCanSell == null || lvsSysHostLocCanSell.isBlank()){ 
        lvsSysHostLocCanSell = "0"; 
      } 
      bizNtyLocModel.setBizNtySysHostLocCanSell(lvsSysHostLocCanSell);
      String lvsSysHostLocCanPchs = cvoCoreRequest.getVal("svsBizNtySysHostLocCanPchs"); 
      if(lvsSysHostLocCanPchs == null || lvsSysHostLocCanPchs.isBlank()){ 
        lvsSysHostLocCanPchs = "0"; 
      } 
      bizNtyLocModel.setBizNtySysHostLocCanPchs(lvsSysHostLocCanPchs);
      
      String lvsHldyGrp = cvoCoreRequest.getVal("svsHldyGrpDesc"); 
      cvoLogger.debug("svsHldyGrpDesc :: " + lvsHldyGrp); 
      HldyGrpReadOnly lvoHldyGrpReadOnly = ReadOnlyCacheManager.getInstance().getFirst(HldyGrpReadOnly.class, (t) -> { return t.svsHldyGrpName.equalsIgnoreCase(lvsHldyGrp); }); 
      if(lvoHldyGrpReadOnly == null){ 
        bizNtyLocModel.setBizNtyHldyGrpD(-1); 
      } 
      else { 
        bizNtyLocModel.setBizNtyHldyGrpD(lvoHldyGrpReadOnly.sviHldyGrpD); 
      } 

      //ToDo : get the D and update it here; cvoCoreRequest.getIntVal("svsHldyGrpDesc");

       
      bizNtyLocModel.save();
      cvoDbManager.commitTrans();
     
    };
   
     public void saveBizNty() throws SQLException{
        BizNtyModel bizNtyModel = BizNtyModel.load(cvoCoreRequest.getIntVal("sviBizNtyD",-1), cvoDbManager);
        bizNtyModel.setBizNtyIdy(cvoCoreRequest.getVal("svsBizNtyIdy"));
        bizNtyModel.setBizNtyFrom(cvoCoreRequest.getDateVal("svdBizNtyFrom",App.DEFAULT_MIN_DATE));
        bizNtyModel.setBizNtyTo(cvoCoreRequest.getDateVal("svdBizNtyTo",App.DEFAULT_MAX_DATE));
        bizNtyModel.setBizNtyName(cvoCoreRequest.getVal("svsBizNtyName"));
        bizNtyModel.setBizNtyNcomTaxIdy(cvoCoreRequest.getVal("svsNcomTaxIdy"));
        bizNtyModel.setBizNtyNcomTaxWthdgIdy(cvoCoreRequest.getVal("svsBizNtyNcomTaxWthdgIdy"));
        bizNtyModel.setBizNtyMSMEIdy(cvoCoreRequest.getVal("svsBizNtyMSMEIdy"));
        bizNtyModel.setBizNtyLegalIdy(cvoCoreRequest.getVal("svsLegalIdy"));
        String lvsIsBizOnr = cvoCoreRequest.getVal("svsIsBizOnr"); 
      if(lvsIsBizOnr == null || lvsIsBizOnr.isBlank()){ 
        lvsIsBizOnr = "0"; 
      } 
      bizNtyModel.setIsBizOnr(lvsIsBizOnr); 
      
      String lvsIsCstmr = cvoCoreRequest.getVal("svsIsCstmr");
       if(lvsIsCstmr == null || lvsIsCstmr.isBlank()){ 
        lvsIsCstmr = "0"; 
      } 
      bizNtyModel.setIsCstmr(lvsIsCstmr);
      
      String lvsIsVndr = cvoCoreRequest.getVal("svsIsVndr");
       if(lvsIsVndr == null || lvsIsVndr.isBlank()){ 
        lvsIsVndr = "0"; 
      } 
      bizNtyModel.setIsVndr(lvsIsVndr);
      bizNtyModel.save(); 
      cvoDbManager.commitTrans();
     }

  public void saveBizNtyLoc() throws SQLException{ 
      BizNtyModel bizNtyModel = BizNtyModel.load(cvoCoreRequest.getIntVal("sviBizNtyD"), cvoDbManager);
      BizNtyLocModel bizNtyLocModel = bizNtyModel.getBizNtyLocModel(cvoCoreRequest.getIntVal("sviBizNtyLocD",-1)); 

      bizNtyLocModel.setBizNtyLocIdy(cvoCoreRequest.getVal("svsBizNtyLocIdy"));
      bizNtyLocModel.setBizNtyLocName(cvoCoreRequest.getVal("svsBizNtyLocName"));
      bizNtyLocModel.setBizNtyLocFrom(cvoCoreRequest.getDateVal("svdBizNtyLocFrom", App.DEFAULT_MIN_DATE));
      bizNtyLocModel.setBizNtyLocTo(cvoCoreRequest.getDateVal("svdBizNtyLocTo", App.DEFAULT_MAX_DATE));
      bizNtyLocModel.setBizNtyLocAdrsLine1(cvoCoreRequest.getVal("svsBizNtyLocAdrsLine1"));
      bizNtyLocModel.setBizNtyLocAdrsLine2(cvoCoreRequest.getVal("svsBizNtyLocAdrsLine2"));
      bizNtyLocModel.setBizNtyLocPostCode(cvoCoreRequest.getVal("svsBizNtyLocPostCode"));
       bizNtyLocModel.setBizNtyLocState(cvoCoreRequest.getVal("svsLocState"));
      bizNtyLocModel.setBizNtyLocCntry(cvoCoreRequest.getVal("svsLocCntry"));
      bizNtyLocModel.setBizNtyLocSalesTaxIdy(cvoCoreRequest.getVal("svsLocSalesTaxIdy"));
      bizNtyLocModel.setIzNtyLocIsSez(cvoCoreRequest.getVal("svsBizNtyLocIsSez"));
      bizNtyLocModel.setBizNtyLocCrdtPrid(cvoCoreRequest.getIntVal("sviBizNtyLocCrdtPrid", 0));
      String lvsIsIsRegOff = cvoCoreRequest.getVal("svsLocIsRegOff"); 
      if(lvsIsIsRegOff == null || lvsIsIsRegOff.isBlank()){ 
        lvsIsIsRegOff = "0"; 
      } 
      bizNtyLocModel.setBizNtyLocIsRegOff(lvsIsIsRegOff);
      String lvsBizNtyLocPtAthry = cvoCoreRequest.getVal("svsBizNtyLocPtAthry");
      if(lvsBizNtyLocPtAthry == null || lvsBizNtyLocPtAthry.isBlank())
      {
        lvsBizNtyLocPtAthry = "0";
      }
      bizNtyLocModel.setBizNtyLocPtAthry(lvsBizNtyLocPtAthry);
      String lvsIsMtrReg = cvoCoreRequest.getVal("svsBizNtyLocIsMtrReg"); 
      if(lvsIsMtrReg == null || lvsIsMtrReg.isBlank()){ 
        lvsIsMtrReg = "0"; 
      } 
      bizNtyLocModel.setBizNtyLocIsMtrReg(lvsIsMtrReg);
      
      String lvsSysHostLocCanSell = cvoCoreRequest.getVal("svsBizNtySysHostLocCanSell"); 
      if(lvsSysHostLocCanSell == null || lvsSysHostLocCanSell.isBlank()){ 
        lvsSysHostLocCanSell = "0"; 
      } 
      bizNtyLocModel.setBizNtySysHostLocCanSell(lvsSysHostLocCanSell);
      String lvsSysHostLocCanPchs = cvoCoreRequest.getVal("svsBizNtySysHostLocCanPchs"); 
      if(lvsSysHostLocCanPchs == null || lvsSysHostLocCanPchs.isBlank()){ 
        lvsSysHostLocCanPchs = "0"; 
      } 
      bizNtyLocModel.setBizNtySysHostLocCanPchs(lvsSysHostLocCanPchs);
      String lvsHldyGrp = cvoCoreRequest.getVal("svsHldyGrpDesc"); 
      cvoLogger.debug("svsHldyGrpDesc :: " + lvsHldyGrp); 
      HldyGrpReadOnly lvoHldyGrpReadOnly = ReadOnlyCacheManager.getInstance().getFirst(HldyGrpReadOnly.class, (t) -> { return t.svsHldyGrpName.equalsIgnoreCase(lvsHldyGrp); }); 
      if(lvoHldyGrpReadOnly == null){ 
        bizNtyLocModel.setBizNtyHldyGrpD(-1); 
      }  
      else { 
        bizNtyLocModel.setBizNtyHldyGrpD(lvoHldyGrpReadOnly.sviHldyGrpD); 
      } 
      
      bizNtyLocModel.save();
      if( cvoDbManager.canCommit()){
         cvoDbManager.commitTrans();
         cvoCoreResponse.addMessage(CoreMessage.RECORDS_SUBMITTED_SUCCESSFULLY);
      }
  }
  
  public void saveBizNtyTo() throws SQLException{
    BizNtyModel bizNtyModel = BizNtyModel.load(cvoCoreRequest.getIntVal("sviBizNtyD"), cvoDbManager);
    bizNtyModel.setBizNtyTo(cvoCoreRequest.getDateVal("svdBizNtyTo"));
    bizNtyModel.save();
    if( cvoDbManager.canCommit()){
       cvoDbManager.commitTrans();
       cvoCoreResponse.addMessage(CoreMessage.RECORDS_SUBMITTED_SUCCESSFULLY);
    } 
  }
  
  public void saveBizNtyLocTo() throws SQLException{
    BizNtyModel bizNtyModel = BizNtyModel.load(cvoCoreRequest.getIntVal("sviBizNtyD"), cvoDbManager);
    BizNtyLocModel bizNtyLocModel = bizNtyModel.getBizNtyLocModel(cvoCoreRequest.getIntVal("sviBizNtyLocD",-1)); 
    bizNtyLocModel.setBizNtyLocTo(cvoCoreRequest.getDateVal("svdBizNtyLocTo"));

    bizNtyLocModel.save();
    if( cvoDbManager.canCommit()){
       cvoDbManager.commitTrans();
       cvoCoreResponse.addMessage(CoreMessage.RECORDS_SUBMITTED_SUCCESSFULLY);
    } 
  } 
  
 
}



