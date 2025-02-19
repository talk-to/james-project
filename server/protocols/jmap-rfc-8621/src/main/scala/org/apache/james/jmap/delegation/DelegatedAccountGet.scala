/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 * http://www.apache.org/licenses/LICENSE-2.0                   *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.apache.james.jmap.delegation

import org.apache.james.jmap.core.{AccountId, Properties}
import org.apache.james.jmap.core.Id.Id
import org.apache.james.jmap.method.WithAccountId
import eu.timepit.refined.auto._

object DelegatedAccountGet {
  val allProperties: Properties = Properties("id", "username")
  val idProperty: Properties = Properties("id")

  def propertiesFiltered(requestedProperties: Properties): Properties = idProperty ++ requestedProperties
}

case class DelegatedAccountGetRequest(accountId: AccountId,
                                      ids: Option[DelegateIds],
                                      properties: Option[Properties]) extends WithAccountId

case class DelegatedAccountNotFound(value: Set[UnparsedDelegateId])

object DelegatedAccountGetResult {
  def from(delegates: Seq[Delegate], requestIds: Option[Set[Id]]): DelegatedAccountGetResult =
    requestIds match {
      case None => DelegatedAccountGetResult(delegates)
      case Some(value) => DelegatedAccountGetResult(
        list = delegates.filter(delegate => value.contains(delegate.id.id)),
        notFound = DelegatedAccountNotFound(value.diff(delegates.map(_.id.id).toSet).map(UnparsedDelegateId)))
    }
}
case class DelegatedAccountGetResult(list: Seq[Delegate],
                                     notFound: DelegatedAccountNotFound = DelegatedAccountNotFound(Set())) {
  def asResponse(accountId: AccountId): DelegatedAccountGetResponse =
    DelegatedAccountGetResponse(accountId, list, notFound)

}

case class DelegatedAccountGetResponse(accountId: AccountId,
                                       list: Seq[Delegate],
                                       notFound: DelegatedAccountNotFound = DelegatedAccountNotFound(Set()))
