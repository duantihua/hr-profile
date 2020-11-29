[#ftl]
[@b.head/]
[@b.toolbar title="教师简介维护"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="profileSearchForm" action="!search" target="profileList" title="ui.searchForm" theme="search"]
      [@b.textfields names="profile.teacher.user.code;工号"/]
      [@b.textfields names="profile.teacher.user.name;姓名"/]
      [@b.select name="profile.teacher.user.department.id" label="部门" items=departments empty="..." style="width:100px"/]
      <input type="hidden" name="orderBy" value="profile.teacher.user.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="profileList" href="!search?orderBy=profile.teacher.user.code"/]
    </div>
  </div>
[@b.foot/]
