/*
 Author : Jin Rubin
 Server Type    : MySQL
 File Encoding         : utf-8
 Date: 05/25/2018 18:03:40 PM
*/

drop database if exists gravity2;
create database gravity2;
Use gravity2;

SET GLOBAL event_scheduler = ON;
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;
SET @disable_trigger = NULL;
SET SQL_SAFE_UPDATES=0;

-- ----------------------------
--  Table structure for `hd`
-- ----------------------------
DROP TABLE IF EXISTS `hd`;
CREATE TABLE `hd` (
  `hdid` int(11),
  `hdm` char(40),
  `stime` datetime,
  `etime` datetime,
  `place` varchar(255),
  `shdjj` char(80),
  `hdjj` varchar(1000),
  `pic` varchar(255),
  `classify` varchar(255),
  `timeflag` varchar(255),
  `maxpeo` int(11),
  `delflag` varchar(255) DEFAULT '', 
  `delday` int(11) DEFAULT 0,
  `crashflag` varchar(255) DEFAULT '可兼报',
  PRIMARY KEY (`hdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




-- ----------------------------
--  Table structure for `hy`
-- ----------------------------
DROP TABLE IF EXISTS `hy`;
CREATE TABLE `hy` (
  `userid` int(11) NOT NULL,
  `hyid` int(11) NOT NULL,
  `flag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userid`,`hyid`),
  KEY `userid` (`userid`) USING BTREE,
  CONSTRAINT `hyfuserid` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hyfhyid` FOREIGN KEY (`hyid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` int(11),
  `headpic` varchar(255),
  `nname` char(30),
  `name` varchar(255),
  `email` char(50),
  `adress` varchar(255),
  `job` char(30),
  `username` varchar(30) not null unique,
  `password` varchar(30) not null,
  `area` varchar(255),
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- -----------------------------
--  Table structure for `friend_recommend`
-- ------------------------------
DROP TABLE IF EXISTS `friend_recommend`;
CREATE TABLE `friend_recommend` (
`userid` int(11) not null,
`hyid`  int(11) not null,
PRIMARY KEY (`userid`,`hyid`),
CONSTRAINT `fuserid` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT `fhyid` FOREIGN KEY (`hyid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT charset=utf8;


-- ----------------------------
--  Table structure for `usercreate`
-- ----------------------------
DROP TABLE IF EXISTS `usercreate`;
CREATE TABLE `usercreate` (
  `userid` int(11) NOT NULL,
  `hdid` int(11) NOT NULL,
  PRIMARY KEY (`userid`,`hdid`),
  CONSTRAINT `cfhdid` FOREIGN KEY (`hdid`) REFERENCES `hd` (`hdid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cfuserid` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
--  Table structure for `userjoin`
-- ----------------------------
DROP TABLE IF EXISTS `userjoin`;
CREATE TABLE `userjoin` (
  `userid` int(11) NOT NULL,
  `hdid` int(11) NOT NULL,
  `flag` varchar(255) DEFAULT '待审',
  PRIMARY KEY (`userid`,`hdid`),
  CONSTRAINT `jfhdid` FOREIGN KEY (`hdid`) REFERENCES `hd` (`hdid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `jfuserid` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `userlike`
-- ----------------------------
DROP TABLE IF EXISTS `userlike`;
CREATE TABLE `userlike` (
  `userid` int(11) NOT NULL,
  `hdid` int(11) NOT NULL,
  PRIMARY KEY (`userid`,`hdid`),
  KEY `hdid` (`hdid`) USING BTREE,
  CONSTRAINT `lfhdid` FOREIGN KEY (`hdid`) REFERENCES `hd` (`hdid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lfuserid` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  View structure for `hdv`
-- ----------------------------
DROP VIEW IF EXISTS `hdv`;
CREATE VIEW `hdv` 
AS select *
from `hd` order by `hd`.`etime` desc;

-- ----------------------------
--  View structure for `hyv`
-- ----------------------------
DROP VIEW IF EXISTS `hyv`;
CREATE VIEW `hyv` 
AS select `hy`.`userid` AS `userid`,`hy`.`hyid` AS `hyid`,`hy`.`flag` AS `flag` from `hy` union select `hy`.`hyid` AS `userid`,`hy`.`userid` AS `hyid`,`hy`.`flag` AS `flag`
from `hy` 
order by `userid`,`hyid`;


-- ----------------------------
--  View structure for `hylikev`
-- ----------------------------
DROP VIEW IF EXISTS `hylikev`;
CREATE VIEW `hylikev` AS select `hyv`.`userid` AS `userid`,`hyv`.`hyid` AS `hyid`,`userlike`.`hdid` AS `hdidlike` 
from (`hyv` join `userlike`) 
where (`hyv`.`hyid` = `userlike`.`userid`) 
order by `hyv`.`userid`,`hyv`.`hyid`,`userlike`.`hdid`;


-- ----------------------------
--  View structure for `hyjoinv`
-- ----------------------------
DROP VIEW IF EXISTS `hyjoinv`;
CREATE VIEW `hyjoinv` AS select `hyv`.`userid` AS `userid`,`hyv`.`hyid` AS `hyid`,`userjoin`.`hdid` AS `hdidjoin`
from (`hyv` join `userjoin`) 
where (`hyv`.`hyid` = `userjoin`.`userid`) 
order by `hyv`.`userid`,`hyv`.`hyid`,`userjoin`.`hdid`;

-- ----------------------------
--  View structure for `userhdv`
-- ----------------------------
DROP VIEW IF EXISTS `userhdv`;
create view userhdv as
select userid,hdid
from user,hd
order by userid asc,hdid asc;

-- ----------------------------
--  View structure for `hdplusv`
-- ----------------------------
DROP VIEW IF EXISTS `hdplusv`;
create view hdplusv as
select addflag.userid as userid,hd.hdid as hdid,hdm,stime,etime,place,shdjj,hdjj,pic,classify,timeflag,maxpeo,delflag,delday,crashflag,
addflag,jointemp,liketemp,joinnum,likenum,hyjoinnum,hylikenum,fqr
from hd,
(select userhdv.userid,userhdv.hdid,(case when flag is null then '未报名' else flag end)as'addflag' from userjoin
right join userhdv on userhdv.userid=userjoin.userid and userhdv.hdid=userjoin.hdid) as addflag,

(select userhdv.userid,userhdv.hdid,count(userjoin.userid) as'jointemp' from userjoin
right join userhdv on userhdv.userid=userjoin.userid and userhdv.hdid=userjoin.hdid
group by userhdv.userid,userhdv.hdid) as jointemp,

(select userhdv.userid,userhdv.hdid,count(userlike.userid) as'liketemp' from userlike
right join userhdv on userhdv.userid=userlike.userid and userhdv.hdid=userlike.hdid
group by userhdv.userid,userhdv.hdid) as liketemp,

(select userhdv.hdid,count(userjoin.userid) as'joinnum' from userjoin
right join userhdv on userhdv.userid=userjoin.userid and userhdv.hdid=userjoin.hdid
group by userhdv.hdid) as joinnum,

(select userhdv.hdid,count(userlike.userid) as'likenum' from userlike
right join userhdv on userhdv.userid=userlike.userid and userhdv.hdid=userlike.hdid
group by userhdv.hdid) as likenum,

(select userhdv.userid,userhdv.hdid,count(hyjoinv.hdidjoin) as'hyjoinnum' from hyjoinv
right join userhdv on userhdv.userid=hyjoinv.userid and userhdv.hdid=hyjoinv.hdidjoin
group by userhdv.userid,userhdv.hdid) as hyjoinnum,

(select userhdv.userid,userhdv.hdid,count(hylikev.hdidlike) as'hylikenum' from hylikev
right join userhdv on userhdv.userid=hylikev.userid and userhdv.hdid=hylikev.hdidlike
group by userhdv.userid,userhdv.hdid) as hylikenum,

(select usercreate.hdid,name as'fqr' from user,usercreate
where user.userid=usercreate.userid) as fqr

where hd.hdid=addflag.hdid
and hd.hdid=jointemp.hdid and addflag.userid=jointemp.userid
and hd.hdid=liketemp.hdid and addflag.userid=liketemp.userid
and hd.hdid=joinnum.hdid
and hd.hdid=likenum.hdid
and hd.hdid=hyjoinnum.hdid and addflag.userid=hyjoinnum.userid
and hd.hdid=hylikenum.hdid and addflag.userid=hylikenum.userid
and hd.hdid=fqr.hdid
order by etime desc;


-- ----------------------------
--  View structure for `hdplusnoidv`
-- ----------------------------
DROP VIEW IF EXISTS `hdplusnoidv`;
create view hdplusnoidv as
select hd.hdid as hdid,hdm,stime,etime,place,shdjj,hdjj,pic,classify,timeflag,maxpeo,delflag,delday,crashflag,
joinnum,likenum,fqr
from hd,

(select userhdv.hdid,count(userjoin.userid) as'joinnum' from userjoin
right join userhdv on userhdv.userid=userjoin.userid and userhdv.hdid=userjoin.hdid
group by userhdv.hdid) as joinnum,

(select userhdv.hdid,count(userlike.userid) as'likenum' from userlike
right join userhdv on userhdv.userid=userlike.userid and userhdv.hdid=userlike.hdid
group by userhdv.hdid) as likenum,

(select usercreate.hdid,name as'fqr' from user,usercreate
where user.userid=usercreate.userid) as fqr

where hd.hdid=joinnum.hdid
and hd.hdid=likenum.hdid
and hd.hdid=fqr.hdid
order by etime desc;

-- ----------------------------
--  View structure for `activity_recommend`
-- ----------------------------
DROP VIEW IF EXISTS `activity_recommend`;
create view activity_recommend as
select DISTINCT rec.userid,friendjoin.hdid from friend_recommend as rec,userjoin as friendjoin
where hyid=friendjoin.userid
and (rec.userid,friendjoin.hdid) not in (select userid,hdid from userjoin)
order by userid asc,hdid asc;

-- ----------------------------
--  View structure for `userjoinlikev`
-- ----------------------------
DROP VIEW IF EXISTS `userjoinlikev`;
create view userjoinlikev as
select userid,hdid from userjoin
union
select userid,hdid from userlike
order by userid asc,hdid asc;



SET FOREIGN_KEY_CHECKS = 1;

DELIMITER &&

create procedure update_timeflag_proc()
begin 
set @timenow=NOW();
update hd set hd.timeflag='预热' where @timenow<hd.stime;
update hd set hd.timeflag='进行中' where @timenow>hd.stime and @timenow<hd.etime;
update hd set hd.timeflag='已结束' where @timenow>hd.etime;
end &&
 

create procedure delete_expired_hd_proc()
begin
delete from hd where datediff(now(),hd.etime)>31;
end &&

CREATE procedure update_day_proc()
begin 
update hd set delday = delday +1 where delflag='已删除';
end  &&


create procedure clear_memory_proc()
begin
delete from hd where delday > 30; 
end &&

DELIMITER ;

create event update_timeflag_event
on schedule every 1 minute
do call update_timeflag_proc();

create event delete_expired_hd_event
on schedule every 1 day
do call delete_expired_hd_proc();

create event update_day_event
on schedule every 1 day
do call update_day_proc();

create event clear_memory_event
on schedule every 1 day 
do call clear_memory_proc();