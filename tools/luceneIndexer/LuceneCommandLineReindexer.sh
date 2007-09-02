jf=../../
java -Djava.ext.dirs=$jf/lib:$jf/web-inf/lib -cp ../bin:$jf/WEB-INF/classes net.jforum.tools.search.LuceneCommandLineReindexer $@