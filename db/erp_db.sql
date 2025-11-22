DROP DATABASE IF EXISTS erp_db;

CREATE DATABASE erp_db;

USE erp_db;

CREATE TABLE students(
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL, -- fetch from db/auth_db.users_auth table after creating entry
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE, -- fetch from db/auth_db.users_auth table after creating entry
    roll_no INT NOT NULL UNIQUE,
    program ENUM('B.Tech', 'M.Tech', 'PhD') NOT NULL,
    current_year INT NOT NULL
);

CREATE TABLE instructors(
    instructor_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL, -- fetch from db/auth_db.users_auth table after creating entry
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE, -- fetch from db/auth_db.users_auth table after creating entry
    department VARCHAR(10) NOT NULL
);

CREATE TABLE courses(
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    title VARCHAR(50) NOT NULL,
    credits INT NOT NULL
);

CREATE TABLE sections(
    section_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT UNIQUE NOT NULL , -- query courses table with course code
    instructor_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    day_time VARCHAR(100),
    room VARCHAR(10),
    capacity INT NOT NULL,
    semester VARCHAR(50) NOT NULL,
    current_year INT NOT NULL
);

CREATE TABLE enrollments(
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    section_id INT NOT NULL,
    status ENUM('Enrolled', 'Dropped') DEFAULT 'Enrolled'

);

CREATE TABLE grades(
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT NOT NULL,
    component VARCHAR(50) NOT NULL,
    score DOUBLE DEFAULT 0,
    final_grade DOUBLE DEFAULT 0
);

CREATE TABLE settings(
    setting_key VARCHAR(255) PRIMARY KEY,
    setting_value VARCHAR(255)
);

INSERT INTO settings (setting_key, setting_value) VALUES ('maintenance_on', 'off');
INSERT INTO settings (setting_key, setting_value) VALUES ('registration_deadline', '2025-12-31T23:59:59');
INSERT INTO settings (setting_key, setting_value) VALUES ('drop_deadline', '2025-12-31T23:59:59');

