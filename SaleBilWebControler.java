/*
 * InSilico Solutions LLP
 * www.insilicoss.com
 */
package com.insilicoss.cab.saleBill;

import com.insilicoss.database.DBManager;
import com.insilicoss.eventManager.CoreRequest;
import com.insilicoss.eventManager.CoreResponse;
import com.insilicoss.eventManager.Row;
import com.insilicoss.eventManager.RowSet;
import com.insilicoss.messaging.CoreMessage;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Alaknanda
 */
public class SaleBilWebControler {
   private CoreRequest cvoCoreRequest;
  private CoreResponse cvoCoreResponse;
  private  DBManager cvoDbManager;
  private Logger cvoLogger; 

  public SaleBilWebControler(CoreRequest pvoCoreRequest, CoreResponse pvoCoreResponse) {
    cvoCoreRequest = pvoCoreRequest;
    cvoCoreResponse = pvoCoreResponse;
    cvoDbManager = cvoCoreRequest.getDBManager();
    cvoLogger = LogManager.getLogger(); 
  } 
  
  public void saveAllBilBilLine() throws SQLException{
    SaleBilModel saleBilModel = null;
    saleBilModel = SaleBilModel.load(cvoCoreRequest.getIntVal("sviBilD",-1), cvoDbManager);
    cvoLogger.debug("===========Bill D" + saleBilModel.cviBilD);
    saleBilModel.setBizNtyOnrLocD(cvoCoreRequest.getIntVal("sviBizNtyOnrLocD"));
    saleBilModel.setTxctgPartyLocD(cvoCoreRequest.getIntVal("sviTxctgPartyLocD"));
    saleBilModel.setBizNtyOnrLocF(cvoCoreRequest.getIntVal("sviBizNtyOnrLocF"));
    saleBilModel.setTxctgPartyLocF(cvoCoreRequest.getIntVal("sviTxctgPartyLocF"));
    saleBilModel.setBizNtyOnrShipLocD(cvoCoreRequest.getIntVal("sviBizNtyOnrLocD"));
    saleBilModel.setBizNtyOnrShipLocF(cvoCoreRequest.getIntVal("sviBizNtyOnrLocF"));
    saleBilModel.setTxctgPartyShipLocD(cvoCoreRequest.getIntVal("sviTxctgPartyLocD")); 
    saleBilModel.setTxctgPartyShipLocF(cvoCoreRequest.getIntVal("sviTxctgPartyLocF"));
    saleBilModel.setBnfcrOnr(cvoCoreRequest.getVal("svsBizNtyOnrLocDesc"));
    saleBilModel.setBnfcrOnrD(cvoCoreRequest.getIntVal("sviBizNtyOnrLocD")); 
    saleBilModel.setBnfcrOnrF(cvoCoreRequest.getIntVal("sviBizNtyOnrLocF")); 
    saleBilModel.setBilIdy(cvoCoreRequest.getVal("svsBilIdy"));
    saleBilModel.setBilDate(cvoCoreRequest.getDateVal("svdBilDate"));
    saleBilModel.setBilDueDate(cvoCoreRequest.getDateVal("svdBilDueDate"));
    saleBilModel.setPchsOrdrIdy(cvoCoreRequest.getVal("svsPchsOrdrIdy"));
    saleBilModel.setPchsOrdrDate(cvoCoreRequest.getDateVal("svdPchsOrdrDate"));
    saleBilModel.setBilCrncIdy(cvoCoreRequest.getVal("svsBilCrncIdy"));
    saleBilModel.setBilXchngRate(cvoCoreRequest.getIntVal("svnBilXchngRate"));
    saleBilModel.setTaxTmpltD(cvoCoreRequest.getIntVal("svsTaxTmpltDesc",-1));
    saleBilModel.setBilPrprdUsrD(cvoCoreRequest.getIntVal("svsBilPrprdUsrDesc",-1));
    saleBilModel.setBilAprvdUsrD(cvoCoreRequest.getIntVal("svsBilAprvdUsrDesc",-1)); 
    
    RowSet lvoBilLine = cvoCoreRequest.getRowSet("svoBilLine");
    while(lvoBilLine.hasNext()){
      Row lvoRowBilLine = lvoBilLine.moveNext();
      
//      SaleBilLineModel lvosaleBilLineModel = saleBilModel.getSaleBilLineModel(lvoRowBilLine.getIntVal("sviBilLineD",-1));
//      
//      if(lvosaleBilLineModel == null)
//      {
//        lvosaleBilLineModel = saleBilModel.createSaleBilLineModel();
//      } 
      
      lvosaleBilLineModel.setItemD(lvoRowBilLine.getIntVal("sviItemD"));
      lvosaleBilLineModel.setItemF(lvoRowBilLine.getIntVal("sviItemF"));
      lvosaleBilLineModel.setBilLineDesc(lvoRowBilLine.getVal("svsBilLineDesc"));
      lvosaleBilLineModel.setBilLineUom(lvoRowBilLine.getVal("svsBilLineUom"));
      lvosaleBilLineModel.setBilLineQty(lvoRowBilLine.getIntVal("svnBilLineQty"));
      lvosaleBilLineModel.setBilLineUnitPrice(lvoRowBilLine.getIntVal("svnBilLineUnitPrice"));
      lvosaleBilLineModel.setBilLineDscnt(lvoRowBilLine.getIntVal("svnBilLineDscnt"));
      lvosaleBilLineModel.setBilLineVal(lvoRowBilLine.getIntVal("svnBilLineVal"));
      lvosaleBilLineModel.setBilLineHSN(lvoRowBilLine.getVal("svsBilLineHSN"));

      
    }
    saleBilModel.save();
     
    
     if( cvoDbManager.canCommit()){ 
      cvoDbManager.commitTrans(); 
      cvoCoreResponse.addMessage(CoreMessage.RECORDS_SUBMITTED_SUCCESSFULLY); 
    } 
  }
}
