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
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_username_/getUserByName",
        "endpoint": "/user/{username}",
        "method": "get"
    }
]

testObjects.each { testObject ->
    def requestObject = findTestObject(testObject.testObjectId)
    addHeaderConfiguration(requestObject)

    if (testObject.endpoint.contains("{username}")) {
        def variables = ["username": "nonExistingUser"]
        requestObject = findTestObject(testObject.testObjectId, variables)
    }

    def response = WSBuiltInKeywords.sendRequest(requestObject)
    WSBuiltInKeywords.verifyResponseStatusCode(response, 404)
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

