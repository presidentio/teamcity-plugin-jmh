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

/**
 * Created by presidentio on 12.05.15.
 */
public class RunnerParamTypeConst {

    public static final int STRING = 1;
    public static final int INT = 2;
    public static final int BOOL = 3;
    public static final int STRING_SELECT = 4;

    public int getSTRING() {
        return STRING;
    }

    public int getINT() {
        return INT;
    }

    public int getBOOL() {
        return BOOL;
    }

    public int getSTRING_SELECT() {
        return STRING_SELECT;
    }
}
