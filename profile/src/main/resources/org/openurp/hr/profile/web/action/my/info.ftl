[#ftl/]
[@b.head/]
[@b.toolbar title="教师简介"/]
<table class="infoTable" width="100%">
	<tr>
		<td class="title" width="15%">工号:</td>
		<td class="content">${(profile.teacher.user.code)!}</td>
		<td class="title">姓名:</td>
		<td class="content"> ${(profile.teacher.user.name)!}</td>
	</tr>
	<tr>
    <td class="title">教学院系:</td>
    <td class="content">${(profile.teacher.department.name)!}</td>
		<td class="title">职称:</td>
		<td class="content">${(profile.teacher.title.name)!}</td>
	</tr>
	<tr>
		<td class="title">学历:</td>
		<td class="content">${(profile.teacher.educationDegree.name)!}</td>
		<td class="title">学位:</td>
		<td class="content">${(profile.teacher.degree.name)!}</td>
	</tr>
	<tr>
		<td class="title">个人简介:</td>
		<td class="content" colspan="3">${profile.intro!}</td>
	</tr>
	<tr>
		<td class="title">教学经历:</td>
		<td class="content" colspan="3">${profile.teachingCareer!}</td>
	</tr>
	<tr>
		<td class="title">研究方向:</td>
		<td class="content" colspan="3">${profile.research!}</td>
	</tr>
	<tr>
		<td class="title">科研成果:</td>
		<td class="content" colspan="3">${profile.harvest!}</td>
	</tr>
	<tr>
		<td class="title">学术兼职:</td>
		<td class="content" colspan="3">${profile.titles!}</td>
	</tr>
	<tr>
		<td class="title">联系信息:</td>
		<td class="content" colspan="3">${profile.contact!}</td>
	</tr>
</table>
<div style="text-align:center">
	[@b.a class="btn btn-default" href="!edit?id=" +profile.id role="button"]修改[/@]
</div>
[@b.foot/]
