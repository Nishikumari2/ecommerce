
package com.insilicoss.cab.saleBill;

import com.insilicoss.database.DBManager;
import com.insilicoss.validation.CoreValidator;
import java.sql.SQLException;
import lombok.TrackChanges;

@TrackChanges 
public class SaleBilLineModel {
  
 private String cvsBilLineActv;
 
 private int cviBilD;
 
 private int cviItemD;
 
 private int cviItemF;
 
 private String cvsBilLineDesc;
 
 private String cvsBilLineUom;
 
 private int cvnBilLineQty;
 
 private int cvnBilLineUnitPrice;
 
 private int cvnBilLineDscnt;
 
 private int cvnBilLineVal;
 
 private String cvbBilLineHasSupotDoc = "1";
 
 private String cvsBilLineDspsl = " ";
 
 private String cvsCntxt = " ";
 
 private int cviCntxtBizNtyD = -1;
 
 private int cviCntxtNgmtD = -1;
 
 private String cvsBilLineHSN;
 
 private int cviLnkdBilLineD = -1;
 
 private String cvsBilLineNotes = " ";
 
 public int cviBilLineD;
 private final DBManager cvoDBManager;

 private SaleBilLineModel(int pviBilD,DBManager cvoDBManager){
   this.cviBilD    = pviBilD; 
   this.cviBilLineD = -1; 
   this.cvoDBManager = cvoDBManager;
 }
 
 private SaleBilLineModel(String pvsBilLineActv,int pviBilD,int pviItemD,int pviItemF,String pvsBilLineDesc,String pvsBilLineUom,int pvnBilLineQty,int pvnBilLineUnitPrice,int pvnBilLineDscnt,
                int pvnBilLineVal,String pvbBilLineHasSupotDoc,String pvsBilLineDspsl,String pvsCntxt,int pviCntxtBizNtyD,int pviCntxtNgmtD,String pvsBilLineHSN,int pviLnkdBilLineD,String pvsBilLineNotes,
                int pviBilLineD,DBManager pvodBManager){
   this.cvsBilLineActv      = pvsBilLineActv;
   this.cviBilD             = pviBilD;
   this.cviItemD            = pviItemD;
   this.cviItemF            = pviItemF;
   this.cvsBilLineDesc      = pvsBilLineDesc;
   this.cvsBilLineUom       = pvsBilLineUom;
   this.cvnBilLineQty       = pvnBilLineQty;
   this.cvnBilLineUnitPrice = pvnBilLineUnitPrice;
   this.cvnBilLineDscnt     = pvnBilLineDscnt;
   this.cvnBilLineVal       = pvnBilLineVal;
   this.cvbBilLineHasSupotDoc = pvbBilLineHasSupotDoc;
   this.cvsBilLineDspsl       = pvsBilLineDspsl;
   this.cvsCntxt              = pvsCntxt;
   this.cviCntxtBizNtyD       = pviCntxtBizNtyD;
   this.cviCntxtNgmtD         = pviCntxtNgmtD;
   this.cvsBilLineHSN         = pvsBilLineHSN;
   this.cviLnkdBilLineD       = pviLnkdBilLineD;
   this.cvsBilLineNotes       = pvsBilLineNotes;
   this.cviBilLineD           = pviBilLineD;
   this.cvoDBManager          = pvodBManager; 
 } 
 
 public static SaleBilLineModel load(String pvsBilLineActv,int pviBilD,int pviItemD,int pviItemF,String pvsBilLineDesc,String pvsBilLineUom,int pvnBilLineQty,int pvnBilLineUnitPrice,int pvnBilLineDscnt,
                int pvnBilLineVal,String pvbBilLineHasSupotDoc,String pvsBilLineDspsl,String pvsCntxt,int pviCntxtBizNtyD,int pviCntxtNgmtD,String pvsBilLineHSN,int pviLnkdBilLineD,String pvsBilLineNotes,
                int pviBilLineD,DBManager dBManager){
   SaleBilLineModel saleBilLineModel = new SaleBilLineModel(pvsBilLineActv,pviBilD,pviItemD,pviItemF,pvsBilLineDesc,pvsBilLineUom,pvnBilLineQty,pvnBilLineUnitPrice,pvnBilLineDscnt,
                pvnBilLineVal,pvbBilLineHasSupotDoc,pvsBilLineDspsl,pvsCntxt,pviCntxtBizNtyD,pviCntxtNgmtD,pvsBilLineHSN,pviLnkdBilLineD,pvsBilLineNotes,
                pviBilLineD,dBManager);
   return saleBilLineModel;
 } 
 
 public static SaleBilLineModel createNew(int pviBilD, DBManager cvoDBManager){ 
    SaleBilLineModel saleBilLineModel = new SaleBilLineModel(pviBilD, cvoDBManager);
    return saleBilLineModel; 
  } 
 
 public void save() throws SQLException{
  CoreValidator.validateAutoFlag(this); 
  if(isChanged){
      cvoDBManager.addContextParam("rviBilD",cviBilD);
      cvoDBManager.addContextParam("rviItemD",cviItemD);
      cvoDBManager.addContextParam("rviItemF",cviItemF);
      cvoDBManager.addContextParam("rvsBilLineDesc",cvsBilLineDesc);
      cvoDBManager.addContextParam("rvsBilLineUom",cvsBilLineUom);
      cvoDBManager.addContextParam("rvnBilLineQty",cvnBilLineQty);
      cvoDBManager.addContextParam("rvnBilLineUnitPrice",cvnBilLineUnitPrice);
      cvoDBManager.addContextParam("rvnBilLineDscnt",cvnBilLineDscnt);
      cvoDBManager.addContextParam("rvnBilLineVal",cvnBilLineVal);
      cvoDBManager.addContextParam("rvbBilLineHasSupotDoc",cvbBilLineHasSupotDoc);
      cvoDBManager.addContextParam("rvsBilLineDspsl",cvsBilLineDspsl);
      cvoDBManager.addContextParam("rvsCntxt",cvsCntxt);
      cvoDBManager.addContextParam("rviCntxtBizNtyD",cviCntxtBizNtyD);
      cvoDBManager.addContextParam("rviCntxtNgmtD",cviCntxtNgmtD);
      cvoDBManager.addContextParam("rvsBilLineHSN",cvsBilLineHSN);
      cvoDBManager.addContextParam("rviLnkdBilLineD",cviLnkdBilLineD);
      cvoDBManager.addContextParam("rvsBilLineNotes",cvsBilLineNotes);

      if(cviBilLineD > 0){
        cvoDBManager.addContextParam("rviBilLineD",cviBilLineD);
        cvoDBManager.update("cab\\saleBill\\uarBilLine");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
       } else { 
        cviBilLineD = cvoDBManager.getNextTxnD("BilLineD"); 
        cvoDBManager.addContextParam("rviBilLineD",cviBilLineD);
        cvoDBManager.update("cab\\saleBill\\iarBilLine");  
      }
  }
 }
 
}
