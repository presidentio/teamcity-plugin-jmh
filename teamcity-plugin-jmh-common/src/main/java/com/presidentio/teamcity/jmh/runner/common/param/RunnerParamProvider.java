package com.presidentio.teamcity.jmh.runner.common.param;

import com.presidentio.teamcity.jmh.runner.common.cons.TimeUnitConst;
import com.presidentio.teamcity.jmh.runner.common.cons.VerboseModeConst;
import com.presidentio.teamcity.jmh.runner.common.cons.WarmupModeConst;

import java.util.Collection;
import java.util.HashMap;

import static com.presidentio.teamcity.jmh.runner.common.cons.ModeConst.MODES_WITH_DESCRIPTION;
import static com.presidentio.teamcity.jmh.runner.common.cons.SettingsConst.*;

/**
 * Created by presidentio on 12.05.15.
 */
public class RunnerParamProvider {

    private static final HashMap<String, RunnerParam> ALL = new HashMap<>();

    static {
        ALL.put(PROP_BENCHMARKS, new BaseRunnerParam(PROP_BENCHMARKS, "", false, "Benchmarks", "Benchmarks to run (regexp+)."));
        ALL.put(PROP_MODE, new SelectRunnerParameter(MODES_WITH_DESCRIPTION, PROP_MODE, "-bm", false, "Benchmark mode",
                "Benchmark mode."));
        ALL.put(PROP_BATCH_SIZE, new IntRunnerParam(PROP_BATCH_SIZE, "-bs", false, "Batch size",
                "Number of benchmark method calls per operation. (some benchmark modes can ignore this setting)", 0));
        ALL.put(PROP_EXCLUDE, new BaseRunnerParam(PROP_EXCLUDE, "-e", false, "Exclude",
                "Benchmarks to exclude from the run."));
        ALL.put(PROP_FORKS, new IntRunnerParam(PROP_FORKS, "-f", false, "Forks",
                "How many times to forks a single benchmark. Use 0 to disable forking altogether (WARNING: disabling " +
                "forking may have detrimental impact on benchmark and infrastructure reliability, you might want " +
                "to use different warmup mode instead).", 0));
        ALL.put(PROP_FAIL_ON_ERROR, new BoolRunnerParam(PROP_FAIL_ON_ERROR, "-foe", false, "Fail on error",
                "Should JMH fail immediately if any benchmark had experienced the unrecoverable error?"));
        ALL.put(PROP_GC, new BoolRunnerParam(PROP_GC, "-gc", false, "Force GC", "Should JMH force GC between iterations?"));
        ALL.put(PROP_MEASUREMENT_ITERATIONS, new IntRunnerParam(PROP_MEASUREMENT_ITERATIONS, "-i", false,
                "Iterations", "Number of measurement iterations to do.", 0));
        ALL.put(PROP_JVM, new BaseRunnerParam(PROP_JVM, "-jvm", false, "Custom JVM",
                "Custom JVM to use when forking (path to JVM executable)."));
        ALL.put(PROP_JVM_ARGS, new BaseRunnerParam(PROP_JVM_ARGS, "-jvmArgs", false, "Custom JVM args",
                "Custom JVM args to use when forking."));
        ALL.put(PROP_JVM_ARGS_APPEND, new BaseRunnerParam(PROP_JVM_ARGS_APPEND, "-jvmArgsAppend", false,
                "Custom JVM args append", "Custom JVM args to use when forking (append these)"));
        ALL.put(PROP_JVM_ARGS_PREPEND, new BaseRunnerParam(PROP_JVM_ARGS_PREPEND, "-jvmArgsPrepend", false,
                "Custom JVM args prepend", "Custom JVM args to use when forking (prepend these)"));
        ALL.put(PROP_OPERATIONS_PER_INVOCATION, new IntRunnerParam(PROP_OPERATIONS_PER_INVOCATION, "-opi", false,
                "Operations", "Operations per invocation.", 0));
        ALL.put(PROP_BENCHMARK_PARAMETERS, new BaseRunnerParam(PROP_BENCHMARK_PARAMETERS, "-p", false,
                "Benchmark parameters", "This option is expected to be used once per parameter. Parameter name and " +
                "parameter values should be separated with equals sign. Parameter values should be separated with commas."));
        ALL.put(PROP_PROFILERS, new BaseRunnerParam(PROP_PROFILERS, "-prof", false, "Profilers",
                "Use profilers to collect additional data."));
        ALL.put(PROP_TIME_PER_MEASUREMENT, new BaseRunnerParam(PROP_TIME_PER_MEASUREMENT, "-r", false,
                "Measurement time", "Time to spend at each measurement iteration. Arguments accept time suffixes, like \"100ms\"."));
        ALL.put(PROP_SYNCHRONIZE, new BoolRunnerParam(PROP_SYNCHRONIZE, "-si", false, "Synchronized",
                "Synchronize iterations?"));
        ALL.put(PROP_THREADS, new IntRunnerParam(PROP_THREADS, "-t", false, "Threads",
                "Number of worker threads to run with.", 0));
        ALL.put(PROP_THREAD_DISTRIBUTION, new BaseRunnerParam(PROP_THREAD_DISTRIBUTION, "-tg", false,
                "Thread group distribution", "Override thread group distribution for asymmetric benchmarks."));
        ALL.put(PROP_TIMEOUT, new BaseRunnerParam(PROP_TIMEOUT, "-to", false, "Timeout",
                "Timeout for benchmark iteration. Arguments accept time suffixes, like \"100ms\"."));
        ALL.put(PROP_TIME_UNIT, new SelectRunnerParameter(TimeUnitConst.TIME_UNITS_WITH_DESCRIPTION, PROP_TIME_UNIT,
                "-tu", false, "Time unit", "Output time unit. Available time units are: [m, s, ms, us, ns]."));
        ALL.put(PROP_VERBOSITY, new SelectRunnerParameter(VerboseModeConst.ALL_WITH_DESCRIPTION, PROP_VERBOSITY, "-v",
                false, "Verbosity", "Verbosity mode. Available modes are: [SILENT, NORMAL, EXTRA]"));
        ALL.put(PROP_TIME_PER_WARMUP, new BaseRunnerParam(PROP_TIME_PER_WARMUP, "-w", false, "Warmup time",
                "Time to spend at each warmup iteration. Arguments accept time suffixes, like \"100ms\"."));
        ALL.put(PROP_WARMUP_BATCH_SIZE, new IntRunnerParam(PROP_WARMUP_BATCH_SIZE, "-wbs", false,
                "Warmup batch size", "Warmup batch size: number of benchmark method calls per operation. " +
                "(some benchmark modes can ignore this setting)", 0));
        ALL.put(PROP_WARMUP_FORKS, new IntRunnerParam(PROP_WARMUP_FORKS, "-wf", false, "Warmup forks",
                "How many warmup forks to make for a single benchmark. 0 to disable warmup forks.", 0));
        ALL.put(PROP_WARMUP_ITERATIONS, new IntRunnerParam(PROP_WARMUP_ITERATIONS, "-wi", false, "Warmup iterations",
                "Number of warmup iterations to do.", 0));
        ALL.put(PROP_WARMUP_MODE, new SelectRunnerParameter(WarmupModeConst.ALL_WITH_DESCRIPTION, PROP_WARMUP_MODE,
                "-wm", false, "Warmup mode", "Warmup mode for warming up selected benchmarks. " +
                "Warmup modes are: [INDI, BULK, BULK_INDI]."));
        ALL.put(PROP_WARMUP_BENCHMARKS, new BaseRunnerParam(PROP_WARMUP_BENCHMARKS, "-wmb", false,
                "Warmup benchmarks", "Warmup benchmarks to include in the run in addition to already selected. " +
                "JMH will not measure these benchmarks, but only use them for the warmup."));
    }

    public Collection<RunnerParam> all() {
        return ALL.values();
    }

    public RunnerParam get(String name) {
        return ALL.get(name);
    }

}
