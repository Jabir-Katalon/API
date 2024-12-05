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
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_logout/logoutUser",
        "endpoint": "/user/logout",
        "method": "get"
    }
]

def responseObject
def expectedStatusCode = 0

testObjects.each { testObject ->
    def request = findTestObject(testObject.testObjectId)
    addHeaderConfiguration(request)

    if (testObject.endpoint.contains("{")) {
        def variables = [:]
        variables.put("variableName", "variableValue")
        request = findTestObject(testObject.testObjectId, variables)
    }

    if (request.getHttpBodyContent() == null) {
        def bodyContent = new HttpTextBodyContent(replaceSuffixWithUUID('{"key": "value"}'))
        request.setBodyContent(bodyContent)
    }

    responseObject = WSBuiltInKeywords.sendRequest(request)
    WSBuiltInKeywords.verifyResponseStatusCode(responseObject, expectedStatusCode)
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

