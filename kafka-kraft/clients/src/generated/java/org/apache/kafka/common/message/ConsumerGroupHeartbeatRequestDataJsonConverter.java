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

package org.apache.kafka.common.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.kafka.common.Uuid;
import org.apache.kafka.common.protocol.MessageUtil;

import static org.apache.kafka.common.message.ConsumerGroupHeartbeatRequestData.*;

public class ConsumerGroupHeartbeatRequestDataJsonConverter {
    public static ConsumerGroupHeartbeatRequestData read(JsonNode _node, short _version) {
        ConsumerGroupHeartbeatRequestData _object = new ConsumerGroupHeartbeatRequestData();
        JsonNode _groupIdNode = _node.get("groupId");
        if (_groupIdNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'groupId', which is mandatory in version " + _version);
        } else {
            if (!_groupIdNode.isTextual()) {
                throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a string type, but got " + _node.getNodeType());
            }
            _object.groupId = _groupIdNode.asText();
        }
        JsonNode _memberIdNode = _node.get("memberId");
        if (_memberIdNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'memberId', which is mandatory in version " + _version);
        } else {
            if (!_memberIdNode.isTextual()) {
                throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a string type, but got " + _node.getNodeType());
            }
            _object.memberId = _memberIdNode.asText();
        }
        JsonNode _memberEpochNode = _node.get("memberEpoch");
        if (_memberEpochNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'memberEpoch', which is mandatory in version " + _version);
        } else {
            _object.memberEpoch = MessageUtil.jsonNodeToInt(_memberEpochNode, "ConsumerGroupHeartbeatRequestData");
        }
        JsonNode _instanceIdNode = _node.get("instanceId");
        if (_instanceIdNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'instanceId', which is mandatory in version " + _version);
        } else {
            if (_instanceIdNode.isNull()) {
                _object.instanceId = null;
            } else {
                if (!_instanceIdNode.isTextual()) {
                    throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a string type, but got " + _node.getNodeType());
                }
                _object.instanceId = _instanceIdNode.asText();
            }
        }
        JsonNode _rackIdNode = _node.get("rackId");
        if (_rackIdNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'rackId', which is mandatory in version " + _version);
        } else {
            if (_rackIdNode.isNull()) {
                _object.rackId = null;
            } else {
                if (!_rackIdNode.isTextual()) {
                    throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a string type, but got " + _node.getNodeType());
                }
                _object.rackId = _rackIdNode.asText();
            }
        }
        JsonNode _rebalanceTimeoutMsNode = _node.get("rebalanceTimeoutMs");
        if (_rebalanceTimeoutMsNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'rebalanceTimeoutMs', which is mandatory in version " + _version);
        } else {
            _object.rebalanceTimeoutMs = MessageUtil.jsonNodeToInt(_rebalanceTimeoutMsNode, "ConsumerGroupHeartbeatRequestData");
        }
        JsonNode _subscribedTopicNamesNode = _node.get("subscribedTopicNames");
        if (_subscribedTopicNamesNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'subscribedTopicNames', which is mandatory in version " + _version);
        } else {
            if (_subscribedTopicNamesNode.isNull()) {
                _object.subscribedTopicNames = null;
            } else {
                if (!_subscribedTopicNamesNode.isArray()) {
                    throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a JSON array, but got " + _node.getNodeType());
                }
                ArrayList<String> _collection = new ArrayList<String>(_subscribedTopicNamesNode.size());
                _object.subscribedTopicNames = _collection;
                for (JsonNode _element : _subscribedTopicNamesNode) {
                    if (!_element.isTextual()) {
                        throw new RuntimeException("ConsumerGroupHeartbeatRequestData element expected a string type, but got " + _node.getNodeType());
                    }
                    _collection.add(_element.asText());
                }
            }
        }
        JsonNode _subscribedTopicRegexNode = _node.get("subscribedTopicRegex");
        if (_subscribedTopicRegexNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'subscribedTopicRegex', which is mandatory in version " + _version);
        } else {
            if (_subscribedTopicRegexNode.isNull()) {
                _object.subscribedTopicRegex = null;
            } else {
                if (!_subscribedTopicRegexNode.isTextual()) {
                    throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a string type, but got " + _node.getNodeType());
                }
                _object.subscribedTopicRegex = _subscribedTopicRegexNode.asText();
            }
        }
        JsonNode _serverAssignorNode = _node.get("serverAssignor");
        if (_serverAssignorNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'serverAssignor', which is mandatory in version " + _version);
        } else {
            if (_serverAssignorNode.isNull()) {
                _object.serverAssignor = null;
            } else {
                if (!_serverAssignorNode.isTextual()) {
                    throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a string type, but got " + _node.getNodeType());
                }
                _object.serverAssignor = _serverAssignorNode.asText();
            }
        }
        JsonNode _clientAssignorsNode = _node.get("clientAssignors");
        if (_clientAssignorsNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'clientAssignors', which is mandatory in version " + _version);
        } else {
            if (_clientAssignorsNode.isNull()) {
                _object.clientAssignors = null;
            } else {
                if (!_clientAssignorsNode.isArray()) {
                    throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a JSON array, but got " + _node.getNodeType());
                }
                ArrayList<Assignor> _collection = new ArrayList<Assignor>(_clientAssignorsNode.size());
                _object.clientAssignors = _collection;
                for (JsonNode _element : _clientAssignorsNode) {
                    _collection.add(AssignorJsonConverter.read(_element, _version));
                }
            }
        }
        JsonNode _topicPartitionsNode = _node.get("topicPartitions");
        if (_topicPartitionsNode == null) {
            throw new RuntimeException("ConsumerGroupHeartbeatRequestData: unable to locate field 'topicPartitions', which is mandatory in version " + _version);
        } else {
            if (_topicPartitionsNode.isNull()) {
                _object.topicPartitions = null;
            } else {
                if (!_topicPartitionsNode.isArray()) {
                    throw new RuntimeException("ConsumerGroupHeartbeatRequestData expected a JSON array, but got " + _node.getNodeType());
                }
                ArrayList<TopicPartitions> _collection = new ArrayList<TopicPartitions>(_topicPartitionsNode.size());
                _object.topicPartitions = _collection;
                for (JsonNode _element : _topicPartitionsNode) {
                    _collection.add(TopicPartitionsJsonConverter.read(_element, _version));
                }
            }
        }
        return _object;
    }
    public static JsonNode write(ConsumerGroupHeartbeatRequestData _object, short _version, boolean _serializeRecords) {
        ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
        _node.set("groupId", new TextNode(_object.groupId));
        _node.set("memberId", new TextNode(_object.memberId));
        _node.set("memberEpoch", new IntNode(_object.memberEpoch));
        if (_object.instanceId == null) {
            _node.set("instanceId", NullNode.instance);
        } else {
            _node.set("instanceId", new TextNode(_object.instanceId));
        }
        if (_object.rackId == null) {
            _node.set("rackId", NullNode.instance);
        } else {
            _node.set("rackId", new TextNode(_object.rackId));
        }
        _node.set("rebalanceTimeoutMs", new IntNode(_object.rebalanceTimeoutMs));
        if (_object.subscribedTopicNames == null) {
            _node.set("subscribedTopicNames", NullNode.instance);
        } else {
            ArrayNode _subscribedTopicNamesArray = new ArrayNode(JsonNodeFactory.instance);
            for (String _element : _object.subscribedTopicNames) {
                _subscribedTopicNamesArray.add(new TextNode(_element));
            }
            _node.set("subscribedTopicNames", _subscribedTopicNamesArray);
        }
        if (_object.subscribedTopicRegex == null) {
            _node.set("subscribedTopicRegex", NullNode.instance);
        } else {
            _node.set("subscribedTopicRegex", new TextNode(_object.subscribedTopicRegex));
        }
        if (_object.serverAssignor == null) {
            _node.set("serverAssignor", NullNode.instance);
        } else {
            _node.set("serverAssignor", new TextNode(_object.serverAssignor));
        }
        if (_object.clientAssignors == null) {
            _node.set("clientAssignors", NullNode.instance);
        } else {
            ArrayNode _clientAssignorsArray = new ArrayNode(JsonNodeFactory.instance);
            for (Assignor _element : _object.clientAssignors) {
                _clientAssignorsArray.add(AssignorJsonConverter.write(_element, _version, _serializeRecords));
            }
            _node.set("clientAssignors", _clientAssignorsArray);
        }
        if (_object.topicPartitions == null) {
            _node.set("topicPartitions", NullNode.instance);
        } else {
            ArrayNode _topicPartitionsArray = new ArrayNode(JsonNodeFactory.instance);
            for (TopicPartitions _element : _object.topicPartitions) {
                _topicPartitionsArray.add(TopicPartitionsJsonConverter.write(_element, _version, _serializeRecords));
            }
            _node.set("topicPartitions", _topicPartitionsArray);
        }
        return _node;
    }
    public static JsonNode write(ConsumerGroupHeartbeatRequestData _object, short _version) {
        return write(_object, _version, true);
    }
    
