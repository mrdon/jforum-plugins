<#macro littlePostPagination topicId postsPerPage totalReplies>
	[ <img src="${contextPath}/templates/${templateName}/images/icon_latest_reply.gif"> ${I18n.getMessage("goToPage")}: 

	<#assign totalPages = (totalReplies+1)/postsPerPage />
	<#if (((totalReplies+1)%postsPerPage) != 0)>
		<#assign totalPages = totalPages +1/>
	</#if>
	
	<#list 0 .. totalPages - 1 as page>
		<#assign start = postsPerPage * page/>
		<#assign nextPage = page + 1/>
		
		<#if ( totalPages?string != nextPage?string )>
				<#assign coma = ","/>
		<#else>
				<#assign coma = ""/>
		</#if>

		<a href="${contextPath}/posts/list/${start}/${topicId}.page">${nextPage}</a>${coma}
	</#list>

	]
</#macro>

<#macro doPagination action id=0>
	<#if (totalRecords > recordsPerPage)>
		<span class="nav"><b>
		${I18n.getMessage("goToPage")}

		<#if (thisPage > 0)>
			<#assign start = thisPage - recordsPerPage>
			<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id>0)>/${id}</#if>.page">${I18n.getMessage("previous")}</a>
		</#if>

		<#if (totalPages > 6)>
			<#list 0 .. 3 as page>
				<#assign start = recordsPerPage * page>
				<#assign nextPage = page + 1>

				<#if start == thisPage>
					${nextPage}
				<#else>
					<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id>0)>/${id}</#if>.page">${nextPage}</a>, 
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
					<a href="${contextPath}/${moduleName}/${action}<#if (id>0)>/${start}/${id}</#if>.page">${nextPage}</a>,
				</#if>
			</#list>

			<#assign start = recordsPerPage * totalPages>
			<#if start != thisPage>
				<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id>0)>/${id}</#if>.page">${totalPages + 1}</a>
			<#else>
				${totalPages + 1}
			</#if>
		<#else>
			<#list 0 .. totalPages - 1 as page>
				<#assign start = recordsPerPage * page>
				<#assign nextPage = page + 1>
				
				<#if (start != thisPage)>
					<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id>0)>/${id}</#if>.page">${nextPage}</a>, 
				<#else>
					${nextPage}
				</#if>
			</#list>

			<#if (totalPages * recordsPerPage != totalRecords)>
				<#assign start = recordsPerPage * totalPages>
				
				<#if start != thisPage>
					<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id>0)>/${id}</#if>.page">${totalPages + 1}</a> 
				<#else>
					${totalPages + 1}
				</#if>
			</#if>
		</#if>

		<#if thisPage != start>
			<#assign start = thisPage + recordsPerPage/>
			<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id>0)>/${id}</#if>.page">${I18n.getMessage("next")}</a>
		</#if>

		</b>
	</span>
	</#if>
</#macro>