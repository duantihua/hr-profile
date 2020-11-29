/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.hr.profile.web.action

import java.time.Instant

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.context.Params
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Department
import org.openurp.code.edu.model.{Degree, EducationDegree}
import org.openurp.code.job.model.ProfessionalTitle
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.web.ProjectSupport
import org.openurp.hr.profile.model.TeacherProfile

class AdminAction extends RestfulAction[TeacherProfile] with ProjectSupport {

  override def indexSetting(): Unit = {
    put("departments", getDeparts)
    super.indexSetting()
  }

  override def simpleEntityName: String = {
    "profile"
  }

  override def editSetting(entity: TeacherProfile): Unit = {
    val teacher = entity.teacher
    put("teacher", teacher)
    val query = OqlBuilder.from(classOf[Department], "d")
    query.where("d.school=:school", teacher.school)
    query.where("d.endOn is null")
    query.orderBy("d.code")
    put("departs", entityDao.search(query))
    put("titles", getCodes(classOf[ProfessionalTitle]))
    put("degrees", getCodes(classOf[Degree]))
    put("eduDegrees", getCodes(classOf[EducationDegree]))
    super.editSetting(entity)
  }

  override def saveAndRedirect(profile: TeacherProfile): View = {
    profile.teacher.user.department = entityDao.get(classOf[Department], intId("user.department"))
    populate(profile.teacher, classOf[Teacher].getName, Params.sub("teacher"))
    entityDao.saveOrUpdate(profile.teacher.user, profile.teacher, profile)
    profile.updatedAt = Instant.now
    super.saveAndRedirect(profile)
  }

}
