import com.presidentio.teamcity.jmh.entity.Benchmark;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by The Beast on 18/06/2016.
 */
public class BenchmarkParseTest {

    @Test
    public void testParseParams() throws IOException {
        List<Benchmark> benchmarks = testParse("jmh-result-with-params.json");
        assertTrue(benchmarks.size() > 0);
    }

    @Test
    public void testParseNoParams() throws IOException {
        List<Benchmark> benchmarks = testParse("jmh-result-no-params.json");
        assertTrue(benchmarks.size() > 0);
    }

    private List<Benchmark> testParse( String JsonResultsResource ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(BenchmarkParseTest.class.getResource( JsonResultsResource ).getPath(), objectMapper.getTypeFactory().constructCollectionType(List.class, Benchmark.class));

    }

}
