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

<#macro doPagination action id=-1>
	<#if (totalRecords > recordsPerPage)>
		<span class="nav"><b>
		${I18n.getMessage("goToPage")}:
		
		<#assign NumberOfActiveLinks = 7>
		<#assign NumberOfSideLinks = 3>
		
		<#if (thisPage > 1)>
			<#assign start = (thisPage - 2)* recordsPerPage>
			<a href="${contextPath}/${moduleName}/${action}/0<#if (id > -1)>/${id}</#if>${extension}">(&lt;&lt;)</a>
			<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id > -1)>/${id}</#if>${extension}">(&lt;)</a>
		</#if>

		<#if (totalPages > NumberOfActiveLinks)>
		
			<#if (thisPage <= NumberOfSideLinks)>
				<#assign startPageAt = 1>
				<#assign stopPageAt = NumberOfActiveLinks>
			<#else>
				<#assign startPageAt = (thisPage - NumberOfSideLinks) >
				<#if (thisPage >= (totalPages - NumberOfSideLinks)) >
					<#assign startPageAt = (totalPages-(NumberOfActiveLinks - 1)) >
				</#if>
				
				<#assign stopPageAt = startPageAt + (NumberOfActiveLinks - 1) >
			</#if>

			<#if (startPageAt > 1) >
				...
			</#if>
						
			<#list startPageAt .. stopPageAt as page >
				<#assign start = recordsPerPage * (page-1) >

				<#if thisPage == page >
					${page}
				<#else>
					<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id > -1)>/${id}</#if>${extension}">${page}</a>
				</#if>
				<#if (page < totalPages) >,</#if>
			</#list>
			
			<#if (stopPageAt < totalPages) >
				...
			</#if>

		<#else>
			<#list 1 .. totalPages as page >
				<#assign start = recordsPerPage * (page-1) >
				
				<#if thisPage == page>
					${page}
				<#else>
					<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id > -1)>/${id}</#if>${extension}">${page}</a>
				</#if>
				<#if (page < totalPages) >,</#if>
			</#list>
		</#if>

		<#if thisPage < totalPages >
			<#assign start = thisPage * recordsPerPage/>
			<a href="${contextPath}/${moduleName}/${action}/${start}<#if (id > -1)>/${id}</#if>${extension}">(&gt;)</a>
			<a href="${contextPath}/${moduleName}/${action}/${(totalPages-1)*recordsPerPage}<#if (id > -1)>/${id}</#if>${extension}">(&gt;&gt;)</a>
		</#if>

		</b>
	</span>
	</#if>
</#macro>