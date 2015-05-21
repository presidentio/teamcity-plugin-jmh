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
 * Created by presidentio on 12.05.15.
 */
public class VerboseModeConst {

    public static final String DEFAULT = "default";
    public static final String SILENT = "SILENT";
    public static final String NORMAL = "NORMAL";
    public static final String EXTRA = "EXTRA";

    public static final List<String> ALL = Arrays.asList(DEFAULT, SILENT, NORMAL, EXTRA);

    public static final Map<String, String> ALL_WITH_DESCRIPTION = new LinkedHashMap<>();

    static {
        ALL_WITH_DESCRIPTION.put(DEFAULT, "<Default>");
        ALL_WITH_DESCRIPTION.put(SILENT, "Silent");
        ALL_WITH_DESCRIPTION.put(NORMAL, "Normal");
        ALL_WITH_DESCRIPTION.put(EXTRA, "Extra");
    }

}
