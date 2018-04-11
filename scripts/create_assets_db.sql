--------------------------------------------------------
--  DDL for Table ACTAS_DOC
--------------------------------------------------------

  CREATE TABLE "ACTAS_DOC" 
   (	"ACTAS_ID" NUMBER(19,0), 
	"NOM_AUTOR" VARCHAR2(255 CHAR), 
	"COD_CORPORACION" VARCHAR2(255 CHAR), 
	"FEC_ACTA" TIMESTAMP (6), 
	"NOM_TITULO" VARCHAR2(255 CHAR), 
	"TIPODOCUMENTO_TIPDOC_ID" NUMBER(19,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Table DOC_DOCUMENTO
--------------------------------------------------------

  CREATE TABLE "DOC_DOCUMENTO" 
   (	"DOC_ID" NUMBER(19,0), 
	"NOM_DESCRIPCION" VARCHAR2(1000 CHAR), 
	"FLG_ESTADO" NUMBER(1,0), 
	"FEC_FECHACARGA" TIMESTAMP (6), 
	"FEC_FECHACREACION" TIMESTAMP (6), 
	"NOM_ORIGINALDOCUMENTBASENAME" VARCHAR2(255 CHAR), 
	"NOM_ORIGINALDOCUMENTEXTENSION" VARCHAR2(255 CHAR), 
	"NOM_TITULO" VARCHAR2(255 CHAR), 
	"COD_TOKENDOCUMENTO" VARCHAR2(255 CHAR), 
	"COR_VERSION" NUMBER(10,0), 
	"DOC_PARENTDOCUMENT_ID" NUMBER(19,0), 
	"DOC_TIPOCORPORACION_ID" NUMBER(19,0), 
	"FLG_TIPODOCUMENTO_ID" NUMBER(19,0),
	"NUM_SESION" NUMBER(19,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Table MEN_MENU
--------------------------------------------------------

  CREATE TABLE "MEN_MENU" 
   (	"MEN_ID" NUMBER(19,0), 
	"NOM_BOOKMARK" VARCHAR2(255 CHAR), 
	"NOM_BOOSTRAPICON" VARCHAR2(255 CHAR), 
	"NOM_IMAGE" VARCHAR2(255 CHAR), 
	"NOM_LABEL" VARCHAR2(255 CHAR), 
	"NOM_OPTIONS" VARCHAR2(255 CHAR), 
	"NUM_ORDER" NUMBER(19,0), 
	"NOM_PAGE" VARCHAR2(255 CHAR), 
	"NOM_PARAMS" VARCHAR2(255 CHAR), 
	"NUM_PARENTID" NUMBER(19,0), 
	"COD_PRIVILEGE" VARCHAR2(255 CHAR), 
	"FLG_ESTADO" NUMBER(1,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Table SEMA_SEMAPHORE
--------------------------------------------------------

  CREATE TABLE "SEMA_SEMAPHORE" 
   (	"SEMA_ID" NUMBER(19,0), 
	"COR_COUNT" NUMBER(19,0), 
	"NOM_SEMAFORO" VARCHAR2(255 CHAR)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Table TIP_TIPOCORPORACION
--------------------------------------------------------

  CREATE TABLE "TIP_TIPOCORPORACION" 
   (	"TIP_ID" NUMBER(19,0), 
	"FLG_ESTADO" NUMBER(1,0), 
	"COD_NMONIC" VARCHAR2(255 CHAR), 
	"NOM_TIPOCORPORACION" VARCHAR2(255 CHAR)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Table TIP_TIPOUSUARIO
--------------------------------------------------------

  CREATE TABLE "TIP_TIPOUSUARIO" 
   (	"TIP_ID" NUMBER(19,0), 
	"FLG_ESTADO" NUMBER(1,0), 
	"COD_NMONIC" VARCHAR2(255 CHAR), 
	"NOM_TIPOUSUARIO" VARCHAR2(255 CHAR)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Table TIPDOC_TIPODOCUMENTO
--------------------------------------------------------

  CREATE TABLE "TIPDOC_TIPODOCUMENTO" 
   (	"TIPDOC_ID" NUMBER(19,0), 
	"FLG_ESTADO" NUMBER(1,0), 
	"COD_NMONIC" VARCHAR2(255 CHAR), 
	"NOM_TIPOACTA" VARCHAR2(255 CHAR),
	"TIP_ID" NUMBER(19,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Table UDV_USERDOCUMENTVISIBILITY
--------------------------------------------------------

  CREATE TABLE "UDV_USERDOCUMENTVISIBILITY" 
   (	"UDV_ID" NUMBER(19,0), 
	"FLG_ESTADO" NUMBER(1,0), 
	"FLG_ISACTIVE" NUMBER(1,0), 
	"UDV_TIPODOCUMENTOBO_ID" NUMBER(19,0), 
	"UDV_USERBO_ID" NUMBER(19,0)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Table USR_USUARIO
--------------------------------------------------------

  CREATE TABLE "USR_USUARIO" 
   (	"USR_ID" NUMBER(19,0), 
	"NOM_LOGINAD" VARCHAR2(255 CHAR), 
	"NOM_NOMBRE" VARCHAR2(255 CHAR), 
	"RUT_USUARIO" VARCHAR2(255 CHAR)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "Tspc_adb_dat_s" ;
--------------------------------------------------------
--  DDL for Index PK_ACTAS_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_ACTAS_ID" ON "ACTAS_DOC" ("ACTAS_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  DDL for Index PK_DOC_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_DOC_ID" ON "DOC_DOCUMENTO" ("DOC_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  DDL for Index PK_MENU_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_MENU_ID" ON "MEN_MENU" ("MEN_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  DDL for Index PK_SEMAPHORE_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_SEMAPHORE_ID" ON "SEMA_SEMAPHORE" ("SEMA_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  DDL for Index PK_TIP_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TIP_ID" ON "TIP_TIPOCORPORACION" ("TIP_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  DDL for Index PK_USUARIO_TIP_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_USUARIO_TIP_ID" ON "TIP_TIPOUSUARIO" ("TIP_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  DDL for Index PK_TIPODOC_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_TIPODOC_ID" ON "TIPDOC_TIPODOCUMENTO" ("TIPDOC_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  DDL for Index PK_UDV_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_UDV_ID" ON "UDV_USERDOCUMENTVISIBILITY" ("UDV_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  DDL for Index PK_USR_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "PK_USR_ID" ON "USR_USUARIO" ("USR_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s" ;
--------------------------------------------------------
--  Constraints for Table ACTAS_DOC
--------------------------------------------------------

  ALTER TABLE "ACTAS_DOC" ADD PRIMARY KEY ("ACTAS_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "ACTAS_DOC" MODIFY ("ACTAS_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DOC_DOCUMENTO
--------------------------------------------------------

  ALTER TABLE "DOC_DOCUMENTO" ADD PRIMARY KEY ("DOC_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "DOC_DOCUMENTO" MODIFY ("DOC_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MEN_MENU
--------------------------------------------------------

  ALTER TABLE "MEN_MENU" ADD PRIMARY KEY ("MEN_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "MEN_MENU" MODIFY ("MEN_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SEMA_SEMAPHORE
--------------------------------------------------------

  ALTER TABLE "SEMA_SEMAPHORE" ADD PRIMARY KEY ("SEMA_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "SEMA_SEMAPHORE" MODIFY ("SEMA_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TIP_TIPOCORPORACION
--------------------------------------------------------

  ALTER TABLE "TIP_TIPOCORPORACION" ADD PRIMARY KEY ("TIP_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "TIP_TIPOCORPORACION" MODIFY ("TIP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TIP_TIPOUSUARIO
--------------------------------------------------------

  ALTER TABLE "TIP_TIPOUSUARIO" ADD PRIMARY KEY ("TIP_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "TIP_TIPOUSUARIO" MODIFY ("TIP_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TIPDOC_TIPODOCUMENTO
--------------------------------------------------------

  ALTER TABLE "TIPDOC_TIPODOCUMENTO" ADD PRIMARY KEY ("TIPDOC_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "TIPDOC_TIPODOCUMENTO" MODIFY ("TIPDOC_ID" NOT NULL ENABLE);
  ALTER TABLE "TIPDOC_TIPODOCUMENTO" ADD CONSTRAINT "FK_14D1SL6RXFLQM9KB6MLAKVT5U" FOREIGN KEY ("TIP_ID")
	  REFERENCES "TIP_TIPOCORPORACION" ("TIP_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table UDV_USERDOCUMENTVISIBILITY
--------------------------------------------------------

  ALTER TABLE "UDV_USERDOCUMENTVISIBILITY" ADD PRIMARY KEY ("UDV_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "UDV_USERDOCUMENTVISIBILITY" MODIFY ("UDV_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USR_USUARIO
--------------------------------------------------------

  ALTER TABLE "USR_USUARIO" ADD PRIMARY KEY ("USR_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  TABLESPACE "Tspc_adb_idx_s"  ENABLE;
  ALTER TABLE "USR_USUARIO" MODIFY ("USR_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ACTAS_DOC
--------------------------------------------------------

  ALTER TABLE "ACTAS_DOC" ADD CONSTRAINT "FK_L43S9YVIQYEMDHETCFJ5TL4VG" FOREIGN KEY ("TIPODOCUMENTO_TIPDOC_ID")
	  REFERENCES "TIPDOC_TIPODOCUMENTO" ("TIPDOC_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table DOC_DOCUMENTO
--------------------------------------------------------

  ALTER TABLE "DOC_DOCUMENTO" ADD CONSTRAINT "FK_3SOTTN5BW8R1EWTGT2KJ844QW" FOREIGN KEY ("DOC_TIPOCORPORACION_ID")
	  REFERENCES "TIP_TIPOCORPORACION" ("TIP_ID") ENABLE;
  ALTER TABLE "DOC_DOCUMENTO" ADD CONSTRAINT "FK_P7I9HEAV8JMTD7FAYWPODXM7K" FOREIGN KEY ("FLG_TIPODOCUMENTO_ID")
	  REFERENCES "TIPDOC_TIPODOCUMENTO" ("TIPDOC_ID") ENABLE;
  ALTER TABLE "DOC_DOCUMENTO" ADD CONSTRAINT "FK_Q1OI3IR04HICED8KDJ829J68E" FOREIGN KEY ("DOC_PARENTDOCUMENT_ID")
	  REFERENCES "DOC_DOCUMENTO" ("DOC_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table UDV_USERDOCUMENTVISIBILITY
--------------------------------------------------------

  ALTER TABLE "UDV_USERDOCUMENTVISIBILITY" ADD CONSTRAINT "FK_74N62O3CLB7FQH4PYBHJN1UN0" FOREIGN KEY ("UDV_USERBO_ID")
	  REFERENCES "USR_USUARIO" ("USR_ID") ENABLE;
  ALTER TABLE "UDV_USERDOCUMENTVISIBILITY" ADD CONSTRAINT "FK_DD19P73YPJHUFOIWGFYHAGRV5" FOREIGN KEY ("UDV_TIPODOCUMENTOBO_ID")
	  REFERENCES "TIPDOC_TIPODOCUMENTO" ("TIPDOC_ID") ENABLE;

 CREATE SEQUENCE  "COR_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 104 CACHE 20 NOORDER  NOCYCLE  NOPARTITION ;

GRANT SELECT, INSERT, UPDATE, DELETE ON actas_doc TO R_ADB_ACT;
GRANT SELECT, INSERT, UPDATE, DELETE ON doc_documento TO R_ADB_ACT;
GRANT SELECT, INSERT, UPDATE, DELETE ON men_menu TO R_ADB_ACT;
GRANT SELECT, INSERT, UPDATE, DELETE ON tip_tipocorporacion TO R_ADB_ACT;
GRANT SELECT, INSERT, UPDATE, DELETE ON tip_tipousuario TO R_ADB_ACT;
GRANT SELECT, INSERT, UPDATE, DELETE ON tipdoc_tipodocumento TO R_ADB_ACT;
GRANT SELECT, INSERT, UPDATE, DELETE ON udv_userdocumentvisibility TO R_ADB_ACT;
GRANT SELECT, INSERT, UPDATE, DELETE ON usr_usuario TO R_ADB_ACT;
GRANT SELECT ON cor_sequence to R_ADB_ACT;


GRANT SELECT ON actas_doc TO R_ADB_CON;
GRANT SELECT ON doc_documento TO R_ADB_CON;
GRANT SELECT ON men_menu TO R_ADB_CON;
GRANT SELECT ON tip_tipocorporacion TO R_ADB_CON;
GRANT SELECT ON tip_tipousuario TO R_ADB_CON;
GRANT SELECT ON tipdoc_tipodocumento TO R_ADB_CON;
GRANT SELECT ON udv_userdocumentvisibility TO R_ADB_CON;
GRANT SELECT ON usr_usuario TO R_ADB_CON;