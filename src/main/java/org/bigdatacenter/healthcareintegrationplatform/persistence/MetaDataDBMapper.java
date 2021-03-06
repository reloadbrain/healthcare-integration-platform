package org.bigdatacenter.healthcareintegrationplatform.persistence;

import org.apache.ibatis.annotations.*;
import org.bigdatacenter.healthcareintegrationplatform.domain.meta.MetaColumnInfo;
import org.bigdatacenter.healthcareintegrationplatform.domain.meta.MetaDatabaseInfo;
import org.bigdatacenter.healthcareintegrationplatform.domain.meta.MetaTableInfo;
import org.bigdatacenter.healthcareintegrationplatform.domain.transaction.TrFilterInfo;
import org.bigdatacenter.healthcareintegrationplatform.domain.transaction.TrProjectionInfo;
import org.bigdatacenter.healthcareintegrationplatform.domain.transaction.TrRequestInfo;
import org.bigdatacenter.healthcareintegrationplatform.domain.transaction.TrYearInfo;

import java.util.List;

@Mapper
public interface MetaDataDBMapper {
    Integer PROCESS_STATE_CODE_COMPLETED = 1;
    Integer PROCESS_STATE_CODE_PROCESSING = 2;
    Integer PROCESS_STATE_CODE_REJECTED = 3;
    Integer PROCESS_STATE_CODE_REQUESTED = 4;
    Integer PROCESS_STATE_CODE_ADMIN_ACCEPTED = 5;
    Integer PROCESS_STATE_CODE_REQUEST_ACCEPTED = 6;

    /*
     * Transaction Database Mapper
     */
    @Select("SELECT * FROM health_care_ui.tr_dataset_list WHERE dataSetUID = #{dataSetUID}")
    TrRequestInfo readRequest(@Param("dataSetUID") Integer dataSetUID);

    @Select("SELECT tw_json FROM health_care_ui.tr_workflow WHERE dataSetUID = #{dataSetUID}")
    String readRequestForWorkFlow(@Param("dataSetUID") Integer dataSetUID);

    @Select("SELECT * FROM health_care_ui.tr_dataset_year WHERE dataSetUID = #{dataSetUID}")
    List<TrYearInfo> readYears(@Param("dataSetUID") Integer dataSetUID);

    @Select("SELECT * FROM health_care_ui.tr_dataset_filter WHERE dataSetUID = #{dataSetUID}")
    List<TrFilterInfo> readFilters(@Param("dataSetUID") Integer dataSetUID);

    @Select("SELECT * FROM health_care_ui.tr_dataset_select WHERE dataSetUID = #{dataSetUID}")
    List<TrProjectionInfo> readProjections1(@Param("dataSetUID") Integer dataSetUID);

    @Select("SELECT * FROM health_care_ui.tr_dataset_select WHERE dataSetUID = #{dataSetUID} AND etl_eng_name = #{etl_eng_name}")
    List<TrProjectionInfo> readProjections2(@Param("dataSetUID") Integer dataSetUID, @Param("etl_eng_name") String etlEngName);

    @Select("SELECT ecl_eng_name FROM health_care_ui.tr_dataset_select WHERE dataSetUID = #{dataSetUID} AND etl_eng_name = #{etl_eng_name}")
    List<String> readProjectionNames(@Param("dataSetUID") Integer dataSetUID, @Param("etl_eng_name") String etlEngName);

    @Update("UPDATE health_care_ui.tr_dataset_list SET jobStartTime = #{jobStartTime} WHERE dataSetUID = #{dataSetUID}")
    Integer updateJobStartTime(@Param("dataSetUID") Integer dataSetUID, @Param("jobStartTime") String jobStartTime);

    @Update("UPDATE health_care_ui.tr_dataset_list SET jobEndTime = #{jobEndTime} WHERE dataSetUID = #{dataSetUID}")
    Integer updateJobEndTime(@Param("dataSetUID") Integer dataSetUID, @Param("jobEndTime") String jobEndTime);

    @Update("UPDATE health_care_ui.tr_dataset_list SET elapsedTime = #{elapsedTime} WHERE dataSetUID = #{dataSetUID}")
    Integer updateElapsedTime(@Param("dataSetUID") Integer dataSetUID, @Param("elapsedTime") String elapsedTime);

    @Update("UPDATE health_care_ui.tr_dataset_list SET processState = #{processState} WHERE dataSetUID = #{dataSetUID}")
    Integer updateProcessState(@Param("dataSetUID") Integer dataSetUID, @Param("processState") Integer processState);

    @Update("UPDATE health_care_ui.tr_dataset_list SET statisticState = #{statisticState} WHERE dataSetUID = #{dataSetUID}")
    Integer updateStatisticState(@Param("dataSetUID") Integer dataSetUID, @Param("statisticState") Integer statisticState);

    @Insert("INSERT INTO health_care_ui.ftp_request_meta(dataSetUID, userID, ftpURI) VALUES(#{dataSetUID}, #{userID}, #{ftpURI})")
    Integer createFtpInfo(@Param("dataSetUID") Integer dataSetUID, @Param("userID") String userID, @Param("ftpURI") String ftpURI);


    /*
     * Meta Database Mapper
     */
    @Select("SELECT * FROM health_care_ui.chu_db_list WHERE edl_idx = #{edl_idx}")
    MetaDatabaseInfo readDatabase(@Param("edl_idx") Integer edlIdx);

    @Select("SELECT * FROM health_care_ui.chu_tb_list WHERE etl_idx = #{etl_idx}")
    MetaTableInfo readTable(@Param("etl_idx") Integer etlIdx);

    @Select("SELECT * FROM health_care_ui.chu_col_list_ref WHERE ecl_idx = #{ecl_idx}")
    MetaColumnInfo readColumn(@Param("ecl_idx") Integer eclIdx);

    @Select("SELECT * FROM health_care_ui.chu_col_list_ref WHERE edl_idx = #{edl_idx} AND ecl_ref = #{ecl_ref} AND ecl_year = #{ecl_year}")
    List<MetaColumnInfo> readColumns(@Param("edl_idx") Integer edlIdx, @Param("ecl_ref") String eclRef, @Param("ecl_year") Integer eclYear);

//    @Select("SELECT etl_eng_name FROM health_care_ui.chu_tb_list WHERE edl_idx = #{edl_idx} AND tb_year = #{tb_year}")
    @Select("SELECT etl_ref FROM health_care_ui.chu_tb_list WHERE edl_idx = #{edl_idx} AND tb_year = #{tb_year}")
    List<String> readTableNames(@Param("edl_idx") Integer edlIdx, @Param("tb_year") Integer tbYear);

    @Select("SELECT DISTINCT ecl_eng_name FROM health_care_ui.chu_col_list_ref WHERE etl_idx = (SELECT etl_idx FROM health_care_ui.chu_tb_list WHERE etl_ref = #{etl_ref} AND tb_year = #{tb_year})")
    List<String> readColumnNames(@Param("etl_ref") String etlRef, @Param("tb_year") Integer tbYear);
}