/**
 * Copyright 2015 presidentio
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
package com.presidentio.teamcity.jmh.runner.common.cons;

import java.util.*;

/**
 * Created by presidentio on 23.04.15.
 */
public class ModeConst {

    public static final String THROUGHPUT = "thrpt";
    public static final String AVERAGE_TIME = "avgt";
    public static final String SAMPLE_TIME = "sample";
    public static final String SINGLE_SHOT_TIME = "ss";
    public static final String ALL = "all";
    public static final String DEFAULT = "default";

    public static final List<String> MODES = Arrays.asList(ALL, AVERAGE_TIME, SAMPLE_TIME, SINGLE_SHOT_TIME,
            THROUGHPUT, DEFAULT);

    public static final Map<String, String> MODES_WITH_DESCRIPTION = new LinkedHashMap<>();

    static {
        MODES_WITH_DESCRIPTION.put(DEFAULT, "<Default>");
        MODES_WITH_DESCRIPTION.put(AVERAGE_TIME, "Average time");
        MODES_WITH_DESCRIPTION.put(SAMPLE_TIME, "Sample time");
        MODES_WITH_DESCRIPTION.put(SINGLE_SHOT_TIME, "Single shot time");
        MODES_WITH_DESCRIPTION.put(THROUGHPUT, "Throughput");
        MODES_WITH_DESCRIPTION.put(ALL, "All");
    }

    public String name(String mode) {
        return MODES_WITH_DESCRIPTION.get(mode);
    }
}
