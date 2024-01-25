package BookApi.Book.advice;

import BookApi.Book.dto.BookRequest;
import BookApi.Book.dto.BookResponse;
import BookApi.Book.model.RequestLogger;
import BookApi.Book.repository.RequestLoggerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class RequestLoggingAdvice {

//    @Around("execution(* BookApi.Book.Controller(..))")
//    public Object controllers(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        if (joinPoint.getArgs().length != 0 && joinPoint.getArgs()[0] instanceof BookResponse) {
//
//            BookResponse requestPayload = (BookResponse) joinPoint.getArgs()[0]; // define request payload object
//
//
//
//            return aroundAbstraction(
//                    () -> {
//
//                        // get HttpServletRequest for current request about to be processed by spring.
//                        HttpServletRequest servletRequest = ((ServletRequestAttributes) currentRequestAttributes()).getRequest();
//
//                        log.info("{} request received", joinPoint.getSignature().getName());
//
//                        // instantiate payloadLogger entity
//                        payloadLogger = new PayloadLogger();
//                        // instantiate requestLogger entity
//                        requestLogger = new RequestLogger();
//                        requestLogger.setOperation(joinPoint.getSignature().getName());
//                        requestLogger.setRequestTime(now());
//                        requestLogger.setSourceModuleId(getModuleId(servletRequest));
//
//                        requestLogger.setTransactionReference(requestPayload.getRequestId());
//                        requestLogger.setBillName(requestPayload.getBillName());
//                        payloadLogger.setTransactionReference(requestPayload.getRequestId());
//                        payloadLogger.setRequestPayload(requestPayload.print());
//
//                        return requestPayload.getRequestId();
//                    },
//                    joinPoint,
//                    returnedValue -> {
//
//                        Response<?> responseBody = (Response<?>) returnedValue; // extract response body from ResponseEntity
//
//                        payloadLogger.setResponsePayload(requireNonNull(responseBody).print()); //set response payload
//
//                        // set other requestLogger fields.
//                        requestLogger.setResponseTime(now());
//                        requestLogger.setResponseCode(responseBody.getResponseCode());
//                        requestLogger.setResponseMessage(responseBody.getResponseDescription());
//
//
//                        loggerService.logRequest(requestLogger, payloadLogger); // save RequestDetails and PayloadDetails to DB
//                    }
//            );
//
//        } else {
//            return aroundAbstraction(
//                    () -> null,
//                    joinPoint,
//                    (returnedValue) -> {}
//            );
//        }
//
////        return joinPoint.proceed();
//    }
    private final RequestLoggerRepository requestLoggerRepository;

    private final Logger logger = LoggerFactory.getLogger(RequestLoggingAdvice.class);

    @Around("execution(* BookApi.Book.controller.*.*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        // Log the request

        RequestLogger requestLogger =  new RequestLogger();
        logger.info("Request received for method: {}", joinPoint.getSignature().toShortString());
        logger.info("Request arguments: {}", joinPoint.getArgs()[1]);



        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // Log request details
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Request Method: {}", request.getMethod());
        logger.info("Request Headers: {}", request.getHeaderNames());

        Class clazz = joinPoint.getTarget().getClass();




        String requestBody = StreamUtils.copyToString(request.getInputStream(), Charset.defaultCharset());
        logger.info("Request Headers: {}", clazz);



        //Payload to retrieve the request  1
        String payload = getPayload(joinPoint);
        logger.info("Request requestBody: {}",payload);

        //Payload to retrieve the request  2

        StringBuilder builder = new StringBuilder(joinPoint.getArgs()[0].toString());
        BookRequest builder2 = (BookRequest) joinPoint.getArgs()[1];

        requestLogger.setRequestPayload(String.valueOf(builder));


        logger.info("Request Builder: {}", builder);
        logger.info("Request Builder2: {}", builder2);

        // Proceed with the actual method execution
        Object result = joinPoint.proceed();

        // Log the response
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            logger.info("Response code: {}", responseEntity.getStatusCodeValue());
            logger.info("Response body: {}", responseEntity.getBody());
            requestLogger.setResponsePayload(responseEntity.getBody().toString());
        }

        requestLoggerRepository.save(requestLogger);

        return result;
    }



    private String getPayload(ProceedingJoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            String parameterName = signature.getParameterNames()[i];
            builder.append(parameterName);
            builder.append(": ");
            builder.append(joinPoint.getArgs()[i].toString());
            builder.append(", ");
            logger.info("JointPoint: {}", i);
        }
        return builder.toString();
    }




    private static String getRequestBody(HttpServletRequest request) {
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = null;

        try {
            // Get the InputStream from the request
            reader = new BufferedReader(new InputStreamReader(request.getInputStream()));

            // Read the request body
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace(); // Handle exception according to your needs
        } finally {
            // Close the BufferedReader if it's open
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(); // Handle exception according to your needs
                }
            }
        }

        return requestBody.toString();
    }
}



