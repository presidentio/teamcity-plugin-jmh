import com.presidentio.teamcity.jmh.entity.Benchmark;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by The Beast on 18/06/2016.
 */
public class BenchmarkParseTest {

    @Test
    public void testParseParams() throws IOException {
        List<Benchmark> benchmarks = testParse("jmh-result-with-params.json");
        // check that we parsed the params in the first benchmark
        assertEquals("1",benchmarks.get(0).getParams().get("arg"));
        assertEquals("0",benchmarks.get(0).getParams().get("certainty"));

    }

    @Test
    public void testParseNoParams() throws IOException {
        List<Benchmark> benchmarks = testParse("jmh-result-no-params.json");
        assertNull( benchmarks.get(0).getParams() );
    }

    private List<Benchmark> testParse( String JsonResultsResource ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Benchmark> benchmarks = objectMapper.readValue(new File(BenchmarkParseTest.class.getResource( JsonResultsResource ).getFile()), objectMapper.getTypeFactory().constructCollectionType(List.class, Benchmark.class));
        // check we actually parsed something
        assertTrue(benchmarks.size() > 0);
        return benchmarks;

    }

}
