/*
 *  Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.siddhi.extension.io.grpc.utils;

import com.google.protobuf.Descriptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Util methods to generate protobuf message.
 *
 * @since 1.0.0
 */
public class MessageUtils {
    private static final Logger LOG = LoggerFactory.getLogger(MessageUtils.class);
    private static final String UNKNOWN_ERROR = "Unknown Error";

    /**
     * Returns protobuf message corresponding to the B7a message.
     *
     * @param payload B7a message.
     * @param outputType    protobuf message type.
     * @return generated protobuf message.
     */
    public static Message generateProtoMessage(String payload, Descriptors.Descriptor outputType) {
        Message.Builder responseBuilder = Message.newBuilder(outputType.getName());
        for (Descriptors.FieldDescriptor fieldDescriptor : outputType.getFields()) {
            String fieldName = fieldDescriptor.getName();
            responseBuilder.addField(fieldName, payload);
        }
        return responseBuilder.build();
    }

    /**
     * Returns wire type corresponding to the field descriptor type.
     * <p>
     * 0 -> int32, int64, uint32, uint64, sint32, sint64, bool, enum
     * 1 -> fixed64, sfixed64, double
     * 2 -> string, bytes, embedded messages, packed repeated fields
     * 5 -> fixed32, sfixed32, float
     *
     * @param fieldType field descriptor type
     * @return wire type
     */
    static int getFieldWireType(Descriptors.FieldDescriptor.Type fieldType) {
        if (fieldType == null) {
            return -1;
        }
        Integer wireType = Constants.WIRE_TYPE_MAP.get(fieldType.toProto());
        if (wireType != null) {
            return wireType;
        } else {
            // Returns embedded messages, packed repeated fields message type, if field type doesn't map with the
            // predefined proto types.
            return 2;
        }
    }

    /**
     * Check whether message object is an array.
     *
     * @param object message object
     * @return true if object is array, false otherwise.
     */
    static boolean isArray(Object object) {
        return object != null && object.getClass().isArray();
    }

    /**
     * This function returns camelcase value of the input string.
     *
     * @param name string value
     * @return camelcase value
     */
    public static String toCamelCase(String name) {
        if (name == null) {
            return null;
        }
        String[] parts = name.split("_");
        StringBuilder camelCaseString = new StringBuilder();
        for (String part : parts) {
            camelCaseString.append(part.substring(0, 1).toUpperCase(Locale.ENGLISH)).append(part.substring(1)
                    .toLowerCase(Locale.ENGLISH));
        }
        return camelCaseString.toString();
    }
}