<#macro littlePostPagination topicId postsPerPage totalReplies>
	[ <img src="${contextPath}/templates/${templateName}/images/icon_latest_reply.gif"> ${I18n.getMessage("goToPage")}: 

	<#assign totalPostPages = ((totalReplies+1)/postsPerPage)?int />
	<#if (((totalReplies+1)%postsPerPage) > 0)>
		<#assign totalPostPages = (totalPostPages +1)/>
	</#if>

	<#list 1 .. totalPostPages as page>
		<#assign start = postsPerPage * (page - 1)/>

		<a href="${contextPath}/posts/list/${start}/${topicId}${extension}">${page}</a>	
		<#if (page < totalPostPages) >,</#if>
	</#list>

	]
</#macro>

<#macro doPagination action id formName="fp">
	<#if (totalRecords > recordsPerPage)>
		<form name="${formName}">
		<span class="gensmall">${I18n.getMessage("goToPage")}:</span> 
		<select name="p">
			<#list 1 .. totalPages as page >
				<#assign start = recordsPerPage * (page - 1)/>
				<option value="${start}" <#if thisPage == page>selected</#if>>${page}</option>
			</#list>
		</select>
		
		<#if (id > -1)>
			<#assign idAux = " + '/" + id/>
		<#else>	
			<#assign idAux = " + '"/>
		</#if>

		&nbsp;<input type="button" value="${I18n.getMessage("ForumIndex.goToGo")}" class="mainoption" onClick="document.location = '${JForumContext.encodeURL("/${moduleName}/${action}/' + document.${formName}.p[document.${formName}.p.selectedIndex].value ${idAux}")}';">
		</form>
	</#if>
</#macro>