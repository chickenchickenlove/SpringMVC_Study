package hello.typeconverter.converter;


import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionServiceTest {


    @Test
    public void test1() {
        DefaultConversionService conversionService = new DefaultConversionService();

        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new StringToIntegerConverter());


        assertThat(conversionService.convert("1000", Integer.class))
                .isEqualTo(1000);

        assertThat(conversionService.convert(1000, String.class))
                .isEqualTo("1000");


        assertThat(conversionService.convert("127.1.1.0:8080", IpPort.class))
                .isEqualTo(new IpPort("127.1.1.0", 8080));


        assertThat(conversionService.convert(new IpPort("127.1.1.0", 8080), String.class))
                .isEqualTo("127.1.1.0:8080");

    }





}
