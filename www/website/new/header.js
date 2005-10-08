// header.js
function SymError(){return true;}
function SymWinOpen(url, name, attributes){return (new Object());}
function liO(idN){document.getElementsByTagName('LI')[idN].style.background = "url(img/tabs.gif) no-repeat 100% -400px";}
function liU(idN){document.getElementsByTagName('LI')[idN].style.background = "url(img/tabs.gif) no-repeat 100% -600px";}
function nav(idN){d=document.getElementsByTagName('DIV')[idN]; if (d.style.display=="block") { d.style.display="none"; } else { d.style.display="block"; }}
var SymRealWinOpen = window.open;
window.open = SymWinOpen;
window.onerror = SymError;