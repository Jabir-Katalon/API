<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>updateUser</name>
   <tag></tag>
   <elementGuidId>25bbdf1c-5b68-48f7-8c8a-3efa4a998971</elementGuidId>
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
      &quot;name&quot;: &quot;username&quot;,
      &quot;value&quot;: &quot;theUser&quot;
    },
    {
      &quot;name&quot;: &quot;firstName&quot;,
      &quot;value&quot;: &quot;John&quot;
    },
    {
      &quot;name&quot;: &quot;lastName&quot;,
      &quot;value&quot;: &quot;James&quot;
    },
    {
      &quot;name&quot;: &quot;email&quot;,
      &quot;value&quot;: &quot;john@email.com&quot;
    },
    {
      &quot;name&quot;: &quot;password&quot;,
      &quot;value&quot;: &quot;12345&quot;
    },
    {
      &quot;name&quot;: &quot;phone&quot;,
      &quot;value&quot;: &quot;12345&quot;
    },
    {
      &quot;name&quot;: &quot;userStatus&quot;,
      &quot;value&quot;: &quot;1&quot;
    }
  ]
}</httpBodyContent>
   <httpBodyType>x-www-form-urlencoded</httpBodyType>
   <katalonVersion>9.6.0</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path>/user/{username}</path>
   <restRequestMethod>PUT</restRequestMethod>
   <restUrl>https://petstore3.swagger.io/api/v3/user/${username}</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>null</defaultValue>
      <description>&lt;string> {Required} name that need to be deleted</description>
      <id>27c8072b-8199-4768-b2af-731faf58ca6d</id>
      <masked>false</masked>
      <name>username</name>
   </variables>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
