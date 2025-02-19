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

package org.apache.james.mailbox.jpa.openjpa;

import java.time.Clock;

import javax.mail.Flags;

import org.apache.james.events.EventBus;
import org.apache.james.mailbox.MailboxPathLocker;
import org.apache.james.mailbox.MailboxSession;
import org.apache.james.mailbox.model.Mailbox;
import org.apache.james.mailbox.model.MessageId;
import org.apache.james.mailbox.quota.QuotaManager;
import org.apache.james.mailbox.quota.QuotaRootResolver;
import org.apache.james.mailbox.store.BatchSizes;
import org.apache.james.mailbox.store.MailboxSessionMapperFactory;
import org.apache.james.mailbox.store.MessageStorer;
import org.apache.james.mailbox.store.PreDeletionHooks;
import org.apache.james.mailbox.store.StoreMailboxManager;
import org.apache.james.mailbox.store.StoreMessageManager;
import org.apache.james.mailbox.store.StoreRightManager;
import org.apache.james.mailbox.store.mail.ThreadIdGuessingAlgorithm;
import org.apache.james.mailbox.store.search.MessageSearchIndex;

/**
 * OpenJPA implementation of Mailbox
 */
public class OpenJPAMessageManager extends StoreMessageManager {

    public OpenJPAMessageManager(MailboxSessionMapperFactory mapperFactory,
                                 MessageSearchIndex index, EventBus eventBus,
                                 MailboxPathLocker locker, Mailbox mailbox,
                                 QuotaManager quotaManager, QuotaRootResolver quotaRootResolver,
                                 MessageId.Factory messageIdFactory, BatchSizes batchSizes,
                                 StoreRightManager storeRightManager, ThreadIdGuessingAlgorithm threadIdGuessingAlgorithm, Clock clock) {
        super(StoreMailboxManager.DEFAULT_NO_MESSAGE_CAPABILITIES, mapperFactory, index, eventBus, locker, mailbox,
            quotaManager, quotaRootResolver, batchSizes, storeRightManager, PreDeletionHooks.NO_PRE_DELETION_HOOK,
            new MessageStorer.WithoutAttachment(mapperFactory, messageIdFactory, new OpenJPAMessageFactory(OpenJPAMessageFactory.AdvancedFeature.None), threadIdGuessingAlgorithm, clock));
    }

    /**
     * Support user flags
     */
    @Override
    public Flags getPermanentFlags(MailboxSession session) {
        Flags flags =  super.getPermanentFlags(session);
        flags.add(Flags.Flag.USER);
        return flags;
    }
}
