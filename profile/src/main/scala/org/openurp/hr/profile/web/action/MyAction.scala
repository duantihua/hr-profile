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

import org.beangle.data.dao.OqlBuilder
import org.beangle.security.Securities
import org.beangle.webmvc.api.context.Params
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.edu.model.Teacher
import org.openurp.base.model.Department
import org.openurp.boot.edu.helper.ProjectSupport
import org.openurp.code.edu.model.{Degree, EducationDegree}
import org.openurp.code.job.model.ProfessionalTitle
import org.openurp.hr.base.model.TeacherProfile

import java.time.Instant

class MyAction extends RestfulAction[TeacherProfile] with ProjectSupport {

  override def index(): View = {
    getTeacher match {
      case Some(t) =>
        val builder = OqlBuilder.from(classOf[TeacherProfile], "tb")
        builder.where("tb.teacher=:teacher", t)
        val profiles = entityDao.search(builder)
        if (profiles.isEmpty) {
          redirect("editNew")
        } else {
          put("profile", profiles.head)
          forward("info")
        }
      case None =>
        forward("notteacher")
    }
  }

  override def editSetting(entity: TeacherProfile): Unit = {
    val teacher = getTeacher.get
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

  override def simpleEntityName: String = {
    "profile"
  }

  override def saveAndRedirect(profile: TeacherProfile): View = {
    profile.teacher = getTeacher.head
    populate(profile.teacher, classOf[Teacher].getName, Params.sub("teacher"))
    entityDao.saveOrUpdate(profile.teacher, profile)
    profile.updatedAt = Instant.now
    redirect("index", "info.save.success")
  }

  private def getTeacher: Option[Teacher] = {
    val tQuery = OqlBuilder.from(classOf[Teacher], "t")
    tQuery.where("t.user.code=:code", Securities.user)
    entityDao.search(tQuery).headOption
  }
}
