package uftTest;

import com.hp.octane.integrations.dto.DTOFactory;
import com.hp.octane.integrations.dto.general.MbtActionParameter;
import com.hp.octane.integrations.dto.general.MbtData;
import com.hp.octane.integrations.executor.converters.MfUftConverter;
import com.hp.octane.integrations.uft.ufttestresults.UftTestResultsUtils;
import com.hp.octane.integrations.uft.ufttestresults.schema.UftResultData;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class MbtTests {
    @Test
    public void parseConfiguration() {
        URL url = getClass().getResource("mbtExample1.json");
        MbtData mbtData = DTOFactory.getInstance().dtoFromJsonFile(new File(url.getFile()), MbtData.class);
        Assert.assertEquals(4, mbtData.getActions().size());
        Assert.assertEquals(6, mbtData.getData().getParameters().size());
        Assert.assertEquals(2, mbtData.getData().getIterations().size());
        mbtData.getData().getIterations().forEach(strings -> Assert.assertEquals(6, strings.size()));
        mbtData.getActions().forEach(mbtAction -> System.out.println(mbtAction.getName() + ", parameters: " +
                Optional.ofNullable(mbtAction.getParameters()).orElse(Collections.emptyList()).stream().map(MbtActionParameter::getParameterId).collect(Collectors.joining(", "))));
        mbtData.getData().getParameters().forEach(System.out::println);
        mbtData.getData().getIterations().forEach(strings -> strings.forEach(System.out::println));
    }

    @Test
    public void testComputeResourcePath() {
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase(Locale.ROOT).contains("windows")){
            String path = MfUftConverter.computeResourcePath("..\\ss", "c:\\aa\\bb");
            Assert.assertEquals("c:\\aa\\ss", path.toLowerCase(Locale.ROOT));
        }
    }

    @Test
    public void readActionResults1() {
        File file = new File(getClass().getResource("run_mbt_results_with_errors.xml").getFile());
        List<UftResultData> resultData = UftTestResultsUtils.getMBTData(file);
        Assert.assertEquals(1, resultData.size());
        UftResultData data1 = resultData.get(0);
        String errorMessage = "Cannot find the \"password\" object's parent \"Micro Focus MyFlight Sample\" (class WpfWindow).<br/>Verify that parent properties match an object currently displayed in your application.<br/><br/>Object's physical description:<br>wpftypename = window<br>regexpwndtitle = Micro Focus MyFlight Sample Application<br>devname = Micro Focus MyFlight Sample Application<br> (Warning). ";
        validateAction(23, "Warning", errorMessage,"Action1 [Two test_same function 2]", data1);
    }

    @Test
    public void readActionResults2() {
        File file = new File(getClass().getResource("run_mbt_results_8_successful.xml").getFile());
        List<UftResultData> resultData = UftTestResultsUtils.getMBTData(file);
        Assert.assertEquals(8, resultData.size());

        validateAction(1, "Done", "", "Launch App [FlightGUIBU2]", resultData.get(0));
        validateAction(3, "Done", "", "Login [FlightGUIBU2]", resultData.get(1));
        validateAction(1, "Done", "", "Search Order Tab [FlightGUIBU2]", resultData.get(2));
        validateAction(1, "Done", "", "Search Order By Name [FlightGUIBU2]", resultData.get(3));
        validateAction(0, "Done", "", "Select Order [FlightGUIBU2]", resultData.get(4));
        validateAction(8, "Done", "", "Update Order Details [FlightGUIBU2]", resultData.get(5));
        validateAction(7, "Done", "", "Update Order Details [FlightGUIBU2]", resultData.get(6));
        validateAction(0, "Done", "", "Close App [FlightGUIBU2]", resultData.get(7));
    }

    @Test
    public void readActionResults3() {
        File file = new File(getClass().getResource("run_mbt_results_with2_runs.xml").getFile());
        List<UftResultData> resultData = UftTestResultsUtils.getMBTData(file);
        Assert.assertEquals(2, resultData.size());

        validateAction(11, "Done", "", "Action1 [FUNCTION-TEST1]", resultData.get(0));
        validateAction(9 , "Done", "", "Action1 [FUNCTION-TEST1]", resultData.get(1));
    }

    private void validateAction(long duration, String result, String errorMessage, String lastParent, UftResultData data) {
        Assert.assertEquals(errorMessage, data.getMessage());
        Assert.assertEquals("Action", data.getType());
        Assert.assertEquals(duration, data.getDuration());
        Assert.assertEquals(result, data.getResult());
        Assert.assertEquals(4, data.getParents().size());
        Assert.assertEquals(lastParent, data.getParents().get(3));
    }
}
