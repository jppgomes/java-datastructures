/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.examples.cluster;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.examples.ExampleNodeStartup;
import org.apache.ignite.examples.ExamplesUtils;
import org.apache.ignite.lang.IgnitePredicate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ClusterGroupExample {

	public static Ignite ignite = null;

	/**
	 * Executes example.
	 *
	 * @param args Command line arguments, none required.
	 * @throws IgniteException If example execution failed.
	 */
	public static void main(String[] args) throws IgniteException {

		try {

			ignite = Ignition
					.start("/Users/admim/Documents/apache-ignite-2.7.5-bin/examples/config/example-ignite.xml");

		} catch (Exception e) {

			System.out.println(e.getCause());
		}

			// Local listener that listenes to local events.
			IgnitePredicate<CacheEvent> locLsnr = evt -> {
				
				BinaryObject po = (BinaryObject) evt.newValue();
				
				String userdata = po.field("USERDATA");
				Timestamp id = po.field("TIMEIN");
								
				Gson gson = new Gson();
				Map<String, Object> retMap = new Gson().fromJson(
						userdata, new TypeToken<HashMap<String, Object>>() {}.getType()
					);
				
				retMap.put("id", "wkg4ygj9219atsi2s3l857d3d9t424");
				
				System.out.println(retMap + " | " + id);
				
				
				
				Map<String, Map<String, String>> map = new HashMap<>();
				
				return true; // Continue listening.
			};

			ignite.events().localListen(locLsnr, EventType.EVT_CACHE_OBJECT_PUT);


		CacheConfiguration<?, ?> cacheConfiguration = new CacheConfiguration<>("Jp").setSqlSchema("PUBLIC");

		IgniteCache<?, ?> cache = ignite.createCache(cacheConfiguration);

		try {

			cache.query(new SqlFieldsQuery(
					"CREATE TABLE wkg4ygj9219atsi2s3l857d3d9t424 (id VARCHAR PRIMARY KEY, USERDATA VARCHAR, TIMEIN TIMESTAMP) WITH \"template=replicated\""))
					.getAll();

			List<List<?>> list = 
					
					cache.query(new SqlFieldsQuery(
					"INSERT INTO wkg4ygj9219atsi2s3l857d3d9t424 (ID, USERDATA, TIMEIN) VALUES ('wkg4ygj9219atsi2s3l857d3d9t424', '{\"sessionId\": \"12345\", \"nmCli\": \"joao\", \"idade\": 20 }', '2038-01-19 03:14:07') "))
					.getAll();
			
			System.out.println("insert and get: " + list.get(0).get(0));
			

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * Print 'Hello' message on remote nodes.
	 *
	 * @param ignite Ignite.
	 * @param grp    Cluster group.
	 * @throws IgniteException If failed.
	 */
	private static void sayHello(Ignite ignite, final ClusterGroup grp) throws IgniteException {
		// Print out hello message on all cluster nodes.
		ignite.compute(grp)
				.broadcast(() -> System.out.println(">>> Hello Node: " + grp.ignite().cluster().localNode().id()));
	}
}