    public static class AssignorJsonConverter {
        public static Assignor read(JsonNode _node, short _version) {
            Assignor _object = new Assignor();
            JsonNode _nameNode = _node.get("name");
            if (_nameNode == null) {
                throw new RuntimeException("Assignor: unable to locate field 'name', which is mandatory in version " + _version);
            } else {
                if (!_nameNode.isTextual()) {
                    throw new RuntimeException("Assignor expected a string type, but got " + _node.getNodeType());
                }
                _object.name = _nameNode.asText();
            }
            JsonNode _minimumVersionNode = _node.get("minimumVersion");
            if (_minimumVersionNode == null) {
                throw new RuntimeException("Assignor: unable to locate field 'minimumVersion', which is mandatory in version " + _version);
            } else {
                _object.minimumVersion = MessageUtil.jsonNodeToShort(_minimumVersionNode, "Assignor");
            }
            JsonNode _maximumVersionNode = _node.get("maximumVersion");
            if (_maximumVersionNode == null) {
                throw new RuntimeException("Assignor: unable to locate field 'maximumVersion', which is mandatory in version " + _version);
            } else {
                _object.maximumVersion = MessageUtil.jsonNodeToShort(_maximumVersionNode, "Assignor");
            }
            JsonNode _reasonNode = _node.get("reason");
            if (_reasonNode == null) {
                throw new RuntimeException("Assignor: unable to locate field 'reason', which is mandatory in version " + _version);
            } else {
                _object.reason = MessageUtil.jsonNodeToByte(_reasonNode, "Assignor");
            }
            JsonNode _metadataVersionNode = _node.get("metadataVersion");
            if (_metadataVersionNode == null) {
                throw new RuntimeException("Assignor: unable to locate field 'metadataVersion', which is mandatory in version " + _version);
            } else {
                _object.metadataVersion = MessageUtil.jsonNodeToShort(_metadataVersionNode, "Assignor");
            }
            JsonNode _metadataBytesNode = _node.get("metadataBytes");
            if (_metadataBytesNode == null) {
                throw new RuntimeException("Assignor: unable to locate field 'metadataBytes', which is mandatory in version " + _version);
            } else {
                _object.metadataBytes = MessageUtil.jsonNodeToBinary(_metadataBytesNode, "Assignor");
            }
            return _object;
        }
        public static JsonNode write(Assignor _object, short _version, boolean _serializeRecords) {
            ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
            _node.set("name", new TextNode(_object.name));
            _node.set("minimumVersion", new ShortNode(_object.minimumVersion));
            _node.set("maximumVersion", new ShortNode(_object.maximumVersion));
            _node.set("reason", new ShortNode(_object.reason));
            _node.set("metadataVersion", new ShortNode(_object.metadataVersion));
            _node.set("metadataBytes", new BinaryNode(Arrays.copyOf(_object.metadataBytes, _object.metadataBytes.length)));
            return _node;
        }
        public static JsonNode write(Assignor _object, short _version) {
            return write(_object, _version, true);
        }
    }
    
