<#macro littlePostPagination topicId postsPerPage totalPages totalReplies>
	[ <img src="${contextPath}/templates/${templateName}/images/icon_latest_reply.gif"> ${I18n.getMessage("goToPage")}: 
	
	<#list 0 .. totalPages - 1 as page>
		<#assign start = postsPerPage * page/>
		<#assign nextPage = page + 1/>
		
		<a href="${contextPath}/posts/list/${start}/${topicId}.page">${nextPage}</a>, 
	</#list>
	
	<#if (postsPerPage * totalPages  != totalReplies)>
		<#assign start = postsPerPage * totalPages>
		<a href="${contextPath}/posts/list/${start}/${topicId}.page">${totalPages + 1}</a>
	</#if>
	
	 ]
	
</#macro>

<#macro doPagination action id>
	<#if (totalRecords > recordsPerPage)>
		<SPAN class=nav><B>
		${I18n.getMessage("goToPage")} 
		  
		<#if (thisPage > 0)>
			<#assign start = thisPage - recordsPerPage>
		  	 <a href="${contextPath}/${moduleName}/${action}/${start}/${id}.page">${I18n.getMessage("previous")}</a> 
		</#if>
		  
		<#if (totalPages > 6)>
			<#list 0 .. 3 as page>
				<#assign start = recordsPerPage * page>
				<#assign nextPage = page + 1>
				
				<#if start == thisPage>
					${nextPage}
				<#else>
					<a href="${contextPath}/${moduleName}/${action}/${start}/${id}.page">${nextPage}</a>, 
				</#if>
			</#list>
			
			 ... 

			<#assign startLastFrom = totalPages - 2>
			<#list startLastFrom .. totalPages - 1 as page>
				<#assign start = recordsPerPage * page >
				<#assign nextPage = page + 1>
				
				<#if start == thisPage>
					${nextPage}, 
				<#else>
					 <a href="${contextPath}/${moduleName}/${action}/${start}/${id}.page">${nextPage}</a>,
				</#if>
			</#list>

			<#assign start = recordsPerPage * totalPages>
			<#if start != thisPage>
				<a href="${contextPath}/${moduleName}/${action}/${start}/${id}.page">${totalPages + 1}</a>
			<#else>
				${totalPages + 1}
			</#if>
		<#else>
			<#list 0 .. totalPages - 1 as page>
				<#assign start = recordsPerPage * page>
				<#assign nextPage = page + 1>
				
				<#if (start != thisPage)>
					<a href="${contextPath}/${moduleName}/${action}/${start}/${id}.page">${nextPage}</a>, 
				<#else>
					${nextPage}
				</#if>
			</#list>
			
			<#if (totalPages * recordsPerPage != totalRecords)>
				<#assign start = recordsPerPage * totalPages>
				
				<#if start != thisPage>
					<a href="${contextPath}/${moduleName}/${action}/${start}/${id}.page">${totalPages + 1}</a> 
				<#else>
					${totalPages + 1}
				</#if>
			</#if>
		</#if>
		  
		<#if thisPage != start>
			<#assign start = thisPage + recordsPerPage/>
			<a href="${contextPath}/${moduleName}/${action}/${start}/${id}.page">${I18n.getMessage("next")}</a>
		</#if>
		  
		</B>
	</SPAN>
	<#else>
		 
	</#if>
</#macro>