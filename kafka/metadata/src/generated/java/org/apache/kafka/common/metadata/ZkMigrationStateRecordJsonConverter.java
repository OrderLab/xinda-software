/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// THIS CODE IS AUTOMATICALLY GENERATED.  DO NOT EDIT.

package org.apache.kafka.common.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ShortNode;
import org.apache.kafka.common.protocol.MessageUtil;

import static org.apache.kafka.common.metadata.ZkMigrationStateRecord.*;

public class ZkMigrationStateRecordJsonConverter {
    public static ZkMigrationStateRecord read(JsonNode _node, short _version) {
        ZkMigrationStateRecord _object = new ZkMigrationStateRecord();
        JsonNode _zkMigrationStateNode = _node.get("zkMigrationState");
        if (_zkMigrationStateNode == null) {
            throw new RuntimeException("ZkMigrationStateRecord: unable to locate field 'zkMigrationState', which is mandatory in version " + _version);
        } else {
            _object.zkMigrationState = MessageUtil.jsonNodeToByte(_zkMigrationStateNode, "ZkMigrationStateRecord");
        }
        return _object;
    }
    public static JsonNode write(ZkMigrationStateRecord _object, short _version, boolean _serializeRecords) {
        ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
        _node.set("zkMigrationState", new ShortNode(_object.zkMigrationState));
        return _node;
    }
    public static JsonNode write(ZkMigrationStateRecord _object, short _version) {
        return write(_object, _version, true);
    }
}
