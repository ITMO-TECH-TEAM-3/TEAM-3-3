package com.tech.tournaments.configuration;

/**
 * @Author : Licf
 * @ Описание: класс конфигурации Swagger2
 * При интеграции с Spring boot поместите его в каталог того же уровня, что и Application.java.
 * Через аннотацию @Configuration позвольте Spring загрузить этот тип конфигурации.
 * Затем используйте аннотацию @ EnableSwagger2, чтобы включить Swagger2.
 * @Date : Created in 17:02 2018/4/12
 * @Modified By : Licf
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 @Api: украсить весь класс и описать роль контроллера
 @ApiOperation: описать метод класса или интерфейс
 @ApiParam: описание одного параметра
 @ApiModel: использовать объекты для получения параметров
 @ApiProperty: при получении параметров с объектом, опишите поле объекта
 @ApiResponse: 1 описание ответа HTTP
 @ApiResponses: общее описание ответа HTTP.
 @ApiIgnore: используйте эту аннотацию, чтобы игнорировать этот API
 @ApiError: информация, возвращаемая при возникновении ошибки
 @ApiImplicitParam: параметр запроса
 @ApiImplicitParams: несколько параметров запроса
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("JavaInUse API")
                .description("JavaInUse API reference for developers").version("1.0").build();
    }

}
