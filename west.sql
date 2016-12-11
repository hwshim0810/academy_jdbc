--------------------------------------------------------
--  파일이 생성됨 - 수요일-12월-07-2016   
--------------------------------------------------------
DROP TABLE "WEST"."CAR" cascade constraints;
DROP TABLE "WEST"."MEMBER" cascade constraints;
DROP TABLE "WEST"."RESERVE" cascade constraints;
DROP SEQUENCE "WEST"."SEQ_RES_RID";
--------------------------------------------------------
--  DDL for Table CAR
--------------------------------------------------------

  CREATE TABLE "WEST"."CAR" 
   (	"CID" VARCHAR2(15 BYTE), 
	"CNAME" VARCHAR2(20 BYTE), 
	"PRICE" NUMBER(10,0), 
	"RES" CHAR(1 BYTE), 
	"RESDATE" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table MEMBER
--------------------------------------------------------

  CREATE TABLE "WEST"."MEMBER" 
   (	"MID" VARCHAR2(15 BYTE), 
	"PWD" VARCHAR2(30 BYTE), 
	"MNAME" VARCHAR2(15 BYTE), 
	"LICENSE" VARCHAR2(30 BYTE), 
	"TEL" VARCHAR2(20 BYTE), 
	"ADDR" VARCHAR2(30 BYTE), 
	"AGE" NUMBER(3,0), 
	"EMAIL" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table RESERVE
--------------------------------------------------------

  CREATE TABLE "WEST"."RESERVE" 
   (	"RID" NUMBER, 
	"CID" VARCHAR2(15 BYTE), 
	"MID" VARCHAR2(15 BYTE), 
	"CNAME" VARCHAR2(20 BYTE), 
	"STARTDATE" DATE, 
	"ENDDATE" DATE, 
	"RESDATE" DATE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Sequence SEQ_RES_RID
--------------------------------------------------------

   CREATE SEQUENCE  "WEST"."SEQ_RES_RID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 141 CACHE 20 NOORDER  NOCYCLE ;
REM INSERTING into WEST.CAR
SET DEFINE OFF;
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('1001','Porche',100000,'Y',to_date('16/11/28','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('1002','Porche',100000,'Y',to_date('15/11/25','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('1003','Porche',100000,'N',to_date('14/10/15','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('1004','Porche',100000,'N',to_date('15/12/25','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('1005','Porche',100000,'N',to_date('15/11/08','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('2001','avante',30000,'Y',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('2002','avante',30000,'N',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('2003','avante',30000,'N',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('2004','avante',30000,'N',to_date('15/09/09','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('2005','avante',30000,'N',to_date('14/07/25','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('3001','morning',15000,'Y',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('3002','morning',15000,'N',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('3003','morning',15000,'N',to_date('16/01/02','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('3004','morning',15000,'N',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('3005','morning',15000,'N',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('4001','k5',35000,'Y',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('4002','k5',35000,'Y',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('4003','k5',35000,'Y',to_date('16/01/12','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('4004','k5',35000,'Y',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('4005','k5',35000,'N',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('5001','Jaguer',200000,'Y',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('5002','RangeRover',150000,'Y',to_date('16/12/07','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('5003','Lexus',80000,'Y',to_date('16/01/12','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('5004','Beatle',40000,'Y',to_date('15/02/22','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('5005','Genesis',60000,'N',to_date('16/05/30','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('8888','Bentley',300000,'Y',to_date('16/05/31','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('9999','Lamborghini',500000,'Y',to_date('16/03/31','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('3618','Lamborghini',500000,'Y',to_date('16/03/31','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('9009','Lamborghini',700000,'N',to_date('16/04/30','RR/MM/DD'));
Insert into WEST.CAR (CID,CNAME,PRICE,RES,RESDATE) values ('0307','Lamborghini',700000,'Y',to_date('16/04/30','RR/MM/DD'));
REM INSERTING into WEST.MEMBER
SET DEFINE OFF;
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('admin','admin','관리자','9999999','010-9999-9999','서울',100,'admin@admin.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer1','1234','cus1','1000001','010-1111-1111','서울',20,'cus1@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer2','1234','cus2','1000002','010-1111-1112','서울',20,'cus2@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer3','1234','cus3','1000003','010-1111-1113','서울',20,'cus3@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer4','1234','cus4','1000004','010-1111-1114','서울',20,'cus4@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer5','1234','cus5','1000005','010-1111-1115','서울',20,'cus5@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer6','1234','cus6','1000006','010-1111-1116','서울',20,'cus6@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer7','1234','cus7','1000007','010-1111-1117','서울',20,'cus7@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer8','1234','cus8','1000008','010-1111-1118','서울',20,'cus8@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer9','1234','cus9','1000009','010-1111-1119','서울',20,'cus9@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer10','1234','cus10','1000010','010-1111-1121','서울',20,'cus10@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer11','1234','cus11','1000011','010-1111-1122','서울',20,'cus11@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer12','1234','cus12','1000012','010-1111-1123','서울',20,'cus12@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer13','1234','cus13','1000013','010-1111-1124','서울',20,'cus13@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer14','1234','cus14','1000014','010-1111-1125','서울',20,'cus14@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer15','1234','cus15','1000015','010-1111-1126','서울',20,'cus15@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer16','1234','cus16','1000016','010-1111-1127','서울',20,'cus16@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer17','1234','cus17','1000017','010-1111-1128','서울',20,'cus17@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer18','1234','cus18','1000018','010-1111-1129','서울',20,'cus18@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('customer19','1234','cus19','1000019','010-1111-1130','서울',20,'cus19@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('kimc1','1234','김철수','1000020','010-1111-1131','서울',21,'cus20@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('young1','1234','김영희','1000021','010-1111-1132','서울',22,'cus21@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('ahnc1','1234','안철수','1000022','010-1111-1133','서울',23,'cus22@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('leej1','1234','이재명','1000023','010-1111-1134','서울',24,'cus23@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('moonj1','1234','문재인','1000024','010-1111-1135','서울',25,'cus24@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('jungh1','1234','이정현','1000025','010-1111-1136','서울',26,'cus25@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('shimh','1234','심현우','1000026','010-2222-3333','서울',29,'shim@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('yoohs','1234','유형선','1000027','010-3333-4444','서울',28,'yoo@cus.com');
Insert into WEST.MEMBER (MID,PWD,MNAME,LICENSE,TEL,ADDR,AGE,EMAIL) values ('leeyk','1234','이영광','1000028','010-4444-5555','서울',28,'lee@cus.com');
REM INSERTING into WEST.RESERVE
SET DEFINE OFF;
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (113,'1001','customer1','Porche',to_date('16/12/02','RR/MM/DD'),to_date('16/12/05','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (114,'1002','customer2','Porche',to_date('16/12/01','RR/MM/DD'),to_date('16/12/04','RR/MM/DD'),to_date('16/11/29','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (115,'2001','customer3','avante',to_date('16/12/01','RR/MM/DD'),to_date('16/12/03','RR/MM/DD'),to_date('16/11/28','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (116,'3001','customer4','morning',to_date('16/12/01','RR/MM/DD'),to_date('16/12/02','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (117,'4001','kimc1','K5',to_date('16/12/03','RR/MM/DD'),to_date('16/12/10','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (118,'4002','kimc1','K5',to_date('16/12/05','RR/MM/DD'),to_date('16/12/10','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (119,'4003','kimc1','K5',to_date('16/12/06','RR/MM/DD'),to_date('16/12/10','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (120,'5001','kimc1','Jaguer',to_date('16/12/01','RR/MM/DD'),to_date('16/12/10','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (121,'5002','young1','RangeRover',to_date('16/12/01','RR/MM/DD'),to_date('16/12/11','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (122,'4004','young1','K5',to_date('16/12/01','RR/MM/DD'),to_date('16/12/11','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (123,'5003','ahnc1','Lexus',to_date('16/12/02','RR/MM/DD'),to_date('16/12/31','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (124,'5004','leej1','Beatle',to_date('16/12/03','RR/MM/DD'),to_date('16/12/25','RR/MM/DD'),to_date('16/11/30','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (125,'3618','yoohs','Lamborghini',to_date('16/11/28','RR/MM/DD'),to_date('16/12/07','RR/MM/DD'),to_date('16/12/07','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (126,'9999','shimh','Lamborghini',to_date('16/12/01','RR/MM/DD'),to_date('16/12/10','RR/MM/DD'),to_date('16/11/20','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (127,'0307','leeyk','Lamborghini',to_date('16/12/01','RR/MM/DD'),to_date('16/12/15','RR/MM/DD'),to_date('16/11/28','RR/MM/DD'));
Insert into WEST.RESERVE (RID,CID,MID,CNAME,STARTDATE,ENDDATE,RESDATE) values (128,'8888','leeyk','Bentley',to_date('16/12/01','RR/MM/DD'),to_date('16/12/31','RR/MM/DD'),to_date('16/11/25','RR/MM/DD'));
--------------------------------------------------------
--  DDL for Index SYS_C0011407
--------------------------------------------------------

  CREATE UNIQUE INDEX "WEST"."SYS_C0011407" ON "WEST"."CAR" ("CID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0011414
--------------------------------------------------------

  CREATE UNIQUE INDEX "WEST"."SYS_C0011414" ON "WEST"."MEMBER" ("MID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index SYS_C0011419
--------------------------------------------------------

  CREATE UNIQUE INDEX "WEST"."SYS_C0011419" ON "WEST"."RESERVE" ("RID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table CAR
--------------------------------------------------------

  ALTER TABLE "WEST"."CAR" ADD CONSTRAINT "CAR_RES_CHECK" CHECK (res in('Y','N')) ENABLE;
 
  ALTER TABLE "WEST"."CAR" MODIFY ("CNAME" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."CAR" MODIFY ("PRICE" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."CAR" MODIFY ("RES" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."CAR" ADD PRIMARY KEY ("CID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table MEMBER
--------------------------------------------------------

  ALTER TABLE "WEST"."MEMBER" MODIFY ("PWD" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."MEMBER" MODIFY ("MNAME" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."MEMBER" MODIFY ("LICENSE" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."MEMBER" MODIFY ("TEL" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."MEMBER" MODIFY ("ADDR" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."MEMBER" MODIFY ("AGE" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."MEMBER" ADD PRIMARY KEY ("MID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table RESERVE
--------------------------------------------------------

  ALTER TABLE "WEST"."RESERVE" MODIFY ("CNAME" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."RESERVE" MODIFY ("STARTDATE" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."RESERVE" MODIFY ("ENDDATE" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."RESERVE" MODIFY ("RESDATE" NOT NULL ENABLE);
 
  ALTER TABLE "WEST"."RESERVE" ADD PRIMARY KEY ("RID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESERVE
--------------------------------------------------------

  ALTER TABLE "WEST"."RESERVE" ADD FOREIGN KEY ("CID")
	  REFERENCES "WEST"."CAR" ("CID") ENABLE;
 
  ALTER TABLE "WEST"."RESERVE" ADD FOREIGN KEY ("MID")
	  REFERENCES "WEST"."MEMBER" ("MID") ENABLE;
