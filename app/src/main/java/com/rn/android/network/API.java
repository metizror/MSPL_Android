package com.rn.android.network;


import com.rn.android.BuildConfig;

public class API {
    public static String BASE_URL = BuildConfig.API;
    public static String Login_URL = BASE_URL + "Login";
    public static String Register_URL = BASE_URL + "Register";
    public static String GET_ALL_FACILITY = BASE_URL + "GetFacilityList";
    public static String Patient_List_URL = BASE_URL + "PatientList";
    public static String ConfirmRegistration = BASE_URL + "MobileRegisterMaster_ConfirmRegistration";
    public static String ConfirmHipa = BASE_URL + "ConfirmHipa";
    public static String OrderDelivertype = BASE_URL + "OrderDelivertype";
    public static String AddOrder = BASE_URL + "AddOrder";
    public static String ResetPassword = BASE_URL + "ResetPassword";
    public static String GetOrderByPatientID = BASE_URL + "GetOrderListByPatientID";
    public static String GetOrderByFacilityID = BASE_URL + "GetOrderListByFacilityID";
    public static String PrescriptionData = BASE_URL + "PrescriptionData";
    public static String DeleteOrder = BASE_URL + "DeleteOrderbyOrderID?OrderID=";
    public static String AddRefillorder = BASE_URL + "AddRefillorder";
    public static String NotificationList = BASE_URL + "NotificationByFacilityID";
    public static String GetUserlist = BASE_URL + "GetUserlist";
    public static String CreateUser = BASE_URL + "CreateUser";
    public static String ActiveDeactiveUser = BASE_URL + "ActiveDeactiveUser";
    public static String GetUpdatedDoctorData = "https://mymsync.mapyourmedsapi.com:5003/api/" + "GetUpdatedDoctorData";
    public static String GetPrescriptionDataByPatient = "https://mymsync.mapyourmedsapi.com:5003/api/PrescriptionData/GetPrescriptionDataForMobileByPatient?patient_id=";
    public static String AddPharmaClient = BASE_URL + "PharmacyClient";
    public static String GetPassWordflag = BASE_URL + "GetPassWordflag";
    public static String GetLogout = BASE_URL + "Logout";
    public static String SetNotification = BASE_URL + "SetNotification";
    public static String Signature = BASE_URL + "SignaturePath";
    public static String uploadClientDocuments = BASE_URL + "UploadPharmacyClientFiles";
    public static String Banner = BASE_URL + "HolidayBanner";
    public static String SendReply = BASE_URL + "NotificationReply";
    public static String Allergymaster = BASE_URL + "allergymaster";
    public static String NewNotification = BASE_URL + "GetNotificationDetailsbyID";
    public static String GetFacilityCycleDetails = BASE_URL + "GetFacilityCycleDetails";
    public static String GetCycleHeaderDetails = BASE_URL + "GetCycleHeaderDetails";
    public static String GetCycleListofDetails = BASE_URL + "GetCycleListofDetails";
    public static String GetCycleDeliveryDate = BASE_URL + "GetCycleDeliveryDate";
    public static String UpdateCycleDeliveryDate = BASE_URL + "UpdateCycleDeliveryDate";
    public static String PriscriptionImagePath = BASE_URL + "PriscriptionImagePath";
    public static String GetMedPassPatientList = BASE_URL + "GetMedPassPatientList";
    public static String GetMedPassDrugDetailsByPatientID = BASE_URL + "GetMedPassDrugDetailsByPatientID";
    public static String UpdatePatientMedPassDrugDetails = BASE_URL + "UpdatePatientMedPassDrugDetails";
    public static String GetMedPassDrugDetailsByRxnumber = BASE_URL + "GetMedPassDrugDetailsByRxnumber";
    public static String GetGivenMedPassDrugDetailsByPatientID = BASE_URL + "GetGivenMedPassDrugDetailsByPatientID";
    public static String PatientImagePath = BASE_URL + "PatientImagePath";
    public static String GetNoOfPendingFillPack = BASE_URL + "GetNo_OfPendingFillPack";
    public static String DrugImagePath = BASE_URL + "DrugImagePath";
    public static String SaveFacilityNoteByFacilityUser = BASE_URL + "SaveFacilityNoteByFacilityUser";

    public static String UpdateCyclePatientStatus = BASE_URL + "UpdateCyclePatientStatus";
    public static String UpdateCyclePatientStatusDemo = BASE_URL + "UpdateCyclePatientStatusDemo";
    public static String UpdateCyclePatientStatusSingle = BASE_URL + "UpdateCyclePatientWiseStatus";
    public static String DrugReceiver = BASE_URL + "DrugReceiver";
    public static String ReceivedDrugsList = BASE_URL + "ReceivedDrugsList";
    public static String GetInventoryDetailsByTranId = BASE_URL + "GetInventoryDetailsByTranId";
    public static String AddDrugReceiver = BASE_URL + "AddDrugReceiverDemo";
    public static String Getdeliverynobyfacility = BASE_URL + "Getdeliverynobyfacility";
    public static String GetPatientMedsheetReport = BASE_URL + "GetPatientMedsheetReport";
    public static String GetMedGivenpatientList = BASE_URL + "GetMedGivenpatientList";
    public static String UpdateCyclewisePatientStatus = BASE_URL + "UpdateCyclewisePatientStatus";
    public static String CycleMedChangesForSinglePatient = BASE_URL + "CycleMedChangesForSinglePatient";
    public static String CycleMedChangesForAllPatient = BASE_URL + "CycleMedChangesForAllPatient";
    public static String CensusChangesForPatient = BASE_URL + "CensusChangesForPatient";
    public static String PatientPrescription = BASE_URL + "PatientPrescription";
    public static String DeleteCycleChangesEntry = BASE_URL + "DeleteCycleChangesEntry";
    public static String UpdatePatientMedSheetDownloadStatus = BASE_URL + "UpdatePatientMedSheetDownloadStatus";

    public static String GetLoaHospitalNames = BASE_URL + "GetLOAHospitalList";
    public static String UpdateLoaHospital = BASE_URL + "UpdateLOAHospital";
    public static String MissdoseUpdateLoaHospital = BASE_URL + "Update_Missdose_LOAHospital";
    public static String GetMedPassPRNDrugDetailsByPatientID = BASE_URL + "GetMedPassPRNDrugDetailsByPatientID";
    public static String ManagePatientPRNMedPassDrugDetails = BASE_URL + "ManagePatientPRNMedPassDrugDetails";

    //Edited by rohan

    public static String GetInternalIPSPatientID = BASE_URL + "GetInternalIPSPatientID";
    public static String GetCurrentMedpassDetailsByPatientID = BASE_URL + "GetCurrentMedpassDetailsByPatientID";

    //    Edited by jaimin
//    public static String UpdatePatientMedpassDetails_By_MedpassID = BASE_URL + "UpdatePatientMedpassDetails_By_MedpassID";
    public static String UpdatePatientMedpassDetails_By_MedpassID = BASE_URL + "UpdatePatientMedpassDetails_By_MedpassID_July_04";


    public static String GetAllPatientMedSheetReport = BASE_URL + "GetAllPatientMedSheetReport";
    public static String Forgotpassword = BASE_URL + "Forgotpassword";
    public static String SendRandomPasswordToUser = BASE_URL + "SendRandomPasswordToUser";
    public static String InsertMedpassLog = BASE_URL + "InsertMedpassLog";
    public static String InsertSometingwantErrorLog = BASE_URL + "InsertSometingwantErrorLog";

}
