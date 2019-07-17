/*
 *  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import io.grpc.Status;
import io.siddhi.core.exception.SiddhiAppCreationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic Proto3 Message.
 *
 * @since 1.0.0
 */
public class Message extends GeneratedMessageV3 {
    private static final long serialVersionUID = 0L;
    private Map<String, Object> fields = new HashMap<>();
    private String messageName;
    
    // Use Message.newBuilder() to construct.
    protected Message(Builder builder) {
        super(builder);
        this.messageName = builder.messageName;
    }
    
    protected Message(String messageName) {
        this.messageName = messageName;
    }
    
    void setFieldValues(Map<String, Object> fieldValues) {
        this.fields = fieldValues;
    }
    
    public Map<String, Object> getFields() {
        return fields;
    }
    
    @Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }
    
    public Message(
            String messageName,
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        this(messageName);
        Descriptors.Descriptor messageDescriptor = getDescriptor();
        Map<Integer, Descriptors.FieldDescriptor> fields = new HashMap<>();
        for (Descriptors.FieldDescriptor fieldDescriptor : messageDescriptor.getFields()) {
            Descriptors.FieldDescriptor.Type fieldType = fieldDescriptor.getType();
            int number = fieldDescriptor.getNumber();
            int byteCode = ((number << 3) + MessageUtils.getFieldWireType(fieldType));
            fields.put(byteCode, fieldDescriptor);
        }
        
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                com.google.protobuf.UnknownFieldSet.newBuilder();
        try {
            boolean done = false;
            while (!done) {
                int tag = input.readTag();
                if (tag == 0) {
                    done = true;
                } else if (fields.containsKey(tag)) {
                    Descriptors.FieldDescriptor fieldDescriptor = fields.get(tag);
                    String name = fieldDescriptor.getName();
                    switch (fieldDescriptor.getType().toProto().getNumber()) {

                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING_VALUE: {
                            if (fieldDescriptor.isRepeated()) {
                                List<String> messages = new ArrayList<>();
                                if (this.fields.containsKey(name)) {
                                    messages = (List<String>) this.fields.get(name);
                                }
                                messages.add(input.readStringRequireUtf8());
                                this.fields.put(name, messages);
                            } else {
                                this.fields.put(name, input.readStringRequireUtf8());
                            }
                            break;
                        }
                        default: {
                            throw new SiddhiAppCreationException("Error while decoding request message. Field " +
                                    "type is not supported : " + fieldDescriptor.getType());
                        }
                    }
                } else {
                    if (!parseUnknownFieldProto3(
                            input, unknownFields, extensionRegistry, tag)) {
                        done = true;
                    }
                }
            }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(
                    e).setUnfinishedMessage(this);
        } finally {
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }
    }
    
    public Descriptors.Descriptor getDescriptor() {
        return MessageRegistry.getInstance().getMessageDescriptor(messageName);
    }

    protected FieldAccessorTable internalGetFieldAccessorTable() {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    private byte memoizedIsInitialized = -1;

    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }

        memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
        Descriptors.Descriptor messageDescriptor = getDescriptor();
        for (Descriptors.FieldDescriptor fieldDescriptor : messageDescriptor.getFields()) {
            if (fields.containsKey(fieldDescriptor.getName())) {
                switch (fieldDescriptor.getType().toProto().getNumber()) {

                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING_VALUE: {
                        Object msgObject = fields.get(fieldDescriptor.getName());
                        if (MessageUtils.isArray(msgObject)) {
                            String[] messages = (String[]) msgObject;
                            for (String message : messages) {
                                output.writeString(fieldDescriptor.getNumber(), message);
                            }
                        } else {
                            GeneratedMessageV3.writeString(output, fieldDescriptor.getNumber(),
                                    msgObject);
                        }
                        break;
                    }
                    default: {
                        throw new SiddhiAppCreationException("Error while writing output stream. Field " +
                                "type is not supported : " + fieldDescriptor.getType());
                    }
                }

            }
        }
        unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) {
            return size;
        }

        size = 0;
        Descriptors.Descriptor messageDescriptor = getDescriptor();
        if (messageDescriptor == null) {
            throw Status.INTERNAL
                    .withDescription("Error while processing the message, Couldn't find message descriptor.")
                    .asRuntimeException();
        }
        for (Descriptors.FieldDescriptor fieldDescriptor : messageDescriptor.getFields()) {
            if (fields.containsKey(fieldDescriptor.getName())) {
                switch (fieldDescriptor.getType().toProto().getNumber()) {

                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING_VALUE: {
                        Object msgObject = fields.get(fieldDescriptor.getName());
                        if (MessageUtils.isArray(msgObject)) {
                            String[] messages = (String[]) msgObject;
                            for (String message : messages) {
                                size += GeneratedMessageV3.computeStringSize(fieldDescriptor
                                        .getNumber(), message);
                            }
                        } else {
                            size += GeneratedMessageV3.computeStringSize(fieldDescriptor
                                    .getNumber(), fields.get(fieldDescriptor.getName()));
                        }
                        break;
                    }
                    default: {
                        throw new SiddhiAppCreationException("Error while calculating the serialized type. Field " +
                                "type is not supported : " + fieldDescriptor.getType());
                    }
                }
            }
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
    }

    public Builder newBuilderForType() {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    public static Builder newBuilder(String messageName) {
        return new Message.Builder(messageName);
    }

    public Builder toBuilder() {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    protected Builder newBuilderForType(BuilderParent parent) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * <pre>
     * The request message containing the user's name.
     * </pre>
     * <p>
     * Protobuf type {@code org.ballerinalang.net.grpc.Message}
     */
    public static final class Builder extends GeneratedMessageV3.Builder<Builder> {

        private Map<String, Object> fields = new HashMap<>();
        private final String messageName;

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            throw new UnsupportedOperationException("Operation is not supported");
        }

        // Construct using org.ballerinalang.net.grpc.Message.newBuilder()
        private Builder(String messageName) {
            this.messageName = messageName;
        }

        private Descriptors.Descriptor getDescriptor() {
            return MessageRegistry.getInstance().getMessageDescriptor(messageName);
        }

        public Builder clear() {
            super.clear();
            fields.clear();

            return this;
        }

        public Descriptors.Descriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public Message getDefaultInstanceForType() {
            return new Message(messageName);
        }
        
        public Message build() {
            Message result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }
        
        public Message buildPartial() {
            Message result = new Message(this);
            if (!fields.isEmpty()) {
                result.setFieldValues(fields);
            }
            onBuilt();
            return result;
        }
        
        public Builder addField(String name, Object value) {
            fields.put(name, value);
            return this;
        }
        
        @Override
        public String toString() {
            return "Builder{" +
                    "fields=" + fields +
                    ", messageName='" + messageName + '\'' +
                    '}';
        }
    }
    
    @Override
    public com.google.protobuf.Parser<Message> getParserForType() {
        return new MessageParser(messageName);
    }
    
    public Message getDefaultInstanceForType() {
        throw new UnsupportedOperationException("Default instance is not supported.");
    }
    
}