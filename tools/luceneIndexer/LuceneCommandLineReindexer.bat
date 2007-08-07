@echo off
set jf=f:\eclipse-projects\jforum
java -Djava.ext.dirs=%jf%\lib;%jf%\web-inf\lib; -cp %jf%\web-inf\classes net.jforum.search.LuceneCommandLineReindexer