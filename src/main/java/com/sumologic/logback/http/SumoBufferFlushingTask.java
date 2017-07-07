/**
 * _____ _____ _____ _____    __    _____ _____ _____ _____
 * |   __|  |  |     |     |  |  |  |     |   __|     |     |
 * |__   |  |  | | | |  |  |  |  |__|  |  |  |  |-   -|   --|
 * |_____|_____|_|_|_|_____|  |_____|_____|_____|_____|_____|
 * <p>
 * UNICORNS AT WARP SPEED SINCE 2010
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.sumologic.logback.http;

import java.util.List;

import com.sumologic.logback.aggregation.BufferFlushingTask;
import com.sumologic.logback.queue.BufferWithEviction;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jose Muniz (jose@sumologic.com)
 */
@Slf4j
public class SumoBufferFlushingTask extends BufferFlushingTask<String, String> {

	private SumoHttpSender sender;
	private long           maxFlushInterval;
	private long           messagesPerRequest;
	private String 		   sourceName;
	private String         sourceHost;
	private String         sourceCategory;

	public SumoBufferFlushingTask(BufferWithEviction<String> queue) {
		super(queue);
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setSourceHost(String sourceHost) { this.sourceHost = sourceHost; }

	public void setSourceCategory(String sourceCategory) { this.sourceCategory = sourceCategory; }

	public void setSender(SumoHttpSender sender) {
		this.sender = sender;
	}

	public void setMessagesPerRequest(long messagesPerRequest) {
		this.messagesPerRequest = messagesPerRequest;
	}

	public void setMaxFlushInterval(long maxFlushInterval) {
		this.maxFlushInterval = maxFlushInterval;
	}

	@Override
	protected long getMaxFlushInterval() {
		return maxFlushInterval;
	}

	@Override
	protected long getMessagesPerRequest() {
		return messagesPerRequest;
	}

	protected String getSourceName() {
		return sourceName;
	}

	protected String getSourceHost() {
		return sourceHost;
	}

	@Override
	protected String getSourceCategory() {
		return sourceCategory;
	}

	@Override
	protected String aggregate(List<String> messages) {
		StringBuilder builder = new StringBuilder(messages.size() * 10);
		for (String message : messages) {
			builder.append(message + "\r\n");
		}
		return builder.toString();
	}

	@Override
	protected void sendOut(String body, String name, String sourceHost, String sourceCategory) {
		if (sender.isInitialized()) {
			sender.send(body, name, sourceHost, sourceCategory);
		} else {
			log.error("HTTPSender is not initialized");

		}
	}
}
