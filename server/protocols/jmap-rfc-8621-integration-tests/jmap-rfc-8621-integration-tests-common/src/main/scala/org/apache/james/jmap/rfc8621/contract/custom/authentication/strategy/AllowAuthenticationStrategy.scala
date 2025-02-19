/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.apache.james.jmap.rfc8621.contract.custom.authentication.strategy

import com.google.common.collect.ImmutableMap
import javax.inject.Inject
import org.apache.james.jmap.http.{AuthenticationChallenge, AuthenticationScheme, AuthenticationStrategy}
import org.apache.james.jmap.rfc8621.contract.Fixture
import org.apache.james.mailbox.{MailboxManager, MailboxSession}
import reactor.core.publisher.Mono
import reactor.core.scala.publisher.SMono
import reactor.netty.http.server.HttpServerRequest

case class AllowAuthenticationStrategy @Inject() (mailboxManager: MailboxManager) extends AuthenticationStrategy {
  override def createMailboxSession(httpRequest: HttpServerRequest): Mono[MailboxSession] =
    SMono.fromCallable(() => mailboxManager.login(Fixture.BOB))
      .asJava()

  override def correspondingChallenge(): AuthenticationChallenge =
    AuthenticationChallenge.of(AuthenticationScheme.of("Allow Authentication Strategy"), ImmutableMap.of)
}
