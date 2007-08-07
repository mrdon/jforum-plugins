@echo off
set jf=..\..\
java -Djava.ext.dirs=%jf%\lib;%jf%\web-inf\lib; -cp %jf%\web-inf\classes net.jforum.search.LuceneCommandLineReindexer %*