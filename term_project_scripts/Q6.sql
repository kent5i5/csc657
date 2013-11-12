SELECT * FROM TaggedPhoto P 
JOIN Tag T ON P.tpid = T.tpid 
JOIN usertable U ON P.userid = U.userid 
WHERE  P.tpid = 002
