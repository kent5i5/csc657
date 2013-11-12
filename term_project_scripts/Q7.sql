SELECT tpid FROM TaggedPhoto P 
JOIN usertable U ON P.userid = U.userid  
WHERE uname = 'kent'