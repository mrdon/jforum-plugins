<#-- ********************************************** -->
<#-- Replace the @ and "." chars with another thing -->
<#-- ********************************************** -->
<#macro convertEmail email>
	<#assign e = email.replaceAll("@", " [at] ")/>
	<#assign e = e.replaceAll("\\.", " [dot] ")/>
	${e}
</#macro>