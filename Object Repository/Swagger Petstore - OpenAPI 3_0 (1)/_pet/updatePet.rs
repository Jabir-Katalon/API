<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>updatePet</name>
   <tag></tag>
   <elementGuidId>503f62be-9797-475e-b0b3-1a4962f048b4</elementGuidId>
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
   <restRequestMethod>PUT</restRequestMethod>
   <restUrl>https://petstore3.swagger.io/api/v3/pet</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
