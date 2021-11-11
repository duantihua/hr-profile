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
package org.openurp.hr.profile.web.ws

import org.beangle.commons.collection.Properties
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.annotation.{mapping, param}
import org.beangle.webmvc.entity.action.EntityAction
import org.openurp.hr.base.model.TeacherProfile

class TeacherWS extends EntityAction[TeacherProfile] {

  @mapping("{id}")
  def index(@param("id") id: String): Properties = {
    val builder = OqlBuilder.from(classOf[TeacherProfile], "tp")
    builder.where("tp.teacher.id=:teacherId", id.toLong)
    entityDao.search(builder).headOption match {
      case Some(e) =>
        val p = new Properties(e, "id", "intro", "teachingCareer", "harvest", "research", "titles", "contact")
        val t = new Properties(e.teacher)
        e.teacher.title foreach { title =>
          t.put("title", title.name)
        }
        e.teacher.educationDegree foreach { ed =>
          t.put("educationDegree", ed.name)
        }
        e.teacher.degree foreach { d =>
          t.put("degree", d.name)
        }
        val u = new Properties(e.teacher.user, "code", "name")
        u.put("department", e.teacher.department.name)
        t.put("user", u)
        p.put("teacher", t)
        p
      case None => new Properties()
    }
  }
}
