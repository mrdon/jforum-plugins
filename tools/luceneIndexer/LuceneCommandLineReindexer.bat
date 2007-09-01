@echo off
set jf=..\..\
java -Djava.ext.dirs=%jf%\lib;%jf%\web-inf\lib; -cp ..\bin net.jforum.tools.search.LuceneCommandLineReindexer %*