INSERT INTO courses (code, title, credits) VALUES ('OAF164', 'Oshyjx Azro Fpek ', 4);
INSERT INTO courses (code, title, credits) VALUES ('XGQ668', 'Xntaa Gcqzt Qszfn ', 2);
INSERT INTO courses (code, title, credits) VALUES ('JJK729', 'Jnht Jcxbuj Kvzp ', 4);
INSERT INTO courses (code, title, credits) VALUES ('DNB249', 'Ddkdwz Nmimml Bkle ', 4);
INSERT INTO courses (code, title, credits) VALUES ('ULB838', 'Uovi Lwfmrg Bzijjh ', 1);
INSERT INTO courses (code, title, credits) VALUES ('HIO370', 'Hnjhl Iespqc Ovbee ', 1);
INSERT INTO courses (code, title, credits) VALUES ('HFU175', 'Hiytq Ftcir Ubbadx ', 4);
INSERT INTO courses (code, title, credits) VALUES ('WJA596', 'Wvyva Jqnvxz Agsbe ', 2);
INSERT INTO courses (code, title, credits) VALUES ('UOV781', 'Ukbt Oarmf Vytcav ', 2);
INSERT INTO courses (code, title, credits) VALUES ('GQK707', 'Ggzuez Qrmi Kkshkh ', 1);
INSERT INTO courses (code, title, credits) VALUES ('EED235', 'Erpbft Ehgoyi Dbktuz ', 1);
INSERT INTO courses (code, title, credits) VALUES ('GHN649', 'Gjkurj Hrehnl Nisa ', 1);
INSERT INTO courses (code, title, credits) VALUES ('GFJ843', 'Gshufo Fcztbj Jpmks ', 2);
INSERT INTO courses (code, title, credits) VALUES ('KDR487', 'Klxxnz Dqje Rivtck ', 4);
INSERT INTO courses (code, title, credits) VALUES ('SWX152', 'Sjwok Wcazd Xtuo ', 1);
INSERT INTO courses (code, title, credits) VALUES ('DQU628', 'Dwxfni Qkgop Urct ', 2);
INSERT INTO courses (code, title, credits) VALUES ('MXY539', 'Mcydyi Xvcok Yjwgz ', 2);
INSERT INTO courses (code, title, credits) VALUES ('XPP196', 'Xbnjxr Petxq Pgnshl ', 2);
INSERT INTO courses (code, title, credits) VALUES ('COZ615', 'Cmfhya Okbyr Zbcyei ', 1);
INSERT INTO courses (code, title, credits) VALUES ('HPW899', 'Hvafak Pjyzb Wvma ', 2);
INSERT INTO courses (code, title, credits) VALUES ('GOS651', 'Gvauj Okatxr Slomlr ', 4);
INSERT INTO courses (code, title, credits) VALUES ('SFL365', 'Synw Frplud Lthyc ', 4);
INSERT INTO courses (code, title, credits) VALUES ('OJD162', 'Oxomjl Jnbezw Dsgta ', 1);
INSERT INTO courses (code, title, credits) VALUES ('NLM575', 'Nwijat Lzxbq Mqemi ', 1);
INSERT INTO courses (code, title, credits) VALUES ('EYO191', 'Eekd Ygdn Ouycer ', 1);
INSERT INTO courses (code, title, credits) VALUES ('KEX365', 'Kxbfps Ekzu Xbmti ', 2);
INSERT INTO courses (code, title, credits) VALUES ('HSJ118', 'Hyodb Scon Jpft ', 1);
INSERT INTO courses (code, title, credits) VALUES ('NGM827', 'Nmnfn Gmftk Mvszs ', 1);
INSERT INTO courses (code, title, credits) VALUES ('OTF913', 'Oouy Tlxbk Fdod ', 1);
INSERT INTO courses (code, title, credits) VALUES ('LBF331', 'Liwn Bjysuk Fujto ', 1);
INSERT INTO courses (code, title, credits) VALUES ('PBM226', 'Pycoh Bikn Meckm ', 2);
INSERT INTO courses (code, title, credits) VALUES ('FHS950', 'Fhdkll Hqhef Seqkvf ', 1);
INSERT INTO courses (code, title, credits) VALUES ('WZW303', 'Wlfk Zxvayz Wpuemc ', 1);
INSERT INTO courses (code, title, credits) VALUES ('LVL679', 'Lovne Vrjsxy Lznx ', 2);
INSERT INTO courses (code, title, credits) VALUES ('YTC248', 'Ypyhub Tzbww Cbzx ', 2);
INSERT INTO courses (code, title, credits) VALUES ('JQR454', 'Jwtdpp Qynrrm Rhby ', 4);
INSERT INTO courses (code, title, credits) VALUES ('BPT108', 'Bduqvu Pzmdea Tsboh ', 4);
INSERT INTO courses (code, title, credits) VALUES ('SUJ772', 'Srcniy Ufxz Jbzk ', 4);
INSERT INTO courses (code, title, credits) VALUES ('CQW908', 'Cezub Qbixk Wdpmwo ', 1);
INSERT INTO courses (code, title, credits) VALUES ('LBF712', 'Lhqoh Bglrvb Ftdp ', 4);
INSERT INTO courses (code, title, credits) VALUES ('OQB490', 'Oevd Qmbi Bzygw ', 2);
INSERT INTO courses (code, title, credits) VALUES ('PFW638', 'Pgqudm Fxmro Wjcga ', 2);
INSERT INTO courses (code, title, credits) VALUES ('RJW220', 'Rdcm Jbpkzy Wuhhuv ', 4);
INSERT INTO courses (code, title, credits) VALUES ('RJM240', 'Rhskvd Jmskb Myrcp ', 4);
INSERT INTO courses (code, title, credits) VALUES ('XBB863', 'Xquw Brrrex Bwibg ', 2);
INSERT INTO courses (code, title, credits) VALUES ('CEJ257', 'Chet Esfk Jpxkrc ', 1);
INSERT INTO courses (code, title, credits) VALUES ('TJP154', 'Tkzha Jxoskf Prvlsv ', 2);
INSERT INTO courses (code, title, credits) VALUES ('EPD303', 'Echtob Pmnsd Djta ', 4);
INSERT INTO courses (code, title, credits) VALUES ('SEI928', 'Spsxqx Ekrksb Iodz ', 2);
INSERT INTO courses (code, title, credits) VALUES ('JLW976', 'Jtte Lcikrv Wixc ', 1);
INSERT INTO courses (code, title, credits) VALUES ('ZIP822', 'Zetg Iqulwc Phbe ', 2);
INSERT INTO courses (code, title, credits) VALUES ('OJY995', 'Oebfjx Jlzc Yffdz ', 1);
INSERT INTO courses (code, title, credits) VALUES ('DOD322', 'Dkdq Oetdi Dvkhd ', 2);
INSERT INTO courses (code, title, credits) VALUES ('AXT113', 'Acpesj Xlob Thiarf ', 1);
INSERT INTO courses (code, title, credits) VALUES ('LGK751', 'Lttraq Gllxe Kkny ', 1);
INSERT INTO courses (code, title, credits) VALUES ('YJI428', 'Yadh Jxmm Ilbj ', 4);
INSERT INTO courses (code, title, credits) VALUES ('SXF670', 'Sibteg Xeho Fpqfz ', 2);
INSERT INTO courses (code, title, credits) VALUES ('LQY885', 'Lyqavo Qmqn Ygyt ', 2);
INSERT INTO courses (code, title, credits) VALUES ('CFL543', 'Cyole Fkphu Lqosoy ', 2);
INSERT INTO courses (code, title, credits) VALUES ('FJD802', 'Fuivle Jdyyua Dtciz ', 2);
INSERT INTO courses (code, title, credits) VALUES ('FDM503', 'Fide Dwcs Msatni ', 1);
INSERT INTO courses (code, title, credits) VALUES ('YIF697', 'Yrqlf Itbmys Feqevr ', 2);
INSERT INTO courses (code, title, credits) VALUES ('XSO230', 'Xsixjk Sripr Oxwt ', 2);
INSERT INTO courses (code, title, credits) VALUES ('EVX681', 'Efdfj Veysd Xtpu ', 4);
INSERT INTO courses (code, title, credits) VALUES ('LHZ479', 'Ldbn Hrcrsm Zmsl ', 1);
INSERT INTO courses (code, title, credits) VALUES ('OEK192', 'Obbbu Ebyg Kkkvz ', 4);
INSERT INTO courses (code, title, credits) VALUES ('ONQ930', 'Orvl Npujx Qssfz ', 2);
INSERT INTO courses (code, title, credits) VALUES ('HAA798', 'Hyvm Adaro Ahoe ', 2);
INSERT INTO courses (code, title, credits) VALUES ('NAN838', 'Ndzr Ahqp Nzcnw ', 4);
INSERT INTO courses (code, title, credits) VALUES ('HBB506', 'Hoxvml Bhwstl Bbagpf ', 4);
INSERT INTO courses (code, title, credits) VALUES ('RBV211', 'Rxso Brnwy Vxjvqr ', 2);
INSERT INTO courses (code, title, credits) VALUES ('HNY865', 'Hsxf Ndnix Yqslc ', 1);
INSERT INTO courses (code, title, credits) VALUES ('LUJ657', 'Lkydpl Upjses Jbwjy ', 2);
INSERT INTO courses (code, title, credits) VALUES ('OIF258', 'Okobso Iebh Fnorf ', 2);
INSERT INTO courses (code, title, credits) VALUES ('JEP241', 'Jhjvy Eieck Psswp ', 1);
INSERT INTO courses (code, title, credits) VALUES ('CXE112', 'Coaue Xjwyyg Ebzf ', 4);
INSERT INTO courses (code, title, credits) VALUES ('UCD134', 'Uwsl Cuhr Dyrjo ', 1);
INSERT INTO courses (code, title, credits) VALUES ('HJA188', 'Hckod Jvxurd Aiabb ', 1);
INSERT INTO courses (code, title, credits) VALUES ('CEM268', 'Cwvrb Ezeak Mmzeam ', 4);
INSERT INTO courses (code, title, credits) VALUES ('YSG544', 'Yvitgd Sdwxd Gyfjzd ', 2);
INSERT INTO courses (code, title, credits) VALUES ('UAD881', 'Updcdd Aidi Dazf ', 4);
INSERT INTO courses (code, title, credits) VALUES ('SJB280', 'Smqii Jickjz Bkfheu ', 4);
INSERT INTO courses (code, title, credits) VALUES ('QAR673', 'Qjyx Abmg Ruep ', 4);
INSERT INTO courses (code, title, credits) VALUES ('ZUS488', 'Znzkn Ueiyxo Sgjtc ', 4);
INSERT INTO courses (code, title, credits) VALUES ('TEO307', 'Trvcfk Enkhbb Otiwci ', 1);
INSERT INTO courses (code, title, credits) VALUES ('CTV273', 'Cjyilm Tnfpbi Vnil ', 2);
INSERT INTO courses (code, title, credits) VALUES ('EAS699', 'Ediaep Axcyi Sxfngg ', 2);
INSERT INTO courses (code, title, credits) VALUES ('BEC857', 'Btnkbm Emchxa Clbfe ', 4);
INSERT INTO courses (code, title, credits) VALUES ('BXF829', 'Bctnl Xajcm Flbjy ', 2);
INSERT INTO courses (code, title, credits) VALUES ('HAB670', 'Hsmu Akfj Bxklp ', 2);
INSERT INTO courses (code, title, credits) VALUES ('GVY415', 'Gjaxi Vtbk Yqksp ', 1);
INSERT INTO courses (code, title, credits) VALUES ('BON974', 'Byvuso Olmpco Newo ', 4);
INSERT INTO courses (code, title, credits) VALUES ('FXN756', 'Fkjp Xcuf Nksqql ', 2);
INSERT INTO courses (code, title, credits) VALUES ('WKV822', 'Wbhxx Kjjfs Vxetsf ', 2);
INSERT INTO courses (code, title, credits) VALUES ('WKO337', 'Wfnvlo Koyeqi Orwkc ', 1);
INSERT INTO courses (code, title, credits) VALUES ('VWD449', 'Vkpi Wkogb Dsdj ', 4);
INSERT INTO courses (code, title, credits) VALUES ('RDN784', 'Rlkqee Dktvg Nzeoxp ', 1);
INSERT INTO courses (code, title, credits) VALUES ('OFK759', 'Okwsys Fsxmwc Kzacse ', 2);
INSERT INTO courses (code, title, credits) VALUES ('KFT358', 'Kdxcip Fgww Tizh ', 1);
INSERT INTO courses (code, title, credits) VALUES ('IHT380', 'Ipln Hntsu Tphiat ', 2);

INSERT INTO students(user_id, name, username, roll_no, program, current_year) VALUES (3, 'Jack Baker', 'stu1', 2001000, 'B.Tech', 2023);
INSERT INTO students(user_id, name, username, roll_no, program, current_year) VALUES (4, 'Dexter Morgan', 'stu2', 2004000, 'M.Tech', 2025);

INSERT INTO instructors (user_id, name, username, department) VALUES (2, 'Hector Munday', 'inst1', 'ECE');