    public static class TopicPartitionsJsonConverter {
        public static TopicPartitions read(JsonNode _node, short _version) {
            TopicPartitions _object = new TopicPartitions();
            JsonNode _topicIdNode = _node.get("topicId");
            if (_topicIdNode == null) {
                throw new RuntimeException("TopicPartitions: unable to locate field 'topicId', which is mandatory in version " + _version);
            } else {
                if (!_topicIdNode.isTextual()) {
                    throw new RuntimeException("TopicPartitions expected a JSON string type, but got " + _node.getNodeType());
                }
                _object.topicId = Uuid.fromString(_topicIdNode.asText());
            }
            JsonNode _partitionsNode = _node.get("partitions");
            if (_partitionsNode == null) {
                throw new RuntimeException("TopicPartitions: unable to locate field 'partitions', which is mandatory in version " + _version);
            } else {
                if (!_partitionsNode.isArray()) {
                    throw new RuntimeException("TopicPartitions expected a JSON array, but got " + _node.getNodeType());
                }
                ArrayList<Integer> _collection = new ArrayList<Integer>(_partitionsNode.size());
                _object.partitions = _collection;
                for (JsonNode _element : _partitionsNode) {
                    _collection.add(MessageUtil.jsonNodeToInt(_element, "TopicPartitions element"));
                }
            }
            return _object;
        }
        public static JsonNode write(TopicPartitions _object, short _version, boolean _serializeRecords) {
            ObjectNode _node = new ObjectNode(JsonNodeFactory.instance);
            _node.set("topicId", new TextNode(_object.topicId.toString()));
            ArrayNode _partitionsArray = new ArrayNode(JsonNodeFactory.instance);
            for (Integer _element : _object.partitions) {
                _partitionsArray.add(new IntNode(_element));
            }
            _node.set("partitions", _partitionsArray);
            return _node;
        }
        public static JsonNode write(TopicPartitions _object, short _version) {
            return write(_object, _version, true);
        }
    }
}
