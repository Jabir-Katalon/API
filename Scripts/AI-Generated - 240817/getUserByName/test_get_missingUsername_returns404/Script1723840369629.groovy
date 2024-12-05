import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def addHeaderConfiguration(request) {
    def content_type_header = new TestObjectProperty("content-type", ConditionType.EQUALS, "application/json")
    request.getHttpHeaderProperties().add(content_type_header)
}

uuid = UUID.randomUUID().toString()

def testObjects = [
    {
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/",
        "endpoint": "/user/",
        "method": "get"
    }
]

def responseObject
def expectedStatusCode = 404

testObjects.each { testObject ->
    def request = findTestObject(testObject.testObjectId)
    addHeaderConfiguration(request)

    if (testObject.endpoint.contains("{")) {
        def variables = [:]
        // Add path parameters if necessary
        // variables.put("paramName", paramValue)
        request = findTestObject(testObject.testObjectId, variables)
    }

    responseObject = WSBuiltInKeywords.sendRequest(request)
    assert WSBuiltInKeywords.verifyResponseStatusCode(responseObject, expectedStatusCode)
}

println("Step 1 - GET ${responseObject.getRestResponse().getRequestUrl()}")
println("Response Status Code: ${responseObject.getStatusCode()}")
println("Step 2 - Verify response status code is ${expectedStatusCode}")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

