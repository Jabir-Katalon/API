<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>addPet</name>
   <tag></tag>
   <elementGuidId>330c4d9f-d7a5-4970-b08c-9874be7b45ac</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;contentType&quot;: &quot;application/x-www-form-urlencoded&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;,
  &quot;parameters&quot;: [
    {
      &quot;name&quot;: &quot;id&quot;,
      &quot;value&quot;: &quot;10&quot;
    },
    {
      &quot;name&quot;: &quot;name&quot;,
      &quot;value&quot;: &quot;doggie&quot;
    },
    {
      &quot;name&quot;: &quot;category&quot;,
      &quot;value&quot;: &quot;{name\u003dDogs, id\u003d1}&quot;
    },
    {
      &quot;name&quot;: &quot;photoUrls&quot;,
      &quot;value&quot;: &quot;[string]&quot;
    },
    {
      &quot;name&quot;: &quot;tags&quot;,
      &quot;value&quot;: &quot;[{name\u003dstring, id\u003d0}]&quot;
    },
    {
      &quot;name&quot;: &quot;status&quot;,
      &quot;value&quot;: &quot;available&quot;
    }
  ]
}</httpBodyContent>
   <httpBodyType>x-www-form-urlencoded</httpBodyType>
   <katalonVersion>9.6.0</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path>/pet</path>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>https://petstore3.swagger.io/api/v3/pet</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>GlobalVariable.null</defaultValue>
      <description></description>
      <id>0fad4f9c-c7de-43c3-b629-ec5b5e2f750f</id>
      <masked>false</masked>
      <name>variable</name>
   </variables>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
