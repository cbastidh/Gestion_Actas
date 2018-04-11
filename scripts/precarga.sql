Insert into BICE_GA.MEN_MENU (MEN_ID,NOM_BOOKMARK,NOM_BOOSTRAPICON,NOM_IMAGE,NOM_LABEL,NOM_OPTIONS,NUM_ORDER,NOM_PAGE,NOM_PARAMS,NUM_PARENTID,COD_PRIVILEGE,FLG_ESTADO) values ('5',null,null,null,'Buacute;squeda',null,'10','acts/search',null,'3','VISUALIZADOR','1');
Insert into BICE_GA.MEN_MENU (MEN_ID,NOM_BOOKMARK,NOM_BOOSTRAPICON,NOM_IMAGE,NOM_LABEL,NOM_OPTIONS,NUM_ORDER,NOM_PAGE,NOM_PARAMS,NUM_PARENTID,COD_PRIVILEGE,FLG_ESTADO) values ('6',null,null,null,'Carga',null,'15','acts/load',null,'3','ADMINISTRATOR','1');
Insert into BICE_GA.MEN_MENU (MEN_ID,NOM_BOOKMARK,NOM_BOOSTRAPICON,NOM_IMAGE,NOM_LABEL,NOM_OPTIONS,NUM_ORDER,NOM_PAGE,NOM_PARAMS,NUM_PARENTID,COD_PRIVILEGE,FLG_ESTADO) values ('1',null,'fa fa-users',null,'Administracioacute;n',null,'10',null,null,null,'ADMINISTRATOR','1');
Insert into BICE_GA.MEN_MENU (MEN_ID,NOM_BOOKMARK,NOM_BOOSTRAPICON,NOM_IMAGE,NOM_LABEL,NOM_OPTIONS,NUM_ORDER,NOM_PAGE,NOM_PARAMS,NUM_PARENTID,COD_PRIVILEGE,FLG_ESTADO) values ('2',null,null,null,'Usuarios',null,'1','users/list',null,'1','ADMINISTRATOR','1');
Insert into BICE_GA.MEN_MENU (MEN_ID,NOM_BOOKMARK,NOM_BOOSTRAPICON,NOM_IMAGE,NOM_LABEL,NOM_OPTIONS,NUM_ORDER,NOM_PAGE,NOM_PARAMS,NUM_PARENTID,COD_PRIVILEGE,FLG_ESTADO) values ('3',null,'fa fa-file-text-o',null,'Informacioacute;n directorio',null,'5',null,null,null,'VISUALIZADOR','1');
Insert into BICE_GA.MEN_MENU (MEN_ID,NOM_BOOKMARK,NOM_BOOSTRAPICON,NOM_IMAGE,NOM_LABEL,NOM_OPTIONS,NUM_ORDER,NOM_PAGE,NOM_PARAMS,NUM_PARENTID,COD_PRIVILEGE,FLG_ESTADO) values ('4',null,null,null,'Recientes',null,'5','acts/recent',null,'3','VISUALIZADOR','1');


Insert into BICE_GA.SEMA_SEMAPHORE (SEMA_ID,COR_COUNT,NOM_SEMAFORO) values ('1','1','cl.bice.gestionactas.ejb.entity.UserDocumentVisibilityBO');
Insert into BICE_GA.SEMA_SEMAPHORE (SEMA_ID,COR_COUNT,NOM_SEMAFORO) values ('2','1','cl.bice.gestionactas.ejb.entity.UserBO');
Insert into BICE_GA.SEMA_SEMAPHORE (SEMA_ID,COR_COUNT,NOM_SEMAFORO) values ('3','1','cl.bice.gestionactas.ejb.entity.EnterpriseBO');
Insert into BICE_GA.SEMA_SEMAPHORE (SEMA_ID,COR_COUNT,NOM_SEMAFORO) values ('4','1','cl.bice.gestionactas.ejb.entity.ActasBO');
Insert into BICE_GA.SEMA_SEMAPHORE (SEMA_ID,COR_COUNT,NOM_SEMAFORO) values ('5','1','cl.bice.gestionactas.ejb.entity.DocumentoBO');

Insert into BICE_GA.TIP_TIPOCORPORACION (TIP_ID,FLG_ESTADO,COD_NMONIC,NOM_TIPOCORPORACION) values ('1','1','bicecorp','BICECORP');
Insert into BICE_GA.TIP_TIPOCORPORACION (TIP_ID,FLG_ESTADO,COD_NMONIC,NOM_TIPOCORPORACION) values ('2','0','bancobice','Banco Bice');
Insert into BICE_GA.TIP_TIPOCORPORACION (TIP_ID,FLG_ESTADO,COD_NMONIC,NOM_TIPOCORPORACION) values ('3','0','biceinv','Bice Inversiones');
Insert into BICE_GA.TIP_TIPOCORPORACION (TIP_ID,FLG_ESTADO,COD_NMONIC,NOM_TIPOCORPORACION) values ('4','0','otras','Otras');

Insert into BICE_GA.TIPDOC_TIPODOCUMENTO (TIPDOC_ID,FLG_ESTADO,COD_NMONIC,NOM_TIPOACTA) values ('1','1','actacorporacion','Acta Directorio');
Insert into BICE_GA.TIPDOC_TIPODOCUMENTO (TIPDOC_ID,FLG_ESTADO,COD_NMONIC,NOM_TIPOACTA) values ('2','1','actaauditoria','Acta Auditoria');
Insert into BICE_GA.TIPDOC_TIPODOCUMENTO (TIPDOC_ID,FLG_ESTADO,COD_NMONIC,NOM_TIPOACTA) values ('3','1','otros','Otros Documentos');


Insert into BICE_GA.USR_USUARIO (USR_ID,NOM_NOMBRE,NOM_LOGINAD,RUT_USUARIO) values ('41',null,'View','view','2-7');
Insert into BICE_GA.USR_USUARIO (USR_ID,NOM_NOMBRE,NOM_LOGINAD,RUT_USUARIO) values ('30',null,'System','admin','3-9');