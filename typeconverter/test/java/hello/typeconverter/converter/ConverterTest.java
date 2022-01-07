package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ConverterTest {

    @Test
    public void test1() {

        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(1000);
        assertThat(result).isEqualTo("1000");

    }


    @Test
    public void test2() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("1000");
        assertThat(result).isEqualTo(1000);
    }


    @Test
    public void test3() {

        IpPortToStringConverter converter = new IpPortToStringConverter();
        String result = converter.convert(new IpPort("127.1.1.0", 8080));
        assertThat(result).isEqualTo("127.1.1.0:8080");
    }

    @Test
    public void test4() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        IpPort result = converter.convert("127.1.1.0:8080");
        assertThat(result).isEqualTo(new IpPort("127.1.1.0", 8080));
    }


}
