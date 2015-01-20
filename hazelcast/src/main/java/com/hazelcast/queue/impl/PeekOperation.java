/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.queue.impl;

import com.hazelcast.monitor.impl.LocalQueueStatsImpl;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

/**
 * Peek operation for Queue.
 */
public final class PeekOperation extends QueueOperation implements IdentifiedDataSerializable {

    public PeekOperation() {
    }

    public PeekOperation(final String name) {
        super(name);
    }

    @Override
    public void run() {
        QueueItem item = getOrCreateContainer().peek();
        response = item != null ? item.getData() : null;
    }

    @Override
    public void afterRun() throws Exception {
        LocalQueueStatsImpl localQueueStatsImpl = getQueueService().getLocalQueueStatsImpl(name);
        localQueueStatsImpl.incrementOtherOperations();
    }

    @Override
    public int getFactoryId() {
        return QueueDataSerializerHook.F_ID;
    }

    @Override
    public int getId() {
        return QueueDataSerializerHook.PEEK;
    }
}